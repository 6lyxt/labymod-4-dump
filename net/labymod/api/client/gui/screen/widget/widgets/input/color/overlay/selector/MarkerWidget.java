// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.selector;

import java.awt.Color;
import net.labymod.api.client.render.draw.CircleRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gfx.pipeline.util.Scissor;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class MarkerWidget extends AbstractWidget<Widget>
{
    private static final ModifyReason COLOR_POSITION;
    private final SelectorWidget selectorWidget;
    private float x;
    private float y;
    
    protected MarkerWidget(final SelectorWidget selectorWidget) {
        this.selectorWidget = selectorWidget;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
    }
    
    @Override
    public void render(final ScreenContext context) {
        super.render(context);
        final Scissor scissor = this.labyAPI.gfxRenderPipeline().scissor();
        final Stack stack = context.stack();
        try {
            scissor.push(stack, this.selectorWidget.bounds());
            this.renderMarker(stack);
        }
        finally {
            scissor.pop();
        }
    }
    
    private void renderMarker(final Stack stack) {
        final float width = this.bounds().getWidth() / 2.0f;
        this.labyAPI.renderPipeline().circleRenderer().donutRadius(width - 1.0f, width).pos(this.bounds().getLeft() + width, this.bounds().getTop() + width).color(Color.WHITE).render(stack);
    }
    
    public void setX(final float x) {
        this.x = x;
        final float width = this.bounds().getWidth();
        this.bounds().setLeft(this.selectorWidget.bounds().getLeft() + x - width / 2.0f, MarkerWidget.COLOR_POSITION);
        this.bounds().setWidth(width, MarkerWidget.COLOR_POSITION);
    }
    
    public void setY(final float y) {
        this.y = y;
        final float height = this.bounds().getHeight();
        this.bounds().setTop(this.selectorWidget.bounds().getBottom() - y - height / 2.0f, MarkerWidget.COLOR_POSITION);
        this.bounds().setHeight(height, MarkerWidget.COLOR_POSITION);
    }
    
    public void reapply() {
        if (this.x == 0.0f && this.y == 0.0f) {
            this.selectorWidget.updateMarkerPosition();
        }
        this.setX(this.x);
        this.setY(this.y);
    }
    
    static {
        COLOR_POSITION = ModifyReason.of("colorPosition");
    }
}
