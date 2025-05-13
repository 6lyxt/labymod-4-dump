// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.matrix;

import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.Laby;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Contract;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.math.vector.FloatVector4;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.math.vector.Matrix4;

public class Stack implements Matrix4
{
    private static final FloatVector3 TRANSFORM_VEC3;
    private static final FloatVector4 TRANSFORM_VEC4;
    private static final Logging LOGGER;
    protected final StackProvider provider;
    protected Object multiBufferSource;
    private final EnvironmentData environmentData;
    
    protected Stack(final StackProvider provider) {
        this(provider, null);
    }
    
    protected Stack(final StackProvider provider, final Object multiBufferSource) {
        this.provider = provider;
        this.multiBufferSource = multiBufferSource;
        this.environmentData = new EnvironmentData();
    }
    
    public static Stack getDefaultEmptyStack() {
        return EmptyStackHolder.EMPTY_STACK.create();
    }
    
    @Contract("_ -> new")
    @NotNull
    @ApiStatus.Internal
    public static Stack create(final StackProvider provider) {
        return new Stack(provider, null);
    }
    
    @NotNull
    public static Stack create(final Object poseStack) {
        return Laby.references().stackProviderFactory().create(poseStack);
    }
    
    @NotNull
    public static Stack create(final Object poseStack, final Object multiBufferSource) {
        return Laby.references().stackProviderFactory().create(poseStack, multiBufferSource);
    }
    
    public StackProvider getProvider() {
        return this.provider;
    }
    
    public Stack multiBufferSource(final Object source) {
        this.multiBufferSource = source;
        return this;
    }
    
    public Object getMultiBufferSource() {
        return this.multiBufferSource;
    }
    
    @Deprecated
    public EnvironmentData getEnvironmentData() {
        return this.environmentData;
    }
    
    public void push() {
        this.provider.push();
    }
    
    public void pop() {
        this.provider.pop();
    }
    
    @ApiStatus.Experimental
    public void pushAndPop(final Runnable runnable) {
        this.push();
        try {
            runnable.run();
        }
        catch (final Throwable throwable) {
            Stack.LOGGER.error("The last entry in the stack could not be popped normally. Please report this error to \"https://www.labymod.net/ideas\".", throwable);
        }
        this.pop();
    }
    
    public void translate(final double x, final double y, final double z) {
        this.translate((float)x, (float)y, (float)z);
    }
    
    @Override
    public void translate(final float x, final float y, final float z) {
        this.provider.translate(x, y, z);
    }
    
    @Override
    public void rotate(final float angle, final float x, final float y, final float z) {
        this.provider.rotate(angle, x, y, z);
    }
    
    @Override
    public void rotateRadians(final float radians, final float x, final float y, final float z) {
        this.provider.rotateRadians(radians, x, y, z);
    }
    
    public void scale(final float scale) {
        this.scale(scale, scale, scale);
    }
    
    @Override
    public void scale(final float x, final float y, final float z) {
        this.provider.scale(x, y, z);
    }
    
    @Override
    public void multiply(final FloatMatrix4 matrix) {
        this.provider.multiply(matrix);
    }
    
    public void identity() {
        this.provider.identity();
    }
    
    public FloatVector3 transformVector(final float x, final float y, final float z) {
        return this.transformVector(x, y, z, PlatformEnvironment.isAncientOpenGL());
    }
    
    public FloatVector3 transformVector(final float x, final float y, final float z, final boolean ancient) {
        if (ancient) {
            return FloatVector3.ZERO;
        }
        final FloatMatrix4 positionMatrix = this.getProvider().getPosition();
        final float translatedX = positionMatrix.transformVectorX(x, y, z) - x;
        final float translatedY = positionMatrix.transformVectorY(x, y, z) - y;
        final float translatedZ = positionMatrix.transformVectorZ(x, y, z) - z;
        Stack.TRANSFORM_VEC3.set(translatedX, translatedY, translatedZ);
        return Stack.TRANSFORM_VEC3;
    }
    
    public FloatVector4 transformVector(final float left, final float top, final float right, final float bottom) {
        return this.transformVector(left, top, right, bottom, PlatformEnvironment.isAncientOpenGL());
    }
    
    public FloatVector4 transformVector(final Rectangle rectangle) {
        return this.transformVector(rectangle, PlatformEnvironment.isAncientOpenGL());
    }
    
    public FloatVector4 transformVector(final Rectangle rectangle, final boolean ancient) {
        return this.transformVector(rectangle.getLeft(), rectangle.getTop(), rectangle.getRight(), rectangle.getBottom(), ancient);
    }
    
    public FloatVector4 transformVector(final float left, final float top, final float right, final float bottom, final boolean ancient) {
        if (ancient) {
            return FloatVector4.ZERO;
        }
        final FloatMatrix4 matrix = this.getProvider().getPosition();
        final float leftTranslated = matrix.transformVectorX(left, top, 0.0f) - left;
        final float topTranslated = matrix.transformVectorY(left, top, 0.0f) - top;
        final float rightTranslated = matrix.transformVectorX(right, bottom, 0.0f) - right;
        final float bottomTranslated = matrix.transformVectorY(right, bottom, 0.0f) - bottom;
        Stack.TRANSFORM_VEC4.set(leftTranslated, topTranslated, rightTranslated, bottomTranslated);
        return Stack.TRANSFORM_VEC4;
    }
    
    public Stack copy() {
        return new Stack(this.provider, this.multiBufferSource);
    }
    
    static {
        TRANSFORM_VEC3 = new FloatVector3(0.0f, 0.0f, 0.0f);
        TRANSFORM_VEC4 = new FloatVector4(0.0f, 0.0f, 0.0f, 0.0f);
        LOGGER = Logging.create(Stack.class);
    }
    
    @Deprecated
    public static class EnvironmentData
    {
        private boolean shouldUseBufferSource;
        
        public EnvironmentData() {
            this.shouldUseBufferSource = true;
        }
        
        @Deprecated
        public boolean isShouldUseBufferSource() {
            return this.shouldUseBufferSource;
        }
        
        @Deprecated
        public EnvironmentData setShouldUseBufferSource(final boolean shouldUseBufferSource) {
            this.shouldUseBufferSource = shouldUseBufferSource;
            return this;
        }
        
        @Deprecated
        public boolean isUserInterface() {
            return Laby.references().renderEnvironmentContext().isScreenContext();
        }
        
        @Deprecated
        public EnvironmentData setUserInterface(final boolean userInterface) {
            return this;
        }
        
        @Deprecated
        public boolean is3DEnvironment() {
            return !Laby.references().renderEnvironmentContext().isScreenContext();
        }
        
        @Deprecated
        public EnvironmentData set3DEnvironment(final boolean thirdDimensionEnvironment) {
            Laby.references().renderEnvironmentContext().setScreenContext(thirdDimensionEnvironment);
            return this;
        }
        
        @Deprecated
        public int getLight() {
            return Laby.references().renderEnvironmentContext().getPackedLight();
        }
        
        @Deprecated
        public EnvironmentData setLight(final int light) {
            Laby.references().renderEnvironmentContext().setPackedLight(light);
            return this;
        }
        
        @Deprecated
        public EnvironmentData resetLight() {
            return this;
        }
    }
}
