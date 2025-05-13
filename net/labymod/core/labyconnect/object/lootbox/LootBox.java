// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.object.lootbox;

import net.labymod.api.util.io.web.request.AbstractRequest;
import net.labymod.api.client.resources.texture.Texture;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.types.InputStreamRequest;
import net.labymod.api.util.io.web.request.Callback;
import net.labymod.core.main.user.shop.item.geometry.BlockBenchLoader;
import java.io.IOException;
import java.io.InputStream;
import net.labymod.core.main.user.shop.item.geometry.GeometryLoader;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.client.resources.CompletableResourceLocation;
import java.util.Locale;
import net.labymod.api.Textures;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Constants;
import net.labymod.api.client.render.model.ModelService;
import net.labymod.api.Laby;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.core.main.user.shop.item.geometry.animation.AnimationLoader;
import org.jetbrains.annotations.Nullable;

public class LootBox
{
    private static final String AVAILABLE_ID = "available";
    private static final String OPENED_ID = "opened";
    private final int type;
    private final String id;
    @Nullable
    private LootBoxModel model;
    @Nullable
    private AnimationLoader animationLoader;
    private final AnimationController availableController;
    private final AnimationController openedController;
    private ModelAnimation availableAnimation;
    private ModelAnimation openedAnimation;
    
    public LootBox(final int type, final String id) {
        this.type = type;
        this.id = id;
        final ModelService modelService = Laby.references().modelService();
        this.availableController = modelService.createAnimationController();
        this.openedController = modelService.createAnimationController();
    }
    
    public void tick() {
        final LootBoxModel model = this.getModel();
        if (model == null) {
            return;
        }
        if (!this.availableController.isPlaying() && this.availableAnimation != null) {
            this.availableController.playNext(this.availableAnimation);
        }
        if (!this.openedController.isPlaying() && this.openedAnimation != null) {
            this.openedController.playNext(this.openedAnimation);
        }
    }
    
    public void loadModel() {
        this.createRequest(Constants.Urls.REMOTE_INCENTIVES_GEOMETRY, this::loadModel);
        this.createRequest(Constants.Urls.REMOTE_INCENTIVES_ANIMATION, this::loadAnimations);
        final ResourceLocation textureLocation = ResourceLocation.create("labymod", "incentives/" + this.type + "/texture.png");
        final CompletableResourceLocation location = Laby.references().textureRepository().getOrRegisterTexture(textureLocation, Textures.WHITE, String.format(Locale.ROOT, Constants.Urls.REMOTE_INCENTIVES_TEXTURE, this.id), finished -> {
            if (this.model != null) {
                this.model.setTextureLocation(textureLocation);
            }
            return;
        });
        location.addCompletableListener(() -> {
            if (this.model != null) {
                this.model.setTextureLocation(textureLocation);
            }
        });
    }
    
    private void loadModel(final Response<WebInputStream> response) {
        if (!response.isPresent()) {
            throw new LootBoxException("No lootbox model for " + this.type + ":" + this.id + " is present! (Status Code: " + response.getStatusCode(), (Throwable)response.exception());
        }
        try (final WebInputStream stream = response.get()) {
            final BlockBenchLoader loader = new GeometryLoader(stream).toBlockBenchLoader();
            this.model = new LootBoxModel(loader.getModel(), loader.getEffects());
        }
        catch (final IOException exception) {
            throw new LootBoxException("An error occurred while loading the lootbox model! (Type: " + this.type + ", Id: " + this.id, (Throwable)exception);
        }
    }
    
    private void loadAnimations(final Response<WebInputStream> response) {
        if (!response.isPresent()) {
            throw new LootBoxException("No lootbox animations for " + this.type + ":" + this.id + " is present! (Status Code: " + response.getStatusCode(), (Throwable)response.exception());
        }
        try (final WebInputStream stream = response.get()) {
            (this.animationLoader = new AnimationLoader(stream)).load();
            this.availableAnimation = this.animationLoader.getAnimation("available");
            this.openedAnimation = this.animationLoader.getAnimation("opened");
        }
        catch (final IOException exception) {
            throw new LootBoxException("An error occurred while loading the lootbox animations! (Type: " + this.type + ", Id: " + this.id, (Throwable)exception);
        }
    }
    
    public void createRequest(final String url, final Callback<WebInputStream> callback) {
        ((AbstractRequest<WebInputStream, R>)((AbstractRequest<T, InputStreamRequest>)Request.ofInputStream()).url(url, new Object[] { this.id })).execute(callback);
    }
    
    public int getType() {
        return this.type;
    }
    
    public String getId() {
        return this.id;
    }
    
    public AnimationController getAnimationController(final boolean available) {
        return available ? this.availableController : this.openedController;
    }
    
    @Nullable
    public LootBoxModel getModel() {
        return this.model;
    }
    
    @Nullable
    public AnimationLoader getAnimationLoader() {
        return this.animationLoader;
    }
    
    public LootBox copy() {
        final LootBox lootBox = new LootBox(this.type, this.id);
        lootBox.model = ((this.model == null) ? null : this.model.copy());
        lootBox.animationLoader = this.animationLoader;
        lootBox.availableAnimation = this.availableAnimation;
        lootBox.openedAnimation = this.openedAnimation;
        return lootBox;
    }
}
