// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.imgui.window;

import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.Laby;
import java.util.Iterator;
import java.util.Objects;
import java.util.Locale;
import net.labymod.api.client.gui.screen.VanillaScreen;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.key.KeyHandler;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.debug.DebugRegistry;
import java.util.ArrayList;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.activity.Activity;
import java.util.List;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class DocumentHandler
{
    private static final int VANILLA_ACTIVITY = -1;
    private static final int DEFAULT_ACTIVITY = 0;
    private final List<Activity> activities;
    private int selectedActivity;
    private Widget selectedWidget;
    private Widget targetWidget;
    
    public DocumentHandler() {
        this.activities = new ArrayList<Activity>();
    }
    
    public void collectActivity(final Activity activity) {
        if (!DebugRegistry.DEBUG_WINDOWS.isEnabled()) {
            this.activities.clear();
            return;
        }
        if (!this.activities.contains(activity)) {
            this.activities.add(activity);
        }
    }
    
    public void onKey(final KeyEvent event) {
        if (event.state() != KeyEvent.State.PRESS) {
            return;
        }
        final Key key = event.key();
        if (key == Key.TAB) {
            if (KeyHandler.isShiftDown()) {
                --this.selectedActivity;
                if (this.selectedActivity < -1) {
                    this.selectedActivity = -1;
                }
            }
            else {
                ++this.selectedActivity;
            }
        }
    }
    
    public void resetSelectedActivity() {
        this.selectedActivity = 0;
    }
    
    public void clear() {
        this.activities.clear();
    }
    
    @Nullable
    public Widget getSelectedWidget() {
        return this.selectedWidget;
    }
    
    public void setSelectedWidget(final Widget selectedWidget) {
        this.selectedWidget = selectedWidget;
    }
    
    public boolean isSelectedWidget(final Widget other) {
        return this.selectedWidget == other;
    }
    
    public Widget getTargetWidget() {
        return this.targetWidget;
    }
    
    public void setTargetWidget(final Widget targetWidget) {
        this.targetWidget = targetWidget;
    }
    
    public List<Activity> getActivities() {
        return this.activities;
    }
    
    public String getCurrentActivityName() {
        final Object selectedActivity = this.getSelectedActivity();
        String name = null;
        if (selectedActivity instanceof final Activity activity) {
            name = activity.getName();
        }
        else if (selectedActivity instanceof final VanillaScreen vanillaScreen) {
            name = vanillaScreen.getClass().getSimpleName();
        }
        return (name == null) ? "No Activity available" : String.format(Locale.ROOT, "%s (%s/%s)", name, this.selectedActivity + 1, this.activities.size());
    }
    
    public void setSelectedActivity(final String name) {
        for (final Activity activity : this.activities) {
            if (Objects.equals(activity.getName(), name)) {
                this.setSelectedActivity(activity);
                break;
            }
        }
    }
    
    public void setSelectedActivity(final Activity newSelectedActivity) {
        this.selectedActivity = this.activities.indexOf(newSelectedActivity);
    }
    
    public Object getSelectedActivity() {
        if (this.selectedActivity == -1) {
            final ScreenWrapper currentScreen = Laby.labyAPI().minecraft().minecraftWindow().currentScreen();
            if (currentScreen != null) {
                final ScreenInstance screenInstance = currentScreen.mostInnerScreenInstance();
                if (screenInstance instanceof final ScreenWrapper screenWrapper) {
                    if (screenWrapper.getVersionedScreen() instanceof VanillaScreen) {
                        return screenWrapper.getVersionedScreen();
                    }
                }
            }
            this.selectedActivity = 0;
        }
        return (this.selectedActivity > this.activities.size() - 1) ? null : this.activities.get(this.selectedActivity);
    }
}
