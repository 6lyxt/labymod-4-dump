// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.popup;

import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.renderer.EntryRenderer;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

@AutoWidget
public class DropdownListWidget<T> extends VerticalListWidget<DropdownEntryWidget<T>>
{
    private final DropdownWidget<T> dropdown;
    
    public DropdownListWidget(final DropdownWidget<T> dropdown) {
        this.dropdown = dropdown;
        this.setSelectCallback(value -> {
            Laby.references().soundService().play(SoundType.BUTTON_CLICK);
            dropdown.onSelect(value.getEntry());
        });
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final EntryRenderer<T> entryRenderer = this.dropdown.entryRenderer();
        for (final T entry : this.dropdown.entries()) {
            final DropdownEntryWidget<T> widget = new DropdownEntryWidget<T>(entryRenderer, entry);
            widget.addId("entry");
            this.addChild(widget);
        }
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        return super.getContentHeight(type);
    }
    
    @Override
    public String getDefaultRendererName() {
        return "Background";
    }
    
    public DropdownWidget<T> getDropdown() {
        return this.dropdown;
    }
}
