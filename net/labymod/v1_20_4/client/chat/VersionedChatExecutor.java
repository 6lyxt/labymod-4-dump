// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.chat;

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
    private final fbs dummyChatScreen;
    
    @Inject
    public VersionedChatExecutor(final ComponentMapper componentMapper) {
        this.dummyChatScreen = new fbs("");
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
        evi.O().o.a(value);
    }
    
    @Override
    public void chat(final String message) {
        this.chat(message, true);
    }
    
    @Override
    public void chat(final String message, final boolean addToHistory) {
        this.dummyChatScreen.b(evi.O(), 0, 0);
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
        final ewt chatGui = evi.O().l;
        final vf component = (vf)this.componentMapper.toMinecraftComponent(message);
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
        final ewt gui = evi.O().l;
        if (gui.am <= 0) {
            return null;
        }
        final ComponentMapper mapper = Laby.references().componentMapper();
        return new Title(mapper.fromMinecraftComponent(gui.an), mapper.fromMinecraftComponent(gui.ao), gui.ap, gui.aq, gui.ar);
    }
    
    @Override
    public void showTitle(@NotNull final Title title) {
        this.clearTitle();
        if (title.getTitle() != null) {
            evi.O().l.c((vf)Laby.references().componentMapper().toMinecraftComponent(title.getTitle()));
        }
        else {
            evi.O().l.c((vf)vf.i());
        }
        if (title.getSubTitle() != null) {
            evi.O().l.b((vf)Laby.references().componentMapper().toMinecraftComponent(title.getSubTitle()));
        }
        evi.O().l.a(title.getFadeInTicks(), title.getStayTicks(), title.getFadeOutTicks());
    }
    
    @Override
    public void clearTitle() {
        evi.O().l.c();
    }
    
    @Override
    protected void rescaleChat() {
        evi.O().l.d().b();
    }
    
    private void _displayClientMessage(final Component message, final boolean actionBar) {
        final ewt chatGui = evi.O().l;
        final vf component = (vf)this.componentMapper.toMinecraftComponent(message);
        if (actionBar) {
            chatGui.a(component, false);
        }
        else {
            chatGui.d().a(component);
        }
    }
}
