// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity.types;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.Activity;

public abstract class SimpleActivity extends Activity
{
    @Deprecated(forRemoval = true, since = "4.1.23")
    protected boolean renderBackground;
    
    public SimpleActivity() {
        this.renderBackground = true;
    }
    
    @Override
    public boolean renderBackground(final ScreenContext context) {
        return !this.shouldRenderBackground();
    }
    
    public boolean shouldRenderBackground() {
        return this.renderBackground;
    }
    
    @Deprecated(forRemoval = true, since = "4.1.23")
    public void renderBackground(final Stack stack) {
    }
}
