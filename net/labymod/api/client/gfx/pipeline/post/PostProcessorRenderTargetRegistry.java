// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.post;

import java.util.Iterator;
import net.labymod.api.client.gfx.pipeline.post.data.PostProcessorTarget;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.labymod.api.client.gfx.target.RenderTarget;
import java.util.Map;

public class PostProcessorRenderTargetRegistry
{
    private final Map<String, RenderTarget> renderTargets;
    private final List<RenderTarget> fullSizedTargets;
    
    public PostProcessorRenderTargetRegistry() {
        this.renderTargets = new HashMap<String, RenderTarget>();
        this.fullSizedTargets = new ArrayList<RenderTarget>();
    }
    
    public RenderTarget getRenderTarget(@Nullable final String name) {
        if (name == null) {
            return null;
        }
        return this.getRenderTarget(name, null);
    }
    
    @Nullable
    public RenderTarget getRenderTarget(@Nullable final String name, @Nullable final RenderTarget defaultTarget) {
        if (name == null) {
            return null;
        }
        return name.equals("minecraft:main") ? defaultTarget : this.renderTargets.get(name);
    }
    
    public void registerTarget(final PostProcessorTarget target) {
        final String name = target.getName();
        final boolean isCustomSizedTarget = target instanceof PostProcessorTarget.CustomSizeTarget;
        if (isCustomSizedTarget && this.renderTargets.containsKey(name)) {
            throw new IllegalStateException(name + " is already defined");
        }
        final RenderTarget renderTarget = target.create();
        renderTarget.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        this.renderTargets.put(name, renderTarget);
        if (!isCustomSizedTarget) {
            this.fullSizedTargets.add(renderTarget);
        }
    }
    
    public void resizeFullSizedTargets(final int width, final int height) {
        for (final RenderTarget target : this.fullSizedTargets) {
            target.resize(width, height);
        }
    }
}
