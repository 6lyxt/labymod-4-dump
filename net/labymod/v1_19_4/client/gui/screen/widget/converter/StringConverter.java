// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.List;
import net.labymod.core.client.accessor.gui.StringWidgetAccessor;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;

public class StringConverter extends AbstractMinecraftWidgetConverter<epc, ComponentWidget>
{
    public StringConverter() {
        super(MinecraftWidgetType.STRING);
    }
    
    @Override
    public ComponentWidget createDefault(final epc source) {
        final ComponentWidget destination = ComponentWidget.component(this.mapComponent(source.k()));
        destination.setFocused(source.aD_());
        this.copyBounds(source, destination);
        return destination;
    }
    
    @Override
    public void update(final epc source, final ComponentWidget destination) {
        destination.setFocused(source.aD_());
        destination.setComponent(this.mapComponent(source.k()));
        if (source instanceof final StringWidgetAccessor accessor) {
            if (accessor.isBasedOnTextWidth()) {
                source.d(emh.N().h.a((tn)source.k()));
            }
        }
        this.copyBounds(source, destination);
    }
    
    @Override
    public String getWidgetId(final epc source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.k());
    }
    
    @Override
    public List<String> getWidgetIds(final epc source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.k());
    }
}
