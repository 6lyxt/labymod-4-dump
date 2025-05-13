// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.gui;

import net.labymod.api.client.gui.mouse.MutableMouse;

public interface GuiSliderAccessor
{
    boolean isDragging();
    
    float getMinValue();
    
    float getMaxValue();
    
    float getValue();
    
    float getStep();
    
    void labymod$mouseDragged(final ave p0, final MutableMouse p1);
}
