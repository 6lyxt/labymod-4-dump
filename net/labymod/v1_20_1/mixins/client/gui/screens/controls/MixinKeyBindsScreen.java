// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.client.gui.screens.controls;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.Iterator;
import java.util.List;
import net.labymod.v1_20_1.client.LabyModKeyMapping;
import net.labymod.api.configuration.settings.creator.hotkey.Hotkey;
import net.labymod.api.configuration.settings.creator.hotkey.HotkeyHolder;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ evk.class })
public class MixinKeyBindsScreen
{
    @Redirect(method = { "render" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;keyMappings:[Lnet/minecraft/client/KeyMapping;"))
    private enl[] labyMod$checkKeys(final enr instance) {
        final List<enl> mappings = new ArrayList<enl>(Arrays.asList(instance.X));
        for (final Hotkey hotkey : HotkeyHolder.getInstance().getHotkeys()) {
            mappings.add(LabyModKeyMapping.create(hotkey));
        }
        return mappings.toArray(new enl[0]);
    }
    
    @Redirect(method = { "lambda$init$0" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;keyMappings:[Lnet/minecraft/client/KeyMapping;"))
    private enl[] labyMod$resetKeys(final enr instance) {
        final List<enl> mappings = new ArrayList<enl>(Arrays.asList(instance.X));
        for (final Hotkey hotkey : HotkeyHolder.getInstance().getHotkeys()) {
            mappings.add(LabyModKeyMapping.create(hotkey));
        }
        return mappings.toArray(new enl[0]);
    }
}
