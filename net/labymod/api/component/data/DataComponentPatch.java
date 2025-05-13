// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.component.data;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

record DataComponentPatch(Collection<TypedDataComponent<?>> components) {
    public static DataComponentPatch createPatch(final DataComponentContainer container) {
        final List<TypedDataComponent<?>> components = new ArrayList<TypedDataComponent<?>>();
        for (final TypedDataComponent<?> typedDataComponent : container) {
            components.add(typedDataComponent);
        }
        return new DataComponentPatch((Collection<TypedDataComponent<?>>)List.copyOf((Collection<?>)components));
    }
}
