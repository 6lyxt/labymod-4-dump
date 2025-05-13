// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.client.session.user;

import java.util.Optional;
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
    public eoc createUser(final Session session) {
        return new eoc(session.getUsername(), session.getUniqueId().toString(), session.getAccessToken(), (Optional)Optional.empty(), (Optional)Optional.empty(), session.isPremium() ? eoc.a.c : eoc.a.a);
    }
}
