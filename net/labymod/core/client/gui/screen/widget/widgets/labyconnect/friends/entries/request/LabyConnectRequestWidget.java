// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.entries.request;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ParentScreen;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.LabyConnectEntryWidget;
import net.labymod.api.labyconnect.protocol.model.User;

@AutoWidget
public abstract class LabyConnectRequestWidget<T extends User> extends LabyConnectEntryWidget
{
    protected final T request;
    
    public LabyConnectRequestWidget(final ParentScreen contentDisplay, final T request) {
        super(contentDisplay);
        this.request = request;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final DivWidget container = new DivWidget();
        final IconWidget widgetHead = new IconWidget(Icon.head(this.request.getUniqueId()));
        widgetHead.addId("icon");
        ((AbstractWidget<IconWidget>)container).addChild(widgetHead);
        final ComponentWidget widgetName = ComponentWidget.text(this.request.getName());
        widgetName.addId("text");
        ((AbstractWidget<ComponentWidget>)container).addChild(widgetName);
        this.initializeButtons(container);
        this.addChild(container);
    }
    
    @Override
    public void select() {
    }
    
    protected abstract void initializeButtons(final DivWidget p0);
    
    @Override
    public int getSortingValue() {
        return 0;
    }
    
    public T getRequest() {
        return this.request;
    }
}
