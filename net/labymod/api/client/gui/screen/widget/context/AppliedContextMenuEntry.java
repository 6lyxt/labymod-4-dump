// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.context;

import org.jetbrains.annotations.NotNull;

public class AppliedContextMenuEntry
{
    private final ContextMenu contextMenu;
    private final ContextMenuEntry entry;
    
    public AppliedContextMenuEntry(@NotNull final ContextMenu contextMenu, @NotNull final ContextMenuEntry entry) {
        this.contextMenu = contextMenu;
        this.entry = entry;
    }
    
    @NotNull
    public ContextMenu contextMenu() {
        return this.contextMenu;
    }
    
    @NotNull
    public ContextMenuEntry entry() {
        return this.entry;
    }
}
