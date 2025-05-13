// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.announcement;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import java.awt.Color;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import java.util.Iterator;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.Constants;
import net.labymod.api.util.io.web.request.Request;
import com.google.gson.JsonElement;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import net.labymod.api.util.io.web.request.AbstractRequest;
import javax.inject.Inject;
import java.util.Collections;
import java.util.ArrayList;
import net.labymod.core.main.announcement.model.SideBarAnnouncement;
import net.labymod.api.notification.announcement.Announcement;
import java.util.List;
import net.labymod.api.LabyAPI;
import com.google.gson.Gson;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.notification.announcement.AnnouncementService;
import net.labymod.api.service.Service;

@Singleton
@Implements(AnnouncementService.class)
public class DefaultAnnouncementService extends Service implements AnnouncementService
{
    private static final Gson GSON;
    private final LabyAPI labyAPI;
    private final List<Announcement> announcements;
    private final List<Announcement> unmodifiableAnnouncements;
    private final List<SideBarAnnouncement> sideBarAnnouncements;
    private Runnable onResoleListener;
    
    @Inject
    public DefaultAnnouncementService(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
        this.announcements = new ArrayList<Announcement>();
        this.unmodifiableAnnouncements = Collections.unmodifiableList((List<? extends Announcement>)this.announcements);
        this.sideBarAnnouncements = new ArrayList<SideBarAnnouncement>();
    }
    
    @Override
    protected void prepare() {
        final Response<JsonElement> response = Request.ofGson(JsonElement.class).url(Constants.Urls.ANNOUNCEMENTS, new Object[0]).async(false).executeSync();
        if (response.hasException()) {
            return;
        }
        if (response.isEmpty()) {
            return;
        }
        final JsonElement element = response.get();
        if (!element.isJsonArray()) {
            return;
        }
        if (!this.announcements.isEmpty()) {
            this.announcements.clear();
        }
        for (final JsonElement entry : element.getAsJsonArray()) {
            this.sideBarAnnouncements.add((SideBarAnnouncement)DefaultAnnouncementService.GSON.fromJson(entry, (Class)SideBarAnnouncement.class));
        }
        if (this.onResoleListener != null) {
            this.onResoleListener.run();
        }
    }
    
    public void setResolveListener(final Runnable resolveListener) {
        this.onResoleListener = resolveListener;
    }
    
    @Override
    public void onServiceCompleted() {
        this.labyAPI.minecraft().executeOnRenderThread(() -> {});
    }
    
    public boolean isLoaded() {
        return !this.sideBarAnnouncements.isEmpty();
    }
    
    public List<SideBarAnnouncement> getSideBarAnnouncements() {
        return this.sideBarAnnouncements;
    }
    
    @Override
    public void register(@NotNull final Announcement announcement) {
        if (this.announcements.contains(announcement)) {
            throw new IllegalArgumentException("Announcement already registered");
        }
        if (this.get(announcement.getId()) != null) {
            throw new IllegalArgumentException("Announcement with id " + announcement.getId() + " already registered");
        }
        this.announcements.add(announcement);
    }
    
    @Override
    public void unregister(@NotNull final Announcement announcement) {
        this.announcements.remove(announcement);
    }
    
    @Override
    public void unregister(@NotNull final String id) {
        final Announcement announcement = this.get(id);
        if (announcement != null) {
            this.announcements.remove(announcement);
        }
    }
    
    @NotNull
    @Override
    public List<Announcement> getAnnouncements() {
        return this.unmodifiableAnnouncements;
    }
    
    @Nullable
    @Override
    public Announcement get(@NotNull final String id) {
        for (final Announcement announcement : this.announcements) {
            if (announcement.getId().equals(id)) {
                return announcement;
            }
        }
        return null;
    }
    
    static {
        GSON = new GsonBuilder().registerTypeAdapter((Type)Color.class, (Object)((json, typeOfT, context) -> Color.decode(json.getAsString()))).create();
    }
}
