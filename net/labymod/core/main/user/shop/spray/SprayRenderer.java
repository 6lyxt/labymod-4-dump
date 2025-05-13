// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.spray;

import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;
import net.labymod.api.client.gfx.shader.uniform.Uniform1F;
import net.labymod.api.client.gfx.shader.material.ShaderMaterial;
import net.labymod.api.client.gfx.pipeline.program.RenderProgram;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.gfx.pipeline.renderer.immediate.ImmediateRenderer;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gfx.shader.material.SprayShaderMaterial;
import net.labymod.api.client.gfx.pipeline.program.RenderPrograms;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.main.user.shop.spray.model.Spray;
import net.labymod.api.event.client.lifecycle.GameShutdownEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.world.RenderWorldEvent;
import net.labymod.api.user.GameUser;
import net.labymod.api.client.world.ClientWorld;
import java.util.Iterator;
import java.util.Collection;
import net.labymod.api.util.collection.map.Multimap;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.Laby;
import net.labymod.api.util.math.Direction;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.client.render.matrix.Stack;
import java.util.ArrayList;
import java.util.List;
import net.labymod.core.main.user.shop.spray.model.SprayAssetProvider;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gfx.pipeline.renderer.level.SurfaceRenderer;
import net.labymod.api.event.EventBus;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public final class SprayRenderer
{
    private static final float DEFAULT_RADIUS = 2.0f;
    private static final FloatMatrix4 DEFAULT_MODEL_VIEW_MATRIX;
    private final EventBus eventBus;
    private final SurfaceRenderer surfaceRenderer;
    private final GFXRenderPipeline renderPipeline;
    private final SprayAssetProvider sprayAssetProvider;
    private final SprayService sprayService;
    private final SprayRegistry sprayRegistry;
    private final List<SprayObject> toRemove;
    
    public SprayRenderer(final EventBus eventBus, final SurfaceRenderer surfaceRenderer, final GFXRenderPipeline renderPipeline, final SprayAssetProvider sprayAssetProvider, final SprayService sprayService, final SprayRegistry sprayRegistry) {
        this.toRemove = new ArrayList<SprayObject>();
        this.eventBus = eventBus;
        this.surfaceRenderer = surfaceRenderer;
        this.renderPipeline = renderPipeline;
        this.sprayAssetProvider = sprayAssetProvider;
        this.sprayService = sprayService;
        this.sprayRegistry = sprayRegistry;
        this.eventBus.registerListener(this);
    }
    
    private void renderObjects(final Stack stack, final MinecraftCamera camera) {
        this.renderPipeline.setProjectionMatrix();
        final FloatMatrix4 prevModelViewMatrix = this.renderPipeline.getModelViewMatrix();
        this.renderPipeline.setModelViewMatrix(SprayRenderer.DEFAULT_MODEL_VIEW_MATRIX);
        final DoubleVector3 renderPosition = camera.renderPosition();
        final double cameraX = renderPosition.getX();
        final double cameraY = renderPosition.getY();
        final double cameraZ = renderPosition.getZ();
        final RenderEnvironmentContext context = this.renderPipeline.renderEnvironmentContext();
        final int prevPacketLight = context.getPackedLight();
        final Multimap<Direction, SprayObject> objects = this.sprayRegistry.getObjects();
        for (final Direction direction : Direction.VALUES) {
            final Collection<SprayObject> sprays = objects.get(direction);
            if (!sprays.isEmpty()) {
                stack.push();
                for (final SprayObject spray : sprays) {
                    if (spray.isExpired()) {
                        this.toRemove.add(spray);
                    }
                    final double x = spray.getX() - cameraX;
                    final double y = spray.getY() - cameraY;
                    final double z = spray.getZ() - cameraZ;
                    this.applyOffset(stack, direction);
                    final ClientWorld level = Laby.labyAPI().minecraft().clientWorld();
                    final DoubleVector3 position = spray.position();
                    final int packedLight = switch (direction) {
                        default -> throw new MatchException(null, null);
                        case DOWN -> level.getPackedLight(position.getX(), position.getY() - 1.0, position.getZ());
                        case UP -> level.getPackedLight(position.getX(), position.getY() + 1.0, position.getZ());
                        case NORTH -> level.getPackedLight(position.getX(), position.getY(), position.getZ() - 1.0);
                        case SOUTH -> level.getPackedLight(position.getX(), position.getY(), position.getZ() + 1.0);
                        case WEST -> level.getPackedLight(position.getX() - 1.0, position.getY(), position.getZ());
                        case EAST -> level.getPackedLight(position.getX() + 1.0, position.getY(), position.getZ());
                    };
                    context.setPackedLight(packedLight);
                    stack.push();
                    stack.translate(x, y, z);
                    this.rotateStack(stack, direction);
                    this.render(stack, spray);
                    stack.pop();
                }
                stack.pop();
                for (final SprayObject sprayObject : this.toRemove) {
                    sprays.remove(sprayObject);
                    final Multimap<GameUser, SprayObject> ownerSprays = this.sprayRegistry.getOwnerSprays();
                    ownerSprays.get(sprayObject.getOwner()).remove(sprayObject);
                }
                this.toRemove.clear();
            }
        }
        context.setPackedLight(prevPacketLight);
        this.renderPipeline.setModelViewMatrix(prevModelViewMatrix);
    }
    
    @Subscribe
    public void onWorldRender(final RenderWorldEvent event) {
        final MinecraftCamera camera = event.camera();
        final Stack stack = event.stack();
        stack.push();
        this.renderObjects(stack, camera);
        stack.pop();
    }
    
    @Subscribe
    public void onShutdown(final GameShutdownEvent event) {
        if (this.surfaceRenderer != null) {
            this.surfaceRenderer.close();
        }
    }
    
    public void render(final Stack stack, final SprayObject object) {
        final Spray spray = this.sprayService.findSpray(object.getId());
        if (spray == null) {
            return;
        }
        this.render(stack, object, spray);
    }
    
    public void render(final Stack stack, final SprayObject object, final Spray spray) {
        final CompletableResourceLocation diffuseTextureLocation = this.sprayAssetProvider.getTexture(spray, SprayAssetProvider.TextureType.DIFFUSE);
        final CompletableResourceLocation wearTextureLocation = this.sprayAssetProvider.getTexture(spray, object.isFadeOut() ? SprayAssetProvider.TextureType.WEAR_FADE_OUT : SprayAssetProvider.TextureType.WEAR_FADE_IN);
        final RenderProgram renderProgram = RenderPrograms.getSpray(diffuseTextureLocation.getCompleted(), wearTextureLocation.getCompleted());
        final ShaderMaterial shaderMaterial = renderProgram.shaderMaterial();
        if (shaderMaterial instanceof final SprayShaderMaterial sprayShaderMaterial) {
            final int packedLight = this.renderPipeline.renderEnvironmentContext().getPackedLight();
            sprayShaderMaterial.getLightCoords().set(packedLight & 0xFFFF, packedLight >> 16 & 0xFFFF);
            final long time = object.getCreationTime() + 60000L;
            final long lifespan = TimeUtil.getMillis() + 60000L;
            final long duration = lifespan - time;
            float normalizedDuration = duration / 60000.0f;
            final Uniform1F wear = sprayShaderMaterial.getWear();
            if (object.isFadeIn()) {
                normalizedDuration *= 60.0f;
                normalizedDuration = Math.abs(normalizedDuration);
            }
            else if (object.isNormal()) {
                normalizedDuration = 1.0f;
            }
            else if (object.isFadeOut()) {
                normalizedDuration = 1.0f - normalizedDuration;
                normalizedDuration *= 60.0f;
            }
            final float interpolatedDuration = MathHelper.lerp(normalizedDuration, object.getPreviousDuration());
            object.setPreviousDuration(interpolatedDuration);
            wear.set(interpolatedDuration);
        }
        final BufferBuilder buffer = this.renderPipeline.getDefaultBufferBuilder();
        buffer.begin(renderProgram);
        this.surfaceRenderer.renderSurface(stack, buffer, object.getX(), object.getY(), object.getZ(), object.direction(), 2.0f, object.getRotation());
        ImmediateRenderer.drawWithProgram(buffer.end());
    }
    
    private void rotateStack(final Stack stack, final Direction direction) {
        if (direction == Direction.UP) {
            stack.rotate(90.0f, 1.0f, 0.0f, 0.0f);
        }
        else if (direction == Direction.EAST) {
            stack.rotate(-90.0f, 0.0f, 1.0f, 0.0f);
            stack.rotate(180.0f, 1.0f, 0.0f, 0.0f);
            stack.translate(0.0f, 0.0f, 0.001f);
        }
        else if (direction == Direction.WEST) {
            stack.rotate(90.0f, 0.0f, 1.0f, 0.0f);
            stack.rotate(180.0f, 1.0f, 0.0f, 0.0f);
            stack.translate(0.0f, 0.0f, 0.001f);
        }
        else if (direction == Direction.DOWN) {
            stack.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
            stack.rotate(180.0f, 0.0f, 0.0f, 1.0f);
            stack.scale(-1.0f, 1.0f, 1.0f);
            stack.translate(0.0f, 0.0f, -0.01f);
        }
        else if (direction == Direction.SOUTH) {
            stack.rotate(180.0f, 1.0f, 0.0f, 0.0f);
            stack.translate(0.0f, 0.0f, -0.001f);
        }
        else if (direction == Direction.NORTH) {
            stack.rotate(180.0f, 1.0f, 0.0f, 0.0f);
            stack.translate(0.0f, 0.0f, 0.001f);
        }
    }
    
    private void applyOffset(final Stack stack, final Direction direction) {
        this.applyOffset(stack, direction, 0);
    }
    
    private void applyOffset(final Stack stack, final Direction direction, int size) {
        if (size <= 0) {
            size = 1;
        }
        final float offset = 1.0E-4f * size;
        if (direction == Direction.UP) {
            stack.translate(0.0f, offset, 0.0f);
        }
        else if (direction == Direction.DOWN) {
            stack.translate(0.0f, -offset, 0.0f);
        }
        else if (direction == Direction.NORTH) {
            stack.translate(0.0f, 0.0f, -offset);
        }
        else if (direction == Direction.SOUTH) {
            stack.translate(0.0f, 0.0f, offset);
        }
        else if (direction == Direction.EAST) {
            stack.translate(offset, 0.0f, 0.0f);
        }
        else {
            stack.translate(-offset, 0.0f, 0.0f);
        }
    }
    
    static {
        DEFAULT_MODEL_VIEW_MATRIX = FloatMatrix4.newIdentity();
    }
}
