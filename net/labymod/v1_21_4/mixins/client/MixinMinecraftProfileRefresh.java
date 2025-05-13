// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins.client;

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

@Mixin({ flk.class })
public class MixinMinecraftProfileRefresh implements SessionRefresher
{
    @Shadow
    @Final
    private static Logger G;
    @Unique
    private CompletableFuture<ProfileResult> labymod$profileFuture;
    @Shadow
    @Final
    public File q;
    @Mutable
    @Final
    @Shadow
    private UserApiService aA;
    @Mutable
    @Final
    @Shadow
    private ggn aP;
    @Shadow
    @Final
    private YggdrasilAuthenticationService ay;
    @Shadow
    @Final
    private flw Z;
    @Mutable
    @Shadow
    @Final
    private fzc aM;
    @Shadow
    private ghj bv;
    @Mutable
    @Shadow
    @Final
    private hka aO;
    @Final
    @Shadow
    private MinecraftSessionService az;
    
    @Override
    public void refresh() {
        this.aA = this.labyMod$createUserApiService();
        this.aP = ggn.a(this.aA, this.Z, this.q.toPath());
        this.aM = new fzc((flk)this, this.aA);
        this.aO = new hka((flk)this, this.aA, this.Z);
        this.bv = ghj.a(ghg.a(), this.aA);
        this.labymod$profileFuture = CompletableFuture.supplyAsync(() -> this.az.fetchProfile(this.Z.b(), true), (Executor)af.j());
    }
    
    @Redirect(method = { "getGameProfile" }, at = @At(value = "INVOKE", target = "Ljava/util/concurrent/CompletableFuture;join()Ljava/lang/Object;"))
    private Object labyMod$joinProfileFuture(final CompletableFuture<ProfileResult> profileFuture) {
        final ProfileResult result = profileFuture.join();
        final UUID profileId = (result == null) ? null : result.profile().getId();
        final UUID currentId = this.Z.b();
        if (!Objects.equals(currentId, profileId) && this.labymod$profileFuture != null) {
            return this.labymod$profileFuture.join();
        }
        return result;
    }
    
    private UserApiService labyMod$createUserApiService() {
        return this.ay.createUserApiService(this.Z.d());
    }
}
