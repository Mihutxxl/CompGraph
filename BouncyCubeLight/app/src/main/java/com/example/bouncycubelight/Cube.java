package com.example.bouncycubelight;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ByteOrder;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

public class Cube {
    private final FloatBuffer mFVertexBuffer;
    private final ByteBuffer mColorBuffer;
    private final ByteBuffer mTFan1;
    private final ByteBuffer mTFan2;
    private FloatBuffer mNormalBuffer;
    float[] red = {1.0f, 0.0f, 0.0f, 1.0f};

    public Cube() {
        float vertices[] = {
                -1.0f, 1.0f, 1.0f,   // 0
                1.0f, 1.0f, 1.0f,    // 1
                1.0f, -1.0f, 1.0f,   // 2
                -1.0f, -1.0f, 1.0f,  // 3
                -1.0f, 1.0f, -1.0f,  // 4
                1.0f, 1.0f, -1.0f,   // 5
                1.0f, -1.0f, -1.0f,  // 6
                -1.0f, -1.0f, -1.0f  // 7
        };

        byte maxColor = (byte) 255;
        byte colors[] = {
                0, 0, 0, maxColor,   // 0
                maxColor, 0, 0, maxColor,   // 1
                maxColor, 0, 0, maxColor,   // 2
                maxColor, 0, 0, maxColor,   // 3
                maxColor, 0, 0, maxColor,   // 4
                maxColor, 0, 0, maxColor,   // 5
                maxColor, 0, 0, maxColor,   // 6
                maxColor, 0, 0, maxColor  // 7
        };

        byte tFan1[] = {
                1, 0, 3,
                1, 3, 2,
                1, 2, 6,
                1, 6, 5,
                1, 5, 4,
                1, 4, 0
        };

        byte tFan2[] = {
                7, 4, 5,
                7, 5, 6,
                7, 6, 2,
                7, 2, 3,
                7, 3, 0,
                7, 0, 4
        };

        float[] normals =
                {
                        -1.0f/(float)Math.sqrt(3), 1.0f/(float)Math.sqrt(3), 1.0f/(float)Math.sqrt(3),
                        1.0f/(float)Math.sqrt(3), 1.0f/(float)Math.sqrt(3), 1.0f/(float)Math.sqrt(3),
                        1.0f/(float)Math.sqrt(3), -1.0f/(float)Math.sqrt(3), 1.0f/(float)Math.sqrt(3),
                        -1.0f/(float)Math.sqrt(3), -1.0f/(float)Math.sqrt(3), 1.0f/(float)Math.sqrt(3),
                        -1.0f/(float)Math.sqrt(3), 1.0f/(float)Math.sqrt(3), -1.0f/(float)Math.sqrt(3),
                        1.0f/(float)Math.sqrt(3), 1.0f/(float)Math.sqrt(3), -1.0f/(float)Math.sqrt(3),
                        1.0f/(float)Math.sqrt(3), -1.0f/(float)Math.sqrt(3), -1.0f/(float)Math.sqrt(3),
                        -1.0f/(float)Math.sqrt(3), -1.0f/(float)Math.sqrt(3), -1.0f/(float)Math.sqrt(3)
                };

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mFVertexBuffer = vbb.asFloatBuffer();
        mFVertexBuffer.put(vertices);
        mFVertexBuffer.position(0);

        mColorBuffer = ByteBuffer.allocateDirect(colors.length);
        mColorBuffer.order(ByteOrder.nativeOrder());
        mColorBuffer.put(colors);
        mColorBuffer.position(0);

        mTFan1 = ByteBuffer.allocateDirect(tFan1.length);
        mTFan1.order(ByteOrder.nativeOrder());
        mTFan1.put(tFan1);
        mTFan1.position(0);

        mTFan2 = ByteBuffer.allocateDirect(tFan2.length);
        mTFan2.order(ByteOrder.nativeOrder());
        mTFan2.put(tFan2);
        mTFan2.position(0);

        ByteBuffer nbb = ByteBuffer.allocateDirect(normals.length * 4);
        nbb.order(ByteOrder.nativeOrder());
        mNormalBuffer = nbb.asFloatBuffer();
        mNormalBuffer.put(normals);
        mNormalBuffer.position(0);
    }

    public void draw(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glEnable(GL10.GL_COLOR_MATERIAL);
        gl.glVertexPointer(3, GL11.GL_FLOAT, 0, mFVertexBuffer);
        gl.glColorPointer(4, GL11.GL_UNSIGNED_BYTE, 0, mColorBuffer);

        gl.glDrawElements(GL10.GL_TRIANGLE_FAN, 6 * 3, GL10.GL_UNSIGNED_BYTE, mTFan1);
        gl.glDrawElements(GL10.GL_TRIANGLE_FAN, 6 * 3, GL10.GL_UNSIGNED_BYTE, mTFan2);

        gl.glNormalPointer(GL10.GL_FLOAT, 0, mNormalBuffer);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, makeFloatBuffer(red));

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }

    protected static FloatBuffer makeFloatBuffer(float[] array)
    {
        ByteBuffer bb = ByteBuffer.allocateDirect(array.length*4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(array);
        fb.position(0);
        return fb;
    }
}
