// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.layout.list;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.widget.Widget;

public abstract class SessionedListWidget<T extends Widget> extends ListWidget<T>
{
    protected final ListSession<T> session;
    
    protected SessionedListWidget(@NotNull final ListSession<T> listSession) {
        Objects.requireNonNull(listSession, "ListSession cannot be null!");
        this.session = listSession;
    }
    
    protected SessionedListWidget() {
        this((ListSession)new ListSession());
    }
    
    @NotNull
    public ListSession<T> listSession() {
        return this.session;
    }
}
