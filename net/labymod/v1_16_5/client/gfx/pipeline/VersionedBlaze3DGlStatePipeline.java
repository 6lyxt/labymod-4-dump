// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.gfx.pipeline;

import net.labymod.api.util.function.Functional;
import net.labymod.api.client.gfx.util.ShadeType;
import java.nio.FloatBuffer;
import net.labymod.api.client.gfx.shader.Shader;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.opengl.OpenGLVertexArrayObject;
import net.labymod.api.client.gfx.AttributeMask;
import net.labymod.api.client.gfx.texture.TextureTarget;
import net.labymod.api.client.gfx.target.FramebufferTarget;
import net.labymod.api.client.gfx.pipeline.util.TextureId;
import net.labymod.api.client.gfx.target.stencil.StencilOperation;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.Laby;
import org.lwjgl.opengl.GL11;
import net.labymod.v1_16_5.client.render.matrix.OpenGLStackProvider;
import net.labymod.api.client.render.matrix.Stack;
import javax.inject.Inject;
import net.labymod.core.client.gfx.pipeline.state.optifine.OptiFineShaderStateStorage;
import org.lwjgl.opengl.ARBShaderObjects;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.v1_16_5.generated.client.gfx.pipeline.state.TextureStateStorage;
import net.labymod.v1_16_5.generated.client.gfx.pipeline.state.FogStateStorage;
import net.labymod.v1_16_5.generated.client.gfx.pipeline.state.StencilStateStorage;
import net.labymod.v1_16_5.generated.client.gfx.pipeline.state.ColorLogicStateStorage;
import net.labymod.v1_16_5.generated.client.gfx.pipeline.state.PolygonOffsetStateStorage;
import net.labymod.v1_16_5.generated.client.gfx.pipeline.state.ScissorStateStorage;
import net.labymod.v1_16_5.generated.client.gfx.pipeline.state.CullStateStorage;
import net.labymod.v1_16_5.generated.client.gfx.pipeline.state.DepthStateStorage;
import net.labymod.v1_16_5.generated.client.gfx.pipeline.state.ColorMaskStateStorage;
import net.labymod.core.client.gfx.pipeline.state.StateStorage;
import net.labymod.v1_16_5.generated.client.gfx.pipeline.state.BlendStateStorage;
import net.labymod.api.client.gfx.pipeline.Blaze3DFog;
import java.nio.ByteBuffer;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.core.client.gfx.pipeline.AbstractBlaze3DGlStatePipeline;

@Singleton
@Implements(Blaze3DGlStatePipeline.class)
public final class VersionedBlaze3DGlStatePipeline extends AbstractBlaze3DGlStatePipeline implements Blaze3DGlStatePipeline
{
    private ByteBuffer lightBuffer;
    private static final g DIFFUSE_LIGHT_0;
    private static final g DIFFUSE_LIGHT_1;
    private final Blaze3DFog blaze3DFog;
    
    @Inject
    public VersionedBlaze3DGlStatePipeline() {
        this.blaze3DFog = new VersionedBlaze3DFog();
        this.addStateStorage(new BlendStateStorage(), () -> dem.f);
        this.addStateStorage(new ColorMaskStateStorage(), () -> dem.t);
        this.addStateStorage(new DepthStateStorage(), () -> dem.g);
        this.addStateStorage(new CullStateStorage(), () -> dem.i);
        this.addStateStorage(new ScissorStateStorage(), () -> dem.n);
        this.addStateStorage(new PolygonOffsetStateStorage(), () -> dem.j);
        this.addStateStorage(new ColorLogicStateStorage(), () -> dem.k);
        this.addStateStorage(new StencilStateStorage(), () -> dem.m);
        this.addStateStorage(new FogStateStorage(), () -> dem.h);
        final dem.x[] textures = dem.q;
        for (int index = textures.length - 1; index >= 0; --index) {
            final int finalIndex = index;
            this.addStateStorage(new TextureStateStorage(index), () -> textures[finalIndex]);
        }
        if (OptiFine.isPresent()) {
            this.addStateStorage((StateStorage<Void>)new OptiFineShaderStateStorage(() -> OptiFine.config().getActiveProgramId(), ARBShaderObjects::glUseProgramObjectARB), () -> null);
        }
    }
    
    @Override
    public Stack getModelViewStack() {
        return OpenGLStackProvider.DEFAULT_STACK;
    }
    
    @Override
    public void enableAlpha() {
        dem.e();
    }
    
    @Override
    public void alphaFunc(final int func, final float ref) {
        this.enableAlpha();
        dem.a(func, ref);
    }
    
    @Override
    public void disableAlpha() {
        dem.d();
    }
    
    @Override
    public void enableRescaleNormal() {
        GL11.glEnable(32826);
    }
    
    @Override
    public void disableRescaleNormal() {
        GL11.glDisable(32826);
    }
    
    @Override
    public void enableLighting() {
        GL11.glEnable(2896);
    }
    
    @Override
    public void disableLighting() {
        GL11.glDisable(2896);
    }
    
    @Override
    public void enableLightMap() {
        final GFXBridge gfx = Laby.gfx();
        gfx.setActiveTexture(2);
        djz.C().h.l().c();
        gfx.setActiveTexture(0);
    }
    
    @Override
    public void disableLightMap() {
        final GFXBridge gfx = Laby.gfx();
        gfx.setActiveTexture(2);
        djz.C().h.l().b();
        gfx.setActiveTexture(0);
    }
    
    @Override
    public void enableCull() {
        dem.C();
    }
    
    @Override
    public void disableCull() {
        dem.D();
    }
    
    @Override
    public void enableBlend() {
        dem.o();
    }
    
    @Override
    public void disableBlend() {
        dem.n();
    }
    
    @Override
    public void enableDepthTest() {
        dem.m();
    }
    
    @Override
    public void disableDepthTest() {
        dem.l();
    }
    
    @Override
    public void depthFunc(final int func) {
        dem.b(func);
    }
    
    @Override
    public void depthMask(final boolean writeDepth) {
        dem.a(writeDepth);
    }
    
    @Override
    public void colorMask(final boolean red, final boolean green, final boolean blue, final boolean alpha) {
        dem.a(red, green, blue, alpha);
    }
    
    @Override
    public void enablePolygonOffset() {
        dem.E();
    }
    
    @Override
    public void disablePolygonOffset() {
        dem.F();
    }
    
    @Override
    public void polygonOffset(final float factor, final float units) {
        dem.a(factor, units);
    }
    
    @Override
    public void stencilFunc(final int func, final int ref, final int mask) {
        dem.d(func, ref, mask);
    }
    
    @Override
    public void stencilMask(final int mask) {
        dem.u(mask);
    }
    
    @Override
    public void stencilOp(final StencilOperation sfail, final StencilOperation dpfail, final StencilOperation dppass) {
        dem.e(sfail.getId(), dpfail.getId(), dppass.getId());
    }
    
    @Override
    public void enableScissor() {
        dem.k();
    }
    
    @Override
    public void scissor(final int x, final int y, final int width, final int height) {
        dem.a(x, y, width, height);
    }
    
    @Override
    public void disableScissor() {
        dem.j();
    }
    
    @Override
    public boolean isScissorActive() {
        return dem.n.a.b;
    }
    
    @Override
    public void blendEquation(final int mode) {
        dem.c(mode);
    }
    
    @Override
    public void blendFunc(final int sourceRGB, final int destinationRGB) {
        dem.b(sourceRGB, destinationRGB);
    }
    
    @Override
    public void blendFuncSeparate(final int sourceRGB, final int destinationRGB, final int sourceAlpha, final int destinationAlpha) {
        dem.b(sourceRGB, destinationRGB, sourceAlpha, destinationAlpha);
    }
    
    @Override
    public void enableTexture() {
        dem.K();
    }
    
    @Override
    public void disableTexture() {
        dem.L();
    }
    
    @Override
    public void bindTexture(final TextureId textureId) {
        final int id = textureId.getId();
        dem.s(id);
    }
    
    @Override
    public void deleteTexture(final int id) {
        dem.r(id);
    }
    
    @Override
    public void setActiveTexture(final int activeTexture) {
        dem.q(activeTexture);
    }
    
    @Override
    public int getActiveTexture() {
        return dem.p + 33984;
    }
    
    @Override
    public void invalidateBuffers() {
    }
    
    @Override
    public int genFramebuffers() {
        return dem.s();
    }
    
    @Override
    public void bindFramebuffer(final FramebufferTarget target, final int id) {
        dem.h(target.getId(), id);
    }
    
    @Override
    public void framebufferTexture2D(final FramebufferTarget target, final int attachment, final TextureTarget texTarget, final int texture, final int level) {
        dem.a(target.getId(), attachment, texTarget.getId(), texture, level);
    }
    
    @Override
    public void deleteFramebuffers(final int id) {
        dem.k(id);
    }
    
    @Override
    public void clearColor(final float red, final float green, final float blue, final float alpha) {
        dem.b(red, green, blue, alpha);
    }
    
    @Override
    public void clear(final AttributeMask... bits) {
        int mask = 0;
        for (final AttributeMask bit : bits) {
            mask |= bit.getId();
        }
        dem.a(mask, djz.a);
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
        return dem.q();
    }
    
    @Override
    public void deleteBuffers(final int buffer) {
        dem.j(buffer);
    }
    
    @Override
    public int createProgram() {
        return dem.p();
    }
    
    @Override
    public void attachShader(final int program, final int shader) {
        dem.d(program, shader);
    }
    
    @Override
    public void deleteShader(final int shader) {
        dem.d(shader);
    }
    
    @Override
    public int createShader(final Shader.Type type) {
        return dem.e(type.getId());
    }
    
    @Override
    public void useProgram(final int programId) {
        dem.g(programId);
    }
    
    @Override
    public void linkProgram(final int programId) {
        dem.i(programId);
    }
    
    @Override
    public void deleteProgram(final int programId) {
        dem.h(programId);
    }
    
    @Override
    public void viewport(final int x, final int y, final int width, final int height) {
        dem.d(x, y, width, height);
    }
    
    @Override
    public void color4f(final float red, final float green, final float blue, final float alpha) {
        dem.d(red, green, blue, alpha);
    }
    
    @Override
    public void setLineWidth(final float width) {
        dem.d(width);
    }
    
    @Override
    public Blaze3DFog blaze3DFog() {
        return this.blaze3DFog;
    }
    
    @Override
    public void setupFlatLighting(final Runnable runnable) {
        final float[] light0PositionBuffer = this.getLightData(16384, 4611);
        final float[] light0DiffuseBuffer = this.getLightData(16384, 4609);
        final float[] light0AmbientBuffer = this.getLightData(16384, 4608);
        final float[] light0SpecularBuffer = this.getLightData(16384, 4610);
        final float[] light1PositionBuffer = this.getLightData(16385, 4611);
        final float[] light1DiffuseBuffer = this.getLightData(16385, 4609);
        final float[] light1AmbientBuffer = this.getLightData(16385, 4608);
        final float[] light1SpecularBuffer = this.getLightData(16385, 4610);
        dem.Q();
        dem.P();
        dem.a(0);
        dem.a(1);
        dem.a(16384, 4611, this.getBuffer(VersionedBlaze3DGlStatePipeline.DIFFUSE_LIGHT_0.a(), VersionedBlaze3DGlStatePipeline.DIFFUSE_LIGHT_0.b(), VersionedBlaze3DGlStatePipeline.DIFFUSE_LIGHT_0.c(), 0.0f));
        dem.a(16384, 4609, this.getBuffer(0.6f, 0.6f, 0.6f, 1.0f));
        dem.a(16384, 4608, this.getBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        dem.a(16384, 4610, this.getBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        dem.a(16385, 4611, this.getBuffer(VersionedBlaze3DGlStatePipeline.DIFFUSE_LIGHT_1.a(), VersionedBlaze3DGlStatePipeline.DIFFUSE_LIGHT_1.b(), VersionedBlaze3DGlStatePipeline.DIFFUSE_LIGHT_1.c(), 0.0f));
        dem.a(16385, 4609, this.getBuffer(0.6f, 0.6f, 0.6f, 1.0f));
        dem.a(16385, 4608, this.getBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        dem.a(16385, 4610, this.getBuffer(0.0f, 0.0f, 0.0f, 1.0f));
        dem.t(7424);
        dem.a(2899, this.getBuffer(0.4f, 0.4f, 0.4f, 1.0f));
        dem.R();
        runnable.run();
        dem.Q();
        dem.P();
        dem.a(0);
        dem.a(1);
        dem.a(16384, 4611, this.getBuffer(light0PositionBuffer[0], light0PositionBuffer[1], light0PositionBuffer[2], light0PositionBuffer[3]));
        dem.a(16384, 4609, this.getBuffer(light0DiffuseBuffer[0], light0DiffuseBuffer[1], light0DiffuseBuffer[2], light0DiffuseBuffer[3]));
        dem.a(16384, 4608, this.getBuffer(light0AmbientBuffer[0], light0AmbientBuffer[1], light0AmbientBuffer[2], light0AmbientBuffer[3]));
        dem.a(16384, 4610, this.getBuffer(light0SpecularBuffer[0], light0SpecularBuffer[1], light0SpecularBuffer[2], light0SpecularBuffer[3]));
        dem.a(16385, 4611, this.getBuffer(light1PositionBuffer[0], light1PositionBuffer[1], light1PositionBuffer[2], light1PositionBuffer[3]));
        dem.a(16385, 4609, this.getBuffer(light1DiffuseBuffer[0], light1DiffuseBuffer[1], light1DiffuseBuffer[2], light1DiffuseBuffer[3]));
        dem.a(16385, 4608, this.getBuffer(light1AmbientBuffer[0], light1AmbientBuffer[1], light1AmbientBuffer[2], light1AmbientBuffer[3]));
        dem.a(16385, 4610, this.getBuffer(light1SpecularBuffer[0], light1SpecularBuffer[1], light1SpecularBuffer[2], light1SpecularBuffer[3]));
        dem.t(7424);
        dem.a(2899, this.getBuffer(0.4f, 0.4f, 0.4f, 1.0f));
        dem.R();
    }
    
    private FloatBuffer getBuffer(final float x, final float y, final float z, final float scale) {
        final ByteBuffer lightBuffer = this.allocateLightBuffer();
        lightBuffer.putFloat(0, x);
        lightBuffer.putFloat(4, y);
        lightBuffer.putFloat(8, z);
        lightBuffer.putFloat(12, scale);
        return lightBuffer.asFloatBuffer();
    }
    
    private float[] getLightData(final int light, final int pname) {
        final float[] buffer = new float[4];
        GL11.glGetLightfv(light, pname, buffer);
        return buffer;
    }
    
    private ByteBuffer allocateLightBuffer() {
        if (this.lightBuffer == null) {
            this.lightBuffer = Laby.gfx().backend().memoryHandler().create(16);
        }
        return this.lightBuffer;
    }
    
    @Override
    public void shadeModel(final ShadeType type) {
        switch (type) {
            case SMOOTH: {
                dem.t(7425);
                break;
            }
            case FLAT: {
                dem.t(7424);
                break;
            }
        }
    }
    
    static {
        DIFFUSE_LIGHT_0 = Functional.of(new g(0.0f, 0.0f, 0.2f), g::d);
        DIFFUSE_LIGHT_1 = Functional.of(new g(0.2f, 0.0f, 0.0f), g::d);
    }
}
