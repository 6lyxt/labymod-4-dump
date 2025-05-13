// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.client.network.chat;

import java.util.List;
import net.labymod.api.client.component.Component;

public interface MutableComponentAccessor
{
    VersionedBaseComponent<? extends Component, ?> getLabyComponent();
    
    List<Component> getChildren();
    
    void setContents(final xq p0);
}
