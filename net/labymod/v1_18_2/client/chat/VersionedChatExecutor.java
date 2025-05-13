// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.client.chat;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.chat.Title;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.ThreadSafe;
import net.labymod.v1_18_2.client.gui.ScreenAccessor;
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
    private final edw dummy;
    
    @Inject
    public VersionedChatExecutor(final ComponentMapper componentMapper) {
        this.dummy = new edw((qk)Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(Component.empty())) {};
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
        dyr.D().n.a(value);
    }
    
    @Override
    public void chat(final String message) {
        this.chat(message, true);
    }
    
    @Override
    public void chat(final String message, final boolean addToHistory) {
        ((ScreenAccessor)this.dummy).setMinecraft(dyr.D());
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
        final dzq chatGui = dyr.D().k;
        final qk component = (qk)this.componentMapper.toMinecraftComponent(message);
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
        final dzq gui = dyr.D().k;
        if (gui.J <= 0) {
            return null;
        }
        final ComponentMapper mapper = Laby.references().componentMapper();
        return new Title(mapper.fromMinecraftComponent(gui.K), mapper.fromMinecraftComponent(gui.L), gui.M, gui.N, gui.O);
    }
    
    @Override
    public void showTitle(@NotNull final Title title) {
        this.clearTitle();
        if (title.getTitle() != null) {
            dyr.D().k.c((qk)Laby.references().componentMapper().toMinecraftComponent(title.getTitle()));
        }
        else {
            dyr.D().k.c(qx.d);
        }
        if (title.getSubTitle() != null) {
            dyr.D().k.b((qk)Laby.references().componentMapper().toMinecraftComponent(title.getSubTitle()));
        }
        dyr.D().k.a(title.getFadeInTicks(), title.getStayTicks(), title.getFadeOutTicks());
    }
    
    @Override
    public void clearTitle() {
        dyr.D().k.b();
    }
    
    @Override
    protected void rescaleChat() {
        dyr.D().k.c().a();
    }
    
    private void _displayClientMessage(final Component message, final boolean actionBar) {
        final dzq chatGui = dyr.D().k;
        final qk component = (qk)this.componentMapper.toMinecraftComponent(message);
        if (actionBar) {
            chatGui.a(component, false);
        }
        else {
            chatGui.c().a(component);
        }
    }
}
