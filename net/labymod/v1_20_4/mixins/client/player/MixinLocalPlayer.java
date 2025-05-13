// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.client.player;

import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.entity.LivingEntity;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.player.ClientPlayer;

@Mixin({ fsj.class })
public abstract class MixinLocalPlayer extends MixinAbstractLocalPlayer implements ClientPlayer
{
    private boolean labyMod$cancelHandSwingPacket;
    
    public MixinLocalPlayer() {
        this.labyMod$cancelHandSwingPacket = false;
    }
    
    @Shadow
    public abstract void a(final bka p0);
    
    @Override
    public boolean isAbilitiesFlying() {
        return this.labyMod$getClientPlayer().fT().b;
    }
    
    @Override
    public float getAbilitiesWalkingSpeed() {
        return this.labyMod$getClientPlayer().fT().b();
    }
    
    @Override
    public boolean isHandActive() {
        return this.labyMod$getClientPlayer().fn();
    }
    
    @Override
    public boolean isUsingBow() {
        return this.labyMod$getClientPlayer().fp().d() == cnb.or;
    }
    
    @Override
    public void swingArm(final LivingEntity.Hand hand) {
        this.swingArm(hand, false);
    }
    
    @Override
    public void swingArm(final LivingEntity.Hand hand, final boolean cancelPacket) {
        final bka interactionHand = (hand == LivingEntity.Hand.MAIN_HAND) ? bka.a : bka.b;
        this.labyMod$cancelHandSwingPacket = cancelPacket;
        this.a(interactionHand);
    }
    
    @Override
    public Inventory inventory() {
        return (Inventory)this.labyMod$getClientPlayer().fS();
    }
    
    @Override
    public void setDistanceWalked(final float distance) {
        this.labyMod$getClientPlayer().Y = distance;
    }
    
    @Override
    public float getForwardMovingSpeed() {
        return this.labyMod$getClientPlayer().co.b;
    }
    
    @Insert(method = { "swing" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;send(Lnet/minecraft/network/protocol/Packet;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$cancelHandSwingPacket(final bka hand, final InsertInfo ci) {
        if (this.labyMod$cancelHandSwingPacket) {
            ci.cancel();
            this.labyMod$cancelHandSwingPacket = false;
        }
    }
    
    private fsj labyMod$getClientPlayer() {
        return (fsj)this;
    }
}
