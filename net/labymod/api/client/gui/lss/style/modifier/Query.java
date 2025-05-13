// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style.modifier;

import net.labymod.api.client.gui.screen.widget.attributes.rules.media.MediaRule;
import net.labymod.api.client.gui.lss.style.reader.StyleRule;

public interface Query
{
    String identifier();
    
    boolean matches(final String p0);
    
    MediaRule process(final StyleRule p0);
}
