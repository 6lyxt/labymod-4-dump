// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.window.debug.bounds;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.util.TextFormat;
import net.labymod.api.util.bounds.Point;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.screen.Parent;
import java.util.Optional;
import net.labymod.api.client.render.font.ComponentRenderMeta;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.LabyScreen;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.KeyHandler;
import net.labymod.api.event.client.input.KeyEvent;
import java.util.HashMap;
import net.labymod.api.util.bounds.RectangleModification;
import net.labymod.api.util.bounds.RectangleState;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Map;
import net.labymod.api.client.component.Component;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.core.client.gui.screen.widget.window.AbstractCoreScreenWindowOverlay;

public class DefaultBoundsDebugScreenWindowOverlay extends AbstractCoreScreenWindowOverlay
{
    private static final float ARROW_LENGTH = 3.0f;
    private static final float TEXT_PADDING = 4.0f;
    private static final float TEXT_MARGIN = 10.0f;
    private static final Rectangle EMPTY;
    private Component component;
    private boolean fixed;
    private boolean expanded;
    private boolean storeMods;
    private final Map<Widget, Map<RectangleState, RectangleModification>> lastMods;
    
    public DefaultBoundsDebugScreenWindowOverlay() {
        super(1400);
        this.component = Component.empty();
        this.fixed = false;
        this.expanded = false;
        this.storeMods = false;
        this.lastMods = new HashMap<Widget, Map<RectangleState, RectangleModification>>();
    }
    
    @Subscribe
    public void onKey(final KeyEvent event) {
        if (!this.labyAPI.labyModLoader().isLabyModDevelopmentEnvironment()) {
            return;
        }
        if (event.state() != KeyEvent.State.PRESS || this.labyAPI.minecraft().isMouseLocked()) {
            return;
        }
        if (KeyHandler.isControlDown() && event.key() == Key.J) {
            this.enabled = !this.enabled;
            this.fixed = false;
            for (final Activity activity : Laby.references().activityController().getOpenActivities()) {
                this.adaptRecording(activity);
            }
        }
        if (KeyHandler.isControlDown() && event.key() == Key.I) {
            this.expanded = !this.expanded;
        }
        if (this.enabled && KeyHandler.isControlDown() && event.key() == Key.U) {
            this.fixed = !this.fixed;
            if (this.fixed) {
                this.storeMods = true;
            }
        }
    }
    
    private void adaptRecording(final Activity activity) {
        for (final Widget child : activity.document().getChildren()) {
            if (this.enabled) {
                child.bounds().recordModifications();
            }
            else {
                child.bounds().stopRecordingModifications();
            }
            if (child instanceof final ScreenRendererWidget screenRendererWidget) {
                final LabyScreen screen = screenRendererWidget.currentLabyScreen();
                if (!(screen instanceof Activity)) {
                    continue;
                }
                this.adaptRecording((Activity)screen);
            }
        }
    }
    
    @Override
    public void renderWindow(final Stack stack, final MutableMouse mouse) {
        if (!this.isEnabled()) {
            return;
        }
        if (!this.component.children().isEmpty()) {
            final Window window = this.labyAPI.minecraft().minecraftWindow();
            final RenderableComponent component = RenderableComponent.of(this.component);
            float fontSize = 1.0f;
            if (component.getHeight() > window.getScaledHeight() - 10.0f) {
                fontSize = Math.max((window.getScaledHeight() - 10.0f) / component.getHeight() - 0.05f, 0.55f);
            }
            final float width = component.getWidth() * fontSize;
            final float height = component.getHeight() * fontSize;
            final float x = (this.fixed || mouse.getXDouble() > window.getScaledWidth() / 2.0f) ? 10.0f : (window.getScaledWidth() - 10.0f - width - 4.0f);
            final float y = (this.fixed || mouse.getYDouble() > window.getScaledHeight() / 2.0f) ? 10.0f : (window.getScaledHeight() - 10.0f - height - 4.0f);
            this.labyAPI.renderPipeline().rectangleRenderer().pos(x - 4.0f, y - 4.0f).size(width + 8.0f, height + 8.0f).rounded(5.0f).gradientVertical(ColorFormat.ARGB32.pack(11141120, 255), ColorFormat.ARGB32.pack(4456448, 255)).render(stack);
            final ComponentRenderer componentRenderer = this.labyAPI.renderPipeline().componentRenderer();
            final ComponentRenderMeta meta = componentRenderer.builder().pos(x / fontSize, y / fontSize).mousePos(new MutableMouse(mouse.getXDouble() / fontSize, mouse.getYDouble() / fontSize)).text(component).scale(fontSize).render(stack);
            if (this.fixed) {
                final Optional<RenderableComponent> optional = meta.getHovered();
                if (optional.isPresent()) {
                    final RenderableComponent renderableComponent = optional.get();
                    final Component hoverValue = renderableComponent.getHoverValue(HoverEvent.Action.SHOW_TEXT);
                    if (hoverValue != null) {
                        componentRenderer.renderHoverComponent(stack, mouse, RenderableComponent.of(hoverValue));
                    }
                }
            }
        }
        if (!this.fixed) {
            this.lastMods.clear();
            this.component = Component.empty();
        }
        this.storeMods = false;
    }
    
    @Override
    public void widgetPreInitialize(final Widget widget, final Parent parent) {
        if (!this.isEnabled()) {
            return;
        }
        widget.bounds().recordModifications();
    }
    
    @Override
    public void renderWidget(final Stack stack, final MutableMouse mouse, final Widget widget) {
        if (!this.isEnabled()) {
            return;
        }
        widget.bounds().recordModifications();
        Map<RectangleState, RectangleModification> mods = widget.bounds().lastModifications();
        if (this.fixed && !this.storeMods) {
            mods = this.lastMods.get(widget);
        }
        if (mods != null && !mods.isEmpty()) {
            this.renderMods(stack, mouse, widget, mods);
            if (this.storeMods) {
                this.lastMods.put(widget, mods);
            }
        }
    }
    
    private void renderMods(final Stack stack, final MutableMouse mouse, final Widget widget, final Map<RectangleState, RectangleModification> mods) {
        Component component = Component.text(widget.getTypeName() + " (" + String.join(", ", widget.getIds()), NamedTextColor.GOLD);
        component = this.renderHorizontal(stack, widget, mouse, mods.get(RectangleState.LEFT), component);
        component = this.renderVertical(stack, widget, mouse, mods.get(RectangleState.TOP), component);
        component = this.renderHorizontal(stack, widget, mouse, mods.get(RectangleState.RIGHT), component);
        component = this.renderVertical(stack, widget, mouse, mods.get(RectangleState.BOTTOM), component);
        component = this.renderHorizontal(stack, widget, mouse, mods.get(RectangleState.WIDTH), component);
        component = this.renderVertical(stack, widget, mouse, mods.get(RectangleState.HEIGHT), component);
        if (this.isWidgetHovered(stack, widget, mouse) && !component.children().isEmpty() && (!this.fixed || this.storeMods)) {
            if (this.component.children().isEmpty()) {
                this.component = this.component.append(component);
            }
            else {
                this.component = this.component.append(((BaseComponent<Component>)Component.newline().append(Component.newline())).append(component));
            }
        }
    }
    
    private Component renderHorizontal(final Stack stack, final Widget widget, final MutableMouse mouse, final RectangleModification mod, final Component component) {
        if (mod != null && !mod.reason().isRenderOnly()) {
            return component.append(this.makeComponent(mod, this.isHovered(mouse, this.renderHorizontal(stack, widget.bounds(), mod)), true));
        }
        return component;
    }
    
    private Component renderVertical(final Stack stack, final Widget widget, final MutableMouse mouse, final RectangleModification mod, final Component component) {
        if (mod != null && !mod.reason().isRenderOnly()) {
            return component.append(this.makeComponent(mod, this.isHovered(mouse, this.renderVertical(stack, widget.bounds(), mod)), true));
        }
        return component;
    }
    
    private boolean isWidgetHovered(final Stack stack, final Widget widget, final MutableMouse mouse) {
        Rectangle rect = widget.bounds().rectangle(BoundsType.MIDDLE);
        if (rect.getWidth() == 0.0f || rect.getHeight() == 0.0f) {
            rect = rect.copy().expand((rect.getWidth() == 0.0f) ? 2.0f : 0.0f, (rect.getHeight() == 0.0f) ? 2.0f : 0.0f);
            if (rect.distanceSquared(mouse) < 400.0f) {
                this.labyAPI.renderPipeline().rectangleRenderer().renderOutline(stack, rect, ColorFormat.ARGB32.pack(NamedTextColor.RED.value(), 255), 1.0f);
            }
            if (rect.isInRectangle(mouse)) {
                return true;
            }
        }
        return widget.isHovered();
    }
    
    private boolean isHovered(final MutableMouse mouse, final Rectangle rectangle) {
        return rectangle.copy().expand(6.0f).isInRectangle(mouse);
    }
    
    private Component makeComponent(final RectangleModification mod, final boolean hovered, final boolean addHoverEvent) {
        final String modName = TextFormat.SNAKE_CASE.toCamelCase(mod.state().name(), false);
        Component component = ((BaseComponent<Component>)Component.newline().append(Component.text(modName, NamedTextColor.GRAY)).append(Component.text(": ", NamedTextColor.DARK_GRAY)).append(Component.text(mod.reason().reason(), NamedTextColor.WHITE)).append(Component.text(" (", NamedTextColor.GRAY)).append(Component.text(mod.from(), NamedTextColor.YELLOW)).append(Component.text(" -> ", NamedTextColor.GRAY)).append(Component.text(mod.to(), NamedTextColor.YELLOW)).append(Component.text(" | \u0394 ", NamedTextColor.GRAY)).append(Component.text(mod.to() - mod.from(), NamedTextColor.YELLOW)).append(Component.text(")", NamedTextColor.GRAY)).append(Component.newline()).append(Component.text("            from ", NamedTextColor.GRAY))).append(Component.text(mod.reason().source().getSimpleName(), NamedTextColor.YELLOW));
        final LoadedAddon addon = mod.reason().sourceAddon();
        if (addon != null) {
            component = component.append(Component.text(" (Addon: ", NamedTextColor.GRAY)).append(Component.text(addon.info().getDisplayName(), NamedTextColor.YELLOW)).append(Component.text(")", NamedTextColor.GRAY));
        }
        if (hovered) {
            component = component.decorate(TextDecoration.ITALIC).decorate(TextDecoration.BOLD).decorate(TextDecoration.UNDERLINED);
        }
        if (addHoverEvent) {
            final int max = this.expanded ? 5 : 15;
            Component hover = ((BaseComponent<Component>)Component.empty()).append(Component.text("All modifications (max. " + max + "):", NamedTextColor.GRAY, TextDecoration.BOLD));
            RectangleModification current = mod;
            int i = 0;
            do {
                hover = hover.append(Component.newline()).append(Component.text(" - ", NamedTextColor.WHITE)).append(Component.text(current.reason().reason(), NamedTextColor.GRAY)).append(Component.text(": ", NamedTextColor.DARK_GRAY)).append(Component.text(current.from(), NamedTextColor.YELLOW)).append(Component.text(" -> ", NamedTextColor.GRAY)).append(Component.text(current.to(), NamedTextColor.YELLOW)).append(Component.text(" <- ", NamedTextColor.GRAY)).append(Component.text(current.reason().source().getSimpleName(), NamedTextColor.YELLOW)).append(Component.text(" === ", NamedTextColor.DARK_GRAY)).append(Component.text("Frame", NamedTextColor.GRAY)).append(Component.text(": ", NamedTextColor.DARK_GRAY)).append(Component.text(current.frame(), NamedTextColor.WHITE)).append(Component.text(" / From")).append(Component.text(": ", NamedTextColor.DARK_GRAY));
                if (this.expanded) {
                    final StackTraceElement[] stackTrace = current.externalStackTrace();
                    for (int j = 0; j < Math.min(20, stackTrace.length); ++j) {
                        hover = hover.append(Component.newline()).append(Component.text("   - ", NamedTextColor.GRAY)).append(this.stackTraceToComponent(stackTrace[j]));
                    }
                }
                else {
                    final StackTraceElement trace = current.lastExternalTrace();
                    hover = hover.append(this.stackTraceToComponent(trace));
                }
            } while (i++ < max && (current = current.getPreviousModification()) != null);
            component = component.hoverEvent(HoverEvent.showText(hover));
        }
        return component;
    }
    
    private Component stackTraceToComponent(final StackTraceElement element) {
        final String[] className = element.getClassName().split("\\.");
        return Component.text(className[className.length - 1] + "." + element.getMethodName() + ":" + element.getLineNumber(), NamedTextColor.YELLOW);
    }
    
    private Rectangle renderVertical(final Stack stack, final Bounds bounds, final RectangleModification mod) {
        if (mod.reason().isRenderOnly()) {
            return DefaultBoundsDebugScreenWindowOverlay.EMPTY;
        }
        final float fromY = mod.from();
        final float toY = mod.to();
        final float x = bounds.getCenterX();
        return this.renderArrow(stack, x, fromY, x, toY);
    }
    
    private Rectangle renderHorizontal(final Stack stack, final Bounds bounds, final RectangleModification mod) {
        if (mod.reason().isRenderOnly()) {
            return DefaultBoundsDebugScreenWindowOverlay.EMPTY;
        }
        final float fromX = mod.from();
        final float toX = mod.to();
        final float y = bounds.getCenterY();
        return this.renderArrow(stack, fromX, y, toX, y);
    }
    
    private Rectangle renderArrow(final Stack stack, final float fromX, final float fromY, final float toX, final float toY) {
        final RenderPipeline pipeline = this.labyAPI.renderPipeline();
        final float ax = toX + ((fromX == toX) ? 3.0f : ((fromX > toX) ? 3.0f : -3.0f));
        final float ay = toY + ((fromY == toY) ? 3.0f : ((fromY > toY) ? 3.0f : -3.0f));
        final float ax2 = toX + ((fromX == toX) ? -3.0f : ((fromX > toX) ? 3.0f : -3.0f));
        final float ay2 = toY + ((fromY == toY) ? -3.0f : ((fromY > toY) ? 3.0f : -3.0f));
        pipeline.renderContexts().lineRenderContext().begin(stack).renderGradient(fromX, fromY, toX, toY, ColorFormat.ARGB32.pack(7244288, 255), ColorFormat.ARGB32.pack(13434626, 187)).render(toX, toY, ax, ay, 1.0f, 0.0f, 0.0f, 1.0f).render(toX, toY, ax2, ay2, 1.0f, 0.0f, 0.0f, 1.0f).uploadToBuffer();
        return Rectangle.absolute(ax, ay, ax2 + ((fromY == toY) ? ((fromX < toX) ? 3.0f : -3.0f) : 0.0f), ay2 + ((fromX == toX) ? ((fromY < toY) ? 3.0f : -3.0f) : 0.0f));
    }
    
    @Override
    public void preRenderActivity(final Stack stack, final MutableMouse mouse, final Activity activity) {
    }
    
    static {
        EMPTY = Rectangle.absolute(0.0f, 0.0f, 0.0f, 0.0f);
    }
}
