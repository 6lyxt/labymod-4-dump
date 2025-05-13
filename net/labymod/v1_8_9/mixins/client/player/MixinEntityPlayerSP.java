// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.player;

import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.entity.LivingEntity;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.entity.player.ClientPlayer;

@Mixin({ bew.class })
public abstract class MixinEntityPlayerSP extends MixinAbstractLocalPlayer implements ClientPlayer
{
    private boolean labyMod$cancelSwingPacket;
    
    @Shadow
    public abstract void bw();
    
    @Override
    public boolean isAbilitiesFlying() {
        return this.labyMod$getClientPlayer().bA.b;
    }
    
    @Override
    public float getAbilitiesWalkingSpeed() {
        return this.labyMod$getClientPlayer().bA.b();
    }
    
    @Override
    public boolean isHandActive() {
        return this.labyMod$getClientPlayer().bS();
    }
    
    @Override
    public boolean isUsingBow() {
        return this.labyMod$getClientPlayer().bQ().b() == zy.f;
    }
    
    @Override
    public void swingArm(final LivingEntity.Hand hand) {
        this.swingArm(hand, false);
    }
    
    @Override
    public void swingArm(final LivingEntity.Hand hand, final boolean cancelPacket) {
        this.labyMod$cancelSwingPacket = cancelPacket;
        this.bw();
    }
    
    @Override
    public void setDistanceWalked(final float distance) {
        this.labyMod$getClientPlayer().M = distance;
    }
    
    @Override
    public float getForwardMovingSpeed() {
        return this.labyMod$getClientPlayer().b.b;
    }
    
    @Override
    public float getStrafeMovingSpeed() {
        return this.labyMod$getClientPlayer().b.a;
    }
    
    @Override
    public Inventory inventory() {
        return (Inventory)((bet)this).bi;
    }
    
    @Insert(method = { "swingItem()V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/NetHandlerPlayClient;addToSendQueue(Lnet/minecraft/network/Packet;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$cancelSwingPacket(final InsertInfo ci) {
        if (this.labyMod$cancelSwingPacket) {
            ci.cancel();
            this.labyMod$cancelSwingPacket = false;
        }
    }
    
    private bew labyMod$getClientPlayer() {
        return ave.A().h;
    }
}
