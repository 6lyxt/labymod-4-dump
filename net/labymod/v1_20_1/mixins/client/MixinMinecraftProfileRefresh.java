// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.client;

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

@Mixin({ enn.class })
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
    private ffc aK;
    @Shadow
    @Final
    private YggdrasilAuthenticationService aw;
    @Shadow
    @Final
    private eoc W;
    @Mutable
    @Shadow
    @Final
    private eys aG;
    @Mutable
    @Shadow
    @Final
    private fzg aJ;
    @Shadow
    private ffs bt;
    
    @Override
    public void refresh() {
        this.ay = this.labyMod$createUserApiService();
        this.aK = ffc.a(this.ay, this.W, this.p.toPath());
        this.aG = new eys((enn)this, this.ay);
        this.aJ = new fzg((enn)this, this.ay, this.W);
        this.bt = ffs.a(ffq.a(), this.ay);
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
