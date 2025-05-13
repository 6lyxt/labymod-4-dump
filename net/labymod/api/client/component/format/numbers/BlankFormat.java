// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component.format.numbers;

import net.labymod.api.client.component.Component;

public class BlankFormat implements NumberFormat
{
    public static final BlankFormat INSTANCE;
    
    @Override
    public Component format(final int number) {
        return Component.empty();
    }
    
    static {
        INSTANCE = new BlankFormat();
    }
}
