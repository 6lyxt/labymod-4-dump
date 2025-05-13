// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.session;

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
public class VersionedSessionAccessor extends DefaultAbstractSessionAccessor<ejv>
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
    public ejv toGameSession(final Session session) {
        return (ejv)this.userFactory.createUser(session);
    }
    
    @Override
    public Session fromGameSession(final ejv gameSession) {
        return (gameSession == null) ? null : this.createSession(gameSession.c(), gameSession.h().getId(), gameSession.d());
    }
    
    @Override
    public ejv getGameSession() {
        return ejf.N().U();
    }
}
