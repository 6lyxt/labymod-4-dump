// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex.phase;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Contract;
import java.util.Iterator;
import net.labymod.api.Laby;
import java.util.Locale;
import net.labymod.api.client.render.vertex.shard.RenderShard;
import java.util.List;
import net.labymod.api.client.render.vertex.OldVertexFormat;

@Deprecated
public class RenderPhase
{
    private final String name;
    private final OldVertexFormat vertexFormat;
    private final int mode;
    private final int bufferSize;
    private final boolean affectsCrumbling;
    private final boolean sortOnUpload;
    private final List<RenderShard> shards;
    private final Runnable setupShard;
    private final Runnable finishShard;
    
    public RenderPhase(final String name, final OldVertexFormat vertexFormat, final int mode, final int bufferSize, final boolean affectsCrumbling, final boolean sortOnUpload, final List<RenderShard> shards) {
        if (shards == null) {
            throw new RenderPhaseException(String.format(Locale.ROOT, "No render shards were added to the \"%s\" phase.", name));
        }
        this.name = name;
        this.vertexFormat = vertexFormat;
        this.mode = mode;
        this.bufferSize = bufferSize;
        this.affectsCrumbling = affectsCrumbling;
        this.sortOnUpload = sortOnUpload;
        this.shards = shards;
        this.setupShard = (() -> {
            Laby.gfx().storeBlaze3DStates();
            this.shards.iterator();
            final Iterator iterator;
            while (iterator.hasNext()) {
                final RenderShard shard = iterator.next();
                shard.setupShared();
            }
            return;
        });
        this.finishShard = (() -> {
            this.shards.iterator();
            final Iterator iterator2;
            while (iterator2.hasNext()) {
                final RenderShard shard2 = iterator2.next();
                shard2.finishShared();
            }
            Laby.gfx().restoreBlaze3DStates();
        });
    }
    
    @Contract(value = " -> new", pure = true)
    @NotNull
    public static RenderPhaseBuilder builder() {
        return new RenderPhaseBuilder();
    }
    
    public String getName() {
        return this.name;
    }
    
    public OldVertexFormat getVertexFormat() {
        return this.vertexFormat;
    }
    
    public int getMode() {
        return this.mode;
    }
    
    public int getBufferSize() {
        return this.bufferSize;
    }
    
    public boolean isAffectsCrumbling() {
        return this.affectsCrumbling;
    }
    
    public boolean isSortOnUpload() {
        return this.sortOnUpload;
    }
    
    public void apply() {
        this.setupShard.run();
    }
    
    public void clear() {
        this.finishShard.run();
    }
    
    public Runnable getSetupShard() {
        return this.setupShard;
    }
    
    public Runnable getFinishShard() {
        return this.finishShard;
    }
    
    public void finish(@NotNull final Uploader uploader, final int cameraX, final int cameraY, final int cameraZ) {
        uploader.sort(cameraX, cameraY, cameraZ);
        uploader.endBufferBuilder();
        this.apply();
        uploader.upload();
        this.clear();
    }
    
    public List<RenderShard> getShards() {
        return this.shards;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final RenderPhase that = (RenderPhase)o;
        return this.mode == that.mode && this.bufferSize == that.bufferSize && this.affectsCrumbling == that.affectsCrumbling && this.sortOnUpload == that.sortOnUpload && Objects.equals(this.name, that.name) && Objects.equals(this.vertexFormat, that.vertexFormat) && Objects.equals(this.shards, that.shards);
    }
    
    @Override
    public int hashCode() {
        int result = (this.name != null) ? this.name.hashCode() : 0;
        result = 31 * result + ((this.vertexFormat != null) ? this.vertexFormat.hashCode() : 0);
        result = 31 * result + this.mode;
        result = 31 * result + this.bufferSize;
        result = 31 * result + (this.affectsCrumbling ? 1 : 0);
        result = 31 * result + (this.sortOnUpload ? 1 : 0);
        result = 31 * result + this.shards.hashCode();
        return result;
    }
    
    @FunctionalInterface
    public interface Uploader
    {
        default void sort(final int cameraX, final int cameraY, final int cameraZ) {
        }
        
        default void endBufferBuilder() {
        }
        
        void upload();
    }
}
