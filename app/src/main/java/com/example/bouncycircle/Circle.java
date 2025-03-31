package com.example.bouncycircle;

import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Circle {
    private FloatBuffer mVertexBuffer;
    private ByteBuffer mColorBuffer;
    private int numSegments = 40; // More segments = smoother circle
    private float[] vertices;
    private byte[] colors;

    public Circle(float radius) {
        vertices = new float[(numSegments + 2) * 2]; // +2 for center vertex
        colors = new byte[(numSegments + 2) * 4];   // RGBA for each vertex

        // Center vertex (x=0, y=0)
        vertices[0] = 0.0f;
        vertices[1] = 0.0f;
        colors[0] = (byte) 255;  // Red
        colors[1] = (byte) 0;    // Green
        colors[2] = (byte) 0;    // Blue
        colors[3] = (byte) 255;  // Alpha

        // Generate circle vertices
        for (int i = 1; i <= numSegments + 1; i++) {
            double angle = (i - 1) * (2.0 * Math.PI / numSegments);
            vertices[i * 2] = (float) Math.cos(angle) * radius;
            vertices[i * 2 + 1] = (float) Math.sin(angle) * radius;

            // Color (gradient effect)
            colors[i * 4] = (byte) 255;   // Red
            colors[i * 4 + 1] = (byte) 0; // Green gradient
            colors[i * 4 + 2] = (byte) 0; // Blue
            colors[i * 4 + 3] = (byte) 255; // Alpha
        }

        // Create Vertex Buffer
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);

        // Create Color Buffer
        mColorBuffer = ByteBuffer.allocateDirect(colors.length);
        mColorBuffer.put(colors);
        mColorBuffer.position(0);
    }

    public void draw(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, mVertexBuffer);
        gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, mColorBuffer);

        // Draw using TRIANGLE FAN to create the filled circle
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, numSegments + 2);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }
}
