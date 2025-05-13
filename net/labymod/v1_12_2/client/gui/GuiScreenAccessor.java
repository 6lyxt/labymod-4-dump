// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.gui;

import net.labymod.api.client.gui.screen.VanillaScreen;

public interface GuiScreenAccessor extends VanillaScreen
{
    void setMinecraft(final bib p0);
    
    boolean isHandleMouseInput();
    
    void setHandleMouseInput(final boolean p0);
    
    void resize(final int p0, final int p1);
}
