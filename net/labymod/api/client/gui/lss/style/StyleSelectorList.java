// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style;

import net.labymod.api.client.gui.lss.style.modifier.attribute.StyleInstructions;
import java.util.Map;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.reader.StyleBlock;

public interface StyleSelectorList
{
    void add(final String p0, final StyleBlock p1);
    
    void applyToWidget(final Widget p0, final int p1);
    
    Map<String, StyleInstructions> generateAttributePatches(final Widget p0, final int p1);
}
