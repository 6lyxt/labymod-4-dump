// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.draw;

import java.util.EnumMap;
import net.labymod.api.client.render.AtlasRenderer;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.render.vertex.shard.RenderShards;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gui.icon.Icon;
import java.util.Map;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface HeartRenderer
{
    void renderHealthBar(final Stack p0, final float p1, final float p2, final int p3, final int p4, final int p5);
    
    void renderHealthBar(final Stack p0, final float p1, final float p2, final int p3, final int p4, final int p5, final int p6);
    
    default void renderSingleHeart(final HeartTexture heartTexture, final Stack stack, final float x, final float y, final int size) {
        this.renderHearts(heartTexture, stack, x, y, size, 1);
    }
    
    void renderHearts(final HeartTexture p0, final Stack p1, final float p2, final float p3, final int p4, final int p5);
    
    default void renderSingleHeart(final HeartTexture heartTexture, final ResourceRenderContext context, final float x, final float y, final int size) {
        this.renderHearts(heartTexture, context, x, y, size, 1);
    }
    
    void renderHearts(final HeartTexture p0, final ResourceRenderContext p1, final float p2, final float p3, final int p4, final int p5);
    
    void startFlashing(final int p0);
    
    void stopFlashing();
    
    boolean isFlashing();
    
    boolean isCurrentlyFlashing();
    
    int getWidth(final int p0, final int p1);
    
    default HeartTexture getBackgroundTexture() {
        return this.isCurrentlyFlashing() ? HeartTexture.FLASHING_HEART : HeartTexture.EMPTY_HEART;
    }
    
    public enum HeartTexture
    {
        EMPTY_HEART("hud/heart/container"), 
        FLASHING_HEART("hud/heart/full_blinking"), 
        FULL_HEART("hud/heart/full"), 
        HALF_HEART("hud/heart/half"), 
        FULL_ABSORPTION_HEART("hud/heart/absorbing_full"), 
        HALF_ABSORPTION_HEART("hud/heart/absorbing_half");
        
        private static final Map<HeartTexture, Icon> ICONS;
        private final ResourceLocation location;
        
        private HeartTexture(final String path) {
            this.location = ResourceLocation.create("minecraft", path);
        }
        
        public void render(final Stack stack, final float x, final float y, final float size) {
            final ResourceLocation iconsTexture = Laby.labyAPI().minecraft().textures().iconsTexture();
            final Icon icon = HeartTexture.ICONS.computeIfAbsent(this, absent -> {
                final TextureAtlas atlas = Laby.references().atlasRegistry().getAtlas(iconsTexture);
                return Icon.sprite(atlas, atlas.findSprite(this.location));
            });
            RenderShards.LEGACY_DEPTH_TEST.setupShared();
            icon.render(stack, x, y, size);
            RenderShards.LEGACY_DEPTH_TEST.finishShared();
        }
        
        public void render(final ResourceRenderContext context, final float x, final float y, final float size) {
            final AtlasRenderer renderer = ResourceRenderContext.ATLAS_RENDERER;
            renderer.blitSprite(context, Laby.references().atlasRegistry().getAtlas(Laby.labyAPI().minecraft().textures().iconsTexture()), this.location, (int)x, (int)y, (int)size, (int)size, -1);
        }
        
        static {
            ICONS = new EnumMap<HeartTexture, Icon>(HeartTexture.class);
        }
    }
}
