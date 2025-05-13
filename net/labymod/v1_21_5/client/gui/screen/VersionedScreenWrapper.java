// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.gui.screen;

import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.core.client.gui.screen.accessor.FileDropHandler;
import java.nio.file.Path;
import java.util.List;
import net.labymod.api.client.gui.screen.key.MouseButton;
import java.util.Map;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.ScreenInstance;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.v1_21_5.client.gui.GuiGraphicsAccessor;
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
    private final fzq screen;
    private final ftk graphics;
    private Metadata metadata;
    private boolean initialized;
    
    public VersionedScreenWrapper(final fzq screen) {
        if (screen == null) {
            throw new NullPointerException("screen is null");
        }
        final fqq minecraft = fqq.Q();
        this.graphics = new ftk(minecraft, minecraft.aR().c());
        this.screen = screen;
        this.metadata = Metadata.create();
    }
    
    @Override
    public void onOpenScreen() {
    }
    
    @Override
    public void initialize(final Parent parent) {
        if (parent != null) {
            this.screen.b(fqq.Q(), (int)parent.bounds().getWidth(BoundsType.INNER), (int)parent.bounds().getHeight(BoundsType.INNER));
        }
        else {
            final Window window = Laby.labyAPI().minecraft().minecraftWindow();
            this.screen.b(fqq.Q(), window.getScaledWidth(), window.getScaledHeight());
        }
        this.initialized = true;
        if (this.screen instanceof final LabyScreenRenderer labyScreenRenderer) {
            labyScreenRenderer.setParentScreen(this);
        }
    }
    
    @Override
    public void render(final Stack stack, final MutableMouse mouse, final float partialTicks) {
        ((GuiGraphicsAccessor)this.graphics).setPose((fld)stack.getProvider().getPoseStack());
        this.screen.a(this.graphics, mouse.getX(), mouse.getY(), partialTicks);
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
    public void resize(final int width, final int height) {
        this.screen.b(fqq.Q(), width, height);
        this.initialized = true;
    }
    
    @Override
    public void render(final ScreenContext context) {
        ((GuiGraphicsAccessor)this.graphics).setPose((fld)context.stack().getProvider().getPoseStack());
        final MutableMouse mouse = context.mouse();
        this.screen.a(this.graphics, mouse.getX(), mouse.getY(), context.getTickDelta());
    }
    
    @Override
    public void tick() {
        this.screen.e();
    }
    
    @Override
    public void onCloseScreen() {
        if (this.initialized) {
            this.screen.aF_();
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
        return this.screen.a(key.getId(), 0, 0);
    }
    
    @Override
    public boolean keyReleased(final Key key, final InputType type) {
        return this.screen.c(key.getId(), 0, 0);
    }
    
    @Override
    public boolean charTyped(final Key key, final char character) {
        return this.screen.a(character, key.getId());
    }
    
    @Override
    public void doScreenAction(final String action, final Map<String, Object> parameters) {
        if (this.screen instanceof final LabyScreenRenderer labyScreenRenderer) {
            labyScreenRenderer.screen().doScreenAction(action, parameters);
        }
    }
    
    @Override
    public boolean mouseReleased(final MutableMouse mouse, final MouseButton mouseButton) {
        return this.screen.b(mouse.getXDouble(), mouse.getYDouble(), mouseButton.getId());
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        return this.screen.a(mouse.getXDouble(), mouse.getYDouble(), mouseButton.getId());
    }
    
    @Override
    public boolean mouseScrolled(final MutableMouse mouse, final double scrollDeltaY) {
        return this.screen.a(mouse.getXDouble(), mouse.getYDouble(), 0.0, scrollDeltaY);
    }
    
    @Override
    public boolean mouseDragged(final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
        return this.screen.a(mouse.getXDouble(), mouse.getYDouble(), button.getId(), deltaX, deltaY);
    }
    
    @Override
    public boolean fileDropped(final MutableMouse mouse, final List<Path> paths) {
        return ((FileDropHandler)this.screen).handleDroppedFiles(mouse, paths);
    }
    
    @Override
    public boolean isPauseScreen() {
        return this.screen.k();
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
