// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client.player;

import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.entity.LivingEntity;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.player.ClientPlayer;

@Mixin({ fcz.class })
public abstract class MixinLocalPlayer extends MixinAbstractLocalPlayer implements ClientPlayer
{
    private boolean labyMod$cancelHandSwingPacket;
    
    public MixinLocalPlayer() {
        this.labyMod$cancelHandSwingPacket = false;
    }
    
    @Shadow
    public abstract void a(final bcl p0);
    
    @Override
    public boolean isAbilitiesFlying() {
        return this.labyMod$getClientPlayer().fF().b;
    }
    
    @Override
    public float getAbilitiesWalkingSpeed() {
        return this.labyMod$getClientPlayer().fF().b();
    }
    
    @Override
    public boolean isHandActive() {
        return this.labyMod$getClientPlayer().eZ();
    }
    
    @Override
    public boolean isUsingBow() {
        return this.labyMod$getClientPlayer().fb().c() == cdw.nh;
    }
    
    @Override
    public void swingArm(final LivingEntity.Hand hand) {
        this.swingArm(hand, false);
    }
    
    @Override
    public void swingArm(final LivingEntity.Hand hand, final boolean cancelPacket) {
        final bcl interactionHand = (hand == LivingEntity.Hand.MAIN_HAND) ? bcl.a : bcl.b;
        this.labyMod$cancelHandSwingPacket = cancelPacket;
        this.a(interactionHand);
    }
    
    @Override
    public Inventory inventory() {
        return (Inventory)this.labyMod$getClientPlayer().fE();
    }
    
    @Override
    public void setDistanceWalked(final float distance) {
        this.labyMod$getClientPlayer().I = distance;
    }
    
    @Override
    public float getForwardMovingSpeed() {
        return this.labyMod$getClientPlayer().co.b;
    }
    
    @Insert(method = { "swing" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;send(Lnet/minecraft/network/protocol/Packet;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$cancelHandSwingPacket(final bcl hand, final InsertInfo ci) {
        if (this.labyMod$cancelHandSwingPacket) {
            ci.cancel();
            this.labyMod$cancelHandSwingPacket = false;
        }
    }
    
    private fcz labyMod$getClientPlayer() {
        return (fcz)this;
    }
}
