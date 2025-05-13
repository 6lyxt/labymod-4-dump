// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client;

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

@Mixin({ ejf.class })
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
    private UserApiService ay;
    @Mutable
    @Final
    @Shadow
    private ezf aL;
    @Shadow
    @Final
    private YggdrasilAuthenticationService av;
    @Shadow
    @Final
    private ejv W;
    @Mutable
    @Shadow
    @Final
    private eta aH;
    @Mutable
    @Shadow
    @Final
    private fsv aK;
    @Shadow
    private ezu bt;
    
    @Override
    public void refresh() {
        this.ay = this.labyMod$createUserApiService();
        this.aL = ezf.a(this.ay, this.W, this.p.toPath());
        this.aH = new eta((ejf)this, this.ay);
        this.aK = new fsv((ejf)this, this.ay, this.W);
        this.bt = ezu.a(ezs.a(), this.ay);
    }
    
    private UserApiService labyMod$createUserApiService() {
        try {
            return this.av.createUserApiService(this.W.d());
        }
        catch (final AuthenticationException exception) {
            MixinMinecraftProfileRefresh.G.error("Failed to verify authentication", (Throwable)exception);
            return UserApiService.OFFLINE;
        }
    }
}
