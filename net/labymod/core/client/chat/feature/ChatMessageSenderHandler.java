// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.chat.feature;

import java.util.Iterator;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.component.Component;
import net.labymod.api.event.Subscribe;
import net.labymod.api.labynet.models.ServerChat;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.labynet.models.ServerGroup;
import net.labymod.api.event.client.chat.ChatMessageGuessSenderEvent;
import javax.inject.Inject;
import net.labymod.api.Laby;
import net.labymod.api.labynet.LabyNetController;
import net.labymod.api.LabyAPI;
import java.util.Map;
import javax.inject.Singleton;

@Singleton
public class ChatMessageSenderHandler
{
    private static final String VALID_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_";
    private static final Map<Character, String> SEPARATORS;
    private final LabyAPI labyAPI;
    private final LabyNetController labyNetController;
    
    @Inject
    public ChatMessageSenderHandler() {
        this.labyAPI = Laby.labyAPI();
        this.labyNetController = Laby.references().labyNetController();
    }
    
    @Subscribe(-127)
    public void guessSender(final ChatMessageGuessSenderEvent event) {
        if (event.getSenderProfile() != null) {
            return;
        }
        final ClientPacketListener clientPacketListener = this.labyAPI.minecraft().clientPacketListener();
        if (clientPacketListener == null) {
            return;
        }
        if (event.getSenderUniqueId() != null) {
            final NetworkPlayerInfo playerInfo = clientPacketListener.getNetworkPlayerInfo(event.getSenderUniqueId());
            if (playerInfo != null) {
                event.setSenderProfile(playerInfo.profile());
                return;
            }
        }
        final ServerGroup serverGroup = this.labyNetController.getCurrentServer().orElse(null);
        String senderName;
        if (serverGroup != null && serverGroup.getChat() != null && serverGroup.getChat().getMessageFormats() != null) {
            final ServerChat.ParsedMessage parsed = serverGroup.getChat().parseMessage(event.component());
            senderName = ((parsed != null) ? parsed.getSender() : null);
        }
        else {
            senderName = this.findSenderName(event.component(), event.getMessage());
        }
        if (senderName == null || senderName.isEmpty()) {
            return;
        }
        final NetworkPlayerInfo playerInfo2 = clientPacketListener.getNetworkPlayerInfo(senderName);
        if (playerInfo2 != null) {
            event.setSenderProfile(playerInfo2.profile());
        }
    }
    
    private String findSenderName(final Component component, final String message) {
        if (component instanceof final TranslatableComponent translatable) {
            if (translatable.getKey().equals("chat.type.text") && translatable.getArguments().size() == 2) {
                final Component nameComponent = translatable.getArguments().get(0);
                if (nameComponent instanceof final TextComponent textComponent) {
                    final String name = textComponent.content();
                    if (this.isValidSenderName(name)) {
                        return name;
                    }
                }
            }
        }
        for (final Map.Entry<Character, String> entry : ChatMessageSenderHandler.SEPARATORS.entrySet()) {
            final char separator = entry.getKey();
            final String name2 = this.extractName(message, separator, entry.getValue());
            if (name2 != null) {
                return name2;
            }
        }
        return null;
    }
    
    private String extractName(final String text, final char separator, final String ignoredChars) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.length(); ++i) {
            final char c = text.charAt(i);
            if (ignoredChars.indexOf(c) == -1) {
                if (c == ' ' && i < text.length() - 1 && text.charAt(i + 1) == separator) {
                    return builder.toString();
                }
                if (c == separator) {
                    return builder.toString();
                }
                if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_".indexOf(c) == -1) {
                    return null;
                }
                builder.append(c);
            }
        }
        return null;
    }
    
    private boolean isValidSenderName(final String name) {
        for (int i = 0; i < name.length(); ++i) {
            if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_".indexOf(name.charAt(i)) == -1) {
                return false;
            }
        }
        return true;
    }
    
    static {
        SEPARATORS = Map.of(':', "", '>', "<", '»', "");
    }
}
