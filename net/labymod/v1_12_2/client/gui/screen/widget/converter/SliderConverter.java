// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.v1_12_2.client.gui.GuiSliderAccessor;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.converter.AbstractMinecraftWidgetConverter;

public class SliderConverter extends AbstractMinecraftWidgetConverter<bja, SliderWidget>
{
    public SliderConverter() {
        super(MinecraftWidgetType.SLIDER);
    }
    
    @Override
    public SliderWidget createDefault(final bja source) {
        final SliderWidget destination = new SliderWidget().withFormatter(() -> Component.text(source.j));
        destination.range(((GuiSliderAccessor)source).getMinValue(), ((GuiSliderAccessor)source).getMaxValue());
        destination.steps(((GuiSliderAccessor)source).getStep());
        this.copyBounds(source, destination);
        return destination;
    }
    
    @Override
    public void update(final bja source, final SliderWidget destination) {
        destination.setVisible(source.m);
        this.copyBounds(source, destination);
        destination.setDragging(((GuiSliderAccessor)source).isDragging());
        destination.setValue(((GuiSliderAccessor)source).getValue());
    }
    
    @Override
    public String getWidgetId(final bja source) {
        return String.valueOf(source.k);
    }
}
