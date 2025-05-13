// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.multiplayer.directconnect;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.util.time.TimeUtil;
import java.awt.Color;
import java.util.function.Consumer;
import java.util.function.Function;
import net.labymod.api.client.render.batch.RectangleRenderContext;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
public class LoadingBarWidget extends SimpleWidget
{
    private static final RectangleRenderContext RECTANGLE_RENDERER;
    private final Function<Consumer<State>, State> callback;
    private final long cooldown;
    private Color successColor;
    private Color failedColor;
    private State state;
    private Color color;
    private long lastUpdate;
    private boolean disabled;
    
    public LoadingBarWidget(final long cooldown, final State defaultState, final Function<Consumer<State>, State> callback) {
        this.successColor = new Color(105, 240, 105, 150);
        this.failedColor = new Color(240, 105, 105, 150);
        this.cooldown = cooldown;
        this.callback = callback;
        this.lastUpdate = TimeUtil.getMillis();
        this.state = defaultState;
        this.color = (defaultState.isPositive() ? this.successColor : this.failedColor);
    }
    
    @Override
    public void render(final ScreenContext context) {
        super.render(context);
        if (!this.isVisible()) {
            return;
        }
        final Bounds bounds = this.bounds();
        float progress = bounds.getWidth() / this.cooldown * (TimeUtil.getMillis() - this.lastUpdate);
        if (progress > bounds.getWidth()) {
            progress = bounds.getWidth();
        }
        int colorPercent = 50;
        if (this.lastUpdate != -1L && !this.disabled) {
            colorPercent = (int)Math.round(155.0 / this.cooldown * (TimeUtil.getMillis() - this.lastUpdate - 100L));
        }
        final int fadeOutColor = ColorFormat.ARGB32.pack(this.color, Math.min(Math.max(165 - colorPercent, 0), 255));
        LoadingBarWidget.RECTANGLE_RENDERER.begin(context.stack());
        LoadingBarWidget.RECTANGLE_RENDERER.render(bounds, Color.BLACK.getRGB());
        LoadingBarWidget.RECTANGLE_RENDERER.render(bounds, fadeOutColor);
        LoadingBarWidget.RECTANGLE_RENDERER.render(bounds.getLeft(), bounds.getTop(), bounds.getLeft() + progress, bounds.getBottom(), this.color);
        LoadingBarWidget.RECTANGLE_RENDERER.uploadToBuffer();
    }
    
    @Override
    public void tick() {
        super.tick();
        if (this.disabled || this.state == State.CANCELED || this.state == State.LOADING) {
            return;
        }
        if (this.lastUpdate != -1L && this.lastUpdate + this.cooldown < TimeUtil.getMillis()) {
            this.update();
        }
    }
    
    public State getState() {
        return this.state;
    }
    
    public void update() {
        final State state = this.callback.apply(this::updateState);
        if (state == State.SUCCESS) {
            this.state = State.LOADING;
            this.updateState(State.SUCCESS);
        }
        else {
            this.state = state;
        }
    }
    
    public void updateState(final State state) {
        if (this.state != state) {
            this.lastUpdate = TimeUtil.getMillis();
            this.state = state;
            if (this.state != State.LOADING) {
                this.applyColor();
            }
        }
    }
    
    public void disable() {
        this.disabled = true;
        this.state = State.CANCELED;
        this.applyColor();
    }
    
    public void enable() {
        this.disabled = false;
        this.state = State.SUCCESS;
        this.applyColor();
        this.update();
    }
    
    private void applyColor() {
        this.color = (this.state.isPositive() ? this.successColor : this.failedColor);
    }
    
    static {
        RECTANGLE_RENDERER = Laby.references().rectangleRenderContext();
    }
    
    public enum State
    {
        SUCCESS(true), 
        LOADING(true), 
        FAILED(false), 
        CANCELED(false);
        
        private final boolean positive;
        
        private State(final boolean positive) {
            this.positive = positive;
        }
        
        public boolean isPositive() {
            return this.positive;
        }
    }
}
