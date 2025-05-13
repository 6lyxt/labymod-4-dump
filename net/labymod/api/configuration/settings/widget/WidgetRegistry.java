// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.widget;

import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.Laby;
import java.lang.reflect.AnnotatedElement;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.SettingInfo;
import java.lang.annotation.Annotation;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface WidgetRegistry
{
    void loadWidgetStorage(final ClassLoader p0);
    
    void register(final Class<? extends Widget> p0);
    
    Widget[] createWidgets(final Setting p0, final Annotation p1, final SettingInfo<?> p2, final SettingAccessor p3);
    
    default Widget[] createWidgets(final SettingInfo<?> info, final SettingAccessor accessor) {
        return this.createWidgets(null, info, accessor);
    }
    
    default Widget[] createWidgets(final Setting setting, final SettingInfo<?> info, final SettingAccessor accessor) {
        for (final Annotation annotation : ((AnnotatedElement)info.getMember()).getDeclaredAnnotations()) {
            final WidgetRegistry widgetRegistry = Laby.labyAPI().widgetRegistry();
            final Widget[] widgets = widgetRegistry.createWidgets(setting, annotation, info, accessor);
            if (widgets != null) {
                if (setting.isElement()) {
                    setting.asElement().setAnnotation(annotation);
                    setting.asElement().setExtended(annotation.annotationType().getAnnotation(SettingElement.class).extended());
                }
                return widgets;
            }
        }
        return null;
    }
    
    Class<? extends Widget> getWidgetTypeByAnnotation(final Annotation p0);
}
