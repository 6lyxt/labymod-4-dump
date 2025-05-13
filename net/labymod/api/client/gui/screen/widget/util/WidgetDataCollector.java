// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.util;

import net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor;
import java.util.HashMap;
import net.labymod.api.client.gui.lss.property.PropertyRegistry;
import net.labymod.api.client.gui.screen.util.StatefulRenderer;
import net.labymod.api.Laby;
import java.nio.CharBuffer;
import net.labymod.api.client.gui.screen.activity.ElementActivity;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Map;

public final class WidgetDataCollector
{
    private static final Map<Class<? extends Widget>, String> WIDGET_QUALIFIED_NAMES;
    private static final Map<Class<? extends Widget>, String> WIDGET_SIMPLE_NAMES;
    private static final Map<Class<? extends ElementActivity>, String> ACTIVITY_QUALIFIED_NAMES;
    private static final Map<Class<? extends ElementActivity>, String> ACTIVITY_SIMPLE_NAMES;
    
    public static void collectData(final Class<? extends Widget> widgetClass, final WidgetDataAccessor accessor) {
        final String qualifiedName = WidgetDataCollector.WIDGET_QUALIFIED_NAMES.computeIfAbsent(widgetClass, cls -> cls.getName().replace(".", "/"));
        final String simpleName = WidgetDataCollector.WIDGET_SIMPLE_NAMES.computeIfAbsent(widgetClass, cls -> {
            String name = cls.getSimpleName();
            if (name.endsWith("Widget")) {
                name = CharBuffer.wrap(name, 0, name.length() - 6).toString();
            }
            return name;
        });
        final PropertyRegistry propertyRegistry = Laby.references().propertyRegistry();
        accessor.accept(qualifiedName, simpleName, propertyRegistry.getDirectPropertyValueAccessor(widgetClass));
        StatefulRenderer.registerWidgetAnalyzer(widgetClass);
    }
    
    public static void collectData(final Class<? extends ElementActivity> activityClass, final ActivityDataAccessor accessor) {
        final String qualifiedName = WidgetDataCollector.ACTIVITY_QUALIFIED_NAMES.computeIfAbsent(activityClass, cls -> cls.getName().replace(".", "/"));
        final String simpleName = WidgetDataCollector.ACTIVITY_SIMPLE_NAMES.computeIfAbsent(activityClass, cls -> {
            String name = cls.getSimpleName();
            if (name.endsWith("Activity")) {
                name = CharBuffer.wrap(name, 0, name.length() - 8).toString();
            }
            return name;
        });
        accessor.accept(qualifiedName, simpleName);
    }
    
    static {
        WIDGET_QUALIFIED_NAMES = new HashMap<Class<? extends Widget>, String>();
        WIDGET_SIMPLE_NAMES = new HashMap<Class<? extends Widget>, String>();
        ACTIVITY_QUALIFIED_NAMES = new HashMap<Class<? extends ElementActivity>, String>();
        ACTIVITY_SIMPLE_NAMES = new HashMap<Class<? extends ElementActivity>, String>();
    }
    
    @FunctionalInterface
    public interface WidgetDataAccessor
    {
        void accept(final String p0, final String p1, final DirectPropertyValueAccessor p2);
    }
    
    @FunctionalInterface
    public interface ActivityDataAccessor
    {
        void accept(final String p0, final String p1);
    }
}
