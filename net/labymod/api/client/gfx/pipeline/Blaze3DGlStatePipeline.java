// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline;

import net.labymod.api.client.gfx.util.ShadeType;
import net.labymod.api.client.gfx.shader.Shader;
import net.labymod.api.client.gfx.AttributeMask;
import net.labymod.api.client.gfx.texture.TextureTarget;
import net.labymod.api.client.gfx.target.FramebufferTarget;
import net.labymod.api.client.gfx.pipeline.util.TextureId;
import net.labymod.api.client.gfx.target.stencil.StencilOperation;
import net.labymod.api.client.gfx.GFXObject;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.NOPStack;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface Blaze3DGlStatePipeline
{
    public static final NOPStack NOP_STACK = new NOPStack();
    
    Blaze3DShaderUniformPipeline shaderUniformPipeline();
    
    default Stack getModelViewStack() {
        return Blaze3DGlStatePipeline.NOP_STACK;
    }
    
    default void applyModelViewMatrix() {
    }
    
    default void enableAlpha() {
    }
    
    default void defaultAlphaFunc() {
        this.alphaFunc(516, 0.003921569f);
    }
    
    default void alphaFunc(final int func, final float ref) {
    }
    
    default void disableAlpha() {
    }
    
    default void enableColorMaterial() {
    }
    
    default void disableColorMaterial() {
    }
    
    void enableRescaleNormal();
    
    void disableRescaleNormal();
    
    void enableLighting();
    
    void disableLighting();
    
    void enableLightMap();
    
    void disableLightMap();
    
    void enableCull();
    
    void disableCull();
    
    void enableBlend();
    
    void disableBlend();
    
    void enableDepthTest();
    
    void disableDepthTest();
    
    void depthFunc(final int p0);
    
    @Deprecated(forRemoval = true, since = "4.2.41")
    default void depthFunc(final GFXObject func) {
        this.depthFunc(func.getHandle());
    }
    
    void depthMask(final boolean p0);
    
    void colorMask(final boolean p0, final boolean p1, final boolean p2, final boolean p3);
    
    void enablePolygonOffset();
    
    void disablePolygonOffset();
    
    void polygonOffset(final float p0, final float p1);
    
    void stencilFunc(final int p0, final int p1, final int p2);
    
    void stencilMask(final int p0);
    
    void stencilOp(final StencilOperation p0, final StencilOperation p1, final StencilOperation p2);
    
    void enableScissor();
    
    void scissor(final int p0, final int p1, final int p2, final int p3);
    
    void disableScissor();
    
    boolean isScissorActive();
    
    void blendEquation(final int p0);
    
    void blendFunc(final int p0, final int p1);
    
    void blendFuncSeparate(final int p0, final int p1, final int p2, final int p3);
    
    @Deprecated(forRemoval = true, since = "4.2.41")
    default void blendFunc(final GFXObject sourceRGB, final GFXObject destinationRGB) {
        this.blendFunc(sourceRGB.getHandle(), destinationRGB.getHandle());
    }
    
    void enableTexture();
    
    void disableTexture();
    
    void bindTexture(final TextureId p0);
    
    void deleteTexture(final int p0);
    
    void setActiveTexture(final int p0);
    
    int getActiveTexture();
    
    void invalidateBuffers();
    
    void storeStates();
    
    void restoreStates();
    
    int genFramebuffers();
    
    void bindFramebuffer(final FramebufferTarget p0, final int p1);
    
    void framebufferTexture2D(final FramebufferTarget p0, final int p1, final TextureTarget p2, final int p3, final int p4);
    
    void deleteFramebuffers(final int p0);
    
    int getBindingFramebuffer();
    
    void clearColor(final float p0, final float p1, final float p2, final float p3);
    
    void clear(final AttributeMask... p0);
    
    int genVertexArrays();
    
    void bindVertexArray(final int p0);
    
    void deleteVertexArrays(final int p0);
    
    int genBuffers();
    
    void deleteBuffers(final int p0);
    
    int createProgram();
    
    void attachShader(final int p0, final int p1);
    
    void deleteShader(final int p0);
    
    int createShader(final Shader.Type p0);
    
    void useProgram(final int p0);
    
    void linkProgram(final int p0);
    
    void deleteProgram(final int p0);
    
    void viewport(final int p0, final int p1, final int p2, final int p3);
    
    void color4f(final float p0, final float p1, final float p2, final float p3);
    
    void setLineWidth(final float p0);
    
    Blaze3DFog blaze3DFog();
    
    @Deprecated(forRemoval = true, since = "4.2")
    default void setupFlatLighting(final Runnable runnable) {
        runnable.run();
    }
    
    default void shadeModel(final ShadeType type) {
    }
}
