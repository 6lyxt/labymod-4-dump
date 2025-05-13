// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.gui.screens.controls;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.Iterator;
import java.util.List;
import net.labymod.v1_18_2.client.LabyModKeyMapping;
import net.labymod.api.configuration.settings.creator.hotkey.Hotkey;
import net.labymod.api.configuration.settings.creator.hotkey.HotkeyHolder;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ eep.class })
public class MixinKeyBindsScreen
{
    @Redirect(method = { "render" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;keyMappings:[Lnet/minecraft/client/KeyMapping;"))
    private dyo[] labyMod$checkKeys(final dyv instance) {
        final List<dyo> mappings = new ArrayList<dyo>(Arrays.asList(instance.aT));
        for (final Hotkey hotkey : HotkeyHolder.getInstance().getHotkeys()) {
            mappings.add(LabyModKeyMapping.create(hotkey));
        }
        return mappings.toArray(new dyo[0]);
    }
    
    @Redirect(method = { "lambda$init$0" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;keyMappings:[Lnet/minecraft/client/KeyMapping;"))
    private dyo[] labyMod$resetKeys(final dyv instance) {
        final List<dyo> mappings = new ArrayList<dyo>(Arrays.asList(instance.aT));
        for (final Hotkey hotkey : HotkeyHolder.getInstance().getHotkeys()) {
            mappings.add(LabyModKeyMapping.create(hotkey));
        }
        return mappings.toArray(new dyo[0]);
    }
}
