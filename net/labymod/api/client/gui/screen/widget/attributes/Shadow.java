// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.attributes;

import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.client.gui.screen.theme.config.VanillaThemeConfigAccessor;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.MinecraftTextures;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.render.draw.batch.BatchRectangleRenderer;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer;

public class Shadow implements WidgetRenderer<AbstractWidget<?>>
{
    private static final int CLASSIC_COLOR_OUTER;
    private static final int CLASSIC_COLOR_INNER;
    private boolean inset;
    private FloatVector2 offset;
    private float spread;
    private float blur;
    private int color;
    private boolean classic;
    private boolean classicIsBackground;
    private boolean classicLeft;
    private boolean classicTop;
    private boolean classicRight;
    private boolean classicBottom;
    
    public Shadow(final boolean classic, final boolean left, final boolean top, final boolean right, final boolean bottom, final boolean classicIsBackground) {
        this.classic = false;
        this.classicIsBackground = false;
        this.classicLeft = false;
        this.classicTop = false;
        this.classicRight = false;
        this.classicBottom = false;
        this.classic = classic;
        this.classicLeft = left;
        this.classicTop = top;
        this.classicRight = right;
        this.classicBottom = bottom;
        this.classicIsBackground = classicIsBackground;
    }
    
    public Shadow(final boolean inset, final float offsetX, final float offsetY, final float spread, final float blur, final int color) {
        this.classic = false;
        this.classicIsBackground = false;
        this.classicLeft = false;
        this.classicTop = false;
        this.classicRight = false;
        this.classicBottom = false;
        this.inset = inset;
        this.offset = new FloatVector2(offsetX, offsetY);
        this.spread = spread;
        this.blur = blur;
        this.color = color;
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        final Bounds bounds = widget.bounds();
        if (this.classic && this.classicIsBackground) {
            if (this.isFreshUI()) {
                this.renderFreshShadow(context.stack(), bounds);
            }
            else {
                this.renderClassicShadow(context.stack(), bounds);
            }
        }
        else if (!this.classic) {
            final float blurStart = this.spread - this.blur / 2.0f;
            if (!this.inset) {
                final BatchRectangleRenderer renderer = Laby.references().rectangleRenderer().beginBatch(context.stack());
                final MutableRectangle rect = bounds.rectangle(BoundsType.MIDDLE).copy().expand(this.offset).expand(blurStart);
                renderer.pos(rect).outline(0, this.color, this.blur).build();
                renderer.pos(rect).color(this.color).build();
                renderer.upload();
            }
        }
    }
    
    @Override
    public void renderPost(final AbstractWidget<?> widget, final ScreenContext context) {
        final Bounds bounds = widget.bounds();
        if (this.classic && !this.classicIsBackground) {
            if (this.isFreshUI()) {
                this.renderFreshShadow(context.stack(), bounds);
            }
            else {
                this.renderClassicShadow(context.stack(), bounds);
            }
        }
        else if (!this.classic) {
            final float blurStart = this.spread - this.blur / 2.0f;
            if (this.inset) {
                final BatchRectangleRenderer renderer = Laby.references().rectangleRenderer().beginBatch(context.stack());
                final Rectangle middleRectangle = bounds.rectangle(BoundsType.MIDDLE);
                final float middleLeft = middleRectangle.getLeft();
                final float middleTop = middleRectangle.getTop();
                final float middleRight = middleRectangle.getRight();
                renderer.pos(middleLeft, middleTop, middleRight, middleTop + this.offset.getY() + blurStart).color(this.color).build();
                renderer.pos(middleLeft, middleTop, middleRight, middleTop + this.offset.getY() + blurStart).color(this.color).build();
                final float middleBottom = middleRectangle.getBottom();
                renderer.pos(middleLeft, middleBottom + this.offset.getY() - blurStart, middleRight, middleBottom).color(this.color).build();
                renderer.pos(middleLeft, middleTop, middleLeft + this.offset.getX() + blurStart, middleBottom).color(this.color).build();
                renderer.pos(middleRight + this.offset.getX() - blurStart, middleTop, middleRight, middleBottom).color(this.color).build();
                renderer.renderOutline(bounds.getLeft(BoundsType.MIDDLE) + Math.max(this.offset.getX() + blurStart + this.blur, this.blur), bounds.getTop(BoundsType.MIDDLE) + Math.max(this.offset.getY() + blurStart + this.blur, this.blur), bounds.getRight(BoundsType.MIDDLE) + Math.min(this.offset.getX() - blurStart - this.blur, -this.blur), bounds.getBottom(BoundsType.MIDDLE) + Math.min(this.offset.getY() - blurStart - this.blur, -this.blur), this.color, 0, this.blur);
                renderer.upload();
            }
        }
    }
    
    private void renderClassicShadow(final Stack stack, final Bounds bounds) {
        final BatchRectangleRenderer renderer = Laby.references().rectangleRenderer().beginBatch(stack);
        if (this.classicLeft) {
            renderer.pos(bounds.getLeft(BoundsType.MIDDLE), bounds.getTop(BoundsType.MIDDLE), bounds.getLeft(BoundsType.MIDDLE) + 5.0f, bounds.getBottom(BoundsType.MIDDLE)).gradientHorizontal(Shadow.CLASSIC_COLOR_OUTER, Shadow.CLASSIC_COLOR_INNER).build();
        }
        if (this.classicTop) {
            renderer.pos(bounds.getLeft(BoundsType.MIDDLE), bounds.getTop(BoundsType.MIDDLE), bounds.getRight(BoundsType.MIDDLE), bounds.getY(BoundsType.MIDDLE) + 5.0f).gradientVertical(Shadow.CLASSIC_COLOR_OUTER, Shadow.CLASSIC_COLOR_INNER).build();
        }
        if (this.classicRight) {
            renderer.pos(bounds.getRight(BoundsType.MIDDLE) - 5.0f, bounds.getTop(BoundsType.MIDDLE), bounds.getRight(BoundsType.MIDDLE), bounds.getBottom(BoundsType.MIDDLE)).gradientHorizontal(Shadow.CLASSIC_COLOR_INNER, Shadow.CLASSIC_COLOR_OUTER).build();
        }
        if (this.classicBottom) {
            renderer.pos(bounds.getLeft(BoundsType.MIDDLE), bounds.getBottom(BoundsType.MIDDLE) - 5.0f, bounds.getRight(BoundsType.MIDDLE), bounds.getBottom(BoundsType.MIDDLE)).gradientVertical(Shadow.CLASSIC_COLOR_INNER, Shadow.CLASSIC_COLOR_OUTER).build();
        }
        renderer.upload();
    }
    
    private void renderFreshShadow(final Stack stack, final Bounds bounds) {
        final ReasonableMutableRectangle rectangle = bounds.rectangle(BoundsType.MIDDLE);
        final MinecraftTextures textures = Laby.labyAPI().minecraft().textures();
        final ResourceRenderer resourceRenderer = Laby.references().resourceRenderer();
        if (this.classicTop) {
            final ResourceLocation location = textures.screenMenuHeaderSeparatorTexture();
            resourceRenderer.texture(location).pos(rectangle.getLeft(), rectangle.getTop()).size(rectangle.getWidth(), 2.0f).sprite(0.0f, 0.0f, 1.0f, 1.0f).resolution(1.0f, 1.0f).render(stack);
        }
        if (this.classicBottom) {
            final ResourceLocation location = textures.screenMenuFooterSeparatorTexture();
            resourceRenderer.texture(location).pos(rectangle.getLeft(), rectangle.getBottom() - 1.0f).size(rectangle.getWidth(), 2.0f).sprite(0.0f, 0.0f, 1.0f, 1.0f).resolution(1.0f, 1.0f).render(stack);
        }
    }
    
    public void setInset(final boolean inset) {
        this.inset = inset;
    }
    
    public void setOffset(final FloatVector2 offset) {
        this.offset = offset;
    }
    
    public void setSpread(final float spread) {
        this.spread = spread;
    }
    
    public void setBlur(final float blur) {
        this.blur = blur;
    }
    
    public void setColor(final int color) {
        this.color = color;
    }
    
    public boolean isInset() {
        return this.inset;
    }
    
    public FloatVector2 getOffset() {
        return this.offset;
    }
    
    public float getSpread() {
        return this.spread;
    }
    
    public float getBlur() {
        return this.blur;
    }
    
    public int getColor() {
        return this.color;
    }
    
    private boolean isFreshUI() {
        final VanillaThemeConfigAccessor config = Laby.labyAPI().themeService().getThemeConfig(VanillaThemeConfigAccessor.class);
        return PlatformEnvironment.isFreshUI() && config != null && config.freshUI().get();
    }
    
    static {
        CLASSIC_COLOR_OUTER = ColorFormat.ARGB32.pack(0, 0, 0, 255);
        CLASSIC_COLOR_INNER = ColorFormat.ARGB32.pack(0, 0, 0, 0);
    }
}
