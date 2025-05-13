// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.tag;

import java.util.Locale;

public enum TagType
{
    MAIN_TAG, 
    SCOREBOARD, 
    CUSTOM;
    
    private static final TagType[] VALUES;
    private final String lowerCase;
    
    private TagType() {
        this.lowerCase = this.name().toLowerCase(Locale.ENGLISH);
    }
    
    @Override
    public String toString() {
        return this.lowerCase;
    }
    
    public static TagType fromString(final String string) {
        TagType tagType = TagType.CUSTOM;
        for (final TagType value : TagType.VALUES) {
            if (value.lowerCase.equals(string)) {
                tagType = value;
                break;
            }
        }
        return tagType;
    }
    
    static {
        VALUES = values();
    }
}
