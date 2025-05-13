// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen;

import net.labymod.api.client.gui.screen.activity.Activity;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.Laby;
import net.labymod.api.event.client.gui.screen.ScreenOpenEvent;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.Interactable;

public interface ScreenInstance extends Interactable
{
    void resize(final int p0, final int p1);
    
    void render(final ScreenContext p0);
    
    @Deprecated(forRemoval = true, since = "4.2.42")
    void render(final Stack p0, final MutableMouse p1, final float p2);
    
    boolean renderBackground(final ScreenContext p0);
    
    void renderOverlay(final ScreenContext p0);
    
    void renderHoverComponent(final ScreenContext p0);
    
    default void onOpenScreen() {
        this.onVisibilityChanged(true);
        Laby.fireEvent(new ScreenOpenEvent(this));
    }
    
    default void onCloseScreen() {
        this.onVisibilityChanged(false);
    }
    
    default void onVisibilityChanged(final boolean visible) {
    }
    
    @NotNull
    Object mostInnerScreen();
    
    @NotNull
    ScreenInstance mostInnerScreenInstance();
    
    default ScreenWrapper wrap() {
        return Laby.labyAPI().minecraft().wrapScreen(this);
    }
    
    default ScreenInstance unwrap() {
        if (this instanceof final ScreenWrapper screenWrapper) {
            final Object versioned = screenWrapper.getVersionedScreen();
            if (versioned instanceof final LabyScreenAccessor labyScreen) {
                if (labyScreen.screen() instanceof Activity) {
                    return labyScreen.screen().mostInnerScreenInstance();
                }
            }
        }
        return this;
    }
}
