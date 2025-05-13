// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.lagdetector;

import net.labymod.api.notification.NotificationController;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.Component;
import net.labymod.api.notification.Notification;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.core.client.gui.screen.activity.activities.debug.MemoryUpgradeActivity;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.loader.platform.PlatformEnvironment;
import java.util.Map;
import net.labymod.api.Laby;

public class GarbageCollectorLagDetectionModule extends LagDetectionModule
{
    public static final String OUT_OF_MEMORY_TITLE_KEY = "labymod.notification.outOfMemoryWarning.title";
    private static final String OUT_OF_MEMORY_DESCRIPTION_KEY = "labymod.notification.outOfMemoryWarning.description";
    private static final long TRIGGER_FRAME_DURATION = 100L;
    private static final long TRIGGER_SCORE = 3L;
    private long maxMemoryUsed;
    private long lastMemoryMax;
    private long lastFrameMemory;
    private long lastFrameTimestamp;
    private boolean outOfMemoryDetected;
    private int frameHits;
    private boolean hasOutOfMemoryProperty;
    
    public GarbageCollectorLagDetectionModule() {
        super("Garbage Collector");
        this.lastFrameMemory = -1L;
        this.lastFrameTimestamp = -1L;
        final ConfigProperty<Map<String, Boolean>> warningsProperty = Laby.labyAPI().config().other().outOfMemoryWarnings();
        final Map<String, Boolean> warnings = warningsProperty.get();
        this.hasOutOfMemoryProperty = warnings.containsKey(PlatformEnvironment.getRunningVersion());
    }
    
    @Override
    public void onUpdate() {
        if (this.hasOutOfMemoryProperty) {
            return;
        }
        final Runtime runtime = Runtime.getRuntime();
        final long maxMemory = runtime.maxMemory();
        final long totalMemory = runtime.totalMemory();
        final long freeMemory = runtime.freeMemory();
        final long usedMemory = totalMemory - freeMemory;
        this.maxMemoryUsed = Math.max(usedMemory, (this.lastMemoryMax == maxMemory) ? this.maxMemoryUsed : usedMemory);
        this.lastMemoryMax = maxMemory;
        final long percent = this.maxMemoryUsed * 100L / maxMemory;
        if (!this.outOfMemoryDetected && this.lastFrameMemory != -1L) {
            final long frameDuration = TimeUtil.getCurrentTimeMillis() - this.lastFrameTimestamp;
            if (percent >= 99L) {
                this.gameIsOutOfMemory();
                return;
            }
            if (this.lastFrameMemory > usedMemory) {
                this.garbageCollector(frameDuration);
            }
            else if (frameDuration > 100L) {
                this.decreaseFrameHits();
            }
        }
        this.captureFrame(percent >= 90L, usedMemory);
    }
    
    @Override
    protected void onKey(final Key key, final KeyEvent.State state) {
        if (this.hasOutOfMemoryProperty || state != KeyEvent.State.PRESS) {
            return;
        }
        if (!this.outOfMemoryDetected) {
            return;
        }
        if (key == Key.M) {
            Laby.labyAPI().minecraft().minecraftWindow().displayScreen(new MemoryUpgradeActivity());
            this.saveConfig(true);
        }
        if (key == Key.K) {
            this.saveConfig(false);
        }
    }
    
    private void saveConfig(final boolean state) {
        final ConfigProperty<Map<String, Boolean>> warningsProperty = Laby.labyAPI().config().other().outOfMemoryWarnings();
        final Map<String, Boolean> warnings = warningsProperty.get();
        warnings.put(PlatformEnvironment.getRunningVersion(), state);
        this.hasOutOfMemoryProperty = true;
        this.outOfMemoryDetected = false;
        LabyMod.getInstance().getLabyConfig().save();
    }
    
    private void captureFrame(final boolean capture, final long usedMemory) {
        if (!capture) {
            this.lastFrameMemory = -1L;
            return;
        }
        this.lastFrameMemory = usedMemory;
        this.lastFrameTimestamp = TimeUtil.getCurrentTimeMillis();
    }
    
    private void garbageCollector(final long frameDuration) {
        if (frameDuration <= 100L) {
            this.decreaseFrameHits();
            return;
        }
        ++this.frameHits;
        if (this.frameHits < 3L) {
            return;
        }
        this.gameIsOutOfMemory();
    }
    
    private void gameIsOutOfMemory() {
        this.outOfMemoryDetected = true;
        System.gc();
        final NotificationController controller = Laby.references().notificationController();
        controller.push(Notification.builder().title(Component.translatable("labymod.notification.outOfMemoryWarning.title", new Component[0])).text(Component.translatable("labymod.notification.outOfMemoryWarning.description", Component.text("M", NamedTextColor.YELLOW), Component.text("K", NamedTextColor.GRAY))).type(Notification.Type.SYSTEM).duration(10000L).build());
    }
    
    private void decreaseFrameHits() {
        if (this.frameHits <= 0) {
            return;
        }
        --this.frameHits;
    }
}
