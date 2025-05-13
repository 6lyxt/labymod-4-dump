// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.gfx.pipeline;

import net.labymod.api.client.gfx.util.ShadeType;
import org.lwjgl.opengl.GL11;
import net.labymod.api.client.gfx.shader.Shader;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.opengl.OpenGLVertexArrayObject;
import net.labymod.api.client.gfx.AttributeMask;
import net.labymod.api.client.gfx.texture.TextureTarget;
import net.labymod.api.client.gfx.target.FramebufferTarget;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.opengl.OpenGLFramebuffer;
import net.labymod.api.client.gfx.pipeline.util.TextureId;
import org.lwjgl.opengl.GL14;
import net.labymod.api.client.gfx.target.stencil.StencilOperation;
import net.labymod.v1_8_9.client.render.lighting.LightmapController;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import javax.inject.Inject;
import net.labymod.core.client.gfx.pipeline.state.optifine.OptiFineShaderStateStorage;
import org.lwjgl.opengl.ARBShaderObjects;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.v1_8_9.generated.client.gfx.pipeline.state.ColorStateStorage;
import net.labymod.v1_8_9.generated.client.gfx.pipeline.state.ColorMaskStateStorage;
import net.labymod.v1_8_9.generated.client.gfx.pipeline.state.TextureStateStorage;
import net.labymod.v1_8_9.generated.client.gfx.pipeline.state.StencilStateStorage;
import net.labymod.v1_8_9.generated.client.gfx.pipeline.state.ClearStateStorage;
import net.labymod.v1_8_9.generated.client.gfx.pipeline.state.ColorLogicStateStorage;
import net.labymod.v1_8_9.generated.client.gfx.pipeline.state.PolygonOffsetStateStorage;
import net.labymod.v1_8_9.generated.client.gfx.pipeline.state.CullStateStorage;
import net.labymod.v1_8_9.generated.client.gfx.pipeline.state.FogStateStorage;
import net.labymod.v1_8_9.generated.client.gfx.pipeline.state.DepthStateStorage;
import net.labymod.v1_8_9.generated.client.gfx.pipeline.state.BlendStateStorage;
import net.labymod.v1_8_9.generated.client.gfx.pipeline.state.ColorMaterialStateStorage;
import net.labymod.v1_8_9.generated.client.gfx.pipeline.state.BooleanStateStorage;
import net.labymod.core.client.gfx.pipeline.state.StateStorage;
import net.labymod.v1_8_9.generated.client.gfx.pipeline.state.AlphaStateStorage;
import net.labymod.api.client.gfx.pipeline.Blaze3DFog;
import net.labymod.core.client.gfx.pipeline.state.custom.Blaze3DShaderProgramState;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.core.client.gfx.pipeline.AbstractBlaze3DGlStatePipeline;

@Singleton
@Implements(Blaze3DGlStatePipeline.class)
public final class VersionedBlaze3DGlStatePipeline extends AbstractBlaze3DGlStatePipeline implements Blaze3DGlStatePipeline
{
    private static final Blaze3DShaderProgramState SHADER_PROGRAM_STATE;
    private final Blaze3DFog blaze3DFog;
    
    @Inject
    public VersionedBlaze3DGlStatePipeline() {
        this.blaze3DFog = new VersionedBlaze3DFog();
        this.addStateStorage(new AlphaStateStorage(), () -> bfl.a);
        this.addStateStorage(new BooleanStateStorage(bfl::e, bfl::f), () -> bfl.b);
        this.addStateStorage(new ColorMaterialStateStorage(), () -> bfl.d);
        this.addStateStorage(new BlendStateStorage(), () -> bfl.e);
        this.addStateStorage(new DepthStateStorage(), () -> bfl.f);
        this.addStateStorage(new FogStateStorage(), () -> bfl.g);
        this.addStateStorage(new CullStateStorage(), () -> bfl.h);
        this.addStateStorage(new PolygonOffsetStateStorage(), () -> bfl.i);
        this.addStateStorage(new ColorLogicStateStorage(), () -> bfl.j);
        this.addStateStorage(new ClearStateStorage(), () -> bfl.l);
        this.addStateStorage(new StencilStateStorage(), () -> bfl.m);
        this.addStateStorage(new BooleanStateStorage(bfl::z, bfl::A), () -> bfl.n);
        final bfl.r[] textureState = bfl.p;
        for (int index = textureState.length - 1; index >= 0; --index) {
            final int finalIndex = index;
            this.addStateStorage(new TextureStateStorage(index), () -> textureState[finalIndex]);
        }
        this.addStateStorage(new BooleanStateStorage(bfl::B, bfl::C), () -> bfl.r);
        this.addStateStorage(new ColorMaskStateStorage(), () -> bfl.s);
        this.addStateStorage(new ColorStateStorage(), () -> bfl.t);
        if (OptiFine.isPresent()) {
            this.addStateStorage((StateStorage<Void>)new OptiFineShaderStateStorage(() -> OptiFine.config().getActiveProgramId(), ARBShaderObjects::glUseProgramObjectARB), () -> null);
        }
    }
    
    @Override
    public void enableAlpha() {
        bfl.d();
    }
    
    @Override
    public void alphaFunc(final int func, final float ref) {
        this.enableAlpha();
        bfl.a(func, ref);
    }
    
    @Override
    public void disableAlpha() {
        bfl.c();
    }
    
    @Override
    public void enableColorMaterial() {
        bfl.g();
    }
    
    @Override
    public void disableColorMaterial() {
        bfl.h();
    }
    
    @Override
    public void enableRescaleNormal() {
        bfl.B();
    }
    
    @Override
    public void disableRescaleNormal() {
        bfl.C();
    }
    
    @Override
    public void enableLighting() {
        bfl.e();
    }
    
    @Override
    public void disableLighting() {
        bfl.f();
    }
    
    @Override
    public void enableLightMap() {
        LightmapController.apply(VersionedStackProvider.DEFAULT_STACK);
        bfl.e();
    }
    
    @Override
    public void disableLightMap() {
        bfl.f();
        LightmapController.clear();
    }
    
    @Override
    public void enableCull() {
        bfl.o();
    }
    
    @Override
    public void disableCull() {
        bfl.p();
    }
    
    @Override
    public void enableBlend() {
        bfl.l();
    }
    
    @Override
    public void disableBlend() {
        bfl.k();
    }
    
    @Override
    public void enableDepthTest() {
        bfl.j();
    }
    
    @Override
    public void disableDepthTest() {
        bfl.i();
    }
    
    @Override
    public void depthFunc(final int func) {
        bfl.c(func);
    }
    
    @Override
    public void depthMask(final boolean writeDepth) {
        bfl.a(writeDepth);
    }
    
    @Override
    public void colorMask(final boolean red, final boolean green, final boolean blue, final boolean alpha) {
        bfl.a(red, green, blue, alpha);
    }
    
    @Override
    public void enablePolygonOffset() {
        bfl.q();
    }
    
    @Override
    public void disablePolygonOffset() {
        bfl.r();
    }
    
    @Override
    public void polygonOffset(final float factor, final float units) {
        bfl.a(factor, units);
    }
    
    @Override
    public void stencilFunc(final int func, final int ref, final int mask) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void stencilMask(final int mask) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void stencilOp(final StencilOperation sfail, final StencilOperation dpfail, final StencilOperation dppass) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void enableScissor() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void scissor(final int x, final int y, final int width, final int height) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void disableScissor() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean isScissorActive() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void blendEquation(final int mode) {
        GL14.glBlendEquation(mode);
    }
    
    @Override
    public void blendFunc(final int sourceRGB, final int destinationRGB) {
        bfl.b(sourceRGB, destinationRGB);
    }
    
    @Override
    public void blendFuncSeparate(final int sourceRGB, final int destinationRGB, final int sourceAlpha, final int destinationAlpha) {
        bfl.a(sourceRGB, destinationRGB, sourceAlpha, destinationAlpha);
    }
    
    @Override
    public void enableTexture() {
        bfl.w();
    }
    
    @Override
    public void disableTexture() {
        bfl.x();
    }
    
    @Override
    public void bindTexture(final TextureId textureId) {
        final int id = textureId.getId();
        bfl.i(id);
    }
    
    @Override
    public void deleteTexture(final int id) {
        bfl.h(id);
    }
    
    @Override
    public void setActiveTexture(final int activeTexture) {
        bfl.g(activeTexture);
    }
    
    @Override
    public int getActiveTexture() {
        return bfl.o + 33984;
    }
    
    @Override
    public void invalidateBuffers() {
    }
    
    @Override
    public int genFramebuffers() {
        return OpenGLFramebuffer.generate(bqs::g);
    }
    
    @Override
    public void bindFramebuffer(final FramebufferTarget target, final int id) {
        OpenGLFramebuffer.bind(target, id, bqs::h);
    }
    
    @Override
    public void framebufferTexture2D(final FramebufferTarget target, final int attachment, final TextureTarget texTarget, final int texture, final int level) {
        OpenGLFramebuffer.texture2D(target, attachment, texTarget, texture, level, bqs::a);
    }
    
    @Override
    public void deleteFramebuffers(final int id) {
        OpenGLFramebuffer.delete(id, bqs::i);
    }
    
    @Override
    public void clearColor(final float red, final float green, final float blue, final float alpha) {
        bfl.a(red, green, blue, alpha);
    }
    
    @Override
    public void clear(final AttributeMask... bits) {
        int mask = 0;
        for (final AttributeMask bit : bits) {
            mask |= bit.getId();
        }
        bfl.m(mask);
    }
    
    @Override
    public int genVertexArrays() {
        return OpenGLVertexArrayObject.generate();
    }
    
    @Override
    public void bindVertexArray(final int id) {
        OpenGLVertexArrayObject.bind(id);
    }
    
    @Override
    public void deleteVertexArrays(final int id) {
        OpenGLVertexArrayObject.delete(id);
    }
    
    @Override
    public int genBuffers() {
        return bqs.e();
    }
    
    @Override
    public void deleteBuffers(final int buffer) {
        bqs.g(buffer);
    }
    
    @Override
    public int createProgram() {
        return bqs.d();
    }
    
    @Override
    public void attachShader(final int program, final int shader) {
        bqs.b(program, shader);
    }
    
    @Override
    public void deleteShader(final int shader) {
        bqs.a(shader);
    }
    
    @Override
    public int createShader(final Shader.Type type) {
        return bqs.b(type.getId());
    }
    
    @Override
    public void useProgram(final int programId) {
        bqs.d(programId);
    }
    
    @Override
    public void linkProgram(final int programId) {
        bqs.f(programId);
    }
    
    @Override
    public void deleteProgram(final int programId) {
        bqs.e(programId);
    }
    
    @Override
    public void viewport(final int x, final int y, final int width, final int height) {
        bfl.b(x, y, width, height);
    }
    
    @Override
    public void color4f(final float red, final float green, final float blue, final float alpha) {
        bfl.c(red, green, blue, alpha);
    }
    
    @Override
    public void setLineWidth(final float width) {
        GL11.glLineWidth(width);
    }
    
    @Override
    public Blaze3DFog blaze3DFog() {
        return this.blaze3DFog;
    }
    
    @Override
    public void shadeModel(final ShadeType type) {
        switch (type) {
            case SMOOTH: {
                bfl.j(7425);
                break;
            }
            case FLAT: {
                bfl.j(7424);
                break;
            }
        }
    }
    
    static {
        SHADER_PROGRAM_STATE = new Blaze3DShaderProgramState();
    }
}
