// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.flint;

import java.lang.reflect.Type;
import net.labymod.api.util.version.serial.VersionCompatibilityDeserializer;
import net.labymod.api.models.version.VersionCompatibility;
import com.google.gson.GsonBuilder;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.Laby;
import net.labymod.api.util.StringUtil;
import net.labymod.api.util.io.web.request.types.StringRequest;
import net.labymod.api.util.collection.Lists;
import com.google.gson.JsonObject;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.core.platform.launcher.communication.LauncherCommunicationClient;
import net.labymod.core.platform.launcher.communication.LauncherPacket;
import net.labymod.core.platform.launcher.communication.packets.addons.LauncherModLoaderChangePacket;
import net.labymod.api.client.gui.screen.widget.widgets.popup.SimpleAdvancedPopup;
import net.labymod.api.client.component.Component;
import net.labymod.core.platform.launcher.DefaultLauncherService;
import net.labymod.api.client.Minecraft;
import java.util.Objects;
import java.io.IOException;
import net.labymod.core.addon.DefaultAddonService;
import java.util.function.Consumer;
import net.labymod.core.flint.downloader.FlintDownloadTask;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.util.KeyValue;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import java.nio.file.Path;
import net.labymod.api.addon.LoadedAddon;
import java.util.Optional;
import java.util.Collection;
import java.util.Iterator;
import com.google.gson.JsonElement;
import net.labymod.api.util.io.web.request.Request;
import com.google.gson.JsonArray;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import net.labymod.api.util.io.web.request.AbstractRequest;
import javax.inject.Inject;
import net.labymod.core.main.LabyMod;
import java.util.ArrayList;
import java.util.HashMap;
import net.labymod.core.flint.marketplace.FlintPermission;
import net.labymod.core.flint.marketplace.FlintTag;
import net.labymod.core.flint.downloader.FlintDownloader;
import net.labymod.core.flint.index.FlintIndex;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.LabyAPI;
import net.labymod.api.util.io.web.result.ResultCallback;
import java.util.List;
import net.labymod.core.flint.marketplace.FlintModification;
import net.labymod.core.flint.marketplace.FlintOrganization;
import java.util.Map;
import com.google.gson.Gson;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class FlintController
{
    public static final Gson GSON;
    private static final Map<Integer, FlintOrganization> ORGANIZATIONS;
    private static final Map<String, FlintModification> MODIFICATIONS;
    private static final Map<String, List<FlintModification.Review>> REVIEWS;
    private static final Map<String, List<FlintModification.Changelog>> CHANGELOGS;
    private static final Map<String, List<ResultCallback<?>>> CURRENT_QUERIES;
    private final LabyAPI labyAPI;
    private final Logging logging;
    private final FlintIndex flintIndex;
    private final String version;
    private final FlintDownloader flintDownloader;
    private final Map<Integer, FlintTag> tags;
    private final List<FlintPermission> permissions;
    
    @Inject
    public FlintController(final Logging.Factory loggingFactory, final LabyAPI labyAPI) {
        this.tags = new HashMap<Integer, FlintTag>();
        this.permissions = new ArrayList<FlintPermission>();
        this.labyAPI = labyAPI;
        this.logging = loggingFactory.create(FlintController.class);
        this.version = LabyMod.getInstance().labyModLoader().version().toString();
        this.flintIndex = new FlintIndex(this);
        this.flintDownloader = LabyMod.references().flintDownloader();
        this.loadTags();
        this.loadPermissions();
    }
    
    public static String getVariableBrandUrl(final String baseUrl, final int width, final int height) {
        String url = baseUrl;
        if (height != 0) {
            url = url + "?height=" + height;
        }
        if (width != 0) {
            if (height == 0) {
                url = url;
            }
            else {
                url = url;
            }
            url = url + "width=" + width;
        }
        return url;
    }
    
    private void loadPermissions() {
        this.permissions.clear();
        Request.ofGson(JsonArray.class).url("https://flintmc.net/api/client-store/get-permissions", new Object[0]).async().execute(response -> {
            if (!(!response.isPresent())) {
                final JsonArray array = (JsonArray)response.get();
                array.iterator();
                final Iterator iterator;
                while (iterator.hasNext()) {
                    final JsonElement jsonElement = iterator.next();
                    this.permissions.add((FlintPermission)FlintController.GSON.fromJson(jsonElement, (Class)FlintPermission.class));
                }
            }
        });
    }
    
    public FlintPermission getPermission(final String key) {
        for (final FlintPermission permission : this.permissions) {
            if (permission.getKey().equals(key)) {
                return permission;
            }
        }
        final FlintPermission flintPermission = new FlintPermission(key);
        this.permissions.add(flintPermission);
        return flintPermission;
    }
    
    public void setup() {
        this.flintIndex.setupIndex();
    }
    
    public FlintIndex getFlintIndex() {
        return this.flintIndex;
    }
    
    public Collection<FlintTag> getTags() {
        return this.tags.values();
    }
    
    public Optional<FlintTag> getTag(final int id) {
        return Optional.ofNullable(this.tags.isEmpty() ? null : this.tags.get(id));
    }
    
    public boolean isInstalled(final FlintModification modification) {
        for (final LoadedAddon loadedAddon : this.labyAPI.addonService().getLoadedAddons()) {
            final String namespace = loadedAddon.info().getNamespace();
            if (namespace.equalsIgnoreCase(modification.getNamespace()) && !this.flintDownloader.isScheduledForRemoval(namespace)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isInstalled(final Path path) {
        for (final LoadedAddon loadedAddon : this.labyAPI.addonService().getLoadedAddons()) {
            final InstalledAddonInfo info = loadedAddon.info();
            final String fileName = info.getFileName();
            if (fileName == null) {
                continue;
            }
            if (fileName.equals(path.getFileName().toString()) && !this.flintDownloader.isScheduledForRemoval(info.getNamespace())) {
                return true;
            }
        }
        return false;
    }
    
    public Optional<FlintModification> getCachedModification(final String namespace) {
        final FlintModification modification = FlintController.MODIFICATIONS.get(namespace);
        if (modification != null) {
            return Optional.of(modification);
        }
        return Optional.empty();
    }
    
    public Optional<Setting> getSettings(final FlintModification modification) {
        return this.getSettings(modification.getNamespace());
    }
    
    public Optional<Setting> getSettings(final String namespace) {
        for (final KeyValue<Setting> element : this.labyAPI.coreSettingRegistry().getElements()) {
            final Setting setting = element.getValue();
            if (setting.getId().equals(namespace)) {
                return Optional.of(setting);
            }
        }
        return Optional.empty();
    }
    
    public Pressable displaySettings(final Setting setting) {
        return () -> this.labyAPI.showSetting(setting);
    }
    
    public Optional<FlintDownloadTask> getDownloadTask(final FlintModification modification) {
        return this.flintDownloader.getDownloadTask(modification);
    }
    
    public FlintDownloadTask downloadModification(final FlintModification modification, final Consumer<FlintDownloadTask> finished) {
        return this.downloadModification(modification, null, finished);
    }
    
    public FlintDownloadTask downloadModification(final FlintModification modification, final List<String> skippedDependencies, final Consumer<FlintDownloadTask> finished) {
        return this.flintDownloader.downloadModification(modification, skippedDependencies, finished);
    }
    
    public void uninstallModification(final FlintModification modification, final Runnable finished) throws IOException {
        final String namespace = modification.getNamespace();
        if (this.flintIndex.filter().isHidden(modification)) {
            final DefaultAddonService addonService = DefaultAddonService.getInstance();
            final InstalledAddonInfo addonInfo = addonService.getAddonInfo(namespace);
            if (addonService.addonLoader().isAdditionalAddon(addonInfo)) {
                this.setModLoader(namespace, false, true, result -> {
                    if (result == SetModLoaderResult.CONTINUE) {
                        try {
                            this.flintDownloader.scheduleForRemoval(namespace);
                        }
                        catch (final IOException e) {
                            e.printStackTrace();
                        }
                        finally {
                            this.labyAPI.minecraft();
                            Objects.requireNonNull(finished);
                            final Minecraft minecraft2;
                            minecraft2.executeOnRenderThread(finished::run);
                        }
                    }
                });
                return;
            }
        }
        try {
            this.flintDownloader.scheduleForRemoval(namespace);
        }
        finally {
            final Minecraft minecraft = this.labyAPI.minecraft();
            Objects.requireNonNull(finished);
            minecraft.executeOnRenderThread(finished::run);
        }
    }
    
    public void setModLoader(final String namespace, final boolean installed, final boolean restartPopup, final Consumer<SetModLoaderResult> callback) {
        if (!this.flintIndex.filter().isHidden(namespace)) {
            callback.accept(SetModLoaderResult.CONTINUE);
            return;
        }
        final DefaultLauncherService launcherService = DefaultLauncherService.getInstance();
        final Component modPackComponent = Component.text((launcherService.getModPackName() == null) ? "Unknown" : launcherService.getModPackName());
        if (!launcherService.isConnectedToLauncher()) {
            String message = "labymod.addons.store.profile.modLoader.no-communicator.";
            if (!installed) {
                message += "uninstall";
            }
            else {
                message += "install";
            }
            SimpleAdvancedPopup.builder().title(Component.translatable("labymod.addons.store.profile.modLoader.title", new Component[0])).description(Component.translatable(message, modPackComponent)).addButton(SimpleAdvancedPopup.SimplePopupButton.create(Component.translatable("labymod.ui.button.close", new Component[0]), button -> callback.accept(SetModLoaderResult.CONTINUE_NO_SUCCESS))).build().displayInOverlay();
            return;
        }
        final Component addonNameComponent = Component.text(this.getName(namespace));
        final String translationKeyPrefix = "labymod.addons.store.profile.modLoader.communicator.";
        Component buttonText;
        Component message2;
        if (!installed) {
            buttonText = Component.translatable(translationKeyPrefix + "uninstall-button", new Component[0]);
            message2 = Component.translatable(translationKeyPrefix + "uninstall", addonNameComponent, modPackComponent);
        }
        else {
            buttonText = Component.translatable(translationKeyPrefix + "install-button", new Component[0]);
            if (launcherService.isUsingModLoader()) {
                message2 = Component.translatable(translationKeyPrefix + "install-loader", modPackComponent, Component.text(this.getName(launcherService.getModLoader())), addonNameComponent);
            }
            else {
                message2 = Component.translatable(translationKeyPrefix + "install-no-loader", modPackComponent, addonNameComponent);
            }
        }
        SimpleAdvancedPopup.builder().title(Component.translatable("labymod.addons.store.profile.modLoader.title", new Component[0])).description(message2).addButton(SimpleAdvancedPopup.SimplePopupButton.cancel(button -> callback.accept(SetModLoaderResult.CANCEL))).addButton(SimpleAdvancedPopup.SimplePopupButton.create(buttonText, button -> {
            final LauncherCommunicationClient communicator2;
            final LauncherCommunicationClient communicator = communicator2 = launcherService.getCommunicator();
            new LauncherModLoaderChangePacket(installed ? namespace : null);
            final LauncherModLoaderChangePacket packet;
            communicator2.sendPacket(packet);
            callback.accept(SetModLoaderResult.SUCCESS);
            if (!(!restartPopup)) {
                SimpleAdvancedPopup.builder().title(Component.translatable("labymod.addons.store.profile.restart.alternative-title", new Component[0])).description(Component.translatable("labymod.addons.store.profile.restart.alternative-title", new Component[0])).addButton(SimpleAdvancedPopup.SimplePopupButton.create(Component.translatable("labymod.addons.store.profile.restart.later", new Component[0]))).addButton(SimpleAdvancedPopup.SimplePopupButton.create(Component.translatable("labymod.addons.store.profile.restart.now", new Component[0]), simplePopupButton -> launcherService.restart())).build().displayInOverlay();
            }
        })).build().displayInOverlay();
    }
    
    private String getName(final String namespace) {
        final FlintModification modification = this.flintIndex.getModification(namespace);
        return (modification == null) ? namespace : modification.getName();
    }
    
    public FlintOrganization getOrganization(final int id, final ResultCallback<FlintOrganization> callback) {
        final FlintOrganization organization = FlintController.ORGANIZATIONS.get(id);
        if (organization != null) {
            return organization;
        }
        if (callback != null && !this.addQuery("org", id, callback, false)) {
            return null;
        }
        Request.ofGson(FlintOrganization.class).url("https://flintmc.net/api/client-store/get-organization/%d", new Object[] { id }).async().execute(response -> {
            if (response.hasException()) {
                this.logging.error("Failed to load organization (id:" + id, (Throwable)response.exception());
            }
            else if (response.isPresent()) {
                FlintController.ORGANIZATIONS.put(id, (FlintOrganization)response.get());
            }
            this.callQueries("org", id, (Result<Object>)response);
            return;
        });
        return null;
    }
    
    private void loadTags() {
        this.logging.debug("Loading Tags...", new Object[0]);
        this.tags.clear();
        Request.ofGson(JsonObject.class).url("https://flintmc.net/api/client-store/get-tags", new Object[0]).async(true).execute(response -> {
            if (response.hasException()) {
                this.logging.error("Failed to load tags", response.exception());
            }
            else {
                ((JsonObject)response.get()).entrySet().iterator();
                final Iterator iterator;
                while (iterator.hasNext()) {
                    final Map.Entry<String, JsonElement> entry = iterator.next();
                    final FlintTag flintTag = (FlintTag)FlintController.GSON.fromJson((JsonElement)entry.getValue(), (Class)FlintTag.class);
                    this.tags.put(flintTag.getId(), flintTag);
                }
            }
        });
    }
    
    public Optional<List<FlintModification.Review>> getOrLoadReviews(final String namespace, final ResultCallback<List<FlintModification.Review>> callback) {
        final List<FlintModification.Review> cache = FlintController.REVIEWS.get(namespace);
        if (cache != null) {
            return Optional.of(cache);
        }
        if (!this.addQuery("reviews", namespace, callback, true)) {
            return Optional.empty();
        }
        final Result<List<FlintModification.Review>> result = Result.empty();
        Request.ofGson(JsonArray.class).url("https://flintmc.net/api/client-store/get-modification-ratings/%s", new Object[] { namespace }).async().execute(response -> {
            if (response.hasException()) {
                callback.acceptException(response.exception());
                this.clearQueries("reviews", namespace);
                return;
            }
            else {
                final List<FlintModification.Review> reviews = (List<FlintModification.Review>)Lists.newArrayList();
                ((JsonArray)response.get()).iterator();
                final Iterator iterator;
                while (iterator.hasNext()) {
                    final JsonElement jsonElement = iterator.next();
                    reviews.add((FlintModification.Review)FlintController.GSON.fromJson(jsonElement, (Class)FlintModification.Review.class));
                }
                FlintController.REVIEWS.put(namespace, reviews);
                result.set(reviews);
                this.callQueries("reviews", namespace, (Result<Object>)result);
                return;
            }
        });
        return Optional.empty();
    }
    
    public void loadDescription(final String namespace, final ResultCallback<String> callback) {
        if (!this.addQuery("description", namespace, callback, true)) {
            return;
        }
        final Result<String> result = Result.empty();
        ((AbstractRequest<String, R>)((AbstractRequest<T, StringRequest>)((AbstractRequest<T, StringRequest>)Request.ofString()).url("https://flintmc.net/api/client-store/get-modification-description/%s", new Object[] { namespace })).async()).execute(response -> {
            if (response.hasException()) {
                callback.acceptException(response.exception());
                this.clearQueries("description", namespace);
            }
            else {
                final String rawDescription = (String)response.get();
                final String description = rawDescription.substring(1, rawDescription.length() - 1).replace("\\r\\n", "\n").replace("\\n", "\n").replace("\\/", "/");
                result.set(StringUtil.parseEscapedUnicode(description));
                this.callQueries("description", namespace, (Result<Object>)result);
            }
        });
    }
    
    public Optional<List<FlintModification.Changelog>> getOrLoadChangelog(final String namespace, final ResultCallback<List<FlintModification.Changelog>> callback) {
        final List<FlintModification.Changelog> cache = FlintController.CHANGELOGS.get(namespace);
        if (cache != null) {
            return Optional.of(cache);
        }
        if (!this.addQuery("changelogs", namespace, callback, true)) {
            return Optional.empty();
        }
        final Result<List<FlintModification.Changelog>> result = Result.empty();
        Request.ofGson(JsonArray.class).url("https://flintmc.net/api/client-store/get-modification-changelogs/%s/%s", new Object[] { namespace, FlintUrls.getCurrentReleaseChannel() }).async().execute(response -> {
            if (response.hasException()) {
                callback.acceptException(response.exception());
                this.clearQueries("changelogs", namespace);
                return;
            }
            else {
                final List<FlintModification.Changelog> changelogs = (List<FlintModification.Changelog>)Lists.newArrayList();
                ((JsonArray)response.get()).iterator();
                final Iterator iterator;
                while (iterator.hasNext()) {
                    final JsonElement jsonElement = iterator.next();
                    changelogs.add(0, (FlintModification.Changelog)FlintController.GSON.fromJson(jsonElement, (Class)FlintModification.Changelog.class));
                }
                FlintController.CHANGELOGS.put(namespace, changelogs);
                result.set(changelogs);
                this.callQueries("changelogs", namespace, (Result<Object>)result);
                return;
            }
        });
        return Optional.empty();
    }
    
    public void getModification(final String namespace, final ResultCallback<FlintModification> callback) {
        final FlintModification cached = this.loadModification(namespace, callback);
        if (cached != null && callback != null) {
            callback.acceptRaw(cached);
        }
    }
    
    public FlintModification loadModification(final String namespace, final ResultCallback<FlintModification> callback) {
        final FlintModification cached = FlintController.MODIFICATIONS.get(namespace);
        if (cached != null) {
            return cached;
        }
        if (callback != null && !this.addQuery("mod", namespace, callback, false)) {
            return null;
        }
        Request.ofGson(FlintModification.class, FlintController.GSON).url("https://flintmc.net/api/client-store/get-modification/%s/%s", new Object[] { namespace, FlintUrls.getCurrentReleaseChannel() }).async().execute(response -> {
            if (response.isPresent()) {
                final FlintModification value = (FlintModification)response.get();
                FlintController.MODIFICATIONS.put(namespace, value);
                value.setCompatible(value.getVersionCompatibility().isCompatible(Laby.labyAPI().labyModLoader().version()));
            }
            this.callQueries("mod", namespace, (Result<Object>)response);
            return;
        });
        return null;
    }
    
    private boolean addQuery(final String type, final Object identifier, final ResultCallback<?> callback, final boolean single) {
        final List<ResultCallback<?>> queries = FlintController.CURRENT_QUERIES.get(type + ":" + String.valueOf(identifier));
        if (single) {
            FlintController.CURRENT_QUERIES.put(type + ":" + String.valueOf(identifier), Lists.newArrayList(callback));
            return queries == null;
        }
        if (queries == null) {
            FlintController.CURRENT_QUERIES.put(type + ":" + String.valueOf(identifier), Lists.newArrayList(callback));
            return true;
        }
        queries.add(callback);
        return false;
    }
    
    private <T> void callQueries(final String type, final Object identifier, final Result<T> result) {
        final List<ResultCallback<?>> queries = FlintController.CURRENT_QUERIES.get(type + ":" + String.valueOf(identifier));
        if (queries == null || queries.isEmpty()) {
            return;
        }
        for (final ResultCallback<?> query : queries) {
            query.accept((Result<?>)result);
        }
        FlintController.CURRENT_QUERIES.remove(type + ":" + String.valueOf(identifier));
    }
    
    private void clearQueries(final String type, final Object identifier) {
        FlintController.CURRENT_QUERIES.remove(type + ":" + String.valueOf(identifier));
    }
    
    static {
        GSON = new GsonBuilder().registerTypeAdapter((Type)VersionCompatibility.class, (Object)new VersionCompatibilityDeserializer()).create();
        ORGANIZATIONS = new HashMap<Integer, FlintOrganization>();
        MODIFICATIONS = new HashMap<String, FlintModification>();
        REVIEWS = new HashMap<String, List<FlintModification.Review>>();
        CHANGELOGS = new HashMap<String, List<FlintModification.Changelog>>();
        CURRENT_QUERIES = new HashMap<String, List<ResultCallback<?>>>();
    }
    
    public enum SetModLoaderResult
    {
        CONTINUE, 
        CONTINUE_NO_SUCCESS, 
        SUCCESS, 
        CANCEL;
    }
}
