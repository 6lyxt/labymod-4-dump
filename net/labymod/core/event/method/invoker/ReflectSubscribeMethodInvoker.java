// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.method.invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.labymod.core.event.method.SubscribeMethodInvoker;

public class ReflectSubscribeMethodInvoker implements SubscribeMethodInvoker
{
    private final Method method;
    
    public ReflectSubscribeMethodInvoker(final Method method) {
        this.method = method;
    }
    
    @Override
    public void invoke(final Object listener, final Object event) throws Throwable {
        try {
            this.method.invoke(listener, event);
        }
        catch (final InvocationTargetException exception) {
            throw exception.getCause();
        }
    }
}
