// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.client.session.user;

import java.util.Optional;
import java.util.UUID;
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
    public ffu createUser(final Session session) {
        final UUID newUuidObject = UUID.fromString(session.getUniqueId().toString());
        return new ffu(session.getUsername(), newUuidObject, session.getAccessToken(), (Optional)Optional.empty(), (Optional)Optional.empty(), session.isPremium() ? ffu.a.c : ffu.a.a);
    }
}
