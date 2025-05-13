// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.chat;

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
    private final fsu dummyChatScreen;
    
    @Inject
    public VersionedChatExecutor(final ComponentMapper componentMapper) {
        this.dummyChatScreen = new fsu("");
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
        fmg.Q().p.a(value);
    }
    
    @Override
    public void chat(final String message) {
        this.chat(message, true);
    }
    
    @Override
    public void chat(final String message, final boolean addToHistory) {
        this.dummyChatScreen.b(fmg.Q(), 0, 0);
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
        final fnr chatGui = fmg.Q().m;
        final xv component = (xv)this.componentMapper.toMinecraftComponent(message);
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
        final fnr gui = fmg.Q().m;
        if (gui.ay <= 0) {
            return null;
        }
        final ComponentMapper mapper = Laby.references().componentMapper();
        return new Title(mapper.fromMinecraftComponent(gui.az), mapper.fromMinecraftComponent(gui.aA), gui.aB, gui.aC, gui.aD);
    }
    
    @Override
    public void showTitle(@NotNull final Title title) {
        this.clearTitle();
        if (title.getTitle() != null) {
            fmg.Q().m.c((xv)Laby.references().componentMapper().toMinecraftComponent(title.getTitle()));
        }
        else {
            fmg.Q().m.c((xv)xv.i());
        }
        if (title.getSubTitle() != null) {
            fmg.Q().m.b((xv)Laby.references().componentMapper().toMinecraftComponent(title.getSubTitle()));
        }
        fmg.Q().m.a(title.getFadeInTicks(), title.getStayTicks(), title.getFadeOutTicks());
    }
    
    @Override
    public void clearTitle() {
        fmg.Q().m.c();
    }
    
    @Override
    protected void rescaleChat() {
        fmg.Q().m.d().b();
    }
    
    private void _displayClientMessage(final Component message, final boolean actionBar) {
        final fnr chatGui = fmg.Q().m;
        final xv component = (xv)this.componentMapper.toMinecraftComponent(message);
        if (actionBar) {
            chatGui.a(component, false);
        }
        else {
            chatGui.d().a(component);
        }
    }
}
