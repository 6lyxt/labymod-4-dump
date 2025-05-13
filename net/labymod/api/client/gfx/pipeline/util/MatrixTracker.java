// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.util;

import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.client.render.matrix.ArrayStackProvider;
import net.labymod.api.Laby;
import java.nio.FloatBuffer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.util.logging.Logging;

public final class MatrixTracker
{
    private static final Logging LOGGER;
    private static final int GL_MODEL_VIEW = 5888;
    private static final int GL_PROJECTION = 5889;
    private static final int GL_TEXTURE = 5890;
    private static final int GL_MAX_MODEL_VIEW_STACK_DEPTH = 3382;
    private static final int GL_MAX_PROJECTION_STACK_DEPTH = 3384;
    private static final int GL_MAX_TEXTURE_STACK_DEPTH = 3385;
    private static final FloatMatrix4 SHARED_MATRIX;
    public static final Stack MODEL_VIEW_MATRIX;
    public static final Stack PROJECTION_MATRIX;
    public static final Stack TEXTURE_MATRIX;
    private static Stack currentStack;
    
    public static void beginTracking(final int matrixType) {
        MatrixTracker.currentStack = switch (matrixType) {
            case 5888 -> MatrixTracker.MODEL_VIEW_MATRIX;
            case 5889 -> MatrixTracker.PROJECTION_MATRIX;
            case 5890 -> MatrixTracker.TEXTURE_MATRIX;
            default -> throw new IllegalStateException("Unexpected value: " + matrixType);
        };
    }
    
    public static void push() {
        if (MatrixTracker.currentStack == null) {
            return;
        }
        MatrixTracker.currentStack.push();
    }
    
    public static void translate(final float x, final float y, final float z) {
        if (MatrixTracker.currentStack == null) {
            return;
        }
        MatrixTracker.currentStack.translate(x, y, z);
    }
    
    public static void rotate(final float angle, final float x, final float y, final float z) {
        if (MatrixTracker.currentStack == null) {
            return;
        }
        MatrixTracker.currentStack.rotate(angle, x, y, z);
    }
    
    public static void scale(final float x, final float y, final float z) {
        if (MatrixTracker.currentStack == null) {
            return;
        }
        MatrixTracker.currentStack.scale(x, y, z);
    }
    
    public static void loadIdentity() {
        if (MatrixTracker.currentStack == null) {
            return;
        }
        final FloatMatrix4 position = MatrixTracker.currentStack.getProvider().getPosition();
        position.identity();
    }
    
    public static void ortho(final float left, final float right, final float bottom, final float top, final float zNear, final float zFar) {
        if (MatrixTracker.currentStack == null) {
            return;
        }
        final FloatMatrix4 position = MatrixTracker.currentStack.getProvider().getPosition();
        MatrixTracker.SHARED_MATRIX.identity();
        MatrixTracker.SHARED_MATRIX.setOrthographic(left, right, bottom, top, zNear, zFar);
        position.multiply(MatrixTracker.SHARED_MATRIX);
    }
    
    public static void multiply(final FloatBuffer buffer) {
        if (MatrixTracker.currentStack == null) {
            return;
        }
        MatrixTracker.SHARED_MATRIX.identity();
        MatrixTracker.SHARED_MATRIX.load(buffer);
        MatrixTracker.currentStack.multiply(MatrixTracker.SHARED_MATRIX);
    }
    
    public static void pop() {
        if (MatrixTracker.currentStack == null) {
            return;
        }
        MatrixTracker.currentStack.pop();
    }
    
    private static Stack createStack(final String name, final int pname) {
        final int size = Laby.gfx().getInteger(pname);
        MatrixTracker.LOGGER.info("[OpenGL] {}Matrix has a size of {}", name, size);
        return Stack.create(new ArrayStackProvider(size));
    }
    
    static {
        LOGGER = Logging.create(MatrixTracker.class);
        SHARED_MATRIX = FloatMatrix4.newIdentity();
        MODEL_VIEW_MATRIX = createStack("ModelView", 3382);
        PROJECTION_MATRIX = createStack("Projection", 3384);
        TEXTURE_MATRIX = createStack("Texture", 3385);
        MatrixTracker.currentStack = null;
    }
}
