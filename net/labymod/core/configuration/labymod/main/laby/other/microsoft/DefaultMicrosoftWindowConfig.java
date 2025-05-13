// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.other.microsoft;

import it.unimi.dsi.fastutil.longs.LongListIterator;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.Minecraft;
import net.labymod.api.Laby;
import net.labymod.core.client.os.windows.window.WindowManagement;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.util.Color;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.labymod.main.laby.other.microsoft.MicrosoftWindowConfig;
import net.labymod.api.configuration.loader.Config;

public class DefaultMicrosoftWindowConfig extends Config implements MicrosoftWindowConfig
{
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> immersiveDarkMode;
    @DropdownWidget.DropdownSetting
    private final ConfigProperty<WindowMaterial> windowMaterial;
    @DropdownWidget.DropdownSetting
    private final ConfigProperty<CornerCurvatures> cornerCurvatures;
    @SettingSection("windowBorder")
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> defaultWindowBorderColor;
    @SwitchWidget.SwitchSetting
    @SettingRequires(value = "defaultWindowBorderColor", invert = true)
    private final ConfigProperty<Boolean> hideWindowBorder;
    @ColorPickerWidget.ColorPickerSetting
    @SettingRequires(value = "defaultWindowBorderColor", invert = true)
    private final ConfigProperty<Color> windowBorderColor;
    @SettingSection("windowTitleBar")
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> defaultWindowTitleBarColor;
    @ColorPickerWidget.ColorPickerSetting
    @SettingRequires(value = "defaultWindowTitleBarColor", invert = true)
    private final ConfigProperty<Color> windowTitleBarColor;
    @SettingSection("titleText")
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> defaultTitleTextColor;
    @ColorPickerWidget.ColorPickerSetting
    @SettingRequires(value = "defaultTitleTextColor", invert = true)
    private final ConfigProperty<Color> titleTextColor;
    
    public DefaultMicrosoftWindowConfig() {
        this.immersiveDarkMode = ConfigProperty.create(false).addChangeListener(new UpdateListener());
        this.windowMaterial = ConfigProperty.createEnum(WindowMaterial.AUTO).visibilitySupplier(WindowManagement::isWindowMaterialSupported).addChangeListener(new UpdateListener());
        this.cornerCurvatures = ConfigProperty.createEnum(CornerCurvatures.DEFAULT).addChangeListener(new UpdateListener());
        this.defaultWindowBorderColor = ConfigProperty.create(true).addChangeListener(new UpdateListener());
        this.hideWindowBorder = ConfigProperty.create(false).addChangeListener(new UpdateListener());
        this.windowBorderColor = ConfigProperty.create(Color.WHITE).addChangeListener(new UpdateListener());
        this.defaultWindowTitleBarColor = ConfigProperty.create(true).addChangeListener(new UpdateListener());
        this.windowTitleBarColor = ConfigProperty.create(Color.WHITE).addChangeListener(new UpdateListener());
        this.defaultTitleTextColor = ConfigProperty.create(true).addChangeListener(new UpdateListener());
        this.titleTextColor = ConfigProperty.create(Color.BLACK).addChangeListener(new UpdateListener());
    }
    
    @Override
    public ConfigProperty<Boolean> immersiveDarkMode() {
        return this.immersiveDarkMode;
    }
    
    @Override
    public ConfigProperty<WindowMaterial> windowMaterial() {
        return this.windowMaterial;
    }
    
    @Override
    public ConfigProperty<CornerCurvatures> cornerCurvatures() {
        return this.cornerCurvatures;
    }
    
    @Override
    public ConfigProperty<Boolean> defaultWindowBorderColor() {
        return this.defaultWindowBorderColor;
    }
    
    @Override
    public ConfigProperty<Boolean> hideWindowBorder() {
        return this.hideWindowBorder;
    }
    
    @Override
    public ConfigProperty<Color> windowBorderColor() {
        return this.windowBorderColor;
    }
    
    @Override
    public ConfigProperty<Boolean> defaultWindowTitleBarColor() {
        return this.defaultWindowTitleBarColor;
    }
    
    @Override
    public ConfigProperty<Color> windowTitleBarColor() {
        return this.windowTitleBarColor;
    }
    
    @Override
    public ConfigProperty<Boolean> defaultTitleTextColor() {
        return this.defaultTitleTextColor;
    }
    
    @Override
    public ConfigProperty<Color> titleTextColor() {
        return this.titleTextColor;
    }
    
    private static class UpdateListener implements Runnable
    {
        @Override
        public void run() {
            final Minecraft minecraft = Laby.labyAPI().minecraft();
            if (minecraft != null) {
                final Window window = minecraft.minecraftWindow();
                WindowManagement.update(window.getPointer());
                for (final long windowPointer : Laby.gfx().backend().glfwNatives().getWindows()) {
                    if (windowPointer == window.getPointer()) {
                        continue;
                    }
                    WindowManagement.update(windowPointer);
                }
            }
        }
    }
}
