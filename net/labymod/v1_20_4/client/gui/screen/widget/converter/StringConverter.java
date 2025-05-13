// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.List;
import net.labymod.core.client.accessor.gui.StringWidgetAccessor;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;

public class StringConverter extends AbstractMinecraftWidgetConverter<eyn, ComponentWidget>
{
    public StringConverter() {
        super(MinecraftWidgetType.STRING);
    }
    
    @Override
    public ComponentWidget createDefault(final eyn source) {
        final ComponentWidget destination = ComponentWidget.component(this.mapComponent(source.x()));
        destination.setFocused(source.aI_());
        this.copyBounds(source, destination);
        return destination;
    }
    
    @Override
    public void update(final eyn source, final ComponentWidget destination) {
        destination.setFocused(source.aI_());
        destination.setComponent(this.mapComponent(source.x()));
        if (source instanceof final StringWidgetAccessor accessor) {
            if (accessor.isBasedOnTextWidth()) {
                source.l(evi.O().h.a((vk)source.x()));
            }
        }
        this.copyBounds(source, destination);
    }
    
    @Override
    public String getWidgetId(final eyn source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.x());
    }
    
    @Override
    public List<String> getWidgetIds(final eyn source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.x());
    }
}
