// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.session;

import net.labymod.api.client.component.BaseComponent;
import java.lang.reflect.Type;
import net.labymod.api.util.gson.UUIDTypeAdapter;
import com.google.gson.GsonBuilder;
import net.labymod.api.client.resources.texture.CompletableTextureImage;
import net.labymod.api.client.resources.texture.TextureImage;
import net.labymod.api.Textures;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.session.SessionUpdateEvent;
import net.labymod.api.mojang.texture.MojangTextureType;
import net.labymod.api.mojang.texture.MojangTextureService;
import net.labymod.api.Laby;
import net.labymod.api.client.session.model.MojangTexture;
import java.util.UUID;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.api.util.io.web.result.ResultCallback;
import net.labymod.api.client.resources.texture.GameImage;
import java.io.InputStream;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.mojang.texture.SkinPolicy;
import net.labymod.api.util.io.IOUtil;
import java.io.IOException;
import org.jetbrains.annotations.Nullable;
import java.nio.file.Path;
import java.nio.file.Paths;
import net.labymod.api.util.I18n;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;
import net.labymod.api.notification.Notification;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.util.ThreadSafe;
import java.util.function.Consumer;
import javax.inject.Inject;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.client.session.model.MojangTextureChangedResponse;
import net.labymod.api.util.FileDialogs;
import net.labymod.api.client.resources.texture.GameImageProvider;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.core.labyconnect.DefaultLabyConnect;
import net.labymod.api.notification.NotificationController;
import com.google.gson.Gson;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class ApplyTextureController
{
    private static final Gson GSON;
    private final NotificationController notificationController;
    private final DefaultLabyConnect labyConnect;
    private final ResourceLocationFactory resourceLocationFactory;
    private final LabyAPI labyAPI;
    private final TextureRepository textureRepository;
    private final MinecraftServices minecraftServices;
    private final Minecraft minecraft;
    private final GameImageProvider gameImageProvider;
    private final FileDialogs fileDialogs;
    private MojangTextureChangedResponse userProfile;
    private boolean requestedUserProfile;
    
    @Inject
    public ApplyTextureController(final NotificationController notificationController, final LabyConnect labyConnect, final LabyAPI labyAPI, final ResourceLocationFactory resourceLocationFactory, final TextureRepository textureRepository, final MinecraftServices minecraftServices, final GameImageProvider gameImageProvider, final FileDialogs fileDialogs) {
        this.requestedUserProfile = false;
        final Minecraft minecraft = labyAPI.minecraft();
        this.notificationController = notificationController;
        this.labyConnect = (DefaultLabyConnect)labyConnect;
        this.minecraft = minecraft;
        this.resourceLocationFactory = resourceLocationFactory;
        this.labyAPI = labyAPI;
        this.textureRepository = textureRepository;
        this.minecraftServices = minecraftServices;
        this.gameImageProvider = gameImageProvider;
        this.fileDialogs = fileDialogs;
        this.labyAPI.eventBus().registerListener(this);
    }
    
    public void getProfileAsync(final Consumer<MojangTextureChangedResponse> consumer) {
        if (this.requestedUserProfile) {
            consumer.accept(this.userProfile);
            return;
        }
        this.requestedUserProfile = true;
        Task.builder(() -> {
            final Response<MojangTextureChangedResponse> profileResponse = this.minecraftServices.getProfile();
            profileResponse.ifPresent(response -> {
                this.userProfile = (MojangTextureChangedResponse)profileResponse.get();
                ThreadSafe.executeOnRenderThread(() -> consumer.accept(response));
                return;
            });
            if (profileResponse.hasException()) {
                profileResponse.exception().printStackTrace();
                consumer.accept(null);
            }
        }).build().execute();
    }
    
    public void setCapeAsync(final String capeId, final Consumer<MojangTextureChangedResponse> consumer) {
        Task.builder(() -> {
            Response<MojangTextureChangedResponse> profileResponse;
            if (capeId == null) {
                profileResponse = this.minecraftServices.hideCape();
            }
            else {
                profileResponse = this.minecraftServices.showCape(capeId);
            }
            profileResponse.ifPresent(response -> {
                this.userProfile = (MojangTextureChangedResponse)profileResponse.get();
                consumer.accept(response);
                this.notificationController.push(Notification.builder().title(Component.translatable("labymod.activity.customization.textures.cape.title", new Component[0])).text(Component.translatable("labymod.activity.customization.textures.cape.change.success", new Component[0])).build());
                return;
            });
            if (profileResponse.hasException()) {
                profileResponse.exception().printStackTrace();
                this.notificationController.push(Notification.builder().title(Component.translatable("labymod.activity.customization.textures.cape.title", new Component[0])).text(((BaseComponent<Component>)Component.translatable("labymod.activity.customization.textures.cape.change.success", new Component[0])).append(Component.text(" " + profileResponse.getStatusCode()))).build());
            }
        }).build().execute();
    }
    
    public void requestUploadSkin(final Icon previewIcon, final Runnable callback) {
        final Notification notification = Notification.builder().title(Component.translatable("labymod.activity.skins.apply.title", new Component[0])).text(Component.translatable("labymod.activity.skins.apply.description", new Component[0])).icon(previewIcon).addButton(Notification.NotificationButton.of(Component.translatable("labymod.ui.button.apply", new Component[0]), callback)).duration(300000L).build();
        this.notificationController.push(notification);
    }
    
    public void browseSkinFile(final MinecraftServices.SkinVariant variant) {
        try {
            this.fileDialogs.open(I18n.translate("labymod.activity.skins.choose.title", new Object[0]), Paths.get("/", new String[0]), I18n.translate("labymod.activity.skins.choose.type", new Object[0]), new String[] { "png" }, false, paths -> {
                if (paths.length == 1) {
                    this.uploadSkinFileAsync(paths[0], variant);
                }
            });
        }
        catch (final Exception e) {
            e.printStackTrace();
            this.notificationController.push(Notification.builder().title(Component.text("Error")).text(Component.text("File chooser not supported yet: " + e.getClass().getSimpleName())).build());
        }
    }
    
    public void uploadSkinFileAsync(final Path path) {
        this.uploadSkinFileAsync(path, null);
    }
    
    public void uploadSkinFileAsync(final Path path, @Nullable final MinecraftServices.SkinVariant variant) {
        Task.builder(() -> {
            try {
                this.uploadSkinFile(path, variant);
            }
            catch (final IOException e) {
                e.printStackTrace();
            }
        }).build().execute();
    }
    
    public void uploadSkinFile(final Path path, @Nullable MinecraftServices.SkinVariant variant) throws IOException {
        final InputStream inputStream = IOUtil.newInputStream(path);
        final GameImage image = this.gameImageProvider.getImage(inputStream);
        if (this.isInvalidSkin(image)) {
            return;
        }
        final GameImage imageLegal = SkinPolicy.applyPolicy(image);
        if (variant == null) {
            variant = SkinPolicy.guessVariant(imageLegal);
        }
        final ResourceLocation resourceLocation = ResourceLocation.create(this.labyAPI.getNamespace(this), "skins/upload");
        this.textureRepository.releaseTexture(resourceLocation);
        imageLegal.uploadTextureAt(resourceLocation);
        this.uploadSkinAsync(variant, new MinecraftServices.SkinPayload(imageLegal));
    }
    
    public void uploadSkinAsync(final MinecraftServices.SkinVariant variant, final MinecraftServices.SkinPayload payload, final ResultCallback<MojangTextureChangedResponse> result) {
        Task.builder(() -> result.accept(this.uploadSkin(variant, payload))).build().execute();
    }
    
    public void uploadSkinAsync(final MinecraftServices.SkinVariant variant, final MinecraftServices.SkinPayload payload) {
        Task.builder(() -> this.uploadSkin(variant, payload)).build().execute();
    }
    
    public Result<MojangTextureChangedResponse> uploadSkin(final MinecraftServices.SkinVariant variant, MinecraftServices.SkinPayload payload) {
        try {
            final GameImage image = this.getGameImage(payload);
            if (image == null) {
                return Result.ofException(new IllegalStateException("No image could be created from the payload"));
            }
            if (this.isInvalidSkin(image)) {
                return Result.ofException(new IllegalStateException("This image does not have the correct skin format."));
            }
            payload = new MinecraftServices.SkinPayload(SkinPolicy.applyPolicy(image));
            final Response<MojangTextureChangedResponse> result = this.minecraftServices.changeSkin(variant, payload);
            if (result.hasException()) {
                this.notificationController.push(Notification.builder().title(Component.translatable("labymod.activity.skins.apply.title", new Component[0])).text(Component.translatable("labymod.activity.skins.apply.error", new Component[0])).build());
                return result;
            }
            final MojangTextureChangedResponse response = result.get();
            if (response.getSkins().length > 0) {
                final LabyConnectSession session = this.labyConnect.getSession();
                if (session != null) {
                    session.sendTextureUpdated(response);
                }
                this.notificationController.push(Notification.builder().title(Component.translatable("labymod.activity.skins.apply.title", new Component[0])).text(Component.translatable("labymod.activity.skins.apply.success", new Component[0])).build());
                return result;
            }
            this.notificationController.push(Notification.builder().title(Component.translatable("labymod.activity.skins.apply.title", new Component[0])).text(Component.translatable("labymod.activity.skins.apply.error", new Component[0])).build());
            return Result.ofException(new IllegalStateException("No skins found"));
        }
        catch (final Exception e) {
            e.printStackTrace();
            this.notificationController.push(Notification.builder().title(Component.translatable("labymod.activity.skins.apply.title", new Component[0])).text(Component.translatable("labymod.activity.skins.apply.error", new Component[0])).build());
            return Result.ofException(e);
        }
    }
    
    public void applySkinTexture(final UUID uniqueId, final MojangTexture texture) {
        ThreadSafe.executeOnRenderThread(() -> {
            final MojangTextureService textureService = Laby.references().mojangTextureService();
            textureService.applySkinTexture(uniqueId, texture.getVariant(), texture.getUrl());
        });
    }
    
    public void applyCapeTexture(final UUID uniqueId, final MojangTexture texture) {
        ThreadSafe.executeOnRenderThread(() -> {
            final MojangTextureService textureService = Laby.references().mojangTextureService();
            textureService.applyTexture(uniqueId, MojangTextureType.CAPE, (texture == null) ? null : texture.getUrl());
        });
    }
    
    @Subscribe
    public void onSessionUpdate(final SessionUpdateEvent event) {
        this.userProfile = null;
        this.requestedUserProfile = false;
    }
    
    private boolean isInvalidSkin(final GameImage image) {
        if (!SkinPolicy.isValidFormat(image)) {
            this.notificationController.push(Notification.builder().title(Component.translatable("labymod.activity.skins.apply.title", new Component[0])).text(Component.translatable("labymod.activity.skins.apply.wrong_format", new Component[0])).build());
            return true;
        }
        return false;
    }
    
    @Nullable
    private GameImage getGameImage(final MinecraftServices.SkinPayload payload) throws IOException {
        if (payload.hasGameImage()) {
            return payload.getGameImage();
        }
        final String url = payload.getUrl();
        if (url == null) {
            return null;
        }
        final CompletableTextureImage target = new CompletableTextureImage(new TextureImage(Textures.WHITE));
        this.textureRepository.executeTextureLoader(url, target);
        return target.getCompleted().getGameImage();
    }
    
    static {
        GSON = new GsonBuilder().registerTypeAdapter((Type)UUID.class, (Object)new UUIDTypeAdapter()).create();
    }
}
