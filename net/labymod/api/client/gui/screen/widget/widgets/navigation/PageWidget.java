// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.navigation;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class PageWidget extends AbstractWidget<ButtonWidget>
{
    private final Component component;
    private final ScreenInstance screen;
    private final ButtonWidget button;
    
    private PageWidget(final Component component, final ScreenInstance screen) {
        this.component = component;
        this.screen = screen;
        this.button = ButtonWidget.component(this.component);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.addChild(this.button);
        this.setActive(this.isActive());
    }
    
    public ScreenInstance getScreen() {
        return this.screen;
    }
    
    @Override
    public float getContentWidth(final BoundsType type) {
        float max = 0.0f;
        for (final Widget child : this.children) {
            max = Math.max(child.bounds().getWidth(type), max);
        }
        return Math.max(max, this.getDefaultContentWidth(type));
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        float max = 0.0f;
        for (final Widget child : this.children) {
            max = Math.max(child.bounds().getHeight(type), max);
        }
        return Math.max(max, this.getDefaultContentHeight(type));
    }
    
    @Override
    public void setActive(final boolean active) {
        super.setActive(active);
        this.button.setEnabled(!active);
        for (final ButtonWidget child : this.getGenericChildren()) {
            child.setActive(active);
            child.setEnabled(!active);
        }
    }
    
    public static PageWidget i18n(final String key, final ScreenInstance screen) {
        return component(Component.translatable(key, new Component[0]), screen);
    }
    
    public static PageWidget text(final String string, final ScreenInstance screen) {
        return component(Component.text(string), screen);
    }
    
    public static PageWidget component(final Component component, final ScreenInstance screen) {
        return new PageWidget(component, screen);
    }
}
