// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.window.debug.util;

import java.util.List;
import net.labymod.api.client.gui.screen.VanillaScreen;

public class VersionedDocument extends VersionedWidget
{
    private final VanillaScreen vanillaScreen;
    
    public VersionedDocument(final VanillaScreen vanillaScreen) {
        this.vanillaScreen = vanillaScreen;
    }
    
    @Override
    public List<VersionedWidget> getChildren() {
        return (List<VersionedWidget>)this.vanillaScreen.getWrappedWidgets();
    }
    
    @Override
    public String getIdentifier() {
        return "VersionedDocument";
    }
}
