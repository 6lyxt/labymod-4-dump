// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.List;
import net.labymod.core.client.accessor.gui.StringWidgetAccessor;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;

public class StringConverter extends AbstractMinecraftWidgetConverter<fjt, ComponentWidget>
{
    public StringConverter() {
        super(MinecraftWidgetType.STRING);
    }
    
    @Override
    public ComponentWidget createDefault(final fjt source) {
        final ComponentWidget destination = ComponentWidget.component(this.mapComponent(source.z()));
        destination.setFocused(source.aO_());
        this.copyBounds(source, destination);
        return destination;
    }
    
    @Override
    public void update(final fjt source, final ComponentWidget destination) {
        destination.setFocused(source.aO_());
        destination.setComponent(this.mapComponent(source.z()));
        if (source instanceof final StringWidgetAccessor accessor) {
            if (accessor.isBasedOnTextWidth()) {
                source.k(fgo.Q().h.a((xe)source.z()));
            }
        }
        this.copyBounds(source, destination);
    }
    
    @Override
    public String getWidgetId(final fjt source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.z());
    }
    
    @Override
    public List<String> getWidgetIds(final fjt source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.z());
    }
}
