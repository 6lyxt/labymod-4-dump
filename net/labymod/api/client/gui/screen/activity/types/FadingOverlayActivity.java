// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity.types;

import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.client.gui.screen.ScreenInstance;

public abstract class FadingOverlayActivity extends AbstractLayerActivity
{
    protected boolean fading;
    protected long fadeInStart;
    protected boolean fadeParent;
    
    public FadingOverlayActivity(final ScreenInstance parentScreen) {
        this(parentScreen, !PlatformEnvironment.isAncientOpenGL());
    }
    
    public FadingOverlayActivity(final ScreenInstance parentScreen, final boolean fading) {
        super(parentScreen);
        this.fadeParent = true;
        this.fading = fading;
    }
    
    @Override
    public void render(final ScreenContext context) {
        final long running = this.labyAPI.minecraft().getRunningMillis();
        if (this.fadeInStart == 0L && this.fading) {
            this.fadeInStart = running;
        }
        final float timePassed = this.fading ? ((running - this.fadeInStart) / 1000.0f) : 2.0f;
        float opacity = MathHelper.clamp(timePassed - 1.0f, 0.0f, 1.0f);
        if (PlatformEnvironment.isAncientOpenGL()) {
            opacity = 1.0f;
        }
        this.labyAPI.renderPipeline().multiplyAlpha(opacity, () -> {
            super.renderParent(context);
            this.renderCustom(context);
            return;
        });
        ((Document)this.document).opacity().set(opacity);
        super.renderSuper(context);
    }
    
    @Override
    protected void renderParent(final ScreenContext environment) {
        if (this.fadeParent) {
            super.renderParent(environment);
        }
        else {
            this.labyAPI.renderPipeline().setAlpha(1.0f, () -> super.renderParent(environment));
        }
    }
    
    protected void renderCustom(final ScreenContext environment) {
    }
}
