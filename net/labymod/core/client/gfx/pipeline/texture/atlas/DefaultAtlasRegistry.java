// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.texture.atlas;

import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.Laby;
import org.jetbrains.annotations.NotNull;
import net.labymod.core.client.gfx.pipeline.texture.atlas.atlas.minecraft.DefaultBarsTextureAtlas;
import net.labymod.core.client.gfx.pipeline.texture.atlas.atlas.minecraft.DefaultIconTextureAtlas;
import java.util.function.Supplier;
import net.labymod.core.client.gfx.pipeline.texture.atlas.atlas.minecraft.DefaultWidgetTextureAtlas;
import net.labymod.core.client.gfx.pipeline.texture.atlas.atlas.DefaultLiquidTextureAtlas;
import net.labymod.api.Textures;
import net.labymod.core.client.gfx.pipeline.texture.atlas.atlas.DefaultParticleTextureAtlas;
import net.labymod.core.client.gfx.pipeline.texture.atlas.atlas.DefaultBlockTextureAtlas;
import java.util.HashMap;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Map;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.pipeline.texture.atlas.AtlasRegistry;

@Singleton
@Implements(AtlasRegistry.class)
public class DefaultAtlasRegistry implements AtlasRegistry
{
    private static final boolean GUI_ATLAS;
    private static final boolean ICONS_ATLAS;
    private final Map<ResourceLocation, TextureAtlas> atlases;
    
    public DefaultAtlasRegistry() {
        this.atlases = new HashMap<ResourceLocation, TextureAtlas>();
        this.register(new DefaultBlockTextureAtlas());
        this.register(new DefaultParticleTextureAtlas());
        this.register(new DefaultLiquidTextureAtlas(Textures.Splash.LAVA_FLOW, "lava_flow"));
        this.register(this.findBestAtlas(DefaultAtlasRegistry.GUI_ATLAS, (Supplier<TextureAtlas>)DefaultWidgetTextureAtlas::new));
        this.register(this.findBestAtlas(DefaultAtlasRegistry.GUI_ATLAS, (Supplier<TextureAtlas>)DefaultIconTextureAtlas::new));
        if (!DefaultAtlasRegistry.ICONS_ATLAS) {
            this.register(this.findBestAtlas(DefaultAtlasRegistry.GUI_ATLAS, (Supplier<TextureAtlas>)DefaultBarsTextureAtlas::new));
        }
    }
    
    @Override
    public void register(final ResourceLocation location, final TextureAtlas atlas) {
        this.atlases.put(location, atlas);
    }
    
    @NotNull
    @Override
    public TextureAtlas getAtlas(final ResourceLocation location) {
        final TextureAtlas atlas = this.atlases.get(location);
        if (atlas == null) {
            throw new IllegalStateException("No texture atlas could be found with the resource location \"" + String.valueOf(location));
        }
        return atlas;
    }
    
    private TextureAtlas findBestAtlas(final boolean atlasGui, final Supplier<TextureAtlas> atlasSupplier) {
        return atlasGui ? Laby.references().minecraftAtlases().getGuiAtlas() : atlasSupplier.get();
    }
    
    static {
        GUI_ATLAS = MinecraftVersions.V23w31a.orNewer();
        ICONS_ATLAS = MinecraftVersions.V1_8_9.orOlder();
    }
}
