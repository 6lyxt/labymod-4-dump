// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.overlay;

import java.util.function.Supplier;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.lss.meta.LinkMetaLoader;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import java.util.ArrayList;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ScreenOverlayHandler
{
    void registerOverlay(final ScreenOverlay p0);
    
    void unregisterOverlay(final ScreenOverlay p0);
    
    @Nullable
     <T extends ScreenOverlay> T screenOverlay(final Class<T> p0);
    
     <T extends ScreenOverlay> Optional<T> findScreenOverlay(final Class<T> p0);
    
    List<ScreenOverlay> overlays();
    
    void forEachReversed(final Consumer<ScreenOverlay> p0);
    
    boolean isOverlayHovered();
    
    boolean isActive(final Function<ScreenOverlay, Boolean> p0);
    
    default WidgetReference displayInOverlay(final Widget widget) {
        return this.displayInOverlay(widget, widget);
    }
    
    default WidgetReference displayInOverlay(final Object linkSource, final Widget widget) {
        return this.displayInOverlay(linkSource.getClass(), widget);
    }
    
    default WidgetReference displayInOverlay(final Class<?> linkSourceClass, final Widget widget) {
        final List<StyleSheet> stylesheets = new ArrayList<StyleSheet>();
        final LinkMetaLoader linkMetaLoader = Laby.references().linkMetaLoader();
        final List<StyleSheet> obj = stylesheets;
        Objects.requireNonNull((ArrayList)obj);
        linkMetaLoader.loadMeta(linkSourceClass, (Consumer<StyleSheet>)obj::add);
        return this.displayInOverlay(stylesheets, widget);
    }
    
    default WidgetReference displayInOverlay(final List<StyleSheet> styles, final Widget widget) {
        return this.displayInOverlay(null, styles, widget);
    }
    
    default WidgetReference displayInOverlay(final LabyScreen sourceScreen, final List<StyleSheet> styles, final Widget widget) {
        final WidgetScreenOverlay overlay = this.screenOverlay(WidgetScreenOverlay.class);
        if (overlay != null) {
            return overlay.display(sourceScreen, styles, widget);
        }
        return null;
    }
    
    @Deprecated(forRemoval = true, since = "4.1.4")
    default void allowCustomFont(final LabyScreen screen) {
    }
    
    @Deprecated(forRemoval = true, since = "4.1.4")
    default void allowCustomFont(final Object screen) {
    }
    
    @Deprecated(forRemoval = true, since = "4.1.4")
    default void allowCustomFont(final ScreenInstance screen, final Runnable runnable) {
    }
    
    @Deprecated(forRemoval = true, since = "4.1.4")
    default <T> T allowCustomFont(final ScreenInstance screen, final Supplier<T> runnable) {
        return null;
    }
    
    @Deprecated(forRemoval = true, since = "4.1.4")
    default void denyCustomFont(final LabyScreen screen) {
    }
    
    @Deprecated(forRemoval = true, since = "4.1.4")
    default void denyCustomFont(final Object screen) {
    }
}
