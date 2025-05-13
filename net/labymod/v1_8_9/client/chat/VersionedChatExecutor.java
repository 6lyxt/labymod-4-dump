// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.chat;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.chat.Title;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.component.serializer.legacy.LegacyComponentSerializer;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.client.component.Component;
import net.labymod.v1_8_9.client.gui.GuiScreenAccessor;
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
    private final axu dummy;
    
    @Inject
    public VersionedChatExecutor(final ComponentMapper componentMapper) {
        this.dummy = new axu() {};
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
        axu.e(value);
    }
    
    @Override
    public void chat(final String message) {
        this.chat(message, true);
    }
    
    @Override
    public void chat(final String message, final boolean addToHistory) {
        ((GuiScreenAccessor)this.dummy).setMinecraft(ave.A());
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
        final avo ingameGui = ave.A().q;
        final String text = LegacyComponentSerializer.legacySection().serialize(message);
        ingameGui.a(text, animate);
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
        final avo gui = ave.A().q;
        if (gui.w <= 0) {
            return null;
        }
        return new Title(LegacyComponentSerializer.legacySection().deserialize(gui.x), LegacyComponentSerializer.legacySection().deserialize(gui.y), gui.z, gui.A, gui.B);
    }
    
    @Override
    public void showTitle(@NotNull final Title title) {
        this.clearTitle();
        final Component titleComponent = title.getTitle();
        final avo gui = ave.A().q;
        if (titleComponent != null) {
            final eu component = (eu)this.componentMapper.toMinecraftComponent(titleComponent);
            gui.a(component.d(), (String)null, 0, 0, 0);
        }
        else {
            gui.a("", (String)null, 0, 0, 0);
        }
        final Component subTitleComponent = title.getSubTitle();
        if (subTitleComponent != null) {
            final eu component2 = (eu)this.componentMapper.toMinecraftComponent(subTitleComponent);
            gui.a((String)null, component2.d(), 0, 0, 0);
        }
        gui.a((String)null, (String)null, title.getFadeInTicks(), title.getStayTicks(), title.getFadeOutTicks());
    }
    
    @Override
    public void clearTitle() {
        ave.A().q.a((String)null, (String)null, -1, -1, -1);
    }
    
    @Override
    protected void rescaleChat() {
        final avo gui = ave.A().q;
        if (gui == null) {
            return;
        }
        gui.d().b();
    }
    
    private void _displayClientMessage(final Component message, final boolean actionBar) {
        if (actionBar) {
            this.displayActionBar(message, false);
        }
        else {
            final avo ingameGui = ave.A().q;
            final eu component = (eu)this.componentMapper.toMinecraftComponent(message);
            ingameGui.d().a(component);
        }
    }
}
