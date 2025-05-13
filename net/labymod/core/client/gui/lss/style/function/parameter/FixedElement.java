// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.function.parameter;

import net.labymod.api.Laby;
import java.util.function.Consumer;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.modifier.WidgetModifier;
import net.labymod.api.client.gui.lss.style.function.Element;

public class FixedElement implements Element
{
    private static final WidgetModifier MODIFIER;
    private final String value;
    
    public FixedElement(final String value) {
        this.value = value;
    }
    
    @Override
    public ProcessedObject[] computeValue(@NotNull final Widget widget, @NotNull final String key, @NotNull final Class<?> type) throws Exception {
        return (type == Object.class) ? new ProcessedObject[] { new ProcessedObject(null, this.value, this.value) } : FixedElement.MODIFIER.processValue(widget, type, key, this.value, this);
    }
    
    @Override
    public String getRawValue() {
        return this.value;
    }
    
    @Override
    public String toString() {
        return this.value;
    }
    
    @Override
    public void forEach(final Consumer<Element> consumer) {
        consumer.accept(this);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final FixedElement that = (FixedElement)o;
        return this.value.equals(that.value);
    }
    
    @Override
    public int hashCode() {
        return (this.value != null) ? this.value.hashCode() : 0;
    }
    
    static {
        MODIFIER = Laby.references().widgetModifier();
    }
}
