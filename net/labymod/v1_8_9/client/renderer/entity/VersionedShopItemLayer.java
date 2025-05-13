// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.renderer.entity;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import net.labymod.core.main.LabyMod;
import net.labymod.api.Laby;

public class VersionedShopItemLayer implements blb<bet>
{
    private final bbr playerModel;
    
    public VersionedShopItemLayer(final bln renderPlayer) {
        this.playerModel = renderPlayer.g();
    }
    
    public void doRenderLayer(final bet player, final float f, final float f1, final float partialTicks, final float f3, final float f4, final float f5, final float f6) {
        if (!Laby.labyAPI().config().ingame().cosmetics().renderCosmetics().get()) {
            return;
        }
        final LabyMod labyMod = LabyMod.getInstance();
        final Stack stack = VersionedStackProvider.DEFAULT_STACK;
        final int packedLight = MinecraftUtil.getPackedLight(player, partialTicks);
        labyMod.gfxRenderPipeline().renderEnvironmentContext().setPackedLight(packedLight);
        labyMod.getShopItemLayer().render(stack, (Player)player, (PlayerModel)this.playerModel, packedLight, partialTicks);
    }
    
    public boolean b() {
        return false;
    }
}
