// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.thirdparty;

import com.google.gson.GsonBuilder;
import java.util.Iterator;
import io.sentry.Attachment;
import java.nio.charset.StandardCharsets;
import io.sentry.protocol.User;
import net.labymod.core.labyconnect.util.Snooper;
import io.sentry.Hint;
import io.sentry.SentryEvent;
import net.labymod.api.BuildData;
import io.sentry.SentryOptions;
import net.labymod.api.client.util.SystemInfo;
import net.labymod.core.main.LabyMod;
import net.labymod.api.models.OperatingSystem;
import io.sentry.Scope;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.nio.file.Path;
import net.labymod.api.configuration.loader.ConfigLoader;
import java.io.Reader;
import com.google.gson.JsonElement;
import java.nio.file.Files;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.core.configuration.labymod.main.DefaultLabyConfig;
import net.labymod.api.configuration.loader.impl.AbstractConfigLoader;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.core.configuration.labymod.LabyConfigProvider;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;
import io.sentry.ScopeCallback;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.LabyScreenAccessor;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import java.util.List;
import net.labymod.api.client.session.Session;
import net.labymod.api.util.UUIDHelper;
import net.labymod.api.Laby;
import java.util.UUID;
import net.labymod.api.util.Pair;
import io.sentry.Sentry;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.loader.DefaultLabyModLoader;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.loader.LabyModLoader;
import com.google.gson.Gson;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.thirdparty.SentryService;

@Singleton
@Implements(SentryService.class)
public final class DefaultSentryService implements SentryService
{
    private static final Gson GSON;
    private final long initializeTime;
    LabyModLoader loader;
    private ScreenInstance previousScreen;
    private ScreenInstance currentScreen;
    
    public DefaultSentryService() {
        this.loader = DefaultLabyModLoader.getInstance();
        this.initializeTime = TimeUtil.getCurrentTimeMillis();
        if (this.loader.isLabyModDevelopmentEnvironment()) {
            return;
        }
        this.initialize();
    }
    
    @Override
    public void capture(final Throwable throwable) {
        if (Sentry.isEnabled()) {
            Sentry.captureException(throwable);
        }
    }
    
    public void initialize() {
        Sentry.init(options -> {
            options.setDsn("https://ee56a4c224624431bf9c659d7e1856de@sentry.laby.tech/14");
            options.setTracesSampleRate(Double.valueOf(1.0));
            options.setEnvironment(BuildData.releaseType());
            options.setBeforeSend((event, hint) -> {
                try {
                    if (!Snooper.isInternalReleaseChannel() && !this.isAnonymousStatisticsEnabled()) {
                        return null;
                    }
                }
                catch (final Exception ex) {}
                if (event.getServerName() != null) {
                    event.setServerName((String)null);
                }
                try {
                    final Pair<String, UUID> currentUser = this.getUser();
                    final User user = new User();
                    user.setId("" + currentUser.getSecond().hashCode());
                    user.setEmail(Snooper.getIdentifier());
                    if (Snooper.isInternalReleaseChannel()) {
                        user.setUsername(currentUser.getSecond().toString());
                    }
                    event.setUser(user);
                }
                catch (final Exception ex2) {}
                try {
                    event.setExtra("screen-current", (Object)this.getScreenName(this.currentScreen));
                    event.setExtra("screen-previous", (Object)this.getScreenName(this.previousScreen));
                }
                catch (final Exception ex3) {}
                try {
                    final long millis = TimeUtil.getCurrentTimeMillis() - this.initializeTime;
                    if (millis < 1000L) {
                        event.setExtra("age", millis + "ms");
                    }
                    else {
                        event.setExtra("age", "" + millis / 1000L);
                    }
                }
                catch (final Exception ex4) {}
                try {
                    final Path path = DefaultLabyModLoader.getInstance().getGameDirectory().resolve("logs/latest.log");
                    final List<String> strings = Files.readAllLines(path, StandardCharsets.UTF_8);
                    final StringBuilder builder = new StringBuilder();
                    for (final String string : strings) {
                        final String censoredLogLine = this.getCensoredLogLine(string);
                        if (censoredLogLine != null) {
                            builder.append(censoredLogLine).append('\n');
                        }
                    }
                    hint.addAttachment(new Attachment(builder.toString().getBytes(), "latest.txt"));
                }
                catch (final Exception ex5) {}
                return event;
            });
        });
        this.configureScope();
    }
    
    private Pair<String, UUID> getUser() {
        final Session session = Laby.labyAPI().minecraft().sessionAccessor().getSession();
        String userName = null;
        UUID uuid = null;
        if (session == null) {
            final List<String> arguments = DefaultLabyModLoader.getInstance().getArguments();
            for (int i = 0; i < arguments.size(); ++i) {
                try {
                    final String argument = arguments.get(i);
                    if (argument.equals("--username")) {
                        userName = arguments.get(i + 1);
                    }
                    else if (argument.equals("--uuid")) {
                        uuid = UUID.fromString(arguments.get(i + 1));
                    }
                }
                catch (final Exception ex) {}
            }
        }
        else {
            userName = session.getUsername();
            uuid = session.getUniqueId();
        }
        if (uuid == null && userName != null) {
            uuid = UUIDHelper.createUniqueId(userName);
        }
        return Pair.of((userName == null) ? "Unknown" : userName, (uuid == null) ? new UUID(0L, 0L) : uuid);
    }
    
    private String getScreenName(final ScreenInstance screen) {
        if (screen == null) {
            return "none";
        }
        if (screen instanceof final ScreenWrapper wrapper) {
            final Object versionedScreen = wrapper.getVersionedScreen();
            if (versionedScreen instanceof final LabyScreenAccessor accessor) {
                return accessor.screen().getClass().getName();
            }
            return wrapper.getVersionedScreen().getClass().getName();
        }
        else {
            if (screen instanceof LabyScreen) {
                return screen.getClass().getName();
            }
            return screen.getClass().getName();
        }
    }
    
    private void configureScope() {
        this.configureScope(scope -> {
            scope.setTag("operating_system", OperatingSystem.getPlatform().getName());
            scope.setTag("mcversion", System.getProperty("net.labymod.running-version", this.loader.version().toString()));
            scope.setTag("version", LabyMod.getInstance().getVersion());
            scope.setTag("launcher", System.getProperty("net.labymod.launcher", System.getProperty("minecraft.launcher.brand", "Unknown")));
            final SystemInfo systemInfo = Laby.references().systemInfo();
            scope.setExtra("processor", systemInfo.processor().name());
        });
    }
    
    private String getCensoredLogLine(String line) {
        if (line.contains("[CHAT]") || line.contains("Setting user: ")) {
            return null;
        }
        line = this.censorServerAddress(line);
        return line;
    }
    
    private String censorServerAddress(final String line) {
        final int connectingTo = line.indexOf("Connecting to");
        if (connectingTo == -1) {
            return line;
        }
        final int nextCommaIndex = line.indexOf(",");
        if (nextCommaIndex == -1) {
            return line;
        }
        final int lastSpaceIndex = line.lastIndexOf(32, nextCommaIndex);
        if (lastSpaceIndex == -1) {
            return line;
        }
        final StringBuilder builder = new StringBuilder();
        builder.append(line, 0, lastSpaceIndex + 1);
        final String address = line.substring(lastSpaceIndex + 1, nextCommaIndex);
        builder.append("*".repeat(address.length() / 2));
        builder.append(address, address.length() / 2, address.length());
        builder.append(line, nextCommaIndex, line.length());
        return builder.toString();
    }
    
    public void configureScope(final ScopeCallback callback) {
        Sentry.configureScope(callback);
    }
    
    @Subscribe
    public void onScreenOpen(final ScreenDisplayEvent event) {
        this.previousScreen = event.getPreviousScreen();
        this.currentScreen = event.getScreen();
    }
    
    private boolean isAnonymousStatisticsEnabled() {
        try {
            final LabyConfigProvider provider = LabyConfigProvider.INSTANCE;
            final LabyConfig config = provider.get();
            if (config == null) {
                final ConfigLoader loader = provider.getLoader();
                final Path file = ((AbstractConfigLoader)loader).getPath(DefaultLabyConfig.class);
                try (final BufferedReader reader = Files.newBufferedReader(file)) {
                    final JsonElement element = (JsonElement)DefaultSentryService.GSON.fromJson((Reader)reader, (Class)JsonElement.class);
                    if (!element.isJsonObject()) {
                        final boolean b = false;
                        if (reader != null) {
                            reader.close();
                        }
                        return b;
                    }
                    final JsonObject object = element.getAsJsonObject();
                    final JsonObject otherObject = object.getAsJsonObject("other");
                    if (otherObject == null) {
                        final boolean b2 = false;
                        if (reader != null) {
                            reader.close();
                        }
                        return b2;
                    }
                    final JsonPrimitive anonymousStatistics = otherObject.getAsJsonPrimitive("anonymousStatistics");
                    if (anonymousStatistics == null) {
                        final boolean b3 = false;
                        if (reader != null) {
                            reader.close();
                        }
                        return b3;
                    }
                    return anonymousStatistics.isBoolean();
                }
            }
            return config.other().anonymousStatistics().get();
        }
        catch (final Exception ignored) {
            return false;
        }
    }
    
    static {
        GSON = new GsonBuilder().create();
    }
}
