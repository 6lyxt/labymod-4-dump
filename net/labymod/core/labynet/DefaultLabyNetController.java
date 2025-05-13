// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labynet;

import net.labymod.api.util.io.web.request.types.GsonRequest;
import net.labymod.api.util.io.web.request.Callback;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.notification.NotificationController;
import net.labymod.api.client.component.Component;
import net.labymod.api.notification.Notification;
import net.labymod.core.labynet.model.SurveyVoteResult;
import net.labymod.api.util.io.web.request.types.TypeTokenGsonRequest;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.labynet.models.textures.Skin;
import java.util.ArrayList;
import net.labymod.api.labynet.models.textures.TextureResult;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.TokenStorage;
import net.labymod.api.Laby;
import net.labymod.api.labynet.models.service.ServiceStatus;
import net.labymod.api.labynet.models.service.ServiceDataType;
import com.google.gson.JsonArray;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.util.collection.Lists;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.io.web.request.Request;
import com.google.gson.JsonElement;
import net.labymod.api.util.io.web.request.AbstractRequest;
import java.util.Iterator;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.api.util.io.LabyExecutors;
import net.labymod.api.labynet.models.NameHistory;
import net.labymod.api.util.io.web.result.ResultCallback;
import java.util.Locale;
import net.labymod.api.client.network.server.ServerAddress;
import java.util.Optional;
import net.labymod.api.event.client.network.server.NetworkDisconnectEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.NetworkLoginEvent;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.HashMap;
import net.labymod.core.util.jsonfilecache.DefaultJsonFileCacheFactory;
import net.labymod.api.Constants;
import net.labymod.api.event.EventBus;
import net.labymod.api.labynet.models.ServerGroup;
import net.labymod.core.labynet.model.Survey;
import net.labymod.core.labynet.model.NameHistoryCache;
import java.util.Set;
import net.labymod.api.labynet.models.SocialMediaEntry;
import java.util.List;
import java.util.UUID;
import net.labymod.api.labynet.models.ServerManifest;
import java.util.Map;
import com.google.gson.JsonObject;
import net.labymod.api.util.JsonFileCache;
import java.text.SimpleDateFormat;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.labynet.LabyNetController;

@Singleton
@Implements(LabyNetController.class)
public class DefaultLabyNetController implements LabyNetController
{
    public boolean showSurvey;
    private static final SimpleDateFormat DATE_FORMAT;
    private final JsonFileCache<JsonObject> indexFileCache;
    private final Map<String, ServerManifest> manifestCaches;
    private final Map<UUID, List<SocialMediaEntry>> socialMediaCaches;
    private final Set<NameHistoryCache> nameHistoryCache;
    private final Map<UUID, List<Survey>> surveyCache;
    private final ServerGroupRegistry serverGroupRegistry;
    private ServerGroup currentServerGroup;
    
    @Inject
    public DefaultLabyNetController(final EventBus eventBus) {
        this.showSurvey = true;
        this.serverGroupRegistry = new ServerGroupRegistry();
        this.indexFileCache = DefaultJsonFileCacheFactory.createJsonFileCache(Constants.Files.LABY_NET_INDEX, Constants.LegacyUrls.SERVER_GROUPS, "server_groups", JsonObject.class).readLastModifiedDateFromHeader("last-modified", DefaultLabyNetController.DATE_FORMAT);
        this.manifestCaches = new HashMap<String, ServerManifest>();
        this.nameHistoryCache = new HashSet<NameHistoryCache>();
        this.socialMediaCaches = new HashMap<UUID, List<SocialMediaEntry>>();
        this.surveyCache = new HashMap<UUID, List<Survey>>();
        eventBus.registerListener(this);
        eventBus.registerListener(new ServerChatDataHandler(this));
    }
    
    @Subscribe(Byte.MIN_VALUE)
    public void initializeCurrentServerGroup(final NetworkLoginEvent event) {
        this.currentServerGroup = this.getServerByIp(event.serverData().address()).orElse(null);
    }
    
    @Subscribe
    public void clearCurrentServerGroup(final NetworkDisconnectEvent event) {
        this.currentServerGroup = null;
    }
    
    @Override
    public void loadServerData() {
        this.indexFileCache.read(false, result -> {
            if (result.isPresent()) {
                final ServerGroupRegistry.ServerGroupIndex deserialize = this.indexFileCache.deserialize(ServerGroupRegistry.ServerGroupIndex.class);
                if (deserialize != null) {
                    this.serverGroupRegistry.initialize(deserialize);
                }
            }
        });
    }
    
    @Override
    public Optional<ServerGroup> getCurrentServer() {
        return Optional.ofNullable(this.currentServerGroup);
    }
    
    @Override
    public Optional<ServerGroup> getServerByName(final String name) {
        return Optional.ofNullable(this.serverGroupRegistry.getServerByName(name));
    }
    
    @Override
    public Optional<ServerGroup> getServerByIp(final String ip) {
        return Optional.ofNullable(this.serverGroupRegistry.getServerByIp(ip));
    }
    
    @Override
    public Optional<ServerGroup> getServerByIp(final ServerAddress serverAddress) {
        return this.getServerByIp(serverAddress.getHost().toLowerCase(Locale.ROOT));
    }
    
    @Override
    public void getOrLoadManifest(final ServerGroup serverGroup, final ResultCallback<ServerManifest> callback) {
        final ServerManifest manifest = this.manifestCaches.get(serverGroup.getServerName());
        if (manifest != null) {
            callback.acceptRaw(manifest);
            return;
        }
        final Optional<ServerGroup.Attachment> optionalAttachment = serverGroup.getAttachment("manifest.json");
        if (!optionalAttachment.isPresent()) {
            return;
        }
        final JsonFileCache<JsonObject> fileCache = DefaultJsonFileCacheFactory.createJsonFileCache(Constants.Files.LABY_NET_SERVER_GROUPS.resolve(serverGroup.getServerName() + ".json"), optionalAttachment.get().getUrl(), null, JsonObject.class);
        fileCache.readLastModifiedDateFromHeader("last-modified", DefaultLabyNetController.DATE_FORMAT, success -> fileCache.read(false, result -> {
            if (result.isPresent()) {
                final ServerManifest deserialize = fileCache.deserialize(ServerManifest.class);
                if (deserialize != null) {
                    this.manifestCaches.put(serverGroup.getServerName(), deserialize);
                    callback.acceptRaw(deserialize);
                }
            }
            else if (result.hasException()) {
                callback.acceptException(result.exception());
            }
        }));
    }
    
    @Override
    public void loadNameHistory(final UUID uniqueId, final ResultCallback<List<NameHistory>> callback) {
        final NameHistoryCache cachedNameHistory = this.getCachedNameHistory(uniqueId);
        if (cachedNameHistory != null) {
            callback.acceptRaw(cachedNameHistory.getNameChanges());
            return;
        }
        LabyExecutors.executeBackgroundTask(() -> {
            final Result<NameHistoryCache> result = this.loadNameHistory(uniqueId);
            if (result.hasException()) {
                callback.acceptException(result.exception());
            }
            else {
                callback.acceptRaw(result.get().getNameChanges());
            }
        });
    }
    
    @Override
    public void loadNameHistory(final String name, final ResultCallback<List<NameHistory>> callback) {
        final NameHistoryCache cachedNameHistory = this.getCachedNameHistory(name);
        if (cachedNameHistory != null) {
            callback.acceptRaw(cachedNameHistory.getNameChanges());
            return;
        }
        LabyExecutors.executeBackgroundTask(() -> {
            final Result<NameHistoryCache> result = this.loadNameHistory(name);
            if (result.hasException()) {
                callback.acceptException(result.exception());
            }
            else {
                callback.acceptRaw(result.get().getNameChanges());
            }
        });
    }
    
    @Override
    public void loadNameByUniqueId(final UUID uniqueId, final ResultCallback<String> callback) {
        final NameHistoryCache cachedNameHistory = this.getCachedNameHistory(uniqueId);
        if (cachedNameHistory != null) {
            callback.acceptRaw(cachedNameHistory.getName());
            return;
        }
        LabyExecutors.executeBackgroundTask(() -> {
            final Result<NameHistoryCache> result = this.loadNameHistory(uniqueId);
            if (result.hasException()) {
                callback.acceptException(result.exception());
            }
            else {
                callback.acceptRaw(result.get().getName());
            }
        });
    }
    
    @Override
    public Result<String> loadNameByUniqueIdSync(final UUID uniqueId) {
        final NameHistoryCache cachedNameHistory = this.getCachedNameHistory(uniqueId);
        if (cachedNameHistory != null) {
            return Result.of(cachedNameHistory.getName());
        }
        final Result<NameHistoryCache> result = this.loadNameHistory(uniqueId);
        if (result.hasException()) {
            return Result.ofException(result.exception());
        }
        return Result.of(result.get().getName());
    }
    
    @Override
    public void loadUniqueIdByName(final String name, final ResultCallback<UUID> callback) {
        final NameHistoryCache cachedNameHistory = this.getCachedNameHistory(name);
        if (cachedNameHistory != null) {
            callback.acceptRaw(cachedNameHistory.getUuid());
            return;
        }
        LabyExecutors.executeBackgroundTask(() -> {
            final Result<NameHistoryCache> result = this.loadNameHistory(name);
            if (result.hasException()) {
                callback.acceptException(result.exception());
            }
            else {
                callback.acceptRaw(result.get().getUuid());
            }
        });
    }
    
    @Override
    public Result<UUID> loadUniqueIdByNameSync(final String name) {
        final NameHistoryCache cachedNameHistory = this.getCachedNameHistory(name);
        if (cachedNameHistory != null) {
            return Result.of(cachedNameHistory.getUuid());
        }
        final Result<NameHistoryCache> result = this.loadNameHistory(name);
        if (result.hasException()) {
            return Result.ofException(result.exception());
        }
        return Result.of(result.get().getUuid());
    }
    
    private NameHistoryCache getCachedNameHistory(final UUID uuid) {
        NameHistoryCache cache = null;
        for (final NameHistoryCache historyCache : this.nameHistoryCache) {
            if (historyCache.getUuid().equals(uuid)) {
                cache = historyCache;
                break;
            }
        }
        return cache;
    }
    
    private NameHistoryCache getCachedNameHistory(final String name) {
        NameHistoryCache cache = null;
        for (final NameHistoryCache historyCache : this.nameHistoryCache) {
            if (historyCache.getName().equalsIgnoreCase(name)) {
                cache = historyCache;
                break;
            }
        }
        return cache;
    }
    
    private Result<NameHistoryCache> loadNameHistory(final UUID uuid) {
        final Result<NameHistoryCache> result = Result.empty();
        final Response<JsonElement> response = Request.ofGson(JsonElement.class).url("https://laby.net/api/v3/user/%s/names", new Object[] { uuid }).async(false).executeSync();
        this.handleLoadNameHistory(uuid, result, response);
        return result;
    }
    
    private void handleLoadNameHistory(final UUID uuid, final Result<NameHistoryCache> result, final Response<JsonElement> response) {
        if (response.hasException()) {
            result.setException(response.exception());
            return;
        }
        if (response.isEmpty()) {
            result.setException(new IllegalStateException("Invalid response"));
            return;
        }
        final JsonElement element = response.get();
        if (!element.isJsonArray()) {
            result.setException(new IllegalStateException("Invalid response"));
            return;
        }
        final JsonArray entries = element.getAsJsonArray();
        if (entries.isEmpty()) {
            result.setException(new NullPointerException("No user found"));
            return;
        }
        final List<NameHistory> nameChanges = (List<NameHistory>)Lists.newArrayList();
        for (final JsonElement entry : entries) {
            if (!entry.isJsonObject()) {
                result.setException(new IllegalStateException("Invalid response"));
                return;
            }
            nameChanges.add((NameHistory)GsonUtil.DEFAULT_GSON.fromJson(entry, (Class)NameHistory.class));
        }
        final String name = nameChanges.get(nameChanges.size() - 1).getName();
        final NameHistoryCache nameHistoryCache = new NameHistoryCache(name, uuid, nameChanges);
        this.nameHistoryCache.add(nameHistoryCache);
        result.set(nameHistoryCache);
    }
    
    private Result<NameHistoryCache> loadNameHistory(final String name) {
        final Result<NameHistoryCache> result = Result.empty();
        final Response<JsonElement> response = Request.ofGson(JsonElement.class).url("https://laby.net/api/v3/user/%s/profile", new Object[] { name }).async(false).executeSync();
        this.handleLoadNameHistory(name, result, response);
        return result;
    }
    
    private void handleLoadNameHistory(final String name, final Result<NameHistoryCache> result, final Result<JsonElement> response) {
        if (response.hasException()) {
            result.setException(response.exception());
            return;
        }
        if (response.isEmpty()) {
            result.setException(new NullPointerException("Invalid response"));
            return;
        }
        final JsonElement element = response.get();
        if (!element.isJsonObject()) {
            result.setException(new IllegalStateException("Invalid response"));
            return;
        }
        final JsonElement errorElement = element.getAsJsonObject().get("error");
        if (errorElement != null) {
            result.setException(new NullPointerException(errorElement.getAsString()));
            return;
        }
        UUID uniqueId = null;
        final JsonElement uniqueIdElement = element.getAsJsonObject().get("uuid");
        if (uniqueIdElement != null) {
            uniqueId = UUID.fromString(uniqueIdElement.getAsString());
        }
        String userName = null;
        final JsonElement userNameElement = element.getAsJsonObject().get("name");
        if (userNameElement != null) {
            userName = userNameElement.getAsString();
        }
        final List<NameHistory> nameChanges = (List<NameHistory>)Lists.newArrayList();
        final JsonElement historyElement = element.getAsJsonObject().get("name_history");
        if (historyElement != null) {
            final JsonArray historyEntries = historyElement.getAsJsonArray();
            for (final JsonElement entry : historyEntries) {
                if (!entry.isJsonObject()) {
                    result.setException(new IllegalStateException("Invalid response"));
                    return;
                }
                nameChanges.addFirst((NameHistory)GsonUtil.DEFAULT_GSON.fromJson(entry, (Class)NameHistory.class));
            }
        }
        final NameHistoryCache nameHistoryCache = new NameHistoryCache(userName, uniqueId, nameChanges);
        this.nameHistoryCache.add(nameHistoryCache);
        result.set(nameHistoryCache);
    }
    
    @Override
    public void loadServiceData(final ServiceDataType type, final ResultCallback<ServiceStatus> callback) {
        final LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session == null) {
            callback.acceptRaw(ServiceStatus.of(ServiceStatus.Status.NOT_CONNECTED));
            return;
        }
        final TokenStorage.Token token = session.tokenStorage().getToken(TokenStorage.Purpose.CLIENT, session.self().getUniqueId());
        if (token == null || token.isExpired()) {
            callback.acceptRaw(ServiceStatus.of(ServiceStatus.Status.NOT_CONNECTED));
            return;
        }
        Request.ofGson(type.getClazz()).url("https://laby.net/api/v2/service/%s/widget", new Object[] { type.name().toLowerCase(Locale.ROOT) }).authorization("Client", token.getToken()).async().execute(result -> {
            if (result.hasException()) {
                ServiceStatus status = null;
                switch (result.getStatusCode()) {
                    case 401: {
                        status = ServiceStatus.of(ServiceStatus.Status.NOT_CONNECTED);
                        break;
                    }
                    case 400: {
                        status = ServiceStatus.of(ServiceStatus.Status.NOT_LINKED);
                        break;
                    }
                    case 403: {
                        status = ServiceStatus.of(ServiceStatus.Status.NEEDS_RELINK);
                        break;
                    }
                    default: {
                        status = ServiceStatus.of(ServiceStatus.Status.ERROR);
                        break;
                    }
                }
                callback.acceptRaw(status);
            }
            else {
                callback.acceptRaw(result.get());
            }
        });
    }
    
    @Override
    public void loadSocials(final UUID uniqueId, final ResultCallback<List<SocialMediaEntry>> callback) {
        final List<SocialMediaEntry> entries = this.socialMediaCaches.get(uniqueId);
        if (entries != null) {
            callback.acceptRaw(entries);
            return;
        }
        Request.ofGson(JsonArray.class).url("https://laby.net/api/v2/user/%s/socials", new Object[] { uniqueId }).async().execute(result -> {
            if (result.hasException()) {
                callback.acceptException(result.exception());
            }
            else {
                final List<SocialMediaEntry> socialMediaEntries = (List<SocialMediaEntry>)Lists.newArrayList();
                final JsonArray jsonArray = (JsonArray)result.get();
                jsonArray.iterator();
                final Iterator iterator;
                while (iterator.hasNext()) {
                    final JsonElement jsonElement = iterator.next();
                    socialMediaEntries.add((SocialMediaEntry)GsonUtil.DEFAULT_GSON.fromJson(jsonElement, (Class)SocialMediaEntry.class));
                }
                this.socialMediaCaches.put(uniqueId, socialMediaEntries);
                callback.acceptRaw(socialMediaEntries);
            }
        });
    }
    
    public void loadTexturesFromUser(final TextureResult.Type type, final UUID uniqueId, final ResultCallback<TextureResult> callback) {
        Request.ofGson(JsonObject.class).url("https://laby.net/api/user/%s/get-textures", new Object[] { uniqueId }).async().execute(response -> {
            if (response.hasException()) {
                callback.acceptException(response.exception());
            }
            else if (!response.isPresent()) {
                callback.accept(Result.empty());
            }
            else {
                final JsonObject jsonObject = (JsonObject)response.get();
                if (!jsonObject.has(type.name())) {
                    callback.accept(Result.empty());
                }
                else {
                    try {
                        final List<Skin> textures = new ArrayList<Skin>();
                        final JsonArray jsonArray = jsonObject.get(type.name()).getAsJsonArray();
                        jsonArray.iterator();
                        final Iterator iterator;
                        while (iterator.hasNext()) {
                            final JsonElement element = iterator.next();
                            if (!element.isJsonObject()) {
                                continue;
                            }
                            else {
                                final JsonObject textureObject = element.getAsJsonObject();
                                final String imageHash = textureObject.get("image_hash").getAsString();
                                final boolean slim = textureObject.get("slim_skin").getAsBoolean();
                                new Skin(slim ? MinecraftServices.SkinVariant.SLIM : MinecraftServices.SkinVariant.CLASSIC, imageHash);
                                final Skin skin;
                                final Object o;
                                ((List<Skin>)o).add(skin);
                            }
                        }
                        callback.acceptRaw(new TextureResult(textures));
                    }
                    catch (final Exception exception) {
                        callback.acceptException(exception);
                    }
                }
            }
        });
    }
    
    public void getSurveys(final ResultCallback<List<Survey>> callback) {
        final LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session == null) {
            return;
        }
        final List<Survey> cachedSurveys = this.surveyCache.get(session.self().getUniqueId());
        if (cachedSurveys != null) {
            callback.acceptRaw(cachedSurveys);
            return;
        }
        final TokenStorage.Token token = session.tokenStorage().getToken(TokenStorage.Purpose.JWT, session.self().getUniqueId());
        Request.ofGsonList(Survey.class).url("https://laby.net/api/v2/survey?source=CLIENT", new Object[0]).authorization("Bearer", token.getToken()).async().execute(result -> {
            if (result.hasException()) {
                callback.acceptException(result.exception());
            }
            else {
                this.surveyCache.put(session.self().getUniqueId(), (List)result.get());
                callback.acceptRaw(result.get());
            }
        });
    }
    
    public void clearSurveyCache() {
        this.surveyCache.clear();
    }
    
    public void sendSurveyAnswer(final Survey survey, final Integer answerId, final String text, final ResultCallback<SurveyVoteResult> callback) {
        if (survey == null) {
            throw new IllegalArgumentException("Survey must not be null");
        }
        final LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session == null) {
            return;
        }
        final TokenStorage.Token token = session.tokenStorage().getToken(TokenStorage.Purpose.JWT, session.self().getUniqueId());
        final JsonObject jsonObject = new JsonObject();
        if (answerId != null) {
            jsonObject.addProperty("answer_id", (Number)answerId);
        }
        if (text != null) {
            jsonObject.addProperty("text", text);
        }
        Request.ofGson(SurveyVoteResult.class).url("https://laby.net/api/v2/survey/%s", new Object[] { survey.getId() }).authorization("Bearer", token.getToken()).method((answerId != null || text != null) ? Request.Method.PUT : Request.Method.DELETE).json((Object)jsonObject).async().execute(result -> {
            final NotificationController notificationController = Laby.labyAPI().notificationController();
            if (result.hasException()) {
                notificationController.push(Notification.builder().title(Component.translatable("labymod.survey.error.title", new Component[0])).text(Component.translatable("labymod.survey.error.text", new Component[0])).build());
                callback.acceptException(result.exception());
            }
            else {
                notificationController.push(Notification.builder().title(Component.translatable("labymod.survey.success.title", new Component[0])).text(Component.translatable("labymod.survey.success.text", new Component[0])).build());
                callback.acceptRaw(result.get());
            }
        });
    }
    
    public void loadTextures(@NotNull final TextureResult.Type type, @Nullable final String search, @NotNull final TextureResult.Order order, final int offset, final int limit, @NotNull final Callback<TextureResult> response) {
        String url = String.format(Locale.ROOT, "https://laby.net/api/texture/search?type=%s&order=%s", type.name(), order.name().toLowerCase(Locale.ROOT));
        if (limit != 0) {
            url = url + "&limit=" + limit;
        }
        if (offset != 0) {
            url = url + "&offset=" + offset;
        }
        if (search != null && !search.isEmpty()) {
            url = url + "&input=" + search;
        }
        Request.ofGson(TextureResult.class).url(url, new Object[0]).async().execute(response);
    }
    
    static {
        DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
    }
}
