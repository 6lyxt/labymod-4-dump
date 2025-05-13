// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.player;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.session.MinecraftServices;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.function.Supplier;
import java.util.HashMap;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import net.labymod.api.client.resources.CompletableResourceLocation;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_12_2.client.player.PlayerSkinAccessor;
import net.labymod.api.client.network.PlayerSkin;

@Mixin({ bsc.class })
public abstract class MixinPlayerInfo implements PlayerSkin, PlayerSkinAccessor
{
    @Unique
    private final Map<MinecraftProfileTexture.Type, CompletableResourceLocation> labymod$completables;
    @Shadow
    Map<MinecraftProfileTexture.Type, nf> a;
    @Shadow
    private String f;
    
    public MixinPlayerInfo() {
        this.labymod$completables = new HashMap<MinecraftProfileTexture.Type, CompletableResourceLocation>();
    }
    
    @Shadow
    public abstract nf g();
    
    @Shadow
    public abstract nf h();
    
    @Shadow
    public abstract nf i();
    
    private CompletableResourceLocation getCompletableResourceLocation(final MinecraftProfileTexture.Type type, final Supplier<nf> locationSupplier) {
        if (this.labymod$completables.containsKey(type)) {
            return this.labymod$completables.get(type);
        }
        final CompletableResourceLocation completable = new CompletableResourceLocation((ResourceLocation)locationSupplier.get(), this.a.containsKey(type));
        this.labymod$completables.put(type, completable);
        return completable;
    }
    
    @Unique
    private void updateCompletableResourceLocation(final MinecraftProfileTexture.Type type, final Supplier<nf> locationSupplier) {
        this.getCompletableResourceLocation(type, locationSupplier).updateCompletable((ResourceLocation)locationSupplier.get());
    }
    
    @Override
    public void setSkinTexture(@Nullable final ResourceLocation skinTexture) {
        this.a.put(MinecraftProfileTexture.Type.SKIN, (nf)skinTexture);
        this.updateCompletableResourceLocation(MinecraftProfileTexture.Type.SKIN, this::g);
    }
    
    @Override
    public void setCapeTexture(@Nullable final ResourceLocation capeTexture) {
        this.a.put(MinecraftProfileTexture.Type.CAPE, (nf)capeTexture);
        this.updateCompletableResourceLocation(MinecraftProfileTexture.Type.CAPE, this::h);
    }
    
    @Override
    public void setElytraTexture(@Nullable final ResourceLocation elytraTexture) {
        this.a.put(MinecraftProfileTexture.Type.ELYTRA, (nf)elytraTexture);
        this.updateCompletableResourceLocation(MinecraftProfileTexture.Type.ELYTRA, this::i);
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
        return this.getCompletableResourceLocation(MinecraftProfileTexture.Type.ELYTRA, this::i);
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
    
    @Override
    public Map<MinecraftProfileTexture.Type, CompletableResourceLocation> labymod4$getPendingCompletables() {
        return this.labymod$completables;
    }
}
