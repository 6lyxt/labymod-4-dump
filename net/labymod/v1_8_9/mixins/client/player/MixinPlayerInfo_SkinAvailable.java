// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.player;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.v1_8_9.client.player.PlayerSkinAccessor;
import net.labymod.api.client.resources.CompletableResourceLocation;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = { "net.minecraft.client.network.NetworkPlayerInfo$1" })
public abstract class MixinPlayerInfo_SkinAvailable
{
    @Final
    @Shadow
    bdc a;
    
    @Inject(method = { "skinAvailable" }, at = { @At("RETURN") })
    public void skinAvailable(final MinecraftProfileTexture.Type type, final jy resourceLocation, final MinecraftProfileTexture minecraftProfileTexture, final CallbackInfo ci) {
        final CompletableResourceLocation completable = ((PlayerSkinAccessor)this.a).labymod4$getPendingCompletables().get(type);
        if (completable != null) {
            completable.executeCompletableListeners((ResourceLocation)resourceLocation);
        }
    }
}
