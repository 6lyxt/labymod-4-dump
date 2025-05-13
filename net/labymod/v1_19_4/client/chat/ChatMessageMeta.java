// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.client.chat;

import java.util.UUID;
import net.labymod.api.client.options.ChatVisibility;

public class ChatMessageMeta
{
    private static final ChatMessageMeta SYSTEM;
    private final ChatVisibility visibility;
    private final UUID sender;
    
    private ChatMessageMeta(final ChatVisibility visibility, final UUID sender) {
        this.visibility = visibility;
        this.sender = sender;
    }
    
    public static ChatMessageMeta system() {
        return ChatMessageMeta.SYSTEM;
    }
    
    public static ChatMessageMeta player(final UUID sender) {
        return new ChatMessageMeta(ChatVisibility.SHOWN, sender);
    }
    
    public ChatVisibility visibility() {
        return this.visibility;
    }
    
    public UUID getSender() {
        return this.sender;
    }
    
    static {
        SYSTEM = new ChatMessageMeta(ChatVisibility.COMMANDS_ONLY, null);
    }
}
