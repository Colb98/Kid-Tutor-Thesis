/*===============================================================================
Copyright (c) 2020 PTC Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other
countries.
===============================================================================*/

#include "SampleAppRenderer.h"
#include "SampleUtils.h"

SampleAppRenderer::SampleAppRenderer()
{
    // Setup the mutex used to guard updates to RenderingPrimitives
    pthread_mutex_init(&renderingPrimitivesMutex, 0);
}

SampleAppRenderer::~SampleAppRenderer()
{
    pthread_mutex_destroy(&renderingPrimitivesMutex);
}


void SampleAppRenderer::renderVideoBackground()
{
    // Use texture unit 0 for the video background - this will hold the camera frame and we want to reuse for all views
    // So need to use a different texture unit for the augmentation
    int vbVideoTextureUnit = 0;

    // Bind the video bg texture and get the Texture ID from Vuforia
    Vuforia::GLTextureUnit tex;

    // Please note that if you want to use a specific GL texture handle for the video background, you should configure it
    // from the initRendering() method and use the Renderer::setVideoBackgroundTexture( GLTextureData ) method.
    // Please refer to API Guide for more information.
    tex.mTextureUnit = vbVideoTextureUnit;

    if (! Vuforia::Renderer::getInstance().updateVideoBackgroundTexture(&tex))
    {
        LOG("Unable to bind video background texture!!");
        return;
    }

    Vuforia::Matrix44F vbProjectionMatrix = Vuforia::Tool::convert2GLMatrix(
                                                                            renderingPrimitives->getVideoBackgroundProjectionMatrix(Vuforia::VIEW_SINGULAR));

    GLboolean depthTest = GL_FALSE;
    GLboolean cullTest = GL_FALSE;
    GLboolean scissorsTest = GL_FALSE;

    glGetBooleanv(GL_DEPTH_TEST, &depthTest);
    glGetBooleanv(GL_CULL_FACE, &cullTest);
    glGetBooleanv(GL_SCISSOR_TEST, &scissorsTest);

    glDisable(GL_DEPTH_TEST);
    glDisable(GL_CULL_FACE);
    glDisable(GL_SCISSOR_TEST);

    const Vuforia::Mesh& vbMesh = renderingPrimitives->getVideoBackgroundMesh(Vuforia::VIEW_SINGULAR);
    // Load the shader and upload the vertex/texcoord/index data
    glUseProgram(vbShaderProgramID);
    glVertexAttribPointer(static_cast<GLuint>(vbVertexHandle), 3, GL_FLOAT,
                          GL_FALSE, 0, vbMesh.getPositionCoordinates());
    glVertexAttribPointer(static_cast<GLuint>(vbTextureCoordHandle), 2, GL_FLOAT,
                          GL_FALSE, 0, vbMesh.getUVCoordinates());

    glUniform1i(vbTexSampler2DHandle, vbVideoTextureUnit);

    // Render the video background with the custom shader
    // First, we enable the vertex arrays
    glEnableVertexAttribArray(static_cast<GLuint>(vbVertexHandle));
    glEnableVertexAttribArray(static_cast<GLuint>(vbTextureCoordHandle));

    // Pass the projection matrix to OpenGL
    glUniformMatrix4fv(vbMvpMatrixHandle, 1, GL_FALSE, vbProjectionMatrix.data);

    // Then, we issue the render call
    glDrawElements(GL_TRIANGLES, vbMesh.getNumTriangles() * 3, GL_UNSIGNED_SHORT,
                   vbMesh.getTriangles());

    // Finally, we disable the vertex arrays
    glDisableVertexAttribArray(static_cast<GLuint>(vbVertexHandle));
    glDisableVertexAttribArray(static_cast<GLuint>(vbTextureCoordHandle));

    if(depthTest)
        glEnable(GL_DEPTH_TEST);

    if(cullTest)
        glEnable(GL_CULL_FACE);

    if(scissorsTest)
        glEnable(GL_SCISSOR_TEST);

    SampleUtils::checkGlError("Rendering of the video background failed");
}

void SampleAppRenderer::renderFrame()
{
    pthread_mutex_lock(&renderingPrimitivesMutex);

    Vuforia::Renderer& renderer = Vuforia::Renderer::getInstance();
    const Vuforia::State state = Vuforia::TrackerManager::getInstance().getStateUpdater().updateState();
    renderer.begin(state);

    // Clear colour and depth buffers
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    // Set up the viewport
    Vuforia::Vec4I viewport;

    // We're writing directly to the screen, so the viewport is relative to the screen
    viewport = renderingPrimitives->getViewport(Vuforia::VIEW_SINGULAR);

    // Set viewport for current view
    glViewport(viewport.data[0], viewport.data[1], viewport.data[2], viewport.data[3]);

    //set scissor
    glScissor(viewport.data[0], viewport.data[1], viewport.data[2], viewport.data[3]);

    Vuforia::Matrix34F projMatrix = renderingPrimitives->getProjectionMatrix(Vuforia::VIEW_SINGULAR,
            state.getCameraCalibration());

    Vuforia::Matrix44F projectionMatrix = Vuforia::Tool::convertPerspectiveProjection2GLMatrix(
            projMatrix,
            0.01,
            5);

    renderFrameForView(state, projectionMatrix);

    renderer.end();

    pthread_mutex_unlock(&renderingPrimitivesMutex);
}

void SampleAppRenderer::initRendering()
{
    vbShaderProgramID     = SampleUtils::createProgramFromBuffer(cubeMeshVertexShader,
                                                            cubeFragmentShader);

    vbVertexHandle        = glGetAttribLocation(vbShaderProgramID,
                                                "vertexPosition");
    vbTextureCoordHandle  = glGetAttribLocation(vbShaderProgramID,
                                                "vertexTexCoord");
    vbMvpMatrixHandle     = glGetUniformLocation(vbShaderProgramID,
                                                "modelViewProjectionMatrix");
    vbTexSampler2DHandle  = glGetUniformLocation(vbShaderProgramID,
                                                "texSampler2D");
}

void SampleAppRenderer::updateRenderingPrimitives()
{
    LOG("SampleAppRenderer_updateRenderingPrimitives");

    pthread_mutex_lock(&renderingPrimitivesMutex);
    if (renderingPrimitives != NULL)
    {
        delete renderingPrimitives;
        renderingPrimitives = NULL;
    }

    // Cache the rendering primitives whenever there is an orientation change or initial setup
    renderingPrimitives = new Vuforia::RenderingPrimitives(Vuforia::Device::getInstance().getRenderingPrimitives());
    pthread_mutex_unlock(&renderingPrimitivesMutex);
}
