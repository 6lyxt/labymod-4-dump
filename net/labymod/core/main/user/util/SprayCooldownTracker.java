// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.util;

import net.labymod.api.user.group.Group;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.core.main.user.shop.spray.SprayObject;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.user.GameUser;

public class SprayCooldownTracker
{
    private static final long NETWORK_DELAY = 50L;
    private final GameUser user;
    private long lastServerTimeSprayed;
    private long lastClientTimeSprayed;
    private long lastTimeSoundPlayed;
    
    public SprayCooldownTracker(final GameUser user) {
        this.user = user;
    }
    
    public void sprayClient() {
        this.lastClientTimeSprayed = TimeUtil.getCurrentTimeMillis();
        this.lastClientTimeSprayed += this.getNextSprayTime();
    }
    
    public boolean canSprayClient() {
        return this.lastClientTimeSprayed <= TimeUtil.getCurrentTimeMillis();
    }
    
    public long getClientDuration() {
        return this.lastClientTimeSprayed - TimeUtil.getCurrentTimeMillis();
    }
    
    public void sprayServer() {
        this.lastServerTimeSprayed = TimeUtil.getCurrentTimeMillis();
    }
    
    public void playSound(final SprayObject object, final boolean force) {
        if (!this.canPlaySound() && !force) {
            return;
        }
        this.lastTimeSoundPlayed = TimeUtil.getCurrentTimeMillis();
        Laby.labyAPI().minecraft().sounds().playSound(Constants.Resources.SOUND_SPRAY_CAN_SPRAY, 1.0f, 1.0f, object.position());
    }
    
    public boolean canSprayServer() {
        long nextSpray = this.getNextSprayTime();
        nextSpray -= 50L;
        final long remainingCooldown = this.lastServerTimeSprayed + nextSpray;
        return remainingCooldown <= TimeUtil.getCurrentTimeMillis();
    }
    
    private long getNextSprayTime() {
        final Group group = this.user.visibleGroup();
        if (group.isLabyModPlus() || group.isStaff()) {
            return 2000L;
        }
        return 60000L;
    }
    
    public boolean canPlaySound() {
        return this.lastTimeSoundPlayed + 30000L <= TimeUtil.getCurrentTimeMillis();
    }
}
