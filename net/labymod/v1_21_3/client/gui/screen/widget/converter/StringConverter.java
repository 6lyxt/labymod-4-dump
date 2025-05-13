// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.List;
import net.labymod.core.client.accessor.gui.StringWidgetAccessor;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;

public class StringConverter extends AbstractMinecraftWidgetConverter<fpn, ComponentWidget>
{
    public StringConverter() {
        super(MinecraftWidgetType.STRING);
    }
    
    @Override
    public ComponentWidget createDefault(final fpn source) {
        final ComponentWidget destination = ComponentWidget.component(this.mapComponent(source.z()));
        destination.setFocused(source.aN_());
        this.copyBounds(source, destination);
        return destination;
    }
    
    @Override
    public void update(final fpn source, final ComponentWidget destination) {
        destination.setFocused(source.aN_());
        destination.setComponent(this.mapComponent(source.z()));
        if (source instanceof final StringWidgetAccessor accessor) {
            if (accessor.isBasedOnTextWidth()) {
                source.i(fmg.Q().h.a((ya)source.z()));
            }
        }
        this.copyBounds(source, destination);
    }
    
    @Override
    public String getWidgetId(final fpn source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.z());
    }
    
    @Override
    public List<String> getWidgetIds(final fpn source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.z());
    }
}
