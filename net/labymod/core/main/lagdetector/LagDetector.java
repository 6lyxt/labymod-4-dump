// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.lagdetector;

import java.util.ArrayList;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;
import java.util.Iterator;
import net.labymod.api.Laby;
import java.util.List;
import net.labymod.api.util.logging.Logging;

public final class LagDetector
{
    private static final Logging LOGGER;
    private static final List<LagDetectionModule> MODULES;
    private static LagDetector instance;
    private static boolean initialized;
    
    private LagDetector() {
    }
    
    private static void addModule(final LagDetectionModule module) {
        LagDetector.LOGGER.info("Register \"{}\" lag detection module", module.getName());
        LagDetector.MODULES.add(module);
    }
    
    private static void initialize() {
        if (LagDetector.initialized) {
            return;
        }
        LagDetector.instance = new LagDetector();
        Laby.references().eventBus().registerListener(LagDetector.instance);
        LagDetector.initialized = true;
        addModule(new GarbageCollectorLagDetectionModule());
    }
    
    public static void onUpdate() {
        initialize();
        for (final LagDetectionModule module : LagDetector.MODULES) {
            module.onUpdate();
        }
    }
    
    @Subscribe
    public void onKey(final KeyEvent event) {
        for (final LagDetectionModule module : LagDetector.MODULES) {
            module.onKey(event.key(), event.state());
        }
    }
    
    static {
        LOGGER = Logging.create(LagDetector.class, () -> Laby.labyAPI().labyModLoader().isLabyModDevelopmentEnvironment());
        MODULES = new ArrayList<LagDetectionModule>();
    }
}
