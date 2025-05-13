// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.spray;

import net.labymod.core.main.user.shop.emote.model.EmoteItem;
import java.util.Iterator;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.block.RenderShape;
import net.labymod.api.util.math.vector.IntVector3;
import java.util.Collection;
import net.labymod.core.main.user.util.SprayCooldownTracker;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.api.Laby;
import net.labymod.api.util.collection.map.HashMultimap;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.core.main.user.shop.emote.EmoteService;
import net.labymod.api.user.GameUser;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.collection.map.Multimap;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class SprayRegistry
{
    private static final int SPRAY_EMOTE_ID = 336;
    private static final int PROTOCOL_VERSION = 2;
    private final Multimap<Direction, SprayObject> objects;
    private final Multimap<GameUser, SprayObject> ownerSprays;
    private final EmoteService emoteService;
    private final ClientWorld level;
    
    public SprayRegistry(final EmoteService emoteService, final ClientWorld level) {
        this.objects = new HashMultimap<Direction, SprayObject>();
        this.ownerSprays = new HashMultimap<GameUser, SprayObject>();
        this.emoteService = emoteService;
        this.level = level;
    }
    
    public void sprayServer(final GameUser owner, final short id, final double x, final double y, final double z, final Direction direction, final float rotation) {
        final SprayObject newObject = new SprayObject(owner, id, x, y, z, direction, rotation);
        this.addSpray(newObject);
    }
    
    public SprayState sprayClient(final short id, final double x, final double y, final double z, final Direction direction, final float rotation) {
        final LabyConnect labyConnect = Laby.references().labyConnect();
        final LabyConnectSession session = labyConnect.getSession();
        if (!labyConnect.isAuthenticated() || session == null) {
            return SprayState.NOT_CONNECTED;
        }
        final DefaultGameUser owner = (DefaultGameUser)this.clientGameUser();
        final SprayCooldownTracker sprayCooldownTracker = owner.sprayCooldownTracker();
        if (!sprayCooldownTracker.canSprayServer()) {
            return SprayState.SPRAY_COOLDOWN;
        }
        sprayCooldownTracker.sprayServer();
        if (this.isInvisibleBlock(direction, x, y, z)) {
            return SprayState.INVISIBLE_BLOCK;
        }
        session.spray(id, 2, x, y, z, direction, rotation);
        return SprayState.SUCCESS;
    }
    
    public Multimap<Direction, SprayObject> getObjects() {
        return this.objects;
    }
    
    public Multimap<GameUser, SprayObject> getOwnerSprays() {
        return this.ownerSprays;
    }
    
    private void addSpray(final SprayObject object) {
        final DefaultGameUser owner = (DefaultGameUser)object.getOwner();
        final SprayCooldownTracker sprayCooldownTracker = owner.sprayCooldownTracker();
        if (!this.canSpray(owner, sprayCooldownTracker)) {
            return;
        }
        if (this.isInvisibleBlock(object.direction(), object.getX(), object.getY(), object.getZ())) {
            return;
        }
        sprayCooldownTracker.sprayServer();
        final boolean isClientOwner = owner == this.clientGameUser();
        sprayCooldownTracker.playSound(object, isClientOwner);
        this.playSprayEmote(owner);
        final Collection<SprayObject> sprayObjects = this.ownerSprays.get(owner);
        if (!sprayObjects.isEmpty()) {
            this.adjustTimes(sprayObjects);
        }
        sprayObjects.add(object);
        this.objects.put(object.direction(), object);
    }
    
    private boolean canSpray(final DefaultGameUser owner, final SprayCooldownTracker sprayCooldownTracker) {
        return owner == this.clientGameUser() || sprayCooldownTracker.canSprayServer();
    }
    
    private boolean isInvisibleBlock(final Direction direction, final double x, final double y, final double z) {
        double newX = x;
        double newY = y;
        double newZ = z;
        if (direction == Direction.UP) {
            newY -= 0.0625;
        }
        else if (direction == Direction.EAST) {
            newX -= 0.0625;
        }
        else if (direction == Direction.SOUTH) {
            newZ -= 0.0625;
        }
        final IntVector3 blockPosition = new IntVector3(newX, newY, newZ);
        final BlockState blockState = this.level.getBlockState(blockPosition);
        if (blockState.renderShape() == RenderShape.INVISIBLE) {
            return true;
        }
        final AxisAlignedBoundingBox bounds = blockState.bounds();
        final double blockY = blockPosition.getY() + bounds.getMaxY();
        return y > blockY;
    }
    
    private void adjustTimes(final Collection<SprayObject> sprayObjects) {
        for (final SprayObject sprayObject : sprayObjects) {
            final long adjustedCreationTime = sprayObject.getAdjustedCreationTime();
            if (adjustedCreationTime != 0L) {
                continue;
            }
            sprayObject.setAdjustCreationTime(true);
        }
    }
    
    private void playSprayEmote(final GameUser owner) {
        final EmoteItem emote = this.emoteService.getEmote(336);
        if (emote == null) {
            return;
        }
        this.emoteService.playClientEmote(owner.getUniqueId(), emote);
    }
    
    private GameUser clientGameUser() {
        return Laby.references().gameUserService().clientGameUser();
    }
    
    public enum SprayState
    {
        NOT_CONNECTED, 
        INVISIBLE_BLOCK, 
        SPRAY_COOLDOWN, 
        SUCCESS;
    }
}
