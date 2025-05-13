// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.input;

import net.labymod.api.client.gui.screen.key.InputType;
import java.util.Iterator;
import net.labymod.api.client.gui.KeyboardUser;
import net.labymod.api.event.client.gui.screen.tree.ScreenPhase;
import net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.Key;
import javax.inject.Inject;
import net.labymod.api.event.client.gui.screen.tree.ScreenTreeTopRegistry;
import net.labymod.api.LabyAPI;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class KeyboardBridge
{
    private final LabyAPI labyAPI;
    private final ScreenTreeTopRegistry treeTopRegistry;
    
    @Inject
    public KeyboardBridge(final LabyAPI labyAPI, final ScreenTreeTopRegistry treeTopRegistry) {
        this.labyAPI = labyAPI;
        this.treeTopRegistry = treeTopRegistry;
    }
    
    public boolean onCharTyped(final Key key, final char character) {
        boolean result = false;
        for (final IngameOverlayActivity activity : Laby.labyAPI().ingameOverlay().getActivities()) {
            if (activity.isAcceptingInput()) {
                this.treeTopRegistry.charTyped(ScreenPhase.PRE, activity, key, character);
                if (activity.charTyped(key, character)) {
                    result = true;
                }
                this.treeTopRegistry.charTyped(ScreenPhase.POST, activity, key, character);
            }
        }
        return result;
    }
    
    public boolean onKeyPress(final Key key, final InputType inputType) {
        if (key == Key.ESCAPE) {
            return false;
        }
        boolean pressed = false;
        for (final IngameOverlayActivity activity : this.labyAPI.ingameOverlay().getActivities()) {
            if (activity.isAcceptingInput()) {
                this.treeTopRegistry.keyPressed(ScreenPhase.PRE, activity, key, inputType);
                if (activity.keyPressed(key, inputType)) {
                    pressed = true;
                }
                this.treeTopRegistry.keyPressed(ScreenPhase.POST, activity, key, inputType);
            }
        }
        return pressed;
    }
    
    public boolean onKeyRelease(final Key key, final InputType inputType) {
        if (key == Key.ESCAPE) {
            return false;
        }
        boolean pressed = false;
        for (final IngameOverlayActivity activity : this.labyAPI.ingameOverlay().getActivities()) {
            if (activity.isAcceptingInput()) {
                this.treeTopRegistry.keyReleased(ScreenPhase.PRE, activity, key, inputType);
                if (activity.keyReleased(key, inputType)) {
                    pressed = true;
                }
                this.treeTopRegistry.keyReleased(ScreenPhase.POST, activity, key, inputType);
            }
        }
        return pressed;
    }
}
