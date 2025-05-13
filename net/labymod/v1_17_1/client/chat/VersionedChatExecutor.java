// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.client.chat;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.chat.Title;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.ThreadSafe;
import net.labymod.v1_17_1.client.gui.ScreenAccessor;
import net.labymod.core.client.accessor.chat.ChatScreenAccessor;
import javax.inject.Inject;
import net.labymod.api.client.component.Component;
import net.labymod.api.Laby;
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
    private final eaq dummy;
    
    @Inject
    public VersionedChatExecutor(final ComponentMapper componentMapper) {
        this.dummy = new eaq((os)Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(Component.empty())) {};
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
        dvp.C().n.a(value);
    }
    
    @Override
    public void chat(final String message) {
        this.chat(message, true);
    }
    
    @Override
    public void chat(final String message, final boolean addToHistory) {
        ((ScreenAccessor)this.dummy).setMinecraft(dvp.C());
        this.dummy.b(message, addToHistory);
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
        final dwm chatGui = dvp.C().k;
        final os component = (os)this.componentMapper.toMinecraftComponent(message);
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
        final dwm gui = dvp.C().k;
        if (gui.H <= 0) {
            return null;
        }
        final ComponentMapper mapper = Laby.references().componentMapper();
        return new Title(mapper.fromMinecraftComponent(gui.I), mapper.fromMinecraftComponent(gui.J), gui.K, gui.L, gui.M);
    }
    
    @Override
    public void showTitle(@NotNull final Title title) {
        this.clearTitle();
        if (title.getTitle() != null) {
            dvp.C().k.c((os)Laby.references().componentMapper().toMinecraftComponent(title.getTitle()));
        }
        else {
            dvp.C().k.c(pf.d);
        }
        if (title.getSubTitle() != null) {
            dvp.C().k.b((os)Laby.references().componentMapper().toMinecraftComponent(title.getSubTitle()));
        }
        dvp.C().k.a(title.getFadeInTicks(), title.getStayTicks(), title.getFadeOutTicks());
    }
    
    @Override
    public void clearTitle() {
        dvp.C().k.c();
    }
    
    @Override
    protected void rescaleChat() {
        dvp.C().k.d().a();
    }
    
    private void _displayClientMessage(final Component message, final boolean actionBar) {
        final dwm chatGui = dvp.C().k;
        final os component = (os)this.componentMapper.toMinecraftComponent(message);
        if (actionBar) {
            chatGui.a(component, false);
        }
        else {
            chatGui.d().a(component);
        }
    }
}
