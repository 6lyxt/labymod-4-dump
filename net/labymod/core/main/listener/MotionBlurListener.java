// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.listener;

import java.util.Iterator;
import net.labymod.api.modloader.ModLoader;
import net.labymod.api.util.KeyValue;
import net.labymod.api.modloader.ModLoaderRegistry;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.gfx.pipeline.post.processors.PostProcessors;
import net.labymod.api.event.client.render.post.PostProcessingScreenEvent;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.MotionBlurConfig;

public final class MotionBlurListener
{
    private static final String IRIS_ID = "iris";
    private final MotionBlurConfig config;
    private boolean iris;
    private boolean checkIris;
    
    public MotionBlurListener(final LabyConfig config) {
        this.config = config.ingame().motionBlur();
    }
    
    @Subscribe
    public void onPostProcessingScreen(final PostProcessingScreenEvent event) {
        if (!this.config.enabled().get() || event.phase() != this.getMotionBlurPhase()) {
            return;
        }
        PostProcessors.processMotionBlur(event.partialTicks());
    }
    
    private PostProcessingScreenEvent.Phase getMotionBlurPhase() {
        if (OptiFine.isPresent()) {
            return PostProcessingScreenEvent.Phase.WORLD;
        }
        if (this.isIrisLoaded()) {
            return PostProcessingScreenEvent.Phase.WORLD;
        }
        return PostProcessingScreenEvent.Phase.BEFORE_HAND;
    }
    
    private boolean isIrisLoaded() {
        if (this.checkIris) {
            return this.iris;
        }
        this.checkIris = true;
        for (final KeyValue<ModLoader> element : ModLoaderRegistry.instance().getElements()) {
            final ModLoader loader = element.getValue();
            if (loader.isModLoaded("iris")) {
                this.iris = true;
                break;
            }
        }
        return this.iris;
    }
}
