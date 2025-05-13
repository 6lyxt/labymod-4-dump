// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.window;

import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.window.WindowResizeEvent;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.render.draw.RectangleRenderer;
import java.util.Map;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import java.nio.file.Path;
import java.util.List;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gfx.pipeline.util.Scissor;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.ScreenUser;
import net.labymod.api.client.gui.KeyboardUser;
import net.labymod.api.client.gui.MouseUser;
import net.labymod.api.util.bounds.DefaultRectangle;

@Deprecated
public abstract class AbstractScreenWindow extends DefaultRectangle implements MouseUser, KeyboardUser, ScreenUser
{
    public static final float BORDER_THICKNESS = 1.0f;
    protected final LabyAPI labyAPI;
    protected Component title;
    protected boolean dragging;
    protected boolean rescale;
    protected float dragOffsetX;
    protected float dragOffsetY;
    protected long lastDragBarInteraction;
    protected boolean enabled;
    protected boolean initialized;
    private float lastDragBarHeight;
    
    public AbstractScreenWindow(final String title) {
        this(Component.text(title));
    }
    
    public AbstractScreenWindow(final Component title) {
        this.title = title;
        this.labyAPI = Laby.labyAPI();
        this.dragging = false;
        this.rescale = false;
        this.lastDragBarInteraction = -1L;
        this.enabled = false;
    }
    
    public void initialize() {
    }
    
    public void renderWindow(final Stack stack, final MutableMouse mouse) {
        if (!this.enabled) {
            return;
        }
        if (!this.initialized) {
            this.initialize();
            this.initialized = true;
        }
        this.renderFrame(stack, mouse);
        if (this.getHeight() == 0.0f) {
            return;
        }
        final Scissor scissor = this.labyAPI.gfxRenderPipeline().scissor();
        try {
            scissor.push(stack, this);
            this.render(stack, mouse);
        }
        finally {
            scissor.pop();
        }
        this.renderOverlay(stack, mouse);
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton button) {
        if (!this.enabled) {
            return false;
        }
        if (button.isLeft() && this.isHoveredTitleBar(mouse)) {
            final long duration = TimeUtil.getMillis() - this.lastDragBarInteraction;
            if (duration > 10L && duration < 250L) {
                this.setHeight((this.getHeight() == 0.0f) ? 100.0f : 0.0f);
            }
            this.dragging = true;
            this.dragOffsetX = mouse.getX() - this.left;
            this.dragOffsetY = mouse.getY() - this.top;
            this.lastDragBarInteraction = TimeUtil.getMillis();
            return true;
        }
        return button.isLeft() && this.isHoveredRescaleButton(mouse) && (this.rescale = true);
    }
    
    @Override
    public boolean mouseReleased(final MutableMouse mouse, final MouseButton button) {
        if (!this.enabled) {
            return false;
        }
        this.dragging = false;
        return this.rescale = false;
    }
    
    @Override
    public boolean mouseScrolled(final MutableMouse mouse, final double scrollDelta) {
        return false;
    }
    
    @Override
    public boolean mouseDragged(final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
        return false;
    }
    
    @Override
    public boolean fileDropped(final MutableMouse mouse, final List<Path> paths) {
        return false;
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        return false;
    }
    
    @Override
    public boolean keyReleased(final Key key, final InputType type) {
        return false;
    }
    
    @Override
    public boolean charTyped(final Key key, final char character) {
        return false;
    }
    
    @Override
    public void doScreenAction(final String action, final Map<String, Object> parameters) {
    }
    
    public Component title() {
        return this.title;
    }
    
    public void resize() {
        this.initialized = false;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    protected void renderFrame(final Stack stack, final MutableMouse mouse) {
        final ComponentRenderer componentRenderer = this.labyAPI.renderPipeline().componentRenderer();
        if (this.dragging) {
            this.setPosition(mouse.getX() - this.dragOffsetX, mouse.getY() - this.dragOffsetY);
        }
        if (this.rescale) {
            this.setRight(Math.max(this.left + 100.0f, (float)mouse.getX()));
            this.setBottom(Math.max(this.top, (float)mouse.getY()));
        }
        final RectangleRenderer rectangleRenderer = this.labyAPI.renderPipeline().rectangleRenderer();
        this.lastDragBarHeight = componentRenderer.height() + 1.0f;
        rectangleRenderer.pos(this.getRight() - 1.0f - 1.0f, this.getBottom() - 1.0f - 1.0f, this.getRight() - 0.75f + 2.0f, this.getBottom() - 0.75f + 2.0f).color(this.isHoveredRescaleButton(mouse) ? -1 : -4474061).render(stack);
        rectangleRenderer.pos(this.getLeft() - 1.0f, this.getTop() - 1.0f - this.lastDragBarHeight, this.getRight() + 1.0f, this.getBottom() + 1.0f).color(ColorFormat.ARGB32.pack(460551, 250)).rounded(2.0f).render(stack);
        rectangleRenderer.pos(this).color(ColorFormat.ARGB32.pack(3355443, 100)).render(stack);
        componentRenderer.builder().text(RenderableComponent.of(this.title())).pos(this.getLeft() + 1.0f, this.getTop() - this.lastDragBarHeight + 1.0f).color(this.isHoveredTitleBar(mouse) ? -1 : -188).allowColors(true).render(stack);
    }
    
    protected abstract void render(final Stack p0, final MutableMouse p1);
    
    protected void renderOverlay(final Stack stack, final MutableMouse mouse) {
    }
    
    protected boolean isHoveredRescaleButton(final MutableMouse mouse) {
        return mouse.getX() >= this.right - 4.0f && mouse.getY() >= this.bottom - 4.0f && mouse.getX() <= this.right + 4.0f && mouse.getY() <= this.bottom + 4.0f;
    }
    
    protected boolean isHoveredTitleBar(final MutableMouse mouse) {
        return this.isXInRectangle((float)mouse.getX()) && mouse.getY() >= this.top - this.lastDragBarHeight && mouse.getY() <= this.top;
    }
    
    public boolean dragging() {
        return this.dragging;
    }
    
    @Subscribe
    public void onWindowResize(final WindowResizeEvent event) {
        this.resize();
    }
}
