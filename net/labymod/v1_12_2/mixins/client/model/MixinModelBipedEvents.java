// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.model;

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

@Mixin({ bpx.class })
public class MixinModelBipedEvents
{
    @Shadow
    public bpx.a l;
    @Shadow
    public bpx.a m;
    public bpx.a storedRightArmPose;
    public bpx.a storedLeftArmPose;
    
    @Insert(method = { "setRotationAngles" }, at = @At("HEAD"))
    protected void labyMod$preSetRotationAngles(final float limbSwing, final float prevLimbSwing, final float renderTick, final float yawHead, final float pitch, final float scale, final vg entity, final InsertInfo callbackInfo) {
        Laby.fireEvent(new HumanoidModelAnimateEvent((LivingEntity)entity, (HumanoidModel)this, Phase.PRE));
    }
    
    @Inject(method = { "setRotationAngles" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelBiped;leftArmPose:Lnet/minecraft/client/model/ModelBiped$ArmPose;", ordinal = 0, shift = At.Shift.BEFORE) })
    private void prePose(final float limbSwing, final float prevLimbSwing, final float renderTick, final float yawHead, final float pitch, final float scale, final vg entity, final CallbackInfo callbackInfo) {
        this.storedLeftArmPose = this.l;
        this.storedRightArmPose = this.m;
        if (Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.PRE, (LivingEntity)entity, LivingEntity.HandSide.LEFT)).isCancelled()) {
            this.l = bpx.a.a;
        }
        if (Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.PRE, (LivingEntity)entity, LivingEntity.HandSide.RIGHT)).isCancelled()) {
            this.m = bpx.a.a;
        }
    }
    
    @Inject(method = { "setRotationAngles" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelBiped;swingProgress:F", ordinal = 0, shift = At.Shift.BEFORE) })
    private void postPose(final float limbSwing, final float prevLimbSwing, final float renderTick, final float yawHead, final float pitch, final float scale, final vg entity, final CallbackInfo callbackInfo) {
        this.l = this.storedLeftArmPose;
        this.m = this.storedRightArmPose;
        Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.POST, (LivingEntity)entity, LivingEntity.HandSide.LEFT));
        Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.POST, (LivingEntity)entity, LivingEntity.HandSide.RIGHT));
    }
    
    @Insert(method = { "setRotationAngles" }, at = @At("TAIL"))
    protected void labyMod$postSetRotationAngles(final float limbSwing, final float prevLimbSwing, final float renderTick, final float yawHead, final float pitch, final float scale, final vg entity, final InsertInfo callbackInfo) {
        Laby.fireEvent(new HumanoidModelAnimateEvent((LivingEntity)entity, (HumanoidModel)this, Phase.POST));
    }
}
