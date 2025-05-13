// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.selector;

import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.Color;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.Orientation;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorData;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;

@AutoWidget
public class AlphaSelectorWidget extends SelectorWidget
{
    public AlphaSelectorWidget(final ColorData colorData) {
        super(colorData);
    }
    
    @Override
    public void renderSelector(final Orientation orientation, final Stack stack) {
        AlphaSelectorWidget.RENDER_CONTEXT.begin(stack);
        final Color color = this.colorData().getColor();
        if (orientation == Orientation.HORIZONTAL) {
            AlphaSelectorWidget.RENDER_CONTEXT.renderGradientHorizontally(this.bounds(), ColorFormat.ARGB32.pack(color.get(), 255), ColorFormat.ARGB32.pack(color.get(), 0));
        }
        else {
            AlphaSelectorWidget.RENDER_CONTEXT.renderGradientVertically(this.bounds(), ColorFormat.ARGB32.pack(color.get(), 255), ColorFormat.ARGB32.pack(color.get(), 0));
        }
        AlphaSelectorWidget.RENDER_CONTEXT.uploadToBuffer();
    }
    
    @Override
    public void update(final float posX, final float posY) {
        this.updateMarkerPosition(this.bounds().getWidth() / 2.0f, posY);
        final float alpha = MathHelper.clamp(posY * 255.0f / this.bounds().getHeight(), 0.0f, 255.0f);
        this.colorData().setAlpha((int)alpha);
    }
    
    @Override
    public void updateMarkerPosition() {
        final float posY = this.colorData().getColor().getAlpha() / 255.0f * this.bounds().getHeight();
        this.updateMarkerPosition(this.bounds().getWidth() / 2.0f, posY);
    }
}
