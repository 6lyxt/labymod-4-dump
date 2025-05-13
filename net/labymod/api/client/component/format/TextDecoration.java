// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component.format;

import java.util.List;

public enum TextDecoration
{
    OBFUSCATED, 
    BOLD, 
    STRIKETHROUGH, 
    UNDERLINED, 
    ITALIC;
    
    private static final List<TextDecoration> VALUES;
    
    public static List<TextDecoration> getValues() {
        return TextDecoration.VALUES;
    }
    
    static {
        VALUES = List.of(values());
    }
    
    @Deprecated
    public enum State
    {
        NOT_SET, 
        TRUE, 
        FALSE;
    }
}
