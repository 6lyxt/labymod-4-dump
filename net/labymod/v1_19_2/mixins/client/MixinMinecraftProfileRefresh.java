// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.client;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import org.spongepowered.asm.mixin.Mutable;
import com.mojang.authlib.minecraft.UserApiService;
import java.io.File;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.session.SessionRefresher;

@Mixin({ efu.class })
public class MixinMinecraftProfileRefresh implements SessionRefresher
{
    @Shadow
    @Final
    private static Logger G;
    @Shadow
    @Final
    public File p;
    @Mutable
    @Final
    @Shadow
    private UserApiService ax;
    @Mutable
    @Final
    @Shadow
    private eva aK;
    @Shadow
    @Final
    private YggdrasilAuthenticationService au;
    @Shadow
    @Final
    private egk W;
    @Mutable
    @Shadow
    @Final
    private eph aG;
    @Shadow
    private evr bs;
    
    @Override
    public void refresh() {
        this.ax = this.labyMod$createUserApiService();
        this.aK = new eva(this.ax, this.W.h().getId(), this.p.toPath());
        this.aG = new eph((efu)this, this.ax);
        this.bs = evr.a(evp.a(), this.ax);
    }
    
    private UserApiService labyMod$createUserApiService() {
        try {
            return this.au.createUserApiService(this.W.d());
        }
        catch (final AuthenticationException exception) {
            MixinMinecraftProfileRefresh.G.error("Failed to verify authentication", (Throwable)exception);
            return UserApiService.OFFLINE;
        }
    }
}
