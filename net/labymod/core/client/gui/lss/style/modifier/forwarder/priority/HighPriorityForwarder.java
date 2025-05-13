// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.forwarder.priority;

public class HighPriorityForwarder extends AbstractPriorityForwarder
{
    @Override
    String[] getKeys() {
        return new String[] { "marginLeft", "marginTop", "marginRight", "marginBottom", "paddingLeft", "paddingTop", "paddingRight", "paddingBottom" };
    }
    
    @Override
    public int getPriority() {
        return 1;
    }
}
