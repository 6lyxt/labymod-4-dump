// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity.types;

import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.overlay.IngameOverlayElementRenderEvent;
import net.labymod.api.event.client.entity.player.ClientPlayerInteractEvent;
import net.labymod.api.event.client.network.server.NetworkDisconnectEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.entity.player.ClientPlayerTurnEvent;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.options.Perspective;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.entity.player.CameraLockController;

@Deprecated(forRemoval = true, since = "4.1.12")
public abstract class IngameSelectionOverlayActivity extends IngameOverlayActivity
{
    private final CameraLockController cameraLockController;
    private final Minecraft minecraft;
    private boolean prevUnlimitedPitch;
    protected boolean unlockOnClose;
    protected float startYaw;
    protected float startPitch;
    protected boolean firstPersonMotion;
    protected boolean visible;
    
    public IngameSelectionOverlayActivity() {
        this.cameraLockController = Laby.references().cameraLockController();
        this.minecraft = super.labyAPI.minecraft();
        this.firstPersonMotion = true;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        ((Document)this.document).setVisible(this.visible);
    }
    
    @Override
    public void render(final ScreenContext context) {
        if (((Document)this.document).isVisible() && this.firstPersonMotion && this.minecraft.options().perspective() == Perspective.FIRST_PERSON) {
            final float diffX = this.startYaw - this.cameraLockController.getYaw();
            final float diffY = this.startPitch - this.cameraLockController.getPitch();
            ((Document)this.document).setTranslateX(diffX);
            ((Document)this.document).setTranslateY(diffY);
        }
        else {
            ((Document)this.document).setTranslateX(0.0f);
            ((Document)this.document).setTranslateY(0.0f);
        }
        final MutableMouse mouse = context.mouse();
        if (mouse.isOutOfBounds()) {
            mouse.set(this.getCursorX(), this.getCursorY(), () -> super.render(context));
        }
        else {
            super.render(context);
        }
    }
    
    @Subscribe(126)
    public void onClientPlayerTurnEvent(final ClientPlayerTurnEvent event) {
        if (!this.isVisible()) {
            return;
        }
        this.updateCursorInBounds(this.getYaw(), this.getPitch(), this.getCursorX(), this.getCursorY());
    }
    
    @Subscribe
    public void onDisconnect(final NetworkDisconnectEvent event) {
        this.setVisible(false);
    }
    
    protected abstract void updateCursorInBounds(final double p0, final double p1, final float p2, final float p3);
    
    @Override
    public boolean isVisible() {
        return ((Document)this.document).isVisible();
    }
    
    public void setVisible(final boolean visible) {
        if (this.visible == visible) {
            return;
        }
        if (visible) {
            this.startYaw = this.cameraLockController.getYaw();
            this.startPitch = this.cameraLockController.getPitch();
            if (!this.cameraLockController.isLocked() || this.cameraLockController.lockType() != CameraLockController.LockType.HEAD) {
                this.unlockOnClose = true;
                this.cameraLockController.lock(CameraLockController.LockType.HEAD);
            }
            else {
                this.prevUnlimitedPitch = this.cameraLockController.isUnlimitedPitch();
            }
            this.cameraLockController.setUnlimitedPitch(true);
        }
        else if (this.unlockOnClose) {
            this.cameraLockController.unlock();
            this.unlockOnClose = false;
        }
        else {
            this.cameraLockController.setUnlimitedPitch(this.prevUnlimitedPitch);
        }
        this.visible = visible;
        ((Document)this.document).setVisible(visible);
    }
    
    public float getCursorX() {
        return super.bounds().getCenterX() - (this.startYaw - this.getYaw()) * 2.0f;
    }
    
    public float getCursorY() {
        return super.bounds().getCenterY() - (this.startPitch - this.getPitch()) * 2.0f;
    }
    
    public void setCursorX(final float x) {
        this.setYaw(x / 2.0f - this.bounds().getCenterX() / 2.0f + this.startYaw);
    }
    
    public void setCursorY(final float y) {
        this.setPitch(y / 2.0f - this.bounds().getCenterY() / 2.0f + this.startPitch);
    }
    
    public void setYaw(final float yaw) {
        this.cameraLockController.setYaw(yaw);
    }
    
    public void setPitch(final float pitch) {
        this.cameraLockController.setPitch(pitch);
    }
    
    public float getYaw() {
        return this.cameraLockController.getYaw();
    }
    
    public float getPitch() {
        return this.cameraLockController.getPitch();
    }
    
    public CameraLockController cameraLockController() {
        return this.cameraLockController;
    }
    
    @Subscribe
    public void preventWorldInteraction(final ClientPlayerInteractEvent event) {
        if (((Document)this.document).isVisible()) {
            event.setCancelled(true);
        }
    }
    
    @Subscribe
    public void preventCrosshairRender(final IngameOverlayElementRenderEvent event) {
        if (event.phase() == Phase.PRE && event.elementType() == IngameOverlayElementRenderEvent.OverlayElementType.CROSSHAIR && ((Document)this.document).isVisible()) {
            event.setCancelled(true);
        }
    }
}
