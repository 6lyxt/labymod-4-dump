// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style.modifier;

import net.labymod.api.client.gui.screen.widget.Widget;

public interface Forwarder
{
    boolean requiresForwarding(final Widget p0, final String p1);
    
    void forward(final Widget p0, final String p1, final ProcessedObject p2) throws Exception;
    
    default void forwardArray(final Widget widget, final String key, final ProcessedObject... objects) throws Exception {
    }
    
    default void forward(final Widget widget, final String key, final ProcessedObject... objects) throws Exception {
        if (objects == null) {
            this.forward(widget, key, (ProcessedObject)null);
            return;
        }
        if (objects.length == 1) {
            this.forward(widget, key, objects[0]);
            return;
        }
        this.forwardArray(widget, key, objects);
    }
    
    default void reset(final Widget widget, final String key) {
    }
    
    Class<?> getType(final Widget p0, final String p1, final String p2);
    
    int getPriority();
}
