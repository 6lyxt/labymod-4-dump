// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.layout.entry;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.WrappedWidget;

@AutoWidget
public class FlexibleContentEntry extends WrappedWidget
{
    private final boolean flexible;
    private final LssProperty<Boolean> ignoreBounds;
    
    public FlexibleContentEntry(final Widget widget, final boolean flexible) {
        super(widget);
        this.ignoreBounds = new LssProperty<Boolean>(false);
        this.addId("flexible-content-entry");
        this.flexible = flexible;
    }
    
    public boolean isFlexible() {
        return this.flexible;
    }
    
    public LssProperty<Boolean> ignoreBounds() {
        return this.ignoreBounds;
    }
}
