// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.player;

import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.entity.LivingEntity;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.player.ClientPlayer;

@Mixin({ gqm.class })
public abstract class MixinLocalPlayer extends MixinAbstractLocalPlayer implements ClientPlayer
{
    private boolean labyMod$cancelHandSwingPacket;
    
    public MixinLocalPlayer() {
        this.labyMod$cancelHandSwingPacket = false;
    }
    
    @Shadow
    public abstract void a(final bvb p0);
    
    @Override
    public boolean isAbilitiesFlying() {
        return this.labyMod$getClientPlayer().gk().b;
    }
    
    @Override
    public float getAbilitiesWalkingSpeed() {
        return this.labyMod$getClientPlayer().gk().b();
    }
    
    @Override
    public boolean isHandActive() {
        return this.labyMod$getClientPlayer().fz();
    }
    
    @Override
    public boolean isUsingBow() {
        return this.labyMod$getClientPlayer().fB().h() == dao.pj;
    }
    
    @Override
    public void swingArm(final LivingEntity.Hand hand) {
        this.swingArm(hand, false);
    }
    
    @Override
    public void swingArm(final LivingEntity.Hand hand, final boolean cancelPacket) {
        final bvb interactionHand = (hand == LivingEntity.Hand.MAIN_HAND) ? bvb.a : bvb.b;
        this.labyMod$cancelHandSwingPacket = cancelPacket;
        this.a(interactionHand);
    }
    
    @Override
    public Inventory inventory() {
        return (Inventory)this.labyMod$getClientPlayer().gj();
    }
    
    @Override
    public void setDistanceWalked(final float distance) {
        this.labyMod$getClientPlayer().g = distance;
    }
    
    @Override
    public float getForwardMovingSpeed() {
        return this.labyMod$getClientPlayer().k.b().k;
    }
    
    @Insert(method = { "swing" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;send(Lnet/minecraft/network/protocol/Packet;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$cancelHandSwingPacket(final bvb hand, final InsertInfo ci) {
        if (this.labyMod$cancelHandSwingPacket) {
            ci.cancel();
            this.labyMod$cancelHandSwingPacket = false;
        }
    }
    
    private gqm labyMod$getClientPlayer() {
        return (gqm)this;
    }
}
