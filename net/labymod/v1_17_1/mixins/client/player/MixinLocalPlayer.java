// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.player;

import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.player.ClientPlayer;

@Mixin({ emm.class })
public abstract class MixinLocalPlayer extends MixinAbstractLocalPlayer implements ClientPlayer
{
    private boolean labyMod$cancelHandSwingPacket;
    @Shadow
    @Final
    public ejj cx;
    
    public MixinLocalPlayer() {
        this.labyMod$cancelHandSwingPacket = false;
    }
    
    @Shadow
    public abstract void a(final asa p0);
    
    @Override
    public boolean isAbilitiesFlying() {
        return this.labyMod$getClientPlayer().fl().b;
    }
    
    @Override
    public float getAbilitiesWalkingSpeed() {
        return this.labyMod$getClientPlayer().fl().b();
    }
    
    @Override
    public boolean isHandActive() {
        return this.labyMod$getClientPlayer().eF();
    }
    
    @Override
    public boolean isUsingBow() {
        return this.labyMod$getClientPlayer().eH().c() == bqs.mg;
    }
    
    @Override
    public void swingArm(final LivingEntity.Hand hand) {
        this.swingArm(hand, false);
    }
    
    @Override
    public void swingArm(final LivingEntity.Hand hand, final boolean cancelPacket) {
        final asa interactionHand = (hand == LivingEntity.Hand.MAIN_HAND) ? asa.a : asa.b;
        this.labyMod$cancelHandSwingPacket = cancelPacket;
        this.a(interactionHand);
    }
    
    @Override
    public Inventory inventory() {
        return (Inventory)this.labyMod$getClientPlayer().fk();
    }
    
    @Override
    public void setDistanceWalked(final float distance) {
        this.labyMod$getClientPlayer().H = distance;
    }
    
    @Override
    public float getForwardMovingSpeed() {
        return this.labyMod$getClientPlayer().cy.b;
    }
    
    @Override
    public float getStrafeMovingSpeed() {
        return this.labyMod$getClientPlayer().cy.a;
    }
    
    @Insert(method = { "swing" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;send(Lnet/minecraft/network/protocol/Packet;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$cancelHandSwingPacket(final asa hand, final InsertInfo ci) {
        if (this.labyMod$cancelHandSwingPacket) {
            ci.cancel();
            this.labyMod$cancelHandSwingPacket = false;
        }
    }
    
    private emm labyMod$getClientPlayer() {
        return (emm)this;
    }
}
