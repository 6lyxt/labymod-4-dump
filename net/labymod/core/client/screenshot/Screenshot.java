// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.screenshot;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.core.labynet.insight.controller.InsightUploader;
import java.util.function.Consumer;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import net.labymod.api.notification.NotificationController;
import net.labymod.api.Textures;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.notification.Notification;
import net.labymod.api.util.io.IOUtil;
import java.nio.file.Files;
import net.labymod.api.client.resources.texture.GameImageTexture;
import net.labymod.api.client.resources.Resources;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.resources.texture.GameImage;
import java.io.InputStream;
import net.labymod.api.client.resources.texture.Texture;
import java.util.UUID;
import java.util.Locale;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import net.labymod.api.Laby;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import java.io.IOException;
import net.labymod.core.client.screenshot.meta.ScreenshotMetaProvider;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.screenshot.meta.ScreenshotMeta;
import java.nio.file.Path;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.resources.texture.GameImageProvider;

public class Screenshot
{
    private static final GameImageProvider IMAGE_PROVIDER;
    private static final Logging LOGGER;
    private final Path path;
    private ScreenshotMeta meta;
    private ResourceLocation resourceLocation;
    private boolean loaded;
    private Icon icon;
    private ContextMenu contextMenu;
    
    public Screenshot(final Path path) {
        this.loaded = false;
        this.path = path;
    }
    
    public Screenshot(final Path path, final ScreenshotMeta meta) {
        this.loaded = false;
        this.path = path;
        this.meta = meta;
    }
    
    public void initialize(final ScreenshotMetaProvider metaProvider) {
        try {
            this.meta = metaProvider.provide(this.path);
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
        this.contextMenu = new ContextMenu().with(ContextMenuEntry.builder().text(Component.translatable("labymod.activity.screenshotBrowser.context.open", new Component[0])).clickHandler(entry -> {
            this.openInSystem();
            return true;
        }).build());
        if (OperatingSystem.getPlatform() == OperatingSystem.WINDOWS) {
            this.contextMenu.with(ContextMenuEntry.builder().text(Component.translatable("labymod.activity.screenshotBrowser.context.copy", new Component[0])).clickHandler(entry -> {
                this.copyToClipboard();
                return true;
            }).build());
        }
        this.contextMenu.with(ContextMenuEntry.builder().text(Component.translatable("labymod.activity.screenshotBrowser.context.upload", new Component[0])).clickHandler(entry -> {
            this.upload().thenAccept(url -> {
                if (url == null) {
                    return;
                }
                else {
                    OperatingSystem.getPlatform().openUrl(url);
                    Laby.labyAPI().minecraft().chatExecutor().copyToClipboard(url);
                    return;
                }
            });
            return true;
        }).build()).with(ContextMenuEntry.builder().text(Component.translatable("labymod.ui.button.delete", new Component[0])).clickHandler(entry -> {
            PopupWidget.builder().title(Component.translatable("labymod.activity.screenshotBrowser.viewer.delete.warning", new Component[0]).arguments(Component.text(this.getFileName()))).confirmCallback(() -> {
                if (this.delete()) {
                    final ScreenshotBrowser browser = LabyMod.references().screenshotBrowser();
                    browser.removeScreenshot(this);
                }
                return;
            }).build().displayInOverlay();
            return true;
        }).build());
    }
    
    public void load(final QualityType quality) throws IOException {
        this.load(quality, null);
    }
    
    public void load(final QualityType quality, final Runnable callback) throws IOException {
        this.ensureFreeSpace();
        final InputStream inputStream = this.newInputStream();
        try {
            final GameImage image = Screenshot.IMAGE_PROVIDER.getImage(inputStream);
            final float aspectRatio = image.getWidth() / (float)image.getHeight();
            final boolean isRaw = quality == QualityType.RAW;
            GameImage scaledImage = image;
            if (!isRaw) {
                try {
                    scaledImage = image.scale(quality.getWidth(), quality.getHeight(aspectRatio));
                    image.close();
                }
                catch (final Exception e) {
                    Screenshot.LOGGER.info("Could not scale down " + this.path.getFileName().toString() + " (from " + image.getWidth() + "x" + image.getHeight() + " to " + quality.getWidth() + "x" + quality.getHeight(aspectRatio), new Object[0]);
                    e.printStackTrace();
                }
            }
            final ResourceLocation previousResource = this.resourceLocation;
            final LabyAPI api = Laby.labyAPI();
            final String path = String.format(Locale.ROOT, "screenshot/%s", this.getMeta().getIdentifier());
            this.resourceLocation = api.renderPipeline().resources().resourceLocationFactory().create(api.getNamespace(this), UUID.nameUUIDFromBytes(path.getBytes()).toString());
            (this.icon = Icon.texture(this.resourceLocation)).resolution(scaledImage.getWidth(), scaledImage.getHeight());
            if (previousResource != null && this.loaded) {
                Laby.labyAPI().renderPipeline().resources().textureRepository().releaseTexture(previousResource);
            }
            final Resources resources = api.renderPipeline().resources();
            final GameImageTexture texture = resources.gameImageTextureFactory().create(this.resourceLocation, scaledImage);
            resources.textureRepository().register(this.resourceLocation, texture, () -> {
                this.loaded = true;
                if (callback != null) {
                    callback.run();
                }
                return;
            });
            if (inputStream != null) {
                inputStream.close();
            }
        }
        catch (final Throwable t) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (final Throwable exception) {
                    t.addSuppressed(exception);
                }
            }
            throw t;
        }
    }
    
    private void ensureFreeSpace() {
        try {
            final Runtime runtime = Runtime.getRuntime();
            for (long freeMemory = runtime.freeMemory(), requiredSpace = Files.size(this.path); freeMemory < requiredSpace; freeMemory = runtime.freeMemory()) {
                System.gc();
                Thread.sleep(500L);
            }
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }
    
    public void unload() {
        if (this.resourceLocation == null) {
            return;
        }
        Laby.labyAPI().renderPipeline().resources().textureRepository().releaseTexture(this.resourceLocation);
        this.loaded = false;
    }
    
    public void updateQuality(final QualityType quality) {
        try {
            this.load(quality);
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }
    
    public void openInSystem() {
        OperatingSystem.getPlatform().openFile(IOUtil.toFile(this.path));
    }
    
    public void copyToClipboard() {
        try (final InputStream stream = this.newInputStream()) {
            final GameImage image = Screenshot.IMAGE_PROVIDER.getImage(stream);
            Laby.labyAPI().operatingSystemAccessor().copyToClipboard(image);
        }
        catch (final Throwable e) {
            e.printStackTrace();
            final NotificationController notifications = LabyMod.references().notificationController();
            notifications.push(Notification.builder().title(((BaseComponent<Component>)Component.text(e.getClass().getSimpleName())).color(NamedTextColor.RED)).icon(Textures.SpriteCommon.PICTURE).text(Component.text(e.getMessage())).type(Notification.Type.SYSTEM).build());
        }
    }
    
    public CompletableFuture<String> upload() {
        if (!this.getMeta().hasInsight()) {
            return CompletableFuture.completedFuture((String)null);
        }
        final CompletableFuture<String> future = new CompletableFuture<String>();
        final InsightUploader insightUploader;
        final InsightUploader uploader = insightUploader = LabyMod.references().insightUploader();
        final Path path = this.path;
        final CompletableFuture<String> obj = future;
        Objects.requireNonNull(obj);
        insightUploader.uploadAsync(path, (Consumer<String>)obj::complete, e -> {
            if (e == null) {
                future.complete(null);
                return;
            }
            else {
                e.printStackTrace();
                final NotificationController notifications = LabyMod.references().notificationController();
                notifications.push(Notification.builder().title(((BaseComponent<Component>)Component.translatable("labymod.activity.screenshotBrowser.viewer.upload.error", new Component[0])).color(NamedTextColor.RED)).icon(Textures.SpriteCommon.PICTURE).text(Component.text(e.getMessage())).type(Notification.Type.SYSTEM).build());
                future.completeExceptionally(e);
                return;
            }
        });
        return future;
    }
    
    public boolean exists() {
        return IOUtil.exists(this.path);
    }
    
    public boolean delete() {
        try {
            Files.delete(this.path);
            LabyMod.references().screenshotBrowser().removeScreenshot(this);
            return true;
        }
        catch (final Throwable e) {
            e.printStackTrace();
            final NotificationController notifications = LabyMod.references().notificationController();
            notifications.push(Notification.builder().title(((BaseComponent<Component>)Component.text(e.getClass().getSimpleName())).color(NamedTextColor.RED)).icon(Textures.SpriteCommon.PICTURE).text(Component.text(e.getMessage())).type(Notification.Type.SYSTEM).build());
            return false;
        }
    }
    
    public boolean isLoaded() {
        return this.loaded;
    }
    
    public Path getPath() {
        return this.path;
    }
    
    public ScreenshotMeta getMeta() {
        return this.meta;
    }
    
    public String getFileName() {
        return this.path.getFileName().toString();
    }
    
    public boolean isValid() {
        return this.path.getFileName().toString().endsWith(".png");
    }
    
    public ResourceLocation getResourceLocation() {
        return this.resourceLocation;
    }
    
    public InputStream newInputStream() throws IOException {
        return IOUtil.newInputStream(this.path);
    }
    
    public GameImage asGameImage() throws IOException {
        return Screenshot.IMAGE_PROVIDER.getImage(this.newInputStream());
    }
    
    public Icon getIcon() {
        return this.icon;
    }
    
    public float getAspectRatio() {
        return 1.7777778f;
    }
    
    public ContextMenu getContextMenu() {
        return this.contextMenu;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Screenshot)) {
            return false;
        }
        final Screenshot other = (Screenshot)obj;
        if (other.meta == null || this.meta == null) {
            return this.path.equals(other.path);
        }
        return this.meta.getIdentifier().equals(other.meta.getIdentifier());
    }
    
    static {
        IMAGE_PROVIDER = Laby.references().gameImageProvider();
        LOGGER = Logging.create(Screenshot.class);
    }
    
    public enum QualityType
    {
        LOW(128), 
        MEDIUM(512), 
        HIGH(2048), 
        RAW(-1);
        
        private final int width;
        
        private QualityType(final int width) {
            this.width = width;
        }
        
        public int getWidth() {
            return this.width;
        }
        
        public int getHeight(final float aspectRatio) {
            return (int)(this.width / aspectRatio);
        }
    }
}
