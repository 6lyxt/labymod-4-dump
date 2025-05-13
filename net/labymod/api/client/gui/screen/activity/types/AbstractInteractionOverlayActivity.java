// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity.types;

import net.labymod.api.event.client.gui.screen.ScreenOpenEvent;
import net.labymod.api.event.client.entity.player.ClientPlayerInteractEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.event.client.entity.player.ClientPlayerTurnEvent;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.options.Perspective;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.CameraLockController;
import net.labymod.api.client.gui.screen.activity.AutoActivity;

@AutoActivity
public abstract class AbstractInteractionOverlayActivity extends IngameOverlayActivity
{
    private final CameraLockController cameraLockController;
    private boolean interactionOpen;
    private float initialYaw;
    private float initialPitch;
    
    public AbstractInteractionOverlayActivity() {
        this(Laby.references().cameraLockController());
    }
    
    public AbstractInteractionOverlayActivity(final CameraLockController cameraLockController) {
        this.cameraLockController = cameraLockController;
    }
    
    @Override
    public void render(final ScreenContext context) {
        final float yawOffset = this.getYawOffset();
        final float pitchOffset = this.getPitchOffset();
        final Bounds bounds = this.bounds();
        final float mouseX = bounds.getCenterX() - yawOffset;
        final float mouseY = bounds.getCenterY() - pitchOffset;
        final MutableMouse mouse = context.mouse();
        if (!this.isAcceptingInput()) {
            mouse.set(mouseX, mouseY);
        }
        final Perspective perspective = this.labyAPI.minecraft().options().perspective();
        context.pushStack();
        final Stack stack = context.stack();
        if (perspective == Perspective.FIRST_PERSON) {
            stack.translate(yawOffset, pitchOffset, 0.0f);
        }
        final float tickDelta = context.getTickDelta();
        this.renderInteractionOverlay(stack, mouse, tickDelta);
        super.render(context);
        if (perspective != Perspective.FIRST_PERSON) {
            final float size = 4.0f;
            this.labyAPI.renderPipeline().rectangleRenderer().pos(mouseX - 1.0f, mouseY - size - 1.0f).size(1.0f, size * 2.0f + 1.0f).color(Integer.MAX_VALUE).render(stack);
            this.labyAPI.renderPipeline().rectangleRenderer().pos(mouseX - size - 1.0f, mouseY - 1.0f).size(size * 2.0f + 1.0f, 1.0f).color(Integer.MAX_VALUE).render(stack);
        }
        context.popStack();
    }
    
    @Subscribe(126)
    public void onClientPlayerTurnEvent(final ClientPlayerTurnEvent event) {
        if (!this.isInteractionOpen()) {
            return;
        }
        final float yawOffset = this.getYawOffset();
        final float pitchOffset = this.getPitchOffset();
        final float offsetDistance = (float)Math.sqrt(MathHelper.square(yawOffset) + MathHelper.square(pitchOffset));
        final float movementRadius = this.getRadius();
        if (offsetDistance > movementRadius) {
            final float multiplier = movementRadius / offsetDistance;
            this.cameraLockController.setYaw(this.initialYaw - yawOffset / 2.0f * multiplier);
            this.cameraLockController.setPitch(this.initialPitch - pitchOffset / 2.0f * multiplier);
        }
    }
    
    @Subscribe
    public void onClientPlayerInteract(final ClientPlayerInteractEvent event) {
        if (this.isVisible()) {
            event.setCancelled(true);
        }
    }
    
    @Subscribe
    public void onScreenOpen(final ScreenOpenEvent event) {
        if (event.getScreen() == this) {
            return;
        }
        if (this.shouldCloseOnScreenOpen()) {
            this.closeInteraction();
        }
    }
    
    public boolean openInteraction() {
        if (this.cameraLockController.isLocked()) {
            return false;
        }
        this.cameraLockController.lock(CameraLockController.LockType.HEAD);
        this.initialYaw = this.cameraLockController.getYaw();
        this.initialPitch = this.cameraLockController.getPitch();
        this.interactionOpen = true;
        this.reload();
        return true;
    }
    
    public void closeInteraction() {
        if (!this.interactionOpen) {
            return;
        }
        this.interactionOpen = false;
        this.cameraLockController.unlock();
    }
    
    @Override
    public boolean isVisible() {
        return this.interactionOpen;
    }
    
    public boolean isInteractionOpen() {
        return this.interactionOpen;
    }
    
    protected float getYawOffset() {
        final float yawOffset = this.initialYaw - this.cameraLockController.getYaw();
        return yawOffset * this.getSensitivity();
    }
    
    protected float getPitchOffset() {
        final float pitchOffset = this.initialPitch - this.cameraLockController.getPitch();
        return pitchOffset * this.getSensitivity();
    }
    
    protected abstract void renderInteractionOverlay(final Stack p0, final MutableMouse p1, final float p2);
    
    protected abstract float getRadius();
    
    protected float getSensitivity() {
        return 2.0f;
    }
    
    protected boolean shouldCloseOnScreenOpen() {
        return true;
    }
}
