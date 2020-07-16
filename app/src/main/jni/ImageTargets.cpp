/*===============================================================================
Copyright (c) 2020 PTC Inc. All Rights Reserved.

Copyright (c) 2012-2014 Qualcomm Connected Experiences, Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other 
countries.
===============================================================================*/

#include <jni.h>
#include <android/log.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>

#include <GLES2/gl2.h>
#include <GLES2/gl2ext.h>

#include <Vuforia/Vuforia.h>
#include <Vuforia/CameraDevice.h>
#include <Vuforia/Renderer.h>
#include <Vuforia/VideoBackgroundConfig.h>
#include <Vuforia/Trackable.h>
#include <Vuforia/TrackableResult.h>
#include <Vuforia/DeviceTrackableResult.h>
#include <Vuforia/ImageTargetResult.h>
#include <Vuforia/Tool.h>
#include <Vuforia/Tracker.h>
#include <Vuforia/TrackerManager.h>
#include <Vuforia/ObjectTracker.h>
#include <Vuforia/PositionalDeviceTracker.h>
#include <Vuforia/CameraCalibration.h>
#include <Vuforia/UpdateCallback.h>
#include <Vuforia/DataSet.h>
#include <Vuforia/Device.h>
#include <Vuforia/RenderingPrimitives.h>
#include <Vuforia/GLRenderer.h>
#include <Vuforia/StateUpdater.h>
#include <Vuforia/ViewList.h>


#include "SampleUtils.h"
#include "Texture.h"
#include "CubeShaders.h"
#include "Teapot.h"
#include "Buildings.h"
#include "SampleAppRenderer.h"
#include "SampleMath.h"

#ifdef __cplusplus
extern "C"
{
#endif

Vuforia::Matrix44F mInverseProjMatrix;
Vuforia::Matrix44F mModelViewMatrix;

// Textures:
int textureCount                = 0;
Texture** textures              = 0;

unsigned int shaderProgramID    = 0;
GLuint vertexHandle             = 0;
GLuint textureCoordHandle       = 0;
GLint mvpMatrixHandle           = 0;
GLint texSampler2DHandle        = 0;

// Screen dimensions:
int screenWidth                 = 0;
int screenHeight                = 0;


jclass activityClass            = 0;
JNIEnv* environment             = 0;
jobject actObj                  = 0;

// Indicates whether screen is in portrait (true) or landscape (false) mode
bool isActivityInPortraitMode   = false;

// Constants:
static const float kObjectScale          = 0.003f;
static const float kBuildingsObjectScale = 0.012f;

static const float kBuildingsTranslation = -0.06f;

Vuforia::DataSet* dataSetStonesAndChips  = 0;
Vuforia::DataSet* dataSetTarmac          = 0;

SampleAppRenderer* sampleAppRenderer = 0;

bool switchDataSetAsap           = false;
bool isDeviceTrackerActivated = false;

const int STONES_AND_CHIPS_DATASET_ID = 0;
const int TARMAC_DATASET_ID = 1;
int selectedDataset = STONES_AND_CHIPS_DATASET_ID;

// Object to receive update callbacks from Vuforia Engine
class ImageTargets_UpdateCallback : public Vuforia::UpdateCallback
{   
    virtual void Vuforia_onUpdate(Vuforia::State& /*state*/)
    {
        if (switchDataSetAsap)
        {
            switchDataSetAsap = false;

            // Get the object tracker:
            Vuforia::TrackerManager& trackerManager = Vuforia::TrackerManager::getInstance();
            Vuforia::ObjectTracker* objectTracker = static_cast<Vuforia::ObjectTracker*>(
                trackerManager.getTracker(Vuforia::ObjectTracker::getClassType()));
            if (objectTracker == 0 || dataSetStonesAndChips == 0 || dataSetTarmac == 0 ||
                objectTracker->getActiveDataSets().at(0) == 0)
            {
                LOG("Failed to switch data set.");
                return;
            }
            
            switch( selectedDataset )
            {
                default:
                case STONES_AND_CHIPS_DATASET_ID:
                    if (objectTracker->getActiveDataSets().at(0) != dataSetStonesAndChips)
                    {
                        objectTracker->deactivateDataSet(dataSetTarmac);
                        objectTracker->activateDataSet(dataSetStonesAndChips);
                    }
                    break;
                    
                case TARMAC_DATASET_ID:
                    if (objectTracker->getActiveDataSets().at(0) != dataSetTarmac)
                    {
                        objectTracker->deactivateDataSet(dataSetStonesAndChips);
                        objectTracker->activateDataSet(dataSetTarmac);
                    }
                    break;
            }
        }
    }
};

ImageTargets_UpdateCallback updateCallback;


JNIEXPORT void JNICALL
Java_com_vuforia_engine_ImageTargets_ImageTargets_setActivityPortraitMode(JNIEnv *, jobject, jboolean isPortrait)
{
    isActivityInPortraitMode = isPortrait;
}



JNIEXPORT void JNICALL
Java_com_vuforia_engine_ImageTargets_ImageTargets_switchDatasetAsap(JNIEnv *, jobject, jint datasetId)
{
    selectedDataset = datasetId;
    switchDataSetAsap = true;
}


JNIEXPORT int JNICALL
Java_com_vuforia_engine_ImageTargets_ImageTargets_initTrackers(JNIEnv *, jobject)
{
    LOG("Java_com_vuforia_engine_ImageTargets_ImageTargets_initTrackers");

    Vuforia::TrackerManager& trackerManager = Vuforia::TrackerManager::getInstance();

    // Initialize the object tracker:
    Vuforia::Tracker* tracker = trackerManager.initTracker(Vuforia::ObjectTracker::getClassType());
    if (tracker == NULL)
    {
        LOG("Failed to initialize ObjectTracker.");
        return 0;
    }

    // Initialize the device tracker:
    Vuforia::PositionalDeviceTracker* deviceTracker = static_cast<Vuforia::PositionalDeviceTracker*>
        (trackerManager.initTracker(Vuforia::PositionalDeviceTracker::getClassType()));
    if (deviceTracker == NULL)
    {
        LOG("Failed to initialize DeviceTracker.");
        return 0;
    }

    LOG("Successfully initialized trackers.");
    return 1;
}


JNIEXPORT int JNICALL
Java_com_vuforia_engine_ImageTargets_ImageTargets_initDeviceTracker(JNIEnv *, jobject)
{
    LOG("Java_com_vuforia_engine_ImageTargets_ImageTargets_initDeviceTracker");

    Vuforia::TrackerManager& trackerManager = Vuforia::TrackerManager::getInstance();

    // Initialize the device tracker:
    Vuforia::PositionalDeviceTracker* deviceTracker = static_cast<Vuforia::PositionalDeviceTracker*>
                        (trackerManager.getTracker(Vuforia::PositionalDeviceTracker::getClassType()));

    if (deviceTracker == NULL)
    {
        deviceTracker = static_cast<Vuforia::PositionalDeviceTracker*>
            (trackerManager.initTracker(Vuforia::PositionalDeviceTracker::getClassType()));
        if (deviceTracker == NULL)
        {
            LOG("Failed to initialize DeviceTracker.");
            return 0;
        }

        LOG("Successfully initialized trackers.");
    }
    else
    {
        LOG("DeviceTracker already initialized.");
    }

    return 1;
}


JNIEXPORT void JNICALL
 Java_com_vuforia_engine_ImageTargets_ImageTargets_deinitTrackers(JNIEnv *, jobject)
 {
     LOG("Java_com_vuforia_engine_ImageTargets_ImageTargets_deinitTrackers");

     // Deinit the object tracker:
     Vuforia::TrackerManager& trackerManager = Vuforia::TrackerManager::getInstance();
     trackerManager.deinitTracker(Vuforia::ObjectTracker::getClassType());
     trackerManager.deinitTracker(Vuforia::PositionalDeviceTracker::getClassType());
 }


 JNIEXPORT void JNICALL
 Java_com_vuforia_engine_ImageTargets_ImageTargets_deinitDeviceTracker(JNIEnv *, jobject)
 {
     LOG("Java_com_vuforia_engine_ImageTargets_ImageTargets_deinitDeviceTracker");

     // Deinit the object tracker:
     Vuforia::TrackerManager& trackerManager = Vuforia::TrackerManager::getInstance();
     trackerManager.deinitTracker(Vuforia::PositionalDeviceTracker::getClassType());
 }


JNIEXPORT int JNICALL
Java_com_vuforia_engine_ImageTargets_ImageTargets_loadTrackerData(JNIEnv *, jobject)
{
    LOG("Java_com_vuforia_engine_ImageTargets_ImageTargets_loadTrackerData");
    
    // Get the object tracker:
    Vuforia::TrackerManager& trackerManager = Vuforia::TrackerManager::getInstance();
    Vuforia::ObjectTracker* objectTracker = static_cast<Vuforia::ObjectTracker*>(
                    trackerManager.getTracker(Vuforia::ObjectTracker::getClassType()));
    if (objectTracker == NULL)
    {
        LOG("Failed to load tracking data set because the ObjectTracker has not"
            " been initialized.");
        return 0;
    }

    // Create the data sets:
    dataSetStonesAndChips = objectTracker->createDataSet();
    if (dataSetStonesAndChips == 0)
    {
        LOG("Failed to create a new tracking data.");
        return 0;
    }

    dataSetTarmac = objectTracker->createDataSet();
    if (dataSetTarmac == 0)
    {
        LOG("Failed to create a new tracking data.");
        return 0;
    }

    // Load the data sets:
    if (!dataSetStonesAndChips->load("StonesAndChips.xml", Vuforia::STORAGE_APPRESOURCE))
    {
        LOG("Failed to load data set.");
        return 0;
    }

    if (!dataSetTarmac->load("Tarmac.xml", Vuforia::STORAGE_APPRESOURCE))
    {
        LOG("Failed to load data set.");
        return 0;
    }

    // Activate the data set:
    if (!objectTracker->activateDataSet(dataSetStonesAndChips))
    {
        LOG("Failed to activate data set.");
        return 0;
    }

    LOG("Successfully loaded and activated data set.");
    return 1;
}


JNIEXPORT int JNICALL
Java_com_vuforia_engine_ImageTargets_ImageTargets_destroyTrackerData(JNIEnv *, jobject)
{
    LOG("Java_com_vuforia_engine_ImageTargets_ImageTargets_destroyTrackerData");

    // Get the object tracker:
    Vuforia::TrackerManager& trackerManager = Vuforia::TrackerManager::getInstance();
    Vuforia::ObjectTracker* objectTracker = static_cast<Vuforia::ObjectTracker*>(
        trackerManager.getTracker(Vuforia::ObjectTracker::getClassType()));
    if (objectTracker == NULL)
    {
        LOG("Failed to destroy the tracking data set because the ObjectTracker has not"
            " been initialized.");
        return 0;
    }
    
    if (dataSetStonesAndChips != 0)
    {
        if (objectTracker->getActiveDataSets().at(0) == dataSetStonesAndChips &&
            !objectTracker->deactivateDataSet(dataSetStonesAndChips))
        {
            LOG("Failed to destroy the tracking data set StonesAndChips because the data set "
                "could not be deactivated.");
            return 0;
        }

        if (!objectTracker->destroyDataSet(dataSetStonesAndChips))
        {
            LOG("Failed to destroy the tracking data set StonesAndChips.");
            return 0;
        }

        LOG("Successfully destroyed the data set StonesAndChips.");
        dataSetStonesAndChips = 0;
    }

    if (dataSetTarmac != 0)
    {
        if (objectTracker->getActiveDataSets().at(0) == dataSetTarmac &&
            !objectTracker->deactivateDataSet(dataSetTarmac))
        {
            LOG("Failed to destroy the tracking data set Tarmac because the data set "
                "could not be deactivated.");
            return 0;
        }

        if (!objectTracker->destroyDataSet(dataSetTarmac))
        {
            LOG("Failed to destroy the tracking data set Tarmac.");
            return 0;
        }

        LOG("Successfully destroyed the data set Tarmac.");
        dataSetTarmac = 0;
    }

    return 1;
}


JNIEXPORT void JNICALL
Java_com_vuforia_engine_ImageTargets_ImageTargets_onVuforiaInitializedNative(JNIEnv *, jobject)
{
    // Register the update callback where we handle the data set swap:
    Vuforia::registerCallback(&updateCallback);

    // Comment in to enable tracking of up to 2 targets simultaneously and
    // split the work over multiple frames:
    // Vuforia::setHint(Vuforia::HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 2);
}


JNIEXPORT void JNICALL
Java_com_vuforia_engine_ImageTargets_ImageTargetsRenderer_renderFrame(JNIEnv *, jobject)
{
    // Call renderFrame from SampleAppRenderer which will loop through the rendering primitives
    // views and then it will call renderFrameForView per each of the views available,
    // in this case there is only one view since it is not rendering in stereo mode
    sampleAppRenderer->renderFrame();
}


void
renderModel(Vuforia::Matrix44F& projectionMatrix, Vuforia::Matrix44F& viewMatrix, Vuforia::Matrix44F& modelMatrix, int textureIndex)
{
    if(!isDeviceTrackerActivated)
    {
        // Choose the texture based on the target name:

        const Texture* const thisTexture = textures[textureIndex];

        Vuforia::Matrix44F modelViewProjection;
        Vuforia::Matrix44F modelViewMatrix = SampleUtils::Matrix44FIdentity();

        SampleUtils::translatePoseMatrix(0.0f, 0.0f, kObjectScale,
                                         &modelMatrix.data[0]);
        SampleUtils::scalePoseMatrix(kObjectScale, kObjectScale, kObjectScale,
                                     &modelMatrix.data[0]);

        SampleUtils::multiplyMatrix(&viewMatrix.data[0], &modelMatrix.data[0], &modelViewMatrix.data[0]);

        SampleUtils::multiplyMatrix(&projectionMatrix.data[0],
                                    &modelViewMatrix.data[0] ,
                                    &modelViewProjection.data[0]);

        glUseProgram(shaderProgramID);

        glVertexAttribPointer(vertexHandle, 3, GL_FLOAT, GL_FALSE, 0,
                              (const GLvoid*) &teapotVertices[0]);
        glVertexAttribPointer(textureCoordHandle, 2, GL_FLOAT, GL_FALSE, 0,
                              (const GLvoid*) &teapotTexCoords[0]);

        glEnableVertexAttribArray(vertexHandle);
        glEnableVertexAttribArray(textureCoordHandle);

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, thisTexture->mTextureID);
        glUniform1i(texSampler2DHandle, 0 /*GL_TEXTURE0*/);
        glUniformMatrix4fv(mvpMatrixHandle, 1, GL_FALSE, &modelViewProjection.data[0] );
        glDrawElements(GL_TRIANGLES, NUM_TEAPOT_OBJECT_INDEX, GL_UNSIGNED_SHORT,
                       (const GLvoid*) &teapotIndices[0]);

        glDisableVertexAttribArray(vertexHandle);
        glDisableVertexAttribArray(textureCoordHandle);

        SampleUtils::checkGlError("ImageTargets renderFrame");
    }
    else
    {
        const Texture* const thisTexture = textures[3];

        Vuforia::Matrix44F modelViewProjection;
        Vuforia::Matrix44F modelViewMatrix = SampleUtils::Matrix44FIdentity();

        SampleUtils::translatePoseMatrix(0.0f, kBuildingsTranslation, kBuildingsObjectScale,
                                         &modelMatrix.data[0]);
        SampleUtils::rotatePoseMatrix(90.0f, 1.0f, 0.0f, 0.0f,
                                          &modelMatrix.data[0]);
        SampleUtils::scalePoseMatrix(kBuildingsObjectScale, kBuildingsObjectScale, kBuildingsObjectScale,
                                     &modelMatrix.data[0]);

        SampleUtils::multiplyMatrix(&viewMatrix.data[0], &modelMatrix.data[0], &modelViewMatrix.data[0]);

        SampleUtils::multiplyMatrix(&projectionMatrix.data[0],
                                    &modelViewMatrix.data[0],
                                    &modelViewProjection.data[0]);

        glUseProgram(shaderProgramID);

        glVertexAttribPointer(vertexHandle, 3, GL_FLOAT, GL_FALSE, 0,
                              (const GLvoid*) &buildingsVerts[0]);
        glVertexAttribPointer(textureCoordHandle, 2, GL_FLOAT, GL_FALSE, 0,
                              (const GLvoid*) &buildingsTexCoords[0]);

        glEnableVertexAttribArray(vertexHandle);
        glEnableVertexAttribArray(textureCoordHandle);

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, thisTexture->mTextureID);
        glUniform1i(texSampler2DHandle, 0 /*GL_TEXTURE0*/);
        glUniformMatrix4fv(mvpMatrixHandle, 1, GL_FALSE, &modelViewProjection.data[0] );
        glDrawArrays(GL_TRIANGLES, 0, buildingsNumVerts);

        glDisableVertexAttribArray(vertexHandle);
        glDisableVertexAttribArray(textureCoordHandle);

        SampleUtils::checkGlError("ImageTargets renderFrame");
    }

    return;
}

// This method will be called from SampleAppRenderer per each rendering primitives view
void renderFrameForView(const Vuforia::State& state, Vuforia::Matrix44F& projectionMatrix)
{
    // Explicitly render the Video Background
    sampleAppRenderer->renderVideoBackground();
    glEnable(GL_DEPTH_TEST);

    glEnable(GL_CULL_FACE);
    glCullFace(GL_BACK);
    glFrontFace(GL_CCW);   //Back camera

    Vuforia::Matrix44F devicePoseMatrix = SampleUtils::Matrix44FIdentity();
    mModelViewMatrix = SampleUtils::Matrix44FIdentity();

    if (state.getDeviceTrackableResult() != nullptr
        && state.getDeviceTrackableResult()->getStatus() != Vuforia::TrackableResult::NO_POSE)
    {
        mModelViewMatrix = Vuforia::Tool::convertPose2GLMatrix(state.getDeviceTrackableResult()->getPose());
        mInverseProjMatrix = SampleMath::Matrix44FInverse(mModelViewMatrix);

        float devicePose[16];
        float inversePose[16];

        SampleUtils::inverseMatrix(&mModelViewMatrix.data[0], inversePose);
        SampleUtils::transposeMatrix(inversePose, devicePose);

        for (int i = 0; i < 16; i++)
        {
            devicePoseMatrix.data[i] = devicePose[i];
        }
    }

    // Did we find any trackables this frame?
    const auto& trackableResultList = state.getTrackableResults();
    for (const auto& result : trackableResultList)
    {
        const Vuforia::Trackable& trackable = result->getTrackable();

        mModelViewMatrix = Vuforia::Tool::convertPose2GLMatrix(result->getPose());
        mInverseProjMatrix = SampleMath::Matrix44FInverse(mModelViewMatrix);

        int textureIndex;

        if (result->isOfType(Vuforia::ImageTargetResult::getClassType()))
        {
            if (strcmp(trackable.getName(), "chips") == 0)
            {
                textureIndex = 0;
            }
            else if (strcmp(trackable.getName(), "stones") == 0)
            {
                textureIndex = 1;
                
                jmethodID getTextureCountMethodID = environment->GetMethodID(activityClass,
                                                                "testCallFromNative", "()V");

                environment->CallVoidMethod(actObj, getTextureCountMethodID);\
            }
            else
            {
                textureIndex = 2;
            }

            renderModel(projectionMatrix, devicePoseMatrix, mModelViewMatrix, textureIndex);
        }
    }

    glDisable(GL_DEPTH_TEST);
}


void
configureVideoBackground()
{
    // Get the default video mode:
    Vuforia::CameraDevice& cameraDevice = Vuforia::CameraDevice::getInstance();
    Vuforia::VideoMode videoMode = cameraDevice.
                                getVideoMode(Vuforia::CameraDevice::MODE_DEFAULT);


    // Configure the video background
    Vuforia::VideoBackgroundConfig config;
    config.mPosition.data[0] = 0 - screenWidth / 4;
    config.mPosition.data[1] = 0;
    
    if (isActivityInPortraitMode)
    {
        //LOG("configureVideoBackground PORTRAIT");
        config.mSize.data[0] = static_cast<int>(videoMode.mHeight
                                                * (screenHeight / (float)videoMode.mWidth));
        config.mSize.data[1] = screenHeight;

        if(config.mSize.data[0] < screenWidth)
        {
            LOG("Correcting rendering background size to handle missmatch between screen and video aspect ratios.");
            config.mSize.data[0] = screenWidth;
            config.mSize.data[1] = static_cast<int>(screenWidth *
                                                    (videoMode.mWidth / (float)videoMode.mHeight));
        }
    }
    else
    {
        //LOG("configureVideoBackground LANDSCAPE");
        config.mSize.data[0] = screenWidth/2;
        config.mSize.data[1] = static_cast<int>(videoMode.mHeight
                                                * (screenWidth / 2 / (float)videoMode.mWidth));

        // if(config.mSize.data[1] < screenHeight)
        // {
        //     LOG("Correcting rendering background size to handle missmatch between screen and video aspect ratios.");
        //     config.mSize.data[0] = static_cast<int>(screenHeight
        //                                             * (videoMode.mWidth / (float)videoMode.mHeight));
        //     config.mSize.data[1] = screenHeight;
        // }
    }

    LOG("Configure Video Background : Video (%d,%d), Screen (%d,%d), mSize (%d,%d)", videoMode.mWidth, videoMode.mHeight, screenWidth, screenHeight, config.mSize.data[0], config.mSize.data[1]);

    // Set the config:
    Vuforia::Renderer::getInstance().setVideoBackgroundConfig(config);
}



// ----------------------------------------------------------------------------
// Touch projection
// ----------------------------------------------------------------------------

bool
linePlaneIntersection(Vuforia::Vec3F lineStart, Vuforia::Vec3F lineEnd,
                      Vuforia::Vec3F pointOnPlane, Vuforia::Vec3F planeNormal,
                      Vuforia::Vec3F &intersection)
{
    Vuforia::Vec3F lineDir = SampleMath::Vec3FSub(lineEnd, lineStart);
    lineDir = SampleMath::Vec3FNormalize(lineDir);

    Vuforia::Vec3F planeDir = SampleMath::Vec3FSub(pointOnPlane, lineStart);

    float n = SampleMath::Vec3FDot(planeNormal, planeDir);
    float d = SampleMath::Vec3FDot(planeNormal, lineDir);

    if (fabs(d) < 0.00001) {
        // Line is parallel to plane
        return false;
    }

    float dist = n / d;

    Vuforia::Vec3F offset = SampleMath::Vec3FScale(lineDir, dist);
    intersection = SampleMath::Vec3FAdd(lineStart, offset);

    return true;
}


void
projectScreenPointToPlane(Vuforia::Vec2F point, Vuforia::Vec3F planeCenter, Vuforia::Vec3F planeNormal,
                          Vuforia::Vec3F &intersection, Vuforia::Vec3F &lineStart, Vuforia::Vec3F &lineEnd)
{
    // Window Coordinates to Normalized Device Coordinates
    Vuforia::VideoBackgroundConfig config = Vuforia::Renderer::getInstance().getVideoBackgroundConfig();

    float halfScreenWidth = screenWidth / 2.0f;
    float halfScreenHeight = screenHeight / 2.0f;

    float halfViewportWidth = config.mSize.data[0] / 2.0f;
    float halfViewportHeight = config.mSize.data[1] / 2.0f;

    float x = (point.data[0] - halfScreenWidth) / halfViewportWidth;
    float y = (point.data[1] - halfScreenHeight) / halfViewportHeight * -1;

    Vuforia::Vec4F ndcNear(x, y, -1, 1);
    Vuforia::Vec4F ndcFar(x, y, 1, 1);

    // Normalized Device Coordinates to Eye Coordinates
    Vuforia::Vec4F pointOnNearPlane = SampleMath::Vec4FTransform(ndcNear, mInverseProjMatrix);
    Vuforia::Vec4F pointOnFarPlane = SampleMath::Vec4FTransform(ndcFar, mInverseProjMatrix);
    pointOnNearPlane = SampleMath::Vec4FDiv(pointOnNearPlane, pointOnNearPlane.data[3]);
    pointOnFarPlane = SampleMath::Vec4FDiv(pointOnFarPlane, pointOnFarPlane.data[3]);

    // Eye Coordinates to Object Coordinates
    Vuforia::Matrix44F inverseModelViewMatrix = SampleMath::Matrix44FInverse(mModelViewMatrix);

    Vuforia::Vec4F nearWorld = SampleMath::Vec4FTransform(pointOnNearPlane, inverseModelViewMatrix);
    Vuforia::Vec4F farWorld = SampleMath::Vec4FTransform(pointOnFarPlane, inverseModelViewMatrix);

    lineStart = Vuforia::Vec3F(nearWorld.data[0], nearWorld.data[1], nearWorld.data[2]);
    lineEnd = Vuforia::Vec3F(farWorld.data[0], farWorld.data[1], farWorld.data[2]);
    linePlaneIntersection(lineStart, lineEnd, planeCenter, planeNormal, intersection);
}


JNIEXPORT void JNICALL
Java_com_vuforia_engine_ImageTargets_ImageTargets_initApplicationNative(
                            JNIEnv* env, jobject obj, jint width, jint height)
{
    LOG("Java_com_vuforia_engine_ImageTargets_ImageTargets_initApplicationNative");
    
    // Store screen dimensions
    screenWidth = width;
    screenHeight = height;

    sampleAppRenderer = new SampleAppRenderer();

    // Handle to the activity class:
    environment = env;
    activityClass = env->GetObjectClass(obj);
    actObj = obj;

    jmethodID getTextureCountMethodID = env->GetMethodID(activityClass,
                                                    "getTextureCount", "()I");
    if (getTextureCountMethodID == 0)
    {
        LOG("Function getTextureCount() not found.");
        return;
    }

    textureCount = env->CallIntMethod(obj, getTextureCountMethodID);
    if (!textureCount)
    {
        LOG("getTextureCount() returned zero.");
        return;
    }

    textures = new Texture*[textureCount];

    jmethodID getTextureMethodID = env->GetMethodID(activityClass,
        "getTexture", "(I)Lcom/vuforia/engine/ImageTargets/Texture;");

    if (getTextureMethodID == 0)
    {
        LOG("Function getTexture() not found.");
        return;
    }

    // Register the textures
    for (int i = 0; i < textureCount; ++i)
    {

        jobject textureObject = env->CallObjectMethod(obj, getTextureMethodID, i); 
        if (textureObject == NULL)
        {
            LOG("GetTexture() returned zero pointer");
            return;
        }

        textures[i] = Texture::create(env, textureObject);
    }
    LOG("Java_com_vuforia_engine_ImageTargets_ImageTargets_initApplicationNative finished");
}


JNIEXPORT void JNICALL
Java_com_vuforia_engine_ImageTargets_ImageTargets_deinitApplicationNative(
                                                        JNIEnv*, jobject)
{
    LOG("Java_com_vuforia_engine_ImageTargets_ImageTargets_deinitApplicationNative");

    isDeviceTrackerActivated = false;

    // Release texture resources
    if (textures != 0)
    {    
        for (int i = 0; i < textureCount; ++i)
        {
            delete textures[i];
            textures[i] = NULL;
        }
    
        delete[]textures;
        textures = NULL;
        
        textureCount = 0;
    }

    delete sampleAppRenderer;
    sampleAppRenderer = NULL;
}


JNIEXPORT void JNICALL
Java_com_vuforia_engine_ImageTargets_ImageTargets_startCamera(JNIEnv *,
                                                                         jobject, jint camera)
{
    LOG("Java_com_vuforia_engine_ImageTargets_ImageTargets_startCamera");

    // Initialize the camera:
    if (!Vuforia::CameraDevice::getInstance().init())
        return;

    // Select the default camera mode:
    if (!Vuforia::CameraDevice::getInstance().selectVideoMode(
                                Vuforia::CameraDevice::MODE_DEFAULT))
        return;

    // Configure the rendering of the video background
    configureVideoBackground();
    
    // Start the camera:
    if (!Vuforia::CameraDevice::getInstance().start())
        return;

    // Uncomment to enable flash
    //if(Vuforia::CameraDevice::getInstance().setFlashTorchMode(true))
    //    LOG("IMAGE TARGETS : enabled torch");

    // Uncomment to enable infinity focus mode, or any other supported focus mode
    // See CameraDevice.h for supported focus modes
    //if(Vuforia::CameraDevice::getInstance().setFocusMode(Vuforia::CameraDevice::FOCUS_MODE_INFINITY))
    //    LOG("IMAGE TARGETS : enabled infinity focus");

    // Start the trackers:
    Vuforia::TrackerManager& trackerManager = Vuforia::TrackerManager::getInstance();

    Vuforia::Tracker* objectTracker = trackerManager.getTracker(Vuforia::ObjectTracker::getClassType());
    if(objectTracker != 0)
    {
        objectTracker->start();
    }

    // Only start device tracker if enabled
    if (isDeviceTrackerActivated)
    {
        Vuforia::Tracker* deviceTracker = trackerManager.getTracker(Vuforia::PositionalDeviceTracker::getClassType());
        if(deviceTracker != 0)
        {
            deviceTracker->start();
        }
    }
}


JNIEXPORT void JNICALL
Java_com_vuforia_engine_ImageTargets_ImageTargets_stopCamera(JNIEnv *, jobject)
{
    LOG("Java_com_vuforia_engine_ImageTargets_ImageTargets_stopCamera");

    // Stop the trackers:
    Vuforia::TrackerManager& trackerManager = Vuforia::TrackerManager::getInstance();

    Vuforia::Tracker* objectTracker = trackerManager.getTracker(Vuforia::ObjectTracker::getClassType());

    if(objectTracker != 0)
    {
        objectTracker->stop();
    }

    Vuforia::Tracker* deviceTracker = trackerManager.getTracker(Vuforia::PositionalDeviceTracker::getClassType());

    if(deviceTracker != 0 && isDeviceTrackerActivated)
    {
        deviceTracker->stop();
    }
    
    Vuforia::CameraDevice::getInstance().stop();
    Vuforia::CameraDevice::getInstance().deinit();
}


JNIEXPORT void JNICALL
Java_com_vuforia_engine_ImageTargets_ImageTargetsRenderer_updateRenderingPrimitives(JNIEnv *, jobject)
{
    LOG("Java_com_vuforia_engine_ImageTargets_ImageTargetsRenderer_updateRenderingPrimitives");

    sampleAppRenderer->updateRenderingPrimitives();
}

// ----------------------------------------------------------------------------
// Activates Camera Flash
// ----------------------------------------------------------------------------
JNIEXPORT jboolean JNICALL
Java_com_vuforia_engine_ImageTargets_ImageTargets_activateFlash(JNIEnv*, jobject, jboolean flash)
{
    return static_cast<jboolean>(Vuforia::CameraDevice::getInstance().setFlashTorchMode((flash == JNI_TRUE)) ? JNI_TRUE : JNI_FALSE);
}

JNIEXPORT jboolean JNICALL
Java_com_vuforia_engine_ImageTargets_ImageTargets_autofocus(JNIEnv*, jobject)
{
    return static_cast<jboolean>(Vuforia::CameraDevice::getInstance().setFocusMode(Vuforia::CameraDevice::FOCUS_MODE_TRIGGERAUTO) ? JNI_TRUE : JNI_FALSE);
}


JNIEXPORT jboolean JNICALL
Java_com_vuforia_engine_ImageTargets_ImageTargets_setFocusMode(JNIEnv*, jobject, jint mode)
{
    int focusMode;

    switch ((int)mode)
    {
        case 0:
            focusMode = Vuforia::CameraDevice::FOCUS_MODE_NORMAL;
            break;
        
        case 1:
            focusMode = Vuforia::CameraDevice::FOCUS_MODE_CONTINUOUSAUTO;
            break;
            
        case 2:
            focusMode = Vuforia::CameraDevice::FOCUS_MODE_INFINITY;
            break;
            
        case 3:
            focusMode = Vuforia::CameraDevice::FOCUS_MODE_MACRO;
            break;
    
        default:
            return JNI_FALSE;
    }
    
    return static_cast<jboolean>(Vuforia::CameraDevice::getInstance().setFocusMode(focusMode) ? JNI_TRUE : JNI_FALSE);
}


JNIEXPORT jboolean JNICALL
Java_com_vuforia_engine_ImageTargets_ImageTargets_startDeviceTracker(JNIEnv*, jobject)
{
    Vuforia::TrackerManager& trackerManager = Vuforia::TrackerManager::getInstance();
    Vuforia::PositionalDeviceTracker* deviceTracker = static_cast<Vuforia::PositionalDeviceTracker*>(
          trackerManager.getTracker(Vuforia::PositionalDeviceTracker::getClassType()));

    if (deviceTracker == 0)
    {
        return JNI_FALSE;
    }

    if (!deviceTracker->start())
    {
        return JNI_FALSE;
    }

    isDeviceTrackerActivated = true;
    return JNI_TRUE;
}


JNIEXPORT jboolean JNICALL
Java_com_vuforia_engine_ImageTargets_ImageTargets_stopDeviceTracker(JNIEnv*, jobject)
{
    Vuforia::TrackerManager& trackerManager = Vuforia::TrackerManager::getInstance();
    Vuforia::PositionalDeviceTracker* deviceTracker = static_cast<Vuforia::PositionalDeviceTracker*>(
              trackerManager.getTracker(Vuforia::PositionalDeviceTracker::getClassType()));

    if (deviceTracker == 0)
    {
        return JNI_FALSE;
    }

    deviceTracker->stop();
    
    isDeviceTrackerActivated = false;
    return JNI_TRUE;
}


JNIEXPORT void JNICALL
Java_com_vuforia_engine_ImageTargets_ImageTargetsRenderer_initRendering(
                                                    JNIEnv*, jobject)
{
    LOG("Java_com_vuforia_engine_ImageTargets_ImageTargetsRenderer_initRendering");

    // Define clear color
    glClearColor(0.0f, 0.0f, 0.0f, Vuforia::requiresAlpha() ? 0.0f : 1.0f);
    
    // Now generate the OpenGL texture objects and add settings
    for (int i = 0; i < textureCount; ++i)
    {
        glGenTextures(1, &(textures[i]->mTextureID));
        glBindTexture(GL_TEXTURE_2D, textures[i]->mTextureID);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, textures[i]->mWidth,
                textures[i]->mHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE,
                (GLvoid*)  textures[i]->mData);
    }
  
    shaderProgramID     = SampleUtils::createProgramFromBuffer(cubeMeshVertexShader,
                                                            cubeFragmentShader);

    vertexHandle        = static_cast<GLuint>(glGetAttribLocation(shaderProgramID,
                                                                  "vertexPosition"));
    textureCoordHandle  = static_cast<GLuint>(glGetAttribLocation(shaderProgramID,
                                                                  "vertexTexCoord"));
    mvpMatrixHandle     = glGetUniformLocation(shaderProgramID,
                                                "modelViewProjectionMatrix");
    texSampler2DHandle  = glGetUniformLocation(shaderProgramID, 
                                                "texSampler2D");

    sampleAppRenderer->initRendering();
}


JNIEXPORT void JNICALL
Java_com_vuforia_engine_ImageTargets_ImageTargetsRenderer_updateRendering(
                        JNIEnv*, jobject, jint width, jint height)
{
    LOG("Java_com_vuforia_engine_ImageTargets_ImageTargetsRenderer_updateRendering");

    // Update screen dimensions
    screenWidth = width;
    screenHeight = height;

    // Reconfigure the video background
    configureVideoBackground();
}


#ifdef __cplusplus
}
#endif
