// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.injector;

import net.labymod.api.client.gui.screen.widget.StyledWidget;
import java.util.List;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface LssInjector
{
    @Deprecated
    default void register(@NotNull final Object instance, @NotNull final String fileName, @NotNull final String targetFileName) {
        this.registerStyleSheet(instance, fileName, targetFileName);
    }
    
    void registerStyleSheet(@NotNull final Object p0, @NotNull final String p1, @NotNull final String p2);
    
    void registerWidget(@NotNull final Object p0, @NotNull final String p1, @NotNull final String p2);
    
    boolean unregister(@NotNull final Object p0, @NotNull final String p1);
    
    @NotNull
    List<StyleSheet> getMatchedStyleSheetInjectors(@NotNull final StyleSheet p0);
    
    @NotNull
    List<StyleSheet> getMatchedWidgetInjectors(@NotNull final StyledWidget p0);
}
