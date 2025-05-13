// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.quicklauncher;

import net.labymod.api.util.io.web.request.AbstractRequest;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import net.labymod.api.util.io.web.request.Response;
import java.io.FileOutputStream;
import java.nio.channels.Channels;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.types.InputStreamRequest;
import java.io.File;
import net.labymod.api.loader.LabyModLoader;
import net.labymod.api.BuildData;
import java.io.IOException;
import net.labymod.core.main.quicklauncher.link.Link;
import java.util.UUID;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.net.URL;
import java.util.Locale;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.notification.Notification;
import net.labymod.accountmanager.storage.account.Account;
import net.labymod.core.main.quicklauncher.link.LinkFactory;
import net.labymod.api.models.OperatingSystem;
import javax.inject.Inject;
import java.nio.file.Paths;
import net.labymod.api.Constants;
import net.labymod.core.main.updater.Updater;
import java.nio.file.Path;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.LabyAPI;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class QuickLauncher
{
    private final LabyAPI labyAPI;
    private final Logging logging;
    private final Path quickLauncherPath;
    
    @Inject
    public QuickLauncher(final LabyAPI labyAPI, final Logging.Factory loggingFactory) {
        this.labyAPI = labyAPI;
        this.logging = loggingFactory.create(Updater.class);
        this.quickLauncherPath = Paths.get(Constants.Files.QUICK_LAUNCHER_JAR_PATH, new String[0]);
    }
    
    public boolean iSupported() {
        return this.getProfile() != null && LinkFactory.isSupported(OperatingSystem.getPlatform());
    }
    
    public void createAsync(final Account account) {
        Task.builder(() -> {
            try {
                this.create(account);
            }
            catch (final Throwable e) {
                e.printStackTrace();
                final Notification notification = Notification.builder().title(Component.translatable("labymod.activity.accountManager.title", new Component[0])).text(Component.text(e.getMessage())).type(Notification.Type.SYSTEM).build();
                Laby.references().notificationController().push(notification);
            }
        }).build().execute();
    }
    
    public void create(final Account account) throws IOException {
        final UUID uuid = account.getUUID();
        final Path javaPath = this.getCurrentJavaPath();
        final String profile = this.getProfile();
        final Path directory = Paths.get(Constants.Files.QUICK_LAUNCHER_DIRECTORY, new String[0]);
        if (!IOUtil.exists(directory)) {
            IOUtil.createDirectories(directory);
        }
        final Link link = LinkFactory.create(OperatingSystem.getPlatform());
        if (link == null) {
            return;
        }
        BufferedImage icon;
        try {
            final String url = String.format(Locale.ROOT, "https://laby.net/texture/profile/head/%s.png?size=32", account.getUUID().toString());
            icon = ImageIO.read(new URL(url));
        }
        catch (final Exception e) {
            e.printStackTrace();
            final String base64 = account.getAvatarImage();
            if (base64 == null) {
                icon = new BufferedImage(16, 16, 2);
            }
            else {
                icon = IOUtil.base64ToBufferedImage(base64);
            }
        }
        final String[] command = { javaPath.toString(), "-jar", this.quickLauncherPath.toAbsolutePath().toString(), profile, uuid.toString(), Constants.Files.ACCOUNTS.toAbsolutePath().toString(), javaPath.toString() };
        final Path linkPath = link.create(String.format(Locale.ROOT, "%s-%s", uuid, profile), directory, command, "LabyMod - " + account.getUsername(), icon);
        OperatingSystem.getPlatform().openFile(IOUtil.toFile(linkPath.getParent()));
        this.download();
        final Notification notification = Notification.builder().title(Component.translatable("labymod.activity.accountManager.title", new Component[0])).text(Component.translatable("labymod.activity.accountManager.export.success", new Component[0])).type(Notification.Type.SYSTEM).build();
        Laby.references().notificationController().push(notification);
    }
    
    private String getProfile() {
        final LabyModLoader loader = this.labyAPI.labyModLoader();
        if (loader.isLabyModDevelopmentEnvironment()) {
            return this.labyAPI.minecraft().getVersion();
        }
        return loader.getProfile().replace("-" + BuildData.commitReference(), (CharSequence)"");
    }
    
    private Path getCurrentJavaPath() {
        final String jvmLibraryPath = System.getProperty("sun.boot.library.path");
        String javaBin;
        if (jvmLibraryPath.contains(File.pathSeparator)) {
            javaBin = jvmLibraryPath.split(File.pathSeparator)[0];
        }
        else {
            final String suffix = (OperatingSystem.getPlatform() == OperatingSystem.WINDOWS) ? ".exe" : "";
            javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java" + suffix;
        }
        return Paths.get(javaBin, new String[0]);
    }
    
    private void download() {
        final Response<WebInputStream> response = ((AbstractRequest<WebInputStream, R>)((AbstractRequest<T, InputStreamRequest>)Request.ofInputStream()).url(Constants.LegacyUrls.QUICK_LAUNCHER, new Object[0])).executeSync();
        if (response.hasException()) {
            this.logging.error("An error occurred while downloading the quick launcher!", response.exception());
            return;
        }
        final WebInputStream inputStream = response.get();
        if (inputStream.getContentLength() == 0) {
            return;
        }
        try (final ReadableByteChannel channel = Channels.newChannel(inputStream.getInputStream());
             final FileOutputStream fileOutputStream = new FileOutputStream(IOUtil.toFile(this.quickLauncherPath))) {
            final FileChannel fileChannel = fileOutputStream.getChannel();
            fileChannel.transferFrom(channel, 0L, Long.MAX_VALUE);
        }
        catch (final IOException e) {
            this.logging.error("An error occurred while downloading the updater jar!", e);
        }
    }
}
