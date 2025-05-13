// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.hudwidget;

import java.util.Iterator;
import net.labymod.api.util.KeyValue;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.hud.GlobalHudWidgetConfig;
import java.util.function.Function;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.util.bounds.area.RectangleAreaPosition;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.hud.position.VerticalHudWidgetAlignment;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.client.gui.hud.position.HorizontalHudWidgetAlignment;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.configuration.settings.annotation.CustomTranslation;
import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingOrder;
import net.labymod.api.configuration.loader.Config;

@SettingOrder(-127)
public class HudWidgetConfig extends Config
{
    public static final int GLOBAL_ORDER = 10;
    @Exclude
    @CustomTranslation("scale")
    @SliderWidget.SliderSetting(min = 0.5f, max = 1.5f, steps = 0.0f)
    private final ConfigProperty<Float> scale;
    @SettingSection(value = "alignment", translation = "labymod.hudWidget.global")
    @SettingOrder(15)
    @CustomTranslation("labymod.hudWidget.global.horizontalAlignment")
    @DropdownWidget.DropdownSetting
    private final ConfigProperty<HorizontalHudWidgetAlignment> horizontalAlignment;
    @SettingOrder(15)
    @CustomTranslation("labymod.hudWidget.global.horizontalOrientation")
    @DropdownWidget.DropdownSetting
    private final ConfigProperty<HorizontalHudWidgetAlignment> horizontalOrientation;
    @SettingOrder(15)
    @CustomTranslation("labymod.hudWidget.global.verticalOrientation")
    @DropdownWidget.DropdownSetting
    private final ConfigProperty<VerticalHudWidgetAlignment> verticalOrientation;
    @SettingOrder(10)
    @SettingSection(value = "general", translation = "labymod.hudWidget.global")
    @CustomTranslation("labymod.hudWidget.useGlobal")
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> useGlobal;
    @Exclude
    private RectangleAreaPosition areaIdentifier;
    @Exclude
    private float x;
    @Exclude
    private float y;
    @Exclude
    private boolean enabled;
    @Exclude
    private String parentId;
    @Exclude
    private String dropzoneId;
    
    public HudWidgetConfig() {
        this.scale = new ConfigProperty<Float>(1.0f);
        this.horizontalAlignment = ConfigProperty.createEnum(HorizontalHudWidgetAlignment.AUTO);
        this.horizontalOrientation = ConfigProperty.createEnum(HorizontalHudWidgetAlignment.AUTO);
        this.verticalOrientation = ConfigProperty.createEnum(VerticalHudWidgetAlignment.AUTO);
        this.useGlobal = new ConfigProperty<Boolean>(true);
        this.areaIdentifier = RectangleAreaPosition.TOP_LEFT;
    }
    
    public ConfigProperty<Boolean> useGlobal() {
        return this.useGlobal;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    public String getParentId() {
        return this.parentId;
    }
    
    public void setParentId(final String parentId) {
        this.parentId = parentId;
    }
    
    public RectangleAreaPosition areaIdentifier() {
        return this.areaIdentifier;
    }
    
    public void setAreaIdentifier(final RectangleAreaPosition areaIdentifier) {
        this.areaIdentifier = areaIdentifier;
    }
    
    public ConfigProperty<Float> scale() {
        return this.scale;
    }
    
    public ConfigProperty<HorizontalHudWidgetAlignment> horizontalAlignment() {
        return this.horizontalAlignment;
    }
    
    public ConfigProperty<VerticalHudWidgetAlignment> verticalOrientation() {
        return this.verticalOrientation;
    }
    
    public ConfigProperty<HorizontalHudWidgetAlignment> horizontalOrientation() {
        return this.horizontalOrientation;
    }
    
    public float getX() {
        return this.x;
    }
    
    public void setX(final float x) {
        this.x = x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public void setY(final float y) {
        this.y = y;
    }
    
    public String getDropzoneId() {
        return this.dropzoneId;
    }
    
    public void setDropzoneId(final String dropzoneId) {
        this.dropzoneId = dropzoneId;
    }
    
    public void setDropzone(final HudWidgetDropzone dropzone) {
        this.dropzoneId = dropzone.getId();
    }
    
    protected <T> ConfigProperty<T> property(final Function<GlobalHudWidgetConfig, ConfigProperty<T>> globalFunction, final ConfigProperty<T> property) {
        return this.function(globalFunction, property);
    }
    
    protected <T extends Config> T config(final Function<GlobalHudWidgetConfig, T> globalFunction, final T config) {
        return this.function(globalFunction, config);
    }
    
    protected <T> T function(final Function<GlobalHudWidgetConfig, T> globalFunction, final T object) {
        return this.useGlobal().get() ? globalFunction.apply(Laby.labyAPI().hudWidgetRegistry().globalHudWidgetConfig()) : object;
    }
    
    public boolean setParentToTailOfChainIn(final RectangleAreaPosition areaIdentifier) {
        for (final KeyValue<HudWidget<?>> element : Laby.labyAPI().hudWidgetRegistry().getElements()) {
            final HudWidget<?> head = element.getValue();
            if (head.config != null && ((HudWidgetConfig)head.config).areaIdentifier() == areaIdentifier) {
                if (head.getParent() != null) {
                    continue;
                }
                final HudWidget<?> tail = head.lastWidget();
                this.setParentId(tail.getId());
                return true;
            }
        }
        return false;
    }
}
