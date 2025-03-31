package com.example.bouncycubelight;

import static com.example.bouncycubelight.Cube.makeFloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView;
import java.lang.Math;

public class CubeRenderer implements GLSurfaceView.Renderer {
    private Cube mCube;
    private float mAngle = 0;
    private float mTransY = 0;
    public final static int SS_SUNLIGHT = GL10.GL_LIGHT0;

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
        gl.glDepthMask(false);
        initLighting(gl);
        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(SS_SUNLIGHT);

    }

    public void initLighting(GL10 gl) {
        float[] green = {0.0f, 1.0f, 0.0f, 1.0f};
        float[] red = {1.0f, 0.0f, 0.0f, 1.0f};
        float[] blue = {0.0f, 0.0f, 1.0f, 1.0f};
        float[] yellow = {1.0f, 1.0f, 0.0f, 1.0f};
        float[] position = {10.0f,0.0f,3.0f,1.0f};
        gl.glEnable(GL10.GL_COLOR_MATERIAL);
        float direction[]={1.0f,0.0f,0.0f};
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPOT_DIRECTION, makeFloatBuffer(direction));
        gl.glLightfv(SS_SUNLIGHT, GL10.GL_POSITION, makeFloatBuffer(position));
        gl.glLightfv(SS_SUNLIGHT, GL10.GL_DIFFUSE, makeFloatBuffer(green));
        gl.glLightfv(SS_SUNLIGHT,GL10.GL_SPECULAR, makeFloatBuffer(red));
        gl.glLightfv(SS_SUNLIGHT, GL10.GL_AMBIENT, makeFloatBuffer(blue));
        gl.glLightf(SS_SUNLIGHT, GL10.GL_LINEAR_ATTENUATION, 0.025f);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, makeFloatBuffer(red));
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, makeFloatBuffer(blue));
        //gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_EMISSION, makeFloatBuffer(yellow));
        gl.glMaterialf(GL10.GL_FRONT_AND_BACK,GL10.GL_SHININESS, 1);
        float[] colorVector={0.2f, 0.2f, 0.2f, 1.0f};
        gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, makeFloatBuffer(colorVector));
    }


}
