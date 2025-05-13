// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.converter.exclusion;

import java.util.Objects;

public class ScreenExclusionStrategy implements ExclusionStrategy
{
    private final Class<?> excludedScreen;
    
    ScreenExclusionStrategy(final Class<?> excludedScreen) {
        this.excludedScreen = excludedScreen;
    }
    
    @Override
    public boolean shouldExclude(final Class<?> target) {
        return Objects.equals(target, this.excludedScreen);
    }
}
