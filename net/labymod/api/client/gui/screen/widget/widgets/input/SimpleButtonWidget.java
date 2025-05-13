// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;

@AutoWidget
public class SimpleButtonWidget extends DivWidget
{
    private final ComponentWidget componentWidget;
    
    public SimpleButtonWidget(final String text) {
        this(ComponentWidget.text(text));
    }
    
    public SimpleButtonWidget(final Component component) {
        this(ComponentWidget.component(component));
    }
    
    public SimpleButtonWidget(final ComponentWidget componentWidget) {
        (this.componentWidget = componentWidget).addId("text");
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        ((AbstractWidget<ComponentWidget>)this).addChild(this.componentWidget);
    }
    
    @Override
    public void postStyleSheetLoad() {
        super.postStyleSheetLoad();
        if (this.componentWidget != null && this.componentWidget.renderable().isClipped() && !this.hasHoverComponent()) {
            this.setHoverComponent(this.componentWidget.component());
        }
    }
    
    @Override
    public boolean isHoverComponentRendered() {
        return this.hasHoverComponent() ? super.isHoverComponentRendered() : this.isHovered();
    }
}
