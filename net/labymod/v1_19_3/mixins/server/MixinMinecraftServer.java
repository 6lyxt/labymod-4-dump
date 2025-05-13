// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.server;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_19_3.server.MinecraftServerAccessor;

@Mixin({ MinecraftServer.class })
public class MixinMinecraftServer implements MinecraftServerAccessor
{
    @Final
    @Shadow
    protected dve.c h;
    
    @Override
    public dve.c getStorageSource() {
        return this.h;
    }
}
