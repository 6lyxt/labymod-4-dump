// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.interaction;

import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
public abstract class AbstractPointWidget extends SimpleWidget
{
    private boolean previousHovered;
    private long timeLastHoverStateChanged;
    
    public AbstractPointWidget() {
        this.previousHovered = false;
    }
    
    public void updateHovered(final boolean hovered) {
        if (this.previousHovered != hovered) {
            this.previousHovered = hovered;
            this.timeLastHoverStateChanged = TimeUtil.getMillis();
        }
    }
    
    public float getHoverStrength() {
        final long timeSinceHoverStateChanged = TimeUtil.getMillis() - this.timeLastHoverStateChanged;
        final float progress = Math.min(1.0f, timeSinceHoverStateChanged / 100.0f);
        return this.previousHovered ? progress : (1.0f - progress);
    }
    
    public float getRadius() {
        return this.bounds().getWidth();
    }
    
    public void setCenteredScale(final float scale) {
        final Bounds bounds = this.bounds();
        this.setTranslateX(-bounds.getWidth() / 2.0f * (scale - 1.0f));
        this.setTranslateY(-bounds.getHeight() / 2.0f * (scale - 1.0f));
        this.setScale(scale);
    }
}
