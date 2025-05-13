// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.client;

import net.labymod.v1_17_1.mojang.MojangServices;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mutable;
import com.mojang.authlib.minecraft.SocialInteractionsService;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.session.SessionRefresher;

@Mixin({ dvp.class })
public class MixinMinecraftProfileRefresh implements SessionRefresher
{
    @Mutable
    @Final
    @Shadow
    private SocialInteractionsService au;
    @Shadow
    @Final
    private dwd W;
    @Mutable
    @Shadow
    @Final
    private eea aD;
    @Shadow
    @Final
    private MinecraftSessionService at;
    
    @Override
    public void refresh() {
        this.au = MojangServices.createSocialInteractions(this.at, this.W);
        this.aD = new eea((dvp)this, this.au);
    }
}
