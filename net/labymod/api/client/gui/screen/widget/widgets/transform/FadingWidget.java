// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.transform;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.function.BooleanSupplier;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.WrappedWidget;

@AutoWidget
public class FadingWidget extends WrappedWidget
{
    private final long timestamp;
    private BooleanSupplier forceRendered;
    
    protected FadingWidget(final Widget widget, final long timestamp) {
        super(widget);
        this.timestamp = timestamp;
    }
    
    public static FadingWidget until(final Widget widget, final long timestamp) {
        return new FadingWidget(widget, timestamp);
    }
    
    public static FadingWidget forMillis(final Widget widget, final long millis) {
        return until(widget, TimeUtil.getMillis() + millis);
    }
    
    public long getTimestamp() {
        return this.timestamp;
    }
    
    public void setForceRendered(final BooleanSupplier forceRendered) {
        this.forceRendered = forceRendered;
    }
    
    private boolean isRendered() {
        if (this.forceRendered != null && this.forceRendered.getAsBoolean()) {
            return this.forceRendered.getAsBoolean();
        }
        return this.timestamp > TimeUtil.getMillis() && this.getAlpha() >= 0.0225f;
    }
    
    public long getMillisLeft() {
        return this.timestamp - TimeUtil.getMillis();
    }
    
    private float getAlpha() {
        if (this.forceRendered != null && this.forceRendered.getAsBoolean()) {
            return 1.0f;
        }
        double alphaRoot = this.getMillisLeft() / 50.0 / 200.0;
        alphaRoot *= 10.0;
        alphaRoot = MathHelper.clamp(alphaRoot, 0.0, 1.0);
        return (float)(alphaRoot * alphaRoot);
    }
    
    @Override
    public void render(final ScreenContext context) {
        if (this.isRendered()) {
            final float alpha = this.getAlpha();
            Laby.labyAPI().renderPipeline().multiplyAlpha(alpha, () -> super.render(context));
        }
    }
    
    @Override
    public boolean isVisible() {
        return this.childWidget instanceof AbstractWidget && (boolean)((AbstractWidget)this.childWidget).visible().get();
    }
}
