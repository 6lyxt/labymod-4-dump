// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay;

import net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.selector.HueSelectorWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.selector.AlphaSelectorWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.selector.ShadeSelectorWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorData;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class ColorPickerSelectorWidget extends AbstractWidget<Widget>
{
    private final ColorData colorData;
    
    protected ColorPickerSelectorWidget(final ColorData colorData) {
        this.colorData = colorData;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final DivWidget shadeWrapper = new DivWidget();
        shadeWrapper.addId("stencil-wrapper", "shade-stencil-wrapper");
        shadeWrapper.addChild(new ShadeSelectorWidget(this.colorData).addId("color-selector"));
        ((AbstractWidget<DivWidget>)this).addChild(shadeWrapper);
        if (this.colorData.enabledAlpha()) {
            final DivWidget alphaWrapper = new DivWidget();
            alphaWrapper.addId("stencil-wrapper", "alpha-stencil-wrapper");
            alphaWrapper.addChild(new AlphaSelectorWidget(this.colorData).addId("color-selector"));
            ((AbstractWidget<DivWidget>)this).addChild(alphaWrapper);
            this.addId("with-alpha");
        }
        final DivWidget hueWrapper = new DivWidget();
        hueWrapper.addId("stencil-wrapper", "hue-stencil-wrapper");
        hueWrapper.addChild(new HueSelectorWidget(this.colorData).addId("color-selector"));
        ((AbstractWidget<DivWidget>)this).addChild(hueWrapper);
    }
}
