// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.AutoAlignType;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

public abstract class AbstractMinecraftWidgetConverter<T, K extends AbstractWidget<?>> extends AbstractWidgetConverter<T, K>
{
    protected static final ModifyReason COPY_MINECRAFT_BOUNDS;
    private final String name;
    
    public AbstractMinecraftWidgetConverter(final MinecraftWidgetType widgetType) {
        this.name = widgetType.toString();
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public boolean hasAutoBounds(final Widget child, final AutoAlignType type) {
        return true;
    }
    
    static {
        COPY_MINECRAFT_BOUNDS = ModifyReason.of("copyMinecraftBounds");
    }
}
