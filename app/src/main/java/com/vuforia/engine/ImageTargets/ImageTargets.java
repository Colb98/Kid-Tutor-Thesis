/*===============================================================================
Copyright (c) 2020 PTC Inc. All Rights Reserved.

Copyright (c) 2012-2014 Qualcomm Connected Experiences, Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other
countries.
===============================================================================*/

package com.vuforia.engine.ImageTargets;

import java.lang.ref.WeakReference;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;


import com.mus.myapplication.R;
import com.mus.myapplication.Texture;
import com.mus.myapplication.VuforiaSampleGLView;
import com.vuforia.Vuforia;
import com.vuforia.INIT_ERRORCODE;
import com.vuforia.INIT_FLAGS;

import androidx.annotation.NonNull;


/** The main activity for the ImageTargets sample. */
public class ImageTargets extends Activity
{
    // Focus mode constants:
    private static final int FOCUS_MODE_NORMAL = 0;
    private static final int FOCUS_MODE_CONTINUOUS_AUTO = 1;

    // Application status constants:
    private static final int APPSTATUS_UNINITED = -1;
    private static final int APPSTATUS_INIT_APP = 0;
    private static final int APPSTATUS_INIT_VUFORIA = 1;
    private static final int APPSTATUS_INIT_TRACKER = 2;
    private static final int APPSTATUS_INIT_APP_AR = 3;
    private static final int APPSTATUS_LOAD_TRACKER = 4;
    private static final int APPSTATUS_INITED = 5;
    private static final int APPSTATUS_CAMERA_STOPPED = 6;
    private static final int APPSTATUS_CAMERA_RUNNING = 7;

    private static final String LOGTAG = "ImageTargets";

    // Name of the native dynamic libraries to load:
    private static final String NATIVE_LIB_SAMPLE = "ImageTargetsNative";
    private static final String NATIVE_LIB_VUFORIA = "Vuforia";

    // Constants for Hiding/Showing Loading dialog
    private static final int HIDE_LOADING_DIALOG = 0;
    private static final int SHOW_LOADING_DIALOG = 1;

    private View mLoadingDialogContainer;

    // Our OpenGL view:
    private VuforiaSampleGLView mGlView;

    // Our renderer:
    private ImageTargetsRenderer mRenderer;

    // Display size of the device:
    private int mScreenWidth = 0;
    private int mScreenHeight = 0;

    // Constant representing invalid screen orientation to trigger a query:
    private static final int INVALID_SCREEN_ROTATION = -1;

    // Last detected screen rotation:
    private int mLastScreenRotation = INVALID_SCREEN_ROTATION;

    // The current application status:
    private int mAppStatus = APPSTATUS_UNINITED;

    // The async tasks to handle various lifecycle events in the Vuforia SDK:
    private InitVuforiaTask mInitVuforiaTask;
    private LoadTrackerTask mLoadTrackerTask;

    // An object used for synchronizing lifecycle operations performed
    // asynchronously.
    private final Object mLifecycleLock = new Object();

    // Vuforia initialization flags:
    private int mVuforiaFlags = 0;

    // The textures we will use for rendering:
    private Vector<Texture> mTextures;

    // Detects the double tap gesture for launching the Camera menu
    private GestureDetector mGestureDetector;

//    private SampleAppMenu mSampleAppMenu;

    // Contextual Menu Options for Camera Flash - Autofocus
    private boolean mFlash = false;
    private boolean mContAutofocus = true;
    private boolean mDeviceTrackerEnabled = false;

    private View mFocusOptionView;
    private View mFlashOptionView;

    private RelativeLayout mUILayout;

    private boolean mIsDroidDevice = false;

    /* Static initializer block to load native libraries on start-up. */
    static
    {
        loadLibrary(NATIVE_LIB_VUFORIA);
        loadLibrary(NATIVE_LIB_SAMPLE);
    }

    /**
     * Creates a handler to update the status of the Loading Dialog from an UI
     * Thread
     */
    static class LoadingDialogHandler extends Handler
    {
        private final WeakReference<ImageTargets> mImageTargets;


        LoadingDialogHandler(ImageTargets imageTargets)
        {
            mImageTargets = new WeakReference<>(imageTargets);
        }


        public void handleMessage(Message msg)
        {
            ImageTargets imageTargets = mImageTargets.get();
            if (imageTargets == null)
            {
                return;
            }

            if (msg.what == SHOW_LOADING_DIALOG)
            {
                imageTargets.mLoadingDialogContainer
                        .setVisibility(View.VISIBLE);

            } else if (msg.what == HIDE_LOADING_DIALOG)
            {
                imageTargets.mLoadingDialogContainer.setVisibility(View.GONE);
            }
        }
    }

    private final Handler loadingDialogHandler = new LoadingDialogHandler(this);

    /** An async task to initialize Vuforia asynchronously. */
    private static class InitVuforiaTask extends AsyncTask<Void, Integer, Boolean>
    {
        // Initialize with invalid value:
        private int mProgressValue = -1;

        private final WeakReference<ImageTargets> activityRef;

        InitVuforiaTask(ImageTargets activity)
        {
            activityRef = new WeakReference<>(activity);
        }

        protected Boolean doInBackground(Void... params)
        {
            // Prevent the concurrent lifecycle operations:
            synchronized (activityRef.get().mLifecycleLock)
            {
                Vuforia.setInitParameters(activityRef.get(), activityRef.get().mVuforiaFlags, "AT4AmLr/////AAABmSoFE3AXiUgHgUIMv2goaS83mv7wgkZQ32OVxA/7opZKUNpcYiBghFknxXlKfnb9n+g+xZkbPEppbiIV2kMPn3m8e9v6KVn1m8cGDXmq8oqhixFyFBsmCPftMno2rxbMjZTN6vujgUuo/VAC7mOUhOXSe5x6gk8A/gfIH1GpHNlnkSuPXwPmteotSL1r5CxuqoTH5LnK0DTiUYOr9t9x7F5inxskhRPGThOrHGFcdwYnLGO3UONiHD77WPT6Z9E26K6R6lAAc5PZBFPkdbI1dFGoeInacOseY0cCYN8M8F8gBxeqDlPqoRIQ5mZMABAEPrqCvHda8fXXzv247s8yc8Ax/TXPjwAMj06GaKgl/I1y");

                do
                {
                    // Vuforia.init() blocks until an initialization step is
                    // complete, then it proceeds to the next step and reports
                    // progress in percents (0 ... 100%).
                    // If Vuforia.init() returns -1, it indicates an error.
                    // Initialization is done when progress has reached 100%.
                    mProgressValue = Vuforia.init();

                    // Publish the progress value:
                    publishProgress(mProgressValue);

                    // We check whether the task has been canceled in the
                    // meantime (by calling AsyncTask.cancel(true)).
                    // and bail out if it has, thus stopping this thread.
                    // This is necessary as the AsyncTask will run to completion
                    // regardless of the status of the component that
                    // started is.
                } while (!isCancelled() && mProgressValue >= 0
                        && mProgressValue < 100);

                return (mProgressValue > 0);
            }
        }


        protected void onProgressUpdate(Integer... values)
        {
            // Do something with the progress value "values[0]", e.g. update
            // splash screen, progress bar, etc.
        }


        protected void onPostExecute(Boolean result)
        {
            // Done initializing Vuforia, proceed to next application
            // initialization status:
            if (result)
            {
//                Log.d(LOGTAG, "InitVuforiaTask::onPostExecute: Vuforia "
//                        + "initialization successful");

                activityRef.get().updateApplicationStatus(APPSTATUS_INIT_TRACKER);
            } else
            {
                // Create dialog box for display error:
                AlertDialog dialogError = new AlertDialog.Builder(
                        activityRef.get()).create();

                dialogError.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                // Exiting application:
                                System.exit(1);
                            }
                        });

                String logMessage;

                // NOTE: Check if initialization failed because the device is
                // not supported. At this point the user should be informed
                // with a message.
                logMessage = activityRef.get().getInitializationErrorString(mProgressValue);

                // Log error:
//                Log.e(LOGTAG, "InitVuforiaTask::onPostExecute: " + logMessage
//                        + " Exiting.");

                // Show dialog box with error message:
                dialogError.setMessage(logMessage);
                dialogError.show();
            }
        }
    }

    // Returns the error message for each error code
    private String getInitializationErrorString(int code)
    {
        if (code == INIT_ERRORCODE.INIT_DEVICE_NOT_SUPPORTED)
            return getString(R.string.INIT_ERROR_DEVICE_NOT_SUPPORTED);
        if (code == INIT_ERRORCODE.INIT_NO_CAMERA_ACCESS)
            return getString(R.string.INIT_ERROR_NO_CAMERA_ACCESS);
        if (code == INIT_ERRORCODE.INIT_LICENSE_ERROR_MISSING_KEY)
            return getString(R.string.INIT_LICENSE_ERROR_MISSING_KEY);
        if (code == INIT_ERRORCODE.INIT_LICENSE_ERROR_INVALID_KEY)
            return getString(R.string.INIT_LICENSE_ERROR_INVALID_KEY);
        if (code == INIT_ERRORCODE.INIT_LICENSE_ERROR_NO_NETWORK_TRANSIENT)
            return getString(R.string.INIT_LICENSE_ERROR_NO_NETWORK_TRANSIENT);
        if (code == INIT_ERRORCODE.INIT_LICENSE_ERROR_NO_NETWORK_PERMANENT)
            return getString(R.string.INIT_LICENSE_ERROR_NO_NETWORK_PERMANENT);
        if (code == INIT_ERRORCODE.INIT_LICENSE_ERROR_CANCELED_KEY)
            return getString(R.string.INIT_LICENSE_ERROR_CANCELED_KEY);
        if (code == INIT_ERRORCODE.INIT_LICENSE_ERROR_PRODUCT_TYPE_MISMATCH)
            return getString(R.string.INIT_LICENSE_ERROR_PRODUCT_TYPE_MISMATCH);
        else
        {
            Log.d("something wrong", "ecode " + code);
            return getString(R.string.INIT_LICENSE_ERROR_UNKNOWN_ERROR);
        }
    }


    /** An async task to resume Vuforia asynchronously. */
    private static class ResumeVuforiaTask extends AsyncTask<Void, Void, Void>
    {
        private final WeakReference<ImageTargets> appSessionRef;

        ResumeVuforiaTask(ImageTargets session)
        {
            appSessionRef = new WeakReference<>(session);
        }

        protected Void doInBackground(Void... params)
        {
            // Prevent the concurrent lifecycle operations:
            synchronized (appSessionRef.get().mLifecycleLock)
            {
                Vuforia.onResume();
            }

            return null;
        }

        protected void onPostExecute(Void result)
        {
            Log.d(LOGTAG, "ResumeVuforiaTask::onPostExecute");

            ImageTargets activity = appSessionRef.get();
            // We may start the camera only if the Vuforia SDK has already been
            // initialized
            if (activity.mAppStatus == APPSTATUS_CAMERA_STOPPED)
            {
                activity.updateApplicationStatus(APPSTATUS_CAMERA_RUNNING);
            }

//            // Resume the GL view:
//            if (activity.mGlView != null)
//            {
//                activity.mGlView.setVisibility(View.VISIBLE);
//                activity.mGlView.onResume();
//            }
        }
    }

    /** An async task to start the camera asynchronously. */
    private static class StartCameraTask extends AsyncTask<Void, Void, Boolean>
    {
        private final WeakReference<ImageTargets> appSessionRef;

        StartCameraTask(ImageTargets session)
        {
            appSessionRef = new WeakReference<>(session);
        }

        protected Boolean doInBackground(Void... params)
        {
            ImageTargets activity = appSessionRef.get();

            // Prevent the concurrent lifecycle operations:
            synchronized (activity.mLifecycleLock)
            {
                // Call the native function to start the camera:
                activity.startCamera();
            }

            return true;
        }

        protected void onPostExecute(Boolean result)
        {
            ImageTargets activity = appSessionRef.get();

            if (result)
            {
                Log.d(LOGTAG, "StartCameraTask::onPostExecute: Successfully "
                        + "started the camera");

                activity.mRenderer.updateRenderingPrimitives();

                // Set continuous auto-focus if supported by the device,
                // otherwise default back to regular auto-focus mode.
                // This will be activated by a tap to the screen in this
                // application.

                if (activity.mContAutofocus)
                {
                    boolean couldSetFocusMode = activity.setFocusMode(FOCUS_MODE_CONTINUOUS_AUTO);
                    if (!couldSetFocusMode)
                    {
                        Log.e(LOGTAG, "Unable to enable continuous autofocus");

                        activity.setFocusMode(FOCUS_MODE_NORMAL);

                        // Update toggle state
                        activity.setMenuToggle(activity.mFocusOptionView, false);
                    }
                    else
                    {
                        // Update toggle state
                        activity.setMenuToggle(activity.mFocusOptionView, true);
                    }
                }
                else
                {
                    // Update toggle state
                    activity.setMenuToggle(activity.mFocusOptionView, false);
                }

            }

            // Hides the Loading Dialog
            activity.loadingDialogHandler.sendEmptyMessage(HIDE_LOADING_DIALOG);

            // Sets the layout background to transparent
            activity.mUILayout.setBackgroundColor(Color.TRANSPARENT);
        }
    }


    /** An async task to intialise the tracker asynchronously. */
    private static class InitTrackerTask extends AsyncTask<Void, Void, Boolean>
    {
        private final WeakReference<ImageTargets> appSessionRef;

        InitTrackerTask(ImageTargets session)
        {
            appSessionRef = new WeakReference<>(session);
        }

        // Initialize the ObjectTracker
        protected Boolean doInBackground(Void... params)
        {
            // Prevent the concurrent lifecycle operations:
            synchronized (appSessionRef.get().mLifecycleLock)
            {
                // Load the tracker data set:
                return (appSessionRef.get().initTrackers() > 0);
            }
        }

        protected void onPostExecute(Boolean result)
        {
            if (result)
            {
                // Proceed to next application initialization status:
                appSessionRef.get().updateApplicationStatus(APPSTATUS_INIT_APP_AR);
            }
        }
    }

    /** An async task to load the tracker data asynchronously. */
    private static class LoadTrackerTask extends AsyncTask<Void, Void, Boolean>
    {
        private final WeakReference<ImageTargets> appSessionRef;

        LoadTrackerTask(ImageTargets session)
        {
            appSessionRef = new WeakReference<>(session);
        }

        protected Boolean doInBackground(Void... params)
        {
            // Prevent the concurrent lifecycle operations:
            synchronized (appSessionRef.get().mLifecycleLock)
            {
                // Load the tracker data set:
                return (appSessionRef.get().loadTrackerData() > 0);
            }
        }


        protected void onPostExecute(Boolean result)
        {
            Log.d(LOGTAG, "LoadTrackerTask::onPostExecute: execution "
                    + (result ? "successful" : "failed"));

            if (result)
            {
                // Done loading the tracker, update application status:
                appSessionRef.get().updateApplicationStatus(APPSTATUS_INITED);
            }
            else
            {
                // Create dialog box for display error:
                AlertDialog dialogError = new AlertDialog.Builder(appSessionRef.get()).create();

                dialogError.setButton(DialogInterface.BUTTON_POSITIVE, "Close",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                // Exiting application:
                                System.exit(1);
                            }
                        });

                // Show dialog box with error message:
                dialogError.setMessage("Failed to load tracker data.");
                dialogError.show();
            }
        }
    }


    /** Stores screen dimensions */
    private void storeScreenDimensions()
    {
        // Query display dimensions:
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;
    }


    /**
     * Called when the activity first starts or the user navigates back to an
     * activity.
     */
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(LOGTAG, "onCreate");
        super.onCreate(savedInstanceState);

        // Load any sample specific textures:
        mTextures = new Vector<>();
        loadTextures();

        // Configure Vuforia to use OpenGL ES 2.0
        mVuforiaFlags = INIT_FLAGS.GL_20;

        // Creates the GestureDetector listener for processing double tap
        mGestureDetector = new GestureDetector(this, new GestureListener());

        // Update the application status to start initializing application:
        updateApplicationStatus(APPSTATUS_INIT_APP);

        mIsDroidDevice = android.os.Build.MODEL.toLowerCase().startsWith(
                "droid");

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

//        if (requestCode == 0) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Intent intent = new Intent(MainActivity.this, VuforiaActivity.class);
//                startActivity(intent);
//            } else {
//                Toast.makeText(getApplicationContext(), getString(R.string.error_permission_needed), Toast.LENGTH_LONG).show();
//            }
//        }
    }

    /**
     * We want to load specific textures from the APK, which we will later use
     * for rendering.
     */
    private void loadTextures()
    {
//        mTextures.add(Texture.loadTextureFromApk("TextureTeapotBrass.png",
//                getAssets()));
//        mTextures.add(Texture.loadTextureFromApk("TextureTeapotBlue.png",
//                getAssets()));
//        mTextures.add(Texture.loadTextureFromApk("TextureTeapotRed.png",
//                getAssets()));
//        mTextures
//                .add(Texture.loadTextureFromApk("Buildings.png", getAssets()));
    }


    /** Native tracker initialization and deinitialization. */
    public native int initTrackers();


    public native int initDeviceTracker();


    public native void deinitTrackers();


    public native void deinitDeviceTracker();


    /** Native functions to load and destroy tracking data. */
    public native int loadTrackerData();


    public native void destroyTrackerData();


    /** Native sample initialization. */
    public native void onVuforiaInitializedNative();


    /** Native methods for starting and stopping the camera. */
    private native void startCamera();


    private native void stopCamera();


    /** Native method for starting / stopping off target tracking */
    private native boolean startDeviceTracker();


    private native boolean stopDeviceTracker();


    /** Called when the activity will start interacting with the user. */
    @SuppressLint("SourceLockedOrientationActivity")
    protected void onResume() {
        Log.d(LOGTAG, "onResume");
        super.onResume();

        // This is needed for some Droid devices to force portrait
        if (mIsDroidDevice) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        // Vuforia-specific resume operation asynchronously
        try {
            ResumeVuforiaTask resumeVuforiaTask = new ResumeVuforiaTask(this);
            resumeVuforiaTask.execute();
        }
        catch (Exception e)
        {
            Log.e(LOGTAG, "Resuming Vuforia SDK failed");
        }
    }

    /**
     * Updates projection matrix and viewport after a screen rotation change was
     * detected.
     */
    public void updateRenderView()
    {
        int currentScreenRotation = getWindowManager().getDefaultDisplay()
                .getRotation();
        if (currentScreenRotation != mLastScreenRotation)
        {
            // Set projection matrix if there is already a valid one:
            if (Vuforia.isInitialized()
                    && (mAppStatus == APPSTATUS_CAMERA_RUNNING))
            {
                Log.d(LOGTAG, "updateRenderView");

                // Query display dimensions:
                storeScreenDimensions();

                // Update viewport via renderer:
                mRenderer.updateRendering(mScreenWidth, mScreenHeight);

                // Cache last rotation used for setting projection matrix:
                mLastScreenRotation = currentScreenRotation;
            }
        }
    }


    /** Callback for configuration changes the activity handles itself */
    public void onConfigurationChanged(Configuration config)
    {
        Log.d(LOGTAG, "onConfigurationChanged");
        super.onConfigurationChanged(config);

        storeScreenDimensions();

        // Invalidate screen rotation to trigger query upon next render call:
        mLastScreenRotation = INVALID_SCREEN_ROTATION;
    }


    /** Called when the system is about to start resuming a previous activity. */
    protected void onPause()
    {
        Log.d(LOGTAG, "onPause");
        super.onPause();

        if (mGlView != null)
        {
            mGlView.setVisibility(View.INVISIBLE);
            mGlView.onPause();
        }

        // Turn off the flash
        if (mFlashOptionView != null && mFlash)
        {
            setMenuToggle(mFlashOptionView, false);
        }

        if (mAppStatus == APPSTATUS_CAMERA_RUNNING)
        {
            updateApplicationStatus(APPSTATUS_CAMERA_STOPPED);
        }

        // Vuforia-specific pause operation
        Vuforia.onPause();
    }


    /** Native function to deinitialize the application. */
    private native void deinitApplicationNative();


    /** The final call you receive before your activity is destroyed. */
    protected void onDestroy()
    {
        Log.d(LOGTAG, "onDestroy");
        super.onDestroy();

        // Cancel potentially running tasks
        if (mInitVuforiaTask != null
                && mInitVuforiaTask.getStatus() != InitVuforiaTask.Status.FINISHED)
        {
            mInitVuforiaTask.cancel(true);
            mInitVuforiaTask = null;
        }

        if (mLoadTrackerTask != null
                && mLoadTrackerTask.getStatus() != LoadTrackerTask.Status.FINISHED)
        {
            mLoadTrackerTask.cancel(true);
            mLoadTrackerTask = null;
        }

        // Wait for existing asynchronous operations on Vuforia to complete
        synchronized (mLifecycleLock)
        {

            // Do application deinitialization in native code:
            deinitApplicationNative();

            // Unload texture:
            mTextures.clear();
            mTextures = null;

            // Destroy the tracking data set:
            destroyTrackerData();

            // Deinit the tracker:
            deinitTrackers();

            // Deinitialize Vuforia SDK:
            Vuforia.deinit();
        }

        System.gc();
    }


    /**
     * NOTE: this method is synchronized because of a potential concurrent
     * access by asynchronous tasks.
     */
    private synchronized void updateApplicationStatus(int appStatus)
    {
        // Exit if there is no change in status:
        if (mAppStatus == appStatus)
            return;

        // Store new status value:
        mAppStatus = appStatus;

        // Execute application state-specific actions:
        switch (mAppStatus)
        {
            case APPSTATUS_INIT_APP:
                // Initialize application elements that do not rely on Vuforia
                // initialization:
                initApplication();

                // Proceed to next application initialization status:
                updateApplicationStatus(APPSTATUS_INIT_VUFORIA);
                break;

            case APPSTATUS_INIT_VUFORIA:
                // Initialize Vuforia SDK asynchronously to avoid blocking the
                // main (UI) thread.
                //
                // NOTE: This task instance must be created and invoked on the
                // UI thread and it can be executed only once!
                try
                {
                    mInitVuforiaTask = new InitVuforiaTask(this);
                    mInitVuforiaTask.execute();
                } catch (Exception e)
                {
                    Log.e(LOGTAG, "Initializing Vuforia SDK failed");
                }
                break;

            case APPSTATUS_INIT_TRACKER:
                // Initialize the ObjectTracker:
                try
                {
                    InitTrackerTask initTrackerTask = new InitTrackerTask(this);
                    initTrackerTask.execute();
                } catch (Exception e)
                {
                    Log.e(LOGTAG, "Initializing Vuforia tracker failed");
                }
                break;

            case APPSTATUS_INIT_APP_AR:
                // Initialize Augmented Reality-specific application elements
                // that may rely on the fact that the Vuforia SDK has been
                // already initialized:
                initApplicationAR();

                // Proceed to next application initialization status:
                updateApplicationStatus(APPSTATUS_LOAD_TRACKER);
                break;

            case APPSTATUS_LOAD_TRACKER:
                // Load the tracking data set:
                //
                // NOTE: This task instance must be created and invoked on the
                // UI thread and it can be executed only once!
                try
                {
                    mLoadTrackerTask = new LoadTrackerTask(this);
                    mLoadTrackerTask.execute();
                } catch (Exception e)
                {
                    Log.e(LOGTAG, "Loading tracking data set failed");
                }
                break;

            case APPSTATUS_INITED:
                // Hint to the virtual machine that it would be a good time to
                // run the garbage collector:
                //
                // NOTE: This is only a hint. There is no guarantee that the
                // garbage collector will actually be run.
                System.gc();

                // Native post initialization:
                onVuforiaInitializedNative();

                // Activate the renderer:
                mRenderer.mIsActive = true;

                // Now add the GL surface view. It is important
                // that the OpenGL ES surface view gets added
                // BEFORE the camera is started and video
                // background is configured.
                addContentView(mGlView, new LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

                // Sets the UILayout to be drawn in front of the camera
                mUILayout.bringToFront();

                // Start the camera:
                updateApplicationStatus(APPSTATUS_CAMERA_RUNNING);

                break;

            case APPSTATUS_CAMERA_STOPPED:
                // Call the native function to stop the camera:
                stopCamera();
                break;

            case APPSTATUS_CAMERA_RUNNING:
                // Start the camera asynchronously:
                try
                {
                    StartCameraTask startCameraTask = new StartCameraTask(this);
                    startCameraTask.execute();
                }
                catch (Exception e)
                {
                    Log.e(LOGTAG, "Starting camera failed");
                }
//
//                if( mSampleAppMenu == null)
//                {
//                    mSampleAppMenu = new SampleAppMenu(this, this, "Image Targets",
//                            mGlView, mUILayout, null);
//                    setSampleAppMenuSettings();
//                }
                break;

            default:
                throw new RuntimeException("Invalid application state");
        }
    }


    /** Tells native code whether we are in portait or landscape mode */
    private native void setActivityPortraitMode(boolean isPortrait);


    /** Initialize application GUI elements that are not related to AR. */
    private void initApplication()
    {
//        setActivityPortraitMode(true);

        // Query display dimensions:
        storeScreenDimensions();

        // As long as this window is visible to the user, keep the device's
        // screen turned on and bright:
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    /** Native function to initialize the application. */
    private native void initApplicationNative(int width, int height);


    /** Initializes AR application components. */
    private void initApplicationAR()
    {
        // Do application initialization in native code (e.g. registering
        // callbacks, etc.):
        initApplicationNative(mScreenWidth, mScreenHeight);

        // Create OpenGL ES view:
        int depthSize = 16;
        int stencilSize = 0;
        boolean translucent = Vuforia.requiresAlpha();

        mGlView = new VuforiaSampleGLView(this);
        mGlView.init(translucent, depthSize, stencilSize);

        mRenderer = new ImageTargetsRenderer();
        mRenderer.mActivity = this;
        mGlView.setRenderer(mRenderer);

        mUILayout = (RelativeLayout) View.inflate(getApplicationContext(),
                R.layout.camera_overlay,null);

        mUILayout.setVisibility(View.VISIBLE);
        mUILayout.setBackgroundColor(Color.BLACK);

        // Gets a reference to the loading dialog
        mLoadingDialogContainer = mUILayout
                .findViewById(R.id.loading_indicator);

        // Shows the loading indicator at start
        loadingDialogHandler.sendEmptyMessage(SHOW_LOADING_DIALOG);

        // Adds the inflated layout to the view
        addContentView(mUILayout, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
    }


    /** Tells native code to switch dataset as soon as possible */
    private native void switchDatasetAsap(int datasetId);


    private native boolean autofocus();


    private native boolean setFocusMode(int mode);


    /** Activates the Flash */
    private native boolean activateFlash(boolean flash);


    /** Returns the number of registered textures. */
    @SuppressWarnings("unused")
    public int getTextureCount()
    {
        return mTextures.size();
    }


    /** Returns the texture object at the specified index. */
    @SuppressWarnings("unused")
    public Texture getTexture(int i)
    {
        return mTextures.elementAt(i);
    }


    /** A helper for loading native libraries stored in "libs/armeabi*". */
    @SuppressWarnings("UnusedReturnValue")
    private static boolean loadLibrary(String nLibName)
    {
        try
        {
            System.loadLibrary(nLibName);
            Log.i(LOGTAG, "Native library lib" + nLibName + ".so loaded");
            return true;
        } catch (UnsatisfiedLinkError ulee)
        {
            Log.e(LOGTAG, "The library lib" + nLibName
                    + ".so could not be loaded");
            Log.e(LOGTAG, ulee.getLocalizedMessage());
        } catch (SecurityException se)
        {
            Log.e(LOGTAG, "The library lib" + nLibName
                    + ".so was not allowed to be loaded");
        }

        return false;
    }


    public boolean onTouchEvent(MotionEvent event)
    {
        return mGestureDetector.onTouchEvent(event);
    }

    /**
     * Process Tap event for autofocus
     */
    private class GestureListener extends
            GestureDetector.SimpleOnGestureListener
    {
        // Used to set autofocus one second after a manual focus is triggered
        private final Handler autofocusHandler = new Handler();

        public boolean onDown(MotionEvent e)
        {
            return true;
        }


        public boolean onSingleTapUp(MotionEvent e)
        {

            boolean result = autofocus();

            if (!result)
                Log.e(LOGTAG, "Unable to trigger focus");

            // Generates a Handler to trigger continuous auto-focus
            // after 1 second
            autofocusHandler.postDelayed(new Runnable()
            {
                public void run()
                {
                    if (mContAutofocus)
                    {
                        final boolean autofocusResult = setFocusMode(FOCUS_MODE_CONTINUOUS_AUTO);

                        if (!autofocusResult)
                            Log.e(LOGTAG, "Unable to re-enable continuous auto-focus");
                    }
                }
            }, 1000L);

            return true;
        }

    }

    private final static int CMD_BACK = -1;
    private final static int CMD_DEVICE_TRACKER = 1;
    private final static int CMD_AUTOFOCUS = 2;
    private final static int CMD_FLASH = 3;
    private final static int CMD_DATASET_STONES_AND_CHIPS_DATASET = 4;
    private final static int CMD_DATASET_TARMAC_DATASET = 5;

    private final static int STONES_AND_CHIPS_DATASET_ID = 0;
    private final static int TARMAC_DATASET_ID = 1;


    private void setMenuToggle(View view, boolean value)
    {
        // OnCheckedChangeListener is called upon changing the checked state
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            ((Switch) view).setChecked(value);
        } else
        {
            ((CheckBox) view).setChecked(value);
        }
    }


    private void showToast(String text)
    {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}
