// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.client.gfx.pipeline;

import net.labymod.api.client.gfx.shader.Shader;
import net.labymod.api.client.gfx.AttributeMask;
import net.labymod.api.client.gfx.texture.TextureTarget;
import net.labymod.api.client.gfx.target.FramebufferTarget;
import net.labymod.api.client.gfx.pipeline.util.TextureId;
import net.labymod.api.client.gfx.target.stencil.StencilOperation;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.Laby;
import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.render.matrix.Stack;
import javax.inject.Inject;
import net.labymod.core.client.gfx.pipeline.state.custom.Blaze3DShaderProgramState;
import net.labymod.core.client.gfx.pipeline.state.CustomGlStateManager;
import net.labymod.v1_19_2.generated.client.gfx.pipeline.state.ShaderProgramStateStorage;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.v1_19_2.generated.client.gfx.pipeline.state.TextureStateStorage;
import net.labymod.v1_19_2.generated.client.gfx.pipeline.state.FogStateStorage;
import net.labymod.v1_19_2.generated.client.gfx.pipeline.state.StencilStateStorage;
import net.labymod.v1_19_2.generated.client.gfx.pipeline.state.ColorLogicStateStorage;
import net.labymod.v1_19_2.generated.client.gfx.pipeline.state.PolygonOffsetStateStorage;
import net.labymod.v1_19_2.generated.client.gfx.pipeline.state.ScissorStateStorage;
import net.labymod.v1_19_2.generated.client.gfx.pipeline.state.CullStateStorage;
import net.labymod.v1_19_2.generated.client.gfx.pipeline.state.DepthStateStorage;
import net.labymod.v1_19_2.generated.client.gfx.pipeline.state.ColorMaskStateStorage;
import net.labymod.core.client.gfx.pipeline.state.StateStorage;
import com.mojang.blaze3d.platform.GlStateManager;
import net.labymod.v1_19_2.generated.client.gfx.pipeline.state.BlendStateStorage;
import net.labymod.api.client.gfx.pipeline.Blaze3DFog;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.core.client.gfx.pipeline.AbstractBlaze3DGlStatePipeline;

@Singleton
@Implements(Blaze3DGlStatePipeline.class)
public final class VersionedBlaze3DGlStatePipeline extends AbstractBlaze3DGlStatePipeline implements Blaze3DGlStatePipeline
{
    private static final k DIFFUSE_LIGHT_0;
    private static final k DIFFUSE_LIGHT_1;
    private final Blaze3DFog blaze3DFog;
    
    @Inject
    public VersionedBlaze3DGlStatePipeline() {
        this.blaze3DFog = new VersionedBlaze3DFog();
        this.addStateStorage(new BlendStateStorage(), () -> GlStateManager.BLEND);
        this.addStateStorage(new ColorMaskStateStorage(), () -> GlStateManager.COLOR_MASK);
        this.addStateStorage(new DepthStateStorage(), () -> GlStateManager.DEPTH);
        this.addStateStorage(new CullStateStorage(), () -> GlStateManager.CULL);
        this.addStateStorage(new ScissorStateStorage(), () -> GlStateManager.SCISSOR);
        this.addStateStorage(new PolygonOffsetStateStorage(), () -> GlStateManager.POLY_OFFSET);
        this.addStateStorage(new ColorLogicStateStorage(), () -> GlStateManager.COLOR_LOGIC);
        this.addStateStorage(new StencilStateStorage(), () -> GlStateManager.STENCIL);
        this.addStateStorage((StateStorage<Void>)new FogStateStorage(), () -> null);
        final GlStateManager.l[] textures = GlStateManager.TEXTURES;
        for (int index = textures.length - 1; index >= 0; --index) {
            final int finalIndex = index;
            this.addStateStorage(new TextureStateStorage(index), () -> textures[finalIndex]);
        }
        if (OptiFine.isPresent()) {
            this.addStateStorage(new ShaderProgramStateStorage(), () -> CustomGlStateManager.SHADER_PROGRAM);
        }
    }
    
    @Override
    public Stack getModelViewStack() {
        return ((VanillaStackAccessor)RenderSystem.getModelViewStack()).stack();
    }
    
    @Override
    public void applyModelViewMatrix() {
        RenderSystem.applyModelViewMatrix();
    }
    
    @Override
    public void enableRescaleNormal() {
    }
    
    @Override
    public void disableRescaleNormal() {
    }
    
    @Override
    public void enableLighting() {
    }
    
    @Override
    public void disableLighting() {
    }
    
    @Override
    public void enableLightMap() {
        final GFXBridge gfx = Laby.gfx();
        gfx.setActiveTexture(2);
        efu.I().j.n().c();
        ShaderTextures.setShaderTexture(2, RenderSystem.getShaderTexture(2));
        gfx.setActiveTexture(0);
    }
    
    @Override
    public void disableLightMap() {
        final GFXBridge gfx = Laby.gfx();
        gfx.setActiveTexture(2);
        efu.I().j.n().b();
        gfx.setActiveTexture(0);
    }
    
    @Override
    public void enableCull() {
        GlStateManager._enableCull();
    }
    
    @Override
    public void disableCull() {
        GlStateManager._disableCull();
    }
    
    @Override
    public void enableBlend() {
        GlStateManager._enableBlend();
    }
    
    @Override
    public void disableBlend() {
        GlStateManager._disableBlend();
    }
    
    @Override
    public void enableDepthTest() {
        GlStateManager._enableDepthTest();
    }
    
    @Override
    public void disableDepthTest() {
        GlStateManager._disableDepthTest();
    }
    
    @Override
    public void depthFunc(final int func) {
        GlStateManager._depthFunc(func);
    }
    
    @Override
    public void depthMask(final boolean writeDepth) {
        GlStateManager._depthMask(writeDepth);
    }
    
    @Override
    public void colorMask(final boolean red, final boolean green, final boolean blue, final boolean alpha) {
        GlStateManager._colorMask(red, green, blue, alpha);
    }
    
    @Override
    public void enablePolygonOffset() {
        GlStateManager._enablePolygonOffset();
    }
    
    @Override
    public void disablePolygonOffset() {
        GlStateManager._disablePolygonOffset();
    }
    
    @Override
    public void polygonOffset(final float factor, final float units) {
        GlStateManager._polygonOffset(factor, units);
    }
    
    @Override
    public void stencilFunc(final int func, final int ref, final int mask) {
        GlStateManager._stencilFunc(func, ref, mask);
    }
    
    @Override
    public void stencilMask(final int mask) {
        GlStateManager._stencilMask(mask);
    }
    
    @Override
    public void stencilOp(final StencilOperation sfail, final StencilOperation dpfail, final StencilOperation dppass) {
        GlStateManager._stencilOp(sfail.getId(), dpfail.getId(), dppass.getId());
    }
    
    @Override
    public void enableScissor() {
        GlStateManager._enableScissorTest();
    }
    
    @Override
    public void scissor(final int x, final int y, final int width, final int height) {
        GlStateManager._scissorBox(x, y, width, height);
    }
    
    @Override
    public void disableScissor() {
        GlStateManager._disableScissorTest();
    }
    
    @Override
    public boolean isScissorActive() {
        return GlStateManager.SCISSOR.a.b;
    }
    
    @Override
    public void blendEquation(final int mode) {
        GlStateManager._blendEquation(mode);
    }
    
    @Override
    public void blendFunc(final int sourceRGB, final int destinationRGB) {
        GlStateManager._blendFunc(sourceRGB, destinationRGB);
    }
    
    @Override
    public void blendFuncSeparate(final int sourceRGB, final int destinationRGB, final int sourceAlpha, final int destinationAlpha) {
        GlStateManager._blendFuncSeparate(sourceRGB, destinationRGB, sourceAlpha, destinationAlpha);
    }
    
    @Override
    public void enableTexture() {
        GlStateManager._enableTexture();
    }
    
    @Override
    public void disableTexture() {
        GlStateManager._disableTexture();
    }
    
    @Override
    public void bindTexture(final TextureId textureId) {
        final int id = textureId.getId();
        RenderSystem.setShaderTexture(this.getActiveTexture() - 33984, id);
        GlStateManager._bindTexture(id);
    }
    
    @Override
    public void deleteTexture(final int id) {
        GlStateManager._deleteTexture(id);
    }
    
    @Override
    public void setActiveTexture(final int activeTexture) {
        GlStateManager._activeTexture(activeTexture);
    }
    
    @Override
    public int getActiveTexture() {
        return GlStateManager._getActiveTexture();
    }
    
    @Override
    public void invalidateBuffers() {
        eam.a();
    }
    
    @Override
    public int genFramebuffers() {
        return GlStateManager.glGenFramebuffers();
    }
    
    @Override
    public void bindFramebuffer(final FramebufferTarget target, final int id) {
        GlStateManager._glBindFramebuffer(target.getId(), id);
    }
    
    @Override
    public void framebufferTexture2D(final FramebufferTarget target, final int attachment, final TextureTarget texTarget, final int texture, final int level) {
        GlStateManager._glFramebufferTexture2D(target.getId(), attachment, texTarget.getId(), texture, level);
    }
    
    @Override
    public void deleteFramebuffers(final int id) {
        GlStateManager._glDeleteFramebuffers(id);
    }
    
    @Override
    public void clearColor(final float red, final float green, final float blue, final float alpha) {
        GlStateManager._clearColor(red, green, blue, alpha);
    }
    
    @Override
    public void clear(final AttributeMask... bits) {
        int mask = 0;
        for (final AttributeMask bit : bits) {
            mask |= bit.getId();
        }
        GlStateManager._clear(mask, efu.a);
    }
    
    @Override
    public int genVertexArrays() {
        return GlStateManager._glGenVertexArrays();
    }
    
    @Override
    public void bindVertexArray(final int id) {
        GlStateManager._glBindVertexArray(id);
    }
    
    @Override
    public void deleteVertexArrays(final int id) {
        GlStateManager._glDeleteVertexArrays(id);
    }
    
    @Override
    public int genBuffers() {
        return GlStateManager._glGenBuffers();
    }
    
    @Override
    public void deleteBuffers(final int buffer) {
        GlStateManager._glDeleteBuffers(buffer);
    }
    
    @Override
    public int createProgram() {
        return GlStateManager.glCreateProgram();
    }
    
    @Override
    public void attachShader(final int program, final int shader) {
        GlStateManager.glAttachShader(program, shader);
    }
    
    @Override
    public void deleteShader(final int shader) {
        GlStateManager.glDeleteShader(shader);
    }
    
    @Override
    public int createShader(final Shader.Type type) {
        return GlStateManager.glCreateShader(type.getId());
    }
    
    @Override
    public void useProgram(final int programId) {
        GlStateManager._glUseProgram(programId);
    }
    
    @Override
    public void linkProgram(final int programId) {
        GlStateManager.glLinkProgram(programId);
    }
    
    @Override
    public void deleteProgram(final int programId) {
        GlStateManager.glDeleteProgram(programId);
    }
    
    @Override
    public void viewport(final int x, final int y, final int width, final int height) {
        GlStateManager._viewport(x, y, width, height);
    }
    
    @Override
    public void setLineWidth(final float width) {
        RenderSystem.lineWidth(width);
    }
    
    @Override
    public Blaze3DFog blaze3DFog() {
        return this.blaze3DFog;
    }
    
    @Override
    public void color4f(final float red, final float green, final float blue, final float alpha) {
        RenderSystem.setShaderColor(red, green, blue, alpha);
    }
    
    @Override
    public void setupFlatLighting(final Runnable runnable) {
        final k diffuseLight0 = RenderSystem.shaderLightDirections[0];
        final k diffuseLight2 = RenderSystem.shaderLightDirections[1];
        try {
            RenderSystem.setShaderLights(VersionedBlaze3DGlStatePipeline.DIFFUSE_LIGHT_0, VersionedBlaze3DGlStatePipeline.DIFFUSE_LIGHT_1);
            runnable.run();
        }
        finally {
            RenderSystem.setShaderLights(diffuseLight0, diffuseLight2);
        }
    }
    
    static {
        DIFFUSE_LIGHT_0 = (k)ad.a((Object)new k(0.0f, 0.0f, 0.2f), k::d);
        DIFFUSE_LIGHT_1 = (k)ad.a((Object)new k(0.2f, 0.0f, 0.0f), k::d);
    }
}
