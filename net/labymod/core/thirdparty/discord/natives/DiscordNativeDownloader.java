// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.thirdparty.discord.natives;

import net.labymod.api.util.io.web.request.AbstractRequest;
import java.util.zip.ZipEntry;
import org.jetbrains.annotations.NotNull;
import java.nio.file.OpenOption;
import net.labymod.api.util.io.zip.ZipException;
import java.nio.file.Files;
import net.labymod.api.util.io.zip.Zips;
import net.labymod.api.util.io.web.request.Response;
import java.util.function.Consumer;
import javax.inject.Inject;
import net.labymod.api.util.io.web.request.types.FileRequest;
import java.io.IOException;
import net.labymod.api.util.io.IOUtil;
import java.nio.file.Paths;
import net.labymod.api.Constants;
import java.util.Locale;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.util.io.web.request.Request;
import java.nio.file.Path;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public final class DiscordNativeDownloader
{
    private static final Logging LOGGER;
    private static final int CONNECT_TIMEOUT = 5000;
    private static final String NAME = "discord_game_sdk";
    private static final String PATH = "lib/%s/%s%s";
    private final String zipPath;
    private final Path tempDiscordNatives;
    private final Path nativePath;
    private final Request<Path> nativeRequest;
    
    @Inject
    public DiscordNativeDownloader() {
        final OperatingSystem platform = OperatingSystem.getPlatform();
        final String suffix = "." + platform.getLibraryExtensionName();
        String arch = platform.getArch();
        if (arch.equals("amd64")) {
            arch = "x86_64";
        }
        this.zipPath = String.format(Locale.ROOT, "lib/%s/%s%s", arch, "discord_game_sdk", suffix);
        this.nativePath = Constants.Files.NATIVES.resolve("discord").resolve("discord_game_sdk" + suffix);
        this.tempDiscordNatives = Paths.get("./tmp_discord_natives.zip", new String[0]);
        try {
            IOUtil.deleteIfExits(this.tempDiscordNatives);
        }
        catch (final IOException exception) {
            DiscordNativeDownloader.LOGGER.error("Temporary discord-natives file could not be deleted", exception);
        }
        this.nativeRequest = ((AbstractRequest<T, Request<Path>>)((AbstractRequest<T, FileRequest>)((AbstractRequest<T, FileRequest>)Request.ofFile(this.tempDiscordNatives)).url("https://dl-game-sdk.discordapp.net/3.2.1/discord_game_sdk.zip", new Object[0])).connectTimeout(5000)).async();
    }
    
    public void download(final Consumer<Path> consumer) {
        if (consumer == null) {
            return;
        }
        if (IOUtil.exists(this.nativePath)) {
            consumer.accept(this.nativePath);
            return;
        }
        this.nativeRequest.execute(pathResponse -> this.accept(pathResponse, consumer));
    }
    
    private void accept(final Response<Path> pathResponse, final Consumer<Path> consumer) {
        if (!pathResponse.isPresent()) {
            if (pathResponse.hasException()) {
                DiscordNativeDownloader.LOGGER.error("Something went wrong when downloading the natives ({})", pathResponse.exception());
                return;
            }
            DiscordNativeDownloader.LOGGER.error("Something went wrong when downloading the natives (Response is empty)", new Object[0]);
        }
        else {
            final int responseCode = pathResponse.getStatusCode() / 100;
            if (responseCode != 2) {
                return;
            }
            final Path path = pathResponse.get();
            try {
                Zips.read(path, (entry, data) -> {
                    final String name = entry.getName();
                    if (!name.equals(this.zipPath)) {
                        return Boolean.valueOf(false);
                    }
                    else {
                        consumer.accept(this.createNativeDirectory(data));
                        return Boolean.valueOf(true);
                    }
                });
                Files.deleteIfExists(this.tempDiscordNatives);
            }
            catch (final IOException exception) {
                if (exception instanceof ZipException) {
                    DiscordNativeDownloader.LOGGER.error("Something went wrong when reading the temporary file ({})", exception.getMessage());
                    return;
                }
                DiscordNativeDownloader.LOGGER.error("Temporary file for natives could not be deleted ({})", exception.getMessage());
            }
        }
    }
    
    @NotNull
    private Path createNativeDirectory(final byte[] data) throws IOException {
        if (IOUtil.exists(this.nativePath)) {
            return this.nativePath;
        }
        IOUtil.createDirectories(this.nativePath.getParent());
        Files.write(this.nativePath, data, new OpenOption[0]);
        return this.nativePath;
    }
    
    static {
        LOGGER = Logging.create("Native Controller");
    }
}
