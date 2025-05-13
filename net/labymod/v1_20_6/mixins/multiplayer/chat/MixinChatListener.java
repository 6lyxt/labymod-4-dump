// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.multiplayer.chat;

import net.labymod.v1_20_6.client.chat.VersionedChatComponent;
import com.mojang.authlib.GameProfile;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_20_6.client.chat.ChatMessageMeta;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fyq.class })
public class MixinChatListener
{
    @Shadow
    @Final
    private ffh b;
    
    @Inject(method = { "handleSystemMessage" }, at = { @At("HEAD") })
    private void labyMod$storeSystemChatType(final xp message, final boolean overlay, final CallbackInfo ci) {
        if (overlay) {
            return;
        }
        this.chatComponent().setMessageMeta(message, ChatMessageMeta.system());
    }
    
    @Inject(method = { "handleSystemMessage" }, at = { @At("RETURN") })
    private void labyMod$clearSystemChatType(final xp message, final boolean overlay, final CallbackInfo ci) {
        this.chatComponent().clearMessageMeta(message);
    }
    
    @Inject(method = { "handleDisguisedChatMessage" }, at = { @At("HEAD") })
    private void labyMod$storeSystemChatType(final xp component, final xl.a chatType, final CallbackInfo ci) {
        this.chatComponent().setMessageMeta(component, ChatMessageMeta.system());
    }
    
    @Inject(method = { "handleDisguisedChatMessage" }, at = { @At("RETURN") })
    private void labyMod$clearSystemChatType(final xp component, final xl.a chatType, final CallbackInfo ci) {
        this.chatComponent().clearMessageMeta(component);
    }
    
    @Inject(method = { "handlePlayerChatMessage" }, at = { @At("HEAD") })
    private void labyMod$storeChatMessageChatType(final yf chatMessage, final GameProfile gameProfile, final xl.a chatType, final CallbackInfo ci) {
        final boolean onlyShowSecureChat = (boolean)this.b.m.ag().c();
        final yf msg = onlyShowSecureChat ? chatMessage.a() : chatMessage;
        this.chatComponent().setMessageMeta(chatType.a(msg.d()), ChatMessageMeta.player((gameProfile != null) ? gameProfile.getId() : null));
    }
    
    @Inject(method = { "handlePlayerChatMessage" }, at = { @At("RETURN") })
    private void labyMod$clearChatMessageChatType(final yf chatMessage, final GameProfile gameProfile, final xl.a chatType, final CallbackInfo ci) {
        final boolean onlyShowSecureChat = (boolean)this.b.m.ag().c();
        final yf msg = onlyShowSecureChat ? chatMessage.a() : chatMessage;
        this.chatComponent().clearMessageMeta(chatType.a(msg.d()));
    }
    
    private VersionedChatComponent chatComponent() {
        return (VersionedChatComponent)ffh.Q().l.d();
    }
}
