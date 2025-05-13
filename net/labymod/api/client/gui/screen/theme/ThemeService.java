// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.theme;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ThemeService
{
    void initialize();
    
    void registerTheme(final Theme p0);
    
    default Theme changeTheme(final Theme theme) {
        return this.changeTheme(theme.getId());
    }
    
    void reload();
    
    void unregisterTheme(final Theme p0);
    
    Theme changeTheme(final String p0);
    
    @NotNull
    Theme currentTheme();
    
    boolean isInitialized();
    
    Theme getThemeByName(final String p0);
    
    @Nullable
     <T extends ThemeConfigAccessor> T getThemeConfig(final Theme p0, final Class<T> p1);
    
    @Nullable
    default <T extends ThemeConfigAccessor> T getThemeConfig(final Class<T> configClass) {
        final Theme currentTheme = this.currentTheme();
        return (T)(configClass.isAssignableFrom(currentTheme.getConfigClass()) ? this.getThemeConfig(currentTheme, configClass) : null);
    }
    
    @Deprecated
    default boolean isInitialize() {
        return this.isInitialized();
    }
}
