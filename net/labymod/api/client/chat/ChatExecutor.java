// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.chat;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ChatExecutor
{
    void performAction(final ClickEvent p0);
    
    default void insertText(final String insertion) {
        this.insertText(insertion, false);
    }
    
    void insertText(final String p0, final boolean p1);
    
    void chat(final String p0);
    
    void chat(final String p0, final boolean p1);
    
    default void displayClientMessage(final String message) {
        this.displayClientMessage(Component.text(message), false);
    }
    
    default void displayClientMessage(final String message, final boolean actionBar) {
        this.displayClientMessage(Component.text(message), actionBar);
    }
    
    default void displayClientMessage(final Component component) {
        this.displayClientMessage(component, false);
    }
    
    void displayClientMessage(final Component p0, final boolean p1);
    
    default void displayActionBar(final String message) {
        this.displayActionBar(Component.text(message), false);
    }
    
    default void displayActionBar(final String message, final boolean animate) {
        this.displayActionBar(Component.text(message), animate);
    }
    
    default void displayActionBar(final Component message) {
        this.displayActionBar(message, false);
    }
    
    void displayActionBar(final Component p0, final boolean p1);
    
    ActionBar displayActionBarContinuous(final Component p0);
    
    void openUrl(final String p0, final boolean p1);
    
    void openUrl(final String p0);
    
    void openFile(final String p0);
    
    void suggestCommand(final String p0);
    
    void copyToClipboard(final String p0);
    
    @Nullable
    String getChatInputMessage();
    
    @Nullable
    Title getTitle();
    
    void showTitle(@NotNull final Title p0);
    
    void clearTitle();
}
