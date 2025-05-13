// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.model;

import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.event.client.render.model.entity.player.PlayerModelRenderEvent;
import net.labymod.api.Laby;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.event.Phase;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.render.model.entity.player.PlayerModel;

@Mixin({ bbr.class })
public abstract class MixinModelPlayer extends MixinModelBiped implements PlayerModel
{
    @Shadow
    public bct v;
    @Shadow
    private bct w;
    @Shadow
    public bct c;
    @Shadow
    public bct d;
    @Shadow
    public bct a;
    @Shadow
    public bct b;
    @Shadow
    private boolean y;
    
    @Shadow
    public abstract void a(final float p0, final float p1, final float p2, final float p3, final float p4, final float p5, final pk p6);
    
    @Inject(method = { "<init>" }, at = { @At("RETURN") })
    public void labyMod$addParts(final float value, final boolean slim, final CallbackInfo callbackInfo) {
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
    
    @Insert(method = { "render(Lnet/minecraft/entity/Entity;FFFFFF)V" }, at = @At("HEAD"), cancellable = true)
    public void labyMod$preRender(final pk entity, final float limbSwing, final float prevLimbSwing, final float renderTick, final float yawHead, final float pitch, final float scale, final InsertInfo callbackInfo) {
        if (this.labyMod$firePlayerModelRenderEvent(entity, Phase.PRE)) {
            this.a(limbSwing, prevLimbSwing, renderTick, yawHead, pitch, scale, entity);
            callbackInfo.cancel();
        }
    }
    
    @Insert(method = { "render(Lnet/minecraft/entity/Entity;FFFFFF)V" }, at = @At("TAIL"))
    public void labyMod$postRender(final pk entity, final float limbSwing, final float prevLimbSwing, final float renderTick, final float yawHead, final float pitch, final float scale, final InsertInfo callbackInfo) {
        this.labyMod$firePlayerModelRenderEvent(entity, Phase.POST);
    }
    
    private boolean labyMod$firePlayerModelRenderEvent(final pk entity, final Phase phase) {
        if (!(entity instanceof bet)) {
            return false;
        }
        final int packedLight = MinecraftUtil.getPackedLight(entity);
        final Stack stack = VersionedStackProvider.DEFAULT_STACK;
        Laby.references().renderEnvironmentContext().setPackedLight(packedLight);
        return Laby.fireEvent(new PlayerModelRenderEvent((Player)entity, this, stack, phase, packedLight)).isCancelled();
    }
    
    @Override
    public ModelPart getJacket() {
        return (ModelPart)this.v;
    }
    
    @Override
    public ModelPart getCloak() {
        return (ModelPart)this.w;
    }
    
    @Override
    public ModelPart getLeftPants() {
        return (ModelPart)this.c;
    }
    
    @Override
    public ModelPart getRightPants() {
        return (ModelPart)this.d;
    }
    
    @Override
    public ModelPart getLeftSleeve() {
        return (ModelPart)this.a;
    }
    
    @Override
    public ModelPart getRightSleeve() {
        return (ModelPart)this.b;
    }
    
    @Override
    public boolean isSlim() {
        return this.y;
    }
}
