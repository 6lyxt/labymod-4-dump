// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.session;

import net.labymod.api.client.session.Session;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface UserFactory
{
    Object createUser(final Session p0);
}
