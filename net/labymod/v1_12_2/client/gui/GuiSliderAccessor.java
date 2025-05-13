// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.gui;

import net.labymod.api.client.gui.mouse.MutableMouse;

public interface GuiSliderAccessor
{
    boolean isDragging();
    
    float getMinValue();
    
    float getMaxValue();
    
    float getValue();
    
    float getStep();
    
    void labymod$mouseDragged(final bib p0, final MutableMouse p1);
}
