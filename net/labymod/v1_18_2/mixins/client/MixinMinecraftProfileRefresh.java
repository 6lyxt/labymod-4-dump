// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client;

import net.labymod.v1_18_2.mojang.MojangServices;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mutable;
import com.mojang.authlib.minecraft.UserApiService;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.session.SessionRefresher;

@Mixin({ dyr.class })
public class MixinMinecraftProfileRefresh implements SessionRefresher
{
    @Mutable
    @Final
    @Shadow
    private UserApiService av;
    @Shadow
    @Final
    private dzh W;
    @Mutable
    @Shadow
    @Final
    private ehk aE;
    @Shadow
    @Final
    private MinecraftSessionService au;
    
    @Override
    public void refresh() {
        this.av = MojangServices.createSocialInteractions(this.au, this.W);
        this.aE = new ehk((dyr)this, this.av);
    }
}
