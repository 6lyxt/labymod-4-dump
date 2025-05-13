// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render;

import net.labymod.api.client.render.AtlasRenderer;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.MinecraftTextures;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import javax.inject.Inject;
import net.labymod.api.client.render.StatusIcon;
import net.labymod.api.LabyAPI;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.StatusIconRenderer;

@Singleton
@Implements(StatusIconRenderer.class)
public class DefaultStatusIconRenderer implements StatusIconRenderer
{
    private final LabyAPI labyAPI;
    private StatusIcon[] statusIcons;
    private float x;
    private float y;
    private int amount;
    
    @Inject
    public DefaultStatusIconRenderer(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }
    
    @Override
    public StatusIconRenderer pos(final float x, final float y) {
        this.x = x;
        this.y = y;
        return this;
    }
    
    @Override
    public StatusIconRenderer statusIcon(final StatusIcon... icons) {
        this.statusIcons = icons;
        return this;
    }
    
    @Override
    public StatusIconRenderer amount(final int amount) {
        this.amount = amount;
        return this;
    }
    
    @Override
    public void render(final Stack stack) {
        final MinecraftTextures textures = this.labyAPI.minecraft().textures();
        final ResourceRenderContext resourceRenderContext = this.labyAPI.renderPipeline().renderContexts().resourceRenderContext();
        final ResourceLocation location = textures.iconsTexture();
        resourceRenderContext.begin(stack, location);
        final TextureAtlas atlas = Laby.references().atlasRegistry().getAtlas(location);
        final AtlasRenderer renderer = ResourceRenderContext.ATLAS_RENDERER;
        for (final StatusIcon statusIcon : this.statusIcons) {
            float width = 0.0f;
            for (int i = 0; i < this.amount; ++i) {
                renderer.blitSprite(resourceRenderContext, atlas, statusIcon.location(), (int)(this.x + width), (int)this.y, 9, 9, -1);
                width += 8.0f;
            }
        }
        resourceRenderContext.uploadToBuffer();
    }
    
    @Override
    public float getWidth(final int amount, final float size) {
        return amount * size;
    }
}
