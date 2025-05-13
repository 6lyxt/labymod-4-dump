// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style.modifier;

import net.labymod.api.client.gui.lss.style.function.Function;

public abstract class SingleFunctionPostProcessor implements FunctionPostProcessor
{
    private final String functionName;
    
    public SingleFunctionPostProcessor(final String functionName) {
        this.functionName = functionName;
    }
    
    @Override
    public boolean requiresPostProcessing(final String key, final Function function, final Class<?> type) {
        return function.getName().equals(this.functionName);
    }
}
