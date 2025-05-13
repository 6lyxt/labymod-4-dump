// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.util;

import java.util.Iterator;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.labymod.api.client.component.Component;
import net.labymod.core.watcher.list.WatchableList;

public class WatchableTranslatableComponentArgumentsList implements WatchableList<Component>
{
    private final Supplier<Object[]> argumentsSupplier;
    private final Consumer<Object[]> argumentsConsumer;
    
    public WatchableTranslatableComponentArgumentsList(final Supplier<Object[]> argumentsSupplier, final Consumer<Object[]> argumentsConsumer) {
        this.argumentsSupplier = argumentsSupplier;
        this.argumentsConsumer = argumentsConsumer;
    }
    
    @Override
    public void onAdd(final Component component) {
        final Object[] arguments = this.argumentsSupplier.get();
        final Object[] newArguments = new Object[arguments.length + 1];
        System.arraycopy(arguments, 0, newArguments, 0, arguments.length);
        newArguments[arguments.length] = component;
        this.argumentsConsumer.accept(newArguments);
    }
    
    @Override
    public void onRemove(final Component component) {
        final Object[] arguments = this.argumentsSupplier.get();
        final Object[] newArguments = new Object[arguments.length - 1];
        int index = 0;
        for (final Object argument : arguments) {
            if (argument != component) {
                newArguments[index++] = argument;
            }
        }
        this.argumentsConsumer.accept(newArguments);
    }
    
    @Override
    public void onClear() {
        this.argumentsConsumer.accept(new Object[0]);
    }
    
    @Override
    public void onAddAll(final Collection<? extends Component> c) {
        final Object[] arguments = this.argumentsSupplier.get();
        final Object[] newArguments = new Object[arguments.length + c.size()];
        System.arraycopy(arguments, 0, newArguments, 0, arguments.length);
        int index = arguments.length;
        for (final Component component : c) {
            newArguments[index++] = component;
        }
        this.argumentsConsumer.accept(newArguments);
    }
    
    @Override
    public void onAdd(final int index, final Component component) {
    }
}
