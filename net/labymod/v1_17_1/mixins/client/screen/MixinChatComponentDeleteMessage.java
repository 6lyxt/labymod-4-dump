// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client.screen;

import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.client.chat.ChatMessage;
import java.util.Iterator;
import net.labymod.v1_17_1.client.chat.VersionedChatMessageComponent;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dxb.class })
public class MixinChatComponentDeleteMessage
{
    @Shadow
    @Final
    private List<dvk<os>> e;
    
    @Insert(method = { "removeById" }, at = @At("HEAD"))
    private void labyMod$deleteChatMessage(final int index, final InsertInfo callback) {
        for (final dvk<os> message : this.e) {
            if (message.c() == index) {
                final os lineComponent = (os)message.a();
                if (!(lineComponent instanceof VersionedChatMessageComponent)) {
                    continue;
                }
                final ChatMessage chatMessage = ((VersionedChatMessageComponent)lineComponent).message();
                if (!chatMessage.wasDeleted()) {
                    chatMessage.delete();
                    break;
                }
                break;
            }
        }
    }
}
