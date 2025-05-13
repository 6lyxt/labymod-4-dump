// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.List;
import net.labymod.core.client.accessor.gui.StringWidgetAccessor;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;

public class StringConverter extends AbstractMinecraftWidgetConverter<fqb, ComponentWidget>
{
    public StringConverter() {
        super(MinecraftWidgetType.STRING);
    }
    
    @Override
    public ComponentWidget createDefault(final fqb source) {
        final ComponentWidget destination = ComponentWidget.component(this.mapComponent(source.B()));
        destination.setFocused(source.aM_());
        this.copyBounds(source, destination);
        return destination;
    }
    
    @Override
    public void update(final fqb source, final ComponentWidget destination) {
        destination.setFocused(source.aM_());
        destination.setComponent(this.mapComponent(source.B()));
        if (source instanceof final StringWidgetAccessor accessor) {
            if (accessor.isBasedOnTextWidth()) {
                source.h(flk.Q().h.a((wu)source.B()));
            }
        }
        this.copyBounds(source, destination);
    }
    
    @Override
    public String getWidgetId(final fqb source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.B());
    }
    
    @Override
    public List<String> getWidgetIds(final fqb source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.B());
    }
}
