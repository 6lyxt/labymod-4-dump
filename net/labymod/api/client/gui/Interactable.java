// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui;

import net.labymod.api.client.gui.screen.Parent;

public interface Interactable extends ScreenUser, MouseUser, KeyboardUser
{
    void tick();
    
    void initialize(final Parent p0);
}
