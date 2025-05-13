// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.client.multiplayer.chat;

import net.labymod.v1_19_2.client.chat.VersionedChatComponent;
import java.time.Instant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_19_2.client.chat.ChatMessageMeta;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ eve.class })
public class MixinChatListener
{
    @Inject(method = { "handleSystemMessage" }, at = { @At("HEAD") })
    private void labyMod$storeSystemChatType(final rq message, final boolean overlay, final CallbackInfo ci) {
        if (overlay) {
            return;
        }
        this.chatComponent().setMessageMeta(message, ChatMessageMeta.system());
    }
    
    @Inject(method = { "handleSystemMessage" }, at = { @At("RETURN") })
    private void labyMod$clearSystemChatType(final rq message, final boolean overlay, final CallbackInfo ci) {
        this.chatComponent().clearMessageMeta(message);
    }
    
    @Inject(method = { "processNonPlayerChatMessage" }, at = { @At("HEAD") })
    private void labyMod$storeSystemChatType(final rm.a type, final sd message, final rq component, final CallbackInfoReturnable<Boolean> cir) {
        this.chatComponent().setMessageMeta(component, ChatMessageMeta.system());
    }
    
    @Inject(method = { "processNonPlayerChatMessage" }, at = { @At("RETURN") })
    private void labyMod$clearSystemChatType(final rm.a type, final sd message, final rq component, final CallbackInfoReturnable<Boolean> cir) {
        this.chatComponent().clearMessageMeta(component);
    }
    
    @Inject(method = { "processPlayerChatMessage" }, at = { @At("HEAD") })
    private void labyMod$storeChatMessageChatType(final rm.a type, final sd message, final rq component, final euz playerInfo, final boolean onlyShowSecureChat, final Instant instant, final CallbackInfoReturnable<Boolean> cir) {
        this.chatComponent().setMessageMeta(component, ChatMessageMeta.player((playerInfo != null) ? playerInfo.a().getId() : null));
    }
    
    @Inject(method = { "processPlayerChatMessage" }, at = { @At("RETURN") })
    private void labyMod$clearChatMessageChatType(final rm.a type, final sd message, final rq component, final euz playerInfo, final boolean onlyShowSecureChat, final Instant instant, final CallbackInfoReturnable<Boolean> cir) {
        this.chatComponent().clearMessageMeta(component);
    }
    
    private VersionedChatComponent chatComponent() {
        return (VersionedChatComponent)efu.I().l.d();
    }
}
