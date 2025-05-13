// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.client.player;

import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.entity.LivingEntity;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.player.ClientPlayer;

@Mixin({ eyw.class })
public abstract class MixinLocalPlayer extends MixinAbstractLocalPlayer implements ClientPlayer
{
    private boolean labyMod$cancelHandSwingPacket;
    
    public MixinLocalPlayer() {
        this.labyMod$cancelHandSwingPacket = false;
    }
    
    @Shadow
    public abstract void a(final bai p0);
    
    @Override
    public boolean isAbilitiesFlying() {
        return this.labyMod$getClientPlayer().fB().b;
    }
    
    @Override
    public float getAbilitiesWalkingSpeed() {
        return this.labyMod$getClientPlayer().fB().b();
    }
    
    @Override
    public boolean isHandActive() {
        return this.labyMod$getClientPlayer().eT();
    }
    
    @Override
    public boolean isUsingBow() {
        return this.labyMod$getClientPlayer().eV().c() == caz.mQ;
    }
    
    @Override
    public void swingArm(final LivingEntity.Hand hand) {
        this.swingArm(hand, false);
    }
    
    @Override
    public void swingArm(final LivingEntity.Hand hand, final boolean cancelPacket) {
        final bai interactionHand = (hand == LivingEntity.Hand.MAIN_HAND) ? bai.a : bai.b;
        this.labyMod$cancelHandSwingPacket = cancelPacket;
        this.a(interactionHand);
    }
    
    @Override
    public Inventory inventory() {
        return (Inventory)this.labyMod$getClientPlayer().fA();
    }
    
    @Override
    public void setDistanceWalked(final float distance) {
        this.labyMod$getClientPlayer().I = distance;
    }
    
    @Override
    public float getForwardMovingSpeed() {
        return this.labyMod$getClientPlayer().cp.b;
    }
    
    @Insert(method = { "swing" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;send(Lnet/minecraft/network/protocol/Packet;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$cancelHandSwingPacket(final bai hand, final InsertInfo ci) {
        if (this.labyMod$cancelHandSwingPacket) {
            ci.cancel();
            this.labyMod$cancelHandSwingPacket = false;
        }
    }
    
    private eyw labyMod$getClientPlayer() {
        return (eyw)this;
    }
}
