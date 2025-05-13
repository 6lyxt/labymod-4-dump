// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod;

import net.labymod.api.configuration.loader.ConfigAccessor;

public interface WidgetConfigAccessor extends ConfigAccessor
{
    int getLastSelectedColor();
    
    void setLastSelectedColor(final int p0);
}
