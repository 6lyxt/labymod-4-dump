// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.background;

import net.labymod.core.client.render.schematic.lightning.legacy.LegacyLightEngine;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import net.labymod.api.property.Property;
import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.core.main.LabyMod;
import net.labymod.core.client.gui.background.position.PositionTransition;
import net.labymod.api.client.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.client.gfx.pipeline.buffer.BufferState;
import net.labymod.api.client.gfx.pipeline.buffer.renderer.RenderedBuffer;
import net.labymod.api.client.render.model.ModelUploader;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.gui.screen.activity.ActivityController;
import net.labymod.core.test.cave.BackgroundWorldTestActivity;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.core.client.gui.screen.activity.activities.menu.MainMenuActivity;
import net.labymod.api.util.math.vector.FloatVector4;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.event.client.lifecycle.GameShutdownEvent;
import net.labymod.api.event.labymod.config.SettingUpdateEvent;
import net.labymod.api.event.client.gui.window.WindowResizeEvent;
import net.labymod.api.event.client.gui.screen.ScreenResizeEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.event.client.lifecycle.GameFpsLimitEvent;
import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;
import net.labymod.core.util.camera.CinematicCamera;
import net.labymod.api.client.gfx.pipeline.renderer.immediate.ImmediateRenderer;
import net.labymod.core.client.render.schematic.vertex.SchematicShaderPrograms;
import net.labymod.api.client.gfx.pipeline.program.RenderPrograms;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.ModelService;
import net.labymod.api.client.gfx.pipeline.post.PostProcessorLoader;
import net.labymod.api.client.gfx.pipeline.post.PostEffects;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.Textures;
import net.labymod.api.Constants;
import net.labymod.api.client.gfx.target.RenderTargetAttachment;
import net.labymod.api.client.gfx.target.attachment.Depth24Stencil8RenderTargetAttachment;
import net.labymod.core.client.gui.background.title.TitleScreenLogoListener;
import net.labymod.api.Laby;
import java.util.Calendar;
import java.util.Random;
import net.labymod.api.client.gfx.pipeline.post.PostProcessor;
import net.labymod.core.main.user.shop.item.geometry.animation.AnimationLoader;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.Model;
import net.labymod.core.client.render.schematic.Schematic;
import net.labymod.core.client.gui.background.position.ScreenPositionRegistry;
import net.labymod.core.client.render.schematic.SchematicRenderer;
import net.labymod.api.client.gfx.target.RenderTarget;
import net.labymod.api.configuration.labymod.main.laby.appearance.DynamicBackgroundConfig;
import net.labymod.api.LabyAPI;
import net.labymod.core.util.camera.spline.position.Location;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class DynamicBackgroundController
{
    private static final Logging LOGGER;
    public static final CubicBezier OPENER_CURVE;
    public static final Location POS_OPENER_START;
    public static final Location POS_OPENER_TRANSFER;
    public static final Location POS_TITLE_SCREEN;
    public static final Location CUSTOMIZATION_PLAYER_CAMERA;
    public static final Location SHOP_PLAYER_CAMERA;
    public static final Location POS_PLAYER_ENTITY;
    private static final String ANIMATION_OPENING = "opening";
    private static final float BLUR_STRENGTH = 0.75f;
    private static final boolean INVERSE_SNOW = true;
    private final LabyAPI labyAPI;
    private final DynamicBackgroundConfig config;
    private final RenderTarget schematicTarget;
    private SchematicRenderer renderer;
    private ScreenPositionRegistry screenPositionRegistry;
    private Schematic schematic;
    private Model entityModel;
    private AnimationController entityAnimationController;
    private AnimationLoader entityAnimationLoader;
    private DefaultModelVertexWriter modelVertexWriter;
    private boolean loaded;
    private boolean openingPlayed;
    private boolean wind;
    private float timePassed;
    private long lastTickTime;
    private int prevMouseX;
    private int prevMouseY;
    private long lastTimeMouseMoved;
    private boolean crashed;
    private PostProcessor postProcessor;
    private final float[] snowXOffsets;
    private final float[] snowZOffsets;
    private final Random random;
    
    public DynamicBackgroundController() {
        this.snowXOffsets = new float[1024];
        this.snowZOffsets = new float[1024];
        this.random = new Random();
        final Calendar calendar = Calendar.getInstance();
        final int month = calendar.get(2) + 1;
        final int day = calendar.get(5);
        final int year = calendar.get(1);
        final boolean showSnowflakes = year == 2025 && month == 2 && day >= 10 && day <= 24;
        this.labyAPI = Laby.labyAPI();
        this.labyAPI.eventBus().registerListener(this);
        this.labyAPI.eventBus().registerListener(new TitleScreenLogoListener(this));
        this.config = this.labyAPI.config().appearance().dynamicBackground();
        this.config.brightness().addChangeListener((type, oldValue, newValue) -> {
            if (this.renderer != null) {
                this.renderer.setDirty();
            }
            return;
        });
        (this.schematicTarget = new RenderTarget()).addColorAttachment(0);
        this.schematicTarget.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        this.schematicTarget.addAttachment(new Depth24Stencil8RenderTargetAttachment());
        try {
            final ModelService modelService = Laby.references().modelService();
            (this.entityModel = modelService.loadBlockBenchModel(Constants.Resources.CAVE_ENTITIES)).setTextureLocation(Textures.Splash.CAVE_ENTITIES);
            (this.entityAnimationLoader = new AnimationLoader(Constants.Resources.CAVE_ENTITIES_ANIMATION)).load();
            (this.entityAnimationController = modelService.createAnimationController()).tickProvider(() -> TimeUtil.convertMillisecondsToTicks((long)this.timePassed));
            this.schematic = new Schematic(Constants.Resources.CAVE);
            (this.renderer = new SchematicRenderer(this.schematic)).registerRenderHook((stack, left, top, right, bottom, partialTicks) -> this.renderEntities());
            this.renderer.camera().teleport(DynamicBackgroundController.POS_OPENER_START);
            if (showSnowflakes) {
                this.renderer.registerRenderHook((stack, left, top, right, bottom, partialTicks) -> {
                    if (this.config.snowflakes().get()) {
                        this.renderSnow(stack, partialTicks);
                    }
                    return;
                });
            }
            this.screenPositionRegistry = new ScreenPositionRegistry(this.renderer.camera());
            this.modelVertexWriter = new DefaultModelVertexWriter(this.labyAPI, this.schematic);
            this.screenPositionRegistry.register();
            PostProcessorLoader.loadDynamic(this.schematicTarget, PostEffects.MENU_BLUR, processor -> (this.postProcessor = processor).setCustomPostPassProcessor((name, program, time) -> {
                if (name.equals("main")) {
                    program.setVec2("BlurDirection", 1.0f, 0.0f);
                }
                else {
                    program.setVec2("BlurDirection", 0.0f, 1.0f);
                }
                program.setFloat("Radius", this.labyAPI.config().appearance().dynamicBackground().blur().get());
                program.setFloat("Progress", 1.0f);
            }));
            for (int x = 0; x < 32; ++x) {
                for (int z = 0; z < 32; ++z) {
                    final float strengthX = (float)(z - 16);
                    final float strengthZ = (float)(x - 16);
                    final float distance = (float)Math.sqrt(strengthX * strengthX + strengthZ * strengthZ);
                    this.snowXOffsets[x << 5 | z] = -strengthZ / distance;
                    this.snowZOffsets[x << 5 | z] = strengthX / distance;
                }
            }
            this.wind = true;
            this.loaded = true;
        }
        catch (final Throwable throwable) {
            this.onCrash(throwable);
        }
    }
    
    private void renderSnow(final Stack stack, final float partialTicks) {
        final CinematicCamera camera = this.renderer.camera();
        final Location location = camera.location();
        final int cameraX = MathHelper.floor(location.getX());
        final int cameraY = MathHelper.floor(location.getY());
        final int cameraZ = MathHelper.floor(location.getZ());
        final double timePassed = this.timePassed / 50.0;
        final int maxDistance = 10;
        float strength = 1.0f;
        final int titleSeed = 3;
        final float distanceToTitlePos = (float)Math.abs(location.getZ() - DynamicBackgroundController.POS_TITLE_SCREEN.getZ());
        strength *= 1.0f - MathHelper.clamp(distanceToTitlePos / 60.0f, 0.0f, 1.0f);
        final BufferBuilder builder = this.labyAPI.gfxRenderPipeline().getDefaultBufferBuilder();
        builder.begin(RenderPrograms.getSchematic(Textures.Splash.SNOW), () -> "Snow");
        for (int posZ = cameraZ - maxDistance; posZ <= cameraZ + maxDistance; ++posZ) {
            for (int posX = cameraX - maxDistance; posX <= cameraX + maxDistance; ++posX) {
                final int index = (posZ - cameraZ + 16) * 32 + posX - cameraX + 16;
                final float shiftX = (float)(this.snowXOffsets[index & 0x3FF] * 0.5);
                final float shiftZ = (float)(this.snowZOffsets[index & 0x3FF] * 0.5);
                final int minY = cameraY - maxDistance;
                final int maxY = cameraY + maxDistance;
                this.random.setSeed(posX * posX * 3121L + posX * 45238971L ^ posZ * posZ * 418711L + posZ * 13761L + titleSeed);
                double progress = timePassed % 512.0 / 512.0;
                progress = 1.0 - progress;
                final double offsetX = this.random.nextDouble() + timePassed * this.random.nextGaussian() * 0.01;
                final double offsetY = this.random.nextDouble() + timePassed * this.random.nextGaussian() * 0.001;
                final double distanceX = posX + 0.5f - (double)cameraX;
                final double distanceZ = posZ + 0.5f - (double)cameraZ;
                final double distance = Math.sqrt(distanceX * distanceX + distanceZ * distanceZ);
                final double visibilityFactor = distance / maxDistance;
                double opacity = ((1.0 - visibilityFactor * visibilityFactor) * 0.3 + 0.5) * strength;
                opacity *= MathHelper.clamp(visibilityFactor * visibilityFactor * 3.0, 0.0, 1.0);
                final float brightness = (float)Math.min(1.0, 1.0 - distance / 12.0);
                builder.pos(posX - shiftX + 0.5f, (float)minY, posZ - shiftZ + 0.5f).uv((float)(0.0 + offsetX), (float)(minY * 0.25 + progress + offsetY)).color(brightness, brightness, brightness, (float)opacity).light(15728880).normal(0.0f, 0.0f, 0.0f).endVertex();
                builder.pos(posX + shiftX + 0.5f, (float)minY, posZ + shiftZ + 0.5f).uv((float)(1.0 + offsetX), (float)(minY * 0.25 + progress + offsetY)).color(brightness, brightness, brightness, (float)opacity).light(15728880).normal(0.0f, 0.0f, 0.0f).endVertex();
                builder.pos(posX + shiftX + 0.5f, (float)maxY, posZ + shiftZ + 0.5f).uv((float)(1.0 + offsetX), (float)(maxY * 0.25 + progress + offsetY)).color(brightness, brightness, brightness, (float)opacity).light(15728880).normal(0.0f, 0.0f, 0.0f).endVertex();
                builder.pos(posX - shiftX + 0.5f, (float)maxY, posZ - shiftZ + 0.5f).uv((float)(0.0 + offsetX), (float)(maxY * 0.25 + progress + offsetY)).color(brightness, brightness, brightness, (float)opacity).light(15728880).normal(0.0f, 0.0f, 0.0f).endVertex();
            }
        }
        SchematicShaderPrograms.LIGHTNING = false;
        ImmediateRenderer.drawWithProgram(builder.end());
        SchematicShaderPrograms.LIGHTNING = true;
    }
    
    @Subscribe
    public void onGameFpsLimit(final GameFpsLimitEvent event) {
        if (this.labyAPI.minecraft().isIngame()) {
            return;
        }
        if (!this.loaded) {
            return;
        }
        final long timePassed = TimeUtil.getMillis() - this.lastTickTime;
        final boolean isControllerInUse = timePassed < 300L;
        final boolean limitFps = this.labyAPI.config().appearance().dynamicBackground().limitFpsWhenUnfocused().get();
        if (isControllerInUse && limitFps) {
            final boolean windowFocused = this.labyAPI.minecraft().minecraftWindow().isFocused() || TimeUtil.getMillis() - this.lastTimeMouseMoved < 20000L;
            final boolean animationInProgress = this.renderer.camera().getProgress() < 1.0f;
            event.setFramerateLimit((windowFocused || animationInProgress) ? 60 : 15);
        }
        final MutableMouse mouse = this.labyAPI.minecraft().mouse();
        if (mouse.getX() != this.prevMouseX || mouse.getY() != this.prevMouseY) {
            this.lastTimeMouseMoved = TimeUtil.getMillis();
            this.prevMouseX = mouse.getX();
            this.prevMouseY = mouse.getY();
        }
    }
    
    @Subscribe
    public void onScreenResize(final ScreenResizeEvent event) {
        if (this.schematicTarget != null) {
            this.schematicTarget.resize(event.getWidth(), event.getHeight());
        }
    }
    
    @Subscribe
    public void onWindowResize(final WindowResizeEvent event) {
        if (this.schematicTarget != null) {
            this.schematicTarget.setDirty();
        }
    }
    
    @Subscribe
    public void onSettingUpdate(final SettingUpdateEvent event) {
    }
    
    @Subscribe
    public void onShutdown(final GameShutdownEvent event) {
        if (this.renderer != null) {
            this.renderer.close();
        }
    }
    
    public void playOpening() {
        if (!this.loaded) {
            return;
        }
        final CinematicCamera camera = this.renderer.camera();
        camera.teleport(DynamicBackgroundController.POS_OPENER_START);
        camera.moveTo(4000L, DynamicBackgroundController.OPENER_CURVE, DynamicBackgroundController.POS_OPENER_TRANSFER, DynamicBackgroundController.POS_TITLE_SCREEN);
        this.timePassed = 0.0f;
        this.openingPlayed = true;
        this.entityAnimationController.stop();
        final ModelAnimation animation = this.entityAnimationLoader.getAnimation("opening");
        this.entityAnimationController.playNext(animation);
    }
    
    public boolean isOpeningPlaying() {
        final ModelAnimation animation = this.entityAnimationController.getPlaying();
        return animation != null && animation.getName().equals("opening");
    }
    
    public void tick() {
        if (this.loaded && this.openingPlayed) {
            this.renderer.onTick();
        }
        this.lastTickTime = TimeUtil.getMillis();
    }
    
    public void renderTick(final Stack stack, final float left, final float top, final float right, final float bottom, final float tickDelta) {
        if (!this.loaded || !this.openingPlayed || !this.wind) {
            return;
        }
        final float partialTicks = Laby.labyAPI().minecraft().getPartialTicks();
        final CinematicCamera camera = this.renderer.camera();
        final Location position = camera.location();
        final Location mod = camera.positionModifier(0);
        final float time = this.timePassed / 3000.0f;
        float strength = (float)(1.0 - (1.0f - time * time) * Math.exp(-0.5f * time * time));
        final float distance = (float)Math.abs(position.getZ() - DynamicBackgroundController.POS_TITLE_SCREEN.getZ());
        strength *= 1.0f - MathHelper.clamp(distance / 30.0f, 0.0f, 1.0f);
        mod.setRoll(-MathHelper.sin(time) * strength);
        mod.setPitch(MathHelper.sin(time) * strength);
        mod.setYaw(MathHelper.cos(time) * strength);
        mod.setY(-(1.0f - strength) * strength + MathHelper.cos(time * 2.0f) * strength / 5.0f);
        this.renderer.onRenderTick(stack, left, top, right, bottom, partialTicks);
    }
    
    public void render(final Stack stack, final float left, final float top, final float right, final float bottom, final float tickDelta) {
        if (!this.loaded || !this.openingPlayed) {
            return;
        }
        final float partialTicks = Laby.labyAPI().minecraft().getPartialTicks();
        this.renderFadeOut(stack, left, top, right, bottom, tickDelta);
        final float resolution = this.labyAPI.config().appearance().dynamicBackground().resolution().get();
        try {
            final Window window = this.labyAPI.minecraft().minecraftWindow();
            final int rawWidth = window.getRawWidth();
            final int rawHeight = window.getRawHeight();
            final float factor = 1.0f + (10.0f - resolution) / 9.0f;
            final int targetWidth = (int)(rawWidth / factor);
            final int targetHeight = (int)(rawHeight / factor);
            if (targetWidth == 0 || targetHeight == 0) {
                return;
            }
            if (this.schematicTarget.getWidth() != targetWidth || this.schematicTarget.getHeight() != targetHeight) {
                this.schematicTarget.resize(targetWidth, targetHeight);
                if (this.postProcessor != null) {
                    this.postProcessor.resize(targetWidth, targetHeight);
                }
            }
            final GFXRenderPipeline renderPipeline = this.labyAPI.gfxRenderPipeline();
            renderPipeline.clear(this.schematicTarget);
            renderPipeline.applyToTarget(this.schematicTarget, renderTarget -> this.renderWorld(stack, left, top, right, bottom, partialTicks));
            this.renderSchematicTarget(stack, left, top, right, bottom);
        }
        catch (final Exception exception) {
            this.onCrash(exception);
        }
        this.timePassed += TimeUtil.convertDeltaToMilliseconds(tickDelta);
    }
    
    private void renderSchematicTarget(final Stack stack, final float left, final float top, final float right, final float bottom) {
        final Window window = this.labyAPI.minecraft().minecraftWindow();
        final float scale = window.getScale();
        final int x = (int)(left * scale);
        final int y = (int)(top * scale);
        final int width = (int)((right - left) * scale);
        final int height = (int)((bottom - top) * scale);
        final GFXBridge gfx = Laby.gfx();
        gfx.viewport(x, y, width, height);
        final FloatVector4 colorModulator = gfx.blaze3DGlStatePipeline().shaderUniformPipeline().colorModulator();
        final float prevRed = colorModulator.getX();
        final float prevGreen = colorModulator.getY();
        final float prevBlue = colorModulator.getZ();
        final float prevAlpha = colorModulator.getW();
        colorModulator.set(1.0f, 1.0f, 1.0f, 1.0f);
        this.schematicTarget.render(width, height, true);
        colorModulator.set(prevRed, prevGreen, prevBlue, prevAlpha);
    }
    
    private void renderWorld(final Stack stack, final float left, final float top, final float right, final float bottom, final float partialTicks) {
        this.renderer.render(stack, left, top, right, bottom, partialTicks);
        final ActivityController activityController = Laby.references().activityController();
        if (!activityController.isActivityOpen(MainMenuActivity.class) && !activityController.isActivityOpen(BackgroundWorldTestActivity.class) && this.postProcessor != null) {
            this.postProcessor.process(partialTicks);
        }
    }
    
    private void renderFadeOut(final Stack stack, final float left, final float top, final float right, final float bottom, final float tickDelta) {
        final float blackFadeOutProgress = this.getTransitionProgress();
        this.labyAPI.renderPipeline().rectangleRenderer().pos(left, top, right, bottom).color(0.0f, 0.0f, 0.0f, blackFadeOutProgress).render(stack);
    }
    
    private void renderEntities() {
        final AnimationController controller = this.entityAnimationController;
        controller.applyAnimation(this.entityModel, new String[0]);
        if (!controller.isPlaying()) {
            final ModelAnimation loop = this.entityAnimationLoader.getAnimation("loop");
            if (loop != null) {
                controller.playNext(loop);
            }
        }
        this.renderModel(this.entityModel, 1.0f, true);
    }
    
    public void renderModel(final Model model, final float opacity, final boolean lightning) {
        final RenderedBuffer renderedBuffer = model.getRenderedBuffer();
        if (renderedBuffer != null) {
            renderedBuffer.dispose();
        }
        this.modelVertexWriter.setOpacity(opacity);
        this.modelVertexWriter.setLightning(lightning);
        SchematicShaderPrograms.LIGHTNING = lightning;
        final BufferState state = Laby.references().modelRenderer().modelUploader().model(model).modelVertexWriter(this.modelVertexWriter).upload(RenderPrograms.getSchematic(model.getTextureLocation()));
        if (state != null) {
            model.setDrawCommand(state);
            model.getRenderedBuffer().drawWithProgram();
        }
        SchematicShaderPrograms.LIGHTNING = true;
    }
    
    public void renderUIInWorld(final Stack stack, final float left, final float top, final float right, final float bottom, final Runnable runnable) {
        final SchematicRenderer schematicRenderer = this.getSchematicRenderer();
        if (schematicRenderer == null) {
            return;
        }
        final CinematicCamera camera = schematicRenderer.camera();
        stack.push();
        final GFXRenderPipeline renderPipeline = Laby.references().gfxRenderPipeline();
        final FloatMatrix4 modelViewMatrix = FloatMatrix4.createTranslateMatrix(0.0f, 0.0f, MinecraftVersions.V1_12_2.orOlder() ? 2000.0f : CinematicCamera.getZLevel());
        modelViewMatrix.multiply(camera.viewMatrix());
        renderPipeline.setProjectionMatrix(camera.projectionMatrix());
        stack.multiply(modelViewMatrix);
        final Location startPos = DynamicBackgroundController.POS_OPENER_START;
        stack.translate((float)startPos.getX(), (float)startPos.getY(), (float)startPos.getZ());
        stack.rotate((float)(-startPos.getRoll()), 0.0f, 0.0f, 1.0f);
        stack.rotate((float)(-startPos.getPitch()), 1.0f, 0.0f, 0.0f);
        stack.rotate((float)(-startPos.getYaw()), 0.0f, 1.0f, 0.0f);
        final float zOffset = bottom * 1.07f;
        final float scale = 10.0f / zOffset;
        stack.scale(scale, scale, scale);
        stack.translate(-right / 2.0f, -bottom / 2.0f, zOffset);
        runnable.run();
        stack.pop();
    }
    
    public void dispose() {
        if (this.renderer != null) {
            this.renderer.dispose();
        }
    }
    
    public void reset() {
        this.openingPlayed = false;
        this.timePassed = 0.0f;
        if (this.loaded) {
            this.entityAnimationController.stop();
            this.renderer.particleRenderer().clear();
            this.renderer.camera().teleport(DynamicBackgroundController.POS_OPENER_START);
        }
    }
    
    @Nullable
    public SchematicRenderer getSchematicRenderer() {
        return this.renderer;
    }
    
    public boolean isOpeningPlayed() {
        return this.openingPlayed;
    }
    
    public boolean isLoaded() {
        return this.loaded;
    }
    
    public boolean isInTransition() {
        return !this.openingPlayed || this.getTransitionProgress() < 1.0f;
    }
    
    public float getTimePassed() {
        return this.timePassed;
    }
    
    public void setWind(final boolean wind) {
        this.wind = wind;
    }
    
    public float getTransitionProgress() {
        return (float)MathHelper.clamp(Math.exp(this.timePassed / 1000.0f - 4.0f) * 10.0 - 0.20000000298023224, 0.0, 1.0);
    }
    
    public void executeTransition(final ResourceLocation identifier) {
        this.screenPositionRegistry.executeTransition(identifier);
    }
    
    public void executeTransition(final PositionTransition transition) {
        this.screenPositionRegistry.executeTransition(transition);
    }
    
    private void onCrash(final Throwable throwable) {
        this.crashed = true;
        DynamicBackgroundController.LOGGER.error("Dynamic background crashed", throwable);
    }
    
    public void close() {
        if (this.renderer != null) {
            this.renderer.close();
        }
    }
    
    public static boolean isEnabled() {
        final LabyAPI api = Laby.labyAPI();
        return api.config().appearance().dynamicBackground().enabled().get() && !LabyMod.references().dynamicBackgroundController().crashed && api.themeService().currentTheme().getBackgroundRenderer() != null && !api.minecraft().isIngame();
    }
    
    static {
        LOGGER = Logging.create(DynamicBackgroundController.class);
        OPENER_CURVE = new CubicBezier(0.2, 0.32, 0.2, 1.0);
        POS_OPENER_START = new Location(17.0, 13.0, 82.0, 0.0, 0.0, -90.0);
        POS_OPENER_TRANSFER = new Location(20.0, 12.8, 44.0, 8.0, -5.0, -10.0);
        POS_TITLE_SCREEN = new Location(31.0, 13.0, 6.0, 21.0, -13.0, 0.0);
        CUSTOMIZATION_PLAYER_CAMERA = new Location(20.0, 13.0, 38.599998474121094, 20.0, 0.0, 0.0);
        SHOP_PLAYER_CAMERA = new Location(21.5, 13.0, 39.20000076293945, 38.0, 0.0, 0.0);
        POS_PLAYER_ENTITY = new Location(17.0, 12.0, 40.5, -45.0, 0.0, 0.0);
    }
    
    private static class DefaultModelVertexWriter implements ModelUploader.ModelVertexWriter
    {
        private final LabyAPI labyAPI;
        private final Schematic schematic;
        private boolean lightning;
        private float opacity;
        
        private DefaultModelVertexWriter(final LabyAPI labyAPI, final Schematic schematic) {
            this.labyAPI = labyAPI;
            this.schematic = schematic;
        }
        
        @Override
        public void write(final BufferConsumer consumer, final float x, final float y, final float z, final float u, final float v, final int packedColor, final float normalX, final float normalY, final float normalZ, final float materialType, final boolean glowing, final long rainbowCycle) {
            consumer.pos(-x, -y, z);
            consumer.uv(u, v);
            final FloatMatrix4 viewMatrix = this.labyAPI.gfxRenderPipeline().getModelViewMatrix();
            final int posX = (int)(-x);
            final int posY = (int)(-y);
            final int posZ = (int)z;
            if (this.lightning) {
                float red = (packedColor >> 16 & 0xFF) / 255.0f;
                float green = (packedColor >> 8 & 0xFF) / 255.0f;
                float blue = (packedColor & 0xFF) / 255.0f;
                final LegacyLightEngine lightEngine = this.schematic.legacyLightEngine();
                red *= lightEngine.getRedStrengthAt(posX, posY, posZ);
                green *= lightEngine.getGreenStrengthAt(posX, posY, posZ);
                blue *= lightEngine.getBlueStrengthAt(posX, posY, posZ);
                consumer.color(red, green, blue, this.opacity);
            }
            else {
                consumer.packedColor(0xFFFFFF | (int)(this.opacity * 255.0f) << 24);
            }
            consumer.putFloat(0.0f);
            consumer.normal(normalX, normalY, normalZ);
        }
        
        public void setLightning(final boolean lightning) {
            this.lightning = lightning;
        }
        
        public void setOpacity(final float opacity) {
            this.opacity = opacity;
        }
    }
}
