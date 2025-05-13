// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.selector;

import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.Color;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.Orientation;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorData;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;

@AutoWidget
public class ShadeSelectorWidget extends SelectorWidget
{
    private static final int WHITE_LEFT;
    private static final int WHITE_RIGHT;
    private static final int BLACK_TOP;
    private static final int BLACK_BOTTOM;
    
    public ShadeSelectorWidget(final ColorData colorData) {
        super(colorData);
    }
    
    @Override
    public void renderSelector(final Orientation orientation, final Stack stack) {
        ShadeSelectorWidget.RENDER_CONTEXT.begin(stack);
        ShadeSelectorWidget.RENDER_CONTEXT.render(this.bounds(), Color.HSBtoRGB(this.colorData().getColor().getHue(), 100, 100));
        ShadeSelectorWidget.RENDER_CONTEXT.renderGradientHorizontally(this.bounds(), ShadeSelectorWidget.WHITE_LEFT, ShadeSelectorWidget.WHITE_RIGHT);
        ShadeSelectorWidget.RENDER_CONTEXT.renderGradientVertically(this.bounds(), ShadeSelectorWidget.BLACK_TOP, ShadeSelectorWidget.BLACK_BOTTOM);
        ShadeSelectorWidget.RENDER_CONTEXT.uploadToBuffer();
    }
    
    @Override
    public void update(final float posX, final float posY) {
        this.updateMarkerPosition(posX, posY);
        final float saturation = MathHelper.clamp(posX * 100.0f / this.bounds().getWidth(), 0.0f, 100.0f);
        final float brightness = MathHelper.clamp(posY * 100.0f / this.bounds().getHeight(), 0.0f, 100.0f);
        this.colorData().setSaturation((int)saturation);
        this.colorData().setBrightness((int)brightness);
        this.colorData().applySB();
    }
    
    @Override
    public void updateMarkerPosition() {
        final Color color = this.colorData().getColor();
        final float posX = color.getSaturation() / 100.0f * this.bounds().getWidth();
        final float posY = color.getBrightness() / 100.0f * this.bounds().getHeight();
        this.updateMarkerPosition(posX, posY);
    }
    
    static {
        WHITE_LEFT = ColorFormat.ARGB32.pack(255, 255, 255, 255);
        WHITE_RIGHT = ColorFormat.ARGB32.pack(255, 255, 255, 0);
        BLACK_TOP = ColorFormat.ARGB32.pack(0, 0, 0, 0);
        BLACK_BOTTOM = ColorFormat.ARGB32.pack(0, 0, 0, 255);
    }
}
