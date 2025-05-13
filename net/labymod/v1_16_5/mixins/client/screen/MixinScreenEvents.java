// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.screen;

import org.spongepowered.asm.mixin.Overwrite;
import net.labymod.api.Laby;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dot.class })
public class MixinScreenEvents
{
    @Shadow
    @Nullable
    protected djz i;
    
    @Overwrite
    public void b(final String message, final boolean showInHistory) {
        final ChatMessageSendEvent event = Laby.fireEvent(new ChatMessageSendEvent(message, showInHistory));
        if (event.getHistoryText() != null && !event.getHistoryText().trim().isEmpty()) {
            this.i.j.c().a(event.getHistoryText());
        }
        if (event.isCancelled() || event.getMessage() == null || event.getMessage().trim().isEmpty()) {
            return;
        }
        this.i.s.f(event.getMessage());
    }
}
