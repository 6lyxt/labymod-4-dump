// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.function;

import net.labymod.api.client.gui.lss.property.LssProperty;

@FunctionalInterface
public interface LssPropertyChangeListener<V> extends ChangeListener<LssProperty<? extends V>, V>
{
    void changed(final LssProperty<? extends V> p0, final V p1, final V p2);
}
