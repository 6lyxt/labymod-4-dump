// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.minecraft;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.ClientPlayer;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.event.client.entity.player.ClientPlayerInteractEvent;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ djz.class })
public class MixinClientPlayerInteraction
{
    @Shadow
    public dzm s;
    
    @Insert(method = { "startAttack()V" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$handleAttack(final InsertInfo callbackInfo) {
        if (this.labyMod$fireClientPlayerInteractEvent(ClientPlayerInteractEvent.InteractionType.ATTACK)) {
            callbackInfo.cancel();
        }
    }
    
    @Insert(method = { "startUseItem()V" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$handleUseItem(final InsertInfo callbackInfo) {
        if (this.labyMod$fireClientPlayerInteractEvent(ClientPlayerInteractEvent.InteractionType.INTERACT)) {
            callbackInfo.cancel();
        }
    }
    
    @Insert(method = { "continueAttack(Z)V" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$handleContinueAttack(final boolean leftClick, final InsertInfo callbackInfo) {
        if (!leftClick) {
            return;
        }
        if (this.labyMod$fireClientPlayerInteractEvent(ClientPlayerInteractEvent.InteractionType.CONTINUE_ATTACK)) {
            callbackInfo.cancel();
        }
    }
    
    @Insert(method = { "pickBlock()V" }, at = @At("HEAD"), cancellable = true)
    private void labyMod$handlePickBlock(final InsertInfo callbackInfo) {
        if (this.labyMod$fireClientPlayerInteractEvent(ClientPlayerInteractEvent.InteractionType.PICK_BLOCK)) {
            callbackInfo.cancel();
        }
    }
    
    private boolean labyMod$fireClientPlayerInteractEvent(final ClientPlayerInteractEvent.InteractionType interactionType) {
        return Laby.fireEvent(new ClientPlayerInteractEvent((ClientPlayer)this.s, interactionType)).isCancelled();
    }
}
