/*===============================================================================
Copyright (c) 2020 PTC Inc. All Rights Reserved.

Copyright (c) 2012-2014 Qualcomm Connected Experiences, Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other 
countries.
===============================================================================*/

package com.mus.myapplication;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Texture is a support class for the Vuforia samples applications.
 * 
 * Exposes functionality for loading a texture from the APK.
 * 
 * */

@SuppressWarnings("unused")
public class Texture
{
    private static final String LOGTAG = "Texture";
    private int mWidth;      // / The width of the texture.
    private int mHeight;     // / The height of the texture.
    private int mChannels;   // / The number of channels.
    private byte[] mData;    // / The pixel data.
    
    
    /** Returns the raw data */
    public byte[] getData()
    {
        return mData;
    }
    
    
    /** Factory function to load a texture from the APK. */
    static Texture loadTextureFromApk(String fileName,
                                      AssetManager assets)
    {
        InputStream inputStream;
        try
        {
            inputStream = assets.open(fileName, AssetManager.ACCESS_BUFFER);
            
            BufferedInputStream bufferedStream = new BufferedInputStream(
                inputStream);
            Bitmap bitMap = BitmapFactory.decodeStream(bufferedStream);
            
            int[] data = new int[bitMap.getWidth() * bitMap.getHeight()];
            bitMap.getPixels(data, 0, bitMap.getWidth(), 0, 0,
                bitMap.getWidth(), bitMap.getHeight());
            
            // Convert:
            byte[] dataBytes = new byte[bitMap.getWidth() * bitMap.getHeight()
                * 4];
            for (int p = 0; p < bitMap.getWidth() * bitMap.getHeight(); ++p)
            {
                int colour = data[p];
                dataBytes[p * 4] = (byte) (colour >>> 16);    // R
                dataBytes[p * 4 + 1] = (byte) (colour >>> 8);     // G
                dataBytes[p * 4 + 2] = (byte) colour;            // B
                dataBytes[p * 4 + 3] = (byte) (colour >>> 24);    // A
            }
            
            Texture texture = new Texture();
            texture.mWidth = bitMap.getWidth();
            texture.mHeight = bitMap.getHeight();
            texture.mChannels = 4;
            texture.mData = dataBytes;
            
            return texture;
        } catch (IOException e)
        {
            Log.e(LOGTAG, "Failed to log texture '" + fileName + "' from APK");
            Log.i(LOGTAG, e.getMessage());
            return null;
        }
    }
}
