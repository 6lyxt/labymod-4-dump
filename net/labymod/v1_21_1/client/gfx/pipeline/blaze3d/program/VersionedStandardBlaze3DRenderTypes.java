// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.client.gfx.pipeline.blaze3d.program;

import net.labymod.api.client.gfx.pipeline.blaze3d.program.Blaze3DRenderType;
import net.labymod.api.client.resources.ResourceLocation;
import javax.inject.Inject;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.pipeline.blaze3d.program.StandardBlaze3DRenderTypes;

@Singleton
@Implements(StandardBlaze3DRenderTypes.class)
public class VersionedStandardBlaze3DRenderTypes implements StandardBlaze3DRenderTypes
{
    @Inject
    public VersionedStandardBlaze3DRenderTypes() {
    }
    
    @Override
    public Blaze3DRenderType entityTranslucent(final ResourceLocation location, final boolean hasOutline) {
        return (Blaze3DRenderType)gfh.c((akr)location.getMinecraftLocation(), hasOutline);
    }
}
