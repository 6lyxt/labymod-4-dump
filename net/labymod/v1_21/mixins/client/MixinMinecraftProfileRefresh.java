// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.mixins.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.UUID;
import java.util.Objects;
import java.util.concurrent.Executor;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import org.spongepowered.asm.mixin.Mutable;
import com.mojang.authlib.minecraft.UserApiService;
import java.io.File;
import org.spongepowered.asm.mixin.Unique;
import com.mojang.authlib.yggdrasil.ProfileResult;
import java.util.concurrent.CompletableFuture;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.session.SessionRefresher;

@Mixin({ fgo.class })
public class MixinMinecraftProfileRefresh implements SessionRefresher
{
    @Shadow
    @Final
    private static Logger F;
    @Unique
    private CompletableFuture<ProfileResult> labymod$profileFuture;
    @Shadow
    @Final
    public File p;
    @Mutable
    @Final
    @Shadow
    private UserApiService aw;
    @Mutable
    @Final
    @Shadow
    private fzr aL;
    @Shadow
    @Final
    private YggdrasilAuthenticationService au;
    @Shadow
    @Final
    private fhb V;
    @Mutable
    @Shadow
    @Final
    private fsu aH;
    @Shadow
    private gao bs;
    @Mutable
    @Shadow
    @Final
    private gvj aK;
    @Final
    @Shadow
    private MinecraftSessionService av;
    
    @Override
    public void refresh() {
        this.aw = this.labyMod$createUserApiService();
        this.aL = fzr.a(this.aw, this.V, this.p.toPath());
        this.aH = new fsu((fgo)this, this.aw);
        this.aK = new gvj((fgo)this, this.aw, this.V);
        this.bs = gao.a(gal.a(), this.aw);
        this.labymod$profileFuture = CompletableFuture.supplyAsync(() -> this.av.fetchProfile(this.V.b(), true), ad.i());
    }
    
    @Redirect(method = { "getGameProfile" }, at = @At(value = "INVOKE", target = "Ljava/util/concurrent/CompletableFuture;join()Ljava/lang/Object;"))
    private Object labyMod$joinProfileFuture(final CompletableFuture<ProfileResult> profileFuture) {
        final ProfileResult result = profileFuture.join();
        final UUID profileId = (result == null) ? null : result.profile().getId();
        final UUID currentId = this.V.b();
        if (!Objects.equals(currentId, profileId) && this.labymod$profileFuture != null) {
            return this.labymod$profileFuture.join();
        }
        return result;
    }
    
    private UserApiService labyMod$createUserApiService() {
        return this.au.createUserApiService(this.V.d());
    }
}
