// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.theme;

import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ThemeRendererParser
{
     <T extends Widget> ThemeRenderer<T> parse(final String p0);
}
