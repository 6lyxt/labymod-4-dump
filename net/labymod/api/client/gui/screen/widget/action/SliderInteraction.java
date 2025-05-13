// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.action;

@FunctionalInterface
public interface SliderInteraction
{
    public static final SliderInteraction NOOP = value -> {};
    
    void updateValue(final float p0);
}
