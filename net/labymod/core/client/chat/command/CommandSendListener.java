// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.chat.command;

import net.labymod.api.event.Subscribe;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.client.gui.screen.key.KeyHandler;
import net.labymod.api.event.client.chat.ChatMessageSendEvent;

public class CommandSendListener
{
    private final DefaultCommandService commandService;
    
    protected CommandSendListener(final DefaultCommandService commandService) {
        this.commandService = commandService;
    }
    
    @Subscribe(Byte.MAX_VALUE)
    public void onCommand(final ChatMessageSendEvent event) {
        if (!event.isMessageCommand()) {
            return;
        }
        if (KeyHandler.isShiftDown()) {
            return;
        }
        final String[] message = event.getMessage().substring(1).split(" ");
        final String[] arguments = new String[message.length - 1];
        if (message.length > 1) {
            CollectionHelper.copyOfRange(message, arguments, 1, message.length);
        }
        if (this.commandService.fireCommand(message[0], arguments)) {
            event.setCancelled(true);
        }
    }
}
