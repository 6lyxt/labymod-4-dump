// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline;

import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.function.Functional;
import java.util.ArrayDeque;
import java.util.Deque;

public final class RenderAttributesStack
{
    private final Deque<RenderAttributes> stack;
    
    public RenderAttributesStack() {
        this.stack = Functional.of((ArrayDeque)new ArrayDeque(), stack -> stack.push(new RenderAttributes()));
    }
    
    public void push() {
        ThreadSafe.ensureRenderThread();
        final RenderAttributes last = this.last();
        this.stack.addLast(new RenderAttributes(last));
    }
    
    public RenderAttributes pushAndGet() {
        this.push();
        return this.last();
    }
    
    public void pop() {
        ThreadSafe.ensureRenderThread();
        this.stack.removeLast();
        final RenderAttributes attributes = this.last();
        attributes.apply();
    }
    
    public RenderAttributes last() {
        return this.stack.getLast();
    }
    
    public boolean clear() {
        return this.stack.size() == 1;
    }
    
    public void checkStack() {
        if (!this.clear()) {
            throw new IllegalStateException("RenderAttributes stack is not empty!");
        }
    }
}
