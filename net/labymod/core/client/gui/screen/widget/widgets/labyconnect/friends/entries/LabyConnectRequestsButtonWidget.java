// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.entries;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.core.client.gui.screen.activity.activities.labyconnect.desktop.sections.requests.LabyConnectIncomingRequestsActivity;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.gui.screen.ParentScreen;
import net.labymod.api.labyconnect.protocol.model.request.IncomingFriendRequest;
import java.util.List;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.LabyConnectEntryWidget;

@AutoWidget
public class LabyConnectRequestsButtonWidget extends LabyConnectEntryWidget
{
    private final ComponentWidget widgetName;
    private final List<IncomingFriendRequest> requests;
    
    public LabyConnectRequestsButtonWidget(final ParentScreen contentDisplay, final List<IncomingFriendRequest> requests) {
        super(contentDisplay);
        this.requests = requests;
        (this.widgetName = ComponentWidget.empty()).addId("text");
    }
    
    @Override
    public boolean onPress() {
        super.onPress();
        return false;
    }
    
    private void updateWidgetName(final int amount) {
        final Component component = Component.translatable("labymod.activity.labyconnect.friends.requests", new Component[0]).color(NamedTextColor.GRAY).args(((BaseComponent<Component>)Component.text(amount)).color(NamedTextColor.YELLOW));
        this.widgetName.setComponent(component);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.updateWidgetName(this.requests.size());
        final IconWidget widgetHead = new IconWidget(Textures.SpriteCommon.MULTIPLAYER);
        widgetHead.addId("icon");
        ((AbstractWidget<IconWidget>)this).addChild(widgetHead);
        ((AbstractWidget<ComponentWidget>)this).addChild(this.widgetName);
    }
    
    @Override
    public void select() {
        this.displayContentActivity(new LabyConnectIncomingRequestsActivity());
    }
    
    public void setAmount(final int size) {
        this.updateWidgetName(size);
    }
    
    @Override
    public int getSortingValue() {
        return Integer.MIN_VALUE;
    }
    
    @Override
    public int hashCode() {
        return 0;
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof LabyConnectRequestsButtonWidget;
    }
}
