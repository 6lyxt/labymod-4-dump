// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.lightning.legacy;

import net.labymod.core.client.render.schematic.block.Block;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.Queue;

public class DefaultLegacyLightEngine implements LegacyLightEngine
{
    private final Queue<Integer> lightUpdateQueue;
    private final WorldLightAccessor world;
    private final int width;
    private final int height;
    private final int length;
    private final int skyLight;
    private byte[] light;
    private boolean dirty;
    
    public DefaultLegacyLightEngine(final WorldLightAccessor world, final int width, final int height, final int length, final int skyLight) {
        this.lightUpdateQueue = new LinkedBlockingQueue<Integer>();
        this.dirty = true;
        this.world = world;
        this.width = width;
        this.height = height;
        this.length = length;
        this.skyLight = skyLight;
        Arrays.fill(this.light = new byte[this.width * this.height * this.length], (byte)(-1));
    }
    
    private void updateLight(final int x, final int y, final int z) {
        final Block block = this.world.getBlockAt(x, y, z);
        int level = this.world.getLightLevel(this, block);
        if (block.material().isTranslucent()) {
            level = Math.max(level, this.skyLight);
        }
        if (x > 0) {
            level = Math.max(level, this.getLightAt(x - 1, y, z) - 1);
        }
        if (x < this.width - 1) {
            level = Math.max(level, this.getLightAt(x + 1, y, z) - 1);
        }
        if (y > 0) {
            level = Math.max(level, this.getLightAt(x, y - 1, z) - 1);
        }
        if (y < this.height - 1) {
            level = Math.max(level, this.getLightAt(x, y + 1, z) - 1);
        }
        if (z > 0) {
            level = Math.max(level, this.getLightAt(x, y, z - 1) - 1);
        }
        if (z < this.length - 1) {
            level = Math.max(level, this.getLightAt(x, y, z + 1) - 1);
        }
        final int previousLevel = this.getLightAt(x, y, z);
        if (level != previousLevel) {
            this.setLightAt(x, y, z, (byte)level);
            if (x > 0) {
                this.queueLightUpdate(x - 1, y, z);
            }
            if (x < this.width - 1) {
                this.queueLightUpdate(x + 1, y, z);
            }
            if (y > 0) {
                this.queueLightUpdate(x, y - 1, z);
            }
            if (y < this.height - 1) {
                this.queueLightUpdate(x, y + 1, z);
            }
            if (z > 0) {
                this.queueLightUpdate(x, y, z - 1);
            }
            if (z < this.length - 1) {
                this.queueLightUpdate(x, y, z + 1);
            }
        }
    }
    
    @Override
    public void handleLightUpdates() {
        this.dirty = false;
        for (int i = 0; i <= 1000000; ++i) {
            final Integer index = this.lightUpdateQueue.poll();
            if (index == null) {
                break;
            }
            final int x = index % this.width;
            final int y = index / (this.width * this.length);
            final int z = index / this.width % this.length;
            this.updateLight(x, y, z);
        }
    }
    
    public void queueLightUpdate(final int x, final int y, final int z) {
        if (this.lightUpdateQueue.size() < 1000) {
            final int index = this.getIndex(x, y, z);
            this.lightUpdateQueue.add(index);
        }
    }
    
    private int getIndex(final int x, final int y, final int z) {
        return (y * this.length + z) * this.width + x;
    }
    
    @Override
    public int getLightAt(final int x, final int y, final int z) {
        final int index = this.getIndex(x, y, z);
        if (index < 0 || index >= this.light.length) {
            return 0;
        }
        final byte level = this.light[index];
        if (level == -1) {
            this.queueLightUpdate(x, y, z);
        }
        return level;
    }
    
    public void setLightAt(final int x, final int y, final int z, final byte level) {
        final int index = this.getIndex(x, y, z);
        if (index < 0 || index >= this.light.length) {
            return;
        }
        this.light[index] = level;
        this.dirty = true;
    }
    
    @Override
    public void reset() {
        this.lightUpdateQueue.clear();
        Arrays.fill(this.light, (byte)(-1));
        this.dirty = true;
    }
    
    @Override
    public float getAverageLightLevelAt(final int x, final int y, final int z) {
        int totalLightLevel = 0;
        int totalBlocks = 0;
        for (int offsetX = -1; offsetX <= 0; ++offsetX) {
            for (int offsetY = -1; offsetY <= 0; ++offsetY) {
                for (int offsetZ = -1; offsetZ <= 0; ++offsetZ) {
                    final int posX = x + offsetX;
                    final int posY = y + offsetY;
                    final int posZ = z + offsetZ;
                    if (this.world.isTranslucent(posX, posY, posZ) || this.world.isLightSource(posX, posY, posZ)) {
                        totalLightLevel += this.getLightAt(posX, posY, posZ);
                        ++totalBlocks;
                    }
                    else if (offsetY == 0) {
                        ++totalBlocks;
                    }
                }
            }
        }
        return totalLightLevel / (float)totalBlocks;
    }
    
    @Override
    public boolean isInProgress() {
        return !this.lightUpdateQueue.isEmpty() || this.dirty;
    }
    
    @Override
    public boolean isDirty() {
        return this.dirty;
    }
    
    @Override
    public byte[] getData() {
        return this.light;
    }
    
    @Override
    public void setData(final byte[] data) {
        if (data.length != this.light.length) {
            throw new IllegalArgumentException("Light data has wrong length");
        }
        this.light = data;
    }
}
