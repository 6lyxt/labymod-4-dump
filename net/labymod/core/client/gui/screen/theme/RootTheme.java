// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme;

import net.labymod.api.client.gui.screen.theme.ThemeConfig;
import net.labymod.api.client.gui.screen.theme.AbstractTheme;

public abstract class RootTheme extends AbstractTheme
{
    public RootTheme(final String name) {
        super(name);
    }
    
    public RootTheme(final String name, final Class<? extends ThemeConfig> configClass) {
        super(name, configClass);
    }
}
