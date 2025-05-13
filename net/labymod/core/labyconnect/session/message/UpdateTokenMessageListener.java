// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.session.message;

import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectTokenEvent;
import net.labymod.api.labyconnect.TokenStorage;
import com.google.gson.JsonObject;
import net.labymod.core.labyconnect.session.DefaultTokenStorage;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.util.logging.Logging;

public class UpdateTokenMessageListener implements MessageListener
{
    private static final Logging LOGGER;
    private final LabyConnect labyConnect;
    private final User self;
    private final DefaultTokenStorage tokenStorage;
    
    public UpdateTokenMessageListener(final LabyConnect labyConnect, final User self, final DefaultTokenStorage tokenStorage) {
        this.labyConnect = labyConnect;
        this.self = self;
        this.tokenStorage = tokenStorage;
    }
    
    @Override
    public void listen(final String message) {
        try {
            final JsonObject object = (JsonObject)UpdateTokenMessageListener.GSON.fromJson(message, (Class)JsonObject.class);
            if (object.has("purpose")) {
                final TokenStorage.Purpose purpose = TokenStorage.Purpose.valueOf(object.get("purpose").getAsString());
                final String tokenString = object.get("token").getAsString();
                final long expiresAt = object.get("expires_at").getAsLong();
                final TokenStorage.Token token = new TokenStorage.Token(tokenString, expiresAt);
                final UUID uniqueId = this.self.getUniqueId();
                this.tokenStorage.updateToken(purpose, uniqueId, token);
                Laby.fireEvent(new LabyConnectTokenEvent(this.labyConnect, purpose, uniqueId, token));
            }
        }
        catch (final Exception exception) {
            UpdateTokenMessageListener.LOGGER.error("Failed to parse token", exception);
        }
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
