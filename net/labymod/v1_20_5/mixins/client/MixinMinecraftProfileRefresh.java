// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins.client;

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

@Mixin({ ffg.class })
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
    private UserApiService ax;
    @Mutable
    @Final
    @Shadow
    private fyi aM;
    @Shadow
    @Final
    private YggdrasilAuthenticationService av;
    @Shadow
    @Final
    private ffu W;
    @Mutable
    @Shadow
    @Final
    private frl aI;
    @Shadow
    private fze bu;
    @Mutable
    @Shadow
    @Final
    private gua aL;
    @Final
    @Shadow
    private MinecraftSessionService aw;
    
    @Override
    public void refresh() {
        this.ax = this.labyMod$createUserApiService();
        this.aM = fyi.a(this.ax, this.W, this.p.toPath());
        this.aI = new frl((ffg)this, this.ax);
        this.aL = new gua((ffg)this, this.ax, this.W);
        this.bu = fze.a(fzb.a(), this.ax);
        this.labymod$profileFuture = CompletableFuture.supplyAsync(() -> this.aw.fetchProfile(this.W.b(), true), ac.i());
    }
    
    @Redirect(method = { "getGameProfile" }, at = @At(value = "INVOKE", target = "Ljava/util/concurrent/CompletableFuture;join()Ljava/lang/Object;"))
    private Object labyMod$joinProfileFuture(final CompletableFuture<ProfileResult> profileFuture) {
        final ProfileResult result = profileFuture.join();
        final UUID profileId = (result == null) ? null : result.profile().getId();
        final UUID currentId = this.W.b();
        if (!Objects.equals(currentId, profileId) && this.labymod$profileFuture != null) {
            return this.labymod$profileFuture.join();
        }
        return result;
    }
    
    private UserApiService labyMod$createUserApiService() {
        return this.av.createUserApiService(this.W.d());
    }
}
