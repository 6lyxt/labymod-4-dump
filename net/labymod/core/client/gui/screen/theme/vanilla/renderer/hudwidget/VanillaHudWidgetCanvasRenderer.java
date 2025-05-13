// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer.hudwidget;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.render.RenderMode;
import net.labymod.api.Textures;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.Laby;
import net.labymod.api.client.render.StatusIconRenderer;
import net.labymod.api.client.render.ExperienceBarRenderer;
import net.labymod.api.client.render.HotbarRenderer;
import net.labymod.api.client.render.StatusIcon;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer;

public class VanillaHudWidgetCanvasRenderer extends VanillaBackgroundRenderer
{
    private static final StatusIcon EMPTY_HEART_ICON;
    private static final StatusIcon HEART_ICON;
    private static final StatusIcon EMPTY_SATURATION_ICON;
    private static final StatusIcon SATURATION_ICON;
    private static final StatusIcon ARMOR_ICON;
    private final HotbarRenderer hotbarRenderer;
    private final ExperienceBarRenderer experienceBarRenderer;
    private final StatusIconRenderer statusIconRenderer;
    
    public VanillaHudWidgetCanvasRenderer() {
        super("HudWidgetCanvas");
        this.hotbarRenderer = Laby.references().hotbarRenderer();
        this.experienceBarRenderer = Laby.references().experienceBarRenderer();
        this.statusIconRenderer = Laby.references().statusIconRenderer();
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        super.renderPre(widget, context);
        this.renderBackgroundImage(context.stack(), widget.bounds());
        this.renderHotbar(context.stack(), widget.bounds());
    }
    
    protected void renderBackgroundImage(final Stack stack, final Bounds bounds) {
        final float backgroundWidth = 1920.0f;
        final float backgroundHeight = 1080.0f;
        final float aspectRatio = backgroundWidth / backgroundHeight;
        final float aspectHeight = bounds.getWidth(BoundsType.INNER) / aspectRatio;
        final float aspectWidth = bounds.getHeight(BoundsType.INNER) * aspectRatio;
        final float verticalOverflow = aspectHeight - bounds.getHeight(BoundsType.INNER);
        final float horizontalOverflow = aspectWidth - bounds.getWidth(BoundsType.INNER);
        final float offsetX = (horizontalOverflow > 0.0f) ? horizontalOverflow : 0.0f;
        final float offsetY = (verticalOverflow > 0.0f) ? verticalOverflow : 0.0f;
        final float heightPercent = 1.0f - offsetY / aspectHeight;
        final float widthPercent = 1.0f - offsetX / aspectWidth;
        this.labyAPI.renderPipeline().resourceRenderer().texture(Textures.Hud.BACKGROUND).pos(bounds.getX(), bounds.getY()).size(bounds.getWidth(), bounds.getHeight()).sprite(backgroundWidth * (1.0f - widthPercent) / 2.0f, backgroundHeight * (1.0f - heightPercent) / 2.0f, backgroundWidth * widthPercent, backgroundHeight * heightPercent).resolution(backgroundWidth, backgroundHeight).render(stack);
    }
    
    protected void renderHotbar(final Stack stack, final Bounds bounds) {
        final float x = bounds.getCenterX();
        final float y = bounds.getBottom();
        float hotbarHeight = this.hotbarRenderer.getHeight();
        this.hotbarRenderer.pos(x - this.hotbarRenderer.getWidth() / 2.0f, y - hotbarHeight);
        ++hotbarHeight;
        this.experienceBarRenderer.pos(x - this.experienceBarRenderer.getWidth() / 2.0f, y - hotbarHeight - this.experienceBarRenderer.getHeight());
        hotbarHeight += this.experienceBarRenderer.getHeight() + 1.0f + 9.0f;
        this.statusIconRenderer.statusIcon(VanillaHudWidgetCanvasRenderer.EMPTY_HEART_ICON, VanillaHudWidgetCanvasRenderer.HEART_ICON).pos(x - this.statusIconRenderer.getWidth(10, 9.0f) - 1.0f, y - hotbarHeight).amount(10).render(stack);
        this.statusIconRenderer.statusIcon(VanillaHudWidgetCanvasRenderer.EMPTY_SATURATION_ICON, VanillaHudWidgetCanvasRenderer.SATURATION_ICON).pos(x + 9.0f + 1.0f, y - hotbarHeight).amount(10).render(stack);
        hotbarHeight += 10.0f;
        this.statusIconRenderer.statusIcon(VanillaHudWidgetCanvasRenderer.ARMOR_ICON).pos(x - this.statusIconRenderer.getWidth(10, 9.0f) - 1.0f, y - hotbarHeight).amount(10).render(stack);
        this.hotbarRenderer.mode(RenderMode.DUMMY).layout(HotbarRenderer.Layout.HORIZONTAL).render(stack);
        this.experienceBarRenderer.mode(RenderMode.DUMMY).render(stack);
    }
    
    static {
        EMPTY_HEART_ICON = new StatusIcon(ResourceLocation.create("minecraft", "hud/heart/container"), 16, 0);
        HEART_ICON = new StatusIcon(ResourceLocation.create("minecraft", "hud/heart/full"), 52, 0);
        EMPTY_SATURATION_ICON = new StatusIcon(ResourceLocation.create("minecraft", "hud/food_empty"), 16, 27);
        SATURATION_ICON = new StatusIcon(ResourceLocation.create("minecraft", "hud/food_full"), 52, 27);
        ARMOR_ICON = new StatusIcon(ResourceLocation.create("minecraft", "hud/armor_full"), 43, 9);
    }
}
