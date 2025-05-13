// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.layout.entry;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.WrappedWidget;

@AutoWidget
public class HorizontalListEntry extends WrappedWidget
{
    private final LssProperty<Float> flex;
    private final LssProperty<HorizontalAlignment> alignment;
    private final LssProperty<Boolean> ignoreWidth;
    private final LssProperty<Boolean> ignoreHeight;
    private final LssProperty<Boolean> skipFill;
    
    public HorizontalListEntry(final Widget widget) {
        super(widget);
        this.flex = new LssProperty<Float>(1.0f);
        this.alignment = new LssProperty<HorizontalAlignment>(HorizontalAlignment.LEFT);
        this.ignoreWidth = new LssProperty<Boolean>(false);
        this.ignoreHeight = new LssProperty<Boolean>(false);
        this.skipFill = new LssProperty<Boolean>(false);
        this.addId("horizontal-list-entry");
    }
    
    public LssProperty<Float> flex() {
        return this.flex;
    }
    
    public LssProperty<HorizontalAlignment> alignment() {
        return this.alignment;
    }
    
    public LssProperty<Boolean> ignoreWidth() {
        return this.ignoreWidth;
    }
    
    public LssProperty<Boolean> ignoreHeight() {
        return this.ignoreHeight;
    }
    
    public LssProperty<Boolean> skipFill() {
        return this.skipFill;
    }
}
