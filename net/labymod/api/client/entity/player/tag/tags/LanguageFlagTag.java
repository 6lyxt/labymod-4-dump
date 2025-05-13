// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.tag.tags;

import net.labymod.api.util.CountryCode;
import net.labymod.api.user.GameUser;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.LabyAPI;

public class LanguageFlagTag extends IconTag
{
    private final LabyAPI labyAPI;
    
    public LanguageFlagTag(final LabyAPI labyAPI) {
        super(5.0f, 3.5f);
        this.labyAPI = labyAPI;
    }
    
    @Override
    public boolean isVisible() {
        return this.labyAPI.config().ingame().showCountryFlagBesideName().get() && super.isVisible();
    }
    
    @Override
    public Icon getIcon() {
        if (!(this.entity instanceof Player)) {
            return null;
        }
        final Player player = (Player)this.entity;
        final GameUser gameUser = player.gameUser();
        final CountryCode countryCode = gameUser.getCountryCode();
        return (countryCode == null) ? null : countryCode.getIcon();
    }
}
