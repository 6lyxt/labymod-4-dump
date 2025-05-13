// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss;

import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.screen.theme.ThemeFile;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface StyleSheetLoader
{
    void invalidate();
    
    StyleSheet load(final ThemeFile p0);
}
