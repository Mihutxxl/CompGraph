package com.example.bouncycube;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView;
import java.lang.Math;

public class CubeRenderer implements GLSurfaceView.Renderer {
    private Cube mCube;
    private float mAngle = 0;
    private float mTransY = 0;

    public CubeRenderer() {
        mCube = new Cube();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        // Apply transformations
        gl.glTranslatef(0.0f, (float) Math.sin(mTransY), -7.0f);
        mTransY += 0.075f;

        gl.glRotatef(mAngle, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(mAngle, 1.0f, 0.0f, 0.0f);
        mAngle += 0.4;

        // Enable vertex and color arrays
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        mCube.draw(gl);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (height == 0) height = 1; // Prevent division by zero
        float aspectRatio = (float) width / height;

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();

        // Correct field of view conversion
        float fieldOfView = (float) Math.toRadians(30.0);
        float zNear = 0.1f;
        float zFar = 1000f;
        float size = zNear * (float) Math.tan(fieldOfView / 2.0f);

        gl.glFrustumf(-size, size, -size / aspectRatio, size / aspectRatio, zNear, zFar);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glDisable(GL10.GL_DITHER);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        gl.glClearColor(0, 0, 0, 1); // Change alpha to 1 for better blending
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);
    }
}
