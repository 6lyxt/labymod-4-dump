// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.labyconnect.session;

import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.util.math.Direction;
import java.util.UUID;
import net.labymod.api.event.labymod.labyconnect.LabyConnectEvent;

public class LabyConnectSprayEvent extends LabyConnectEvent
{
    private final UUID uniqueId;
    private final short sprayId;
    private final int protocolVersion;
    private final double x;
    private final double y;
    private final double z;
    private final Direction direction;
    private final float rotation;
    
    public LabyConnectSprayEvent(final LabyConnect api, final UUID uniqueId, final short sprayId, final int protocolVersion, final double x, final double y, final double z, final Direction direction, final float rotation) {
        super(api);
        this.uniqueId = uniqueId;
        this.sprayId = sprayId;
        this.protocolVersion = protocolVersion;
        this.x = x;
        this.y = y;
        this.z = z;
        this.direction = direction;
        this.rotation = rotation;
    }
    
    public UUID getUniqueId() {
        return this.uniqueId;
    }
    
    public short getSprayId() {
        return this.sprayId;
    }
    
    public int getProtocolVersion() {
        return this.protocolVersion;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public Direction direction() {
        return this.direction;
    }
    
    public float getRotation() {
        return this.rotation;
    }
}
