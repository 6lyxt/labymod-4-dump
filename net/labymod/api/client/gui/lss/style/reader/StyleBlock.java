// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style.reader;

import java.util.Map;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.lss.style.StyleSelectorList;

public interface StyleBlock extends StyleSelectorList
{
    StyleSheet styleSheet();
    
    Map<String, SingleInstruction> getInstructions();
    
    void put(final SingleInstruction p0);
    
    void setRawSelector(final int p0, final String p1);
    
    String getRawSelector();
    
    int getDepth();
    
    int getLineOf(final String p0);
}
