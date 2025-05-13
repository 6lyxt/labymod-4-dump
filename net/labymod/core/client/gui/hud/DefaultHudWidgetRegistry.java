// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud;

import net.labymod.core.client.render.font.text.TextUtil;
import org.jetbrains.annotations.NotNull;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.labymod.api.client.gui.hud.GlobalHudWidgetConfig;
import net.labymod.api.configuration.loader.ConfigAccessor;
import java.util.Objects;
import java.util.Iterator;
import net.labymod.api.revision.Revision;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.client.gui.icon.Icon;
import java.util.Locale;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.Laby;
import net.labymod.api.event.client.gui.hud.HudWidgetRegisterEvent;
import net.labymod.core.client.gui.hud.hudwidget.EconomyDisplayHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.BlockBreakHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.ItemCounterHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.TitleHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.DirectionHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.BossBarHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.ActionBarHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.InventoryTrackerHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.PaperDollHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.ScoreboardHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.SaturationHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.PotionEffectHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.item.equipment.FeetHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.item.equipment.LegsHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.item.equipment.ChestHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.item.equipment.HelmetHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.item.equipment.OffHandHudWidget;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.core.client.gui.hud.hudwidget.item.equipment.MainHandHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.item.ArrowHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.service.ServiceYouTubeHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.service.ServiceTwitchHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.service.ServiceTikTokHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.CpuTemperatureHudWidget;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.core.client.gui.hud.hudwidget.text.LightLevelHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.PlaytimeHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.WorldTimeHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.SystemMemoryHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.SystemCpuHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.SystemBatteryHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.SpeedHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.ServerAddressHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.RangeHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.PlayerCountHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.PingHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.FDirectionHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.EntityCountHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.DateHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.ComboHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.ClockHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.ClickTestHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.BiomeHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.AfkTimerHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.MemoryHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.RotationHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.CoordinateHudWidget;
import java.util.function.Supplier;
import net.labymod.core.client.gui.hud.hudwidget.text.FpsHudWidget;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import javax.inject.Inject;
import net.labymod.api.Constants;
import net.labymod.api.configuration.settings.type.RootSettingRegistry;
import net.labymod.api.revision.RevisionRegistry;
import net.labymod.api.configuration.loader.impl.JsonConfigLoader;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategoryRegistry;
import net.labymod.api.LabyAPI;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.hud.HudWidgetRegistry;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.service.DefaultRegistry;

@Singleton
@Implements(HudWidgetRegistry.class)
public class DefaultHudWidgetRegistry extends DefaultRegistry<HudWidget<?>> implements HudWidgetRegistry
{
    private static final String DEFAULT_PROFILE = "default";
    private final LabyAPI labyAPI;
    private final HudWidgetCategoryRegistry categoryRegistry;
    private final JsonConfigLoader configLoader;
    private final RevisionRegistry revisionRegistry;
    private String selectedProfile;
    private DefaultGlobalHudWidgetConfig globalHudWidgetConfig;
    private RootSettingRegistry globalHudWidgetSettingRegistry;
    
    @Inject
    public DefaultHudWidgetRegistry(final LabyAPI labyAPI, final HudWidgetCategoryRegistry categoryRegistry, final RevisionRegistry revisionRegistry) {
        this.labyAPI = labyAPI;
        this.categoryRegistry = categoryRegistry;
        this.configLoader = new JsonConfigLoader(Constants.Files.CONFIGS);
        this.revisionRegistry = revisionRegistry;
        this.selectProfile("default");
    }
    
    @Override
    public void registerDefaults() {
        this.labyAPI.eventBus().registerListener(new HudWidgetListener(this));
        this.categoryRegistry.register(HudWidgetCategory.INGAME);
        this.categoryRegistry.register(HudWidgetCategory.ITEM);
        this.categoryRegistry.register(HudWidgetCategory.SYSTEM);
        this.categoryRegistry.register(HudWidgetCategory.SERVICE);
        this.categoryRegistry.register(HudWidgetCategory.MISCELLANEOUS);
        this.register((Supplier<HudWidget<?>>)FpsHudWidget::new);
        this.register((Supplier<HudWidget<?>>)CoordinateHudWidget::new);
        this.register((Supplier<HudWidget<?>>)RotationHudWidget::new);
        this.register((Supplier<HudWidget<?>>)MemoryHudWidget::new);
        this.register((Supplier<HudWidget<?>>)AfkTimerHudWidget::new);
        this.register((Supplier<HudWidget<?>>)BiomeHudWidget::new);
        this.register((Supplier<HudWidget<?>>)ClickTestHudWidget::new);
        this.register((Supplier<HudWidget<?>>)ClockHudWidget::new);
        this.register((Supplier<HudWidget<?>>)ComboHudWidget::new);
        this.register((Supplier<HudWidget<?>>)DateHudWidget::new);
        this.register((Supplier<HudWidget<?>>)EntityCountHudWidget::new);
        this.register((Supplier<HudWidget<?>>)FDirectionHudWidget::new);
        this.register((Supplier<HudWidget<?>>)PingHudWidget::new);
        this.register((Supplier<HudWidget<?>>)PlayerCountHudWidget::new);
        this.register((Supplier<HudWidget<?>>)RangeHudWidget::new);
        this.register((Supplier<HudWidget<?>>)ServerAddressHudWidget::new);
        this.register((Supplier<HudWidget<?>>)SpeedHudWidget::new);
        this.register((Supplier<HudWidget<?>>)SystemBatteryHudWidget::new);
        this.register((Supplier<HudWidget<?>>)SystemCpuHudWidget::new);
        this.register((Supplier<HudWidget<?>>)SystemMemoryHudWidget::new);
        this.register((Supplier<HudWidget<?>>)WorldTimeHudWidget::new);
        this.register((Supplier<HudWidget<?>>)PlaytimeHudWidget::new);
        this.register((Supplier<HudWidget<?>>)LightLevelHudWidget::new);
        if (!MinecraftVersions.V1_16_5.orOlder()) {
            this.register((Supplier<HudWidget<?>>)CpuTemperatureHudWidget::new);
        }
        this.register((Supplier<HudWidget<?>>)ServiceTikTokHudWidget::new);
        this.register((Supplier<HudWidget<?>>)ServiceTwitchHudWidget::new);
        this.register((Supplier<HudWidget<?>>)ServiceYouTubeHudWidget::new);
        this.register((Supplier<HudWidget<?>>)ArrowHudWidget::new);
        this.register((Supplier<HudWidget<?>>)MainHandHudWidget::new);
        if (!PlatformEnvironment.isAncientPvPVersion()) {
            this.register((Supplier<HudWidget<?>>)OffHandHudWidget::new);
        }
        this.register((Supplier<HudWidget<?>>)HelmetHudWidget::new);
        this.register((Supplier<HudWidget<?>>)ChestHudWidget::new);
        this.register((Supplier<HudWidget<?>>)LegsHudWidget::new);
        this.register((Supplier<HudWidget<?>>)FeetHudWidget::new);
        this.register((Supplier<HudWidget<?>>)PotionEffectHudWidget::new);
        this.register((Supplier<HudWidget<?>>)SaturationHudWidget::new);
        this.register((Supplier<HudWidget<?>>)ScoreboardHudWidget::new);
        this.register((Supplier<HudWidget<?>>)PaperDollHudWidget::new);
        this.register((Supplier<HudWidget<?>>)InventoryTrackerHudWidget::new);
        this.register((Supplier<HudWidget<?>>)ActionBarHudWidget::new);
        this.register((Supplier<HudWidget<?>>)BossBarHudWidget::new);
        this.register((Supplier<HudWidget<?>>)DirectionHudWidget::new);
        this.register((Supplier<HudWidget<?>>)TitleHudWidget::new);
        this.register((Supplier<HudWidget<?>>)ItemCounterHudWidget::new);
        this.register((Supplier<HudWidget<?>>)BlockBreakHudWidget::new);
        this.register((Supplier<HudWidget<?>>)EconomyDisplayHudWidget::new);
    }
    
    @Override
    public void register(final HudWidget<?> hudWidget) {
        final Class<? extends HudWidget> clazz = hudWidget.getClass();
        final String namespace = this.labyAPI.getNamespace(clazz);
        super.register(hudWidget);
        this.labyAPI.eventBus().registerListener(hudWidget);
        try {
            final HudWidgetConfig config = this.loadConfig(hudWidget);
            hudWidget.load(config);
            this.updateLinkedWidgets(hudWidget);
            Laby.fireEvent(new HudWidgetRegisterEvent(hudWidget));
            final SpriteSlot spriteSlot = clazz.getAnnotation(SpriteSlot.class);
            if (spriteSlot != null) {
                final Theme theme = Laby.labyAPI().themeService().currentTheme();
                final ResourceLocation spriteResource = theme.resource(namespace, String.format(Locale.ROOT, "textures/settings/hud/hud%s.png", (spriteSlot.page() == 0) ? "" : ("_" + spriteSlot.page())));
                final Icon icon = Icon.sprite(spriteResource, spriteSlot.x() * spriteSlot.size(), spriteSlot.y() * spriteSlot.size(), spriteSlot.size(), spriteSlot.size(), 128, 128);
                hudWidget.setIcon(icon);
            }
            final IntroducedIn newFeature = clazz.getAnnotation(IntroducedIn.class);
            if (newFeature != null) {
                final Revision revision = this.revisionRegistry.getRevision(namespace, newFeature.value());
                hudWidget.setRevision(revision);
            }
        }
        catch (final Exception e) {
            e.printStackTrace();
            this.unregister(hudWidget.getId());
        }
    }
    
    @Override
    public void unregister(final String id) {
        final HudWidget<?> hudWidget = this.getById(id);
        if (hudWidget == null) {
            return;
        }
        super.unregister(id);
        this.labyAPI.eventBus().unregisterListener(hudWidget);
    }
    
    @Override
    public void selectProfile(final String profile) {
        this.selectedProfile = profile;
        this.configLoader.setVariable("$PROFILE", profile);
        try {
            this.globalHudWidgetConfig = this.configLoader.load(DefaultGlobalHudWidgetConfig.class);
        }
        catch (final Exception e) {
            e.printStackTrace();
            this.globalHudWidgetConfig = new DefaultGlobalHudWidgetConfig();
        }
        for (final HudWidget<?> hudWidget : this.values()) {
            try {
                final HudWidgetConfig config = this.loadConfig(hudWidget);
                hudWidget.load(config);
            }
            catch (final Exception e2) {
                e2.printStackTrace();
            }
        }
        (this.globalHudWidgetSettingRegistry = this.globalHudWidgetConfig.asRegistry("global").translationId("hudWidget.global")).initialize();
        this.saveConfig();
    }
    
    @Override
    public String getSelectedProfile() {
        return this.selectedProfile;
    }
    
    @Override
    public void updateLinkedWidgets(final HudWidget<?> hudWidget) {
        final HudWidget<?> parent = this.getById(((HudWidgetConfig)hudWidget.getConfig()).getParentId());
        if (parent != null) {
            hudWidget.updateParent(parent);
        }
        for (final HudWidget<?> other : this.values()) {
            final String parentId = ((HudWidgetConfig)other.getConfig()).getParentId();
            if (Objects.equals(parentId, hudWidget.getId())) {
                hudWidget.updateChild(other);
            }
        }
        for (final HudWidget<?> value : this.values()) {
            if (value.isEnabled() && value.getParent() != null && !value.getParent().isEnabled()) {
                value.updateParent(null);
            }
        }
    }
    
    @Override
    public void saveConfig() {
        for (final HudWidget<?> hudWidget : this.values()) {
            this.globalHudWidgetConfig.getConfigs().put(hudWidget.getId(), this.configLoader.getGson().toJsonTree((Object)hudWidget.getConfig()).getAsJsonObject());
        }
        this.configLoader.save(this.globalHudWidgetConfig);
    }
    
    @Override
    public GlobalHudWidgetConfig globalHudWidgetConfig() {
        return this.globalHudWidgetConfig;
    }
    
    @Override
    public RootSettingRegistry globalHudWidgetSettingRegistry() {
        return this.globalHudWidgetSettingRegistry;
    }
    
    private HudWidgetConfig loadConfig(final HudWidget<?> hudWidget) throws Exception {
        final JsonObject configObject = this.globalHudWidgetConfig.getConfigs().get(hudWidget.getId());
        if (configObject != null) {
            return (HudWidgetConfig)this.configLoader.getGson().fromJson((JsonElement)configObject, (Class)hudWidget.getConfigClass());
        }
        final HudWidgetConfig config = (HudWidgetConfig)hudWidget.getConfigClass().getConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
        hudWidget.initializePreConfigured(config);
        this.globalHudWidgetConfig.getConfigs().put(hudWidget.getId(), this.configLoader.getGson().toJsonTree((Object)config).getAsJsonObject());
        return config;
    }
    
    public void reloadConfigOfEnabledHudWidgets() {
        for (final HudWidget<?> hudWidget : this.values()) {
            if (!hudWidget.isEnabled()) {
                continue;
            }
            hudWidget.reloadConfig();
        }
    }
    
    @Override
    public HudWidgetCategoryRegistry categoryRegistry() {
        return this.categoryRegistry;
    }
    
    @Override
    public void updateHudWidgets(@NotNull final String reason) {
        TextUtil.pushAndApplyAttributes();
        for (final HudWidget<?> hudWidget : this.values()) {
            this.updateLinkedWidgets(hudWidget);
            if (!hudWidget.isEnabled()) {
                continue;
            }
            hudWidget.requestUpdate(reason);
        }
        TextUtil.popRenderAttributes();
    }
}
