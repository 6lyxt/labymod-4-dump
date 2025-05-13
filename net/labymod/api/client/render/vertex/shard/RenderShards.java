// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.vertex.shard;

import net.labymod.api.Laby;
import net.labymod.api.client.render.vertex.shard.shards.LineWidthRenderShard;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Contract;
import net.labymod.api.client.render.vertex.shard.shards.texture.TexturingRenderShard;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.render.vertex.shard.shards.output.ItemEntityOutputRenderShard;
import net.labymod.api.client.render.vertex.shard.shards.layer.ViewOffsetZLayeringRenderShard;
import net.labymod.api.client.render.vertex.shard.shards.SmoothLineRenderShard;
import net.labymod.api.client.render.vertex.shard.shards.texture.NoTexturingRenderShard;
import net.labymod.api.client.render.vertex.shard.shards.WriteMaskRenderShard;
import net.labymod.api.client.render.vertex.shard.shards.LegacyDepthRenderShard;
import net.labymod.api.client.render.vertex.shard.shards.LightmapRenderShared;
import net.labymod.api.client.render.vertex.shard.shards.DepthTestRenderShard;
import net.labymod.api.client.render.vertex.shard.shards.TransparencyRenderShard;
import net.labymod.api.client.render.vertex.shard.shards.AlphaRenderShard;
import net.labymod.api.client.render.vertex.shard.shards.CullRenderShard;

public final class RenderShards
{
    public static final CullRenderShard CULL;
    public static final CullRenderShard NO_CULL;
    public static final AlphaRenderShard DEFAULT_ALPHA;
    public static final TransparencyRenderShard TRANSLUCENT_TRANSPARENCY_HUD;
    public static final TransparencyRenderShard TRANSLUCENT_TRANSPARENCY;
    public static final DepthTestRenderShard LEQUAL_DEPTH_TEST;
    public static final LightmapRenderShared LIGHTMAP;
    public static final LegacyDepthRenderShard LEGACY_DEPTH_TEST;
    public static final WriteMaskRenderShard COLOR_DEPTH_WRITE;
    public static final NoTexturingRenderShard NO_TEXTURING;
    public static final SmoothLineRenderShard SMOOTH_LINE;
    public static final ViewOffsetZLayeringRenderShard VIEW_OFFSET_Z_LAYERING;
    public static final ItemEntityOutputRenderShard ITEM_ENTITY_TARGET;
    
    @Contract("_, _, _ -> new")
    @NotNull
    public static TexturingRenderShard createTexturing(final ResourceLocation location, final boolean blur, final boolean mipmap) {
        return new TexturingRenderShard(location, blur, mipmap);
    }
    
    @NotNull
    public static LineWidthRenderShard lineWidth(final float lineWidth) {
        return new LineWidthRenderShard(lineWidth);
    }
    
    static {
        CULL = new CullRenderShard(true);
        NO_CULL = new CullRenderShard(false);
        DEFAULT_ALPHA = new AlphaRenderShard("default_alpha");
        TRANSLUCENT_TRANSPARENCY_HUD = new TransparencyRenderShard("translucent_transparency_hud", shard -> {
            shard.gfx().enableBlend();
            shard.gfx().blendEquation(32774);
            shard.gfx().blendSeparate(770, 771, 1, 771);
            shard.gfx().shadeSmooth();
            return;
        }, shard -> {
            shard.gfx().disableBlend();
            shard.gfx().blendEquation(32774);
            shard.gfx().defaultBlend();
            return;
        });
        TRANSLUCENT_TRANSPARENCY = new TransparencyRenderShard("translucent_transparency", shard -> {
            shard.gfx().enableBlend();
            shard.gfx().blendSeparate(770, 771, 1, 771);
            return;
        }, shard -> {
            shard.gfx().disableBlend();
            shard.gfx().defaultBlend();
            return;
        });
        LEQUAL_DEPTH_TEST = new DepthTestRenderShard("<=", 515);
        LIGHTMAP = new LightmapRenderShared(true);
        LEGACY_DEPTH_TEST = new LegacyDepthRenderShard(true);
        COLOR_DEPTH_WRITE = new WriteMaskRenderShard(true, true);
        NO_TEXTURING = new NoTexturingRenderShard();
        SMOOTH_LINE = new SmoothLineRenderShard();
        VIEW_OFFSET_Z_LAYERING = new ViewOffsetZLayeringRenderShard();
        ITEM_ENTITY_TARGET = (ItemEntityOutputRenderShard)Laby.references().outputRenderShard();
    }
}
