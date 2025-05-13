// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.util;

import java.util.HashMap;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.BooleanSupplier;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.util.reflection.MethodOverrideAnalyzer;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Map;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class StatefulRenderer
{
    private static final Map<Class<? extends Widget>, MethodOverrideAnalyzer> RENDER_OVERRIDE_ANALYZERS;
    private static final Map<Class<? extends Widget>, MethodOverrideAnalyzer> RENDER_WIDGET_OVERRIDE_ANALYZERS;
    private static final Map<Class<? extends Activity>, MethodOverrideAnalyzer> ACTIVITY_RENDER_OVERRIDE_ANALYZERS;
    
    public static void tryRender(final ScreenContext context, final StateProvider stateProvider, final BooleanSupplier useLegacy, final Consumer<ScreenContext> newest, final Consumer<ScreenContext> legacy) {
        if (stateProvider.get()) {
            newest.accept(context);
            return;
        }
        if (useLegacy.getAsBoolean()) {
            try {
                stateProvider.set(true);
                legacy.accept(context);
            }
            finally {
                stateProvider.set(false);
            }
        }
        else {
            newest.accept(context);
        }
    }
    
    public static void registerWidgetAnalyzer(final Class<? extends Widget> widgetClass) {
        StatefulRenderer.RENDER_OVERRIDE_ANALYZERS.computeIfAbsent(widgetClass, StatefulRenderer::createWidgetRenderAnalyzer);
        StatefulRenderer.RENDER_WIDGET_OVERRIDE_ANALYZERS.computeIfAbsent(widgetClass, StatefulRenderer::createWidgetRenderWidgetAnalyzer);
    }
    
    public static void registerActivityAnalyzer(final Class<? extends Activity> activityClass) {
        StatefulRenderer.ACTIVITY_RENDER_OVERRIDE_ANALYZERS.computeIfAbsent(activityClass, StatefulRenderer::createActivityRenderAnalyzer);
    }
    
    public static boolean isWidgetRenderOverridden(final Class<? extends Widget> widgetClass) {
        return isWidgetOverridden(widgetClass, StatefulRenderer.RENDER_OVERRIDE_ANALYZERS);
    }
    
    public static boolean isWidgetRenderWidgetOverridden(final Class<? extends Widget> widgetClass) {
        return isWidgetOverridden(widgetClass, StatefulRenderer.RENDER_WIDGET_OVERRIDE_ANALYZERS);
    }
    
    public static boolean isActivityRenderOverridden(final Class<? extends Activity> activityClass) {
        return StatefulRenderer.ACTIVITY_RENDER_OVERRIDE_ANALYZERS.containsKey(activityClass) && StatefulRenderer.ACTIVITY_RENDER_OVERRIDE_ANALYZERS.get(activityClass).isOverridden();
    }
    
    private static boolean isWidgetOverridden(final Class<? extends Widget> widgetClass, final Map<Class<? extends Widget>, MethodOverrideAnalyzer> analyzers) {
        return analyzers.containsKey(widgetClass) && analyzers.get(widgetClass).isOverridden();
    }
    
    private static MethodOverrideAnalyzer createWidgetRenderWidgetAnalyzer(final Class<? extends Widget> widgetClass) {
        return new MethodOverrideAnalyzer(widgetClass, AbstractWidget.class, "renderWidget", (Class<?>[])new Class[] { Stack.class, MutableMouse.class, Float.TYPE });
    }
    
    private static MethodOverrideAnalyzer createWidgetRenderAnalyzer(final Class<? extends Widget> widgetClass) {
        return new MethodOverrideAnalyzer(widgetClass, AbstractWidget.class, "render", (Class<?>[])new Class[] { Stack.class, MutableMouse.class, Float.TYPE });
    }
    
    private static MethodOverrideAnalyzer createActivityRenderAnalyzer(final Class<? extends Activity> activityClass) {
        return new MethodOverrideAnalyzer(activityClass, Activity.class, "render", (Class<?>[])new Class[] { Stack.class, MutableMouse.class, Float.TYPE });
    }
    
    static {
        RENDER_OVERRIDE_ANALYZERS = new HashMap<Class<? extends Widget>, MethodOverrideAnalyzer>();
        RENDER_WIDGET_OVERRIDE_ANALYZERS = new HashMap<Class<? extends Widget>, MethodOverrideAnalyzer>();
        ACTIVITY_RENDER_OVERRIDE_ANALYZERS = new HashMap<Class<? extends Activity>, MethodOverrideAnalyzer>();
    }
    
    public static class StateProvider
    {
        private boolean value;
        
        public StateProvider(final boolean value) {
            this.value = value;
        }
        
        public boolean get() {
            return this.value;
        }
        
        public void set(final boolean value) {
            this.value = value;
        }
    }
}
