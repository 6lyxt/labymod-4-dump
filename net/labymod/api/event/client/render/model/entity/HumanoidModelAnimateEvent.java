// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render.model.entity;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.event.Phase;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.event.Event;

public class HumanoidModelAnimateEvent implements Event
{
    private final LivingEntity livingEntity;
    private final HumanoidModel model;
    private final Phase phase;
    
    public HumanoidModelAnimateEvent(@NotNull final LivingEntity livingEntity, @NotNull final HumanoidModel model, @NotNull final Phase phase) {
        this.livingEntity = livingEntity;
        this.model = model;
        this.phase = phase;
    }
    
    @NotNull
    public LivingEntity livingEntity() {
        return this.livingEntity;
    }
    
    @NotNull
    public HumanoidModel model() {
        return this.model;
    }
    
    @NotNull
    public Phase phase() {
        return this.phase;
    }
}
