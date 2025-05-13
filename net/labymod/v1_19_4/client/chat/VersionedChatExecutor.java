// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.client.chat;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.chat.Title;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.client.component.Component;
import net.labymod.core.client.accessor.chat.ChatScreenAccessor;
import net.labymod.api.Laby;
import javax.inject.Inject;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.chat.ChatExecutor;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.chat.DefaultChatExecutor;

@Singleton
@Implements(ChatExecutor.class)
public class VersionedChatExecutor extends DefaultChatExecutor
{
    private final ComponentMapper componentMapper;
    private final erw dummyChatScreen;
    
    @Inject
    public VersionedChatExecutor(final ComponentMapper componentMapper) {
        this.dummyChatScreen = new erw("");
        this.componentMapper = componentMapper;
    }
    
    @Override
    public void insertText(final String insertion, final boolean skipInput) {
        final Object screen = Laby.labyAPI().minecraft().minecraftWindow().mostInnerScreen();
        if (screen instanceof final ChatScreenAccessor chatScreenAccessor) {
            chatScreenAccessor.insertChatText(insertion, false, skipInput);
        }
    }
    
    @Override
    public void suggestCommand(final String command) {
        final Object screen = Laby.labyAPI().minecraft().minecraftWindow().mostInnerScreen();
        if (screen instanceof final ChatScreenAccessor chatScreenAccessor) {
            chatScreenAccessor.insertChatText(command, true);
        }
    }
    
    @Override
    public void copyToClipboard(final String value) {
        emh.N().o.a(value);
    }
    
    @Override
    public void chat(final String message) {
        this.chat(message, true);
    }
    
    @Override
    public void chat(final String message, final boolean addToHistory) {
        this.dummyChatScreen.b(emh.N(), 0, 0);
        this.dummyChatScreen.b(message, addToHistory);
    }
    
    @Override
    public void displayClientMessage(final Component message, final boolean actionBar) {
        if (ThreadSafe.isRenderThread()) {
            this._displayClientMessage(message, actionBar);
        }
        else {
            ThreadSafe.executeOnRenderThread(() -> this._displayClientMessage(message, actionBar));
        }
    }
    
    @Override
    public void displayActionBar(final Component message, final boolean animate) {
        final enq chatGui = emh.N().l;
        final tj component = (tj)this.componentMapper.toMinecraftComponent(message);
        chatGui.a(component, animate);
    }
    
    @Nullable
    @Override
    public String getChatInputMessage() {
        final Object screen = Laby.labyAPI().minecraft().minecraftWindow().mostInnerScreen();
        if (screen instanceof final ChatScreenAccessor chatScreenAccessor) {
            return chatScreenAccessor.getChatText();
        }
        return null;
    }
    
    @Override
    public Title getTitle() {
        final enq gui = emh.N().l;
        if (gui.L <= 0) {
            return null;
        }
        final ComponentMapper mapper = Laby.references().componentMapper();
        return new Title(mapper.fromMinecraftComponent(gui.M), mapper.fromMinecraftComponent(gui.N), gui.O, gui.P, gui.Q);
    }
    
    @Override
    public void showTitle(@NotNull final Title title) {
        this.clearTitle();
        if (title.getTitle() != null) {
            emh.N().l.c((tj)Laby.references().componentMapper().toMinecraftComponent(title.getTitle()));
        }
        else {
            emh.N().l.c((tj)tj.h());
        }
        if (title.getSubTitle() != null) {
            emh.N().l.b((tj)Laby.references().componentMapper().toMinecraftComponent(title.getSubTitle()));
        }
        emh.N().l.a(title.getFadeInTicks(), title.getStayTicks(), title.getFadeOutTicks());
    }
    
    @Override
    public void clearTitle() {
        emh.N().l.c();
    }
    
    @Override
    protected void rescaleChat() {
        emh.N().l.d().b();
    }
    
    private void _displayClientMessage(final Component message, final boolean actionBar) {
        final enq chatGui = emh.N().l;
        final tj component = (tj)this.componentMapper.toMinecraftComponent(message);
        if (actionBar) {
            chatGui.a(component, false);
        }
        else {
            chatGui.d().a(component);
        }
    }
}
