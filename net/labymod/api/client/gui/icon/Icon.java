// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.icon;

import java.util.Iterator;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.resources.texture.ThemeTextureLocation;
import net.labymod.api.util.math.vector.FloatVector4;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.render.draw.PlayerHeadRenderer;
import java.util.Optional;
import net.labymod.api.labynet.LabyNetController;
import net.labymod.api.labynet.models.ServerGroup;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.metadata.Metadata;
import java.util.UUID;
import net.labymod.api.mojang.texture.MojangTextureService;
import net.labymod.api.mojang.texture.MojangTextureType;
import java.util.concurrent.atomic.AtomicReference;
import java.util.Objects;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.util.HashUtil;
import java.nio.charset.StandardCharsets;
import net.labymod.api.client.resources.Resources;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.Textures;
import net.labymod.api.client.resources.texture.Texture;
import java.util.function.Consumer;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.Laby;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.pipeline.texture.data.Sprite;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.function.Supplier;
import net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder;

public class Icon
{
    private static final String REMOTE_ICON_PREFIX = "remote_icon/";
    private final RoundedGeometryBuilder roundedGeometryBuilder;
    private Supplier<ResourceLocation> resourceLocationSupplier;
    private boolean playerHead;
    private HatConsumer hatConsumer;
    private final Sprite sprite;
    private int resolutionWidth;
    private int resolutionHeight;
    @Nullable
    private TextureAtlas textureAtlas;
    @Nullable
    private TextureSprite textureSprite;
    @Nullable
    private Supplier<TextureSprite> hoverTextureSprite;
    private float aspectRatio;
    private boolean checkedAspectRatio;
    private int spriteHoverOffsetX;
    private int spriteHoverOffsetY;
    private BorderRadius borderRadius;
    private final String url;
    private boolean blur;
    private boolean noMetadata;
    private final Map<String, Runnable> updateListeners;
    
    protected Icon(final ResourceLocation resourceLocation) {
        this(new Supplier<ResourceLocation>() {
            @Override
            public ResourceLocation get() {
                return resourceLocation;
            }
        }, null);
    }
    
    protected Icon(final Supplier<ResourceLocation> resourceLocationSupplier, final String url) {
        this.sprite = new Sprite();
        this.resolutionWidth = 256;
        this.resolutionHeight = 256;
        this.aspectRatio = 0.0f;
        this.blur = false;
        this.updateListeners = new HashMap<String, Runnable>();
        this.roundedGeometryBuilder = Laby.references().roundedGeometryBuilder();
        this.resourceLocationSupplier = resourceLocationSupplier;
        this.url = url;
    }
    
    private int getSpriteWidth() {
        final float width = this.sprite.getWidth();
        return (width == 0.0f) ? this.resolutionWidth : ((int)width);
    }
    
    private int getSpriteHeight() {
        final float height = this.sprite.getHeight();
        return (height == 0.0f) ? this.resolutionHeight : ((int)height);
    }
    
    public void render(final Stack stack, final float x, final float y, final float size) {
        this.render(stack, x, y, size, size);
    }
    
    public void render(final Stack stack, final float x, final float y, final float size, final boolean hover) {
        this.render(stack, x, y, size, hover, -1);
    }
    
    public void render(final Stack stack, final float x, final float y, final float size, final boolean hover, final int color) {
        this.render(stack, x, y, size, size, hover, color);
    }
    
    public void render(final Stack stack, final float x, final float y, final float width, final float height) {
        this.render(stack, x, y, width, height, false, -1);
    }
    
    public void render(final Stack stack, final Rectangle rectangle) {
        this.render(stack, rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }
    
    public void render(final Stack stack, final float x, final float y, final float width, final float height, final boolean hover, final int color) {
        this.render(stack, x, y, width, height, hover, color, null);
    }
    
    public static Icon url(final String url) {
        return url(url, (Consumer<Texture>)null);
    }
    
    public static Icon url(final String url, final Consumer<Texture> textureConsumer) {
        return url(url, Textures.EMPTY, textureConsumer);
    }
    
    public static Icon url(final String url, final ResourceLocation fallbackLocation) {
        return url(url, fallbackLocation, null);
    }
    
    public static Icon url(final String url, final ResourceLocation fallbackLocation, final Consumer<Texture> textureConsumer) {
        final Resources resources = Laby.references().resources();
        final TextureRepository repository = resources.textureRepository();
        final String path = createLocationPath(url);
        final ResourceLocation resourceLocation = resources.resourceLocationFactory().createLabyMod(path);
        return new Icon(new Supplier<ResourceLocation>() {
            @Override
            public ResourceLocation get() {
                return repository.getOrRegisterTexture(resourceLocation, fallbackLocation, url, textureConsumer).getCompleted();
            }
        }, url);
    }
    
    private static String createLocationPath(final String url) {
        return "remote_icon/" + HashUtil.md5Hex(url.getBytes(StandardCharsets.UTF_8)) + ".png";
    }
    
    public void render(final ResourceRenderContext context, final float x, final float y, final float size) {
        this.render(context, x, y, size, size);
    }
    
    public void render(final ResourceRenderContext context, final float x, final float y, final float size, final boolean hover) {
        this.render(context, x, y, size, size, hover, -1);
    }
    
    public void render(final ResourceRenderContext context, final float x, final float y, final float size, final boolean hover, final int color) {
        this.render(context, x, y, size, size, hover, color);
    }
    
    public void render(final ResourceRenderContext context, final float x, final float y, final float width, final float height) {
        this.render(context, x, y, width, height, false, -1);
    }
    
    public void render(final ResourceRenderContext context, final float x, final float y, final float width, final float height, final boolean hover, final int color) {
        final TextureSprite textureSprite = this.getTextureSprite(hover);
        if (textureSprite == null) {
            final ColorFormat colorFormat = ColorFormat.ARGB32;
            context.blit(x, y, width, height, this.sprite.getX() + (hover ? this.spriteHoverOffsetX : 0), this.sprite.getY() + (hover ? this.spriteHoverOffsetY : 0), (float)this.getSpriteWidth(), (float)this.getSpriteHeight(), (float)this.resolutionWidth, (float)this.resolutionHeight, (color == -1) ? 1.0f : colorFormat.normalizedRed(color), (color == -1) ? 1.0f : colorFormat.normalizedGreen(color), (color == -1) ? 1.0f : colorFormat.normalizedBlue(color), (color == -1) ? 1.0f : colorFormat.normalizedAlpha(color));
        }
        else {
            ResourceRenderContext.ATLAS_RENDERER.blitSprite(context, textureSprite.uv(), textureSprite.scaling(), MathHelper.ceil(x), MathHelper.ceil(y), MathHelper.ceil(width), MathHelper.ceil(height), 0, color);
        }
    }
    
    public Icon setHoverOffset(final int offsetX, final int offsetY) {
        this.spriteHoverOffsetX = offsetX;
        this.spriteHoverOffsetY = offsetY;
        return this;
    }
    
    public Icon setHoverSprite(final Supplier<ResourceLocation> sprite) {
        this.hoverTextureSprite = (() -> this.textureAtlas.findSprite(sprite.get()));
        return this;
    }
    
    public static Icon completable(final CompletableResourceLocation completable) {
        Objects.requireNonNull(completable);
        final Icon icon = new Icon(completable::getCompleted, null);
        registerCompletableListener(icon, completable);
        return icon;
    }
    
    public static Icon completable(final Supplier<CompletableResourceLocation> supplier) {
        final AtomicReference<CompletableResourceLocation> ref = new AtomicReference<CompletableResourceLocation>();
        final AtomicReference<Icon> iconRef = new AtomicReference<Icon>();
        final Icon icon = new Icon(new Supplier<ResourceLocation>() {
            @Override
            public ResourceLocation get() {
                if (ref.get() == null) {
                    ref.set(supplier.get());
                    Icon.registerCompletableListener(iconRef.get(), ref.get());
                }
                return ref.get().getCompleted();
            }
        }, null);
        iconRef.set(icon);
        return icon;
    }
    
    private static void registerCompletableListener(final Icon icon, final CompletableResourceLocation completable) {
        completable.addCompletableListener(new Runnable() {
            @Override
            public void run() {
                final ResourceLocation resourceLocation = icon.resourceLocationSupplier.get();
                if (resourceLocation.metadata().has("width") && resourceLocation.metadata().has("height")) {
                    icon.resolution(resourceLocation.metadata().get("width"), resourceLocation.metadata().get("height"));
                }
            }
        });
    }
    
    public static Icon head(final String name) {
        final MojangTextureService textureService = Laby.references().mojangTextureService();
        final Icon icon = completable(() -> textureService.getTexture(name, MojangTextureType.SKIN));
        icon.playerHead = true;
        icon.noMetadata = true;
        return icon.hatConsumer(() -> true);
    }
    
    public void setResourceLocationSupplier(final Supplier<ResourceLocation> resourceLocationSupplier) {
        this.resourceLocationSupplier = resourceLocationSupplier;
    }
    
    public static Icon head(final UUID uuid) {
        return head(uuid, true);
    }
    
    public static Icon head(final UUID uuid, final boolean shouldRenderHat) {
        final MojangTextureService textureService = Laby.references().mojangTextureService();
        final Icon icon = completable(() -> textureService.getTexture(uuid, MojangTextureType.SKIN));
        icon.playerHead = true;
        icon.noMetadata = true;
        return icon.hatConsumer(() -> shouldRenderHat);
    }
    
    public Icon hatConsumer(final HatConsumer consumer) {
        this.hatConsumer = consumer;
        return this;
    }
    
    public Icon enableHat() {
        return this.hatConsumer(new HatConsumer(this) {
            @Override
            public boolean isWearingHat() {
                return true;
            }
        });
    }
    
    public Icon resourceLocation(final ResourceLocation resourceLocation) {
        this.resourceLocationSupplier = new Supplier<ResourceLocation>(this) {
            @Override
            public ResourceLocation get() {
                return resourceLocation;
            }
        };
        return this;
    }
    
    public Icon aspectRatio(final float aspectRatio) {
        this.aspectRatio = aspectRatio;
        return this;
    }
    
    public Icon spritePosition(final int spriteX, final int spriteY) {
        this.sprite.setPosition((float)spriteX, (float)spriteY);
        return this;
    }
    
    public Sprite sprite() {
        return this.sprite;
    }
    
    public Icon spriteSize(final int spriteWidth, final int spriteHeight) {
        this.sprite.setSize((float)spriteWidth, (float)spriteHeight);
        return this;
    }
    
    public Icon aspectRatio(final int width, final int height) {
        final float aspectRatio = width / (float)height;
        if (this.aspectRatio == aspectRatio) {
            return this;
        }
        this.aspectRatio = aspectRatio;
        this.fireUpdate();
        return this;
    }
    
    @Nullable
    public String getUrl() {
        return this.url;
    }
    
    public float getAspectRatio() {
        if (!this.checkedAspectRatio && this.aspectRatio == 0.0f && !this.noMetadata) {
            final ResourceLocation location = this.getResourceLocation();
            if (location == null) {
                return this.aspectRatio;
            }
            final Metadata metadata = location.metadata();
            if (metadata.has("width") && metadata.has("height")) {
                this.resolution(metadata.get("width"), metadata.get("height"));
            }
            this.checkedAspectRatio = true;
        }
        return this.aspectRatio;
    }
    
    public int getResolutionWidth() {
        return this.resolutionWidth;
    }
    
    @Deprecated
    public ResourceLocation resourceLocation() {
        return this.getResourceLocation();
    }
    
    @Nullable
    public ResourceLocation getResourceLocation() {
        final ResourceLocation location = this.resourceLocationSupplier.get();
        if (location == null) {
            return null;
        }
        if (location.metadata().has("width") && location.metadata().has("height")) {
            this.resolution(location.metadata().get("width"), location.metadata().get("height"));
        }
        return location;
    }
    
    public Icon resolution(final int resolutionWidth, final int resolutionHeight) {
        this.resolutionWidth = resolutionWidth;
        this.resolutionHeight = resolutionHeight;
        this.sprite.setSize((float)resolutionWidth, (float)resolutionHeight);
        this.aspectRatio(resolutionWidth, resolutionHeight);
        return this;
    }
    
    public static Icon head(final GameProfile profile) {
        return head(() -> profile.getTexture(MojangTextureType.SKIN));
    }
    
    public static Icon texture(final ResourceLocation resourceLocation) {
        return new Icon(resourceLocation);
    }
    
    public static Icon texture(final Supplier<ResourceLocation> resourceLocationSupplier) {
        return new Icon(resourceLocationSupplier, null);
    }
    
    public static Icon currentServer() {
        final ServerController serverController = Laby.labyAPI().serverController();
        final ServerData currentServerData = serverController.getCurrentServerData();
        if (currentServerData == null) {
            return defaultServer();
        }
        return server(currentServerData.address().toString());
    }
    
    public static Icon defaultServer() {
        return server("default");
    }
    
    public static Icon server(final String address) {
        final LabyNetController labyNetController = Laby.references().labyNetController();
        final Optional<ServerGroup> server = labyNetController.getServerByIp(address);
        Icon serverIcon = url("mcserver://" + address);
        if (server.isEmpty()) {
            return serverIcon;
        }
        final ServerGroup serverGroup = server.get();
        final Optional<ServerGroup.Attachment> attachment = serverGroup.getAttachment("icon");
        if (attachment.isPresent()) {
            final ServerGroup.Attachment iconAttachment = attachment.get();
            serverIcon = iconAttachment.getIcon();
        }
        return serverIcon;
    }
    
    public static Icon head(final ResourceLocation resourceLocation) {
        final Icon icon = texture(resourceLocation);
        icon.playerHead = true;
        return icon;
    }
    
    public static Icon head(final CompletableResourceLocation resourceLocation) {
        final Icon icon = completable(resourceLocation);
        icon.playerHead = true;
        return icon;
    }
    
    public static Icon head(final Supplier<CompletableResourceLocation> supplier) {
        final Icon icon = completable(supplier);
        icon.playerHead = true;
        icon.noMetadata = true;
        return icon;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Icon icon = (Icon)o;
        return this.playerHead == icon.playerHead && Objects.equals(this.sprite, icon.sprite) && this.resolutionWidth == icon.resolutionWidth && this.resolutionHeight == icon.resolutionHeight && Float.compare(icon.aspectRatio, this.aspectRatio) == 0 && this.spriteHoverOffsetX == icon.spriteHoverOffsetX && this.spriteHoverOffsetY == icon.spriteHoverOffsetY && Objects.equals(this.getResourceLocation(), icon.getResourceLocation());
    }
    
    @Override
    public int hashCode() {
        final ResourceLocation location = this.getResourceLocation();
        int result = this.playerHead ? 1 : 0;
        result = 31 * result + ((location != null) ? location.hashCode() : 0);
        result = 31 * result + ((this.hatConsumer != null) ? this.hatConsumer.hashCode() : 0);
        result = 31 * result + this.sprite.hashCode();
        result = 31 * result + this.resolutionWidth;
        result = 31 * result + this.resolutionHeight;
        result = 31 * result + ((this.aspectRatio != 0.0f) ? Float.floatToIntBits(this.aspectRatio) : 0);
        result = 31 * result + this.spriteHoverOffsetX;
        result = 31 * result + this.spriteHoverOffsetY;
        return result;
    }
    
    public Icon makeBlurry() {
        this.blur = true;
        return this;
    }
    
    public void render(final Stack stack, final float x, final float y, final float width, final float height, final boolean hover, final int color, final Rectangle stencil) {
        final RenderPipeline renderPipeline = Laby.labyAPI().renderPipeline();
        final ResourceRenderer renderer = renderPipeline.resourceRenderer();
        final float spriteX = this.sprite.getX() + (hover ? this.spriteHoverOffsetX : 0);
        final float spriteY = this.sprite.getY() + (hover ? this.spriteHoverOffsetY : 0);
        final ResourceLocation location = this.getResourceLocation();
        float spriteWidth = (float)this.getSpriteWidth();
        float spriteHeight = (float)this.getSpriteHeight();
        if (location == null) {
            return;
        }
        if (this.playerHead) {
            final boolean wearingHat = this.hatConsumer != null && this.hatConsumer.isWearingHat();
            renderer.head().pos(x, y).size(width, height).wearingHat(wearingHat).player(location).color(color).render(stack);
        }
        else {
            final float left = (stencil != null) ? stencil.getLeft() : x;
            final float top = (stencil != null) ? stencil.getTop() : y;
            final float right = (stencil != null) ? stencil.getRight() : (x + width);
            final float bottom = (stencil != null) ? stencil.getBottom() : (y + height);
            final FloatVector4 transform = stack.transformVector(left, top, right, bottom);
            final float translatedLeft = transform.getX();
            final float translatedTop = transform.getY();
            final float translatedRight = transform.getZ();
            final float translatedBottom = transform.getW();
            if (this.borderRadius != null) {
                renderer.roundedData(this.roundedGeometryBuilder.pos(translatedLeft + left, translatedTop + top, translatedRight + right, translatedBottom + bottom).leftTopRadius(this.borderRadius.getLeftTop()).rightTopRadius(this.borderRadius.getRightTop()).rightBottomRadius(this.borderRadius.getRightBottom()).leftBottomRadius(this.borderRadius.getLeftBottom()).build());
            }
            final TextureSprite textureSprite = this.getTextureSprite(hover);
            if (textureSprite == null) {
                final boolean currentBlur = this.blur;
                this.blur = false;
                final ColorFormat colorFormat = ColorFormat.ARGB32;
                renderer.texture(location).pos(x, y).size(width, height).sprite(spriteX, spriteY, spriteWidth, spriteHeight).resolution((float)this.resolutionWidth, (float)this.resolutionHeight).lowerEdgeSoftness(-0.125f).upperEdgeSoftness(0.0f).blur(currentBlur).color((color == -1) ? 1.0f : colorFormat.normalizedRed(color), (color == -1) ? 1.0f : colorFormat.normalizedGreen(color), (color == -1) ? 1.0f : colorFormat.normalizedBlue(color), (color == -1) ? 1.0f : colorFormat.normalizedAlpha(color)).render(stack);
            }
            else {
                final ResourceRenderContext resourceRenderContext = renderPipeline.renderContexts().resourceRenderContext();
                resourceRenderContext.begin(stack, this.getResourceLocation());
                spriteWidth = this.sprite.getWidth();
                spriteHeight = this.sprite.getHeight();
                spriteWidth = ((spriteWidth == 0.0f) ? width : spriteWidth);
                spriteHeight = ((spriteHeight == 0.0f) ? height : spriteHeight);
                ResourceRenderContext.ATLAS_RENDERER.blitSprite(resourceRenderContext, textureSprite.uv(), textureSprite.scaling(), MathHelper.ceil(x), MathHelper.ceil(y), MathHelper.ceil(spriteWidth), MathHelper.ceil(spriteHeight), color);
                resourceRenderContext.uploadToBuffer();
            }
        }
    }
    
    @NotNull
    public static Icon spriteCoordinates(final ThemeTextureLocation texture, final int spriteX, final int spriteY, final int spriteWidth, final int spriteHeight) {
        return sprite(texture, spriteX, spriteY, spriteWidth, spriteHeight, texture.getWidth(), texture.getHeight());
    }
    
    @NotNull
    public static Icon sprite(final ThemeTextureLocation texture, final int slotX, final int slotY, final int width, final int height) {
        return spriteCoordinates(texture, slotX * width, slotY * height, width, height);
    }
    
    @NotNull
    public static Icon sprite(final ThemeTextureLocation texture, final int slotX, final int slotY, final int size) {
        return sprite(texture, slotX, slotY, size, size);
    }
    
    @NotNull
    public static Icon sprite(final ResourceLocation resourceLocation, final int spriteX, final int spriteY, final int spriteWidth, final int spriteHeight, final int resolutionWidth, final int resolutionHeight) {
        final Icon icon = texture(resourceLocation);
        icon.sprite.set((float)spriteX, (float)spriteY, (float)spriteWidth, (float)spriteHeight);
        icon.resolutionWidth = resolutionWidth;
        icon.resolutionHeight = resolutionHeight;
        icon.aspectRatio = spriteWidth / (float)spriteHeight;
        return icon;
    }
    
    @NotNull
    public static Icon sprite(final TextureAtlas atlas, final TextureSprite sprite) {
        return sprite(atlas, sprite, 0.0f, 0.0f);
    }
    
    @NotNull
    public static Icon sprite(final TextureAtlas atlas, final TextureSprite sprite, final float width, final float height) {
        final Icon icon = texture(atlas.resource());
        icon.textureAtlas = atlas;
        icon.textureSprite = sprite;
        icon.resolutionWidth = atlas.getAtlasWidth();
        icon.resolutionHeight = atlas.getAtlasHeight();
        icon.sprite.setSize(width, height);
        return icon;
    }
    
    @NotNull
    public static Icon sprite(final Supplier<ResourceLocation> resourceLocationSupplier, final int spriteX, final int spriteY, final int spriteWidth, final int spriteHeight, final int resolutionWidth, final int resolutionHeight) {
        final Icon icon = texture(resourceLocationSupplier);
        icon.sprite.set((float)spriteX, (float)spriteY, (float)spriteWidth, (float)spriteHeight);
        icon.resolutionWidth = resolutionWidth;
        icon.resolutionHeight = resolutionHeight;
        icon.aspectRatio = spriteWidth / (float)spriteHeight;
        return icon;
    }
    
    @NotNull
    public static Icon sprite8(final ResourceLocation resourceLocation, final int slotX, final int slotY) {
        return sprite(resourceLocation, slotX << 3, slotY << 3, 8, 8, 128, 128);
    }
    
    @NotNull
    public static Icon sprite16(final ResourceLocation resourceLocation, final int slotX, final int slotY) {
        return sprite(resourceLocation, slotX << 4, slotY << 4, 16, 16, 128, 128);
    }
    
    @NotNull
    public static Icon sprite32(final ResourceLocation resourceLocation, final int slotX, final int slotY) {
        return sprite(resourceLocation, slotX << 5, slotY << 5, 32, 32, 128, 128);
    }
    
    public int getResolutionHeight() {
        return this.resolutionHeight;
    }
    
    public void setBorderRadius(final BorderRadius borderRadius) {
        this.borderRadius = borderRadius;
    }
    
    public void setUpdateListener(final String key, final Runnable listener) {
        if (listener != null) {
            this.updateListeners.put(key, listener);
        }
        else {
            this.updateListeners.remove(key);
        }
    }
    
    @Nullable
    public TextureSprite getTextureSprite(final boolean hover) {
        if (hover && this.hoverTextureSprite != null) {
            final TextureSprite sprite = this.hoverTextureSprite.get();
            if (sprite != null) {
                return sprite;
            }
        }
        return this.textureSprite;
    }
    
    private void fireUpdate() {
        for (final Runnable runnable : this.updateListeners.values()) {
            runnable.run();
        }
    }
    
    public interface HatConsumer
    {
        boolean isWearingHat();
    }
}
