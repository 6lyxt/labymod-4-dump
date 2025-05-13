// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.meta;

import net.labymod.api.client.gui.lss.style.StyleSheet;
import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface LinkMetaLoader
{
    void injectMeta(final Activity p0);
    
    void loadMeta(final Class<?> p0, final Consumer<StyleSheet> p1);
    
    void loadStyleSheets();
    
    LinkMetaList getMeta(final Class<?> p0);
}
