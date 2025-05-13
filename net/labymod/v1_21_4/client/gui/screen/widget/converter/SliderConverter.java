// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.gui.screen.widget.converter;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.List;
import net.labymod.core.client.accessor.gui.SliderButtonAccessor;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;

public class SliderConverter extends AbstractMinecraftWidgetConverter<foo, SliderWidget>
{
    public SliderConverter() {
        super(MinecraftWidgetType.SLIDER);
    }
    
    @Override
    public SliderWidget createDefault(final foo source) {
        final SliderWidget destination = new SliderWidget().withFormatter(() -> this.mapComponent(source.B()));
        final SliderButtonAccessor accessor = (SliderButtonAccessor)source;
        destination.range(accessor.getMinValue(), accessor.getMaxValue());
        destination.steps(accessor.getSteps());
        this.copyBounds(source, destination);
        return destination;
    }
    
    @Override
    public void update(final foo source, final SliderWidget destination) {
        destination.setVisible(source.k);
        this.copyBounds(source, destination);
        destination.setPercentage((float)((SliderButtonAccessor)source).getRawValue());
    }
    
    @Override
    public String getWidgetId(final foo source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.B());
    }
    
    @Override
    public List<String> getWidgetIds(final foo source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.B());
    }
}
