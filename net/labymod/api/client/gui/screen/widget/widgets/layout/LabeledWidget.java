// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.layout;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.HorizontalListEntry;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;

@AutoWidget
public class LabeledWidget extends HorizontalListWidget
{
    private final Component label;
    private final Widget widget;
    private final LssProperty<HorizontalAlignment> labelAlignment;
    
    public LabeledWidget(final Component label, final Widget widget) {
        this.labelAlignment = new LssProperty<HorizontalAlignment>(HorizontalAlignment.LEFT);
        this.label = label;
        this.widget = widget;
        ((AbstractWidget<Widget>)this).addId("labeled");
    }
    
    public static LabeledWidget fullWidth(final Component label, final Widget widget) {
        return new LabeledWidget(label, widget);
    }
    
    public static LabeledWidget alignedValue(final Component label, final Widget widget) {
        final HorizontalListWidget list = new HorizontalListWidget();
        list.addChild(new HorizontalListEntry(widget));
        return fullWidth(label, list);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final HorizontalListEntry label = new HorizontalListEntry(ComponentWidget.component(this.label));
        final HorizontalListEntry widget = new HorizontalListEntry(this.widget);
        label.addId("labeled-label");
        widget.addId("labeled-widget");
        switch (this.labelAlignment.get()) {
            case LEFT: {
                label.alignment().set(HorizontalAlignment.LEFT);
                widget.alignment().set(HorizontalAlignment.RIGHT);
                break;
            }
            case CENTER: {
                label.alignment().set(HorizontalAlignment.CENTER);
                widget.alignment().set(HorizontalAlignment.RIGHT);
                break;
            }
            case RIGHT: {
                label.alignment().set(HorizontalAlignment.RIGHT);
                widget.alignment().set(HorizontalAlignment.LEFT);
                break;
            }
        }
        this.addChild(label);
        this.addChild(widget);
    }
    
    public LssProperty<HorizontalAlignment> labelAlignment() {
        return this.labelAlignment;
    }
}
