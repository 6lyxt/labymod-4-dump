// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.title.header;

import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.title.header.SplashWidgetAccessor;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class SplashWidget extends AbstractWidget<Widget> implements SplashWidgetAccessor
{
    private RenderableComponent splashText;
    
    @Override
    public void setSplashText(final String splashText) {
        this.splashText = RenderableComponent.of(Component.text(splashText));
    }
    
    @Override
    public RenderableComponent getSplashText() {
        return this.splashText;
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        super.renderWidget(context);
        final Stack stack = context.stack();
        if (this.splashText != null) {
            stack.push();
            stack.translate(this.bounds().getCenterX(), this.bounds().getCenterY() + this.splashText.getHeight() / 2.0f, 0.0f);
            stack.rotate(-20.0f, 0.0f, 0.0f, 1.0f);
            float scale = 1.8f - Math.abs(MathHelper.sin(TimeUtil.getMillis() % 1000L / 1000.0f * 6.2831855f) * 0.1f);
            scale = scale * 100.0f / (this.splashText.getWidth() + 32.0f);
            stack.scale(scale, scale, scale);
            this.labyAPI.renderPipeline().componentRenderer().builder().pos(0.0f, -8.0f).color(NamedTextColor.YELLOW.getValue()).text(this.splashText).centered(true).render(stack);
            stack.pop();
        }
    }
    
    @Override
    public float getContentWidth(final BoundsType type) {
        return 1.0f;
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        return 1.0f;
    }
}
