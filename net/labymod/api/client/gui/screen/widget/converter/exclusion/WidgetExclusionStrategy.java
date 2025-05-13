// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.converter.exclusion;

import java.util.Objects;

public class WidgetExclusionStrategy implements ExclusionStrategy
{
    private final Class<?> screenClass;
    private final Class<?>[] excludedWidgets;
    private boolean currentScreen;
    
    WidgetExclusionStrategy(final Class<?> screenClass, final Class<?>... excludedWidgets) {
        this.screenClass = screenClass;
        this.excludedWidgets = excludedWidgets;
    }
    
    @Override
    public boolean shouldExclude(final Class<?> target) {
        if (Objects.equals(this.screenClass, target)) {
            this.currentScreen = true;
        }
        if (!this.currentScreen) {
            return false;
        }
        for (final Class<?> excludedWidget : this.excludedWidgets) {
            if (excludedWidget.isAssignableFrom(target)) {
                return true;
            }
        }
        return false;
    }
}
