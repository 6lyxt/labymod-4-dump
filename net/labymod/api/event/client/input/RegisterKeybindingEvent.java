// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.input;

import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.function.Supplier;
import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

public class RegisterKeybindingEvent extends DefaultCancellable implements Event
{
    private final String name;
    private String category;
    private Supplier<Widget> replacement;
    
    public RegisterKeybindingEvent(final String name, final String category) {
        this.name = name;
        this.category = category;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getCategory() {
        return this.category;
    }
    
    public void setCategory(final String category) {
        Objects.requireNonNull(category, "category must not be null");
        this.category = category;
    }
    
    public Supplier<Widget> getReplacement() {
        return this.replacement;
    }
    
    public void setReplacement(final Supplier<Widget> replacement) {
        this.replacement = replacement;
        this.setCancelled(false);
    }
    
    @Override
    public void setCancelled(final boolean cancelled) {
        super.setCancelled(cancelled);
    }
}
