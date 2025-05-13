// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity.types;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.Activity;

public abstract class IngameOverlayActivity extends Activity
{
    public IngameOverlayActivity() {
    }
    
    public IngameOverlayActivity(final boolean handleStyleSheet) {
        super(handleStyleSheet);
    }
    
    @Override
    public void render(final ScreenContext context) {
        if (this.isVisible()) {
            super.render(context);
        }
    }
    
    @Deprecated(forRemoval = true, since = "4.2.42")
    @Override
    public void render(final Stack stack, final MutableMouse mouse, final float tickDelta) {
        if (this.isVisible()) {
            super.render(stack, mouse, tickDelta);
        }
    }
    
    public boolean isAcceptingInput() {
        return false;
    }
    
    public abstract boolean isVisible();
    
    public boolean isRenderedHidden() {
        return false;
    }
    
    @Override
    public boolean allowCustomFont() {
        return false;
    }
    
    public int getPriority() {
        return 0;
    }
}
