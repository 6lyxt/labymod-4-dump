// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.entity.player.interaction.defaults;

import net.labymod.api.labyconnect.LabyConnect;
import java.util.UUID;
import net.labymod.api.labyconnect.protocol.LabyConnectState;
import net.labymod.core.client.gui.screen.activity.activities.labyconnect.LabyConnectActivity;
import net.labymod.api.labyconnect.LabyConnectSession;
import java.util.concurrent.TimeUnit;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.core.client.gui.navigation.elements.LabyConnectNavigationElement;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.interaction.AbstractBulletPoint;

public class AddFriendBulletPoint extends AbstractBulletPoint
{
    public AddFriendBulletPoint() {
        super(Component.translatable("labymod.activity.interactionMenu.entry.addFriend", new Component[0]));
    }
    
    @Override
    public void execute(final Player player) {
        final LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session == null) {
            return;
        }
        final String username = player.getName();
        final LabyConnectActivity activity = LabyConnectNavigationElement.ACTIVITY;
        Laby.labyAPI().minecraft().minecraftWindow().displayScreen(activity);
        Task.builder(() -> Laby.labyAPI().minecraft().executeNextTick(() -> activity.desktopActivity().friendsActivity().openOutgoingRequests(username))).delay(100L, TimeUnit.MILLISECONDS).build().execute();
    }
    
    @Override
    public boolean isVisible(final Player player) {
        final UUID target = player.getUniqueId();
        final LabyConnect labyConnect = Laby.labyAPI().labyConnect();
        if (labyConnect.state() != LabyConnectState.PLAY) {
            return false;
        }
        final LabyConnectSession session = labyConnect.getSession();
        return session != null && session.getFriend(target) == null;
    }
}
