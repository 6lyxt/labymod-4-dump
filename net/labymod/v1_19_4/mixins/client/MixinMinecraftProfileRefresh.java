// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client;

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

@Mixin({ emh.class })
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
    private UserApiService az;
    @Mutable
    @Final
    @Shadow
    private fdp aL;
    @Shadow
    @Final
    private YggdrasilAuthenticationService aw;
    @Shadow
    @Final
    private emw W;
    @Mutable
    @Shadow
    @Final
    private exf aH;
    @Mutable
    @Shadow
    @Final
    private fxn aK;
    @Shadow
    private fef bt;
    
    @Override
    public void refresh() {
        this.az = this.labyMod$createUserApiService();
        this.aL = fdp.a(this.az, this.W, this.p.toPath());
        this.aH = new exf((emh)this, this.az);
        this.aK = new fxn((emh)this, this.az, this.W);
        this.bt = fef.a(fed.a(), this.az);
    }
    
    private UserApiService labyMod$createUserApiService() {
        try {
            return this.aw.createUserApiService(this.W.d());
        }
        catch (final AuthenticationException exception) {
            MixinMinecraftProfileRefresh.G.error("Failed to verify authentication", (Throwable)exception);
            return UserApiService.OFFLINE;
        }
    }
}
