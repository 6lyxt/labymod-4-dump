// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.listener;

import net.labymod.api.event.Subscribe;
import java.util.Iterator;
import net.labymod.api.service.CustomServiceLoader;
import net.labymod.api.client.gfx.pipeline.backend.GFXBackend;
import net.labymod.api.event.labymod.ServiceLoadEvent;
import net.labymod.core.client.gfx.pipeline.DefaultGFXRenderPipeline;

public final class GFXBackendServiceLoadListener
{
    private final DefaultGFXRenderPipeline renderPipeline;
    
    public GFXBackendServiceLoadListener(final DefaultGFXRenderPipeline renderPipeline) {
        this.renderPipeline = renderPipeline;
    }
    
    @Subscribe
    public void onServiceLoad(final ServiceLoadEvent event) {
        for (final GFXBackend backend : event.load(GFXBackend.class, CustomServiceLoader.ServiceType.ADVANCED)) {
            this.renderPipeline.registerBackend(backend);
        }
        this.renderPipeline.findBestBackend();
    }
}
