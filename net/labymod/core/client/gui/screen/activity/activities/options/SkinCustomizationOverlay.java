// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.options;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.concurrent.TimeUnit;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.Laby;
import net.labymod.core.client.gui.screen.activity.activities.NavigationActivity;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.AbstractLayerActivity;

@Link("activity/player/skin-customization.lss")
@AutoActivity
public class SkinCustomizationOverlay extends AbstractLayerActivity
{
    public SkinCustomizationOverlay(final ScreenInstance parentScreen) {
        super(parentScreen);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final ButtonWidget refreshButton = createRefreshButton();
        ((AbstractWidget<Widget>)refreshButton).addId("labymod-refresh-button");
        ((AbstractWidget<ButtonWidget>)this.document).addChild(refreshButton);
    }
    
    public static ButtonWidget createRefreshButton() {
        final ButtonWidget widget = ButtonWidget.component(Component.translatable("labymod.activity.customization.refresh", new Component[0]));
        if (Laby.labyAPI().minecraft().minecraftWindow().currentLabyScreen() instanceof NavigationActivity) {
            ((AbstractWidget<Widget>)widget).addId("in-navigation");
        }
        else {
            ((AbstractWidget<Widget>)widget).addId("standalone");
        }
        widget.setActionListener(() -> {
            widget.setEnabled(false);
            Laby.labyAPI().refresh();
            Task.builder(() -> widget.setEnabled(true)).delay(2L, TimeUnit.SECONDS).build().executeOnRenderThread();
            return;
        });
        return widget;
    }
}
