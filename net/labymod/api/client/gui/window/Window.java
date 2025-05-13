// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.window;

import java.util.function.Predicate;
import java.util.Collection;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.activity.Activity;
import java.util.function.Consumer;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.lss.variable.LssVariableHolder;
import net.labymod.api.client.gui.screen.ParentScreen;

public interface Window extends ParentScreen, LssVariableHolder
{
    Bounds absoluteBounds();
    
    int getRawWidth();
    
    int getRawHeight();
    
    int getScaledWidth();
    
    int getAbsoluteScaledWidth();
    
    int getScaledHeight();
    
    int getAbsoluteScaledHeight();
    
    boolean isFocused();
    
    default float getPercentageScaledWidth() {
        return this.getScaledWidth() / 100.0f;
    }
    
    default float getPercentageScaledHeight() {
        return this.getScaledHeight() / 100.0f;
    }
    
    void transform(final Parent p0, final Stack p1, final Rectangle p2, final Runnable p3);
    
    float getScale();
    
    long getPointer();
    
    Object getMostInnerScreen(final Object p0) throws TooManyIterationsException;
    
    @Deprecated
    default void getActivityTree(final Consumer<Activity> consumer) {
        Laby.references().activityController().getActivityTree(consumer);
    }
    
    @Deprecated
    default Collection<Activity> getOpenActivities() {
        return Laby.references().activityController().deprecated$getOpenActivities();
    }
    
    @Deprecated
    default Activity findOpenActivity(final Predicate<Activity> filter) {
        return Laby.references().activityController().findOpenActivity(filter);
    }
}
