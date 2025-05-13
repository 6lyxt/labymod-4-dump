// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.reflection;

import net.labymod.api.util.Lazy;

public class MethodOverrideAnalyzer
{
    private final Class<?> superClass;
    private final Class<?> subClass;
    private final String name;
    private final Class<?>[] parameterTypes;
    private final Lazy<Boolean> overridden;
    
    public MethodOverrideAnalyzer(final Class<?> superClass, final Class<?> subClass, final String name, final Class<?>... parameterTypes) {
        this.superClass = superClass;
        this.subClass = subClass;
        this.name = name;
        this.parameterTypes = parameterTypes;
        this.overridden = Lazy.of(() -> Reflection.isMethodOverridden(this.superClass, this.subClass, this.name, this.parameterTypes));
    }
    
    public Class<?> getSuperClass() {
        return this.superClass;
    }
    
    public Class<?> getSubClass() {
        return this.subClass;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Class<?>[] getParameterTypes() {
        return this.parameterTypes;
    }
    
    public boolean isOverridden() {
        return this.overridden.get();
    }
}
