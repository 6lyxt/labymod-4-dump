// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.List;
import net.labymod.core.client.accessor.gui.StringWidgetAccessor;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;

public class StringConverter extends AbstractMinecraftWidgetConverter<fvf, ComponentWidget>
{
    public StringConverter() {
        super(MinecraftWidgetType.STRING);
    }
    
    @Override
    public ComponentWidget createDefault(final fvf source) {
        final ComponentWidget destination = ComponentWidget.component(this.mapComponent(source.B()));
        destination.setFocused(source.aJ_());
        this.copyBounds(source, destination);
        return destination;
    }
    
    @Override
    public void update(final fvf source, final ComponentWidget destination) {
        destination.setFocused(source.aJ_());
        destination.setComponent(this.mapComponent(source.B()));
        if (source instanceof final StringWidgetAccessor accessor) {
            if (accessor.isBasedOnTextWidth()) {
                source.h(fqq.Q().h.a((xl)source.B()));
            }
        }
        this.copyBounds(source, destination);
    }
    
    @Override
    public String getWidgetId(final fvf source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.B());
    }
    
    @Override
    public List<String> getWidgetIds(final fvf source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.B());
    }
}
