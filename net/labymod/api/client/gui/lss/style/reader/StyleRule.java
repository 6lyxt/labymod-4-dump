// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style.reader;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.lss.style.StyleSheet;

public interface StyleRule
{
    @NotNull
    StyleSheet sourceStyleSheet();
    
    String getKey();
    
    String getValue();
    
    List<StyleBlock> getBlocks();
    
    Object process();
}
