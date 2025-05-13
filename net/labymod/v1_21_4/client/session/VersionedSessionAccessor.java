// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.session;

import net.labymod.core.client.session.DefaultSession;
import net.labymod.core.client.session.SessionUtil;
import net.labymod.api.client.session.Session;
import java.util.UUID;
import javax.inject.Inject;
import net.labymod.core.client.session.UserFactory;
import net.labymod.api.client.session.SessionAccessor;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.session.DefaultAbstractSessionAccessor;

@Singleton
@Implements(SessionAccessor.class)
public class VersionedSessionAccessor extends DefaultAbstractSessionAccessor<flw>
{
    private final UserFactory userFactory;
    
    @Inject
    public VersionedSessionAccessor(final UserFactory userFactory) {
        this.userFactory = userFactory;
    }
    
    @Override
    public Session createSession(final String username, final UUID uuid, final String accessToken) {
        return new DefaultSession(username, uuid, accessToken, SessionUtil.isPremium(accessToken) ? Session.Type.MICROSOFT : Session.Type.LEGACY);
    }
    
    @Override
    public flw toGameSession(final Session session) {
        return (flw)this.userFactory.createUser(session);
    }
    
    @Override
    public Session fromGameSession(final flw gameSession) {
        if (gameSession == null) {
            return null;
        }
        final UUID newUuidObject = UUID.fromString(gameSession.b().toString());
        return this.createSession(gameSession.c(), newUuidObject, gameSession.d());
    }
    
    @Override
    public flw getGameSession() {
        return flk.Q().X();
    }
}
