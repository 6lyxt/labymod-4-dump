// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.game;

import net.labymod.api.tag.Tag;

public final class ScreenTags
{
    public static final Tag OPTIONS;
    public static final Tag ALLOW_CUSTOM_FONT;
    
    static {
        OPTIONS = Tag.of("labymod", "screen/options");
        ALLOW_CUSTOM_FONT = Tag.of("labymod", "screen/settings/allow_custom_font");
    }
}
