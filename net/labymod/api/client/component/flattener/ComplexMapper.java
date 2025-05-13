// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component.flattener;

import net.labymod.api.client.component.Component;
import java.util.function.Consumer;

public interface ComplexMapper<T>
{
    void map(final T p0, final Consumer<Component> p1);
    
    default void exception(final T value, final Consumer<Component> consumer, final Exception e) {
        e.printStackTrace();
    }
}
