// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.test;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.notification.Notification;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.AutoActivity;

@AutoActivity
@Link("test/context-menu-test.lss")
public class ContextMenuTestActivity extends TestActivity
{
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final ButtonWidget button1 = ButtonWidget.text("Click here");
        final ButtonWidget button2 = ButtonWidget.text("Click here");
        this.fillContextMenu(button1.createContextMenu());
        this.fillContextMenu(button2.createContextMenu());
        final ButtonWidget buttonWidget = button1;
        final ButtonWidget obj = button1;
        Objects.requireNonNull(obj);
        buttonWidget.setPressable(obj::openContextMenu);
        final ButtonWidget buttonWidget2 = button2;
        final ButtonWidget obj2 = button2;
        Objects.requireNonNull(obj2);
        buttonWidget2.setPressable(obj2::openContextMenu);
        ((AbstractWidget<ButtonWidget>)this.document).addChild(button1).addId("button-1");
        ((AbstractWidget<ButtonWidget>)this.document).addChild(button2).addId("button-2");
    }
    
    private void fillContextMenu(final ContextMenu contextMenu) {
        contextMenu.addEntry(this.createNotificationEntry("Show notification and keep context menu open", false));
        contextMenu.addEntry(this.createNotificationEntry("Show notification and close context menu", true));
        contextMenu.addEntry(ContextMenuEntry.builder().text(Component.text("Open sub menu")).icon(Textures.SpriteCommon.ARROW_RIGHT).subMenu(() -> {
            final ContextMenu subMenu = new ContextMenu();
            this.fillContextMenu(subMenu);
            return subMenu;
        }).build());
    }
    
    private ContextMenuEntry createNotificationEntry(final String text, final boolean close) {
        return ContextMenuEntry.builder().text(Component.text(text)).icon(Textures.SpriteCommon.PEPE_SAD).clickHandler(entry -> {
            Laby.references().notificationController().push(Notification.builder().title(Component.text("Test notification")).text(Component.text("Test notification")).icon(Textures.SpriteLabyMod.DEFAULT_WOLF_SHARP).duration(5000L).build());
            return close;
        }).build();
    }
}
