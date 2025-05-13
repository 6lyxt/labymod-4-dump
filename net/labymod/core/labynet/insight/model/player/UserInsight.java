// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labynet.insight.model.player;

import com.google.gson.JsonElement;
import net.labymod.core.main.user.GameUserData;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.api.Laby;
import net.labymod.core.main.user.DefaultGameUserService;
import net.labymod.api.client.entity.player.Player;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import net.labymod.core.labynet.insight.Insight;

public class UserInsight implements Insight
{
    private boolean loaded;
    private JsonArray cosmetics;
    
    public UserInsight(final JsonObject object) {
        this.fromJsonObject(object);
    }
    
    public UserInsight(final Player player) {
        final DefaultGameUserService service = (DefaultGameUserService)Laby.labyAPI().gameUserService();
        final DefaultGameUser user = (DefaultGameUser)service.gameUser(player.getUniqueId());
        final GameUserData userData = user.getUserData();
        if (userData != null) {
            try {
                final JsonObject data = service.getUserGson().toJsonTree((Object)userData).getAsJsonObject();
                this.cosmetics = data.getAsJsonArray("c");
                this.loaded = true;
            }
            catch (final Exception e) {
                Logging.create("UserMeta").error("Can't parse cosmetics of " + player.getUniqueId().toString(), (Throwable)e);
            }
        }
    }
    
    public boolean isLoaded() {
        return this.loaded;
    }
    
    @Override
    public JsonObject toJsonObject() {
        final JsonObject user = new JsonObject();
        user.add("cosmetics", (JsonElement)this.cosmetics);
        return user;
    }
    
    @Override
    public void fromJsonObject(final JsonObject object) {
        this.cosmetics = ((object.isJsonNull() || object.get("cosmetics").isJsonNull()) ? new JsonArray() : object.getAsJsonArray("cosmetics"));
    }
}
