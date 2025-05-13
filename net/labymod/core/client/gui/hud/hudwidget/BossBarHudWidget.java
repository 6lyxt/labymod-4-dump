// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget;

import net.labymod.api.client.world.DynamicBossBarProgressHandler;
import net.labymod.api.client.world.BossBarProgressHandler;
import net.labymod.api.client.world.BossBarColor;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.annotation.SpriteTexture;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.client.gfx.pipeline.texture.atlas.Atlases;
import net.labymod.api.client.world.BossBarOverlay;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.overlay.IngameOverlayElementRenderEvent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.ComponentRendererBuilder;
import net.labymod.api.client.render.font.ComponentRenderer;
import java.util.Iterator;
import net.labymod.api.client.world.BossBarRegistry;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.util.VanillaParityUtil;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.world.BossBar;
import java.util.Set;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget;

@IntroducedIn("4.1.0")
@SpriteSlot(x = 4, y = 5)
public class BossBarHudWidget extends SimpleHudWidget<BossBarHudWidgetConfig>
{
    private static final ResourceLocation[] BAR_BACKGROUND_SPRITES;
    private static final ResourceLocation[] BAR_PROGRESS_SPRITES;
    private static final ResourceLocation[] OVERLAY_BACKGROUND_SPRITES;
    private static final ResourceLocation[] OVERLAY_PROGRESS_SPRITES;
    private static final Set<BossBar> DUMMIES;
    private static final float BOSS_BAR_WIDTH = 182.0f;
    
    public BossBarHudWidget() {
        super("boss_bar", BossBarHudWidgetConfig.class);
        this.bindCategory(HudWidgetCategory.INGAME);
        this.bindDropzones(NamedHudWidgetDropzones.BOSS_BAR);
    }
    
    @Override
    public void render(final Stack stack, final MutableMouse mouse, final float partialTicks, final boolean isEditorContext, final HudSize size) {
        final BossBarRegistry registry = Laby.references().bossBarRegistry();
        final int maxHeight = this.labyAPI.minecraft().minecraftWindow().getScaledHeight() / 3;
        final boolean showHealth = ((BossBarHudWidgetConfig)this.config).showBar().get();
        int height = 12;
        final int spacing = 14;
        final int healthHeight = 5;
        final Set<BossBar> bossBars = isEditorContext ? BossBarHudWidget.DUMMIES : registry.getBossBars();
        for (final BossBar bossBar : bossBars) {
            final ComponentRenderer componentRenderer = Laby.references().componentRenderer();
            final ComponentRendererBuilder builder = componentRenderer.builder();
            final float textHeight = componentRenderer.height();
            if (stack != null) {
                stack.push();
                stack.translate(0.0f, 0.0f, VanillaParityUtil.getBossBarZLevel());
                final Component component = bossBar.displayName();
                final float componentWidth = componentRenderer.width(component);
                final float offsetX = this.anchor.getGapX(182.0f, componentWidth);
                builder.text(component).pos(offsetX, height - textHeight).render(stack);
                if (showHealth) {
                    this.drawBar(stack, 0, MathHelper.ceil((float)height), bossBar);
                }
                stack.pop();
            }
            if (showHealth) {
                height += healthHeight;
            }
            height += spacing;
            if (height >= maxHeight) {
                height = maxHeight;
                break;
            }
        }
        size.set(182.0f, (float)Math.max(height - spacing, healthHeight));
    }
    
    @Subscribe
    public void onIngameOverlayElementRender(final IngameOverlayElementRenderEvent event) {
        if (!this.isEnabled()) {
            return;
        }
        if (event.phase() != Phase.PRE || event.elementType() != IngameOverlayElementRenderEvent.OverlayElementType.BOSS_BAR) {
            return;
        }
        event.setCancelled(true);
    }
    
    private void drawBar(final Stack stack, final int x, final int y, final BossBar bar) {
        final int barWidth = 182;
        this.drawBar(stack, x, y, barWidth, bar, BossBarHudWidget.BAR_BACKGROUND_SPRITES, BossBarHudWidget.OVERLAY_BACKGROUND_SPRITES);
        final float barProgress = bar.progressHandler().getProgress();
        final int progress = this.lerpDiscrete(barProgress, 0, barWidth);
        if (progress > 0) {
            this.drawBar(stack, x, y, progress, bar, BossBarHudWidget.BAR_PROGRESS_SPRITES, BossBarHudWidget.OVERLAY_PROGRESS_SPRITES);
        }
    }
    
    private void drawBar(final Stack stack, final int x, final int y, final int progress, final BossBar bar, final ResourceLocation[] bars, final ResourceLocation[] overlays) {
        this.drawBar(stack, bars[bar.bossBarColor().ordinal()], x, y, progress);
        if (bar.bossBarOverlay() != BossBarOverlay.PROGRESS) {
            this.drawBar(stack, overlays[bar.bossBarOverlay().ordinal() - 1], x, y, progress);
        }
    }
    
    private void drawBar(final Stack stack, final ResourceLocation spriteLocation, final int x, final int y, final int progress) {
        final ResourceRenderContext context = Laby.references().resourceRenderContext();
        context.begin(stack, Atlases.BARS_ATLAS);
        final TextureAtlas atlas = Laby.references().atlasRegistry().getAtlas(Atlases.BARS_ATLAS);
        ResourceRenderContext.ATLAS_RENDERER.blitSprite(context, atlas, spriteLocation, 182, 5, 0, 0, x, y, progress, 5, -1);
        context.uploadToBuffer();
    }
    
    @Override
    public boolean isVisibleInGame() {
        return true;
    }
    
    @Override
    public boolean renderInDebug() {
        return true;
    }
    
    private int lerpDiscrete(final float value, final int start, final int end) {
        final int diff = end - start;
        return start + MathHelper.floor(value * (diff - 1)) + ((value > 0.0f) ? 1 : 0);
    }
    
    private static ResourceLocation create(final String path) {
        return ResourceLocation.create("minecraft", path);
    }
    
    static {
        BAR_BACKGROUND_SPRITES = new ResourceLocation[] { create("boss_bar/pink_background"), create("boss_bar/blue_background"), create("boss_bar/red_background"), create("boss_bar/green_background"), create("boss_bar/yellow_background"), create("boss_bar/purple_background"), create("boss_bar/white_background") };
        BAR_PROGRESS_SPRITES = new ResourceLocation[] { create("boss_bar/pink_progress"), create("boss_bar/blue_progress"), create("boss_bar/red_progress"), create("boss_bar/green_progress"), create("boss_bar/yellow_progress"), create("boss_bar/purple_progress"), create("boss_bar/white_progress") };
        OVERLAY_BACKGROUND_SPRITES = new ResourceLocation[] { create("boss_bar/notched_6_background"), create("boss_bar/notched_10_background"), create("boss_bar/notched_12_background"), create("boss_bar/notched_20_background") };
        OVERLAY_PROGRESS_SPRITES = new ResourceLocation[] { create("boss_bar/notched_6_progress"), create("boss_bar/notched_10_progress"), create("boss_bar/notched_12_progress"), create("boss_bar/notched_20_progress") };
        DUMMIES = Set.of(new DummyBossBar());
    }
    
    @SpriteTexture("settings/hud/hud")
    public static class BossBarHudWidgetConfig extends HudWidgetConfig
    {
        @SwitchWidget.SwitchSetting
        @SpriteSlot(x = 5, y = 5)
        private final ConfigProperty<Boolean> showBar;
        
        public BossBarHudWidgetConfig() {
            this.showBar = new ConfigProperty<Boolean>(true);
        }
        
        public ConfigProperty<Boolean> showBar() {
            return this.showBar;
        }
    }
    
    private static class DummyBossBar implements BossBar
    {
        private static final Component DISPLAY_NAME;
        
        @NotNull
        @Override
        public UUID getIdentifier() {
            return UUID.randomUUID();
        }
        
        @Override
        public Component displayName() {
            return DummyBossBar.DISPLAY_NAME;
        }
        
        @Override
        public BossBarColor bossBarColor() {
            return BossBarColor.PINK;
        }
        
        @Override
        public BossBarOverlay bossBarOverlay() {
            return BossBarOverlay.PROGRESS;
        }
        
        @Override
        public BossBarProgressHandler progressHandler() {
            return new DynamicBossBarProgressHandler(t -> {}, () -> 0.8f);
        }
        
        static {
            DISPLAY_NAME = Component.text("Herobrine");
        }
    }
}
