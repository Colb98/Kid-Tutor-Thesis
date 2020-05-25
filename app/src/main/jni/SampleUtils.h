/*===============================================================================
Copyright (c) 2020 PTC Inc. All Rights Reserved.

Copyright (c) 2012-2014 Qualcomm Connected Experiences, Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other 
countries.
===============================================================================*/

#ifndef _VUFORIA_SAMPLEUTILS_H_
#define _VUFORIA_SAMPLEUTILS_H_

// Includes:
#include <stdio.h>
#include <android/log.h>

#include <Vuforia/Matrices.h>

// Utility for logging:
#define LOG_TAG    "Vuforia"
#define LOG(...)  __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

/// A utility class used by the Vuforia SDK samples.
class SampleUtils
{
private:
    /// Enable this flag to debug OpenGL errors
    static const bool DEBUG_GL = false;

public:

    /// Prints a 4x4 matrix.
    static void printMatrix(const float* matrix);

    /// Prints GL error information.
    static void checkGlError(const char* operation);

    /// Return an identity 4x4 matrix
    static Vuforia::Matrix44F Matrix44FIdentity();

    /// Sets matrix to identity matrix
    static void setIdentityMatrix(float* matrix);
    
    /// Set the rotation components of this 4x4 matrix.
    static void setRotationMatrix(float angle, float x, float y, float z, 
        float *nMatrix);
    
    /// Set the translation components of this 4x4 matrix.
    static void translatePoseMatrix(float x, float y, float z,
        float* nMatrix = NULL);
    
    /// Applies a rotation.
    static void rotatePoseMatrix(float angle, float x, float y, float z, 
        float* nMatrix = NULL);
    
    /// Applies a scaling transformation.
    static void scalePoseMatrix(float x, float y, float z, 
        float* nMatrix = NULL);
    
    /// Multiplies the two matrices A and B and writes the result to C.
    static void multiplyMatrix(float *matrixA, float *matrixB, 
        float *matrixC);

    /// Returns determinate
    static float getMatrixDeterminate(float* m);

    /// Inverts matrix
    static void inverseMatrix(float* m, float *result);

    /// Transposes matrix.
    static void transposeMatrix(float* m, float* result);

    /// Initialize a shader.
    static unsigned int initShader(unsigned int shaderType, 
        const char* source);
    
    /// Create a shader program.
    static unsigned int createProgramFromBuffer(const char* vertexShaderBuffer,
        const char* fragmentShaderBuffer);
};

#endif // _VUFORIA_SAMPLEUTILS_H_
