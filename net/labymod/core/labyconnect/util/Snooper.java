// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.util;

import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.platform.launcher.LauncherService;
import java.util.Objects;
import java.util.Map;
import net.labymod.core.main.LabyMod;
import net.labymod.api.addon.LoadedAddon;
import java.util.concurrent.CompletableFuture;
import java.util.Iterator;
import com.google.gson.JsonElement;
import net.labymod.api.modloader.mod.ModInfo;
import com.google.gson.JsonArray;
import net.labymod.api.modloader.ModLoader;
import com.google.gson.JsonObject;
import net.labymod.core.loader.DefaultLabyModLoader;
import java.nio.file.Path;
import java.nio.file.OpenOption;
import java.util.UUID;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.client.util.SystemInfo;
import net.labymod.api.client.render.gl.GlInformation;
import net.labymod.api.LabyAPI;
import java.util.Set;
import net.labymod.api.util.logging.Logging;

public class Snooper
{
    private static final int FORMAT_VERSION = 4;
    private static final Logging LOGGER;
    private static final Set<String> MINECRAFT_OPTION_EXCLUDES;
    private static String identifier;
    private static Boolean isInternalReleaseChannel;
    private final LabyAPI labyAPI;
    private final GlInformation glInformation;
    private final SystemInfo systemInfo;
    private final Logging logging;
    
    public Snooper() {
        this.labyAPI = Laby.labyAPI();
        this.glInformation = Laby.references().glInformation();
        this.systemInfo = Laby.references().systemInfo();
        this.logging = Laby.references().loggingFactory().create(Snooper.class);
    }
    
    public static String getIdentifier() {
        if (Snooper.identifier == null) {
            final Path path = Constants.Files.LABYMOD_DIRECTORY.resolve(".snooper");
            if (Files.exists(path, new LinkOption[0])) {
                try {
                    Snooper.identifier = new String(Files.readAllBytes(path));
                }
                catch (final IOException e) {
                    Snooper.LOGGER.error("Failed to read snooper identifier, generating new one", e);
                }
            }
            if (Snooper.identifier == null) {
                Snooper.identifier = UUID.randomUUID().toString();
                try {
                    Files.write(path, Snooper.identifier.getBytes(), new OpenOption[0]);
                }
                catch (final IOException e) {
                    Snooper.LOGGER.error("Failed to write snooper identifier", e);
                }
            }
        }
        return Snooper.identifier;
    }
    
    public static boolean isInternalReleaseChannel() {
        if (Snooper.isInternalReleaseChannel == null) {
            final String releaseChannel = DefaultLabyModLoader.getInstance().getEffectiveReleaseChannel();
            Snooper.isInternalReleaseChannel = (releaseChannel == null || (!releaseChannel.equals("production") && !releaseChannel.equals("snapshot")));
        }
        return Snooper.isInternalReleaseChannel;
    }
    
    private JsonObject createModLoaderEntry(final String modLoaderId) {
        final ModLoader modLoader = this.labyAPI.modLoaderRegistry().getById(modLoaderId);
        final JsonObject modLoaderEntry = new JsonObject();
        modLoaderEntry.addProperty("installed", Boolean.valueOf(modLoader != null));
        final JsonArray mods = new JsonArray();
        if (modLoader != null) {
            for (final ModInfo modInfo : modLoader.getModInfos()) {
                final JsonObject entry = new JsonObject();
                entry.addProperty("id", modInfo.getId());
                entry.addProperty("version", modInfo.version().toString());
                mods.add((JsonElement)entry);
            }
        }
        modLoaderEntry.add("mods", (JsonElement)mods);
        return modLoaderEntry;
    }
    
    public CompletableFuture<JsonObject> collect() {
        final CompletableFuture<JsonObject> future = new CompletableFuture<JsonObject>();
        if (!isInternalReleaseChannel() && !Laby.labyAPI().config().other().anonymousStatistics().get()) {
            return future;
        }
        try {
            final JsonObject obj = new JsonObject();
            obj.addProperty("version", (Number)4);
            final JsonObject userObj = new JsonObject();
            userObj.addProperty("identifier", getIdentifier());
            if (isInternalReleaseChannel()) {
                userObj.addProperty("account_uuid", Laby.labyAPI().getUniqueId().toString());
            }
            obj.add("user", (JsonElement)userObj);
            final JsonObject minecraft = new JsonObject();
            final LauncherService launcherService = Laby.references().launcherService();
            final JsonObject labymod = new JsonObject();
            final JsonArray addons = new JsonArray();
            for (final LoadedAddon loadedAddon : this.labyAPI.addonService().getLoadedAddons()) {
                final InstalledAddonInfo info = loadedAddon.info();
                final JsonObject entry = new JsonObject();
                entry.addProperty("namespace", info.getNamespace());
                entry.addProperty("name", info.getDisplayName());
                entry.addProperty("offline", Boolean.valueOf(!info.isFlintAddon()));
                entry.addProperty("version", info.getVersion());
                entry.addProperty("hash", info.getFileHash());
                addons.add((JsonElement)entry);
            }
            labymod.add("addons", (JsonElement)addons);
            labymod.add("settings", (JsonElement)((LabyMod)this.labyAPI).getLabyConfig().serialize());
            labymod.addProperty("version", this.labyAPI.getVersion());
            final JsonObject modPack = new JsonObject();
            if (launcherService.isUsingLabyModLauncher() && launcherService.getModPackName() != null) {
                modPack.addProperty("id", launcherService.getModPackId());
                modPack.addProperty("name", launcherService.getModPackName());
                modPack.addProperty("modLoader", launcherService.getModLoader());
            }
            minecraft.add("labymod", (JsonElement)labymod);
            final JsonObject minecraftOptions = new JsonObject();
            final Map<String, String> optionCache = LabyMod.references().optionsTranslator().getOptionCache();
            for (final Map.Entry<String, String> entry2 : optionCache.entrySet()) {
                if (Snooper.MINECRAFT_OPTION_EXCLUDES.contains(entry2.getKey())) {
                    continue;
                }
                minecraftOptions.addProperty((String)entry2.getKey(), (String)entry2.getValue());
            }
            minecraft.add("options", (JsonElement)minecraftOptions);
            minecraft.add("forge", (JsonElement)this.createModLoaderEntry("forge"));
            minecraft.add("neo_forge", (JsonElement)this.createModLoaderEntry("neoforge"));
            minecraft.add("fabric", (JsonElement)this.createModLoaderEntry("fabricloader"));
            minecraft.addProperty("version", this.labyAPI.minecraft().getVersion());
            minecraft.addProperty("protocol_version", (Number)this.labyAPI.minecraft().getProtocolVersion());
            minecraft.addProperty("launcher_brand", launcherService.getLauncherOrDefault("unknown"));
            minecraft.addProperty("launcher_version", (String)Objects.requireNonNullElse(launcherService.getLauncherVersion(), "unknown"));
            obj.add("minecraft", (JsonElement)minecraft);
            final JsonObject operatingSystem = new JsonObject();
            operatingSystem.addProperty("name", System.getProperty("os.name"));
            operatingSystem.addProperty("version", System.getProperty("os.version"));
            operatingSystem.addProperty("arch", System.getProperty("os.arch"));
            obj.add("os", (JsonElement)operatingSystem);
            final JsonObject java = new JsonObject();
            java.addProperty("version", System.getProperty("java.version"));
            obj.add("java", (JsonElement)java);
            this.labyAPI.minecraft().executeOnRenderThread(() -> {
                try {
                    final JsonObject opengl = new JsonObject();
                    opengl.addProperty("version", this.glInformation.getGlVersion());
                    opengl.addProperty("vendor", this.glInformation.getGlVendor());
                    opengl.addProperty("renderer", this.glInformation.getGlRenderer());
                    obj.add("opengl", (JsonElement)opengl);
                    final JsonObject hardware = new JsonObject();
                    hardware.addProperty("cpu", this.systemInfo.processor().name());
                    try {
                        hardware.addProperty("memory", (Number)this.systemInfo.getTotalMemorySize());
                    }
                    catch (final Throwable t) {
                        this.logging.error("Can't access memory information", t);
                    }
                    final JsonArray monitors = new JsonArray();
                    this.glInformation.getMonitors();
                    final GlInformation.Monitor[] array;
                    int i = 0;
                    for (int length = array.length; i < length; ++i) {
                        final GlInformation.Monitor monitor = array[i];
                        final JsonObject monitorObject = new JsonObject();
                        monitorObject.addProperty("width", (Number)monitor.getWidth());
                        monitorObject.addProperty("height", (Number)monitor.getHeight());
                        monitorObject.addProperty("refresh_rate", (Number)monitor.getRefreshRate());
                        monitors.add((JsonElement)monitorObject);
                    }
                    hardware.add("monitors", (JsonElement)monitors);
                    obj.add("hardware", (JsonElement)hardware);
                    future.complete(obj);
                }
                catch (final Throwable e2) {
                    this.logging.error("Can't access GL information", e2);
                }
                return;
            });
        }
        catch (final Throwable e) {
            this.logging.error("Can't create snooper object", e);
        }
        return future;
    }
    
    static {
        LOGGER = Logging.create(Snooper.class);
        MINECRAFT_OPTION_EXCLUDES = Set.of("lastServer", "resourcePacks", "incompatibleResourcePacks");
    }
}
