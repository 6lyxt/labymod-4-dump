// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources;

import net.labymod.api.util.debug.Preconditions;
import javax.inject.Inject;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import net.labymod.api.client.resources.AnimatedResourceLocation;

@Implements(AnimatedResourceLocation.Builder.class)
public class AnimatedResourceLocationBuilder implements AnimatedResourceLocation.Builder
{
    private ResourceLocation[] locations;
    private long delay;
    @Nullable
    private Runnable completableListener;
    
    @Inject
    public AnimatedResourceLocationBuilder() {
        this.locations = new ResourceLocation[0];
        this.delay = 1000L;
    }
    
    @Override
    public AnimatedResourceLocation.Builder resourceLocations(final ResourceLocation[] locations) {
        this.locations = locations;
        return this;
    }
    
    @Override
    public AnimatedResourceLocation.Builder delay(final long delay) {
        this.delay = delay;
        return this;
    }
    
    @Override
    public AnimatedResourceLocation.Builder completableListener(@Nullable final Runnable completableListener) {
        this.completableListener = completableListener;
        return this;
    }
    
    @Override
    public AnimatedResourceLocation build() {
        Preconditions.noneNull(this.locations, "locations");
        return new DefaultAnimatedResourceLocation(this.locations, this.delay, this.completableListener);
    }
}
