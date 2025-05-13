// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util;

import java.nio.FloatBuffer;
import net.labymod.api.client.gfx.pipeline.util.MatrixTracker;
import org.lwjgl.opengl.GL11;

public final class GLMatrix
{
    private GLMatrix() {
    }
    
    public static void glMatrixMode(final int mode) {
        GL11.glMatrixMode(mode);
        MatrixTracker.beginTracking(mode);
    }
    
    public static void glLoadIdentity() {
        GL11.glLoadIdentity();
        MatrixTracker.loadIdentity();
    }
    
    public static void glOrtho(final double left, final double right, final double bottom, final double top, final double zNear, final double zFar) {
        GL11.glOrtho(left, right, bottom, top, zNear, zFar);
        MatrixTracker.ortho((float)left, (float)right, (float)bottom, (float)top, (float)zNear, (float)zFar);
    }
    
    public static void glMultMatrixf(final FloatBuffer buffer) {
        GL11.glMultMatrixf(buffer);
        MatrixTracker.multiply(buffer);
    }
    
    public static void glPushMatrix() {
        GL11.glPushMatrix();
        MatrixTracker.push();
    }
    
    public static void glPopMatrix() {
        GL11.glPopMatrix();
        MatrixTracker.pop();
    }
    
    public static void glTranslated(final double x, final double y, final double z) {
        GL11.glTranslated(x, y, z);
        MatrixTracker.translate((float)x, (float)y, (float)z);
    }
    
    public static void glTranslatef(final float x, final float y, final float z) {
        GL11.glTranslatef(x, y, z);
        MatrixTracker.translate(x, y, z);
    }
    
    public static void glRotated(final double angle, final double x, final double y, final double z) {
        GL11.glRotated(angle, x, y, z);
        MatrixTracker.rotate((float)angle, (float)x, (float)y, (float)z);
    }
    
    public static void glRotatef(final float angle, final float x, final float y, final float z) {
        GL11.glRotatef(angle, x, y, z);
        MatrixTracker.rotate(angle, x, y, z);
    }
    
    public static void glScaled(final double x, final double y, final double z) {
        GL11.glScaled(x, y, z);
        MatrixTracker.scale((float)x, (float)y, (float)z);
    }
    
    public static void glScalef(final float x, final float y, final float z) {
        GL11.glScalef(x, y, z);
        MatrixTracker.scale(x, y, z);
    }
}
