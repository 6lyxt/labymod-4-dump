// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.function;

import net.labymod.api.Laby;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.IntFunction;
import java.util.Arrays;
import java.util.Locale;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.WidgetModifier;
import net.labymod.api.client.gui.lss.style.function.FunctionRegistry;
import net.labymod.api.client.gui.lss.style.function.Function;

public class DefaultFunction implements Function
{
    private static final FunctionRegistry REGISTRY;
    private static final WidgetModifier MODIFIER;
    private final String name;
    private final Element[] elements;
    private final String rawValue;
    
    public DefaultFunction(final String name, final Element[] elements) {
        this.name = name;
        this.elements = elements;
        final StringBuilder builder = new StringBuilder();
        for (int size = this.elements.length, i = 0; i < size; ++i) {
            final Element element = this.elements[i];
            builder.append(element.getRawValue());
            if (i == size - 1) {
                break;
            }
            builder.append(", ");
        }
        this.rawValue = this.name + "(" + String.valueOf(builder);
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public Element[] parameters() {
        return this.elements;
    }
    
    @Override
    public ProcessedObject[][] allValues(final Widget widget, final String key) throws Exception {
        Class<?>[] types = null;
        if (DefaultFunction.REGISTRY.isFunctionRegistered(this.name)) {
            types = DefaultFunction.REGISTRY.getParameterTypes(this.name, this.elements.length);
            if (types == null) {
                throw new IllegalArgumentException(String.format(Locale.ROOT, "Invalid parameter length for function '%s'. Got %d parameters, expected a number of one out of [%s]", this.toString(), this.elements.length, Arrays.stream(DefaultFunction.REGISTRY.getAllowedParameterCounts(this.name)).mapToObj((IntFunction<?>)String::valueOf).collect((Collector<? super Object, ?, String>)Collectors.joining(", "))));
            }
        }
        final ProcessedObject[][] params = new ProcessedObject[this.elements.length][];
        if (types != null && params.length != types.length) {
            throw new IllegalStateException("Function not matching required types: " + String.valueOf(this));
        }
        for (int i = 0; i < this.elements.length; ++i) {
            final ProcessedObject[] computeValues = this.elements[i].computeValue(widget, key, (types != null) ? types[i] : String.class);
            params[i] = computeValues;
        }
        return params;
    }
    
    @Override
    public ProcessedObject[] firstValues(final Widget widget, final String key) throws Exception {
        final ProcessedObject[][] allValues = this.allValues(widget, key);
        final ProcessedObject[] firstValues = new ProcessedObject[allValues.length];
        for (int i = 0; i < allValues.length; ++i) {
            firstValues[i] = allValues[i][0];
        }
        return firstValues;
    }
    
    @Override
    public ProcessedObject[] computeValue(@NotNull final Widget widget, @NotNull final String key, @NotNull final Class<?> type) throws Exception {
        return DefaultFunction.MODIFIER.processValue(widget, type, key, this.getRawValue(), this);
    }
    
    @Override
    public String getRawValue() {
        return this.rawValue;
    }
    
    @Override
    public void forEach(final Consumer<Element> consumer) {
        consumer.accept(this);
        for (final Element parameter : this.elements) {
            parameter.forEach(consumer);
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
        final DefaultFunction that = (DefaultFunction)o;
        return this.name.equals(that.name) && Arrays.equals(this.elements, that.elements);
    }
    
    @Override
    public int hashCode() {
        int result = (this.name != null) ? this.name.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(this.elements);
        return result;
    }
    
    static {
        REGISTRY = Laby.references().functionRegistry();
        MODIFIER = Laby.references().widgetModifier();
    }
}
