// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labyconnect.desktop.sections;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Activity;

@Link("activity/labyconnect/laby-connect-empty.lss")
@AutoActivity
public class LabyConnectEmptyActivity extends Activity
{
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final DivWidget container = new DivWidget();
        container.addId("background");
        ((AbstractWidget<DivWidget>)this.document).addChild(container);
    }
}
