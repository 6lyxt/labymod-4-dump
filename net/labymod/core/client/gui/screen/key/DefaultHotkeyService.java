// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.key;

import net.labymod.core.addon.DefaultAddonService;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;
import net.labymod.api.event.Subscribe;
import java.util.Iterator;
import net.labymod.api.event.client.input.KeyEvent;
import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.key.Key;
import java.util.function.Supplier;
import java.util.function.BooleanSupplier;
import net.labymod.api.Laby;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.key.HotkeyService;

@Singleton
@Implements(HotkeyService.class)
public class DefaultHotkeyService implements HotkeyService
{
    private final Map<String, Hotkey> hotkeys;
    
    public DefaultHotkeyService() {
        this.hotkeys = new HashMap<String, Hotkey>();
        Laby.labyAPI().eventBus().registerListener(this);
    }
    
    @Override
    public void register(final String identifier, final BooleanSupplier condition, final Supplier<Key> keySupplier, final Supplier<Type> typeSupplier, final Consumer<Boolean> active) {
        if (this.hotkeys.containsKey(identifier)) {
            throw new IllegalArgumentException("Hotkey with identifier " + identifier + " already exists");
        }
        this.hotkeys.put(identifier, new Hotkey(keySupplier, condition, typeSupplier, active));
    }
    
    @Override
    public boolean unregister(final String identifier) {
        return this.hotkeys.remove(identifier) != null;
    }
    
    @Subscribe
    public void onKey(final KeyEvent event) {
        if (event.isCancelled() || event.state() == KeyEvent.State.HOLDING || !Laby.labyAPI().minecraft().isMouseLocked()) {
            return;
        }
        final Key key = event.key();
        for (final Hotkey hotkey : this.hotkeys.values()) {
            final Key hotkeyKey = hotkey.keySupplier.get();
            if (key == hotkeyKey && this.handle(hotkey, event.state() == KeyEvent.State.PRESS)) {
                break;
            }
        }
    }
    
    @Subscribe
    public void onScreenDisplay(final ScreenDisplayEvent event) {
        final ScreenInstance screen = event.getScreen();
        final ScreenInstance previousScreen = event.getPreviousScreen();
        if (screen == null || previousScreen != null) {
            return;
        }
        for (final Hotkey hotkey : this.hotkeys.values()) {
            if (hotkey.active) {
                if (hotkey.typeSupplier.get() != Type.HOLD) {
                    continue;
                }
                hotkey.active = false;
                hotkey.activeConsumer.accept(false);
            }
        }
    }
    
    private boolean handle(final Hotkey hotkey, final boolean pressed) {
        if (!hotkey.condition.getAsBoolean()) {
            return false;
        }
        if (hotkey.namespace != null && !DefaultAddonService.getInstance().isEnabled(hotkey.namespace)) {
            return false;
        }
        final Type type = hotkey.typeSupplier.get();
        if (type == Type.TOGGLE) {
            if (pressed) {
                hotkey.active = !hotkey.active;
                hotkey.activeConsumer.accept(hotkey.active);
                return true;
            }
            return false;
        }
        else {
            if (hotkey.active == pressed) {
                return false;
            }
            hotkey.active = pressed;
            hotkey.activeConsumer.accept(hotkey.active);
            return true;
        }
    }
    
    public static class Hotkey
    {
        private final Supplier<Key> keySupplier;
        private final BooleanSupplier condition;
        private final Supplier<Type> typeSupplier;
        private final Consumer<Boolean> activeConsumer;
        private final String namespace;
        private boolean active;
        
        public Hotkey(final Supplier<Key> keySupplier, final BooleanSupplier condition, final Supplier<Type> typeSupplier, final Consumer<Boolean> activeConsumer) {
            this.keySupplier = keySupplier;
            this.condition = condition;
            this.typeSupplier = typeSupplier;
            this.activeConsumer = activeConsumer;
            final String namespace = Laby.labyAPI().getNamespace(activeConsumer);
            this.namespace = (namespace.equals("labymod") ? null : namespace);
        }
    }
}
