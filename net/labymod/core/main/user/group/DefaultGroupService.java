// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.group;

import net.labymod.api.user.GameUserService;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.api.user.GameUser;
import net.labymod.api.Laby;
import java.util.Collection;
import java.util.Iterator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.Constants;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import java.util.HashMap;
import com.google.gson.JsonElement;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.user.group.Group;
import java.util.Map;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.user.group.GroupService;
import net.labymod.api.service.Service;

@Singleton
@Implements(GroupService.class)
public class DefaultGroupService extends Service implements GroupService
{
    private final Map<Integer, Group> groups;
    private final Request<JsonElement> elementRequest;
    
    public DefaultGroupService() {
        this.groups = new HashMap<Integer, Group>();
        this.elementRequest = Request.ofGson(JsonElement.class).url(Constants.LegacyUrls.GROUPS, new Object[0]).async(false);
    }
    
    @Override
    protected void prepare() {
        this.registerDefaultGroups();
        final Response<JsonElement> response = this.elementRequest.executeSync();
        if (response.hasException()) {
            DefaultGroupService.LOGGER.error("Failed to load groups", response.exception());
            return;
        }
        final JsonElement element = response.get();
        if (!element.isJsonObject()) {
            DefaultGroupService.LOGGER.error("Failed to load groups. The response does not contain a valid JSON object (" + String.valueOf(element), new Object[0]);
            return;
        }
        final JsonObject groupContainer = element.getAsJsonObject();
        final JsonArray groups = groupContainer.getAsJsonArray("groups");
        for (final JsonElement groupElement : groups) {
            final Group group = (Group)GsonUtil.DEFAULT_GSON.fromJson(groupElement, (Class)Group.class);
            group.initialize();
            this.registerGroup(group);
        }
    }
    
    @Override
    public void onServiceCompleted() {
        this.refreshUserGroups();
    }
    
    @Override
    public void onServiceError(final Exception exception) {
        this.refreshUserGroups();
        super.onServiceError(exception);
    }
    
    @Override
    public void onServiceUnload() {
        this.groups.clear();
    }
    
    @Override
    public Group getGroup(final int id) {
        return this.groups.getOrDefault(id, DefaultGroups.DEFAULT_GROUP);
    }
    
    @Override
    public Collection<Group> getGroups() {
        return this.groups.values();
    }
    
    private void registerDefaultGroups() {
        this.registerGroup(DefaultGroups.LEGACY_GROUP);
        this.registerGroup(DefaultGroups.DEFAULT_GROUP);
    }
    
    private void registerGroup(final Group group) {
        this.groups.put(group.getIdentifier(), group);
    }
    
    private void refreshUserGroups() {
        final GameUserService gameUserService = Laby.references().gameUserService();
        for (final GameUser user : gameUserService.getGameUsers().values()) {
            if (user instanceof final DefaultGameUser defaultGameUser) {
                defaultGameUser.groupHolder().refresh();
            }
        }
    }
}
