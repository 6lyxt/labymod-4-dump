// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client.player;

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

@Mixin({ eze.class })
public abstract class MixinPlayerInfo implements PlayerSkin
{
    @Unique
    private final Map<MinecraftProfileTexture.Type, CompletableResourceLocation> labymod$completables;
    @Final
    @Shadow
    private Map<MinecraftProfileTexture.Type, acf> b;
    @Shadow
    private String f;
    
    public MixinPlayerInfo() {
        this.labymod$completables = new HashMap<MinecraftProfileTexture.Type, CompletableResourceLocation>();
    }
    
    @Shadow
    @NotNull
    public abstract acf shadow$j();
    
    @Shadow
    @Nullable
    public abstract acf shadow$k();
    
    @Shadow
    @Nullable
    public abstract acf shadow$l();
    
    @Inject(method = { "lambda$registerTextures$0" }, at = { @At("RETURN") })
    private void onRegisterTexturesReturn(final MinecraftProfileTexture.Type type, final acf resourceLocation, final MinecraftProfileTexture minecraftProfileTexture, final CallbackInfo ci) {
        final CompletableResourceLocation completable = this.labymod$completables.get(type);
        if (completable != null) {
            completable.executeCompletableListeners((ResourceLocation)resourceLocation);
        }
    }
    
    @Unique
    private CompletableResourceLocation getCompletableResourceLocation(final MinecraftProfileTexture.Type type, final Supplier<acf> locationSupplier) {
        if (this.labymod$completables.containsKey(type)) {
            return this.labymod$completables.get(type);
        }
        final CompletableResourceLocation completable = new CompletableResourceLocation((ResourceLocation)locationSupplier.get(), this.b.containsKey(type));
        this.labymod$completables.put(type, completable);
        return completable;
    }
    
    @Unique
    private void updateCompletableResourceLocation(final MinecraftProfileTexture.Type type, final Supplier<acf> locationSupplier) {
        this.getCompletableResourceLocation(type, locationSupplier).updateCompletable((ResourceLocation)locationSupplier.get());
    }
    
    @Override
    public void setSkinTexture(@Nullable final ResourceLocation skinTexture) {
        this.b.put(MinecraftProfileTexture.Type.SKIN, (acf)skinTexture);
        this.updateCompletableResourceLocation(MinecraftProfileTexture.Type.SKIN, this::shadow$j);
    }
    
    @Override
    public void setCapeTexture(@Nullable final ResourceLocation capeTexture) {
        this.b.put(MinecraftProfileTexture.Type.CAPE, (acf)capeTexture);
        this.updateCompletableResourceLocation(MinecraftProfileTexture.Type.CAPE, this::shadow$k);
    }
    
    @Override
    public void setElytraTexture(@Nullable final ResourceLocation elytraTexture) {
        this.b.put(MinecraftProfileTexture.Type.ELYTRA, (acf)elytraTexture);
        this.updateCompletableResourceLocation(MinecraftProfileTexture.Type.ELYTRA, this::shadow$l);
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
        return this.getCompletableResourceLocation(MinecraftProfileTexture.Type.SKIN, this::shadow$j);
    }
    
    @NotNull
    @Override
    public CompletableResourceLocation getCompletableCapeTexture() {
        return this.getCompletableResourceLocation(MinecraftProfileTexture.Type.CAPE, this::shadow$k);
    }
    
    @NotNull
    @Override
    public CompletableResourceLocation getCompletableElytraTexture() {
        return this.getCompletableResourceLocation(MinecraftProfileTexture.Type.ELYTRA, this::shadow$l);
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
