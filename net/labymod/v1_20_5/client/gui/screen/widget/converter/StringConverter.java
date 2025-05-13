// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.List;
import net.labymod.core.client.accessor.gui.StringWidgetAccessor;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;

public class StringConverter extends AbstractMinecraftWidgetConverter<fim, ComponentWidget>
{
    public StringConverter() {
        super(MinecraftWidgetType.STRING);
    }
    
    @Override
    public ComponentWidget createDefault(final fim source) {
        final ComponentWidget destination = ComponentWidget.component(this.mapComponent(source.y()));
        destination.setFocused(source.aH_());
        this.copyBounds(source, destination);
        return destination;
    }
    
    @Override
    public void update(final fim source, final ComponentWidget destination) {
        destination.setFocused(source.aH_());
        destination.setComponent(this.mapComponent(source.y()));
        if (source instanceof final StringWidgetAccessor accessor) {
            if (accessor.isBasedOnTextWidth()) {
                source.k(ffg.Q().h.a((xu)source.y()));
            }
        }
        this.copyBounds(source, destination);
    }
    
    @Override
    public String getWidgetId(final fim source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.y());
    }
    
    @Override
    public List<String> getWidgetIds(final fim source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.y());
    }
}
