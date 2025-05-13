// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.test.other;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.resources.pack.ResourcePackDetail;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.core.test.TestActivity;

@AutoActivity
@Link("test/test-menu.lss")
public class ResourcePackDetailTestActivity extends TestActivity
{
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final List<ResourcePackDetail> availablePacks = Laby.references().resourcePackRepository().getAvailablePackDetails();
        final VerticalListWidget<Widget> listWidget = new VerticalListWidget<Widget>();
        listWidget.addId("container-list");
        for (final ResourcePackDetail detail : availablePacks) {
            if (detail.isHidden()) {
                continue;
            }
            listWidget.addChild(this.createContainer(detail));
        }
        final ScrollWidget scrollWidget = new ScrollWidget(listWidget);
        ((AbstractWidget<ScrollWidget>)super.document()).addChild(scrollWidget);
    }
    
    private DivWidget createContainer(final ResourcePackDetail detail) {
        final DivWidget containerWidget = new DivWidget();
        containerWidget.addId("container");
        Component title = detail.getTitle();
        if (detail.isSelected()) {
            title = title.append(Component.text(" (")).append(Component.text("selected")).append(Component.text(")"));
        }
        final ComponentWidget titleComponent = ComponentWidget.component(title);
        titleComponent.addId("title");
        ((AbstractWidget<ComponentWidget>)containerWidget).addChild(titleComponent);
        final ComponentWidget descriptionComponent = ComponentWidget.component(detail.getDescription());
        descriptionComponent.addId("description");
        ((AbstractWidget<ComponentWidget>)containerWidget).addChild(descriptionComponent);
        final IconWidget originalPackIcon = new IconWidget(detail.getPackIcon());
        originalPackIcon.addId("original-pack-icon");
        ((AbstractWidget<IconWidget>)containerWidget).addChild(originalPackIcon);
        return containerWidget;
    }
}
