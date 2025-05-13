// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.gfx.pipeline.blaze3d.program;

import net.labymod.api.Laby;
import net.labymod.api.util.collection.Lists;
import net.labymod.api.client.gfx.pipeline.program.RenderParameters;
import net.labymod.api.client.gfx.pipeline.program.RenderProgram;
import net.labymod.api.client.gfx.DrawingMode;
import net.labymod.api.Textures;
import javax.inject.Inject;
import net.labymod.api.client.gfx.pipeline.blaze3d.program.Blaze3DRenderType;
import net.labymod.api.client.gfx.pipeline.program.RenderParameter;
import java.util.List;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.function.Function;
import net.labymod.api.util.function.FunctionMemoizeStorage;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.pipeline.blaze3d.program.StandardBlaze3DRenderTypes;

@Singleton
@Implements(StandardBlaze3DRenderTypes.class)
public class VersionedStandardBlaze3DRenderTypes implements StandardBlaze3DRenderTypes
{
    private static final FunctionMemoizeStorage MEMOIZE_STORAGE;
    private static final Function<ResourceLocation, List<RenderParameter>> DEFAULT_PARAMETERS;
    private static final Function<ResourceLocation, Blaze3DRenderType> ENTITY_TRANSLUCENT;
    private static final Function<ResourceLocation, Blaze3DRenderType> LEGACY_SNOXH_EYES;
    
    @Inject
    public VersionedStandardBlaze3DRenderTypes() {
    }
    
    @Override
    public Blaze3DRenderType entityTranslucent(final ResourceLocation location, final boolean hasOutline) {
        return VersionedStandardBlaze3DRenderTypes.ENTITY_TRANSLUCENT.apply(location);
    }
    
    @Override
    public Blaze3DRenderType legacySnoxhEyes() {
        return VersionedStandardBlaze3DRenderTypes.LEGACY_SNOXH_EYES.apply(Textures.WHITE);
    }
    
    static {
        MEMOIZE_STORAGE = Laby.references().functionMemoizeStorage();
        DEFAULT_PARAMETERS = VersionedStandardBlaze3DRenderTypes.MEMOIZE_STORAGE.memoize(location -> Lists.newArrayList(RenderParameters.createTextureParameter(location, false, false), RenderParameters.LEQUAL_DEPTH_TEST, RenderParameters.WRITE_DEPTH, RenderParameters.WRITE_RGBA, RenderParameters.TRANSLUCENT_TRANSPARENCY_BLENDING, RenderParameters.NO_CULL));
        ENTITY_TRANSLUCENT = VersionedStandardBlaze3DRenderTypes.MEMOIZE_STORAGE.memoize(location -> {
            new VersionedBlaze3DRenderType(new VersionedRenderProgram("labymod:entity_translucent", DrawingMode.QUADS, bms.c, VersionedStandardBlaze3DRenderTypes.DEFAULT_PARAMETERS.apply(location)));
            return;
        });
        LEGACY_SNOXH_EYES = VersionedStandardBlaze3DRenderTypes.MEMOIZE_STORAGE.memoize(location -> {
            new VersionedBlaze3DRenderType(new VersionedRenderProgram("labymod:legacy_snoxh_eyes", DrawingMode.QUADS, bms.l, VersionedStandardBlaze3DRenderTypes.DEFAULT_PARAMETERS.apply(location)));
            return;
        });
    }
}
