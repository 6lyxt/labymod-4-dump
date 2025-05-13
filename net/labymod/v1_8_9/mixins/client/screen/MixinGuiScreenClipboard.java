// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.screen;

import org.spongepowered.asm.mixin.Overwrite;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ axu.class })
public class MixinGuiScreenClipboard
{
    @Overwrite
    public static String o() {
        return Laby.labyAPI().minecraft().getClipboard();
    }
    
    @Overwrite
    public static void e(final String value) {
        Laby.labyAPI().minecraft().setClipboard(value);
    }
}
