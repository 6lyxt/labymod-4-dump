// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player;

import net.labymod.api.event.client.entity.player.camera.CameraUnlockEvent;
import net.labymod.api.event.client.entity.player.camera.CameraLockEvent;
import net.labymod.api.event.client.render.camera.CameraRotationEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.Laby;
import net.labymod.api.event.client.entity.player.ClientPlayerTurnEvent;
import javax.inject.Inject;
import net.labymod.api.event.EventBus;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class CameraLockController
{
    private boolean locked;
    private LockType lockType;
    private boolean unlimitedPitch;
    private float yaw;
    private float pitch;
    
    @Inject
    public CameraLockController(final EventBus eventBus) {
        eventBus.registerListener(this);
    }
    
    @Subscribe
    public void onClientPlayerTurnEvent(final ClientPlayerTurnEvent event) {
        if (!this.locked) {
            return;
        }
        if (this.lockType == LockType.HEAD) {
            this.setYaw((float)(this.yaw + event.getX()));
            this.setPitch((float)(this.pitch + event.getY()));
        }
        if (event.getX() > 0.0 || event.getY() > 0.0) {
            Laby.labyAPI().minecraft().requestChunkUpdate();
        }
        event.setCancelled(true);
    }
    
    @Subscribe
    public void onCameraRotationEvent(final CameraRotationEvent event) {
        if (this.locked) {
            event.setYaw(this.yaw);
            event.setPitch(this.pitch);
        }
        else {
            this.setYaw(event.getYaw());
            this.setPitch(event.getPitch());
        }
    }
    
    public void lock(final LockType lockType) {
        this.locked = true;
        this.lockType = lockType;
        this.unlimitedPitch = false;
        Laby.fireEvent(new CameraLockEvent(lockType));
    }
    
    public void lock(final LockType lockType, final float yaw, final float pitch) {
        this.lock(lockType);
        this.yaw = yaw;
        this.pitch = pitch;
    }
    
    public void unlock() {
        this.locked = false;
        this.unlimitedPitch = false;
        Laby.labyAPI().minecraft().requestChunkUpdate();
        Laby.fireEvent(new CameraUnlockEvent());
    }
    
    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }
    
    public void setPitch(final float pitch) {
        this.pitch = pitch;
        if (!this.unlimitedPitch) {
            if (this.pitch > 90.0f) {
                this.pitch = 90.0f;
            }
            else if (this.pitch < -90.0f) {
                this.pitch = -90.0f;
            }
        }
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public boolean isLocked() {
        return this.locked;
    }
    
    public LockType lockType() {
        return this.lockType;
    }
    
    public boolean isUnlimitedPitch() {
        return this.unlimitedPitch;
    }
    
    public void setUnlimitedPitch(final boolean unlimitedPitch) {
        this.unlimitedPitch = unlimitedPitch;
    }
    
    public enum LockType
    {
        CAMERA, 
        HEAD;
    }
}
