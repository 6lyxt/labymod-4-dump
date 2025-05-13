// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.reference.annotation.Referenceable;
import java.util.List;
import net.labymod.api.util.time.TimeUtil;

public interface AnimatedResourceLocation
{
    AnimatedFrameResourceLocation getDefault();
    
    AnimatedFrameResourceLocation getCurrentResourceLocation();
    
    AnimatedFrameResourceLocation getResourceLocationAt(final int p0);
    
    default void update() {
        this.update(TimeUtil.getMillis());
    }
    
    void update(final long p0);
    
    List<AnimatedFrameResourceLocation> getAnimatedResourceLocations();
    
    @Referenceable
    public interface Builder
    {
        Builder resourceLocations(final ResourceLocation[] p0);
        
        default Builder resourceLocations(final String namespace, final String path, final int frames) {
            final ResourceLocation[] locations = new ResourceLocation[frames];
            for (int frame = 0; frame < frames; ++frame) {
                locations[frame] = ResourceLocation.create(namespace, path + "_" + frame + ".png");
            }
            return this.resourceLocations(locations);
        }
        
        Builder delay(final long p0);
        
        Builder completableListener(@Nullable final Runnable p0);
        
        AnimatedResourceLocation build();
    }
    
    public interface AnimatedFrameResourceLocation extends ResourceLocation
    {
    }
}
