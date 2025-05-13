// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.session.user;

import net.labymod.api.client.session.Session;
import javax.inject.Inject;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.session.UserFactory;

@Singleton
@Implements(UserFactory.class)
public class VersionedUserFactory implements UserFactory
{
    @Inject
    public VersionedUserFactory() {
    }
    
    @Override
    public Object createUser(final Session session) {
        Session.Type type = session.getType();
        if (type == Session.Type.MICROSOFT) {
            type = Session.Type.MOJANG;
        }
        return new dkm(session.getUsername(), session.getUniqueId().toString(), session.getAccessToken(), type.toString());
    }
}
