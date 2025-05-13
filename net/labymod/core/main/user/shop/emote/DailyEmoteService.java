// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.emote;

import java.util.Iterator;
import com.google.gson.JsonArray;
import net.labymod.api.util.io.web.request.Response;
import javax.inject.Inject;
import net.labymod.api.Constants;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import com.google.gson.JsonElement;
import net.labymod.api.util.io.web.request.Request;
import it.unimi.dsi.fastutil.ints.IntList;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;
import net.labymod.api.service.Service;

@Singleton
@Referenceable
public class DailyEmoteService extends Service
{
    private final IntList dailyEmotes;
    private final Request<JsonElement> dailyEmotesRequest;
    
    @Inject
    public DailyEmoteService() {
        this.dailyEmotes = (IntList)new IntArrayList();
        this.dailyEmotesRequest = Request.ofGson(JsonElement.class).url(Constants.LegacyUrls.DAILY_EMOTES, new Object[0]);
    }
    
    @Override
    protected void prepare() {
        final Response<JsonElement> response = this.dailyEmotesRequest.executeSync();
        if (response.isEmpty()) {
            return;
        }
        if (response.hasException()) {
            response.exception().printStackTrace();
            return;
        }
        final JsonElement result = response.get();
        final JsonArray emotes = result.getAsJsonObject().getAsJsonArray("dailyEmotes");
        for (final JsonElement entry : emotes) {
            final int id = entry.getAsJsonObject().get("id").getAsInt();
            this.dailyEmotes.add(id);
        }
    }
    
    @Override
    public void onServiceUnload() {
        this.dailyEmotes.clear();
    }
    
    public IntList getDailyEmotes() {
        return this.dailyEmotes;
    }
}
