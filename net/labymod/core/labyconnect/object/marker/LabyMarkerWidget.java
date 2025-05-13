// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.object.marker;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.Textures;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.Laby;
import net.labymod.api.client.render.draw.PlayerHeadRenderer;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class LabyMarkerWidget extends AbstractWidget<Widget>
{
    private final MarkerObject marker;
    
    public LabyMarkerWidget(final MarkerObject marker) {
        this.marker = marker;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.bounds().setSize(20.0f, 16.0f, ModifyReason.of(LabyMarkerWidget.class, "initialize"));
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        super.renderWidget(context);
        final Stack stack = context.stack();
        stack.push();
        Laby.references().playerHeadRenderer().player(this.marker.getOwner()).pos(this.bounds().getX(), this.bounds().getY()).size(16.0f).render(stack);
        float pause = (float)(Math.sin(TimeUtil.getMillis() / 500.0) * 3.0);
        if (pause < 0.0f) {
            pause = 0.0f;
        }
        final float bounce = (float)Math.abs(Math.cos(TimeUtil.getMillis() / 100.0) * -pause);
        Textures.SpriteCommon.EXCLAMATION_MARK_LIGHT.render(stack, this.bounds().getX() + 20.0f - 7.0f, this.bounds().getY() - bounce, 8.0f, 16.0f);
        stack.pop();
    }
}
