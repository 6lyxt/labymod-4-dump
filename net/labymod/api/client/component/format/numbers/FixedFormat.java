// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component.format.numbers;

import net.labymod.api.client.component.Component;

record FixedFormat(Component value) implements NumberFormat {
    @Override
    public Component format(final int number) {
        return this.value.copy();
    }
}
