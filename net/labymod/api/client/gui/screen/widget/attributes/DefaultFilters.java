// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.attributes;

import net.labymod.api.Laby;
import net.labymod.api.generated.ReferenceStorage;
import java.util.function.Function;
import net.labymod.api.client.render.draw.BlurRenderer;
import net.labymod.api.util.logging.Logging;

public final class DefaultFilters
{
    private static final Logging LOGGER;
    public static final BlurRenderer BLUR_RENDERER;
    
    public static <T> T createFilter(final String name, final Function<ReferenceStorage, T> factory) {
        try {
            return factory.apply(Laby.references());
        }
        catch (final Throwable throwable) {
            DefaultFilters.LOGGER.error("Unable to create \"{}\"", name, throwable);
            return null;
        }
    }
    
    static {
        LOGGER = Logging.getLogger();
        BLUR_RENDERER = createFilter("Blur Renderer", ReferenceStorage::blurRenderer);
    }
}
