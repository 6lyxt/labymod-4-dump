// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.gui.screen;

import net.labymod.api.client.gui.screen.activity.Activity;
import java.nio.file.Path;
import java.util.List;
import net.labymod.api.client.gui.screen.key.MouseButton;
import java.util.Map;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.ScreenInstance;
import org.jetbrains.annotations.NotNull;
import net.labymod.v1_8_9.client.gui.GuiScreenAccessor;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.client.gui.screen.ScreenWrapper;

public class VersionedScreenWrapper implements ScreenWrapper
{
    private final axu screen;
    private Metadata metadata;
    private boolean initialized;
    private boolean resizing;
    
    public VersionedScreenWrapper(final axu screen) {
        if (screen == null) {
            throw new IllegalArgumentException("screen cannot be null");
        }
        this.screen = screen;
        this.metadata = Metadata.create();
    }
    
    @Override
    public void onOpenScreen() {
    }
    
    @Override
    public void initialize(final Parent parent) {
        if (parent != null) {
            this.resizeScreen((int)parent.bounds().getWidth(BoundsType.INNER), (int)parent.bounds().getHeight(BoundsType.INNER));
        }
        else {
            final Window window = Laby.labyAPI().minecraft().minecraftWindow();
            this.resizeScreen(window.getScaledWidth(), window.getScaledHeight());
        }
        this.initialized = true;
        if (this.screen instanceof final LabyScreenRenderer labyScreenRenderer) {
            labyScreenRenderer.setParentScreen(this);
        }
    }
    
    @Override
    public void render(final Stack stack, final MutableMouse mouse, final float partialTicks) {
        this.screen.a(mouse.getX(), mouse.getY(), partialTicks);
    }
    
    @Override
    public boolean renderBackground(final ScreenContext context) {
        return this.screen instanceof LabyScreenRenderer && ((LabyScreenRenderer)this.screen).screen().renderBackground(context);
    }
    
    @Override
    public void renderOverlay(final ScreenContext context) {
        if (this.screen instanceof final LabyScreenRenderer labyScreenRenderer) {
            labyScreenRenderer.screen().renderOverlay(context);
        }
    }
    
    @Override
    public void renderHoverComponent(final ScreenContext context) {
        if (this.screen instanceof final LabyScreenRenderer labyScreenRenderer) {
            labyScreenRenderer.screen().renderHoverComponent(context);
        }
    }
    
    @Override
    public void render(final ScreenContext context) {
        final MutableMouse mouse = context.mouse();
        this.screen.a(mouse.getX(), mouse.getY(), context.getTickDelta());
    }
    
    @Override
    public void resize(final int width, final int height) {
        this.resizeScreen(width, height);
        this.initialized = true;
    }
    
    private void resizeScreen(final int width, final int height) {
        if (this.resizing) {
            return;
        }
        this.resizing = true;
        ((GuiScreenAccessor)this.screen).resize(width, height);
        this.resizing = false;
    }
    
    @Override
    public void tick() {
        this.screen.e();
    }
    
    @Override
    public void onCloseScreen() {
        if (this.initialized) {
            this.screen.m();
        }
        if (this.screen instanceof final LabyScreenRenderer labyScreenRenderer) {
            labyScreenRenderer.setParentScreen(null);
        }
    }
    
    @NotNull
    @Override
    public Object mostInnerScreen() {
        if (this.screen instanceof final LabyScreenRenderer labyScreenRenderer) {
            return labyScreenRenderer.screen().mostInnerScreen();
        }
        return this.screen;
    }
    
    @NotNull
    @Override
    public ScreenInstance mostInnerScreenInstance() {
        if (this.screen instanceof final LabyScreenRenderer labyScreenRenderer) {
            return labyScreenRenderer.screen().mostInnerScreenInstance();
        }
        return this;
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        return ((LabyScreenRendererAccessor)this.screen).wrappedKeyPressed(key, type);
    }
    
    @Override
    public boolean keyReleased(final Key key, final InputType type) {
        return ((LabyScreenRendererAccessor)this.screen).wrappedKeyReleased(key, type);
    }
    
    @Override
    public boolean charTyped(final Key key, final char character) {
        return ((LabyScreenRendererAccessor)this.screen).wrappedCharTyped(key, character);
    }
    
    @Override
    public void doScreenAction(final String action, final Map<String, Object> parameters) {
        if (this.screen instanceof final LabyScreenRenderer labyScreenRenderer) {
            labyScreenRenderer.screen().doScreenAction(action, parameters);
        }
        if (action.equals("handleMouseInput")) {
            ((GuiScreenAccessor)this.screen).setHandleMouseInput(false);
            this.screen.k();
            ((GuiScreenAccessor)this.screen).setHandleMouseInput(true);
        }
    }
    
    @Override
    public boolean mouseReleased(final MutableMouse mouse, final MouseButton mouseButton) {
        return ((LabyScreenRendererAccessor)this.screen).wrappedMouseReleased(mouse, mouseButton.getId());
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        return ((LabyScreenRendererAccessor)this.screen).wrappedMouseClicked(mouse, mouseButton.getId());
    }
    
    @Override
    public boolean mouseScrolled(final MutableMouse mouse, final double scrollDelta) {
        ((LabyScreenRendererAccessor)this.screen).mouseScrolled(mouse, scrollDelta);
        return false;
    }
    
    @Override
    public boolean mouseDragged(final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
        return ((LabyScreenRendererAccessor)this.screen).wrappedMouseClickMove(mouse, button.getId(), deltaX, deltaY);
    }
    
    @Override
    public boolean fileDropped(final MutableMouse mouse, final List<Path> paths) {
        return ((LabyScreenRendererAccessor)this.screen).handleDroppedFiles(mouse, paths);
    }
    
    @Override
    public boolean isPauseScreen() {
        return this.screen.d();
    }
    
    @Override
    public Object getVersionedScreen() {
        return this.screen;
    }
    
    @Override
    public boolean isActivity() {
        return this.screen instanceof LabyScreenRenderer && ((LabyScreenRenderer)this.screen).screen() instanceof Activity;
    }
    
    @Override
    public Activity asActivity() {
        return (Activity)((LabyScreenRenderer)this.screen).screen();
    }
    
    @Override
    public void metadata(final Metadata metadata) {
        this.metadata = metadata;
    }
    
    @Override
    public Metadata metadata() {
        return this.metadata;
    }
}
