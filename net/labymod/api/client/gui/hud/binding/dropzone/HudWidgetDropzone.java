// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.binding.dropzone;

import net.labymod.api.client.gui.hud.position.HudWidgetAnchor;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.widgets.hud.HudWidgetWidget;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.Laby;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.hud.HudWidgetRendererAccessor;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.hud.binding.HudWidgetBinding;

public abstract class HudWidgetDropzone extends HudWidgetBinding
{
    private static final int ANIMATION_DURATION_TICKS = 8;
    private int opacity;
    private int prevOpacity;
    
    public HudWidgetDropzone(@NotNull final String id) {
        super(id);
    }
    
    public abstract float getX(final HudWidgetRendererAccessor p0, final HudSize p1);
    
    public abstract float getY(final HudWidgetRendererAccessor p0, final HudSize p1);
    
    public void render(final Stack stack, final float tickDelta, final HudWidgetRendererAccessor renderer, final HudSize hudWidgetSize) {
        if (this.opacity <= 0) {
            return;
        }
        final float opacity = MathHelper.lerp(this.opacity / 8.0f, this.prevOpacity / 8.0f);
        final float x = this.getX(renderer, hudWidgetSize);
        final float y = this.getY(renderer, hudWidgetSize);
        Laby.labyAPI().renderPipeline().rectangleRenderer().pos(x, y).size(hudWidgetSize.getScaledWidth(), hudWidgetSize.getScaledHeight()).color(ColorFormat.ARGB32.pack(255, 255, 255, (int)(opacity * 90.0f))).render(stack);
    }
    
    public void tick(final boolean visible, final boolean isOverlapping) {
        this.prevOpacity = this.opacity;
        if (visible) {
            if (isOverlapping) {
                if (this.opacity < 8) {
                    ++this.opacity;
                }
            }
            else {
                if (this.opacity > 4) {
                    --this.opacity;
                }
                if (this.opacity < 4) {
                    ++this.opacity;
                }
            }
        }
        else if (this.opacity > 0) {
            --this.opacity;
        }
    }
    
    public boolean isOverlapping(final HudWidgetRendererAccessor renderer, final HudWidgetWidget widget) {
        final Bounds bounds = widget.bounds();
        return this.isOverlapping(renderer, widget.size(), bounds.getX(), bounds.getY());
    }
    
    public boolean isInside(final HudWidgetRendererAccessor renderer, final HudWidgetWidget widget) {
        final Bounds bounds = widget.bounds();
        return this.isInside(renderer, widget.size(), bounds.getX(), bounds.getY());
    }
    
    public boolean isOverlapping(final HudWidgetRendererAccessor renderer, final HudSize size, final float hudWidgetX, final float hudWidgetY) {
        final float x = this.getX(renderer, size);
        final float y = this.getY(renderer, size);
        return hudWidgetX + size.getScaledWidth() >= x && hudWidgetX <= x + size.getScaledWidth() && hudWidgetY + size.getScaledHeight() >= y && hudWidgetY <= y + size.getScaledHeight();
    }
    
    public boolean isInside(final HudWidgetRendererAccessor renderer, final HudSize size, final float hudWidgetX, final float hudWidgetY) {
        final float x = this.getX(renderer, size);
        final float y = this.getY(renderer, size);
        final float centerX = hudWidgetX + size.getScaledWidth() / 2.0f;
        final float centerY = hudWidgetY + size.getScaledHeight() / 2.0f;
        return centerX >= x && centerX <= x + size.getScaledWidth() && centerY >= y && centerY <= y + size.getScaledHeight();
    }
    
    public abstract HudWidgetDropzone copy();
    
    public abstract HudWidgetAnchor getAnchor();
}
