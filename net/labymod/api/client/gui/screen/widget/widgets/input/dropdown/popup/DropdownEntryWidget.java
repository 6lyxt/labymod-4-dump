// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.popup;

import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.renderer.EntryRenderer;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class DropdownEntryWidget<T> extends AbstractWidget<Widget>
{
    private final EntryRenderer<T> renderer;
    private final T entry;
    private final LssProperty<Float> fontSize;
    
    public DropdownEntryWidget(final EntryRenderer<T> renderer, final T entry) {
        this.fontSize = new LssProperty<Float>(1.0f);
        this.renderer = renderer;
        this.entry = entry;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final Widget widget = this.renderer.createEntryWidget(this.entry);
        widget.addId("value");
        this.addChild(widget);
    }
    
    @Override
    public float getContentWidth(final BoundsType type) {
        final float scale = this.fontSize.get();
        return this.renderer.getWidth(this.entry, this.bounds().getWidth(type) / scale) * scale;
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        final float scale = this.fontSize.get();
        return this.renderer.getHeight(this.entry, this.bounds().getWidth(type) / scale) * scale;
    }
    
    public T getEntry() {
        return this.entry;
    }
    
    public LssProperty<Float> fontSize() {
        return this.fontSize;
    }
}
