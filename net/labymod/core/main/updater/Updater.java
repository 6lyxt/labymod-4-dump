// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.updater;

import net.labymod.core.main.AssetLoader;
import net.labymod.api.util.version.serial.VersionDeserializer;
import java.nio.file.OpenOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.LinkOption;
import java.nio.charset.StandardCharsets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import net.labymod.api.util.io.web.request.Response;
import java.io.FileOutputStream;
import java.nio.channels.Channels;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.io.web.request.types.InputStreamRequest;
import java.util.List;
import net.labymod.api.loader.LabyModLoader;
import net.labymod.core.main.util.JavaLauncher;
import java.util.ArrayList;
import java.util.Enumeration;
import net.labymod.api.util.io.web.request.Callback;
import java.nio.file.Files;
import java.io.IOException;
import java.io.Reader;
import net.labymod.api.util.version.SemanticVersion;
import java.io.InputStreamReader;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.models.version.Version;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameShutdownEvent;
import javax.inject.Inject;
import net.labymod.api.util.io.web.request.AbstractRequest;
import net.labymod.api.Laby;
import java.io.File;
import net.labymod.api.Constants;
import java.util.Locale;
import net.labymod.api.BuildData;
import net.labymod.api.util.concurrent.task.Task;
import java.nio.file.Path;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.LabyAPI;
import net.labymod.core.main.updater.manifest.UpdaterManifest;
import net.labymod.core.main.updater.manifest.LabyModManifest;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class Updater
{
    private static final String RELEASE_CHANNEL_KEY = "Release-Channel";
    private final Request<LabyModManifest.Version> labyModManifestRequest;
    private final Request<UpdaterManifest> updaterManifestRequest;
    private final LabyAPI labyAPI;
    private final Logging logging;
    private final Path updaterPath;
    private final boolean useUpdater;
    private boolean foundUpdate;
    private Task updaterTask;
    
    @Inject
    public Updater(final LabyAPI labyAPI, final Logging.Factory loggingFactory) {
        this.labyAPI = labyAPI;
        this.logging = loggingFactory.create(Updater.class);
        this.labyAPI.eventBus().registerListener(this);
        final String releaseType = BuildData.releaseType();
        this.updaterPath = new File(String.format(Locale.ROOT, Constants.Files.UPDATER_PATH, releaseType)).toPath();
        this.useUpdater = !Laby.references().launcherService().isUsingLabyModLauncher();
        if (this.labyAPI.labyModLoader().isLabyModDevelopmentEnvironment()) {
            this.labyModManifestRequest = null;
            this.updaterManifestRequest = null;
            return;
        }
        this.labyModManifestRequest = Request.ofGson(LabyModManifest.Version.class).url(Constants.Urls.LABYMOD_MANIFEST, new Object[] { releaseType }).addHeader("Release-Channel", (Object)releaseType);
        this.updaterManifestRequest = Request.ofGson(UpdaterManifest.class).url(Constants.Urls.UPDATER_MANIFEST, new Object[] { releaseType }).addHeader("Release-Channel", (Object)releaseType).async();
    }
    
    @Subscribe
    public void shutdown(final GameShutdownEvent event) {
        this.executeUpdater(event);
    }
    
    public void initialize() {
        if (this.labyAPI.labyModLoader().isLabyModDevelopmentEnvironment()) {
            return;
        }
        this.saveUpdaterInformation();
        (this.updaterTask = Task.builder(() -> {
            if (!IOUtil.exists(this.updaterPath) || !this.useUpdater) {
                this.resolveUpdaterManifest(this.labyAPI.labyModLoader().version(), true);
                return;
            }
            else {
                Version currentVersion = null;
                try (final ZipFile zipFile = new ZipFile(IOUtil.toFile(this.updaterPath))) {
                    final Enumeration<? extends ZipEntry> entries = zipFile.entries();
                    while (entries.hasMoreElements()) {
                        final ZipEntry zipEntry = (ZipEntry)entries.nextElement();
                        if (!zipEntry.getName().equals("build-data.json")) {
                            continue;
                        }
                        else {
                            currentVersion = (Version)GsonUtil.DEFAULT_GSON.fromJson((Reader)new InputStreamReader(zipFile.getInputStream(zipEntry)), (Class)SemanticVersion.class);
                            break;
                        }
                    }
                }
                catch (final IOException exception) {
                    this.logging.error("An error occurred while reading the jar!", exception);
                }
                if (currentVersion == null) {
                    try {
                        this.logging.info("No build information about the updater was found!", new Object[0]);
                        Files.delete(this.updaterPath);
                        this.resolveUpdaterManifest(this.labyAPI.labyModLoader().version(), true);
                    }
                    catch (final IOException exception2) {
                        this.logging.error("An error occurred while deleting the jar!", exception2);
                    }
                    return;
                }
                else {
                    this.resolveUpdaterManifest(currentVersion, false);
                    return;
                }
            }
        }).build()).execute();
        this.labyModManifestRequest.execute(new LabyModManifestResponse());
    }
    
    private void executeUpdater(final GameShutdownEvent event) {
        final LabyModLoader loader = this.labyAPI.labyModLoader();
        if (loader.isLabyModDevelopmentEnvironment() || !this.useUpdater) {
            return;
        }
        final boolean crash = event.isCrash();
        if (!crash && !this.foundUpdate) {
            this.logging.info("Not executing the updater as no update was found!", new Object[0]);
            return;
        }
        this.logging.info("Executing {}...", crash ? "crashreporter" : "updater");
        final List<String> arguments = new ArrayList<String>();
        arguments.add("-jar");
        arguments.add(this.updaterPath.toAbsolutePath().toString());
        arguments.add("--application-type");
        arguments.add(crash ? "crashreporter" : "updater");
        if (crash && event.getCrashReportFile() != null) {
            arguments.add("--crash-log");
            arguments.add(event.getCrashReportFile().getAbsolutePath());
        }
        arguments.add("--launcher-type");
        arguments.add(Laby.references().launcherService().getLauncherOrDefault("microsoft"));
        arguments.add("--gd");
        arguments.add(loader.getAssetsDirectory().toAbsolutePath().getParent().toString());
        arguments.add("--wd");
        arguments.add(loader.getGameDirectory().toAbsolutePath().toString());
        JavaLauncher.launch(arguments);
    }
    
    public boolean hasFoundUpdate() {
        return this.foundUpdate;
    }
    
    private void resolveUpdaterManifest(final Version currentVersion, final boolean download) {
        if (!this.useUpdater) {
            return;
        }
        this.updaterManifestRequest.execute(new UpdaterManifestResponse(currentVersion, download));
    }
    
    private void downloadUpdater(final String version) {
        if (!this.useUpdater) {
            return;
        }
        final String releaseType = BuildData.releaseType();
        final Response<WebInputStream> response = ((AbstractRequest<WebInputStream, R>)((AbstractRequest<T, InputStreamRequest>)((AbstractRequest<T, InputStreamRequest>)Request.ofInputStream()).url(Constants.Urls.UPDATER_DOWNLOAD, new Object[] { releaseType, version })).addHeader("Release-Channel", (Object)releaseType)).executeSync();
        if (response.hasException()) {
            this.logging.error("An error occurred while downloading the updater!", response.exception());
            return;
        }
        final WebInputStream inputStream = response.get();
        if (inputStream.getContentLength() == 0) {
            return;
        }
        try (final ReadableByteChannel channel = Channels.newChannel(inputStream.getInputStream());
             final FileOutputStream fileOutputStream = new FileOutputStream(IOUtil.toFile(this.updaterPath))) {
            final FileChannel fileChannel = fileOutputStream.getChannel();
            fileChannel.transferFrom(channel, 0L, Long.MAX_VALUE);
        }
        catch (final IOException e) {
            this.logging.error("An error occurred while downloading the updater jar!", e);
        }
        if (this.updaterTask != null) {
            this.updaterTask.cancel();
            this.updaterTask = null;
        }
    }
    
    private void saveUpdaterInformation() {
        if (!this.useUpdater) {
            return;
        }
        final LabyModLoader labyModLoader = this.labyAPI.labyModLoader();
        if (labyModLoader.getGameDirectory() == null || labyModLoader.getAssetsDirectory() == null) {
            return;
        }
        try {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("gameDirectory", labyModLoader.getAssetsDirectory().getParent().toAbsolutePath().toString());
            final byte[] newInformation = GsonUtil.DEFAULT_GSON.toJson((JsonElement)jsonObject).getBytes(StandardCharsets.UTF_8);
            final Path path = Constants.Files.LABYMOD_DIRECTORY.resolve("updater-info.json");
            if (Files.notExists(path, new LinkOption[0])) {
                Files.createDirectories(path.getParent(), (FileAttribute<?>[])new FileAttribute[0]);
                Files.createFile(path, (FileAttribute<?>[])new FileAttribute[0]);
            }
            else {
                try {
                    final String currentInformation = new String(Files.readAllBytes(path));
                    if (currentInformation.equals(new String(newInformation))) {
                        return;
                    }
                }
                catch (final Exception ex) {}
            }
            Files.write(path, newInformation, new OpenOption[0]);
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }
    
    final class UpdaterManifestResponse implements Callback<UpdaterManifest>
    {
        private final Version currentVersion;
        private final boolean download;
        
        public UpdaterManifestResponse(final Version currentVersion, final boolean download) {
            this.currentVersion = currentVersion;
            this.download = download;
        }
        
        @Override
        public void accept(final Response<UpdaterManifest> response) {
            if (response.hasException()) {
                Updater.this.logging.error("An error occurred when requesting the current update information", response.exception());
                return;
            }
            final UpdaterManifest updaterManifest = response.get();
            final Version latestVersion = VersionDeserializer.from(updaterManifest.getLatest());
            if (this.download) {
                Updater.this.downloadUpdater(updaterManifest.getLatest());
                return;
            }
            if (this.currentVersion.isLowerThan(latestVersion)) {
                Updater.this.downloadUpdater(updaterManifest.getLatest());
            }
        }
    }
    
    final class LabyModManifestResponse implements Callback<LabyModManifest.Version>
    {
        @Override
        public void accept(final Response<LabyModManifest.Version> response) {
            if (response.hasException()) {
                Updater.this.logging.error("An error occurred while requesting the manifest!", response.exception());
                return;
            }
            if (!response.isPresent()) {
                return;
            }
            final LabyModManifest.Version latest = response.get();
            AssetLoader.getInstance().downloadAssets(Laby.labyAPI().isFullyInitialized(), latest);
            final String labyModVersion = latest.getLabyModVersion();
            final Version version = VersionDeserializer.from(labyModVersion);
            if (version.isGreaterThan(BuildData.version())) {
                Updater.this.foundUpdate = true;
            }
            if (!latest.getCommitReference().equals(BuildData.commitReference())) {
                Updater.this.foundUpdate = true;
            }
            if (Updater.this.foundUpdate) {
                Updater.this.logging.info("Found a new labymod update!", new Object[0]);
            }
        }
    }
}
