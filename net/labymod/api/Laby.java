// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api;

import net.labymod.api.reference.ReferenceStorageFinder;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.client.gfx.GFXBridge;
import java.util.function.Consumer;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.ApiStatus;
import net.labymod.api.generated.ReferenceStorage;

public final class Laby
{
    private static boolean initialized;
    private static ReferenceStorage referenceStorage;
    
    @ApiStatus.Internal
    public static void setInitialized() {
        Laby.initialized = true;
    }
    
    public static LabyAPI labyAPI() {
        return references().labyAPI();
    }
    
    public static <T extends Event> void fireEventNextTick(final T event) {
        references().eventBus().fireNextTick(event, null);
    }
    
    public static <T extends Event> void fireEventNextTick(final T event, final Consumer<T> consumer) {
        references().eventBus().fireNextTick(event, consumer);
    }
    
    public static <T extends Event> T fireEvent(final T event) {
        references().eventBus().fire(event);
        return event;
    }
    
    public static GFXBridge gfx() {
        return references().labyAPI().gfxRenderPipeline().gfx();
    }
    
    public static ReferenceStorage references() {
        if (Laby.referenceStorage == null) {
            loadReferenceStorage();
        }
        if (!Laby.initialized) {
            Laby.initialized = true;
            Laby.referenceStorage.labyAPI();
        }
        return Laby.referenceStorage;
    }
    
    private static void loadReferenceStorage() {
        Laby.referenceStorage = (ReferenceStorage)ReferenceStorageFinder.find(PlatformEnvironment.getPlatformClassloader().getPlatformClassloader());
    }
    
    public static boolean isInitialized() {
        return Laby.initialized;
    }
}
