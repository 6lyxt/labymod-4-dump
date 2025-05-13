// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.List;
import net.labymod.core.client.accessor.gui.StringWidgetAccessor;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;

public class StringConverter extends AbstractMinecraftWidgetConverter<etw, ComponentWidget>
{
    public StringConverter() {
        super(MinecraftWidgetType.STRING);
    }
    
    @Override
    public ComponentWidget createDefault(final etw source) {
        final ComponentWidget destination = ComponentWidget.component(this.mapComponent(source.m()));
        destination.setFocused(source.aC_());
        this.copyBounds(source, destination);
        return destination;
    }
    
    @Override
    public void update(final etw source, final ComponentWidget destination) {
        destination.setFocused(source.aC_());
        destination.setComponent(this.mapComponent(source.m()));
        if (source instanceof final StringWidgetAccessor accessor) {
            if (accessor.isBasedOnTextWidth()) {
                source.d(eqv.O().h.a((tp)source.m()));
            }
        }
        this.copyBounds(source, destination);
    }
    
    @Override
    public String getWidgetId(final etw source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.m());
    }
    
    @Override
    public List<String> getWidgetIds(final etw source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.m());
    }
}
