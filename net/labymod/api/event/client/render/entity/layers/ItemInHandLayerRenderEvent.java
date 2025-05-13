// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render.entity.layers;

import net.labymod.api.event.Phase;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.ModelTransformType;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.event.client.render.CancellableRenderEvent;

public class ItemInHandLayerRenderEvent extends CancellableRenderEvent
{
    private final LivingEntity livingEntity;
    private final ItemStack itemStack;
    private final ModelTransformType type;
    private final LivingEntity.HandSide handSide;
    private final int combinedLight;
    
    public ItemInHandLayerRenderEvent(@NotNull final Stack stack, @NotNull final Phase phase, @NotNull final LivingEntity livingEntity, @NotNull final ItemStack itemStack, @NotNull final ModelTransformType type, @NotNull final LivingEntity.HandSide handSide, final int combinedLight) {
        super(stack, phase);
        this.livingEntity = livingEntity;
        this.itemStack = itemStack;
        this.type = type;
        this.handSide = handSide;
        this.combinedLight = combinedLight;
    }
    
    public LivingEntity livingEntity() {
        return this.livingEntity;
    }
    
    public ItemStack itemStack() {
        return this.itemStack;
    }
    
    public ModelTransformType type() {
        return this.type;
    }
    
    public LivingEntity.HandSide handSide() {
        return this.handSide;
    }
    
    public int getCombinedLight() {
        return this.combinedLight;
    }
}
