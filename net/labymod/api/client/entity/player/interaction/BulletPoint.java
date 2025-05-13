// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.interaction;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;

public interface BulletPoint
{
    Component getTitle();
    
    Icon getIcon();
    
    void execute(final Player p0);
    
    default boolean isVisible(final Player playerInfo) {
        return true;
    }
}
