// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labymodnet.data;

import java.util.Map;
import net.labymod.core.labymodnet.models.ChangeResponse;
import java.util.function.Consumer;

public interface RequestContent
{
    CosmeticRequestType getType();
    
    Consumer<ChangeResponse> getChangeResponseConsumer();
    
    void fill(final Map<String, String> p0);
}
