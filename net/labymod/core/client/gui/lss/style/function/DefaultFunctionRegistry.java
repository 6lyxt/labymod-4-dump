// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.function;

import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.lss.style.function.FunctionRegistry;

@Singleton
@Implements(FunctionRegistry.class)
public class DefaultFunctionRegistry implements FunctionRegistry
{
    private final Map<String, Map<Integer, Class<?>[]>> functions;
    
    public DefaultFunctionRegistry() {
        this.functions = new HashMap<String, Map<Integer, Class<?>[]>>();
    }
    
    @Override
    public void registerFunction(final String functionName, final Class<?>... parameterTypes) {
        this.functions.computeIfAbsent(functionName, s -> new HashMap()).put((Object)parameterTypes.length, (Object)parameterTypes);
    }
    
    @Override
    public boolean isFunctionRegistered(final String functionName) {
        return this.functions.containsKey(functionName);
    }
    
    @Override
    public Class<?>[] getParameterTypes(final String functionName, final int parameterCount) {
        final Map<Integer, Class<?>[]> functionOptions = this.functions.get(functionName);
        if (functionOptions == null) {
            return null;
        }
        return functionOptions.get(parameterCount);
    }
    
    @Override
    public int[] getAllowedParameterCounts(final String functionName) {
        final Map<Integer, Class<?>[]> functionOptions = this.functions.get(functionName);
        if (functionOptions == null) {
            return null;
        }
        final int[] counts = new int[functionOptions.keySet().size()];
        int i = 0;
        for (final Integer count : functionOptions.keySet()) {
            counts[i++] = count;
        }
        return counts;
    }
}
