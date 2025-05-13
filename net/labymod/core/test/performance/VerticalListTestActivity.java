// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.test.performance;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.core.test.TestActivity;

@Link("test/test-menu.lss")
@AutoActivity
public class VerticalListTestActivity extends TestActivity
{
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final VerticalListWidget<Widget> listWidget = new VerticalListWidget<Widget>();
        listWidget.addId("vertical-container");
        for (int entries = 250, index = 0; index < entries; ++index) {
            final DivWidget widget = new DivWidget();
            listWidget.addChild(widget, false);
        }
        final ScrollWidget scrollWidget = new ScrollWidget(listWidget);
        ((AbstractWidget<ScrollWidget>)this.document).addChild(scrollWidget);
    }
}
