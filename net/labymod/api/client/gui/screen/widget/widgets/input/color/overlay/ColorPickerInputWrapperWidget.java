// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import java.util.Locale;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.Parent;
import java.util.function.Supplier;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

@AutoWidget
public class ColorPickerInputWrapperWidget<T extends Widget> extends VerticalListWidget<Widget>
{
    private final String identifier;
    private final Supplier<T> widgetSupplier;
    private T widget;
    
    protected ColorPickerInputWrapperWidget(final String identifier, final Supplier<T> widgetSupplier) {
        this.identifier = identifier;
        this.widgetSupplier = widgetSupplier;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.addId(this.identifier + "-input");
        this.addChild(this.widget = this.widgetSupplier.get());
        final DivWidget nameWrapper = new DivWidget();
        ((AbstractWidget<ComponentWidget>)nameWrapper).addChild(ComponentWidget.text(this.identifier.toUpperCase(Locale.ENGLISH)));
        this.addChild(nameWrapper);
    }
    
    public T getWidget() {
        return this.widget;
    }
}
