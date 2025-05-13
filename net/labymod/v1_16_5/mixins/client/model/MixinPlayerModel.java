// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.model;

import net.labymod.api.client.render.model.ModelPart;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.render.model.entity.player.PlayerModel;

@Mixin({ dvd.class })
public class MixinPlayerModel<T extends aqm> extends MixinHumanoidModel<T> implements PlayerModel
{
    @Shadow
    @Final
    public dwn x;
    @Shadow
    @Final
    private dwn b;
    @Shadow
    @Final
    public dwn v;
    @Shadow
    @Final
    public dwn w;
    @Shadow
    @Final
    public dwn t;
    @Shadow
    @Final
    public dwn u;
    @Shadow
    @Final
    private boolean z;
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    private void labyMod$addParts(final float lvt_1_1_, final boolean lvt_2_1_, final CallbackInfo ci) {
        this.labyMod$addInternalChild("left_leg", this.getLeftLeg());
        this.labyMod$addInternalChild("right_leg", this.getRightLeg());
        this.labyMod$addInternalChild("left_arm", this.getLeftArm());
        this.labyMod$addInternalChild("right_arm", this.getRightArm());
        this.labyMod$addInternalChild("jacket", this.getJacket());
        this.labyMod$addInternalChild("cloak", this.getCloak());
        this.labyMod$addInternalChild("left_pants", this.getLeftPants());
        this.labyMod$addInternalChild("right_pants", this.getRightPants());
        this.labyMod$addInternalChild("left_sleeve", this.getLeftSleeve());
        this.labyMod$addInternalChild("right_sleeve", this.getRightSleeve());
    }
    
    @Override
    public ModelPart getJacket() {
        return (ModelPart)this.x;
    }
    
    @Override
    public ModelPart getCloak() {
        return (ModelPart)this.b;
    }
    
    @Override
    public ModelPart getLeftPants() {
        return (ModelPart)this.v;
    }
    
    @Override
    public ModelPart getRightPants() {
        return (ModelPart)this.w;
    }
    
    @Override
    public ModelPart getLeftSleeve() {
        return (ModelPart)this.t;
    }
    
    @Override
    public ModelPart getRightSleeve() {
        return (ModelPart)this.u;
    }
    
    @Override
    public boolean isSlim() {
        return this.z;
    }
}
