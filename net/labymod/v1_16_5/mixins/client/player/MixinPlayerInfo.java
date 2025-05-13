// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.player;

import net.labymod.api.client.session.MinecraftServices;
import java.util.function.Supplier;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Unique;
import net.labymod.api.client.resources.CompletableResourceLocation;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.network.PlayerSkin;

@Mixin({ dwx.class })
public abstract class MixinPlayerInfo implements PlayerSkin
{
    @Unique
    private final Map<MinecraftProfileTexture.Type, CompletableResourceLocation> labymod$completables;
    @Final
    @Shadow
    private Map<MinecraftProfileTexture.Type, vk> b;
    @Shadow
    private String f;
    
    public MixinPlayerInfo() {
        this.labymod$completables = new HashMap<MinecraftProfileTexture.Type, CompletableResourceLocation>();
    }
    
    @Shadow
    @NotNull
    public abstract vk shadow$g();
    
    @Shadow
    @Nullable
    public abstract vk shadow$h();
    
    @Shadow
    @Nullable
    public abstract vk shadow$i();
    
    @Inject(method = { "lambda$registerTextures$0" }, at = { @At("RETURN") })
    private void onRegisterTexturesReturn(final MinecraftProfileTexture.Type type, final vk resourceLocation, final MinecraftProfileTexture minecraftProfileTexture, final CallbackInfo ci) {
        final CompletableResourceLocation completable = this.labymod$completables.get(type);
        if (completable != null) {
            completable.executeCompletableListeners((ResourceLocation)resourceLocation);
        }
    }
    
    @Unique
    private CompletableResourceLocation getCompletableResourceLocation(final MinecraftProfileTexture.Type type, final Supplier<vk> locationSupplier) {
        if (this.labymod$completables.containsKey(type)) {
            return this.labymod$completables.get(type);
        }
        final CompletableResourceLocation completable = new CompletableResourceLocation((ResourceLocation)locationSupplier.get(), this.b.containsKey(type));
        this.labymod$completables.put(type, completable);
        return completable;
    }
    
    @Unique
    private void updateCompletableResourceLocation(final MinecraftProfileTexture.Type type, final Supplier<vk> locationSupplier) {
        this.getCompletableResourceLocation(type, locationSupplier).updateCompletable((ResourceLocation)locationSupplier.get());
    }
    
    @Override
    public void setSkinTexture(@Nullable final ResourceLocation skinTexture) {
        this.b.put(MinecraftProfileTexture.Type.SKIN, (vk)skinTexture);
        this.updateCompletableResourceLocation(MinecraftProfileTexture.Type.SKIN, this::shadow$g);
    }
    
    @Override
    public void setCapeTexture(@Nullable final ResourceLocation capeTexture) {
        this.b.put(MinecraftProfileTexture.Type.CAPE, (vk)capeTexture);
        this.updateCompletableResourceLocation(MinecraftProfileTexture.Type.CAPE, this::shadow$h);
    }
    
    @Override
    public void setElytraTexture(@Nullable final ResourceLocation elytraTexture) {
        this.b.put(MinecraftProfileTexture.Type.ELYTRA, (vk)elytraTexture);
        this.updateCompletableResourceLocation(MinecraftProfileTexture.Type.ELYTRA, this::shadow$i);
    }
    
    @Override
    public void setSkinVariant(@NotNull final MinecraftServices.SkinVariant variant) {
        this.f = switch (variant) {
            default -> throw new MatchException(null, null);
            case SLIM -> "slim";
            case CLASSIC -> "default";
        };
    }
    
    @NotNull
    @Override
    public CompletableResourceLocation getCompletableSkinTexture() {
        return this.getCompletableResourceLocation(MinecraftProfileTexture.Type.SKIN, this::shadow$g);
    }
    
    @NotNull
    @Override
    public CompletableResourceLocation getCompletableCapeTexture() {
        return this.getCompletableResourceLocation(MinecraftProfileTexture.Type.CAPE, this::shadow$h);
    }
    
    @NotNull
    @Override
    public CompletableResourceLocation getCompletableElytraTexture() {
        return this.getCompletableResourceLocation(MinecraftProfileTexture.Type.ELYTRA, this::shadow$i);
    }
    
    @NotNull
    @Override
    public MinecraftServices.SkinVariant getSkinVariant() {
        if (this.f == null) {
            return MinecraftServices.SkinVariant.CLASSIC;
        }
        final String f = this.f;
        return switch (f) {
            case "slim" -> MinecraftServices.SkinVariant.SLIM;
            default -> MinecraftServices.SkinVariant.CLASSIC;
        };
    }
}
