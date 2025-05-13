// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.customization;

import net.labymod.api.util.io.web.request.AbstractRequest;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.math.Transformation;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.core.util.camera.CinematicCamera;
import net.labymod.core.util.camera.spline.position.Location;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.event.client.render.world.RenderWorldEvent;
import net.labymod.api.util.math.Quaternion;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.client.options.SkinLayer;
import net.labymod.api.client.resources.texture.GameImage;
import java.io.InputStream;
import java.io.IOException;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.mojang.texture.SkinPolicy;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.types.InputStreamRequest;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.PlayerActivity;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.mojang.model.MojangModelService;
import net.labymod.api.mojang.texture.MojangTextureService;
import net.labymod.api.mojang.texture.MojangTextureType;
import net.labymod.api.Laby;
import java.util.UUID;
import net.labymod.core.client.render.schematic.SchematicRenderer;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.Model;
import net.labymod.core.client.render.model.animation.DefaultAnimationController;
import net.labymod.core.client.render.model.DefaultModel;
import net.labymod.api.labynet.models.textures.Skin;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.core.client.gui.screen.widget.widgets.model.EmoteModelWidget;

@AutoWidget
public class PlayerModelWidget extends EmoteModelWidget
{
    private final FloatMatrix4 modelMatrix;
    private Skin previewedSkin;
    private float maxTranslationX;
    private float maxTranslationY;
    private float maxScale;
    private boolean scrolling;
    private float clickX;
    private long timeLastRendered;
    
    public PlayerModelWidget() {
        super(new DefaultModel(), new DefaultAnimationController(), 16.0f, 32.0f, null, true);
        this.modelMatrix = FloatMatrix4.newIdentity();
        this.maxTranslationX = 0.0f;
        this.maxTranslationY = 0.0f;
        this.maxScale = 1.0f;
        this.scrolling = true;
        this.animationController.onStop(animation -> this.model.reset());
        final DynamicBackgroundController dynamicBackgroundController = LabyMod.references().dynamicBackgroundController();
        final SchematicRenderer renderer = dynamicBackgroundController.getSchematicRenderer();
        if (renderer != null) {
            renderer.registerRenderHook((stack, left, top, right, bottom, partialTicks) -> {
                if (!(!this.isWorldRenderingSupported())) {
                    this.renderInWorld(stack, partialTicks);
                }
            });
        }
    }
    
    public void setRenderCosmetics(final boolean renderCosmetics) {
        this.cosmetics = renderCosmetics;
    }
    
    public boolean isRenderingCosmetics() {
        return this.cosmetics;
    }
    
    public void stopEmote(final EmoteItem condition) {
        if (condition != null && condition != this.emoteItem) {
            return;
        }
        super.stopEmote();
    }
    
    @Override
    public void tick() {
        super.tick();
        if (this.emoteItem != null && !this.animationController.isPlaying()) {
            this.animationController.playNext(this.emoteItem.getStartAnimation());
        }
    }
    
    public void setModel(final UUID uniqueId) {
        final MojangTextureService textureService = Laby.references().mojangTextureService();
        final MojangModelService modelService = Laby.references().mojangModelService();
        textureService.getTexture(uniqueId, MojangTextureType.SKIN, texture -> {
            final MinecraftServices.SkinVariant variant = textureService.getVariant(texture);
            final Model model = modelService.getPlayerModel(variant, texture);
            this.setModel(model);
            return;
        });
        this.previewedSkin = null;
    }
    
    public boolean isModified() {
        return this.previewedSkin != null;
    }
    
    public void setModel(final Skin skin) {
        this.previewedSkin = skin;
        final String imageHash = skin.getImageHash();
        final CompletableResourceLocation cachedResourceLocation = PlayerActivity.SKIN_CACHE.get(imageHash);
        if (cachedResourceLocation != null) {
            this.setModel(cachedResourceLocation);
            return;
        }
        ((AbstractRequest<WebInputStream, R>)((AbstractRequest<T, InputStreamRequest>)((AbstractRequest<T, InputStreamRequest>)Request.ofInputStream()).url(skin.getDownloadUrl(), new Object[0])).async()).execute(response -> {
            if (!response.isPresent()) {
                this.previewedSkin = null;
            }
            else {
                final InputStream inputStream = ((WebInputStream)response.get()).getInputStream();
                try {
                    final GameImage gameImage = Laby.references().gameImageProvider().getImage(inputStream);
                    final GameImage gameImage2 = SkinPolicy.applyPolicy(gameImage, true);
                    final ResourceLocation resourceLocation = ResourceLocation.create("labymod", "skin/" + imageHash);
                    resourceLocation.metadata().set("variant", skin.skinVariant().getId());
                    final CompletableResourceLocation completable = new CompletableResourceLocation(resourceLocation);
                    gameImage2.uploadTextureAt(resourceLocation);
                    this.labyAPI.minecraft().executeOnRenderThread(() -> {
                        PlayerActivity.SKIN_CACHE.put(imageHash, completable);
                        this.setModel(completable);
                    });
                }
                catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public void setModel(final CompletableResourceLocation resourceLocation) {
        final MojangModelService modelService = Laby.references().mojangModelService();
        final MojangTextureService textureService = Laby.references().mojangTextureService();
        this.setModel(modelService.getPlayerModel(textureService.getVariant(resourceLocation.getCompleted()), resourceLocation.getCompleted()));
        resourceLocation.addCompletableListener(() -> this.setModel(modelService.getPlayerModel(textureService.getVariant(resourceLocation.getCompleted()), resourceLocation.getCompleted())));
    }
    
    @Override
    public void setModel(final Model model) {
        super.setModel(model);
        this.update();
    }
    
    public void setMaxScale(final float maxScale) {
        this.maxScale = maxScale;
    }
    
    public void setMaxTranslation(final float maxTranslationX, final float maxTranslationY) {
        this.maxTranslationX = maxTranslationX;
        this.maxTranslationY = maxTranslationY;
    }
    
    public void setScrolling(final boolean scrolling) {
        this.scrolling = scrolling;
    }
    
    public void update() {
        final MinecraftOptions options = this.labyAPI.minecraft().options();
        final int value = options.getModelParts();
        boolean changedLayer = false;
        for (final SkinLayer layer : SkinLayer.layers()) {
            final ModelPart part = this.model.getPart(layer.getResourceId());
            if (part != null) {
                final boolean enabled = layer.isEnabled(value);
                if (enabled != part.isVisible()) {
                    part.setVisible(enabled);
                    changedLayer = true;
                }
            }
        }
        if (changedLayer) {
            this.rebuildModel();
        }
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        if (this.isDragging()) {
            super.rotation.set(0.0f, (this.clickX - context.mouse().getX()) / 50.0f, 0.0f);
        }
        if (!DynamicBackgroundController.isEnabled() || !this.isWorldRenderingSupported()) {
            super.renderWidget(context);
        }
        if (this.isVisible()) {
            this.timeLastRendered = TimeUtil.getMillis();
        }
    }
    
    private void renderInWorld(final Stack stack, final float partialTicks) {
        final RenderPipeline legacyPipeline = Laby.labyAPI().renderPipeline();
        final GFXRenderPipeline pipeline = Laby.labyAPI().gfxRenderPipeline();
        this.modelMatrix.identity();
        final RenderEnvironmentContext environmentContext = pipeline.renderEnvironmentContext();
        final DynamicBackgroundController controller = LabyMod.references().dynamicBackgroundController();
        final Location entityPosition = DynamicBackgroundController.POS_PLAYER_ENTITY;
        final long timePassedSinceLastRender = TimeUtil.getMillis() - this.timeLastRendered;
        final long timePassedSinceLastInitialize = TimeUtil.getMillis() - this.getLastInitialTime();
        final float focusYaw = (float)(this.rotation.getY() % 6.283185307179586);
        final float opacity = MathHelper.clamp(timePassedSinceLastInitialize / 300.0f, 0.0f, 1.0f) - MathHelper.clamp((timePassedSinceLastRender - 100L) / 300.0f, 0.0f, 1.0f);
        if (opacity <= 0.0f) {
            return;
        }
        this.modelMatrix.multiply(Quaternion.fromEuler((float)MathHelper.toRadiansDouble(entityPosition.getPitch()), (float)MathHelper.toRadiansDouble(entityPosition.getYaw()) - focusYaw, (float)MathHelper.toRadiansDouble(entityPosition.getRoll())));
        this.modelMatrix.translate((float)entityPosition.getX(), (float)entityPosition.getY(), (float)entityPosition.getZ());
        final float scaleOffset = 1.0f - this.scale.getX();
        final CinematicCamera camera = controller.getSchematicRenderer().camera();
        final Location modifier = camera.positionModifier(1);
        final int windowHeight = this.labyAPI.minecraft().minecraftWindow().getScaledHeight();
        final float windowScaleOffset = (1.8f - windowHeight / 250.0f) / 2.0f;
        modifier.setX(-scaleOffset * -0.6f + this.translation.getX() / 13.0f + windowScaleOffset);
        modifier.setY(-this.translation.getY() / ((this.translation.getY() < 0.0f) ? 20.0f : 13.0f));
        modifier.setZ(-scaleOffset * 1.4f);
        modifier.setPitch(-this.translation.getY());
        modifier.setYaw(0.0);
        modifier.setRoll(0.0);
        this.animationController.applyAnimation(this.model, new String[0]);
        pipeline.matrixStorage().setModelViewMatrix(this.modelMatrix, 4);
        controller.renderModel(this.model, opacity, false);
        stack.push();
        stack.identity();
        stack.multiply(camera.viewMatrix());
        stack.multiply(this.modelMatrix);
        stack.scale(-1.0f, -1.0f, 1.0f);
        final boolean prevScreenContext = environmentContext.isScreenContext();
        environmentContext.setScreenContext(false);
        legacyPipeline.setAlpha(opacity);
        this.setForceProjectionMatrix(false);
        this.renderModelAttachments(stack, 15728880, partialTicks);
        Laby.fireEvent(new RenderWorldEvent(stack, camera, partialTicks));
        environmentContext.setScreenContext(prevScreenContext);
        legacyPipeline.setAlpha(1.0f);
        stack.pop();
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        this.clickX = (float)(int)(mouse.getXDouble() + super.rotation.getY() * 50.0f);
        return super.mouseClicked(mouse, mouseButton);
    }
    
    @Override
    public boolean mouseScrolled(final MutableMouse mouse, final double scrollDelta) {
        if (this.scrolling) {
            final float delta = (float)scrollDelta / 15.0f;
            final float scale = MathHelper.clamp(super.scale.getX() + delta, 1.0f, this.maxScale);
            super.scale.set(scale, scale, scale);
            final float progress = (this.maxScale == 1.0f) ? 0.0f : ((scale - 1.0f) / (this.maxScale - 1.0f));
            super.translation.setX(this.maxTranslationX * progress);
            super.translation.setY(this.maxTranslationY * progress);
        }
        return super.mouseScrolled(mouse, scrollDelta);
    }
    
    protected void lookAt(final float posX, final float posY) {
        final Bounds bounds = this.bounds();
        final float centerX = bounds.getCenterX();
        final float centerY = bounds.getTop() + 25.0f;
        float x = centerX - posX;
        float y = centerY - posY;
        final double rotationX = 3.141592653589793 - Math.abs(MathHelper.wrapRadians(super.rotation.getX()) - 3.141592653589793);
        final double rotationY = 3.141592653589793 - Math.abs(MathHelper.wrapRadians(super.rotation.getY()) - 3.141592653589793);
        final double rotationZ = 3.141592653589793 - Math.abs(MathHelper.wrapRadians(super.rotation.getZ()) - 3.141592653589793);
        final float length = (float)(rotationX * rotationX + rotationY * rotationY + rotationZ * rotationZ) * 20.0f;
        x /= length + 1.0f;
        y /= length + 1.0f;
        final Transformation body = this.model.getPart("body").getAnimationTransformation();
        body.setRotationY(MathHelper.toRadiansFloat((float)(Math.atan(x / 40.0f) * 20.0)));
        body.setRotationX(MathHelper.toRadiansFloat(-(float)Math.atan(y / 40.0f) * 2.0f));
        final Transformation head = this.model.getPart("head").getAnimationTransformation();
        head.setRotationX(MathHelper.toRadiansFloat(-(float)Math.atan(y / 40.0f) * 20.0f));
        head.setRotationY(MathHelper.toRadiansFloat((float)Math.atan(x / 40.0f) * 40.0f));
        final float timeAlive = TimeUtil.convertMillisecondsToTicks(TimeUtil.getMillis() - this.getLastInitialTime());
        final Transformation rightArm = this.model.getPart("right_arm").getAnimationTransformation();
        rightArm.setRotationX((float)(Math.sin(timeAlive * 0.067) * 0.05));
        rightArm.setRotationZ((float)(Math.cos(timeAlive * 0.09) * 0.05 + 0.05));
        final Transformation leftArm = this.model.getPart("left_arm").getAnimationTransformation();
        leftArm.setRotationX((float)(-Math.sin(timeAlive * 0.067) * 0.05));
        leftArm.setRotationZ((float)(-(Math.cos(timeAlive * 0.09) * 0.05 + 0.05)));
    }
    
    public Skin getPreviewedSkin() {
        return this.previewedSkin;
    }
    
    public boolean isWorldRenderingSupported() {
        return false;
    }
}
