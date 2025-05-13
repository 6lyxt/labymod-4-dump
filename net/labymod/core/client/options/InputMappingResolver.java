// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.options;

import net.labymod.api.client.options.MinecraftInputMapping;
import java.util.function.Function;

public class InputMappingResolver
{
    private static Function<String, MinecraftInputMapping> resolver;
    
    public static void setResolver(final Function<String, MinecraftInputMapping> resolver) {
        InputMappingResolver.resolver = resolver;
    }
    
    public static MinecraftInputMapping resolve(final String keybind) {
        return (InputMappingResolver.resolver != null) ? InputMappingResolver.resolver.apply(keybind) : null;
    }
    
    static {
        InputMappingResolver.resolver = (Function<String, MinecraftInputMapping>)(keybind -> null);
    }
}
