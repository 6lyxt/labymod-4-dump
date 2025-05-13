// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.model;

import net.labymod.api.event.client.render.model.entity.HumanoidModelPoseAnimationEvent;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.model.entity.HumanoidModelAnimateEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.v1_21_3.client.util.EntityRenderStateAccessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ gbz.class })
public class MixinHumanoidModelEvents<T extends bwg>
{
    public gbz.a storedRightArmPose;
    public gbz.a storedLeftArmPose;
    
    @Inject(method = { "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V" }, at = { @At("HEAD") })
    protected void labyMod$preSetupAnim(final gyl state, final CallbackInfo ci) {
        final EntityRenderStateAccessor<bwg> livingEntityState = EntityRenderStateAccessor.self(state);
        if (livingEntityState == null) {
            return;
        }
        Laby.fireEvent(new HumanoidModelAnimateEvent((LivingEntity)livingEntityState.labyMod$getEntity(), (HumanoidModel)this, Phase.PRE));
    }
    
    @Inject(method = { "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;mainArm:Lnet/minecraft/world/entity/HumanoidArm;") })
    private void labyMod$prePose(final gyl state, final CallbackInfo ci, @Local(ordinal = 0) final LocalRef<gbz.a> leftArmPose, @Local(ordinal = 1) final LocalRef<gbz.a> rightArmPose) {
        final EntityRenderStateAccessor<bwg> livingEntityState = EntityRenderStateAccessor.self(state);
        if (livingEntityState == null) {
            return;
        }
        final bwg entity = livingEntityState.labyMod$getEntity();
        this.storedLeftArmPose = (gbz.a)leftArmPose.get();
        this.storedRightArmPose = (gbz.a)rightArmPose.get();
        if (Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.PRE, (LivingEntity)entity, LivingEntity.HandSide.LEFT)).isCancelled()) {
            leftArmPose.set((Object)gbz.a.a);
        }
        if (Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.PRE, (LivingEntity)entity, LivingEntity.HandSide.RIGHT)).isCancelled()) {
            rightArmPose.set((Object)gbz.a.a);
        }
    }
    
    @Inject(method = { "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;setupAttackAnimation(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;F)V", shift = At.Shift.BEFORE) })
    private void labyMod$postPose(final gyl state, final CallbackInfo ci, @Local(ordinal = 0) final LocalRef<gbz.a> leftArmPose, @Local(ordinal = 1) final LocalRef<gbz.a> rightArmPose) {
        final EntityRenderStateAccessor<bwg> livingEntityState = EntityRenderStateAccessor.self(state);
        if (livingEntityState == null) {
            return;
        }
        final bwg entity = livingEntityState.labyMod$getEntity();
        leftArmPose.set((Object)this.storedLeftArmPose);
        rightArmPose.set((Object)this.storedRightArmPose);
        Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.POST, (LivingEntity)entity, LivingEntity.HandSide.LEFT));
        Laby.fireEvent(new HumanoidModelPoseAnimationEvent(Phase.POST, (LivingEntity)entity, LivingEntity.HandSide.RIGHT));
    }
    
    @Inject(method = { "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V" }, at = { @At("TAIL") })
    protected void labyMod$postSetupAnim(final gyl state, final CallbackInfo ci) {
        final EntityRenderStateAccessor<bwg> livingEntityState = EntityRenderStateAccessor.self(state);
        if (livingEntityState == null) {
            return;
        }
        Laby.fireEvent(new HumanoidModelAnimateEvent((LivingEntity)livingEntityState.labyMod$getEntity(), (HumanoidModel)this, Phase.POST));
    }
}
