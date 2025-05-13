// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.screen;

import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.client.chat.ChatMessage;
import java.util.Iterator;
import net.labymod.v1_18_2.client.chat.VersionedChatMessageComponent;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ eaf.class })
public class MixinChatComponentDeleteMessage
{
    @Shadow
    @Final
    private List<dym<qk>> e;
    
    @Insert(method = { "removeById" }, at = @At("HEAD"))
    private void labyMod$deleteChatMessage(final int index, final InsertInfo callback) {
        for (final dym<qk> message : this.e) {
            if (message.c() == index) {
                final qk lineComponent = (qk)message.a();
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
