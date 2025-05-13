// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labymodnet;

import java.lang.reflect.Type;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.core.main.user.shop.item.AbstractItem;
import net.labymod.core.main.user.shop.item.ItemService;
import java.util.Arrays;
import net.labymod.core.main.user.DefaultGameUserService;
import net.labymod.core.main.user.shop.item.texture.listener.ItemTextureListener;
import net.labymod.core.main.user.shop.item.metadata.util.ItemMetadataUtil;
import java.util.HashMap;
import net.labymod.core.labymodnet.data.EmoteOrderChangeRequestContent;
import net.labymod.core.labymodnet.models.Emote;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.labymod.core.labymodnet.data.SingleCosmeticRequestContent;
import net.labymod.core.labymodnet.data.RequestContent;
import net.labymod.core.labymodnet.models.Cosmetic;
import java.util.Iterator;
import java.util.Map;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.util.ThreadSafe;
import net.labymod.core.labymodnet.event.LabyModNetRefreshEvent;
import net.labymod.core.labymodnet.models.CosmeticOption;
import net.labymod.core.labymodnet.models.CosmeticOptionEntry;
import com.google.gson.JsonElement;
import net.labymod.api.util.GsonUtil;
import java.util.List;
import com.google.gson.JsonArray;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import net.labymod.api.util.io.web.request.AbstractRequest;
import net.labymod.api.user.GameUser;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.event.Phase;
import net.labymod.api.event.labymod.user.UserUpdateDataEvent;
import net.labymod.api.labyconnect.TokenStorage;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectTokenEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.Laby;
import net.labymod.api.labyconnect.protocol.LabyConnectState;
import net.labymod.api.event.labymod.labyconnect.LabyConnectStateUpdateEvent;
import javax.inject.Inject;
import net.labymod.core.labymodnet.models.ChangeResponse;
import java.util.function.Consumer;
import net.labymod.core.labymodnet.data.CosmeticRequestType;
import java.util.concurrent.Executors;
import net.labymod.core.labymodnet.models.CosmeticOptions;
import net.labymod.core.labymodnet.models.UserItems;
import java.util.UUID;
import net.labymod.core.labymodnet.data.MultipleCosmeticRequestContent;
import net.labymod.core.labymodnet.widgetoptions.WidgetOptionService;
import net.labymod.api.client.session.SessionAccessor;
import net.labymod.api.labyconnect.LabyConnect;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.ExecutorService;
import com.google.gson.reflect.TypeToken;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.labymodnet.widgetoptions.OptionProvider;

@Singleton
@Implements(LabyModNetService.class)
public class DefaultLabyModNetService implements LabyModNetService, OptionProvider
{
    private static final TypeToken<?> COSMETIC_OPTION_ENTRIES_TOKEN;
    private final ExecutorService executor;
    private final AtomicBoolean busy;
    private final LabyConnect labyConnect;
    private final SessionAccessor sessionAccessor;
    private final WidgetOptionService widgetOptionService;
    private final MultipleCosmeticRequestContent switchCosmeticRequestContent;
    private boolean hasError;
    private UUID userUniqueId;
    private UserItems userItems;
    private CosmeticOptions cosmeticOptions;
    private long timeLastRequest;
    private boolean lastRequestFailed;
    
    @Inject
    public DefaultLabyModNetService(final LabyConnect labyConnect, final SessionAccessor sessionAccessor) {
        this.executor = Executors.newSingleThreadExecutor();
        this.busy = new AtomicBoolean(false);
        this.switchCosmeticRequestContent = new MultipleCosmeticRequestContent(CosmeticRequestType.SWITCH, null);
        this.hasError = false;
        this.timeLastRequest = 0L;
        this.lastRequestFailed = false;
        this.labyConnect = labyConnect;
        this.sessionAccessor = sessionAccessor;
        this.widgetOptionService = new WidgetOptionService(this);
        this.initializeAsync();
    }
    
    @Subscribe
    public void onLabyConnectStateUpdate(final LabyConnectStateUpdateEvent event) {
        if (event.state() != LabyConnectState.PLAY) {
            return;
        }
        this.initializeAsync();
        Laby.references().textureRepository().invalidateRemoteTextures((url, location) -> (url.startsWith("labyproxy://") || url.startsWith("labyproxys://")) && !location.hasResult());
    }
    
    @Subscribe
    public void onLabyConnectToken(final LabyConnectTokenEvent event) {
        if (event.purpose() != TokenStorage.Purpose.CLIENT) {
            return;
        }
        this.initializeAsync();
    }
    
    @Subscribe
    public void onUserUpdateData(final UserUpdateDataEvent event) {
        if (event.phase() != Phase.PRE) {
            return;
        }
        final GameUser gameUser = event.gameUser();
        if (!gameUser.getUniqueId().equals(this.userUniqueId)) {
            return;
        }
        final long timePassedSinceLastRequest = TimeUtil.getMillis() - this.timeLastRequest;
        if (timePassedSinceLastRequest < 3000L && !this.lastRequestFailed) {
            event.setCancelled(true);
        }
    }
    
    public UUID getUserUniqueId() {
        return this.userUniqueId;
    }
    
    public WidgetOptionService widgetOptionService() {
        return this.widgetOptionService;
    }
    
    private void initializeAsync() {
        this.executor.execute(this::initialize);
    }
    
    private void initialize() {
        this.userItems = null;
        this.cosmeticOptions = null;
        final LabyConnectSession session = this.labyConnect.getSession();
        if (session == null) {
            return;
        }
        final UUID uniqueId = session.self().getUniqueId();
        final TokenStorage tokenStorage = session.tokenStorage();
        if (!tokenStorage.hasValidToken(TokenStorage.Purpose.CLIENT, uniqueId)) {
            return;
        }
        this.busy.set(true);
        this.userUniqueId = uniqueId;
        final String token = tokenStorage.getToken(TokenStorage.Purpose.CLIENT, uniqueId).getToken();
        final Response<UserItems> resultItems = Request.ofGson(UserItems.class).url("https://www.labymod.net/api/user-items", new Object[0]).authorization("Client", token).executeSync();
        if (resultItems.hasException()) {
            resultItems.exception().printStackTrace();
            this.userItems = new UserItems();
            this.hasError = true;
            this.busy.set(false);
            return;
        }
        final Response<JsonArray> resultOptions = Request.ofGson(JsonArray.class).url("https://www.labymod.net/api/user-cosmetic-options", new Object[0]).authorization("Client", token).executeSync();
        if (resultOptions.hasException()) {
            resultOptions.exception().printStackTrace();
            this.cosmeticOptions = new CosmeticOptions();
            this.hasError = true;
            this.busy.set(false);
            return;
        }
        synchronized (this) {
            final List<CosmeticOptionEntry> list = (List<CosmeticOptionEntry>)GsonUtil.DEFAULT_GSON.fromJson((JsonElement)resultOptions.get(), DefaultLabyModNetService.COSMETIC_OPTION_ENTRIES_TOKEN.getType());
            final CosmeticOptions cosmeticOptions = new CosmeticOptions();
            final Map<String, CosmeticOption> map = cosmeticOptions.getMap();
            for (final CosmeticOptionEntry option : list) {
                map.computeIfAbsent(option.getCustomKey(), k -> new CosmeticOption()).push(option);
            }
            this.userItems = resultItems.get();
            this.setDefaultCosmeticData(this.userItems.getCosmetics());
            this.cosmeticOptions = cosmeticOptions;
            this.hasError = false;
            this.busy.set(false);
            ThreadSafe.executeOnRenderThread(() -> Laby.fireEvent(new LabyModNetRefreshEvent()));
        }
    }
    
    @Override
    public void toggleCosmetic(final Cosmetic cosmetic, final boolean enabled, final Consumer<ChangeResponse> callback) {
        final MultipleCosmeticRequestContent data = new MultipleCosmeticRequestContent(CosmeticRequestType.SWITCH, callback);
        data.getModel().add(new MultipleCosmeticRequestContent.BulkModel.BulkEntry(cosmetic, enabled, callback));
        this.makeRequest(data);
    }
    
    @Override
    public void updateCosmetic(final Cosmetic cosmetic, final Consumer<ChangeResponse> callback) {
        this.makeRequest(new SingleCosmeticRequestContent(cosmetic, String.join(",", (CharSequence[])cosmetic.getData()), CosmeticRequestType.MULTI, callback));
    }
    
    @Override
    public void updateEmotes(final Consumer<ChangeResponse> callback) {
        final Int2IntOpenHashMap order = new Int2IntOpenHashMap();
        for (final Emote emote : this.userItems.getEmotes()) {
            order.put(emote.getId(), emote.getOrder());
        }
        this.makeRequest(new EmoteOrderChangeRequestContent(order, callback));
    }
    
    private void makeRequest(final RequestContent data) {
        final Consumer<ChangeResponse> callback = data.getChangeResponseConsumer();
        final LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session == null) {
            callback.accept(null);
            return;
        }
        final UUID uniqueId = session.self().getUniqueId();
        final TokenStorage tokenStorage = session.tokenStorage();
        if (!tokenStorage.hasValidToken(TokenStorage.Purpose.CLIENT, uniqueId)) {
            callback.accept(null);
            return;
        }
        final String token = tokenStorage.getToken(TokenStorage.Purpose.CLIENT, uniqueId).getToken();
        this.makeRequest(data, token);
    }
    
    private void makeRequest(final RequestContent data, final String token) {
        final Map<String, String> body = new HashMap<String, String>();
        data.fill(body);
        final GsonRequest<ChangeResponse> request = Request.ofGson(ChangeResponse.class);
        if (data.getType() == CosmeticRequestType.SWITCH) {
            request.url("https://www.labymod.net/api/change/cosmetics", new Object[0]).method(Request.Method.PATCH).write((Object)data.toString());
        }
        else {
            request.url("https://www.labymod.net/api/change", new Object[0]).method(Request.Method.POST).body((Map)body);
        }
        this.timeLastRequest = TimeUtil.getMillis();
        this.lastRequestFailed = false;
        request.authorization("Client", token).async().execute(result -> {
            if (result.hasException()) {
                this.lastRequestFailed = true;
                result.exception().printStackTrace();
                this.consumeCallback(data, null);
            }
            else {
                result.ifPresent(response -> {
                    if (response.isDone()) {
                        this.lastRequestFailed = false;
                        if (data instanceof final SingleCosmeticRequestContent singleCosmeticRequestContent) {
                            final Cosmetic cosmetic = singleCosmeticRequestContent.getCosmetic();
                            this.updateCosmetic(data, cosmetic);
                        }
                        if (data instanceof final MultipleCosmeticRequestContent multipleCosmeticRequestContent) {
                            final MultipleCosmeticRequestContent.BulkModel model = multipleCosmeticRequestContent.getModel();
                            model.getItems().iterator();
                            final Iterator iterator;
                            while (iterator.hasNext()) {
                                final MultipleCosmeticRequestContent.BulkModel.BulkEntry item = iterator.next();
                                this.updateCosmetic(data, item.getCosmetic());
                            }
                        }
                    }
                    else {
                        this.lastRequestFailed = true;
                    }
                    return;
                });
                this.consumeCallback(data, (ChangeResponse)result.get());
            }
        });
    }
    
    private void invalidateMultipleCosmeticRequestContent(final RequestContent content) {
        if (content instanceof final MultipleCosmeticRequestContent multipleContent) {
            final MultipleCosmeticRequestContent.BulkModel model = multipleContent.getModel();
            model.invalidate();
        }
    }
    
    private void consumeCallback(final RequestContent content, final ChangeResponse response) {
        if (content instanceof final MultipleCosmeticRequestContent multipleCosmeticRequestContent) {
            final MultipleCosmeticRequestContent.BulkModel model = multipleCosmeticRequestContent.getModel();
            for (final MultipleCosmeticRequestContent.BulkModel.BulkEntry item : model.getItems()) {
                final Consumer<ChangeResponse> consumer = item.getChangeResponseConsumer();
                consumer.accept(response);
            }
            this.invalidateMultipleCosmeticRequestContent(content);
            return;
        }
        final Consumer<ChangeResponse> consumer2 = content.getChangeResponseConsumer();
        consumer2.accept(response);
    }
    
    private void updateCosmetic(final RequestContent data, final Cosmetic dataCosmetic) {
        if (this.userItems == null) {
            return;
        }
        for (final Cosmetic cosmetic : this.userItems.getCosmetics()) {
            if (cosmetic.getId() != dataCosmetic.getId()) {
                continue;
            }
            switch (data.getType()) {
                case SWITCH: {
                    cosmetic.setEnabled(dataCosmetic.isEnabled());
                    continue;
                }
                case MULTI: {
                    cosmetic.setData(dataCosmetic.getData());
                    continue;
                }
            }
        }
        ItemMetadataUtil.updateGameUser(this.userUniqueId, dataCosmetic, null);
    }
    
    @Override
    public UserItems getUserItems() {
        return this.userItems;
    }
    
    @Override
    public CosmeticOptions getCosmeticOptions() {
        return this.cosmeticOptions;
    }
    
    @Override
    public void reload() {
        this.initializeAsync();
    }
    
    @Override
    public State getState() {
        final LabyConnectSession session = this.labyConnect.getSession();
        if (!this.sessionAccessor.isPremium()) {
            return State.NOT_PREMIUM;
        }
        if (!this.labyConnect.isConnected() || session == null) {
            return State.NOT_CONNECTED;
        }
        if (this.busy.get()) {
            return State.LOADING;
        }
        if (this.userItems == null || this.cosmeticOptions == null || this.hasError) {
            return State.ERROR;
        }
        if (!session.self().getUniqueId().equals(this.userUniqueId)) {
            return State.ACCOUNT_CHANGED;
        }
        return State.OK;
    }
    
    private void setDefaultCosmeticData(final List<Cosmetic> cosmetics) {
        final ItemService itemService = ((DefaultGameUserService)Laby.references().gameUserService()).itemService();
        for (final Cosmetic cosmetic : cosmetics) {
            final AbstractItem item = itemService.getItemById(cosmetic.getItemId());
            if (item == null) {
                continue;
            }
            final String[] defaultData = item.itemDetails().getDefaultData();
            if (defaultData == null) {
                continue;
            }
            cosmetic.setDefaultData(Arrays.copyOf(defaultData, defaultData.length));
        }
    }
    
    static {
        COSMETIC_OPTION_ENTRIES_TOKEN = TypeToken.getParameterized((Type)List.class, new Type[] { CosmeticOptionEntry.class });
    }
}
