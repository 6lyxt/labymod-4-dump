// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer.hudwidget;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.render.draw.batch.BatchResourceRenderer;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Textures;
import net.labymod.api.client.render.matrix.Stack;
import java.util.Iterator;
import net.labymod.core.client.gui.screen.widget.widgets.hud.window.grid.HudWidgetTypeInfoWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.core.client.gui.screen.widget.widgets.hud.window.grid.HudWidgetTilesGridWidget;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;

public class VanillaHudWidgetTilesGridRenderer extends ThemeRenderer<HudWidgetTilesGridWidget>
{
    public VanillaHudWidgetTilesGridRenderer() {
        super("HudWidgetTilesGrid");
    }
    
    @Override
    public void renderPre(final HudWidgetTilesGridWidget widget, final ScreenContext context) {
        super.renderPre(widget, context);
        for (final HudWidgetTypeInfoWidget infoWidget : widget.getChildren()) {
            if (infoWidget.isDragging()) {
                continue;
            }
            this.renderBackground(context.stack(), infoWidget);
        }
    }
    
    @Override
    public void renderPost(final HudWidgetTilesGridWidget widget, final ScreenContext context) {
        super.renderPost(widget, context);
        for (final HudWidgetTypeInfoWidget infoWidget : widget.getChildren()) {
            if (!infoWidget.isDragging()) {
                continue;
            }
            this.renderBackground(context.stack(), infoWidget);
            infoWidget.render(context);
        }
    }
    
    protected void renderBackground(final Stack stack, final HudWidgetTypeInfoWidget infoWidget) {
        final HudWidget<?> hudWidget = infoWidget.hudWidget();
        final Bounds bounds = infoWidget.bounds();
        final int spriteY = (!hudWidget.isEnabled() || infoWidget.isDragging()) ? (infoWidget.isHovered() ? 16 : 0) : 32;
        final BatchResourceRenderer renderer = this.labyAPI.renderPipeline().resourceRenderer().beginBatch(stack, Textures.SpriteWidgetEditor.TEXTURE);
        final float width = bounds.getWidth();
        final float height = bounds.getHeight();
        final int partSizeWidth = MathHelper.ceil(width / 2.0f);
        final int partSizeHeight = MathHelper.ceil(height / 2.0f);
        renderer.size((float)partSizeWidth, (float)partSizeHeight).resolution(128.0f, 128.0f).sprite(16.0f, (float)spriteY, 12.0f, 12.0f).pos(bounds.getX() + infoWidget.getInterpolationOffsetX(), bounds.getY() + infoWidget.getInterpolationOffsetY()).build();
        renderer.size((float)partSizeWidth, (float)partSizeHeight).resolution(128.0f, 128.0f).sprite(20.0f, (float)spriteY, 12.0f, 12.0f).pos(bounds.getRight() + infoWidget.getInterpolationOffsetX() - width / 2.0f, bounds.getY() + infoWidget.getInterpolationOffsetY()).build();
        renderer.size((float)partSizeWidth, (float)partSizeHeight).resolution(128.0f, 128.0f).sprite(16.0f, (float)(spriteY + 8 - 4), 12.0f, 12.0f).pos(bounds.getX() + infoWidget.getInterpolationOffsetX(), bounds.getBottom() + infoWidget.getInterpolationOffsetY() - height / 2.0f).build();
        renderer.size((float)partSizeWidth, (float)partSizeHeight).resolution(128.0f, 128.0f).sprite(20.0f, (float)(spriteY + 8 - 4), 12.0f, 12.0f).pos(bounds.getRight() + infoWidget.getInterpolationOffsetX() - width / 2.0f, bounds.getBottom() + infoWidget.getInterpolationOffsetY() - height / 2.0f).build();
        renderer.upload();
    }
}
