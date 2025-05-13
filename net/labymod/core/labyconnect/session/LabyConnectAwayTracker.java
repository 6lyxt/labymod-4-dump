// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.session;

import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.labyconnect.protocol.model.UserStatus;
import net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.Laby;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.client.Minecraft;

public class LabyConnectAwayTracker
{
    private static final long TIME_UNTIL_AFK = 300000L;
    private final Minecraft minecraft;
    private final LabyConnect labyConnect;
    private long timeLastMovement;
    private int lastMouseX;
    private int lastMouseY;
    private boolean away;
    private boolean statusChanged;
    
    public LabyConnectAwayTracker(final LabyConnect labyConnect) {
        this.minecraft = Laby.labyAPI().minecraft();
        this.labyConnect = labyConnect;
        this.timeLastMovement = TimeUtil.getMillis();
    }
    
    private void updateState(final boolean away) {
        this.away = away;
        final LabyConnectConfigAccessor config = this.labyConnect.configProvider().get();
        final ConfigProperty<UserStatus> property = config.onlineStatus();
        if (away) {
            if (property.get() == UserStatus.ONLINE) {
                property.set(UserStatus.AWAY);
                this.statusChanged = true;
            }
        }
        else {
            if (this.statusChanged && property.get() == UserStatus.AWAY) {
                property.set(UserStatus.ONLINE);
            }
            this.statusChanged = false;
        }
    }
    
    @Subscribe
    public void onTick(final GameTickEvent event) {
        if (event.phase() != Phase.PRE) {
            return;
        }
        final Mouse mouse = this.minecraft.absoluteMouse();
        final int currentMouseX = mouse.getX();
        final int currentMouseY = mouse.getY();
        if (currentMouseX == this.lastMouseX && currentMouseY == this.lastMouseY) {
            final long timePassed = TimeUtil.getMillis() - this.timeLastMovement;
            if (!this.away && timePassed > 300000L) {
                this.updateState(true);
            }
        }
        else {
            this.timeLastMovement = TimeUtil.getMillis();
            if (this.away) {
                this.updateState(false);
            }
        }
        this.lastMouseX = currentMouseX;
        this.lastMouseY = currentMouseY;
    }
    
    @Subscribe
    public void keyPress(final KeyEvent event) {
        this.timeLastMovement = TimeUtil.getMillis();
        if (this.away) {
            this.updateState(false);
        }
    }
}
