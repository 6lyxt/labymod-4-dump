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
import net.labymod.api.util.I18n;
import net.labymod.api.client.gui.screen.activity.Activity;
import java.util.function.Supplier;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.configuration.settings.util.SettingActivitySupplier;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
@SettingWidget
@Deprecated
public class AddonActivityWidget extends SimpleWidget implements SettingActivitySupplier
{
    private final Supplier<Activity> supplier;
    private String text;
    private String translatable;
    
    private AddonActivityWidget(final Supplier<Activity> supplier) {
        this.supplier = supplier;
    }
    
    public String getText() {
        return (this.translatable != null) ? I18n.getTranslation(this.translatable, new Object[0]) : this.text;
    }
    
    public Activity activity() {
        return this.supplier.get();
    }
    
    @Override
    public Activity activity(final Setting setting) {
        return this.activity();
    }
    
    @SettingFactory
    public static class Factory implements WidgetFactory<AddonActivitySetting, AddonActivityWidget>
    {
        @Override
        public AddonActivityWidget[] create(final Setting setting, final AddonActivitySetting annotation, final SettingInfo<?> info, final SettingAccessor accessor) {
            final AddonActivityWidget addonActivityWidget = new AddonActivityWidget(this.invokeButtonPress(info));
            if (!annotation.text().isEmpty()) {
                addonActivityWidget.text = annotation.text();
                return new AddonActivityWidget[] { addonActivityWidget };
            }
            if (!annotation.translation().isEmpty()) {
                addonActivityWidget.translatable = annotation.translation();
                return new AddonActivityWidget[] { addonActivityWidget };
            }
            addonActivityWidget.translatable = setting.getTranslationKey() + ".text";
            return new AddonActivityWidget[] { addonActivityWidget };
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
    @Deprecated
    public @interface AddonActivitySetting {
        @Deprecated
        String text() default "";
        
        @Deprecated
        String translation() default "";
    }
}
