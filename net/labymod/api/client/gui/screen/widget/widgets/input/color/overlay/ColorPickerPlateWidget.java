// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.client.gui.screen.widget.widgets.layout.TilesGridWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorData;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;

@AutoWidget
public class ColorPickerPlateWidget extends FlexibleContentWidget
{
    private final ColorData colorData;
    private DivWidget previewWidget;
    
    protected ColorPickerPlateWidget(final ColorData colorData) {
        this.colorData = colorData;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final TilesGridWidget<DivWidget> tilesGridWidget = new TilesGridWidget<DivWidget>();
        for (final TextColor defaultColor : ColorUtil.DEFAULT_COLORS) {
            final DivWidget divColor = new DivWidget();
            final int backgroundColor = defaultColor.value() | 0xFF000000;
            divColor.backgroundColor().set(backgroundColor);
            divColor.setPressable(() -> this.colorData.setRgb(backgroundColor));
            tilesGridWidget.addTile(divColor);
        }
        this.addFlexibleContent(tilesGridWidget);
        final DivWidget previewWrapper = new DivWidget();
        previewWrapper.addId("preview-stencil-wrapper");
        (this.previewWidget = new DivWidget()).addId("preview");
        ((AbstractWidget<DivWidget>)previewWrapper).addChild(this.previewWidget);
        this.addContent(previewWrapper);
    }
    
    @Override
    public void render(final ScreenContext context) {
        if (this.previewWidget != null) {
            this.previewWidget.backgroundColor().set(this.colorData.getActualColor().get());
        }
        super.render(context);
    }
}
