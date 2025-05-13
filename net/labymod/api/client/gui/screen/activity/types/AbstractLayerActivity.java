// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity.types;

import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.game.GameScreenRegistry;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import org.jetbrains.annotations.NotNull;
import java.util.Map;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenInstance;

public abstract class AbstractLayerActivity extends SimpleActivity
{
    protected ScreenInstance parentScreen;
    protected ParentMode mode;
    protected ParentMode renderMode;
    
    protected AbstractLayerActivity(final ScreenInstance parentScreen) {
        this.mode = ParentMode.OVERLAY;
        this.renderMode = null;
        this.parentScreen = parentScreen;
    }
    
    @Override
    public boolean shouldRenderBackground() {
        return false;
    }
    
    @Override
    public void initialize(final Parent parent) {
        if (this.mode == ParentMode.OVERLAY) {
            this.parentScreen.initialize(parent);
        }
        super.initialize(parent);
        if (this.mode == ParentMode.UNDERLAY) {
            this.parentScreen.initialize(parent);
        }
    }
    
    @Override
    public void resize(final int width, final int height) {
        if (this.mode == ParentMode.OVERLAY) {
            this.parentScreen.resize(width, height);
        }
        super.resize(width, height);
        if (this.mode == ParentMode.UNDERLAY) {
            this.parentScreen.resize(width, height);
        }
    }
    
    @Override
    public void render(final ScreenContext context) {
        final ParentMode mode = (this.renderMode != null) ? this.renderMode : this.mode;
        context.pushStack();
        this.transformActivity(context);
        if (mode == ParentMode.OVERLAY) {
            this.renderParent(context);
        }
        this.renderSuper(context);
        if (mode == ParentMode.UNDERLAY) {
            this.renderParent(context);
        }
        context.popStack();
    }
    
    protected void renderSuper(final ScreenContext environment) {
        super.render(environment);
    }
    
    protected void renderParent(final ScreenContext environment) {
        final GFXBridge gfx = Laby.gfx();
        gfx.storeBlaze3DStates();
        final Blaze3DGlStatePipeline glStateBridge = Laby.gfx().blaze3DGlStatePipeline();
        glStateBridge.alphaFunc(516, 0.1f);
        this.parentScreen.render(environment);
        glStateBridge.defaultAlphaFunc();
        gfx.restoreBlaze3DStates();
    }
    
    @Override
    public void renderOverlay(final ScreenContext context) {
        final ParentMode mode = (this.renderMode != null) ? this.renderMode : this.mode;
        if (mode == ParentMode.OVERLAY) {
            this.renderParentOverlay(context);
        }
        super.renderOverlay(context);
        if (mode == ParentMode.UNDERLAY) {
            this.renderParentOverlay(context);
        }
    }
    
    protected void renderParentOverlay(final ScreenContext context) {
        this.parentScreen.renderOverlay(context);
    }
    
    @Override
    public void onCloseScreen() {
        if (this.mode == ParentMode.OVERLAY) {
            this.parentScreen.onCloseScreen();
        }
        super.onCloseScreen();
        if (this.mode == ParentMode.UNDERLAY) {
            this.parentScreen.onCloseScreen();
        }
    }
    
    @Override
    public void tick() {
        if (this.mode == ParentMode.OVERLAY) {
            this.parentScreen.tick();
        }
        super.tick();
        if (this.mode == ParentMode.UNDERLAY) {
            this.parentScreen.tick();
        }
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        if (this.mode == ParentMode.UNDERLAY && this.parentScreen.keyPressed(key, type)) {
            return true;
        }
        if (key != Key.ESCAPE && super.keyPressed(key, type)) {
            return true;
        }
        if (this.mode != ParentMode.OVERLAY) {
            return super.keyPressed(key, type);
        }
        if (key != Key.ESCAPE || !this.shouldHandleEscape()) {
            return this.parentScreen.keyPressed(key, type);
        }
        return super.keyPressed(key, type);
    }
    
    @Override
    public boolean keyReleased(final Key key, final InputType type) {
        return (this.mode == ParentMode.UNDERLAY && this.parentScreen.keyReleased(key, type)) || super.keyReleased(key, type) || (this.mode == ParentMode.OVERLAY && this.parentScreen.keyReleased(key, type));
    }
    
    @Override
    public boolean charTyped(final Key key, final char character) {
        return (this.mode == ParentMode.UNDERLAY && this.parentScreen.charTyped(key, character)) || super.charTyped(key, character) || (this.mode == ParentMode.OVERLAY && this.parentScreen.charTyped(key, character));
    }
    
    @Override
    public boolean mouseReleased(final MutableMouse mouse, final MouseButton mouseButton) {
        return (this.mode == ParentMode.UNDERLAY && this.parentScreen.mouseReleased(mouse, mouseButton)) || super.mouseReleased(mouse, mouseButton) || (this.mode == ParentMode.OVERLAY && this.parentScreen.mouseReleased(mouse, mouseButton));
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        return (this.mode == ParentMode.UNDERLAY && this.parentScreen.mouseClicked(mouse, mouseButton)) || super.mouseClicked(mouse, mouseButton) || (this.mode == ParentMode.OVERLAY && this.parentScreen.mouseClicked(mouse, mouseButton));
    }
    
    @Override
    public boolean mouseScrolled(final MutableMouse mouse, final double scrollDelta) {
        return (this.mode == ParentMode.UNDERLAY && this.parentScreen.mouseScrolled(mouse, scrollDelta)) || super.mouseScrolled(mouse, scrollDelta) || (this.mode == ParentMode.OVERLAY && this.parentScreen.mouseScrolled(mouse, scrollDelta));
    }
    
    @Override
    public boolean mouseDragged(final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
        return (this.mode == ParentMode.UNDERLAY && this.parentScreen.mouseDragged(mouse, button, deltaX, deltaY)) || super.mouseDragged(mouse, button, deltaX, deltaY) || (this.mode == ParentMode.OVERLAY && this.parentScreen.mouseDragged(mouse, button, deltaX, deltaY));
    }
    
    @Override
    public void doScreenAction(final String action, final Map<String, Object> parameters) {
        if (this.mode == ParentMode.OVERLAY) {
            this.parentScreen.doScreenAction(action, parameters);
        }
        super.doScreenAction(action, parameters);
        if (this.mode == ParentMode.UNDERLAY) {
            this.parentScreen.doScreenAction(action, parameters);
        }
    }
    
    @NotNull
    @Override
    public Object mostInnerScreen() {
        return this.parentScreen.mostInnerScreen();
    }
    
    @NotNull
    @Override
    public ScreenInstance mostInnerScreenInstance() {
        return this.parentScreen.mostInnerScreenInstance();
    }
    
    @Override
    public boolean allowCustomFont() {
        if (this.parentScreen instanceof final ScreenWrapper screenWrapper) {
            final GameScreen screen = GameScreenRegistry.from(screenWrapper.getVersionedScreen());
            if (screen != null) {
                return screen.allowCustomFont();
            }
        }
        return super.allowCustomFont();
    }
    
    public enum ParentMode
    {
        UNDERLAY, 
        OVERLAY, 
        IGNORE_PARENT;
    }
}
