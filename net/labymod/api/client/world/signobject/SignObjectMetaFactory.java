// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.signobject;

import net.labymod.api.client.world.signobject.template.SignObjectTemplate;
import net.labymod.api.client.world.signobject.template.SignObjectMeta;

public interface SignObjectMetaFactory<M extends SignObjectMeta<T>, T extends SignObjectTemplate>
{
    M newMeta(final T p0, final String[] p1);
}
