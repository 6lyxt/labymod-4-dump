// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.signobject.object;

import net.labymod.api.client.world.signobject.template.SignObjectTemplate;
import net.labymod.api.client.world.signobject.template.SignObjectMeta;

public interface SignObjectListener<M extends SignObjectMeta<? extends SignObjectTemplate>>
{
    void onDispose(final SignObject<M> p0);
}
