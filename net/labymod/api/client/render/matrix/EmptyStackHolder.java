// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.matrix;

import net.labymod.api.Laby;

public final class EmptyStackHolder
{
    public static final EmptyStack EMPTY_STACK;
    
    static {
        EMPTY_STACK = Laby.references().emptyStack();
    }
}
