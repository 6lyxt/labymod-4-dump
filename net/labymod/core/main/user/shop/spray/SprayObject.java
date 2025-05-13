// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.spray;

import net.labymod.api.user.group.Group;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.user.GameUser;

public class SprayObject
{
    private final GameUser owner;
    private final short id;
    private final DoubleVector3 position;
    private final Direction direction;
    private final float rotation;
    private final long creationTime;
    private long adjustedCreationTime;
    private boolean adjustCreationTime;
    private float previousDuration;
    
    public SprayObject(final GameUser owner, final short id, final double x, final double y, final double z, final Direction direction, final float rotation) {
        this(owner, id, new DoubleVector3(x, y, z), direction, rotation);
    }
    
    public SprayObject(final GameUser owner, final short id, final DoubleVector3 position, final Direction direction, final float rotation) {
        this.owner = owner;
        this.id = id;
        this.position = position;
        this.direction = direction;
        this.rotation = rotation;
        this.creationTime = TimeUtil.getMillis();
    }
    
    public GameUser getOwner() {
        return this.owner;
    }
    
    public short getId() {
        return this.id;
    }
    
    public double getX() {
        return this.position.getX();
    }
    
    public double getY() {
        return this.position.getY();
    }
    
    public double getZ() {
        return this.position.getZ();
    }
    
    public DoubleVector3 position() {
        return this.position;
    }
    
    public Direction direction() {
        return this.direction;
    }
    
    public float getRotation() {
        return this.rotation;
    }
    
    public long getCreationTime() {
        if (this.adjustCreationTime) {
            final long duration = this.getDuration();
            this.adjustedCreationTime = this.creationTime - (60000L - duration - this.getLastLifespan());
            this.adjustCreationTime = false;
        }
        return (this.adjustedCreationTime == 0L) ? this.creationTime : this.adjustedCreationTime;
    }
    
    public long getAdjustedCreationTime() {
        return this.adjustedCreationTime;
    }
    
    private long getLastLifespan() {
        final Group group = this.owner.visibleGroup();
        if (group.isLabyModPlus() || group.isStaff()) {
            return 2500L;
        }
        return 0L;
    }
    
    private long getPlainCreationTime() {
        return this.creationTime;
    }
    
    public void setAdjustCreationTime(final boolean adjustCreationTime) {
        this.adjustCreationTime = adjustCreationTime;
    }
    
    public boolean isExpired() {
        return this.getCreationTime() + 60000L < TimeUtil.getMillis();
    }
    
    public boolean isFadeIn() {
        return this.getCreationTime() > TimeUtil.getMillis() - 1000L;
    }
    
    public boolean isNormal() {
        return this.getCreationTime() + 1000L < TimeUtil.getMillis() && TimeUtil.getMillis() < this.getCreationTime() + 60000L - 1000L;
    }
    
    public boolean isFadeOut() {
        return this.getCreationTime() < TimeUtil.getMillis() - 1000L;
    }
    
    public long getDuration() {
        final long time = this.getPlainCreationTime() + 60000L;
        final long lifespan = TimeUtil.getMillis() + 60000L;
        return lifespan - time;
    }
    
    public float getPreviousDuration() {
        return this.previousDuration;
    }
    
    public void setPreviousDuration(final float previousDuration) {
        this.previousDuration = previousDuration;
    }
}
