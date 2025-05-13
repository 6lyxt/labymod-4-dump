// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex.phase;

import net.labymod.api.loader.platform.PlatformEnvironment;
import java.util.ArrayList;
import net.labymod.api.client.render.vertex.OldVertexFormat;
import net.labymod.api.client.render.vertex.shard.RenderShard;
import java.util.List;

public final class RenderPhaseBuilder
{
    private final List<RenderShard> shards;
    private String name;
    private OldVertexFormat vertexFormat;
    private int mode;
    private int bufferSize;
    private boolean affectsCrumbling;
    private boolean sortOnUpload;
    
    RenderPhaseBuilder() {
        this.mode = -1;
        this.bufferSize = 256;
        this.affectsCrumbling = false;
        this.sortOnUpload = false;
        this.shards = new ArrayList<RenderShard>();
    }
    
    public RenderPhaseBuilder name(final String name) {
        this.name = name;
        return this;
    }
    
    public RenderPhaseBuilder vertexFormat(final OldVertexFormat vertexFormat) {
        this.vertexFormat = vertexFormat;
        return this;
    }
    
    public RenderPhaseBuilder mode(final int mode) {
        this.mode = mode;
        return this;
    }
    
    public RenderPhaseBuilder bufferSize(final int bufferSize) {
        this.bufferSize = bufferSize;
        return this;
    }
    
    public RenderPhaseBuilder affectsCrumbling(final boolean affectsCrumbling) {
        this.affectsCrumbling = affectsCrumbling;
        return this;
    }
    
    public RenderPhaseBuilder sortOnUpload(final boolean sortOnUpload) {
        this.sortOnUpload = sortOnUpload;
        return this;
    }
    
    public RenderPhaseBuilder addShard(final RenderShard shard) {
        this.shards.add(shard);
        return this;
    }
    
    public RenderPhaseBuilder addShard(final boolean condition, final RenderShard shard) {
        if (condition) {
            this.shards.add(shard);
            return this;
        }
        return this;
    }
    
    public RenderPhaseBuilder addShard(final RenderShard legacyShard, final RenderShard modernShard) {
        this.shards.add(this.isLegacyVersion() ? legacyShard : modernShard);
        return this;
    }
    
    public RenderPhaseBuilder addModernShard(final boolean condition, final RenderShard shard) {
        if (this.isLegacyVersion() || !condition) {
            return this;
        }
        this.shards.add(shard);
        return this;
    }
    
    public RenderPhaseBuilder addModernShard(final RenderShard shard) {
        if (this.isLegacyVersion()) {
            return this;
        }
        this.shards.add(shard);
        return this;
    }
    
    public RenderPhaseBuilder addLegacyShard(final RenderShard shard) {
        if (this.isLegacyVersion()) {
            this.shards.add(shard);
            return this;
        }
        return this;
    }
    
    public RenderPhase build() {
        this.thrownException(this.name == null, "Name is missing (call name(String))");
        this.thrownException(this.vertexFormat == null, "VertexFormat is missing (call vertexFormat(VertexFormat))");
        this.thrownException(this.mode == -1, "Invalid mode (call mode(int))");
        return new RenderPhase(this.name, this.vertexFormat, this.mode, this.bufferSize, this.affectsCrumbling, this.sortOnUpload, this.shards);
    }
    
    private boolean isLegacyVersion() {
        return PlatformEnvironment.isAncientOpenGL();
    }
    
    private void thrownException(final boolean condition, final String message) {
        if (condition) {
            throw new IllegalStateException(message);
        }
    }
}
