// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlayHandler;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlay;
import java.util.Map;
import java.util.Collections;
import net.labymod.api.Laby;
import net.labymod.api.util.ThreadSafe;
import java.util.function.Consumer;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public final class ActivityController
{
    private final List<Activity> openActivities;
    @Deprecated
    private final Collection<Activity> deprecated$OpenActivities;
    private Activity[] bakedOpenActivities;
    
    @Inject
    public ActivityController() {
        this.openActivities = new ArrayList<Activity>();
        this.deprecated$OpenActivities = new ArrayList<Activity>();
        this.bakedOpenActivities = new Activity[0];
    }
    
    public void addOpenActivity(final Activity activity) {
        this.openActivities.add(activity);
        this.bakeOpenActivities();
    }
    
    public void removeOpenActivity(final Activity activity) {
        this.openActivities.remove(activity);
        this.bakeOpenActivities();
    }
    
    public void getActivityTree(final Consumer<Activity> consumer) {
        ThreadSafe.ensureRenderThread();
        final LabyScreen screen = Laby.labyAPI().minecraft().minecraftWindow().currentLabyScreen();
        if (screen instanceof Activity) {
            screen.doScreenAction("getActivityTree", (Map<String, Object>)Collections.singletonMap("consumer", consumer));
        }
        final ScreenOverlayHandler overlayHandler = Laby.labyAPI().screenOverlayHandler();
        if (overlayHandler != null) {
            for (final ScreenOverlay overlay : overlayHandler.overlays()) {
                overlay.doScreenAction("getActivityTree", (Map<String, Object>)Collections.singletonMap("consumer", consumer));
            }
        }
    }
    
    public Activity findOpenActivity(final Predicate<Activity> filter) {
        for (final Activity activity : this.bakedOpenActivities) {
            if (filter.test(activity)) {
                return activity;
            }
        }
        return null;
    }
    
    public boolean isActivityOpen(final Class<? extends Activity> activityClass) {
        Objects.requireNonNull(activityClass);
        return this.findOpenActivity(activityClass::isInstance) != null;
    }
    
    public Activity[] getOpenActivities() {
        return this.bakedOpenActivities;
    }
    
    public void reloadOpenActivities() {
        for (final Activity activity : this.getOpenActivities()) {
            activity.reload();
        }
    }
    
    @Deprecated
    public Collection<Activity> deprecated$getOpenActivities() {
        return this.deprecated$OpenActivities;
    }
    
    private void bakeOpenActivities() {
        this.bakedOpenActivities = this.openActivities.toArray(new Activity[0]);
        this.deprecated$OpenActivities.clear();
        this.deprecated$OpenActivities.addAll(this.openActivities);
    }
}
