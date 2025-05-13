// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget;

import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.gui.screen.widget.widgets.hud.HudWidgetWidget;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.render.AtlasRenderer;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.Laby;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;

@SpriteSlot(x = 1, y = 2)
public class SaturationHudWidget extends HudWidget<SaturationHudWidgetConfig>
{
    private static final ResourceLocation FOOD_EMPTY_SPRITE;
    private static final ResourceLocation FOOD_HALF_SPRITE;
    private static final ResourceLocation FOOD_FULL_SPRITE;
    private static final float DUMMY_SATURATION = 15.0f;
    
    public SaturationHudWidget() {
        super("saturation", SaturationHudWidgetConfig.class);
        this.bindCategory(HudWidgetCategory.INGAME);
        this.bindDropzones(NamedHudWidgetDropzones.SATURATION);
    }
    
    @Override
    public void render(final Stack stack, final MutableMouse mouse, final float partialTicks, final boolean isEditorContext, final HudSize size) {
        final Player player = this.labyAPI.minecraft().getClientPlayer();
        float saturation = (player == null) ? 15.0f : player.foodData().getSaturationLevel();
        if (saturation <= 0.0f) {
            saturation = 15.0f;
        }
        final int max = isEditorContext ? 10 : MathHelper.ceil(saturation / 2.0f);
        final ResourceRenderContext resourceRenderContext = this.labyAPI.renderPipeline().renderContexts().resourceRenderContext();
        final AtlasRenderer renderer = ResourceRenderContext.ATLAS_RENDERER;
        final ResourceLocation location = this.labyAPI.minecraft().textures().iconsTexture();
        final TextureAtlas atlas = Laby.references().atlasRegistry().getAtlas(location);
        resourceRenderContext.begin(stack, location);
        final int roundedSaturation = (int)Math.ceil(saturation * 2.0f);
        for (int tile = 0; tile < max; ++tile) {
            this.renderSaturationIcon(resourceRenderContext, renderer, atlas, roundedSaturation, tile);
        }
        resourceRenderContext.uploadToBuffer();
    }
    
    @Override
    public void updateSize(final HudWidgetWidget widget, final boolean editorContext, final HudSize size) {
        size.set(80, 9);
    }
    
    @Override
    public boolean isVisibleInGame() {
        final GameMode gameMode = this.labyAPI.minecraft().gameMode();
        final Player player = this.labyAPI.minecraft().getClientPlayer();
        return player != null && gameMode != GameMode.CREATIVE && gameMode != GameMode.SPECTATOR && player.foodData().getSaturationLevel() > 0.0f && !(player.getVehicle() instanceof LivingEntity) && !this.labyAPI.minecraft().options().isHideGUI();
    }
    
    private void renderSaturationIcon(final ResourceRenderContext context, final AtlasRenderer renderer, final TextureAtlas atlas, final int saturation, final int tile) {
        final int index = tile * 2 + 1;
        final boolean isFull = index < saturation;
        final boolean isHalf = index == saturation;
        final float width = (float)(tile * 8);
        final float foodX = 72.0f - width;
        renderer.blitSprite(context, atlas, SaturationHudWidget.FOOD_EMPTY_SPRITE, (int)foodX, 0, 9, 9, -1);
        if (isFull) {
            renderer.blitSprite(context, atlas, SaturationHudWidget.FOOD_FULL_SPRITE, (int)foodX, 0, 9, 9, -1);
        }
        if (isHalf) {
            renderer.blitSprite(context, atlas, SaturationHudWidget.FOOD_HALF_SPRITE, (int)foodX, 0, 9, 9, -1);
        }
    }
    
    @Override
    public boolean renderInDebug() {
        return true;
    }
    
    static {
        FOOD_EMPTY_SPRITE = ResourceLocation.create("minecraft", "hud/food_empty");
        FOOD_HALF_SPRITE = ResourceLocation.create("minecraft", "hud/food_half");
        FOOD_FULL_SPRITE = ResourceLocation.create("minecraft", "hud/food_full");
    }
    
    public static class SaturationHudWidgetConfig extends HudWidgetConfig
    {
    }
}
