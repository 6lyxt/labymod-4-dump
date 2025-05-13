// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.input;

import net.labymod.api.client.options.MinecraftInputMapping;

public final class KeyBindingHelper
{
    public static void unpressKeybindings() {
        final bid settings = bib.z().t;
        for (final bhy keyBinding : settings.as) {
            ((MinecraftInputMapping)keyBinding).unpress();
        }
    }
}
