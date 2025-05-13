// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.matrix;

import javax.inject.Inject;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.client.render.matrix.ArrayStackProvider;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.matrix.EmptyStack;

@Singleton
@Implements(EmptyStack.class)
public class DefaultEmptyStack implements EmptyStack
{
    private final Stack stack;
    
    @Inject
    public DefaultEmptyStack() {
        this.stack = Stack.create(new ArrayStackProvider());
    }
    
    @Override
    public Stack create() {
        return this.stack;
    }
}
