// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.player;

import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.entity.LivingEntity;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import net.labymod.api.client.entity.player.ClientPlayer;

@Implements({ @Interface(iface = ClientPlayer.class, prefix = "player$") })
@Mixin({ bud.class })
public abstract class MixinEntityPlayerSP extends MixinAbstractLocalPlayer implements ClientPlayer
{
    @Shadow
    private boolean cr;
    private boolean labyMod$cancelSwingPacket;
    
    @Shadow
    public abstract void a(final ub p0);
    
    @Override
    public boolean isAbilitiesFlying() {
        return this.labyMod$getClientPlayer().bO.b;
    }
    
    @Override
    public float getAbilitiesWalkingSpeed() {
        return this.labyMod$getClientPlayer().bO.b();
    }
    
    @Intrinsic
    public boolean player$isHandActive() {
        return this.labyMod$getClientPlayer().cG();
    }
    
    @Override
    public boolean isUsingBow() {
        return this.labyMod$getClientPlayer().cJ().c() == air.g;
    }
    
    @Override
    public void swingArm(final LivingEntity.Hand hand) {
        this.swingArm(hand, false);
    }
    
    @Override
    public void swingArm(final LivingEntity.Hand hand, final boolean cancelPacket) {
        this.labyMod$cancelSwingPacket = cancelPacket;
        this.a((hand == LivingEntity.Hand.MAIN_HAND) ? ub.a : ub.b);
    }
    
    @Override
    public void setDistanceWalked(final float distance) {
        this.labyMod$getClientPlayer().J = distance;
    }
    
    @Override
    public float getForwardMovingSpeed() {
        return this.labyMod$getClientPlayer().e.b;
    }
    
    @Override
    public float getStrafeMovingSpeed() {
        return this.labyMod$getClientPlayer().e.a;
    }
    
    @Override
    public Inventory inventory() {
        return (Inventory)((bua)this).bv;
    }
    
    @Insert(method = { "swingArm" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/NetHandlerPlayClient;sendPacket(Lnet/minecraft/network/Packet;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$cancelSwingPacket(final ub hand, final InsertInfo ci) {
        if (this.labyMod$cancelSwingPacket) {
            ci.cancel();
            this.labyMod$cancelSwingPacket = false;
        }
    }
    
    private bud labyMod$getClientPlayer() {
        return bib.z().h;
    }
}
