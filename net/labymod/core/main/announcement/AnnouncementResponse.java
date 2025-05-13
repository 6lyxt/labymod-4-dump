// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.announcement;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import java.awt.Color;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import java.util.Iterator;
import net.labymod.api.util.io.web.exception.WebRequestException;
import com.google.gson.JsonSyntaxException;
import net.labymod.api.util.collection.Lists;
import net.labymod.core.main.announcement.model.SideBarAnnouncement;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.labymod.api.util.io.web.WebResponse;

public class AnnouncementResponse implements WebResponse<JsonElement>
{
    private static final Gson GSON;
    private final DefaultAnnouncementService service;
    private final List<SideBarAnnouncement> announcements;
    
    public AnnouncementResponse(final DefaultAnnouncementService service) {
        this.service = service;
        this.announcements = (List<SideBarAnnouncement>)Lists.newArrayList();
    }
    
    @Override
    public void success(final JsonElement result) throws WebRequestException {
        if (!result.isJsonArray()) {
            throw new WebRequestException((Exception)new JsonSyntaxException("The Announcement Result isn't a JsonArray"));
        }
        if (!this.announcements.isEmpty()) {
            this.announcements.clear();
        }
        for (final JsonElement jsonElement : result.getAsJsonArray()) {
            this.announcements.add((SideBarAnnouncement)AnnouncementResponse.GSON.fromJson(jsonElement, (Class)SideBarAnnouncement.class));
        }
    }
    
    @Override
    public void failed(final WebRequestException exception) {
    }
    
    public List<SideBarAnnouncement> getAnnouncements() {
        return this.announcements;
    }
    
    static {
        GSON = new GsonBuilder().registerTypeAdapter((Type)Color.class, (Object)((json, typeOfT, context) -> Color.decode(json.getAsString()))).create();
    }
}
