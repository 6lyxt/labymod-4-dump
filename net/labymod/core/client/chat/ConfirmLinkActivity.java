// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.chat;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import java.util.function.Consumer;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.activities.ConfirmActivity;

@Link("activity/confirm.lss")
@AutoActivity
public class ConfirmLinkActivity extends ConfirmActivity
{
    private static final TextColor WARNING_COLOR;
    private final Consumer<LinkType> responseConsumer;
    
    public ConfirmLinkActivity(final String link, final Consumer<LinkType> responseConsumer) {
        super(((BaseComponent<Component>)Component.translatable("chat.link.confirm", new Component[0]).append(Component.newline()).append(Component.newline()).append(Component.text(link)).append(Component.newline()).append(Component.newline())).append(Component.translatable("chat.link.warning", ConfirmLinkActivity.WARNING_COLOR)), Laby.labyAPI().minecraft().getTranslation("gui.yes"), Laby.labyAPI().minecraft().getTranslation("gui.no"), allow -> responseConsumer.accept((allow != null && allow) ? LinkType.OPEN : LinkType.NONE));
        this.responseConsumer = responseConsumer;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        super.buttonRow.addEntry(ButtonWidget.text(Laby.labyAPI().minecraft().getTranslation("chat.copy"), () -> {
            this.responseConsumer.accept(LinkType.COPY);
            this.displayPreviousScreen();
        }));
    }
    
    static {
        WARNING_COLOR = TextColor.color(255, 204, 204);
    }
    
    public enum LinkType
    {
        OPEN, 
        COPY, 
        NONE;
    }
}
