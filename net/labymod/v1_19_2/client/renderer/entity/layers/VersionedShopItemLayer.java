// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.client.renderer.entity.layers;

import net.labymod.api.client.gfx.pipeline.blaze3d.buffer.Blaze3DBufferSource;
import net.labymod.core.main.user.shop.bridge.ShopItemLayer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.Laby;
import org.jetbrains.annotations.NotNull;

public class VersionedShopItemLayer extends fis<eyt, esn<eyt>>
{
    private final esn<eyt> playerModel;
    
    public VersionedShopItemLayer(final fgg<eyt, esn<eyt>> layerParent) {
        super((fgg)layerParent);
        this.playerModel = (esn<eyt>)layerParent.a();
    }
    
    public void render(@NotNull final eaq poseStack, @NotNull final ezs bufferSource, final int packedLight, @NotNull final eyt player, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch) {
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
