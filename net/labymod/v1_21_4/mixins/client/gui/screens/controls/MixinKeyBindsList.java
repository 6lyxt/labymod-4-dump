// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins.client.gui.screens.controls;

import javax.annotation.Nullable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.Iterator;
import java.util.List;
import net.labymod.v1_21_4.client.LabyModKeyMapping;
import net.labymod.api.configuration.settings.creator.hotkey.Hotkey;
import net.labymod.api.configuration.settings.creator.hotkey.HotkeyHolder;
import net.labymod.api.client.options.MinecraftInputMapping;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fxw.class })
public abstract class MixinKeyBindsList extends fpa<fxw.b>
{
    public MixinKeyBindsList(final flk $$0, final int $$1, final int $$2, final int $$3, final int $$4) {
        super($$0, $$1, $$2, $$3, $$4);
    }
    
    @Redirect(method = { "<init>" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;keyMappings:[Lnet/minecraft/client/KeyMapping;"))
    private fli[] labyMod$addCustomKeybindings(final flo instance) {
        final List<fli> keyMappings = new ArrayList<fli>(Arrays.asList(instance.V));
        keyMappings.removeIf(mapping -> ((MinecraftInputMapping)mapping).isHidden());
        for (final Hotkey hotkey : HotkeyHolder.getInstance().getHotkeys()) {
            keyMappings.add(LabyModKeyMapping.create(hotkey));
        }
        return keyMappings.toArray(new fli[0]);
    }
}
