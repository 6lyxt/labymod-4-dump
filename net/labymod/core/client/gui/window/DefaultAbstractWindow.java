// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.window;

import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.PositionedBounds;
import java.util.HashMap;
import net.labymod.api.client.gui.lss.variable.LssVariable;
import java.util.Map;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.window.Window;

public abstract class DefaultAbstractWindow implements Window
{
    private static final ModifyReason WINDOW_TRANSFORMED;
    protected static final ModifyReason WINDOW_RESET;
    protected final Bounds bounds;
    protected final Bounds absoluteBounds;
    public Rectangle parent;
    protected boolean alwaysResetBounds;
    protected Object currentScreen;
    protected ScreenWrapper currentScreenWrapper;
    private final Map<String, LssVariable> lssVariables;
    
    public DefaultAbstractWindow() {
        this.lssVariables = new HashMap<String, LssVariable>();
        this.bounds = new PositionedBounds();
        this.absoluteBounds = new PositionedBounds();
        this.alwaysResetBounds = true;
    }
    
    @Override
    public int getScaledWidth() {
        if (this.alwaysResetBounds) {
            this.resetBounds();
        }
        return (int)this.bounds.getWidth();
    }
    
    @Override
    public int getScaledHeight() {
        if (this.alwaysResetBounds) {
            this.resetBounds();
        }
        return (int)this.bounds.getHeight();
    }
    
    @Override
    public Bounds bounds() {
        if (this.alwaysResetBounds) {
            this.resetBounds();
        }
        return this.bounds;
    }
    
    @Override
    public Bounds absoluteBounds() {
        this.resetAbsoluteBounds();
        return this.absoluteBounds;
    }
    
    @Override
    public void transform(final Parent parentObject, final Stack stack, final Rectangle rectangle, final Runnable runnable) {
        final Rectangle parent = this.bounds.copy();
        final float scaleFactor = this.getScale();
        this.alwaysResetBounds = false;
        stack.push();
        this.bounds.setPosition(this.bounds.getX() + rectangle.getX(), this.bounds.getY() + rectangle.getY(), DefaultAbstractWindow.WINDOW_TRANSFORMED);
        this.bounds.setSize(rectangle.getWidth(), rectangle.getHeight(), DefaultAbstractWindow.WINDOW_TRANSFORMED);
        this.updateViewPort(stack, rectangle, parent);
        this.parent = parent;
        runnable.run();
        this.bounds.set(parent, DefaultAbstractWindow.WINDOW_TRANSFORMED);
        stack.pop();
        this.alwaysResetBounds = true;
    }
    
    @Override
    public Parent getParent() {
        return null;
    }
    
    @Override
    public Parent getRoot() {
        return this;
    }
    
    protected void updateViewPort(final Stack stack, final Rectangle rectangle, final Rectangle parent) {
        final float x = rectangle.getX();
        final float y = rectangle.getY();
        stack.translate(x, y, 0.0f);
    }
    
    public abstract void resetBounds();
    
    public abstract void resetAbsoluteBounds();
    
    @Override
    public Map<String, LssVariable> getLssVariables() {
        return this.lssVariables;
    }
    
    @Override
    public void updateLssVariable(final LssVariable variable) {
        for (final Activity openActivity : Laby.references().activityController().getOpenActivities()) {
            openActivity.updateLssVariable(variable);
        }
    }
    
    @Override
    public void forceUpdateLssVariable(final LssVariable variable) {
        for (final Activity openActivity : Laby.references().activityController().getOpenActivities()) {
            openActivity.forceUpdateLssVariable(variable);
        }
    }
    
    static {
        WINDOW_TRANSFORMED = ModifyReason.of("windowTransformed");
        WINDOW_RESET = ModifyReason.of("windowReset");
    }
}
