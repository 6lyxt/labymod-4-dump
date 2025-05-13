// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.navigation.elements;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.navigation.NavigationWrapper;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;

public abstract class ScreenNavigationElement extends ScreenBaseNavigationElement<ButtonWidget>
{
    protected ScreenNavigationElement(final ScreenInstance screen) {
        super(screen);
    }
    
    @Override
    public ButtonWidget createWidget(final NavigationWrapper wrapper) {
        final ButtonWidget widget = super.createWidget(wrapper);
        if (wrapper.isActive(this)) {
            ((AbstractWidget<Widget>)widget).addId("open");
            widget.removeId("closed");
            widget.setEnabled(false);
        }
        else {
            widget.setPressable(() -> wrapper.displayScreen(this));
        }
        return widget;
    }
}
