// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.session.spray;

import net.labymod.api.event.Event;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectSprayEvent;
import net.labymod.api.util.math.Direction;
import java.nio.ByteBuffer;
import java.util.UUID;
import net.labymod.core.labyconnect.DefaultLabyConnect;

public class V1SprayProtocol implements SprayProtocol
{
    @Override
    public void handle(final DefaultLabyConnect labyConnect, final short sprayId, final int protocolVersion, final UUID uniqueId, final ByteBuffer buffer) {
        final float x = buffer.getFloat();
        final float y = buffer.getFloat();
        final float z = buffer.getFloat();
        final Direction direction = Direction.byIndex(buffer.getInt());
        final float rotation = buffer.getFloat();
        labyConnect.fireEventSync(new LabyConnectSprayEvent(labyConnect, uniqueId, sprayId, protocolVersion, x, y, z, direction, rotation));
    }
}
