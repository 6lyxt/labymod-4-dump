// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.thirdparty.discord;

import net.labymod.core.util.logging.DefaultLoggingFactory;
import java.util.Calendar;
import de.jcm.discordgamesdk.activity.ActivityAssets;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.event.labymod.discordrpc.DiscordActivityServerUpdateEvent;
import net.labymod.api.labynet.models.ServerGroup;
import java.util.Optional;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.labyconnect.LabyConnectSession;
import java.util.UUID;
import net.labymod.api.util.AddressUtil;
import net.labymod.core.main.LabyMod;
import de.jcm.discordgamesdk.GameSDKException;
import net.labymod.api.client.network.server.ServerData;
import java.io.IOException;
import java.nio.file.Files;
import de.jcm.discordgamesdk.LogLevel;
import net.labymod.api.util.io.IOUtil;
import java.nio.file.Path;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.event.labymod.discordrpc.DiscordActivityUpdateEvent;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import javax.inject.Inject;
import net.labymod.api.Laby;
import java.util.Locale;
import java.time.Instant;
import de.jcm.discordgamesdk.activity.Activity;
import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import net.labymod.api.thirdparty.discord.DiscordActivity;
import net.labymod.core.thirdparty.discord.natives.DiscordNativeDownloader;
import de.jcm.discordgamesdk.DiscordEventAdapter;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.thirdparty.discord.DiscordApp;

@Singleton
@Implements(DiscordApp.class)
public class DefaultDiscordApp implements DiscordApp
{
    private static final long APPLICATION_ID = 576456544942686228L;
    private static final long UPDATE_COOLDOWN = 500L;
    private static final Logging LOGGER;
    private final DiscordEventAdapter discordEventAdapter;
    private final DiscordNativeDownloader nativeDownloader;
    private final DiscordActivity.Asset defaultAsset;
    private CreateParams parameters;
    private Core core;
    private Activity lastActivity;
    private long lastRunCallbackTime;
    private boolean running;
    private boolean updateRequired;
    private DiscordActivity displayedActivity;
    private DiscordActivity.Asset userAsset;
    private Instant startTime;
    
    @Inject
    public DefaultDiscordApp(final DiscordNativeDownloader nativeDownloader) {
        this.nativeDownloader = nativeDownloader;
        this.discordEventAdapter = new DiscordEventListener();
        this.startTime = Instant.now();
        this.defaultAsset = DiscordActivity.Asset.of("labymod", String.format(Locale.ROOT, "Minecraft %s - LabyMod %s", Laby.labyAPI().minecraft().getVersion(), Laby.labyAPI().getVersion()));
    }
    
    @Override
    public void displayDefaultActivity(final boolean startTimeReset) {
        final DiscordActivity.Builder builder = this.defaultActivityBuilder();
        if (startTimeReset) {
            builder.start();
        }
        this.displayActivity(builder.build());
    }
    
    @Override
    public void displayActivity(@NotNull final DiscordActivity discordActivity) {
        Objects.requireNonNull(discordActivity, "Activity to display cannot be null");
        if (!this.running || discordActivity.equals(this.displayedActivity)) {
            return;
        }
        final DiscordActivityUpdateEvent event = new DiscordActivityUpdateEvent(discordActivity);
        if (event.isCancelled()) {
            return;
        }
        this.displayInternal(event.activity());
    }
    
    @Nullable
    @Override
    public DiscordActivity getDisplayedActivity() {
        return this.displayedActivity;
    }
    
    @Override
    public boolean isRunning() {
        return this.running;
    }
    
    public void initialize() {
        this.nativeDownloader.download(library -> {
            if (library != null) {
                this.initialize(library);
                this.displayDefaultActivity();
            }
        });
    }
    
    private void initialize(final Path discordLibrary) {
        try {
            Core.init(IOUtil.toFile(discordLibrary));
            (this.parameters = new CreateParams()).setClientID(576456544942686228L);
            this.parameters.setFlags(new CreateParams.Flags[] { CreateParams.Flags.NO_REQUIRE_DISCORD });
            this.parameters.registerEventHandler(this.discordEventAdapter);
            (this.core = new Core(this.parameters)).setLogHook(LogLevel.INFO, (level, message) -> {
                switch (level) {
                    case ERROR: {
                        DefaultDiscordApp.LOGGER.error(message, new Object[0]);
                        break;
                    }
                    case WARN: {
                        DefaultDiscordApp.LOGGER.warn(message, new Object[0]);
                        break;
                    }
                    case INFO: {
                        DefaultDiscordApp.LOGGER.info(message, new Object[0]);
                        break;
                    }
                    case DEBUG: {
                        DefaultDiscordApp.LOGGER.debug(message, new Object[0]);
                        break;
                    }
                }
                return;
            });
            this.running = true;
        }
        catch (final Throwable throwable) {
            DefaultDiscordApp.LOGGER.error("Discord integration could not be started {}", throwable.getMessage());
            try {
                Files.deleteIfExists(discordLibrary);
            }
            catch (final IOException ignored) {
                DefaultDiscordApp.LOGGER.error("Discord library could not be deleted ({})", throwable.getMessage());
            }
        }
    }
    
    public void updateServerInfo(final boolean resetStart) {
        final ServerData currentServerData = Laby.labyAPI().serverController().getCurrentServerData();
        if (currentServerData == null) {
            return;
        }
        this.updateServerInfo(currentServerData, resetStart);
    }
    
    public void updateServerInfo(final ServerData serverData, final boolean resetStart) {
        final boolean showServerAddress = Laby.labyAPI().config().other().discord().showServerAddress().get();
        this.updateServerInfo(serverData, showServerAddress, resetStart);
    }
    
    public void dispose() {
        try {
            if (this.core != null) {
                this.core.close();
                this.core = null;
            }
            if (this.parameters != null) {
                this.parameters.close();
                this.parameters = null;
            }
            this.running = false;
        }
        catch (final GameSDKException exception) {
            DefaultDiscordApp.LOGGER.error(exception.getMessage(), new Object[0]);
            this.running = false;
        }
    }
    
    public void updateServerInfo(final ServerData serverData, final boolean showServerAddress, final boolean resetStart) {
        if (!this.running) {
            return;
        }
        if (serverData == null) {
            this.displayDefaultActivity(resetStart);
            return;
        }
        final DiscordActivity.Builder builder = DiscordActivity.builder(this);
        if (resetStart) {
            builder.start();
        }
        if (showServerAddress) {
            final UUID lanHost = LabyMod.references().sharedLanWorldService().getCurrentHost();
            final LabyConnectSession session = Laby.references().labyConnect().getSession();
            if (lanHost != null && session != null) {
                final Friend friend = session.getFriend(lanHost);
                builder.details("LAN world of " + ((friend == null) ? "Unknown" : friend.getName()));
            }
            else {
                String host = serverData.address().toString();
                if (AddressUtil.isLocalHost(host)) {
                    host = "localhost";
                }
                builder.details(host);
                final Optional<ServerGroup> serverGroup = Laby.labyAPI().labyNetController().getServerByIp(host);
                serverGroup.ifPresent(group -> group.getAttachment("icon").ifPresent(icon -> builder.largeAsset(DiscordActivity.Asset.ofWeeklyCached(icon.getUrl(), group.getNiceName()))));
            }
        }
        this.displayServerActivity(serverData, builder.build());
    }
    
    public void displayServerActivity(final ServerData serverData, final DiscordActivity activity) {
        if (serverData == null || activity.equals(this.displayedActivity)) {
            return;
        }
        final DiscordActivityUpdateEvent event = new DiscordActivityServerUpdateEvent(serverData, activity);
        if (event.isCancelled()) {
            return;
        }
        this.displayActivity(event.activity());
    }
    
    public void tick() {
        if (this.core == null || this.lastActivity == null) {
            return;
        }
        final long currentTimeMillis = TimeUtil.getMillis();
        if (this.lastRunCallbackTime < currentTimeMillis) {
            try {
                this.core.runCallbacks();
            }
            catch (final GameSDKException exception) {
                DefaultDiscordApp.LOGGER.error("Failed to run Discord callbacks", (Throwable)exception);
            }
            this.lastRunCallbackTime = currentTimeMillis + 500L;
        }
        if (this.updateRequired) {
            this.core.activityManager().updateActivity(this.lastActivity);
            this.updateRequired = false;
        }
    }
    
    public void updateUserAsset(final String userName, final UUID uniqueId) {
        this.userAsset = DiscordActivity.Asset.ofWeeklyCached(String.format(Locale.ROOT, "https://laby.net/texture/profile/head/%s.png?size=32", uniqueId), userName);
        this.displayInternal(this.displayedActivity);
    }
    
    public void displayInternal(final DiscordActivity discordActivity) {
        if (!this.running) {
            return;
        }
        if (this.lastActivity != null) {
            this.lastActivity.close();
        }
        this.displayedActivity = discordActivity;
        this.lastActivity = this.convertDisplayedActivity(discordActivity);
        this.updateRequired = true;
    }
    
    private DiscordActivity.Builder defaultActivityBuilder() {
        return DiscordActivity.builder(this).details("Menu");
    }
    
    private Activity convertDisplayedActivity(final DiscordActivity discordActivity) {
        final Activity activity = new Activity();
        final String details = discordActivity.getDetails();
        activity.setDetails((details == null) ? "" : details);
        final String state = discordActivity.getState();
        activity.setState((state == null) ? "" : state);
        if (discordActivity.getEndTime() != null) {
            activity.timestamps().setEnd(discordActivity.getEndTime());
        }
        else {
            this.startTime = ((discordActivity.getStartTime() == null) ? this.startTime : discordActivity.getStartTime());
            activity.timestamps().setStart(this.startTime);
        }
        this.applyActivityAssets(activity.assets(), discordActivity.getLargeAsset(), discordActivity.getSmallAsset());
        return activity;
    }
    
    private void applyActivityAssets(final ActivityAssets activityAssets, DiscordActivity.Asset largeAsset, DiscordActivity.Asset smallAsset) {
        if (largeAsset == null) {
            largeAsset = this.defaultAsset;
        }
        else {
            smallAsset = this.defaultAsset;
        }
        if (smallAsset == null && Laby.labyAPI().config().other().discord().displayAccount().get()) {
            smallAsset = this.userAsset;
        }
        if (largeAsset.isValid()) {
            activityAssets.setLargeImage(this.getAssetKey(largeAsset));
            activityAssets.setLargeText(largeAsset.getText());
        }
        if (smallAsset != null && smallAsset.isValid()) {
            activityAssets.setSmallImage(this.getAssetKey(smallAsset));
            activityAssets.setSmallText(smallAsset.getText());
        }
    }
    
    private String getAssetKey(final DiscordActivity.Asset asset) {
        final String key = asset.getKey();
        if (!asset.isWeeklyCached() || !key.startsWith("http")) {
            return key;
        }
        try {
            final Calendar calendar = Calendar.getInstance();
            return key + "?cacheWeek=" + calendar.get(1) + "-" + calendar.get(3);
        }
        catch (final Exception ignored) {
            return key;
        }
    }
    
    static {
        LOGGER = DefaultLoggingFactory.createLogger("DiscordRPC Client");
    }
}
