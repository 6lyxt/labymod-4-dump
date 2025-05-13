// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity.overlay;

import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.activity.overlay.initializer.EmptyScreenInitializer;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.activity.overlay.initializer.InstanceScreenInitializer;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.service.Registry;

@Referenceable
public interface OverlayRegistry extends Registry<RegisteredReplacement>
{
    default void register(final String id, final Class<?> clazz, final InstanceScreenInitializer initializer) {
        this.register(id, new RegisteredReplacement(clazz, initializer));
    }
    
    default void register(final GameScreen screen, final Class<?> clazz, final InstanceScreenInitializer initializer) {
        this.register(screen.getId(), clazz, initializer);
    }
    
    default void register(final NamedScreen screen, final Class<?> clazz, final InstanceScreenInitializer initializer) {
        this.register((GameScreen)screen, clazz, initializer);
    }
    
    default void register(final String id, final Class<?> clazz, final EmptyScreenInitializer initializer) {
        this.register(id, clazz, parentScreen -> initializer.create());
    }
    
    default void register(final GameScreen screen, final Class<?> clazz, final EmptyScreenInitializer initializer) {
        this.register(screen.getId(), clazz, initializer);
    }
    
    default void register(final NamedScreen screen, final Class<?> clazz, final EmptyScreenInitializer initializer) {
        this.register((GameScreen)screen, clazz, initializer);
    }
    
    Activity toOverlay(final ScreenInstance p0);
    
    Object toRawScreen(final Activity p0);
    
    default RegisteredReplacement get(final GameScreen screen) {
        return this.getById(screen.getId());
    }
    
    default RegisteredReplacement get(final NamedScreen screen) {
        return this.get((GameScreen)screen);
    }
}
