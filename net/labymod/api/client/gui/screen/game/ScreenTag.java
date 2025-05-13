// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.game;

import java.util.HashMap;
import net.labymod.api.tag.Tag;
import java.util.Map;

@Deprecated(forRemoval = true, since = "4.1.12")
public final class ScreenTag
{
    private static final Map<String, ScreenTag> INDEX;
    public static final ScreenTag OPTIONS;
    private final String identifier;
    private final Tag tag;
    
    private ScreenTag(final String identifier, final Tag tag) {
        this.identifier = identifier;
        this.tag = tag;
    }
    
    public Tag getTag() {
        return this.tag;
    }
    
    public String getIdentifier() {
        return this.identifier;
    }
    
    public static ScreenTag of(final String identifier) {
        return of(identifier, Tag.ofInternal(identifier));
    }
    
    public static ScreenTag of(final String identifier, final Tag tag) {
        return ScreenTag.INDEX.computeIfAbsent(identifier, id -> new ScreenTag(id, tag));
    }
    
    static {
        INDEX = new HashMap<String, ScreenTag>();
        OPTIONS = of("OPTIONS");
    }
}
