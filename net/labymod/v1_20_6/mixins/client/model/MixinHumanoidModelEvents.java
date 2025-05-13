// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.model;

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

@Mixin({ fup.class })
public class MixinHumanoidModelEvents<T extends btr>
{
    @Shadow
    public fup.a r;
    @Shadow
    public fup.a s;
    public fup.a storedRightArmPose;
    public fup.a storedLeftArmPose;
    
    @Insert(method = { "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V" }, at = @At("HEAD"))
    protected void labyMod$preSetupAnim(final T entity, final float limbSwing, final float prevLimbSwing, final float renderTick, final float yawHead, final float pitch, final InsertInfo callbackInfo) {
        Laby.fireEvent(new HumanoidModelAnimateEvent((LivingEntity)entity, (HumanoidModel)this, Phase.PRE));
    }
    
    @Inject(method = { "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getMainArm()Lnet/minecraft/world/entity/HumanoidArm;", ordinal = 0, shift = At.Shift.BEFORE) })
    private void labyMod$prePose(final T entity, final float limbSwing, final float prevLimbSwing, final float renderTick, final float yawHead, final float pitch, final CallbackInfo callbackInfo) {
        this.storedLeftArmPose = this.r;
        this.storedRightArmPose = this.s;
        if (Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.PRE, (LivingEntity)entity, LivingEntity.HandSide.LEFT)).isCancelled()) {
            this.r = fup.a.a;
        }
        if (Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.PRE, (LivingEntity)entity, LivingEntity.HandSide.RIGHT)).isCancelled()) {
            this.s = fup.a.a;
        }
    }
    
    @Inject(method = { "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;setupAttackAnimation(Lnet/minecraft/world/entity/LivingEntity;F)V", ordinal = 0, shift = At.Shift.BEFORE) })
    private void labyMod$postPose(final T entity, final float limbSwing, final float prevLimbSwing, final float renderTick, final float yawHead, final float pitch, final CallbackInfo callbackInfo) {
        this.r = this.storedLeftArmPose;
        this.s = this.storedRightArmPose;
        Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.POST, (LivingEntity)entity, LivingEntity.HandSide.LEFT));
        Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.POST, (LivingEntity)entity, LivingEntity.HandSide.RIGHT));
    }
    
    @Insert(method = { "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V" }, at = @At("TAIL"))
    protected void labyMod$postSetupAnim(final T entity, final float limbSwing, final float prevLimbSwing, final float renderTick, final float yawHead, final float pitch, final InsertInfo callbackInfo) {
        Laby.fireEvent(new HumanoidModelAnimateEvent((LivingEntity)entity, (HumanoidModel)this, Phase.POST));
    }
}
