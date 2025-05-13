// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.client.gui.screen.widget.converter;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.List;
import net.labymod.core.client.accessor.gui.SliderButtonAccessor;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;

public class SliderConverter extends AbstractMinecraftWidgetConverter<fhb, SliderWidget>
{
    public SliderConverter() {
        super(MinecraftWidgetType.SLIDER);
    }
    
    @Override
    public SliderWidget createDefault(final fhb source) {
        final SliderWidget destination = new SliderWidget().withFormatter(() -> this.mapComponent(source.y()));
        final SliderButtonAccessor accessor = (SliderButtonAccessor)source;
        destination.range(accessor.getMinValue(), accessor.getMaxValue());
        destination.steps(accessor.getSteps());
        this.copyBounds(source, destination);
        return destination;
    }
    
    @Override
    public void update(final fhb source, final SliderWidget destination) {
        destination.setVisible(source.k);
        this.copyBounds(source, destination);
        destination.setPercentage((float)((SliderButtonAccessor)source).getRawValue());
    }
    
    @Override
    public String getWidgetId(final fhb source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.y());
    }
    
    @Override
    public List<String> getWidgetIds(final fhb source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.y());
    }
}
