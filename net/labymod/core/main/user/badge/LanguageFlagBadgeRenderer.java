// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.badge;

import net.labymod.api.util.CountryCode;
import net.labymod.api.user.GameUser;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.core.main.LabyMod;
import net.labymod.api.user.GameUserService;
import net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer;

public class LanguageFlagBadgeRenderer extends BadgeRenderer
{
    private final GameUserService gameUserService;
    
    public LanguageFlagBadgeRenderer() {
        this.gameUserService = LabyMod.getInstance().gameUserService();
    }
    
    @Override
    public void render(final Stack stack, final float x, final float y, final NetworkPlayerInfo player) {
        final GameUser gameUser = this.gameUserService.gameUser(player.profile().getUniqueId());
        final CountryCode countryCode = gameUser.getCountryCode();
        if (countryCode == null) {
            return;
        }
        countryCode.getIcon().render(stack, x, y + 1.0f, 10.0f, 6.0f);
    }
    
    public boolean isVisible(final NetworkPlayerInfo player) {
        return this.gameUserService.gameUser(player.profile().getUniqueId()).getCountryCode() != null;
    }
    
    @Override
    public int getSize() {
        return 10;
    }
}
