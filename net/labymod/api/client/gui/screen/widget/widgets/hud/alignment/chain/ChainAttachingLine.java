// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.hud.alignment.chain;

import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.Laby;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.widget.widgets.hud.alignment.ChainAlignmentSide;
import net.labymod.api.client.gui.screen.widget.widgets.hud.alignment.AlignmentLine;

public class ChainAttachingLine extends AlignmentLine
{
    private final ChainAlignmentSide side;
    
    public ChainAttachingLine(final ChainAlignmentSide side) {
        this.side = side;
    }
    
    @Override
    public void render(final Stack stack, final Rectangle chain, final float tickDelta) {
        super.render(stack, chain, tickDelta);
        final float opacity = this.getOpacity(tickDelta);
        if (opacity > 0.0f) {
            Laby.labyAPI().renderPipeline().rectangleRenderer().pos(0.0f, (this.side == ChainAlignmentSide.TOP) ? 0.0f : chain.getHeight()).size(chain.getWidth(), 1.0f).color(ColorFormat.ARGB32.pack(255, 255, 255, (int)(opacity * 255.0f))).render(stack);
        }
    }
}
