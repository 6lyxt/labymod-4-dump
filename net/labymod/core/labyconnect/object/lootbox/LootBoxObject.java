// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.object.lootbox;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.core.labyconnect.object.lootbox.content.PoolCategory;
import net.labymod.api.client.render.font.ComponentRenderer;
import java.util.List;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.component.Component;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.math.Transformation;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.core.client.gui.screen.activity.activities.ingame.event.LootBoxActivity;
import net.labymod.core.labyconnect.object.lootbox.content.LootBoxShopItem;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.render.matrix.Stack;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.core.main.user.shop.item.geometry.animation.AnimationLoader;
import net.labymod.api.client.render.model.DefaultModelBuffer;
import net.labymod.api.Laby;
import net.labymod.core.main.LabyMod;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelBuffer;
import net.labymod.api.client.render.model.animation.AnimationController;
import java.util.Queue;
import net.labymod.core.labyconnect.object.lootbox.content.LootBoxContent;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.model.compiler.ImmediateModelCompiler;
import net.labymod.api.client.world.object.AbstractWorldObject;

public class LootBoxObject extends AbstractWorldObject implements ImmediateModelCompiler.Visitor
{
    private final Player owner;
    private final float rotation;
    private final LootBoxContent content;
    private final Queue<Runnable> delayedRenderCalls;
    private final AnimationController animationController;
    private final ModelBuffer modelBuffer;
    private Model model;
    private boolean priceRevealed;
    
    public LootBoxObject(final Player owner, final DoubleVector3 location, final float rotation, final LootBoxContent content, final int lootBoxType) {
        super(location);
        this.delayedRenderCalls = new ConcurrentLinkedQueue<Runnable>();
        this.priceRevealed = false;
        this.owner = owner;
        this.rotation = rotation;
        this.content = content;
        final LootBoxService service = LabyMod.references().lootBoxService();
        final LootBox lootBox = service.byId(lootBoxType);
        if (lootBox == null || lootBox.getModel() == null) {
            this.animationController = null;
            this.modelBuffer = null;
        }
        else {
            final LootBoxModel model = lootBox.getModel();
            final AnimationLoader animationLoader = lootBox.getAnimationLoader();
            if (animationLoader == null) {
                this.animationController = null;
            }
            else {
                this.animationController = Laby.references().modelService().createAnimationController().hiddenPart(part -> false);
                final ModelAnimation animation = animationLoader.getAnimation("open");
                if (animation != null) {
                    this.animationController.playNext(animation);
                }
            }
            this.model = model.model();
            (this.modelBuffer = new DefaultModelBuffer(this.model)).setVisitor(this);
            this.modelBuffer.setImmediate(true);
        }
    }
    
    @Override
    public void renderInWorld(@NotNull final MinecraftCamera cam, @NotNull final Stack stack, final double x, final double y, final double z, final float delta, final boolean darker) {
        final LabyAPI api = Laby.labyAPI();
        final GFXRenderPipeline pipeline = api.gfxRenderPipeline();
        if (this.model == null || this.modelBuffer == null) {
            return;
        }
        this.animationController.applyAnimation(this.model, new String[0]);
        final int packedLight = api.minecraft().clientWorld().getPackedLight(this.position());
        pipeline.renderEnvironmentContext().setPackedLight(packedLight);
        pipeline.setProjectionMatrix();
        stack.push();
        stack.rotate(this.rotation, 0.0f, 1.0f, 0.0f);
        this.modelBuffer.rebuildModel();
        this.modelBuffer.render(stack);
        final RenderEnvironmentContext renderEnvironmentContext = Laby.references().renderEnvironmentContext();
        renderEnvironmentContext.setPackedLight(15728880);
        while (!this.delayedRenderCalls.isEmpty()) {
            final Runnable poll = this.delayedRenderCalls.poll();
            poll.run();
        }
        stack.pop();
        if (!this.priceRevealed && this.animationController.getProgress() > 14500L) {
            this.priceRevealed = true;
            final LootBoxShopItem price = this.content.getPriceShopItem();
            this.onPriceReveal(price);
        }
    }
    
    private void onPriceReveal(final LootBoxShopItem price) {
        if (this.owner == Laby.labyAPI().minecraft().getClientPlayer()) {
            Laby.labyAPI().minecraft().sounds().playSound(price.category().getSound(), 0.05f, 1.0f, this.position());
        }
    }
    
    @Override
    public void onRemove() {
        final Minecraft minecraft = Laby.labyAPI().minecraft();
        if (minecraft.getClientPlayer() == this.owner) {
            final Window window = minecraft.minecraftWindow();
            window.displayScreen(LootBoxActivity.showPrice(this.content));
        }
    }
    
    @Override
    public boolean shouldRemove() {
        return !this.animationController.isPlaying();
    }
    
    @Override
    public void visit(final Stack stack, final BufferConsumer consumer, final ModelPart part) {
        final String name = part.getDebugName();
        if (name.equals("spin")) {
            final MinecraftCamera camera = Laby.labyAPI().minecraft().getCamera();
            if (camera != null) {
                stack.rotate(camera.getYaw() - this.rotation, 0.0f, 1.0f, 0.0f);
                stack.rotate(-camera.getPitch() / 4.0f, 1.0f, 0.0f, 0.0f);
            }
        }
        if (name.startsWith("item")) {
            final int index = Integer.parseInt(name.substring(4)) - 1;
            part.setVisible(false);
            final Transformation animationTransformation = part.getAnimationTransformation();
            final FloatVector3 animationScale = animationTransformation.getScale();
            if (animationScale.getX() == 0.0f && animationScale.getY() == 0.0f && animationScale.getZ() == 0.0f) {
                return;
            }
            stack.push();
            part.getModelPartTransform().transform(stack, animationTransformation);
            final FloatMatrix4 modelViewMatrix = stack.getProvider().getPosition().copy();
            this.delayedRenderCalls.add(() -> {
                final Stack localStack = Laby.references().stackProviderFactory().create();
                localStack.push();
                localStack.multiply(modelViewMatrix);
                Laby.gfx().enableDepth();
                Laby.gfx().disableCull();
                this.renderShopItem(localStack, index);
                localStack.pop();
                return;
            });
            stack.pop();
        }
    }
    
    private void renderShopItem(final Stack stack, final int index) {
        final List<LootBoxShopItem> pool = this.content.pool();
        final int poolSize = pool.size();
        if (index < 0 || poolSize <= index) {
            return;
        }
        final ComponentRenderer renderer = Laby.references().componentRenderer();
        final RectangleRenderer rectangleRenderer = Laby.references().rectangleRenderer();
        final LootBoxShopItem item = pool.get(index);
        final Integer itemColor = item.getColor();
        final PoolCategory category = item.category();
        final float scale = 0.0109375f;
        final float iconSize = 32.0f * scale;
        final long timePassed = this.animationController.getProgress();
        final boolean isPrice = index == 29;
        final long timeSinceReveal = timePassed - 14500L;
        if (isPrice && this.priceRevealed) {
            final float fadeIn = MathHelper.clamp(timeSinceReveal / 200.0f, 0.0f, 1.0f);
            stack.push();
            if (!PlatformEnvironment.isAncientOpenGL()) {
                stack.scale(1.0f, 1.0f, -1.0f);
            }
            renderer.builder().centered(true).text(Component.text(item.name(), item.category().getTextColor())).pos(0.0f, -iconSize / 2.0f - 13.0f * scale * fadeIn).scale(scale * fadeIn).shadow(true).color(-1).render(stack);
            if (this.content.hasDuration()) {
                renderer.builder().centered(true).text(this.content.getDurationComponent()).pos(0.0f, -iconSize / 2.0f - 3.0f * scale * fadeIn).scale(scale * fadeIn / 4.0f).shadow(false).color(-1).render(stack);
            }
            stack.pop();
        }
        Laby.gfx().enableDepth();
        Laby.gfx().disableCull();
        final float zOffset = -6.25E-4f;
        final int color = (itemColor == null) ? category.getColor() : itemColor;
        rectangleRenderer.pos(-iconSize / 2.0f, -iconSize / 2.0f).size(iconSize, iconSize).color(color).render(stack);
        stack.translate(0.0f, 0.0f, zOffset);
        rectangleRenderer.pos(-iconSize / 2.0f + scale / 2.0f, -iconSize / 2.0f + scale / 2.0f).size(iconSize - scale, iconSize - scale).gradientVertical(-11513776, color).render(stack);
        stack.translate(0.0f, 0.0f, zOffset);
        final Icon icon = item.getIcon();
        final int width = icon.getResolutionWidth();
        final int height = icon.getResolutionHeight();
        float horizontalOffset = 0.0f;
        float verticalOffset = 0.0f;
        if (height > width) {
            verticalOffset = iconSize / height * (height - width);
        }
        else {
            horizontalOffset = iconSize / width * (width - height);
        }
        icon.render(stack, -iconSize / 2.0f + verticalOffset / 2.0f + scale / 2.0f, -iconSize / 2.0f + horizontalOffset / 2.0f + scale / 2.0f, iconSize - verticalOffset - scale, iconSize - horizontalOffset - scale);
    }
    
    public Player getOwner() {
        return this.owner;
    }
}
