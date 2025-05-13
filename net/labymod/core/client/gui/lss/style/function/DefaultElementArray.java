// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.function;

import java.util.function.Consumer;
import java.util.Iterator;
import java.util.Collection;
import java.util.Collections;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.ArrayList;
import net.labymod.api.client.gui.lss.style.function.Element;
import java.util.List;
import net.labymod.api.client.gui.lss.style.function.ElementArray;

public class DefaultElementArray implements ElementArray
{
    private final List<Element> elements;
    private final String rawValue;
    
    public DefaultElementArray() {
        this(new ArrayList<Element>());
    }
    
    public DefaultElementArray(final List<Element> elements) {
        this.elements = elements;
        final StringBuilder builder = new StringBuilder();
        for (int size = this.elements.size(), i = 0; i < size; ++i) {
            final Element element = this.elements.get(i);
            builder.append(element.getRawValue());
            if (i == size - 1) {
                break;
            }
            builder.append(" ");
        }
        this.rawValue = builder.toString();
    }
    
    @Override
    public ProcessedObject[] computeValue(@NotNull final Widget widget, @NotNull final String key, @NotNull final Class<?> type) throws Exception {
        final int size = this.elements.size();
        final List<ProcessedObject> values = new ArrayList<ProcessedObject>(size);
        for (final Element element : this.elements) {
            Collections.addAll(values, element.computeValue(widget, "", String.class));
        }
        return values.toArray(new ProcessedObject[0]);
    }
    
    @Override
    public String getRawValue() {
        return this.rawValue;
    }
    
    @Override
    public List<Element> getElements() {
        return this.elements;
    }
    
    @Override
    public void forEach(final Consumer<Element> consumer) {
        consumer.accept(this);
        for (final Element content : this.elements) {
            content.forEach(consumer);
        }
    }
    
    @Override
    public String toString() {
        return this.getRawValue();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final DefaultElementArray that = (DefaultElementArray)o;
        return this.elements.equals(that.elements);
    }
    
    @Override
    public int hashCode() {
        return this.elements.hashCode();
    }
}
