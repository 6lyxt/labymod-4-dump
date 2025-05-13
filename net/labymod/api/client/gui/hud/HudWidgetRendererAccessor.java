// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud;

import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.screen.widget.widgets.hud.HudWidgetWidget;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;

public interface HudWidgetRendererAccessor
{
    void onVisibilityChanged(final HudWidget<?> p0);
    
    void onSizeChanged(final HudWidget<?> p0);
    
    Rectangle getArea();
    
    boolean isEditor();
    
    boolean isDebugEnabled();
    
    default boolean shouldIgnoreAlignment() {
        return false;
    }
    
    boolean canUpdateHudWidget(final HudWidget<?> p0);
    
    HudWidget<?> getHudWidgetInDropzone(final String p0);
    
    @NotNull
    HudWidgetWidget getWidget(@NotNull final HudWidget<?> p0);
    
    default void openSettings(final HudWidget<?> hudWidget) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
    default HudWidget<?> getDraggingHudWidget() {
        return null;
    }
    
    default HudWidget<?> getHudWidgetInDropzone(final HudWidgetDropzone dropzone) {
        return this.getHudWidgetInDropzone(dropzone.getId());
    }
    
    default boolean isDropzoneInUse(final String dropzoneId) {
        return this.getHudWidgetInDropzone(dropzoneId) != null;
    }
    
    default boolean isDropzoneInUse(final HudWidgetDropzone dropzone) {
        return this.isDropzoneInUse(dropzone.getId());
    }
    
    default HudWidget<?> getRelevantHudWidgetForDropzone(final HudWidgetDropzone zone) {
        final HudWidget<?> hudWidget = this.getHudWidgetInDropzone(zone);
        if (hudWidget != null) {
            return hudWidget;
        }
        if (this.shouldIgnoreAlignment()) {
            return null;
        }
        final HudWidget<?> dragging = this.getDraggingHudWidget();
        if (dragging != null && dragging.fitsInDropzone(zone)) {
            return dragging;
        }
        return null;
    }
}
