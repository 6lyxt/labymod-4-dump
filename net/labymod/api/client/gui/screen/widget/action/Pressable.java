// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.action;

@FunctionalInterface
public interface Pressable
{
    public static final Pressable NOOP = () -> {};
    
    void press();
}
