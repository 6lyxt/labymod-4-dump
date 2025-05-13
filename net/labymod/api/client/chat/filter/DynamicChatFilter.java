// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.chat.filter;

import java.util.regex.Pattern;
import net.labymod.api.client.gui.icon.Icon;
import java.util.UUID;

public class DynamicChatFilter
{
    private final UUID identifier;
    private final String name;
    private final byte priority;
    private final Icon largeIcon;
    private final Icon smallIcon;
    private final Pattern[] incomingMessagePatterns;
    private final String outgoingMessageFormat;
    
    public DynamicChatFilter(final UUID identifier, final String name, final byte priority, final Icon largeIcon, final Icon smallIcon, final Pattern[] incomingMessagePatterns, final String outgoingMessageFormat) {
        this.identifier = identifier;
        this.name = name;
        this.priority = priority;
        this.largeIcon = largeIcon;
        this.smallIcon = smallIcon;
        this.incomingMessagePatterns = incomingMessagePatterns;
        this.outgoingMessageFormat = outgoingMessageFormat;
    }
    
    public UUID identifier() {
        return this.identifier;
    }
    
    public String name() {
        return this.name;
    }
    
    public byte priority() {
        return this.priority;
    }
    
    public Icon largeIcon() {
        return this.largeIcon;
    }
    
    public Icon smallIcon() {
        return this.smallIcon;
    }
    
    public Pattern[] incomingMessagePatterns() {
        return this.incomingMessagePatterns;
    }
    
    public String outgoingMessageFormat() {
        return this.outgoingMessageFormat;
    }
}
