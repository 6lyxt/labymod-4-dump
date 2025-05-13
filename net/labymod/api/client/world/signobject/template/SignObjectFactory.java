// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.signobject.template;

import net.labymod.api.client.world.signobject.SignObjectPosition;
import net.labymod.api.client.world.signobject.object.SignObject;

public interface SignObjectFactory<O extends SignObject<M>, M extends SignObjectMeta<T>, T extends SignObjectTemplate>
{
    O newObject(final M p0, final SignObjectPosition p1);
}
