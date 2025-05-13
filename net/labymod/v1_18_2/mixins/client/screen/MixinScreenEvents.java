// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.screen;

import org.spongepowered.asm.mixin.Overwrite;
import net.labymod.api.Laby;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ edw.class })
public class MixinScreenEvents
{
    @Shadow
    @Nullable
    protected dyr e;
    
    @Overwrite
    public void b(final String message, final boolean showInHistory) {
        final ChatMessageSendEvent event = Laby.fireEvent(new ChatMessageSendEvent(message, showInHistory));
        if (event.getHistoryText() != null && !event.getHistoryText().trim().isEmpty()) {
            this.e.k.c().a(event.getHistoryText());
        }
        if (event.isCancelled() || event.getMessage() == null || event.getMessage().trim().isEmpty()) {
            return;
        }
        this.e.s.e(event.getMessage());
    }
}
