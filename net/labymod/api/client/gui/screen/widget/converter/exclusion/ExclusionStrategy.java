// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.converter.exclusion;

public interface ExclusionStrategy
{
    default ExclusionStrategy screen(final Class<?> screenClass) {
        return new ScreenExclusionStrategy(screenClass);
    }
    
    default ExclusionStrategy widget(final Class<?> screenClass, final Class<?> widgetClass) {
        return new WidgetExclusionStrategy(screenClass, (Class<?>[])new Class[] { widgetClass });
    }
    
    default ExclusionStrategy widget(final Class<?> screenClass, final Class<?>... widgetClasses) {
        return new WidgetExclusionStrategy(screenClass, widgetClasses);
    }
    
    boolean shouldExclude(final Class<?> p0);
}
