// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget;

import net.labymod.api.client.gui.hud.GlobalHudWidgetConfig;
import net.labymod.api.configuration.settings.annotation.CustomTranslation;
import net.labymod.api.configuration.settings.annotation.SettingOrder;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gui.hud.hudwidget.background.BackgroundConfig;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.font.ComponentRendererBuilder;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.mojang.model.MojangModelService;
import net.labymod.api.mojang.texture.MojangTextureService;
import net.labymod.api.mojang.texture.MojangTextureType;
import net.labymod.api.client.resources.ResourcesReloadWatcher;
import java.util.UUID;
import net.labymod.api.event.Subscribe;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.event.client.session.SessionUpdateEvent;
import net.labymod.core.main.user.shop.emote.animation.EmoteAnimationStorage;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.entity.EntityPose;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.render.model.DefaultModelBuffer;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.client.gui.hud.hudwidget.background.HudWidgetBackgroundRenderer;
import net.labymod.api.client.gui.hud.hudwidget.ResizeableHudWidget;

@IntroducedIn("4.1.0")
@SpriteSlot(x = 6, y = 4)
public class PaperDollHudWidget extends ResizeableHudWidget<PaperDollHudWidgetConfig> implements HudWidgetBackgroundRenderer
{
    private DefaultModelBuffer modelBuffer;
    private long lastInteraction;
    
    public PaperDollHudWidget() {
        super("paper_doll", PaperDollHudWidgetConfig.class);
        this.bindCategory(HudWidgetCategory.INGAME);
        this.updateDummyModel(Laby.labyAPI().getUniqueId());
    }
    
    @Override
    public void render(final Stack stack, final MutableMouse mouse, final float partialTicks, final boolean isEditorContext, final float width, final float height) {
        if (stack != null) {
            this.renderEntireBackground(stack, width, height);
            final float margin = ((PaperDollHudWidgetConfig)this.config).background().getMargin();
            final float padding = ((PaperDollHudWidgetConfig)this.config).background().getPadding();
            final Player player = this.getTargetPlayer();
            if (this.modelBuffer == null && player == null) {
                this.renderPlaceholder(stack, 0.0f, 0.0f, width, height);
            }
            else {
                final boolean isRightAligned = this.anchor().isRight();
                this.renderPaperDoll(stack, player, width / 2.0f, height - margin - padding - 2.0f, 0.0f, (float)(((PaperDollHudWidgetConfig)this.config).rotation().get() * (isRightAligned ? 1 : -1)), 0.0f, (height - margin * 2.0f - padding * 2.0f) / 2.0f, ((PaperDollHudWidgetConfig)this.config).showName().get(), ((PaperDollHudWidgetConfig)this.config).headRotationStrength().get(), this.labyAPI.minecraft().getPartialTicks());
            }
        }
    }
    
    private void renderPaperDoll(final Stack stack, final Player player, final float x, final float y, final float rotationX, final float rotationY, final float rotationZ, final float scale, final boolean renderName, final float headRotationStrength, final float partialTicks) {
        if (player == null) {
            final float modifiedScale = scale - 2.0f;
            stack.push();
            stack.translate(x, y, 10.0f);
            stack.rotate(180.0f - rotationY, 0.0f, 1.0f, 0.0f);
            stack.scale(modifiedScale, modifiedScale, modifiedScale);
            this.modelBuffer.render(stack);
            stack.pop();
            if (((PaperDollHudWidgetConfig)this.config).showName().get()) {
                this.renderNameTag(stack, x, -15.0f, modifiedScale / 38.0f);
            }
        }
        else {
            this.labyAPI.minecraft().entityRenderDispatcher().renderInScreen(stack, player, x, y, rotationX, rotationY, rotationZ, scale, renderName, headRotationStrength, partialTicks);
        }
    }
    
    private void renderNameTag(final Stack stack, final float x, final float y, final float scale) {
        stack.push();
        final RenderPipeline pipeline = this.labyAPI.renderPipeline();
        final Component component = Component.text(Laby.labyAPI().getName());
        final ComponentRenderer componentRenderer = pipeline.componentRenderer();
        final float componentWidth = componentRenderer.width(component);
        stack.translate(this.width / 2.0f, 0.0f, 0.0f);
        stack.scale(scale, scale, 1.0f);
        stack.translate(-this.width / 2.0f, 0.0f, 0.0f);
        pipeline.rectangleRenderer().renderRectangle(stack, this.width / 2.0f - componentWidth / 2.0f - 2.0f, y, this.width / 2.0f + componentWidth / 2.0f + 2.0f, y + 11.0f, 1073741824);
        componentRenderer.builder().text(component).pos(x, y + 2.0f).shadow(false).centered(true).render(stack);
        stack.pop();
    }
    
    @Override
    public boolean isVisibleInGame() {
        return !((PaperDollHudWidgetConfig)this.config).autoHide().get() || TimeUtil.getMillis() - this.lastInteraction <= ((PaperDollHudWidgetConfig)this.config).visibleDuration().get() * 1000 + 100;
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        super.onTick(isEditorContext);
        final Player player = this.getTargetPlayer();
        if (player == null) {
            return;
        }
        if (player.entityPose() != EntityPose.STANDING || player.isOnFire() || player.getVehicle() != null || player.isSprinting()) {
            this.lastInteraction = TimeUtil.getMillis();
        }
        final EmoteAnimationStorage storage = LabyMod.references().emoteService().getAnimationStorage(player);
        if (storage != null && storage.isPlaying()) {
            this.lastInteraction = TimeUtil.getMillis();
        }
    }
    
    @Subscribe
    public void onSessionUpdate(final SessionUpdateEvent event) {
        ThreadSafe.executeOnRenderThread(() -> this.updateDummyModel(event.newSession().getUniqueId()));
    }
    
    private void updateDummyModel(final UUID uniqueId) {
        final ResourcesReloadWatcher watcher = Laby.references().resourcesReloadWatcher();
        watcher.addOrExecuteInitializeListener(() -> this.updateDummyModel0(uniqueId));
    }
    
    private void updateDummyModel0(final UUID uniqueId) {
        final MojangTextureService textureService = Laby.references().mojangTextureService();
        final MojangModelService modelService = Laby.references().mojangModelService();
        textureService.getTexture(uniqueId, MojangTextureType.SKIN, resourceLocation -> {
            final MinecraftServices.SkinVariant variant = textureService.getVariant(resourceLocation);
            final Model model = modelService.getPlayerModel(variant, resourceLocation);
            this.modelBuffer = new DefaultModelBuffer(model);
        });
    }
    
    private void renderPlaceholder(final Stack stack, final float x, final float y, final float width, final float height) {
        final RenderPipeline renderPipeline = this.labyAPI.renderPipeline();
        final ComponentRendererBuilder componentRendererBuilder = renderPipeline.componentRenderer().builder();
        renderPipeline.rectangleRenderer().renderRectangle(stack, x, y, x + width, y + height, Integer.MIN_VALUE);
        componentRendererBuilder.text("PAPER").centered(true).scale(0.5f).pos(x + width / 2.0f, y + height / 2.0f - 5.0f).render(stack);
        componentRendererBuilder.text("DOLL").centered(true).scale(0.5f).pos(x + width / 2.0f, y + height / 2.0f).render(stack);
    }
    
    private Player getTargetPlayer() {
        final Entity cameraEntity = this.labyAPI.minecraft().getCameraEntity();
        return (cameraEntity instanceof Player) ? ((Player)cameraEntity) : this.labyAPI.minecraft().getClientPlayer();
    }
    
    @Override
    public BackgroundConfig config() {
        return ((PaperDollHudWidgetConfig)super.config).background();
    }
    
    public static class PaperDollHudWidgetConfig extends ResizeableHudWidgetConfig
    {
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> showName;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> autoHide;
        @SettingRequires("autoHide")
        @SliderWidget.SliderSetting(min = 0.0f, max = 5.0f, steps = 1.0f)
        private final ConfigProperty<Integer> visibleDuration;
        @SliderWidget.SliderSetting(min = -180.0f, max = 180.0f, steps = 10.0f)
        private final ConfigProperty<Integer> rotation;
        @SliderWidget.SliderSetting(min = 0.0f, max = 1.0f, steps = 0.05f)
        private final ConfigProperty<Float> headRotationStrength;
        @SettingOrder(10)
        @CustomTranslation("labymod.hudWidget.background")
        @SettingRequires(value = "useGlobal", invert = true)
        private BackgroundConfig background;
        
        public PaperDollHudWidgetConfig() {
            super(20.0f, 50.0f, 100.0f, 20.0f, 80.0f, 100.0f);
            this.showName = new ConfigProperty<Boolean>(false);
            this.autoHide = new ConfigProperty<Boolean>(false);
            this.visibleDuration = new ConfigProperty<Integer>(2);
            this.rotation = new ConfigProperty<Integer>(20);
            this.headRotationStrength = new ConfigProperty<Float>(0.5f);
            this.background = new BackgroundConfig();
        }
        
        public ConfigProperty<Boolean> showName() {
            return this.showName;
        }
        
        public ConfigProperty<Boolean> autoHide() {
            return this.autoHide;
        }
        
        public ConfigProperty<Integer> visibleDuration() {
            return this.visibleDuration;
        }
        
        public ConfigProperty<Integer> rotation() {
            return this.rotation;
        }
        
        public ConfigProperty<Float> headRotationStrength() {
            return this.headRotationStrength;
        }
        
        public BackgroundConfig background() {
            return this.config(GlobalHudWidgetConfig::background, this.background);
        }
    }
}
