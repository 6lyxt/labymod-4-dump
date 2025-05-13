// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.client.renderer.entity.layers;

import net.labymod.api.client.gfx.pipeline.blaze3d.buffer.Blaze3DBufferSource;
import net.labymod.core.main.user.shop.bridge.ShopItemLayer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.Laby;
import org.jetbrains.annotations.NotNull;

public class VersionedShopItemLayer extends gnk<gco, fvg<gco>>
{
    private final fvg<gco> playerModel;
    
    public VersionedShopItemLayer(final gku<gco, fvg<gco>> layerParent) {
        super((gku)layerParent);
        this.playerModel = (fvg<gco>)layerParent.a();
    }
    
    public void render(@NotNull final ezz poseStack, @NotNull final gdp bufferSource, final int packedLight, @NotNull final gco player, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch) {
        if (!Laby.labyAPI().config().ingame().cosmetics().renderCosmetics().get()) {
            return;
        }
        final Stack stack = ((VanillaStackAccessor)poseStack).stack(bufferSource);
        final LabyMod labyMod = LabyMod.getInstance();
        final Player apiPlayer = (Player)player;
        final ShopItemLayer shopItemLayer = labyMod.getShopItemLayer();
        final Blaze3DBufferSource blaze3DBufferSource = Laby.gfx().blaze3DBufferSource();
        blaze3DBufferSource.setTemporaryBuffer(bufferSource);
        shopItemLayer.render(stack, apiPlayer, (PlayerModel)this.playerModel, packedLight, partialTicks);
        blaze3DBufferSource.resetTemporaryBuffer();
    }
}
