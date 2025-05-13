// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.entity.player.interaction.defaults;

import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.tab.NameHistoryActivity;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.Laby;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.interaction.AbstractBulletPoint;

public class NameHistoryBulletPoint extends AbstractBulletPoint
{
    public NameHistoryBulletPoint() {
        super(Component.translatable("labymod.activity.interactionMenu.entry.nameHistory", new Component[0]));
    }
    
    @Override
    public void execute(final Player player) {
        final String name = player.getName();
        final NameHistoryActivity activity = LabyMod.references().nameHistoryActivity();
        activity.scheduleQuery(name);
        Laby.labyAPI().minecraft().minecraftWindow().displayScreen(activity);
    }
}
