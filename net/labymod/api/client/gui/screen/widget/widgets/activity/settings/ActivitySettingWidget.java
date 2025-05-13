// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.activity.settings;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.SettingInfo;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;
import net.labymod.api.Textures;
import net.labymod.api.util.I18n;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import java.util.function.Supplier;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.configuration.settings.util.SettingActivitySupplier;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;

@AutoWidget
@SettingWidget
public class ActivitySettingWidget extends ButtonWidget implements SettingActivitySupplier
{
    private final Supplier<Activity> supplier;
    private final String translationKey;
    
    private ActivitySettingWidget(final String translationKey, final Supplier<Activity> supplier) {
        this.translationKey = translationKey;
        this.supplier = supplier;
    }
    
    @Override
    public void initialize(final Parent parent) {
        final String translation = I18n.getTranslation(this.translationKey, new Object[0]);
        if (translation == null) {
            this.component = null;
            this.icon().set(Textures.SpriteCommon.SETTINGS);
        }
        else {
            this.component = Component.text(translation);
            this.icon().set(null);
        }
        super.initialize(parent);
    }
    
    @Override
    public Activity activity(final Setting setting) {
        return this.supplier.get();
    }
    
    @SettingFactory
    public static class Factory implements WidgetFactory<ActivitySetting, ActivitySettingWidget>
    {
        @Override
        public ActivitySettingWidget[] create(final Setting setting, final ActivitySetting annotation, final SettingInfo<?> info, final SettingAccessor accessor) {
            final ActivitySettingWidget addonActivityWidget = new ActivitySettingWidget(setting.getTranslationKey() + ".text", this.invokeButtonPress(info));
            return new ActivitySettingWidget[] { addonActivityWidget };
        }
        
        @Override
        public Class<?>[] types() {
            return new Class[0];
        }
        
        private Supplier<Activity> invokeButtonPress(final SettingInfo<?> settingInfo) {
            return (Supplier<Activity>)(() -> {
                try {
                    return (Object)settingInfo.getMember().invoke(settingInfo.config(), new Object[0]);
                }
                catch (final IllegalAccessException | InvocationTargetException exception) {
                    exception.printStackTrace();
                    return null;
                }
            });
        }
    }
    
    @SettingElement
    @Target({ ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ActivitySetting {
    }
}
