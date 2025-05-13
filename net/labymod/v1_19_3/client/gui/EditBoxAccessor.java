// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.gui;

import java.util.function.Consumer;

public interface EditBoxAccessor
{
    void setValueConsumer(final Consumer<String> p0);
    
    boolean isEditable();
    
    ss getHint();
}
