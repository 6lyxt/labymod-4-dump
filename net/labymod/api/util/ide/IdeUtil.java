// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.ide;

import net.labymod.api.loader.MinecraftVersions;
import java.util.HashMap;
import java.time.temporal.Temporal;
import java.time.Duration;
import java.time.Instant;
import net.labymod.api.client.Minecraft;
import net.labymod.api.util.function.ThrowableRunnable;
import org.jetbrains.annotations.ApiStatus;
import java.nio.file.Path;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gfx.pipeline.texture.data.Sprite;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import net.labymod.api.Constants;
import java.util.Locale;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourcesReloadWatcher;
import net.labymod.api.util.function.ThrowableSupplier;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Map;
import net.labymod.api.util.logging.Logging;

public final class IdeUtil
{
    private static final int IDE_CRASH_EXIT_CODE = -69420;
    public static final boolean RUNNING_IN_IDE;
    public static final boolean DUMP_SPRITE_ICONS;
    public static final Logging LOGGER;
    private static final Map<ResourceLocation, GameImage> OLD_SPRITE_SHEETS;
    private static final boolean INCOMPATIBLE_RESOURCE_LOADING;
    private static boolean resourcesLoaded;
    private static boolean alreadyCrashed;
    
    private IdeUtil() {
    }
    
    public static void doPauseOrThrown(final ThrowableSupplier<Throwable, Throwable> throwableSupplier) {
        _doPause(() -> {
            throw (Throwable)throwableSupplier.get();
        });
    }
    
    public static void ensureResourcesLoaded(final ResourceLocation location) {
        if (IdeUtil.DUMP_SPRITE_ICONS) {
            return;
        }
        if (!IdeUtil.RUNNING_IN_IDE || IdeUtil.INCOMPATIBLE_RESOURCE_LOADING || IdeUtil.resourcesLoaded) {
            return;
        }
        crashGame("\"" + String.valueOf(location) + "\" was loaded too early, please use \"" + ResourcesReloadWatcher.class.getName() + "\" to avoid issues in the launcher environment.", (Throwable)new IdeException());
    }
    
    public static void dumpSpriteIcons(String spriteLocation, final Icon icon) {
        if (!IdeUtil.DUMP_SPRITE_ICONS) {
            return;
        }
        String namespace = Laby.labyAPI().addonService().getClassPathAddon();
        if (namespace == null) {
            namespace = "labymod";
        }
        final ResourceLocation location = icon.getResourceLocation();
        Objects.requireNonNull(location, "Icon resource location cannot be null");
        final String locationNamespace = location.getNamespace();
        if (!locationNamespace.equals(namespace)) {
            return;
        }
        final Sprite sprite = icon.sprite();
        final GameImage image = IdeUtil.OLD_SPRITE_SHEETS.computeIfAbsent(location, l -> {
            try {
                return GameImage.IMAGE_PROVIDER.getImage(l.openStream());
            }
            catch (final IOException exception2) {
                throw new UncheckedIOException(exception2);
            }
        });
        final GameImage spriteImage = GameImage.IMAGE_PROVIDER.createImage((int)sprite.getWidth(), (int)sprite.getHeight());
        spriteImage.drawImage(image, 0, 0, (int)sprite.getX(), (int)sprite.getY(), (int)sprite.getWidth(), (int)sprite.getHeight());
        final Theme currentTheme = Laby.references().themeService().currentTheme();
        try {
            spriteLocation = spriteLocation.toLowerCase(Locale.ROOT);
            spriteLocation = spriteLocation.replace(".", "/");
            if (!spriteLocation.startsWith(namespace)) {
                spriteLocation = namespace + "/" + spriteLocation;
            }
            final Path destination = Constants.Files.LABYMOD_DIRECTORY.resolve("debug").resolve("settings_icons").resolve(currentTheme.getId()).resolve(spriteLocation + ".png");
            Files.createDirectories(destination.getParent(), (FileAttribute<?>[])new FileAttribute[0]);
            spriteImage.write("png", destination);
        }
        catch (final IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }
    
    @ApiStatus.Internal
    public static void setResourcesLoaded(final boolean resourcesLoaded) {
        IdeUtil.resourcesLoaded = resourcesLoaded;
    }
    
    public static void doPause(final ThrowableRunnable<Throwable> executor) {
        _doPause(executor);
    }
    
    public static void doPauseOrCrashGame(final String message, final Throwable throwable) {
        final Minecraft minecraft = Laby.labyAPI().minecraft();
        if (minecraft == null) {
            doPauseOrThrown(() -> throwable);
            return;
        }
        _doPause(() -> minecraft.crashGame(message, throwable));
    }
    
    private static void _doPause(final ThrowableRunnable<Throwable> crashExecutor) {
        if (IdeUtil.alreadyCrashed) {
            return;
        }
        boolean shouldCrash = true;
        if (IdeUtil.RUNNING_IN_IDE) {
            final Instant start = Instant.now();
            printBreakpointMessage();
            shouldCrash = (Duration.between(start, Instant.now()).toMillis() < 500L);
        }
        if (!shouldCrash) {
            IdeUtil.alreadyCrashed = false;
            return;
        }
        IdeUtil.alreadyCrashed = true;
        try {
            crashExecutor.run();
        }
        catch (final Throwable throwable) {
            crashGame(throwable.getMessage(), throwable);
        }
    }
    
    private static void printBreakpointMessage() {
        IdeUtil.LOGGER.error("Did you remember to set a breakpoint here?", new Object[0]);
    }
    
    private static void crashGame(String message, final Throwable throwable) {
        final Minecraft minecraft = Laby.labyAPI().minecraft();
        if (minecraft == null) {
            throwable.printStackTrace();
            System.exit(-69420);
            return;
        }
        if (message == null) {
            message = "Unknown Message";
        }
        minecraft.crashGame(message, throwable);
    }
    
    static {
        RUNNING_IN_IDE = Laby.labyAPI().labyModLoader().isLabyModDevelopmentEnvironment();
        DUMP_SPRITE_ICONS = Constants.SystemProperties.getBoolean(Constants.SystemProperties.DUMP_SPRITES);
        LOGGER = Logging.create("IDE");
        OLD_SPRITE_SHEETS = new HashMap<ResourceLocation, GameImage>();
        INCOMPATIBLE_RESOURCE_LOADING = MinecraftVersions.V1_16_5.isCurrent();
    }
    
    private static class IdeException extends RuntimeException
    {
        public IdeException() {
        }
        
        public IdeException(final String message) {
            super(message);
        }
        
        public IdeException(final String message, final Throwable cause) {
            super(message, cause);
        }
        
        public IdeException(final Throwable cause) {
            super(cause);
        }
        
        public IdeException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}
