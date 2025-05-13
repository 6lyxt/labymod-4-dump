// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.List;
import net.labymod.core.client.accessor.gui.StringWidgetAccessor;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;

public class StringConverter extends AbstractMinecraftWidgetConverter<eqk, ComponentWidget>
{
    public StringConverter() {
        super(MinecraftWidgetType.STRING);
    }
    
    @Override
    public ComponentWidget createDefault(final eqk source) {
        final ComponentWidget destination = ComponentWidget.component(this.mapComponent(source.l()));
        destination.setFocused(source.aB_());
        this.copyBounds(source, destination);
        return destination;
    }
    
    @Override
    public void update(final eqk source, final ComponentWidget destination) {
        destination.setFocused(source.aB_());
        destination.setComponent(this.mapComponent(source.l()));
        if (source instanceof final StringWidgetAccessor accessor) {
            if (accessor.isBasedOnTextWidth()) {
                source.d(enn.N().h.a((ta)source.l()));
            }
        }
        this.copyBounds(source, destination);
    }
    
    @Override
    public String getWidgetId(final eqk source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.l());
    }
    
    @Override
    public List<String> getWidgetIds(final eqk source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.l());
    }
}
