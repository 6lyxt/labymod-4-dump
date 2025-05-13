// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.mixin;

import org.spongepowered.asm.service.mojang.MixinServiceLaunchWrapperBootstrap;

public class LabyModBootstrap extends MixinServiceLaunchWrapperBootstrap
{
    public String getName() {
        return "LabyMod";
    }
    
    public String getServiceClassName() {
        return "net.labymod.core.loader.mixin.LabyModBootstrap";
    }
}
