// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.debug;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.core.main.user.shop.item.items.pet.ai.PetBehavior;
import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.gfx.pipeline.renderer.immediate.ImmediateRenderer;
import net.labymod.api.client.gfx.pipeline.renderer.immediate.ImmediateDrawPhase;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import net.labymod.api.client.gfx.pipeline.program.RenderPrograms;
import net.labymod.api.Laby;
import net.labymod.api.debug.DebugRegistry;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.core.main.user.shop.item.items.pet.WalkingPet;
import java.util.concurrent.atomic.AtomicInteger;

public class WalkingPetDebugRenderer
{
    private static final int HITBOX_COLOR;
    private static final AtomicInteger COUNTER;
    private static final String INTERNAL_NAME = "WalkingPet";
    private final WalkingPet walkingPet;
    private final String itemName;
    private final String name;
    
    public WalkingPetDebugRenderer(final WalkingPet walkingPet) {
        this.walkingPet = walkingPet;
        this.itemName = this.walkingPet.getName();
        this.name = "WalkingPet" + WalkingPetDebugRenderer.COUNTER.getAndIncrement();
    }
    
    public void render(final Stack stack, final float partialTicks) {
        if (!DebugRegistry.PETS_AI.isEnabled()) {
            return;
        }
        final GFXRenderPipeline renderPipeline = Laby.references().gfxRenderPipeline();
        final BufferBuilder bufferBuilder = renderPipeline.getDefaultBufferBuilder();
        bufferBuilder.begin(RenderPrograms.getUIRectangleProgram());
        final PetBehavior behavior = this.walkingPet.behavior();
        this.renderAABB(stack, bufferBuilder, behavior.boundingBox(), WalkingPetDebugRenderer.HITBOX_COLOR);
        final GFXBridge gfx = Laby.gfx();
        gfx.enableDepth();
        gfx.depthFunc(515);
        gfx.disableCull();
        ImmediateRenderer.drawWithProgram(bufferBuilder.end(), ImmediateDrawPhase.IDENTITY_MODEL_MATRIX_DRAW_PHASE);
        gfx.enableCull();
        gfx.disableDepth();
        final TextRenderer renderer = Laby.references().textRendererProvider().getRenderer();
        final float scale = 0.015625f;
        stack.push();
        final MinecraftCamera camera = Laby.labyAPI().minecraft().getCamera();
        stack.rotate(-camera.getYaw(), 0.0f, 1.0f, 0.0f);
        stack.scale(scale);
        stack.scale((float)this.walkingPet.itemDetails().getScale());
        final RenderEnvironmentContext renderEnvironmentContext = renderPipeline.renderEnvironmentContext();
        final int packedLight = renderEnvironmentContext.getPackedLight();
        renderEnvironmentContext.setPackedLight(15728880);
        final Model model = this.walkingPet.getModel();
        final float y = (model.getHeight() + 4.0f) / scale * 0.0625f;
        stack.translate(0.0f, -y, 0.0f);
        renderer.pos(0.0f, 0.0f).text(this.name + " (" + this.itemName).centered(true).color(-1).shadow(false).drawMode(TextDrawMode.SEE_THROUGH).render(stack);
        stack.translate(0.0f, -8.0f, 0.0f);
        renderer.pos(0.0f, 0.0f).text(RenderableComponent.of(((BaseComponent<Component>)Component.text("Animation Trigger: ")).append(Component.text(this.walkingPet.getAnimationTrigger().getName(), NamedTextColor.YELLOW)))).centered(true).color(-1).shadow(false).drawMode(TextDrawMode.SEE_THROUGH).render(stack);
        renderEnvironmentContext.setPackedLight(packedLight);
        stack.pop();
    }
    
    private void renderAABB(final Stack stack, final BufferConsumer consumer, final AxisAlignedBoundingBox aabb, final int color) {
        stack.push();
        final DoubleVector3 center = aabb.getCenter();
        stack.translate(-center.getX(), -aabb.getHeight(), -center.getZ());
        this.fillVertex(stack, consumer, aabb.getMinX(), aabb.getMinY(), aabb.getMinZ(), color);
        this.fillVertex(stack, consumer, aabb.getMinX(), aabb.getMaxY(), aabb.getMinZ(), color);
        this.fillVertex(stack, consumer, aabb.getMaxX(), aabb.getMaxY(), aabb.getMinZ(), color);
        this.fillVertex(stack, consumer, aabb.getMaxX(), aabb.getMinY(), aabb.getMinZ(), color);
        this.fillVertex(stack, consumer, aabb.getMinX(), aabb.getMinY(), aabb.getMaxZ(), color);
        this.fillVertex(stack, consumer, aabb.getMinX(), aabb.getMaxY(), aabb.getMaxZ(), color);
        this.fillVertex(stack, consumer, aabb.getMaxX(), aabb.getMaxY(), aabb.getMaxZ(), color);
        this.fillVertex(stack, consumer, aabb.getMaxX(), aabb.getMinY(), aabb.getMaxZ(), color);
        this.fillVertex(stack, consumer, aabb.getMinX(), aabb.getMinY(), aabb.getMaxZ(), color);
        this.fillVertex(stack, consumer, aabb.getMinX(), aabb.getMaxY(), aabb.getMaxZ(), color);
        this.fillVertex(stack, consumer, aabb.getMinX(), aabb.getMaxY(), aabb.getMinZ(), color);
        this.fillVertex(stack, consumer, aabb.getMinX(), aabb.getMinY(), aabb.getMinZ(), color);
        this.fillVertex(stack, consumer, aabb.getMaxX(), aabb.getMinY(), aabb.getMaxZ(), color);
        this.fillVertex(stack, consumer, aabb.getMaxX(), aabb.getMaxY(), aabb.getMaxZ(), color);
        this.fillVertex(stack, consumer, aabb.getMaxX(), aabb.getMaxY(), aabb.getMinZ(), color);
        this.fillVertex(stack, consumer, aabb.getMaxX(), aabb.getMinY(), aabb.getMinZ(), color);
        this.fillVertex(stack, consumer, aabb.getMinX(), aabb.getMinY(), aabb.getMinZ(), color);
        this.fillVertex(stack, consumer, aabb.getMinX(), aabb.getMinY(), aabb.getMaxZ(), color);
        this.fillVertex(stack, consumer, aabb.getMaxX(), aabb.getMinY(), aabb.getMaxZ(), color);
        this.fillVertex(stack, consumer, aabb.getMaxX(), aabb.getMinY(), aabb.getMinZ(), color);
        this.fillVertex(stack, consumer, aabb.getMinX(), aabb.getMaxY(), aabb.getMinZ(), color);
        this.fillVertex(stack, consumer, aabb.getMinX(), aabb.getMaxY(), aabb.getMaxZ(), color);
        this.fillVertex(stack, consumer, aabb.getMaxX(), aabb.getMaxY(), aabb.getMaxZ(), color);
        this.fillVertex(stack, consumer, aabb.getMaxX(), aabb.getMaxY(), aabb.getMinZ(), color);
        stack.pop();
    }
    
    private void fillVertex(final Stack stack, final BufferConsumer consumer, final double x, final double y, final double z, final int color) {
        consumer.pos(stack, x, y, z).uv(0.0f, 0.0f).color(color).endVertex();
    }
    
    static {
        HITBOX_COLOR = ColorFormat.ARGB32.pack(1.0f, 1.0f, 1.0f, 0.2f);
        COUNTER = new AtomicInteger(0);
    }
}
