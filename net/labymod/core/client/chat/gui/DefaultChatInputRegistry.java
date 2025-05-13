// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.chat.gui;

import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.KeyValue;
import javax.inject.Inject;
import net.labymod.api.configuration.labymod.main.laby.ingame.ChatInputConfig;
import net.labymod.api.client.gui.screen.activity.types.chatinput.ChatInputTabActivity;
import java.util.function.Supplier;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.tab.AutoTextActivity;
import net.labymod.api.Textures;
import net.labymod.api.Laby;
import net.labymod.core.client.chat.gui.listener.ChatButtonPermissionListener;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.tab.NameHistoryActivity;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.tab.ChatSymbolActivity;
import net.labymod.api.event.EventBus;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.chat.input.ChatInputRegistry;
import net.labymod.api.client.gui.screen.widget.widgets.activity.chat.ChatButtonWidget;
import net.labymod.api.service.DefaultRegistry;

@Singleton
@Implements(ChatInputRegistry.class)
public class DefaultChatInputRegistry extends DefaultRegistry<ChatButtonWidget> implements ChatInputRegistry
{
    private static final int OFFSET = 1;
    private Runnable chatWidthRunnable;
    
    @Inject
    public DefaultChatInputRegistry(final EventBus eventBus, final ChatSymbolActivity chatSymbolActivity, final NameHistoryActivity nameHistoryActivity) {
        eventBus.registerListener(new ChatButtonPermissionListener(this));
        final ChatInputConfig chatInputConfig = Laby.labyAPI().config().ingame().chatInput();
        this.register(ChatButtonWidget.icon("namehistory", Textures.SpriteCommon.BOOK, () -> nameHistoryActivity).property(chatInputConfig.nameHistory()));
        this.register(ChatButtonWidget.icon("symbol", Textures.SpriteCommon.SYMBOLS, () -> chatSymbolActivity).property(chatInputConfig.chatSymbols()));
        this.register(ChatButtonWidget.icon("autotext", Textures.SpriteCommon.ROBOT, (Supplier<ChatInputTabActivity<?>>)AutoTextActivity::new).property(chatInputConfig.autoText()).setPermissionId("chat_autotext"));
    }
    
    @Override
    public float getButtonWidth() {
        float width = 0.0f;
        for (final KeyValue<ChatButtonWidget> button : this.getElements()) {
            width += button.getValue().bounds().getWidth(BoundsType.OUTER) + 1.0f;
        }
        return width + 1.0f;
    }
    
    public Runnable getChatWidthRunnable() {
        return this.chatWidthRunnable;
    }
    
    public void setChatWidthRunnable(final Runnable chatWidthRunnable) {
        this.chatWidthRunnable = chatWidthRunnable;
    }
}
