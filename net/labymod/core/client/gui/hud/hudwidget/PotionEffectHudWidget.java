// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.overlay.IngameOverlayElementRenderEvent;
import net.labymod.api.client.render.font.ComponentRendererBuilder;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.Component;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.mouse.MutableMouse;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.bounds.area.RectangleAreaPosition;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.hud.hudwidget.background.BackgroundHudWidget;

@SpriteSlot(x = 1, y = 1)
public class PotionEffectHudWidget extends BackgroundHudWidget<PotionEffectHudWidgetConfig>
{
    private static final PotionEffect[] DUMMY_POTIONS;
    private static final PotionEffect[] EMPTY_POTIONS;
    private static final String MAX_DURATION = "**:**";
    private static final String INFINITE = "\u221e";
    
    public PotionEffectHudWidget() {
        super("potion", PotionEffectHudWidgetConfig.class);
        this.bindCategory(HudWidgetCategory.INGAME);
    }
    
    @Override
    public void initializePreConfigured(final PotionEffectHudWidgetConfig config) {
        super.initializePreConfigured(config);
        config.setEnabled(true);
        config.setAreaIdentifier(RectangleAreaPosition.MIDDLE_LEFT);
        config.setX(2.0f);
        config.setY(-8.0f);
    }
    
    @Override
    public void render(@Nullable final Stack stack, final MutableMouse mouse, final float partialTicks, final boolean isEditorContext, final HudSize size) {
        super.render(stack, mouse, partialTicks, isEditorContext, size);
        final ClientPlayer player = LabyMod.getInstance().minecraft().getClientPlayer();
        final ComponentRendererBuilder componentRenderer = this.labyAPI.renderPipeline().componentRenderer().builder().useFloatingPointPosition(false);
        PotionEffect[] potionEffects = PotionEffectHudWidget.EMPTY_POTIONS;
        if (player != null) {
            potionEffects = player.getActivePotionEffects().toArray(new PotionEffect[0]);
        }
        if (potionEffects.length == 0) {
            potionEffects = PotionEffectHudWidget.DUMMY_POTIONS;
        }
        int y = 0;
        int width = 0;
        final boolean rightBound = this.anchor.isRight();
        final PotionEffect[] array = potionEffects;
        for (int length = array.length, i = 0; i < length; ++i) {
            final PotionEffect potionEffect = array[i];
            int x = 0;
            if (stack != null) {
                potionEffect.renderIcon(stack, (int)(rightBound ? (size.getActualWidth() - 18.0f - x) : ((float)x)), y, 18, 18);
            }
            x += 20;
            final String translationKey = potionEffect.getTranslationKey();
            Component nameComponent = ((BaseComponent<Component>)Component.translatable(translationKey, new Component[0])).color(TextColor.color(this.getConfig().nameColor().get()));
            final int amplifier = potionEffect.getAmplifier();
            if (amplifier != 0) {
                final String enchantmentKey = "enchantment.level." + (amplifier + 1);
                final Component amplifierComponent = (amplifier >= 1 && amplifier <= 9) ? Component.text(" ").append(Component.translatable(enchantmentKey, new Component[0])) : Component.empty();
                final TextColor color = TextColor.color(this.getConfig().amplifierColor().get());
                nameComponent = nameComponent.append(amplifierComponent.color(color));
            }
            final String time = this.getTime(potionEffect);
            final Component timeComponent = ((BaseComponent<Component>)Component.text(time)).color(TextColor.color(this.getConfig().durationColor().get()));
            final RenderableComponent renderHeader = RenderableComponent.of(nameComponent);
            final RenderableComponent renderTime = RenderableComponent.of(timeComponent);
            if (stack != null) {
                componentRenderer.pos(rightBound ? (size.getActualWidth() - x - renderHeader.getWidth()) : ((float)x), (float)(y + 1)).text(renderHeader).render(stack);
            }
            y += (int)renderHeader.getHeight();
            width = (int)Math.max((float)width, x + renderHeader.getWidth());
            if (stack != null) {
                componentRenderer.pos(rightBound ? (size.getActualWidth() - x - renderTime.getWidth()) : ((float)x), (float)(y + 1)).text(renderTime).render(stack);
            }
            y += (int)renderTime.getHeight();
            width = (int)Math.max((float)width, x + renderTime.getWidth());
        }
        size.set(width, y);
    }
    
    @Override
    public boolean isVisibleInGame() {
        final ClientPlayer player = LabyMod.getInstance().minecraft().getClientPlayer();
        return player != null && !player.getActivePotionEffects().isEmpty();
    }
    
    @Subscribe
    public void onPotionEffectsRender(final IngameOverlayElementRenderEvent event) {
        if (this.isEnabled() && event.elementType() == IngameOverlayElementRenderEvent.OverlayElementType.POTION_EFFECTS && this.isVisibleInGame()) {
            event.setCancelled(true);
        }
    }
    
    private String getTime(final PotionEffect effect) {
        if (effect.isInfiniteDuration()) {
            return "\u221e";
        }
        if (effect.hasMaxDuration()) {
            return "**:**";
        }
        String format = TimeUtil.formatTickDuration(effect.getDuration());
        if (!((PotionEffectHudWidgetConfig)this.config).leadingZero().get() && format.startsWith("0")) {
            format = format.substring(1);
        }
        return format;
    }
    
    static {
        DUMMY_POTIONS = new PotionEffect[] { new DummyPotionEffect(1300, 3, "labymod.hudWidget.potion.dummy.name") };
        EMPTY_POTIONS = new PotionEffect[0];
    }
    
    private static class DummyPotionEffect implements PotionEffect
    {
        private final int duration;
        private final int amplifier;
        private final String translationKey;
        
        public DummyPotionEffect(final int duration, final int amplifier, final String translationKey) {
            this.duration = duration;
            this.amplifier = amplifier;
            this.translationKey = translationKey;
        }
        
        @Override
        public int getDuration() {
            return this.duration;
        }
        
        @Override
        public int getAmplifier() {
            return this.amplifier;
        }
        
        @Override
        public String getTranslationKey() {
            return this.translationKey;
        }
        
        @Override
        public Icon getIcon() {
            return Icon.sprite(Textures.SURVIVAL_INVENTORY_BACKGROUND, 72, 198, 18, 18, 256, 256);
        }
        
        @Override
        public void renderIcon(final Stack stack, final int x, final int y, final int width, final int height) {
            final Icon icon = this.getIcon();
            if (icon == null) {
                return;
            }
            icon.render(stack, (float)x, (float)y, (float)width, (float)height);
        }
        
        @Override
        public boolean hasMaxDuration() {
            return false;
        }
    }
    
    public static class PotionEffectHudWidgetConfig extends BackgroundHudWidgetConfig
    {
        @ColorPickerWidget.ColorPickerSetting
        private final ConfigProperty<Integer> nameColor;
        @ColorPickerWidget.ColorPickerSetting
        private final ConfigProperty<Integer> durationColor;
        @ColorPickerWidget.ColorPickerSetting
        private final ConfigProperty<Integer> amplifierColor;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> leadingZero;
        
        public PotionEffectHudWidgetConfig() {
            this.nameColor = new ConfigProperty<Integer>(-1);
            this.durationColor = new ConfigProperty<Integer>(-1);
            this.amplifierColor = new ConfigProperty<Integer>(-1);
            this.leadingZero = new ConfigProperty<Boolean>(false);
        }
        
        public ConfigProperty<Integer> nameColor() {
            return this.nameColor;
        }
        
        public ConfigProperty<Integer> durationColor() {
            return this.durationColor;
        }
        
        public ConfigProperty<Integer> amplifierColor() {
            return this.amplifierColor;
        }
        
        public ConfigProperty<Boolean> leadingZero() {
            return this.leadingZero;
        }
    }
}
