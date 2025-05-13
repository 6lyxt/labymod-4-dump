// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins.client.player;

import net.labymod.api.util.concurrent.AbstractCompletable;
import org.spongepowered.asm.mixin.Overwrite;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.session.MinecraftServices;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Unique;
import net.labymod.api.client.resources.CompletableResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.network.PlayerSkin;

@Mixin({ gqa.class })
public class MixinPlayerSkin implements PlayerSkin
{
    @Unique
    private CompletableResourceLocation labymod4$skin;
    @Unique
    private CompletableResourceLocation labymod4$cape;
    @Unique
    private CompletableResourceLocation labymod4$elytra;
    @Unique
    private gqa.a labymod4$skinModel;
    
    @Inject(method = { "<init>" }, at = { @At("RETURN") })
    private void init(final alf skinTexture, final String skinTextureUrl, final alf capeTexture, final alf elytraTexture, final gqa.a skinModel, final boolean secure, final CallbackInfo callback) {
        this.labymod4$skin = new CompletableResourceLocation((ResourceLocation)skinTexture, true);
        this.labymod4$cape = new CompletableResourceLocation((ResourceLocation)capeTexture, true);
        this.labymod4$elytra = new CompletableResourceLocation((ResourceLocation)elytraTexture, true);
        this.labymod4$skinModel = skinModel;
    }
    
    @Override
    public void setSkinTexture(@Nullable final ResourceLocation skinTexture) {
        this.labymod4$skin.updateCompletable((skinTexture == null) ? ((ResourceLocation)gps.a()) : skinTexture);
    }
    
    @Override
    public void setCapeTexture(@Nullable final ResourceLocation capeTexture) {
        this.labymod4$cape.updateCompletable(capeTexture);
    }
    
    @Override
    public void setElytraTexture(@Nullable final ResourceLocation elytraTexture) {
        this.labymod4$elytra.updateCompletable(elytraTexture);
    }
    
    @Override
    public void setSkinVariant(@NotNull final MinecraftServices.SkinVariant variant) {
        final boolean isSlim = variant == MinecraftServices.SkinVariant.SLIM;
        this.labymod4$skinModel = (isSlim ? gqa.a.a : gqa.a.b);
    }
    
    @NotNull
    @Override
    public CompletableResourceLocation getCompletableSkinTexture() {
        return this.labymod4$skin;
    }
    
    @NotNull
    @Override
    public CompletableResourceLocation getCompletableCapeTexture() {
        return this.labymod4$cape;
    }
    
    @NotNull
    @Override
    public CompletableResourceLocation getCompletableElytraTexture() {
        return this.labymod4$elytra;
    }
    
    @NotNull
    @Override
    public MinecraftServices.SkinVariant getSkinVariant() {
        if (this.labymod4$skinModel == null) {
            return MinecraftServices.SkinVariant.CLASSIC;
        }
        return switch (this.labymod4$skinModel) {
            case a -> MinecraftServices.SkinVariant.SLIM;
            default -> MinecraftServices.SkinVariant.CLASSIC;
        };
    }
    
    @Overwrite
    @NotNull
    public alf a() {
        return ((AbstractCompletable<alf>)this.labymod4$skin).getCompleted();
    }
    
    @Overwrite
    @Nullable
    public alf c() {
        return ((AbstractCompletable<alf>)this.labymod4$cape).getCompleted();
    }
    
    @Overwrite
    @Nullable
    public alf d() {
        return ((AbstractCompletable<alf>)this.labymod4$elytra).getCompleted();
    }
    
    @Overwrite
    @NotNull
    public gqa.a e() {
        return this.labymod4$skinModel;
    }
}
