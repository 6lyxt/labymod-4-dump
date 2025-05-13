// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.entity.player.interaction.defaults;

import java.util.Locale;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.interaction.AbstractBulletPoint;

public class LabyNetBulletPoint extends AbstractBulletPoint
{
    public LabyNetBulletPoint() {
        super(Component.translatable("labymod.activity.interactionMenu.entry.labynetProfile", new Component[0]));
    }
    
    @Override
    public void execute(final Player player) {
        OperatingSystem.getPlatform().openUrl(String.format(Locale.ROOT, "https://laby.net/@%s", player.getName()));
    }
}
