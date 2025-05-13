// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.other.window;

import net.labymod.api.client.Minecraft;
import net.labymod.api.Laby;
import net.labymod.api.property.Property;
import net.labymod.api.util.function.ChangeListener;

public class CleanWindowTitleChangeListener implements ChangeListener<Property<Boolean>, Boolean>
{
    @Override
    public void changed(final Property<Boolean> type, final Boolean oldValue, final Boolean newValue) {
        final Minecraft minecraft = Laby.labyAPI().minecraft();
        if (minecraft == null) {
            return;
        }
        minecraft.updateWindowTitle();
    }
}
