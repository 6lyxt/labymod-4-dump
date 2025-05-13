// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.test.other;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.activities.WorkInProgressActivity;
import java.util.HashMap;
import net.labymod.api.client.gui.screen.activity.Activity;
import java.util.function.Supplier;
import java.util.Map;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.core.test.TestActivity;

@AutoActivity
@Link("test/test-menu.lss")
public class ActivitiesTestActivity extends TestActivity
{
    private final Map<String, Supplier<Activity>> activities;
    
    public ActivitiesTestActivity() {
        (this.activities = new HashMap<String, Supplier<Activity>>()).put("WIP", () -> new WorkInProgressActivity("internal"));
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final VerticalListWidget<ButtonWidget> buttons = new VerticalListWidget<ButtonWidget>();
        buttons.addId("button-list");
        for (final Map.Entry<String, Supplier<Activity>> entry : this.activities.entrySet()) {
            final String key = entry.getKey();
            final ButtonWidget activityButton = ButtonWidget.text(key);
            ((AbstractWidget<Widget>)activityButton).addId("activity-button");
            activityButton.setPressable(() -> this.displayScreen(entry.getValue().get()));
            buttons.addChild(activityButton);
        }
        ((AbstractWidget<VerticalListWidget<ButtonWidget>>)super.document()).addChild(buttons);
    }
}
