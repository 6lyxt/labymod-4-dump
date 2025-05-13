// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.world.signobject;

import net.labymod.api.client.world.signobject.SignObjectPosition;
import net.labymod.api.client.blockentity.BlockEntity;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.world.chunk.Chunk;
import net.labymod.api.event.client.world.WorldLeaveEvent;
import net.labymod.api.event.client.network.server.SubServerSwitchEvent;
import net.labymod.api.event.client.blockentity.BlockEntityUpdateEvent;
import net.labymod.api.event.client.blockentity.SignBlockEntityPostLoadEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.world.signobject.object.SignObject;
import net.labymod.api.client.blockentity.SignBlockEntity;
import java.util.Iterator;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.core.client.world.canvas.DefaultPlacedSignObject;
import net.labymod.api.event.client.render.world.RenderWorldEvent;
import net.labymod.api.Laby;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import net.labymod.api.client.world.signobject.PlacedSignObject;
import net.labymod.api.client.world.block.BlockPosition;
import java.util.Map;
import net.labymod.api.LabyAPI;

public class SignObjectDiscovery
{
    private static final int MAX_CANVAS = 15;
    private final LabyAPI labyAPI;
    private final DefaultSignObjectRegistry registry;
    private final Map<BlockPosition, PlacedSignObject> trackedEntities;
    private final AtomicInteger renderedCanvas;
    
    public SignObjectDiscovery(final DefaultSignObjectRegistry registry) {
        this.trackedEntities = new HashMap<BlockPosition, PlacedSignObject>();
        this.renderedCanvas = new AtomicInteger();
        this.labyAPI = Laby.labyAPI();
        this.registry = registry;
    }
    
    public Map<BlockPosition, PlacedSignObject> trackedEntities() {
        return this.trackedEntities;
    }
    
    @Subscribe
    public void renderWorld(final RenderWorldEvent event) {
        if (this.trackedEntities.isEmpty()) {
            return;
        }
        final float tickDelta = Laby.labyAPI().minecraft().getTickDelta();
        final MinecraftCamera cam = event.camera();
        final DoubleVector3 camPos = cam.renderPosition();
        this.renderedCanvas.set(0);
        boolean cleanup = false;
        for (final PlacedSignObject placedSignObject : this.trackedEntities.values()) {
            final SignBlockEntity sign = placedSignObject.sign();
            if (sign.isRemoved()) {
                cleanup = true;
            }
            else {
                for (int i = 0; i < DefaultPlacedSignObject.SIDES.length; ++i) {
                    final SignObject<?> object = placedSignObject.objects()[i];
                    if (object != null && object.hasRendering() && object.isEnabled() && this.renderedCanvas.getAndIncrement() < 15) {
                        final BlockPosition location = sign.position();
                        final double x = location.getX() - camPos.getX();
                        final double y = location.getY() - camPos.getY();
                        final double z = location.getZ() - camPos.getZ();
                        object.render(event.stack(), x, y, z, tickDelta);
                    }
                }
            }
        }
        if (cleanup) {
            this.checkForRemovedSigns();
        }
    }
    
    private void disposeSigns() {
        for (final PlacedSignObject placedSignObject : this.trackedEntities.values()) {
            this.dispose(placedSignObject);
        }
        this.trackedEntities.clear();
    }
    
    private void checkForRemovedSigns() {
        this.trackedEntities.values().removeIf(signCanvas -> {
            final SignBlockEntity sign = signCanvas.sign();
            if (sign.isRemoved()) {
                this.dispose(signCanvas);
                return true;
            }
            else {
                return false;
            }
        });
    }
    
    private void dispose(final PlacedSignObject placedSignObject) {
        for (final SignObject<?> object : placedSignObject.objects()) {
            if (object != null) {
                object.dispose();
            }
        }
    }
    
    @Subscribe
    public void discoverCanvas(final SignBlockEntityPostLoadEvent event) {
        this.discoverCanvas(event.sign());
    }
    
    @Subscribe
    public void discoverCanvas(final BlockEntityUpdateEvent event) {
        if (event.blockEntity() instanceof final SignBlockEntity signBlockEntity) {
            this.discoverCanvas(signBlockEntity);
        }
    }
    
    @Subscribe
    public void onSubServerSwitch(final SubServerSwitchEvent event) {
        this.disposeSigns();
    }
    
    @Subscribe
    public void onWorldLeave(final WorldLeaveEvent event) {
        this.disposeSigns();
    }
    
    public void rediscoverAllSigns() {
        final ClientWorld world = this.labyAPI.minecraft().clientWorld();
        if (world == null) {
            return;
        }
        for (final Chunk chunk : world.getChunks()) {
            for (final BlockEntity blockEntity : chunk.getBlockEntities()) {
                if (blockEntity instanceof final SignBlockEntity signBlockEntity) {
                    this.discoverCanvas(signBlockEntity);
                }
            }
        }
    }
    
    private void discoverCanvas(final SignBlockEntity sign) {
        final ClientWorld world = this.labyAPI.minecraft().clientWorld();
        if (world == null) {
            return;
        }
        final SignObject<?>[] objects = new SignObject[DefaultPlacedSignObject.SIDES.length];
        boolean validCanvas = false;
        for (int i = 0; i < objects.length; ++i) {
            final SignObject<?> c = this.createObject(sign, DefaultPlacedSignObject.SIDES[i]);
            objects[i] = c;
            validCanvas = (validCanvas || c != null);
        }
        if (!validCanvas) {
            final PlacedSignObject removed = this.trackedEntities.remove(sign.position());
            if (removed != null) {
                this.dispose(removed);
            }
            return;
        }
        final PlacedSignObject placedSignObject = new DefaultPlacedSignObject(sign, objects);
        final PlacedSignObject overridden = this.trackedEntities.put(sign.position(), placedSignObject);
        if (overridden != null) {
            this.dispose(overridden);
        }
    }
    
    private SignObject<?> createObject(final SignBlockEntity sign, final SignBlockEntity.SignSide side) {
        if (!sign.hasSide(side)) {
            return null;
        }
        return this.registry.createObject(new DefaultSignObjectPosition(sign.position(), side, sign.getRotationYaw()), sign.getStringLines(side));
    }
}
