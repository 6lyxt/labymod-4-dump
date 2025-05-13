// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.gui.lss.variable.LssVariable;
import java.util.Map;
import java.util.Objects;
import net.labymod.api.client.gui.MinecraftWidgetBounds;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import java.util.Collections;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.widget.WidgetIdentifier;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.Laby;
import java.util.ArrayList;
import net.labymod.api.util.function.Mapper;
import java.util.List;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.variable.LssVariableHolder;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

public abstract class AbstractWidgetConverter<T, K extends AbstractWidget<?>> implements Parent, LssVariableHolder
{
    private static final ModifyReason COPY_MINECRAFT_BOUNDS;
    protected final ComponentMapper componentMapper;
    private final List<Mapper<T, K>> mappers;
    
    protected AbstractWidgetConverter() {
        this.mappers = new ArrayList<Mapper<T, K>>();
        this.componentMapper = Laby.labyAPI().minecraft().componentMapper();
    }
    
    @Override
    public Parent getParent() {
        return null;
    }
    
    @Override
    public Bounds bounds() {
        return null;
    }
    
    @Override
    public Parent getRoot() {
        return null;
    }
    
    public K convert(final T source) {
        for (final Mapper<T, K> mapper : this.mappers) {
            final K mappedWidget = mapper.map(source);
            if (mappedWidget == null) {
                continue;
            }
            return mappedWidget;
        }
        return this.createDefault(source);
    }
    
    public abstract K createDefault(final T p0);
    
    public void registerMapper(final Mapper<T, K> mapper) {
        if (mapper == null) {
            return;
        }
        this.mappers.add(mapper);
    }
    
    public abstract void update(final T p0, final K p1);
    
    public abstract String getName();
    
    @Deprecated(forRemoval = true, since = "4.1.0")
    public final String findWidgetId(final T source) {
        if (source instanceof final WidgetIdentifier widgetIdentifier) {
            return widgetIdentifier.getIdentifier();
        }
        return this.getWidgetId(source);
    }
    
    public final List<String> findWidgetIds(final T source) {
        if (source instanceof final WidgetIdentifier identifier) {
            return identifier.getIdentifiers();
        }
        return this.getWidgetIds(source);
    }
    
    @Deprecated(forRemoval = true, since = "4.1.0")
    @Nullable
    public String getWidgetId(final T source) {
        return null;
    }
    
    public List<String> getWidgetIds(final T source) {
        final String widgetId = this.getWidgetId(source);
        if (widgetId == null) {
            return Collections.emptyList();
        }
        return List.of(widgetId);
    }
    
    public boolean mouseClicked(final K widget, final MutableMouse mouse, final MouseButton mouseButton) {
        return widget != null && widget.isHovered() && widget.mouseClicked(mouse, mouseButton);
    }
    
    public boolean mouseReleased(final K widget, final MutableMouse mouse, final MouseButton mouseButton) {
        return widget != null && widget.isHovered() && widget.mouseReleased(mouse, mouseButton);
    }
    
    public boolean mouseScrolled(final K widget, final MutableMouse mouse, final double scrollDelta) {
        return widget != null && widget.isHovered() && widget.mouseScrolled(mouse, scrollDelta);
    }
    
    public boolean mouseDragged(final K widget, final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
        return widget != null && widget.isHovered() && widget.mouseDragged(mouse, button, deltaX, deltaY);
    }
    
    public boolean keyPressed(final K widget, final Key key, final InputType type) {
        return widget != null && widget.keyPressed(key, type);
    }
    
    public boolean keyReleased(final K widget, final Key key, final InputType type) {
        return widget != null && widget.keyReleased(key, type);
    }
    
    public boolean charTyped(final K widget, final Key key, final char character) {
        return widget != null && widget.charTyped(key, character);
    }
    
    protected Component mapComponent(final Object sourceComponent) {
        return this.componentMapper.fromMinecraftComponent(sourceComponent);
    }
    
    protected void copyBounds(final T source, final K destination) {
        final MinecraftWidgetBounds widgetBounds = MinecraftWidgetBounds.self(source);
        if (widgetBounds == null) {
            return;
        }
        final Bounds bounds = destination.bounds();
        bounds.setOuterPosition((float)widgetBounds.getBoundsX(), (float)widgetBounds.getBoundsY(), AbstractWidgetConverter.COPY_MINECRAFT_BOUNDS);
        bounds.setOuterSize((float)widgetBounds.getBoundsWidth(), (float)widgetBounds.getBoundsHeight(), AbstractWidgetConverter.COPY_MINECRAFT_BOUNDS);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final AbstractWidgetConverter<?, ?> that = (AbstractWidgetConverter<?, ?>)o;
        return Objects.equals(this.getName(), that.getName());
    }
    
    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }
    
    @Override
    public LssVariableHolder getParentVariableHolder() {
        return this.window();
    }
    
    @Override
    public Map<String, LssVariable> getLssVariables() {
        return this.window().getLssVariables();
    }
    
    @Override
    public void updateLssVariable(final LssVariable variable) {
        this.window().updateLssVariable(variable);
    }
    
    @Override
    public void forceUpdateLssVariable(final LssVariable variable) {
        this.window().forceUpdateLssVariable(variable);
    }
    
    private Window window() {
        return Laby.labyAPI().minecraft().minecraftWindow();
    }
    
    static {
        COPY_MINECRAFT_BOUNDS = ModifyReason.of("copyMinecraftBounds");
    }
}
