// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.gui.screens.controls;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.Iterator;
import java.util.List;
import net.labymod.v1_12_2.client.LabyModKeyMapping;
import net.labymod.api.configuration.settings.creator.hotkey.Hotkey;
import net.labymod.api.configuration.settings.creator.hotkey.HotkeyHolder;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bme.class })
public class MixinKeyBindsScreen
{
    @Redirect(method = { "drawScreen" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;keyBindings:[Lnet/minecraft/client/settings/KeyBinding;"))
    private bhy[] labyMod$checkKeys(final bid instance) {
        final List<bhy> mappings = new ArrayList<bhy>(Arrays.asList(instance.as));
        for (final Hotkey hotkey : HotkeyHolder.getInstance().getHotkeys()) {
            mappings.add(LabyModKeyMapping.create(hotkey));
        }
        return mappings.toArray(new bhy[0]);
    }
    
    @Redirect(method = { "actionPerformed" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;keyBindings:[Lnet/minecraft/client/settings/KeyBinding;"))
    private bhy[] labyMod$resetKeys(final bid instance) {
        final List<bhy> mappings = new ArrayList<bhy>(Arrays.asList(instance.as));
        for (final Hotkey hotkey : HotkeyHolder.getInstance().getHotkeys()) {
            mappings.add(LabyModKeyMapping.create(hotkey));
        }
        return mappings.toArray(new bhy[0]);
    }
}
