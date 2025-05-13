// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client;

import com.mojang.authlib.exceptions.AuthenticationException;
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

@Mixin({ eqv.class })
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
    private fjg aK;
    @Shadow
    @Final
    private YggdrasilAuthenticationService av;
    @Shadow
    @Final
    private erk V;
    @Mutable
    @Shadow
    @Final
    private fcq aG;
    @Mutable
    @Shadow
    @Final
    private gdu aJ;
    @Shadow
    private fjz bs;
    @Final
    @Shadow
    private MinecraftSessionService aw;
    
    @Override
    public void refresh() {
        this.ax = this.labyMod$createUserApiService();
        this.aK = fjg.a(this.ax, this.V, this.p.toPath());
        this.aG = new fcq((eqv)this, this.ax);
        this.aJ = new gdu((eqv)this, this.ax, this.V);
        this.bs = fjz.a(fjw.a(), this.ax);
        this.labymod$profileFuture = CompletableFuture.supplyAsync(() -> this.aw.fetchProfile(this.V.b(), true), ac.g());
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
        try {
            return this.av.createUserApiService(this.V.d());
        }
        catch (final AuthenticationException exception) {
            MixinMinecraftProfileRefresh.F.error("Failed to verify authentication", (Throwable)exception);
            return UserApiService.OFFLINE;
        }
    }
}
