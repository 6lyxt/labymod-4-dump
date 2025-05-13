// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.gui.screen;

import java.util.function.Supplier;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

public class VanillaWidgetReplacementEvent extends DefaultCancellable implements Event
{
    private final AbstractWidget<?> widget;
    private Supplier<AbstractWidget<?>> replacement;
    
    public VanillaWidgetReplacementEvent(final AbstractWidget<?> widget) {
        this.widget = widget;
    }
    
    public AbstractWidget<?> getWidget() {
        return this.widget;
    }
    
    public Supplier<AbstractWidget<?>> getReplacement() {
        return this.replacement;
    }
    
    public void setReplacement(final Supplier<AbstractWidget<?>> replacement) {
        this.replacement = replacement;
        this.setCancelled(true);
    }
}
