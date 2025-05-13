// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry;

import org.jetbrains.annotations.NotNull;
import net.labymod.core.client.render.model.animation.DefaultAnimationController;
import net.labymod.api.client.render.model.animation.AnimationController;

public class AnimationStorage
{
    private AnimationController controller;
    private boolean lastSneaking;
    private boolean lastMoving;
    private boolean lastFirstPerson;
    private long lastTriggerMillis;
    
    private AnimationStorage() {
        this.controller = new DefaultAnimationController(DefaultAnimationController.SHARED_MODEL_ANIMATION);
    }
    
    private AnimationStorage(final AnimationStorage other) {
        this.controller = other.controller;
        this.lastSneaking = other.lastSneaking;
        this.lastMoving = other.lastMoving;
        this.lastTriggerMillis = other.lastTriggerMillis;
    }
    
    @NotNull
    public static AnimationStorage create() {
        return new AnimationStorage();
    }
    
    @NotNull
    public static AnimationStorage copyOf(final AnimationStorage other) {
        return new AnimationStorage(other);
    }
    
    public AnimationController getController() {
        return this.controller;
    }
    
    public void setController(final AnimationController controller) {
        this.controller = controller;
    }
    
    public boolean isLastSneaking() {
        return this.lastSneaking;
    }
    
    public void setLastSneaking(final boolean lastSneaking) {
        this.lastSneaking = lastSneaking;
    }
    
    public boolean isLastMoving() {
        return this.lastMoving;
    }
    
    public void setLastMoving(final boolean lastMoving) {
        this.lastMoving = lastMoving;
    }
    
    public boolean isLastFirstPerson() {
        return this.lastFirstPerson;
    }
    
    public void setLastFirstPerson(final boolean lastFirstPerson) {
        this.lastFirstPerson = lastFirstPerson;
    }
    
    public void setLastTriggerMillis(final long lastTriggerMillis) {
        this.lastTriggerMillis = lastTriggerMillis;
    }
    
    public long getLastTriggerMillis() {
        return this.lastTriggerMillis;
    }
}
