// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.badge;

import net.labymod.api.user.group.Group;
import net.labymod.api.user.GameUser;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.Textures;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.user.GameUserService;
import net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer;

public class RankBadgeRenderer extends BadgeRenderer
{
    private final GameUserService gameUserService;
    private final Icon wolfIcon;
    private final Icon groupIcon;
    
    public RankBadgeRenderer() {
        this.gameUserService = LabyMod.getInstance().gameUserService();
        this.wolfIcon = Textures.SpriteLabyMod.DEFAULT_WOLF_BLURRY;
        this.groupIcon = Textures.SpriteLabyMod.WHITE_WOLF_BLURRY;
    }
    
    @Override
    public void render(final Stack stack, final float x, final float y, final NetworkPlayerInfo player) {
        final GameUser gameUser = this.gameUserService.gameUser(player.profile().getUniqueId());
        if (gameUser.isUsingLabyMod()) {
            final Group visibleGroup = gameUser.visibleGroup();
            if (visibleGroup.isDefault()) {
                this.wolfIcon.render(stack, x, y, 8.0f);
            }
            else {
                this.groupIcon.render(stack, x, y, 8.0f, false, visibleGroup.getColor().getRGB());
            }
        }
    }
    
    public boolean isVisible(final NetworkPlayerInfo player) {
        return this.gameUserService.gameUser(player.profile().getUniqueId()).isUsingLabyMod();
    }
    
    @Override
    public int getSize() {
        return 7;
    }
    
    @Override
    protected boolean begin(final Stack stack) {
        return LabyMod.getInstance().config().multiplayer().tabList().labyModBadge().get();
    }
}
