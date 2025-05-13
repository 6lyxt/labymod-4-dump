// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.renderer.entity.layers;

import net.labymod.api.client.gfx.pipeline.blaze3d.buffer.Blaze3DBufferSource;
import net.labymod.core.main.user.shop.bridge.ShopItemLayer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.Laby;
import net.labymod.v1_21_4.client.util.EntityRenderStateAccessor;
import org.jetbrains.annotations.NotNull;

public class VersionedShopItemLayer extends gwu<gzx, gdh>
{
    private final gdh playerModel;
    
    public VersionedShopItemLayer(final guc<gzx, gdh> layerParent) {
        super((guc)layerParent);
        this.playerModel = (gdh)layerParent.c();
    }
    
    public void render(@NotNull final ffv poseStack, @NotNull final glz bufferSource, final int packedLightCoords, @NotNull final gzx state, final float yRot, final float xRot) {
        final EntityRenderStateAccessor<gku> playerState = EntityRenderStateAccessor.self(state);
        if (playerState == null) {
            return;
        }
        if (!Laby.labyAPI().config().ingame().cosmetics().renderCosmetics().get()) {
            return;
        }
        final Stack stack = ((VanillaStackAccessor)poseStack).stack(bufferSource);
        final LabyMod labyMod = LabyMod.getInstance();
        final Player apiPlayer = (Player)playerState.labyMod$getEntity();
        final ShopItemLayer shopItemLayer = labyMod.getShopItemLayer();
        final Blaze3DBufferSource blaze3DBufferSource = Laby.gfx().blaze3DBufferSource();
        blaze3DBufferSource.setTemporaryBuffer(bufferSource);
        shopItemLayer.render(stack, apiPlayer, (PlayerModel)this.playerModel, packedLightCoords, Laby.labyAPI().minecraft().getPartialTicks());
        blaze3DBufferSource.resetTemporaryBuffer();
    }
}
