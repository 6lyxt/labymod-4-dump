// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.forwarder.priority;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.PropertyValueAccessorForwarder;

public abstract class AbstractPriorityForwarder extends PropertyValueAccessorForwarder
{
    private final String[] keys;
    
    public AbstractPriorityForwarder() {
        this.keys = this.getKeys();
    }
    
    abstract String[] getKeys();
    
    @Override
    public boolean requiresForwarding(final Widget widget, final String key) {
        for (final String priorityKey : this.keys) {
            if (key.equals(priorityKey)) {
                return true;
            }
        }
        return false;
    }
}
