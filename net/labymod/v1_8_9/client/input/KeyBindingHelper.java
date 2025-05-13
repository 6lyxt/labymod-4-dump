// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.input;

import net.labymod.api.client.options.MinecraftInputMapping;

public final class KeyBindingHelper
{
    public static void unpressKeybindings() {
        final avh settings = ave.A().t;
        for (final avb keyBinding : settings.ax) {
            ((MinecraftInputMapping)keyBinding).unpress();
        }
    }
}
