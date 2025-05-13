// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.entity.player.interaction.defaults;

import net.labymod.api.client.chat.command.Command;
import net.labymod.core.labyconnect.commands.CapeReportCommand;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.LabyConnect;
import java.util.UUID;
import net.labymod.api.labyconnect.protocol.LabyConnectState;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.chat.command.CommandService;
import net.labymod.api.client.entity.player.interaction.AbstractBulletPoint;

public class ReportCloakBulletPoint extends AbstractBulletPoint
{
    private final CommandService commandService;
    
    public ReportCloakBulletPoint() {
        super(Component.translatable("labymod.activity.interactionMenu.entry.reportCloak", new Component[0]));
        this.commandService = Laby.references().commandService();
    }
    
    @Override
    public boolean isVisible(final Player player) {
        final UUID target = player.getUniqueId();
        final DefaultGameUser gameUser = (DefaultGameUser)LabyMod.references().gameUserService().gameUser(target);
        if (!gameUser.getUserData().hasItem(0)) {
            return false;
        }
        final LabyConnect labyConnect = Laby.labyAPI().labyConnect();
        final LabyConnectSession session = labyConnect.getSession();
        return labyConnect.state() == LabyConnectState.PLAY && session != null && session.getFriend(target) == null;
    }
    
    @Override
    public void execute(final Player player) {
        this.commandService.fireCommand(CapeReportCommand.class, "capereport", new String[] { player.getName() });
    }
}
