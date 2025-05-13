// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.key;

import java.util.Objects;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.BooleanSupplier;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface HotkeyService
{
    void register(final String p0, final BooleanSupplier p1, final Supplier<Key> p2, final Supplier<Type> p3, final Consumer<Boolean> p4);
    
    boolean unregister(final String p0);
    
    default void register(final String identifier, final Supplier<Key> keySupplier, final Supplier<Type> typeSupplier, final Consumer<Boolean> active) {
        this.register(identifier, () -> true, keySupplier, typeSupplier, active);
    }
    
    default void register(final String identifier, final ConfigProperty<? extends Key> keyConfigProperty, final Supplier<Type> typeSupplier, final Consumer<Boolean> active) {
        Objects.requireNonNull(keyConfigProperty);
        this.register(identifier, keyConfigProperty::get, typeSupplier, active);
    }
    
    default void register(final String identifier, final ConfigProperty<? extends Key> keyConfigProperty, final BooleanSupplier condition, final Supplier<Type> typeSupplier, final Consumer<Boolean> active) {
        Objects.requireNonNull(keyConfigProperty);
        this.register(identifier, condition, keyConfigProperty::get, typeSupplier, active);
    }
    
    public enum Type
    {
        TOGGLE, 
        HOLD;
    }
}
