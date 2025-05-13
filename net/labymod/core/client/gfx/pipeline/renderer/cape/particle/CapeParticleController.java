// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.renderer.cape.particle;

import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.client.gfx.pipeline.buffer.BufferState;
import net.labymod.api.client.gfx.pipeline.program.RenderPrograms;
import net.labymod.api.client.gfx.shader.uniform.Uniform4F;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.shader.uniform.UniformMatrix4;
import net.labymod.api.client.gfx.pipeline.buffer.renderer.RenderedBuffer;
import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;
import net.labymod.api.client.gfx.pipeline.program.RenderParameters;
import net.labymod.api.Textures;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.client.render.matrix.ArrayStackProvider;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameShutdownEvent;
import java.util.Iterator;
import net.labymod.api.client.entity.datawatcher.DataWatcher;
import net.labymod.api.util.math.MathHelper;
import java.util.LinkedList;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.loader.MinecraftVersion;
import net.labymod.api.loader.MinecraftVersions;
import javax.inject.Inject;
import net.labymod.api.Laby;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.gfx.pipeline.program.parameters.TexturingRenderParameter;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.render.matrix.Stack;
import java.util.Random;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public final class CapeParticleController implements Runnable
{
    private static final Random RANDOM;
    private static final Stack PARTICLE_STACK;
    private static final FloatVector3 OFFSET_VECTOR;
    private static final TexturingRenderParameter STAR_RENDER_PARAMETER;
    private static final boolean CUSTOM_OFFSET;
    private static final float PARTICLE_MIN_X = -0.28125f;
    private static final float PARTICLE_MIN_Y = -0.03125f;
    private static final float PARTICLE_MAX_X = 0.8f;
    private static final float PARTICLE_MAX_Y = 0.8f;
    private static final float PARTICLE_AREA_PIVOT_OFFSET_X = 0.4375f;
    private static final float PARTICLE_AREA_PIVOT_OFFSET_Y = 0.4375f;
    private final CapeParticleRenderedBuffer buffer;
    private final List<CapeParticleStorage> storages;
    
    @Inject
    public CapeParticleController() {
        this.storages = new ArrayList<CapeParticleStorage>();
        this.buffer = new CapeParticleRenderedBuffer();
        Laby.labyAPI().eventBus().registerListener(this);
    }
    
    private static boolean isSupportedVersion() {
        return isSupportedVersion(MinecraftVersions.current());
    }
    
    private static boolean isSupportedVersion(final MinecraftVersion current) {
        return current.isGreaterThan(MinecraftVersions.V1_13) && current.isLowerThan(MinecraftVersions.V1_21_3);
    }
    
    private void updateOffsetVector(final Player player) {
        final float offsetX = 0.0f;
        float offsetZ = -1.0125f;
        float offsetY = -0.85f;
        if (CapeParticleController.CUSTOM_OFFSET) {
            if (!player.getEquipmentItemStack(LivingEntity.EquipmentSpot.CHEST).isAir()) {
                if (player.isCrouching()) {
                    offsetZ = -0.75f;
                    offsetY = -1.85f;
                }
                else {
                    offsetZ = -2.2f;
                    offsetY = -0.4f;
                }
            }
            else if (player.isCrouching()) {
                offsetZ = 0.35f;
                offsetY = -2.7f;
            }
        }
        CapeParticleController.OFFSET_VECTOR.set(offsetX * 0.0625f, offsetY * 0.0625f, offsetZ * 0.0625f);
    }
    
    public void spawn(final FloatMatrix4 modelViewMatrix, final Player player, final float partialTicks) {
        if (!Laby.labyAPI().config().ingame().showCapeParticles().get()) {
            return;
        }
        CapeParticleController.PARTICLE_STACK.push();
        CapeParticleController.PARTICLE_STACK.multiply(modelViewMatrix);
        this.updateOffsetVector(player);
        final float renderTick = player.getRenderTick(partialTicks);
        this.buffer.createRenderedBuffer();
        final DataWatcher dataWatcher = player.dataWatcher();
        final List<CapeParticle> particles = dataWatcher.computeIfAbsent("capeParticles", k -> new LinkedList()).get();
        this.spawnParticle(dataWatcher, particles, renderTick);
        final Iterator<CapeParticle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            final CapeParticle particle = iterator.next();
            CapeParticleController.PARTICLE_STACK.push();
            final float x = particle.getX() - CapeParticleController.OFFSET_VECTOR.getX() - 0.4375f;
            CapeParticleController.PARTICLE_STACK.translate(x, particle.getY() - CapeParticleController.OFFSET_VECTOR.getY() - 0.4375f, CapeParticleController.OFFSET_VECTOR.getZ());
            CapeParticleController.PARTICLE_STACK.translate(0.5f, 0.5f, 0.0f);
            final float ticksPassed = renderTick - particle.getSpawnTick();
            final float previousProgress = particle.getPreviousProgress();
            final float progress = MathHelper.lerp(ticksPassed / 10.0f, previousProgress, (previousProgress == -1.0f) ? 1.0f : partialTicks);
            final float scale = progress * 2.0f;
            CapeParticleController.PARTICLE_STACK.scale(0.015625f);
            CapeParticleController.PARTICLE_STACK.scale(scale);
            CapeParticleController.PARTICLE_STACK.translate(-0.5f, -0.5f, 0.0f);
            this.storages.add(new CapeParticleStorage(CapeParticleController.PARTICLE_STACK.getProvider().getPosition().copy(), progress));
            particle.setPreviousProgress(progress);
            CapeParticleController.PARTICLE_STACK.pop();
            if (progress > 2.0f) {
                iterator.remove();
            }
        }
        this.buffer.renderParticles(this);
        this.storages.clear();
        CapeParticleController.PARTICLE_STACK.pop();
    }
    
    private void spawnParticle(final DataWatcher dataWatcher, final List<CapeParticle> particles, final float currentTickTime) {
        if (dataWatcher.get("lastParticleSpawned", 0.0f).get() < currentTickTime) {
            dataWatcher.set("lastParticleSpawned", currentTickTime + 8.0f);
            float x = -0.28125f + CapeParticleController.RANDOM.nextFloat() * 0.5f;
            float y = -0.03125f + CapeParticleController.RANDOM.nextFloat();
            x = MathHelper.clamp(x, -0.28125f, 0.8f);
            y = MathHelper.clamp(y, -0.03125f, 0.8f);
            particles.add(new CapeParticle(x, y, currentTickTime));
        }
    }
    
    @Subscribe
    public void onShutdown(final GameShutdownEvent event) {
        this.buffer.close();
    }
    
    @Override
    public void run() {
        final GFXRenderPipeline renderPipeline = Laby.labyAPI().gfxRenderPipeline();
        final GFXBridge gfx = renderPipeline.gfx();
        gfx.enableBlend();
        gfx.defaultBlend();
        gfx.enableDepth();
        gfx.depthFunc(515);
        CapeParticleController.STAR_RENDER_PARAMETER.apply();
        this.buffer.updateProjectionMatrix(renderPipeline.matrices().projectionMatrix());
        final FloatMatrix4 prevModelViewMatrix = renderPipeline.getModelViewMatrix();
        int index = 0;
        for (final CapeParticleStorage storage : this.storages) {
            this.buffer.updateAlpha(storage.getAlpha());
            FloatMatrix4 modelViewMatrix = storage.modelViewMatrix();
            renderPipeline.setModelViewMatrix(modelViewMatrix);
            modelViewMatrix = renderPipeline.getModelViewMatrix();
            modelViewMatrix.translate(0.0f, 0.0f, 1.0E-6f * index);
            this.buffer.updateModelViewMatrix(modelViewMatrix);
            this.buffer.draw();
            ++index;
        }
        renderPipeline.setModelViewMatrix(prevModelViewMatrix);
    }
    
    static {
        RANDOM = new Random();
        PARTICLE_STACK = Stack.create(new ArrayStackProvider(16));
        OFFSET_VECTOR = new FloatVector3();
        STAR_RENDER_PARAMETER = RenderParameters.createTextureParameter(Textures.STAR);
        CUSTOM_OFFSET = isSupportedVersion();
    }
    
    static class CapeParticleRenderedBuffer
    {
        private final BufferBuilder bufferBuilder;
        private boolean created;
        private RenderedBuffer renderedBuffer;
        @Nullable
        private UniformMatrix4 projectionMatrixUniform;
        @Nullable
        private UniformMatrix4 modelViewMatrixUniform;
        @Nullable
        private Uniform4F colorModulatorUniform;
        
        public CapeParticleRenderedBuffer() {
            this.bufferBuilder = Laby.references().gfxRenderPipeline().createBufferBuilder(20);
        }
        
        public boolean isCreated() {
            return this.created;
        }
        
        public void draw() {
            if (this.renderedBuffer == null) {
                return;
            }
            this.renderedBuffer.draw();
        }
        
        public void renderParticles(final Runnable renderer) {
            if (!this.created) {
                return;
            }
            this.renderedBuffer.drawWithProgram(renderer);
        }
        
        public void updateProjectionMatrix(final FloatMatrix4 projectionMatrix) {
            if (!this.created || this.projectionMatrixUniform == null) {
                return;
            }
            this.projectionMatrixUniform.setAndUpload(projectionMatrix);
        }
        
        public void updateModelViewMatrix(final FloatMatrix4 modelViewMatrix) {
            if (!this.created || this.modelViewMatrixUniform == null) {
                return;
            }
            this.modelViewMatrixUniform.setAndUpload(modelViewMatrix);
        }
        
        public void updateAlpha(final float alpha) {
            if (!this.created || this.colorModulatorUniform == null) {
                return;
            }
            this.colorModulatorUniform.set(1.0f, 1.0f, 1.0f, MathHelper.clamp(alpha, 0.0f, 1.0f));
            this.colorModulatorUniform.upload(true);
        }
        
        public void close() {
            this.bufferBuilder.close();
        }
        
        private void createRenderedBuffer() {
            if (this.renderedBuffer != null) {
                return;
            }
            this.bufferBuilder.begin(RenderPrograms.getPositionTextureColorProgram(), () -> "Cape Particle");
            this.bufferBuilder.pos(0.0f, 0.0f, 0.0f).uv(0.0f, 0.0f).color(-1).endVertex();
            this.bufferBuilder.pos(0.0f, 1.0f, 0.0f).uv(0.0f, 1.0f).color(-1).endVertex();
            this.bufferBuilder.pos(1.0f, 1.0f, 0.0f).uv(1.0f, 1.0f).color(-1).endVertex();
            this.bufferBuilder.pos(1.0f, 0.0f, 0.0f).uv(1.0f, 0.0f).color(-1).endVertex();
            final BufferState command = this.bufferBuilder.end();
            if (command == null) {
                return;
            }
            this.renderedBuffer = command.uploadStaticDraw();
            final ShaderProgram shaderProgram = this.renderedBuffer.renderProgram().shaderProgram();
            this.projectionMatrixUniform = shaderProgram.getUniform("ProjectionMatrix");
            this.modelViewMatrixUniform = shaderProgram.getUniform("ModelViewMatrix");
            this.colorModulatorUniform = shaderProgram.getUniform("ColorModulator");
            this.created = true;
        }
    }
}
