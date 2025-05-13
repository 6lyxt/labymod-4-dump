// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.renderer.primitive;

import net.labymod.api.client.gfx.pipeline.renderer.batch.BufferBatchable;
import net.labymod.api.client.gfx.pipeline.renderer.Renderer;

public class SpriteRenderer implements Renderer
{
    private final BufferBatchable batchable;
    
    public SpriteRenderer(final BufferBatchable batchable) {
        this.batchable = batchable;
    }
}
