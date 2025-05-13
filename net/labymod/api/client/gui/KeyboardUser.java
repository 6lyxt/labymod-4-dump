// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui;

import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;

public interface KeyboardUser
{
    boolean keyPressed(final Key p0, final InputType p1);
    
    boolean keyReleased(final Key p0, final InputType p1);
    
    boolean charTyped(final Key p0, final char p1);
}
