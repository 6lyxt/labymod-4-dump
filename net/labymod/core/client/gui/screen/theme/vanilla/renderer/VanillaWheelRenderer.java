// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.client.render.matrix.Stack;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.draw.TriangleRenderer;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;

public class VanillaWheelRenderer extends ThemeRenderer<WheelWidget>
{
    private static final float BORDER_WIDTH = 2.0f;
    private final TriangleRenderer triangleRenderer;
    
    public VanillaWheelRenderer() {
        super("Wheel");
        this.triangleRenderer = super.labyAPI.renderPipeline().triangleRenderer();
    }
    
    @Override
    public void renderPre(final WheelWidget widget, final ScreenContext context) {
        final float radius = widget.bounds().getWidth(BoundsType.INNER) / 2.0f;
        for (final AbstractWidget<?> child : widget.getGenericChildren()) {
            if (!(child instanceof WheelWidget.Segment)) {
                continue;
            }
            this.renderSegmentBackground(widget, context.stack(), (WheelWidget.Segment)child, widget.bounds().getCenterX(), widget.bounds().getCenterY(), radius, context.getTickDelta());
        }
    }
    
    private void renderSegmentBackground(final WheelWidget wheelWidget, final Stack stack, final WheelWidget.Segment segment, final float centerX, final float centerY, final float radius, final float delta) {
        final float startingAngle = segment.getStartingAngle();
        final float endingAngle = segment.getEndingAngle();
        final float innerRadius = wheelWidget.innerRadius().get();
        final double segmentDistanceRadians = (float)Math.toRadians(wheelWidget.segmentDistanceDegrees().get() / 2.0f);
        final GFXBridge gfx = this.labyAPI.gfxRenderPipeline().gfx();
        gfx.disableTexture();
        gfx.enableBlend();
        gfx.defaultBlend();
        if (segment.isVisible()) {
            int color;
            if (segment.isHighlighted()) {
                color = wheelWidget.segmentHighlightColor().get();
            }
            else if (segment.isSegmentSelected() && segment.isSelectable()) {
                color = wheelWidget.segmentSelectedColor().get();
            }
            else {
                color = wheelWidget.segmentBackgroundColor().get();
            }
            final LssProperty<Integer> segmentColorProperty = segment.segmentColor();
            segmentColorProperty.set(color);
            this.triangleRenderer.renderTrapezoidBordered(stack, centerX + (float)Math.sin(startingAngle) * radius, centerY + (float)Math.cos(startingAngle) * radius, centerX + (float)Math.sin(endingAngle) * radius, centerY + (float)Math.cos(endingAngle) * radius, centerX + (float)Math.sin(endingAngle) * innerRadius, centerY + (float)Math.cos(endingAngle) * innerRadius, centerX + (float)Math.sin(startingAngle) * innerRadius, centerY + (float)Math.cos(startingAngle) * innerRadius, ColorUtil.lerpedColor(wheelWidget.backgroundColorTransitionDuration().get(), delta, segmentColorProperty), wheelWidget.innerBorderColor().get(), 2.0f);
        }
        this.triangleRenderer.renderUnderlined(stack, centerX + (float)Math.sin(startingAngle - segmentDistanceRadians) * (innerRadius - 1.0f), centerY + (float)Math.cos(startingAngle - segmentDistanceRadians) * (innerRadius - 1.0f), centerX + (float)Math.sin(endingAngle + segmentDistanceRadians) * (innerRadius - 1.0f), centerY + (float)Math.cos(endingAngle + segmentDistanceRadians) * (innerRadius - 1.0f), centerX, centerY, wheelWidget.innerBackgroundColor().get(), wheelWidget.innerBorderColor().get(), 2.0f);
        gfx.disableBlend();
        gfx.enableTexture();
    }
}
