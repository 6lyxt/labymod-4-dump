// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.world.canvas;

import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.window.Window;
import java.util.Objects;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.Laby;
import net.labymod.api.client.world.canvas.Canvas;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.world.canvas.CanvasRenderer;

public class ActivityCanvasRenderer implements CanvasRenderer
{
    private static final float SCALE = 0.25f;
    private final Activity activity;
    private boolean initialized;
    
    public ActivityCanvasRenderer(final Activity activity) {
        this.activity = activity;
    }
    
    @Override
    public void render2D(final Stack stack, final MutableMouse mouse, final Canvas canvas, final CanvasSide side, final float tickDelta) {
        if (!this.initialized) {
            final Window window = Laby.labyAPI().minecraft().minecraftWindow();
            this.activity.resize((int)(canvas.getWidth() / 0.25f), (int)(canvas.getHeight() / 0.25f));
            this.activity.load(window);
            this.initialized = true;
        }
        this.transform(stack, canvas, () -> {
            final ScreenContext screenContext2;
            final ScreenContext screenContext = screenContext2 = Laby.references().renderEnvironmentContext().screenContext();
            final Activity activity = this.activity;
            Objects.requireNonNull(activity);
            screenContext2.runInContext(stack, mouse, tickDelta, activity::render);
        });
    }
    
    @Override
    public void renderOverlay2D(final Stack stack, final MutableMouse mouse, final Canvas canvas, final CanvasSide side, final float tickDelta) {
        this.transform(stack, canvas, () -> {
            final ScreenContext screenContext2;
            final ScreenContext screenContext = screenContext2 = Laby.references().renderEnvironmentContext().screenContext();
            final Activity activity = this.activity;
            Objects.requireNonNull(activity);
            screenContext2.runInContext(stack, mouse, tickDelta, activity::renderOverlay);
        });
    }
    
    private void transform(final Stack stack, final Canvas canvas, final Runnable renderer) {
        stack.scale(0.25f, 0.25f, 1.0f);
        final Rectangle bounds = Rectangle.relative(0.0f, 0.0f, canvas.getWidth() / 0.25f, canvas.getHeight() / 0.25f);
        final Window window = Laby.labyAPI().minecraft().minecraftWindow();
        window.transform(null, stack, bounds, renderer);
    }
    
    @Override
    public void dispose(final Canvas canvas) {
        if (this.initialized) {
            this.activity.onCloseScreen();
            this.initialized = false;
        }
    }
}
