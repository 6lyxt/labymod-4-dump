// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.core.main.LabyMod;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gui.screen.VanillaLabyScreen;

public class ModernMouseDraggedHandler implements VanillaLabyScreen.MouseDraggedHandler
{
    @Override
    public FloatVector2 getDeltaMousePosition(final Mouse mouse) {
        final MutableMouse previousMouse = LabyMod.references().mouseBridge().previousMouse();
        return new FloatVector2((float)(mouse.getX() - previousMouse.getX()), (float)(mouse.getY() - previousMouse.getY()));
    }
}
