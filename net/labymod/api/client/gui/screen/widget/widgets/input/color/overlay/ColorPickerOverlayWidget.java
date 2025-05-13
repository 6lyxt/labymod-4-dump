// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorData;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

@AutoWidget
public class ColorPickerOverlayWidget extends VerticalListWidget<Widget>
{
    private final ColorData colorData;
    
    public ColorPickerOverlayWidget(final ColorData colorData) {
        this.colorData = colorData;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        ((AbstractWidget<ColorPickerPlateWidget>)this).addChild(new ColorPickerPlateWidget(this.colorData));
        ((AbstractWidget<ColorPickerSelectorWidget>)this).addChild(new ColorPickerSelectorWidget(this.colorData));
        ((AbstractWidget<ColorPickerInputWidget>)this).addChild(new ColorPickerInputWidget(this.colorData));
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        super.mouseClicked(mouse, mouseButton);
        return true;
    }
}
