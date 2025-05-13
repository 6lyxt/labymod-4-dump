// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user;

import net.labymod.api.util.io.web.exception.WebRequestException;
import net.labymod.api.Laby;
import net.labymod.api.user.GameUser;
import net.labymod.api.util.logging.Logging;
import com.google.gson.JsonElement;
import net.labymod.api.util.io.web.WebResponse;

final class GameUserDataResponse implements WebResponse<JsonElement>
{
    private final Logging logging;
    private final GameUser gameUser;
    
    GameUserDataResponse(final GameUser gameUser) {
        this.gameUser = gameUser;
        this.logging = Laby.references().loggingFactory().create(GameUserDataResponse.class);
    }
    
    @Override
    public void success(final JsonElement result) {
        if (!(this.gameUser instanceof DefaultGameUser)) {
            return;
        }
        ((DefaultGameUser)this.gameUser).updateUserData(result);
    }
    
    @Override
    public void failed(final WebRequestException exception) {
        this.logging.error("Response code for {} is {}", this.gameUser.getUniqueId(), exception.getResponseCode());
        if (!(this.gameUser instanceof DefaultGameUser)) {
            return;
        }
        ((DefaultGameUser)this.gameUser).userDataFailed(exception);
    }
}
