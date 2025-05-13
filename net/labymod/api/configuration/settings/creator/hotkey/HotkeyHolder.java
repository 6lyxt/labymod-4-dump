// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.creator.hotkey;

import java.util.Collections;
import net.labymod.api.util.CollectionHelper;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public final class HotkeyHolder
{
    private static HotkeyHolder instance;
    private final List<Hotkey> keybindings;
    
    private HotkeyHolder() {
        this.keybindings = new ArrayList<Hotkey>();
    }
    
    public static HotkeyHolder getInstance() {
        if (HotkeyHolder.instance == null) {
            HotkeyHolder.instance = new HotkeyHolder();
        }
        return HotkeyHolder.instance;
    }
    
    public void registerHotkey(final Hotkey keybinding) {
        this.keybindings.add(keybinding);
    }
    
    public Collection<Hotkey> getHotkeys() {
        return Collections.unmodifiableCollection((Collection<? extends Hotkey>)CollectionHelper.filter((Collection<? extends T>)this.keybindings, hotkey -> hotkey.visibility().getAsBoolean()));
    }
}
