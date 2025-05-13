// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labyconnect.desktop;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ParentScreen;
import net.labymod.core.client.gui.screen.activity.activities.labyconnect.desktop.sections.LabyConnectEmptyActivity;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.core.client.gui.screen.activity.activities.labyconnect.desktop.sections.LabyConnectFriendsActivity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;

@Link("activity/labyconnect/laby-connect-desktop.lss")
@AutoActivity
public class LabyConnectDesktopActivity extends SimpleActivity
{
    private final LabyConnectFriendsActivity friendsActivity;
    private ScreenInstance contentActivity;
    private final ScreenRendererWidget widgetFriends;
    private final ScreenRendererWidget widgetContent;
    
    public LabyConnectDesktopActivity() {
        this.friendsActivity = new LabyConnectFriendsActivity();
        this.contentActivity = new LabyConnectEmptyActivity();
        (this.widgetFriends = new ScreenRendererWidget()).addId("friends-container");
        this.widgetFriends.setPreviousScreenHandler(screen -> true);
        (this.widgetContent = new ScreenRendererWidget()).addId("content-container");
        this.widgetContent.addPostDisplayListener(screenInstance -> this.contentActivity = screenInstance);
        this.friendsActivity.setContentDisplay(this.widgetContent);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final FlexibleContentWidget containerDesktop = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)containerDesktop).addId("desktop-container");
        containerDesktop.addContent(this.widgetFriends);
        containerDesktop.addContent(new DivWidget().addId("split"));
        containerDesktop.addFlexibleContent(this.widgetContent);
        this.widgetFriends.displayScreen(this.friendsActivity);
        this.widgetContent.displayScreen(this.contentActivity);
        ((AbstractWidget<FlexibleContentWidget>)this.document).addChild(containerDesktop);
    }
    
    @Override
    public boolean shouldRenderBackground() {
        return false;
    }
    
    @Override
    public boolean displayPreviousScreen() {
        return false;
    }
    
    public LabyConnectFriendsActivity friendsActivity() {
        return this.friendsActivity;
    }
}
