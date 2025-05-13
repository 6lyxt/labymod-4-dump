// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.multiplayer.chat;

import net.labymod.v1_19_3.client.chat.VersionedChatComponent;
import com.mojang.authlib.GameProfile;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_19_3.client.chat.ChatMessageMeta;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ezj.class })
public class MixinChatListener
{
    @Shadow
    @Final
    private ejf a;
    
    @Inject(method = { "handleSystemMessage" }, at = { @At("HEAD") })
    private void labyMod$storeSystemChatType(final ss message, final boolean overlay, final CallbackInfo ci) {
        if (overlay) {
            return;
        }
        this.chatComponent().setMessageMeta(message, ChatMessageMeta.system());
    }
    
    @Inject(method = { "handleSystemMessage" }, at = { @At("RETURN") })
    private void labyMod$clearSystemChatType(final ss message, final boolean overlay, final CallbackInfo ci) {
        this.chatComponent().clearMessageMeta(message);
    }
    
    @Inject(method = { "handleDisguisedChatMessage" }, at = { @At("HEAD") })
    private void labyMod$storeSystemChatType(final ss component, final so.a chatType, final CallbackInfo ci) {
        this.chatComponent().setMessageMeta(component, ChatMessageMeta.system());
    }
    
    @Inject(method = { "handleDisguisedChatMessage" }, at = { @At("RETURN") })
    private void labyMod$clearSystemChatType(final ss component, final so.a chatType, final CallbackInfo ci) {
        this.chatComponent().clearMessageMeta(component);
    }
    
    @Inject(method = { "handlePlayerChatMessage" }, at = { @At("HEAD") })
    private void labyMod$storeChatMessageChatType(final th chatMessage, final GameProfile gameProfile, final so.a chatType, final CallbackInfo ci) {
        final boolean onlyShowSecureChat = (boolean)this.a.m.Z().c();
        final th msg = onlyShowSecureChat ? chatMessage.a() : chatMessage;
        this.chatComponent().setMessageMeta(chatType.a(msg.c()), ChatMessageMeta.player((gameProfile != null) ? gameProfile.getId() : null));
    }
    
    @Inject(method = { "handlePlayerChatMessage" }, at = { @At("RETURN") })
    private void labyMod$clearChatMessageChatType(final th chatMessage, final GameProfile gameProfile, final so.a chatType, final CallbackInfo ci) {
        final boolean onlyShowSecureChat = (boolean)this.a.m.Z().c();
        final th msg = onlyShowSecureChat ? chatMessage.a() : chatMessage;
        this.chatComponent().clearMessageMeta(chatType.a(msg.c()));
    }
    
    private VersionedChatComponent chatComponent() {
        return (VersionedChatComponent)ejf.N().l.d();
    }
}
