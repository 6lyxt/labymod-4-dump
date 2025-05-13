// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.session.message;

import net.labymod.api.user.GameUser;
import java.util.Iterator;
import com.google.gson.JsonObject;
import net.labymod.api.util.CountryCode;
import net.labymod.core.main.user.DefaultGameUser;
import java.util.UUID;
import java.util.Map;
import com.google.gson.JsonElement;
import net.labymod.api.user.GameUserService;

public class LanguageFlagsMessageListener implements MessageListener
{
    private final GameUserService gameUserService;
    
    public LanguageFlagsMessageListener(final GameUserService gameUserService) {
        this.gameUserService = gameUserService;
    }
    
    @Override
    public void listen(final String message) {
        final JsonElement element = (JsonElement)LanguageFlagsMessageListener.GSON.fromJson(message, (Class)JsonElement.class);
        if (element.isJsonObject()) {
            final JsonObject object = element.getAsJsonObject();
            for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
                UUID uuid;
                try {
                    uuid = UUID.fromString(entry.getKey());
                }
                catch (final IllegalArgumentException exception) {
                    continue;
                }
                final JsonElement value = entry.getValue();
                if (value == null) {
                    continue;
                }
                final String language = value.getAsString();
                final GameUser gameUser = this.gameUserService.gameUser(uuid);
                if (!(gameUser instanceof DefaultGameUser)) {
                    continue;
                }
                final CountryCode countryCode = CountryCode.fromCode(language);
                ((DefaultGameUser)gameUser).setCountryCode(countryCode);
            }
        }
    }
}
