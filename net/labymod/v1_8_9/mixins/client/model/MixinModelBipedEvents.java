// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.model;

import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.event.client.render.model.entity.HumanoidModelPoseAnimationEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.model.entity.HumanoidModelAnimateEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bbj.class })
public class MixinModelBipedEvents
{
    @Shadow
    public int l;
    @Shadow
    public int m;
    public int storedHeldItemLeft;
    public int storedHeldItemRight;
    
    @Insert(method = { "setRotationAngles" }, at = @At("HEAD"))
    protected void labyMod$preSetRotationAngles(final float limbSwing, final float prevLimbSwing, final float renderTick, final float yawHead, final float pitch, final float scale, final pk entity, final InsertInfo callbackInfo) {
        Laby.fireEvent(new HumanoidModelAnimateEvent((LivingEntity)entity, (HumanoidModel)this, Phase.PRE));
    }
    
    @Inject(method = { "setRotationAngles" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelBiped;heldItemLeft:I", ordinal = 0, shift = At.Shift.BEFORE) })
    private void prePose(final float limbSwing, final float prevLimbSwing, final float renderTick, final float yawHead, final float pitch, final float scale, final pk entity, final CallbackInfo callbackInfo) {
        this.storedHeldItemLeft = this.l;
        this.storedHeldItemRight = this.m;
        if (Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.PRE, (LivingEntity)entity, LivingEntity.HandSide.LEFT)).isCancelled()) {
            this.l = 0;
        }
        if (Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.PRE, (LivingEntity)entity, LivingEntity.HandSide.RIGHT)).isCancelled()) {
            this.m = 0;
        }
    }
    
    @Inject(method = { "setRotationAngles" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelBiped;swingProgress:F", ordinal = 0, shift = At.Shift.BEFORE) })
    private void postPose(final float limbSwing, final float prevLimbSwing, final float renderTick, final float yawHead, final float pitch, final float scale, final pk entity, final CallbackInfo callbackInfo) {
        this.l = this.storedHeldItemLeft;
        this.m = this.storedHeldItemRight;
        Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.POST, (LivingEntity)entity, LivingEntity.HandSide.LEFT));
        Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.POST, (LivingEntity)entity, LivingEntity.HandSide.RIGHT));
    }
    
    @Insert(method = { "setRotationAngles" }, at = @At("TAIL"))
    protected void labyMod$postSetRotationAngles(final float limbSwing, final float prevLimbSwing, final float renderTick, final float yawHead, final float pitch, final float scale, final pk entity, final InsertInfo callbackInfo) {
        Laby.fireEvent(new HumanoidModelAnimateEvent((LivingEntity)entity, (HumanoidModel)this, Phase.POST));
    }
}
