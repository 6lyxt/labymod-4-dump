// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.player;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.session.MinecraftServices;
import org.jetbrains.annotations.Nullable;
import java.util.function.Supplier;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.HashMap;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import net.labymod.api.client.resources.CompletableResourceLocation;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_8_9.client.player.PlayerSkinAccessor;
import net.labymod.api.client.network.PlayerSkin;

@Mixin({ bdc.class })
public abstract class MixinPlayerInfo implements PlayerSkin, PlayerSkinAccessor
{
    @Unique
    private final Map<MinecraftProfileTexture.Type, CompletableResourceLocation> labymod$completables;
    @Unique
    private final CompletableResourceLocation labymod4$Elytra;
    @Shadow
    private jy e;
    @Shadow
    private jy f;
    @Shadow
    private String g;
    
    public MixinPlayerInfo() {
        this.labymod$completables = new HashMap<MinecraftProfileTexture.Type, CompletableResourceLocation>();
        this.labymod4$Elytra = new CompletableResourceLocation(null, true);
    }
    
    @Shadow
    public abstract jy g();
    
    @Shadow
    public abstract jy h();
    
    private CompletableResourceLocation getCompletableResourceLocation(final MinecraftProfileTexture.Type type, final Supplier<jy> locationSupplier) {
        if (this.labymod$completables.containsKey(type)) {
            return this.labymod$completables.get(type);
        }
        final boolean isLoaded = switch (type) {
            default -> throw new MatchException(null, null);
            case SKIN -> this.e != null;
            case CAPE -> this.f != null;
        };
        final CompletableResourceLocation completable = new CompletableResourceLocation((ResourceLocation)locationSupplier.get(), isLoaded);
        this.labymod$completables.put(type, completable);
        return completable;
    }
    
    @Unique
    private void updateCompletableResourceLocation(final MinecraftProfileTexture.Type type, final Supplier<jy> locationSupplier) {
        this.getCompletableResourceLocation(type, locationSupplier).updateCompletable((ResourceLocation)locationSupplier.get());
    }
    
    @Override
    public void setSkinTexture(@Nullable final ResourceLocation skinTexture) {
        this.e = (jy)skinTexture;
        this.updateCompletableResourceLocation(MinecraftProfileTexture.Type.SKIN, this::g);
    }
    
    @Override
    public void setCapeTexture(@Nullable final ResourceLocation capeTexture) {
        this.f = (jy)capeTexture;
        this.updateCompletableResourceLocation(MinecraftProfileTexture.Type.CAPE, this::h);
    }
    
    @Override
    public void setElytraTexture(@Nullable final ResourceLocation elytraTexture) {
    }
    
    @Override
    public void setSkinVariant(@NotNull final MinecraftServices.SkinVariant variant) {
        this.g = switch (variant) {
            default -> throw new MatchException(null, null);
            case SLIM -> "slim";
            case CLASSIC -> "default";
        };
    }
    
    @NotNull
    @Override
    public CompletableResourceLocation getCompletableSkinTexture() {
        return this.getCompletableResourceLocation(MinecraftProfileTexture.Type.SKIN, this::g);
    }
    
    @NotNull
    @Override
    public CompletableResourceLocation getCompletableCapeTexture() {
        return this.getCompletableResourceLocation(MinecraftProfileTexture.Type.CAPE, this::h);
    }
    
    @NotNull
    @Override
    public CompletableResourceLocation getCompletableElytraTexture() {
        return this.labymod4$Elytra;
    }
    
    @NotNull
    @Override
    public MinecraftServices.SkinVariant getSkinVariant() {
        if (this.g == null) {
            return MinecraftServices.SkinVariant.CLASSIC;
        }
        final String g = this.g;
        return switch (g) {
            case "slim" -> MinecraftServices.SkinVariant.SLIM;
            default -> MinecraftServices.SkinVariant.CLASSIC;
        };
    }
    
    @Override
    public Map<MinecraftProfileTexture.Type, CompletableResourceLocation> labymod4$getPendingCompletables() {
        return this.labymod$completables;
    }
}
