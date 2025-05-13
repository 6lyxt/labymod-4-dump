// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.platform.launcher;

import java.util.List;
import java.nio.file.OpenOption;
import java.io.IOException;
import java.util.Collection;
import java.nio.file.Files;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.Constants;
import java.util.ArrayList;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.Laby;
import net.labymod.api.util.version.serial.VersionDeserializer;
import net.labymod.core.loader.DefaultLabyModLoader;
import java.util.UUID;
import net.labymod.api.models.version.Version;
import net.labymod.core.platform.launcher.communication.LauncherCommunicationClient;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.platform.launcher.LauncherService;

@Singleton
@Implements(LauncherService.class)
public class DefaultLauncherService implements LauncherService
{
    private static final Logging LOGGER;
    private final LauncherCommunicationClient communicator;
    private final String launcher;
    private final String launcherVersion;
    private final String modPackId;
    private final String modPackName;
    private final Version modPackVersion;
    private final String modLoader;
    private final UUID gameSessionId;
    private final boolean usingLabyModLauncher;
    
    public DefaultLauncherService() {
        final boolean devEnvironment = DefaultLabyModLoader.getInstance().isLabyModDevelopmentEnvironment();
        String launcher = System.getProperty("net.labymod.launcher");
        if (launcher == null) {
            launcher = System.getProperty("minecraft.launcher.brand");
        }
        if (launcher != null) {
            launcher = launcher.replace("\"", "");
        }
        this.launcher = launcher;
        this.launcherVersion = System.getProperty("minecraft.launcher.version");
        this.usingLabyModLauncher = (devEnvironment || (this.launcher != null && this.launcher.equalsIgnoreCase("labymod-launcher")));
        this.gameSessionId = this.getGameSessionIdFromProperties(devEnvironment);
        this.modPackId = System.getProperty("net.labymod.mod-pack-id");
        this.modLoader = System.getProperty("net.labymod.mod-loader");
        final String modPackName = System.getProperty("net.labymod.mod-pack-name");
        this.modPackName = ((modPackName == null) ? this.modPackId : modPackName);
        final String modPackVersion = System.getProperty("net.labymod.mod-pack-version");
        this.modPackVersion = ((modPackVersion == null || !this.isUsingModPack()) ? null : VersionDeserializer.from(modPackVersion));
        LauncherCommunicationClient client = null;
        if (this.usingLabyModLauncher && this.gameSessionId != null) {
            String rawPort = System.getProperty("net.labymod.launcher.port");
            if (devEnvironment && rawPort == null) {
                rawPort = "1337";
            }
            if (rawPort == null) {
                DefaultLauncherService.LOGGER.warn("Using LabyMod Launcher but no communication port was specified.", new Object[0]);
            }
            else {
                Integer port = null;
                try {
                    port = Integer.parseInt(rawPort);
                }
                catch (final NumberFormatException e) {
                    DefaultLauncherService.LOGGER.error("Failed to parse launcher communication port from system properties", e);
                }
                if (port != null && port > 0 && port < 65536) {
                    client = new LauncherCommunicationClient(this.gameSessionId.toString(), port);
                    client.start();
                }
                else {
                    DefaultLauncherService.LOGGER.warn("Invalid launcher communication port specified: " + rawPort, new Object[0]);
                }
            }
        }
        this.communicator = client;
    }
    
    public static DefaultLauncherService getInstance() {
        return (DefaultLauncherService)Laby.references().launcherService();
    }
    
    @Nullable
    public LauncherCommunicationClient getCommunicator() {
        return this.communicator;
    }
    
    @Nullable
    @Override
    public String getLauncher() {
        return this.launcher;
    }
    
    @Nullable
    @Override
    public String getLauncherVersion() {
        return this.launcherVersion;
    }
    
    @Nullable
    @Override
    public UUID getGameSessionId() {
        return this.gameSessionId;
    }
    
    @Nullable
    @Override
    public String getModPackId() {
        return this.modPackId;
    }
    
    @Nullable
    @Override
    public String getModPackName() {
        return this.modPackName;
    }
    
    @Nullable
    @Override
    public Version getModPackVersion() {
        return this.modPackVersion;
    }
    
    @Override
    public boolean isUsingLabyModLauncher() {
        return this.usingLabyModLauncher;
    }
    
    @Override
    public boolean isConnectedToLauncher() {
        return this.communicator != null && this.communicator.isConnected();
    }
    
    @Override
    public boolean isUsingModLoader() {
        return this.modLoader != null;
    }
    
    @Override
    public String getModLoader() {
        return this.modLoader;
    }
    
    @Override
    public void restart() {
        if (this.gameSessionId == null) {
            throw new IllegalStateException("The game session id is null. Was LabyMod started using the LabyMod Launcher (v1.0.28 or newer)?");
        }
        final List<String> uniqueIds = new ArrayList<String>();
        uniqueIds.add(this.gameSessionId.toString());
        if (IOUtil.exists(Constants.Files.RESTART)) {
            try {
                uniqueIds.addAll(Files.readAllLines(Constants.Files.RESTART));
            }
            catch (final IOException e) {
                DefaultLauncherService.LOGGER.warn("Failed to read restart file", e);
            }
        }
        try {
            Files.write(Constants.Files.RESTART, uniqueIds, new OpenOption[0]);
        }
        catch (final IOException e) {
            DefaultLauncherService.LOGGER.warn("Failed to write restart file", e);
        }
        Laby.labyAPI().minecraft().shutdownGame();
    }
    
    private UUID getGameSessionIdFromProperties(final boolean devEnvironment) {
        final String gameSessionIdString = System.getProperty("net.labymod.game-session-id");
        UUID gameSessionId = null;
        if (gameSessionIdString != null) {
            try {
                gameSessionId = UUID.fromString(gameSessionIdString);
            }
            catch (final Exception ex) {}
        }
        if (devEnvironment) {
            gameSessionId = UUID.randomUUID();
        }
        return this.usingLabyModLauncher ? gameSessionId : null;
    }
    
    static {
        LOGGER = Logging.create(LauncherService.class);
    }
}
