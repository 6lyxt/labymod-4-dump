// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.session;

import net.labymod.core.client.session.DefaultSession;
import net.labymod.core.client.session.SessionUtil;
import net.labymod.api.client.session.Session;
import java.util.UUID;
import javax.inject.Inject;
import net.labymod.api.client.session.SessionAccessor;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.session.DefaultAbstractSessionAccessor;

@Singleton
@Implements(SessionAccessor.class)
public class VersionedSessionAccessor extends DefaultAbstractSessionAccessor<bii>
{
    @Inject
    public VersionedSessionAccessor() {
    }
    
    @Override
    public Session createSession(final String username, final UUID uuid, final String accessToken) {
        return new DefaultSession(username, uuid, accessToken, SessionUtil.isPremium(accessToken) ? Session.Type.MICROSOFT : Session.Type.LEGACY);
    }
    
    @Override
    public bii toGameSession(final Session session) {
        Session.Type type = session.getType();
        if (type == Session.Type.MICROSOFT) {
            type = Session.Type.MOJANG;
        }
        return new bii(session.getUsername(), session.getUniqueId().toString(), session.getAccessToken(), type.toString());
    }
    
    @Override
    public Session fromGameSession(final bii gameSession) {
        return (gameSession == null) ? null : this.createSession(gameSession.c(), gameSession.e().getId(), gameSession.d());
    }
    
    @Override
    public bii getGameSession() {
        return bib.z().K();
    }
}
