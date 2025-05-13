// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.client;

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

@Mixin({ evi.class })
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
    private UserApiService ay;
    @Mutable
    @Final
    @Shadow
    private foc aM;
    @Shadow
    @Final
    private YggdrasilAuthenticationService aw;
    @Shadow
    @Final
    private evx W;
    @Mutable
    @Shadow
    @Final
    private fhj aI;
    @Shadow
    private fow bv;
    @Mutable
    @Shadow
    @Final
    private gji aL;
    @Final
    @Shadow
    private MinecraftSessionService ax;
    
    @Override
    public void refresh() {
        this.ay = this.labyMod$createUserApiService();
        this.aM = foc.a(this.ay, this.W, this.p.toPath());
        this.aI = new fhj((evi)this, this.ay);
        this.aL = new gji((evi)this, this.ay, this.W);
        this.bv = fow.a(fot.a(), this.ay);
        this.labymod$profileFuture = CompletableFuture.supplyAsync(() -> this.ax.fetchProfile(this.W.b(), true), ac.h());
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
        return this.aw.createUserApiService(this.W.d());
    }
}
