// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.gui;

import java.util.function.Consumer;

public interface GuiTextFieldAccessor
{
    void setWidth(final int p0);
    
    void setHeight(final int p0);
    
    int getHeight();
    
    boolean isEnabled();
    
    void setTextConsumer(final Consumer<String> p0);
}
