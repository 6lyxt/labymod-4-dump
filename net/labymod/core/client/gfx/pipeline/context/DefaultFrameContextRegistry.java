// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.context;

import java.util.Iterator;
import net.labymod.api.util.ThreadSafe;
import java.util.Objects;
import javax.inject.Inject;
import java.util.ArrayList;
import net.labymod.api.client.gfx.pipeline.context.FrameContext;
import java.util.List;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.pipeline.context.FrameContextRegistry;

@Singleton
@Implements(FrameContextRegistry.class)
public final class DefaultFrameContextRegistry implements FrameContextRegistry
{
    private final List<FrameContext> frameContexts;
    private final FrameContext recordFrameContext;
    private int index;
    
    @Inject
    public DefaultFrameContextRegistry() {
        (this.frameContexts = new ArrayList<FrameContext>()).add(new RenderAttributesFrameContext());
        this.recordFrameContext = new RecordFrameContext();
    }
    
    @Override
    public void register(final FrameContext context) {
        Objects.requireNonNull(context, "context must not be null");
        ThreadSafe.executeOnRenderThread(() -> this.frameContexts.add(context));
    }
    
    @Override
    public void beginFrame() {
        ++this.index;
        if (this.index != 1) {
            return;
        }
        this.recordFrameContext.beginFrame();
        for (final FrameContext frameContext : this.frameContexts) {
            frameContext.beginFrame();
        }
    }
    
    @Override
    public void endFrame() {
        --this.index;
        if (this.index != 0) {
            return;
        }
        for (final FrameContext frameContext : this.frameContexts) {
            frameContext.endFrame();
        }
        this.recordFrameContext.endFrame();
    }
}
