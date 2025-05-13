// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.gfx.pipeline;

import net.labymod.api.client.gfx.util.ShadeType;
import net.labymod.api.client.gfx.shader.Shader;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.opengl.OpenGLVertexArrayObject;
import net.labymod.api.client.gfx.AttributeMask;
import net.labymod.api.client.gfx.texture.TextureTarget;
import net.labymod.api.client.gfx.target.FramebufferTarget;
import net.labymod.api.client.gfx.pipeline.util.TextureId;
import org.lwjgl.opengl.GL14;
import net.labymod.api.client.gfx.target.stencil.StencilOperation;
import net.labymod.v1_12_2.client.render.lighting.LightmapController;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import javax.inject.Inject;
import net.labymod.core.client.gfx.pipeline.state.optifine.OptiFineShaderStateStorage;
import org.lwjgl.opengl.ARBShaderObjects;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.v1_12_2.generated.client.gfx.pipeline.state.ColorStateStorage;
import net.labymod.v1_12_2.generated.client.gfx.pipeline.state.ColorMaskStateStorage;
import net.labymod.v1_12_2.generated.client.gfx.pipeline.state.TextureStateStorage;
import net.labymod.v1_12_2.generated.client.gfx.pipeline.state.StencilStateStorage;
import net.labymod.v1_12_2.generated.client.gfx.pipeline.state.ClearStateStorage;
import net.labymod.v1_12_2.generated.client.gfx.pipeline.state.ColorLogicStateStorage;
import net.labymod.v1_12_2.generated.client.gfx.pipeline.state.PolygonOffsetStateStorage;
import net.labymod.v1_12_2.generated.client.gfx.pipeline.state.CullStateStorage;
import net.labymod.v1_12_2.generated.client.gfx.pipeline.state.FogStateStorage;
import net.labymod.v1_12_2.generated.client.gfx.pipeline.state.DepthStateStorage;
import net.labymod.v1_12_2.generated.client.gfx.pipeline.state.BlendStateStorage;
import net.labymod.v1_12_2.generated.client.gfx.pipeline.state.ColorMaterialStateStorage;
import net.labymod.v1_12_2.generated.client.gfx.pipeline.state.BooleanStateStorage;
import net.labymod.core.client.gfx.pipeline.state.StateStorage;
import net.labymod.v1_12_2.generated.client.gfx.pipeline.state.AlphaStateStorage;
import net.labymod.api.client.gfx.pipeline.Blaze3DFog;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.core.client.gfx.pipeline.AbstractBlaze3DGlStatePipeline;

@Singleton
@Implements(Blaze3DGlStatePipeline.class)
public final class VersionedBlaze3DGlStatePipeline extends AbstractBlaze3DGlStatePipeline implements Blaze3DGlStatePipeline
{
    private final Blaze3DFog blaze3DFog;
    
    @Inject
    public VersionedBlaze3DGlStatePipeline() {
        this.blaze3DFog = new VersionedBlaze3DFog();
        this.addStateStorage(new AlphaStateStorage(), () -> bus.c);
        this.addStateStorage(new BooleanStateStorage(bus::f, bus::g), () -> bus.d);
        this.addStateStorage(new ColorMaterialStateStorage(), () -> bus.f);
        this.addStateStorage(new BlendStateStorage(), () -> bus.g);
        this.addStateStorage(new DepthStateStorage(), () -> bus.h);
        this.addStateStorage(new FogStateStorage(), () -> bus.i);
        this.addStateStorage(new CullStateStorage(), () -> bus.j);
        this.addStateStorage(new PolygonOffsetStateStorage(), () -> bus.k);
        this.addStateStorage(new ColorLogicStateStorage(), () -> bus.l);
        this.addStateStorage(new ClearStateStorage(), () -> bus.n);
        this.addStateStorage(new StencilStateStorage(), () -> bus.o);
        this.addStateStorage(new BooleanStateStorage(bus::B, bus::C), () -> bus.p);
        final bus.x[] textureState = bus.r;
        for (int index = textureState.length - 1; index >= 0; --index) {
            final int finalIndex = index;
            this.addStateStorage(new TextureStateStorage(index), () -> textureState[finalIndex]);
        }
        this.addStateStorage(new BooleanStateStorage(bus::D, bus::E), () -> bus.t);
        this.addStateStorage(new ColorMaskStateStorage(), () -> bus.u);
        this.addStateStorage(new ColorStateStorage(), () -> bus.v);
        if (OptiFine.isPresent()) {
            this.addStateStorage((StateStorage<Void>)new OptiFineShaderStateStorage(() -> OptiFine.config().getActiveProgramId(), ARBShaderObjects::glUseProgramObjectARB), () -> null);
        }
    }
    
    @Override
    public void enableAlpha() {
        bus.e();
    }
    
    @Override
    public void alphaFunc(final int func, final float ref) {
        this.enableAlpha();
        bus.a(func, ref);
    }
    
    @Override
    public void disableAlpha() {
        bus.d();
    }
    
    @Override
    public void enableColorMaterial() {
        bus.h();
    }
    
    @Override
    public void disableColorMaterial() {
        bus.i();
    }
    
    @Override
    public void enableRescaleNormal() {
        bus.D();
    }
    
    @Override
    public void disableRescaleNormal() {
        bus.E();
    }
    
    @Override
    public void enableLighting() {
        bus.f();
    }
    
    @Override
    public void disableLighting() {
        bus.g();
    }
    
    @Override
    public void enableLightMap() {
        LightmapController.apply(VersionedStackProvider.DEFAULT_STACK);
        bus.f();
    }
    
    @Override
    public void disableLightMap() {
        bus.g();
        LightmapController.clear();
    }
    
    @Override
    public void enableCull() {
        bus.q();
    }
    
    @Override
    public void disableCull() {
        bus.r();
    }
    
    @Override
    public void enableBlend() {
        bus.m();
    }
    
    @Override
    public void disableBlend() {
        bus.l();
    }
    
    @Override
    public void enableDepthTest() {
        bus.k();
    }
    
    @Override
    public void disableDepthTest() {
        bus.j();
    }
    
    @Override
    public void depthFunc(final int func) {
        bus.c(func);
    }
    
    @Override
    public void depthMask(final boolean writeDepth) {
        bus.a(writeDepth);
    }
    
    @Override
    public void colorMask(final boolean red, final boolean green, final boolean blue, final boolean alpha) {
        bus.a(red, green, blue, alpha);
    }
    
    @Override
    public void enablePolygonOffset() {
        bus.s();
    }
    
    @Override
    public void disablePolygonOffset() {
        bus.t();
    }
    
    @Override
    public void polygonOffset(final float factor, final float units) {
        bus.a(factor, units);
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
        bus.b(sourceRGB, destinationRGB);
    }
    
    @Override
    public void blendFuncSeparate(final int sourceRGB, final int destinationRGB, final int sourceAlpha, final int destinationAlpha) {
        bus.a(sourceRGB, destinationRGB, sourceAlpha, destinationAlpha);
    }
    
    @Override
    public void enableTexture() {
        bus.y();
    }
    
    @Override
    public void disableTexture() {
        bus.z();
    }
    
    @Override
    public void bindTexture(final TextureId textureId) {
        final int id = textureId.getId();
        bus.i(id);
    }
    
    @Override
    public void deleteTexture(final int id) {
        bus.h(id);
    }
    
    @Override
    public void setActiveTexture(final int activeTexture) {
        bus.g(activeTexture);
    }
    
    @Override
    public int getActiveTexture() {
        return bus.q + 33984;
    }
    
    @Override
    public void invalidateBuffers() {
    }
    
    @Override
    public int genFramebuffers() {
        return cii.g();
    }
    
    @Override
    public void bindFramebuffer(final FramebufferTarget target, final int id) {
        cii.h(target.getId(), id);
    }
    
    @Override
    public void framebufferTexture2D(final FramebufferTarget target, final int attachment, final TextureTarget texTarget, final int texture, final int level) {
        cii.a(target.getId(), attachment, texTarget.getId(), texture, level);
    }
    
    @Override
    public void deleteFramebuffers(final int id) {
        cii.i(id);
    }
    
    @Override
    public void clearColor(final float red, final float green, final float blue, final float alpha) {
        bus.a(red, green, blue, alpha);
    }
    
    @Override
    public void clear(final AttributeMask... bits) {
        int mask = 0;
        for (final AttributeMask bit : bits) {
            mask |= bit.getId();
        }
        bus.m(mask);
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
        return cii.e();
    }
    
    @Override
    public void deleteBuffers(final int buffer) {
        cii.g(buffer);
    }
    
    @Override
    public int createProgram() {
        return cii.d();
    }
    
    @Override
    public void attachShader(final int program, final int shader) {
        cii.b(program, shader);
    }
    
    @Override
    public void deleteShader(final int shader) {
        cii.a(shader);
    }
    
    @Override
    public int createShader(final Shader.Type type) {
        return cii.b(type.getId());
    }
    
    @Override
    public void useProgram(final int programId) {
        cii.d(programId);
    }
    
    @Override
    public void linkProgram(final int programId) {
        cii.f(programId);
    }
    
    @Override
    public void deleteProgram(final int programId) {
        cii.e(programId);
    }
    
    @Override
    public void viewport(final int x, final int y, final int width, final int height) {
        bus.b(x, y, width, height);
    }
    
    @Override
    public void color4f(final float red, final float green, final float blue, final float alpha) {
        bus.c(red, green, blue, alpha);
    }
    
    @Override
    public void setLineWidth(final float width) {
        bus.d(width);
    }
    
    @Override
    public Blaze3DFog blaze3DFog() {
        return this.blaze3DFog;
    }
    
    @Override
    public void shadeModel(final ShadeType type) {
        switch (type) {
            case SMOOTH: {
                bus.j(7425);
                break;
            }
            case FLAT: {
                bus.j(7424);
                break;
            }
        }
    }
}
