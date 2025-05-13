// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.volt;

import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import net.labymod.api.volt.injector.insert.InsertInjectionInfo;

public class VoltMixinBootstrap
{
    private static boolean initialized;
    
    public static void initialize() {
        if (VoltMixinBootstrap.initialized) {
            return;
        }
        VoltMixinBootstrap.initialized = true;
        InjectionInfo.register((Class)InsertInjectionInfo.class);
    }
    
    static {
        VoltMixinBootstrap.initialized = false;
    }
}
