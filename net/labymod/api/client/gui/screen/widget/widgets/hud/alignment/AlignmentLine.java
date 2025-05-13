// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.hud.alignment;

import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.Laby;
import net.labymod.api.client.render.draw.RectangleRenderer;

public abstract class AlignmentLine
{
    private static final int ANIMATION_DURATION_TICKS = 7;
    protected final RectangleRenderer rectangleRenderer;
    protected int opacity;
    protected int prevOpacity;
    
    public AlignmentLine() {
        this.opacity = 0;
        this.prevOpacity = 0;
        this.rectangleRenderer = Laby.labyAPI().renderPipeline().rectangleRenderer();
    }
    
    public void render(final Stack stack, final Rectangle chain, final float tickDelta) {
    }
    
    public void tick(final boolean visible) {
        this.prevOpacity = this.opacity;
        if (visible) {
            if (this.opacity < 7) {
                ++this.opacity;
            }
        }
        else if (this.opacity > 0) {
            --this.opacity;
        }
    }
    
    protected float getOpacity(final float tickDelta) {
        return MathHelper.lerp((float)this.opacity, (float)this.prevOpacity) / 7.0f;
    }
}
