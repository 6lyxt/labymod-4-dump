// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.server;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_1.server.MinecraftServerAccessor;

@Mixin({ MinecraftServer.class })
public class MixinMinecraftServer implements MinecraftServerAccessor
{
    @Final
    @Shadow
    protected dyy.c h;
    
    @Override
    public dyy.c getStorageSource() {
        return this.h;
    }
}
