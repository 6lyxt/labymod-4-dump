// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.navigation.elements;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.navigation.NavigationWrapper;
import net.labymod.api.client.gui.navigation.NavigationElement;
import net.labymod.api.client.gui.screen.widget.Widget;

public abstract class AbstractNavigationElement<T extends Widget> implements NavigationElement<T>
{
    private NavigationWrapper navigation;
    
    public abstract Component getDisplayName();
    
    public abstract Icon getIcon();
    
    public AbstractNavigationElement() {
        Laby.labyAPI().eventBus().registerListener(this);
    }
    
    @Override
    public T createWidget(final NavigationWrapper wrapper) {
        final ButtonWidget button = ButtonWidget.component(this.getDisplayName(), this.getIcon());
        ((AbstractWidget<Widget>)button).addId("closed");
        return (T)button;
    }
    
    public void setNavigation(final NavigationWrapper navigation) {
        this.navigation = navigation;
    }
    
    public void updateWidget() {
        if (this.navigation == null) {
            return;
        }
        this.navigation.updateElement(this);
    }
}
