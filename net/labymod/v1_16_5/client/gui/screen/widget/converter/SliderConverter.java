// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.gui.screen.widget.converter;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.List;
import net.labymod.core.client.accessor.gui.SliderButtonAccessor;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;

public class SliderConverter extends AbstractMinecraftWidgetConverter<dlg, SliderWidget>
{
    public SliderConverter() {
        super(MinecraftWidgetType.SLIDER);
    }
    
    @Override
    public SliderWidget createDefault(final dlg source) {
        final SliderWidget destination = new SliderWidget().withFormatter(() -> this.mapComponent(source.i()));
        final SliderButtonAccessor accessor = (SliderButtonAccessor)source;
        destination.range(accessor.getMinValue(), accessor.getMaxValue());
        destination.steps(accessor.getSteps());
        this.copyBounds(source, destination);
        return destination;
    }
    
    @Override
    public void update(final dlg source, final SliderWidget destination) {
        destination.setVisible(source.p);
        this.copyBounds(source, destination);
        destination.setPercentage((float)((SliderButtonAccessor)source).getRawValue());
    }
    
    @Override
    public String getWidgetId(final dlg source) {
        return this.componentMapper.getTranslationKeyOfComponent(source.i());
    }
    
    @Override
    public List<String> getWidgetIds(final dlg source) {
        return this.componentMapper.getTranslationKeysOfComponent(source.i());
    }
}
