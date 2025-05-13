// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins.client.gui.screens.controls;

import javax.annotation.Nullable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.Iterator;
import java.util.List;
import net.labymod.v1_20_5.client.LabyModKeyMapping;
import net.labymod.api.configuration.settings.creator.hotkey.Hotkey;
import net.labymod.api.configuration.settings.creator.hotkey.HotkeyHolder;
import net.labymod.api.client.options.MinecraftInputMapping;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fnw.class })
public abstract class MixinKeyBindsList extends fhl<fnw.b>
{
    public MixinKeyBindsList(final ffg $$0, final int $$1, final int $$2, final int $$3, final int $$4) {
        super($$0, $$1, $$2, $$3, $$4);
    }
    
    @Redirect(method = { "<init>" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;keyMappings:[Lnet/minecraft/client/KeyMapping;"))
    private ffe[] labyMod$addCustomKeybindings(final ffk instance) {
        final List<ffe> keyMappings = new ArrayList<ffe>(Arrays.asList(instance.W));
        keyMappings.removeIf(mapping -> ((MinecraftInputMapping)mapping).isHidden());
        for (final Hotkey hotkey : HotkeyHolder.getInstance().getHotkeys()) {
            keyMappings.add(LabyModKeyMapping.create(hotkey));
        }
        return keyMappings.toArray(new ffe[0]);
    }
}
