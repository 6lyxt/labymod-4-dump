// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.gui.screens.controls;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.Iterator;
import java.util.List;
import net.labymod.v1_8_9.client.LabyModKeyMapping;
import net.labymod.api.configuration.settings.creator.hotkey.Hotkey;
import net.labymod.api.configuration.settings.creator.hotkey.HotkeyHolder;
import net.labymod.api.client.options.MinecraftInputMapping;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ayi.class })
public abstract class MixinKeyBindsList
{
    @Redirect(method = { "<init>" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;keyBindings:[Lnet/minecraft/client/settings/KeyBinding;"))
    private avb[] labyMod$addCustomKeybindings(final avh instance) {
        final List<avb> keyMappings = new ArrayList<avb>(Arrays.asList(instance.ax));
        keyMappings.removeIf(mapping -> ((MinecraftInputMapping)mapping).isHidden());
        for (final Hotkey hotkey : HotkeyHolder.getInstance().getHotkeys()) {
            keyMappings.add(LabyModKeyMapping.create(hotkey));
        }
        return keyMappings.toArray(new avb[0]);
    }
}
