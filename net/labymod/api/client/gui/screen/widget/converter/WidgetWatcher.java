// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.converter;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.render.Renderable;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

public class WidgetWatcher<W extends AbstractWidget<?>> implements Renderable
{
    private final WidgetConverterRegistry registry;
    private AbstractWidgetConverter<?, ?> widgetConverter;
    private boolean initialized;
    private Object lastSourceContent;
    private W widget;
    private boolean hasReplacement;
    
    public WidgetWatcher() {
        this.registry = Laby.references().widgetConverterRegistry();
        this.initialized = false;
        this.hasReplacement = false;
    }
    
    public void update(final Object source, final Object sourceContent) {
        if (!this.initialized || this.hasChanged(sourceContent)) {
            this.initialize(source);
        }
        if (this.widget != null) {
            this.registry.sync(source, this.widget);
        }
        this.lastSourceContent = sourceContent;
    }
    
    private boolean hasChanged(final Object sourceContent) {
        return !Objects.equals(sourceContent, this.lastSourceContent);
    }
    
    private void initialize(final Object source) {
        this.initialized = true;
        this.registry.findConverter(source.getClass()).ifPresent(converter -> {
            this.widgetConverter = converter;
            this.widget = (W)this.registry.convertWidget(source, converter, () -> this.hasReplacement = true);
            this.registry.registerWatcher(this);
        });
    }
    
    @Override
    public boolean render(final Stack stack, final MutableMouse mouse, final float tickDelta) {
        if (this.widget == null) {
            return false;
        }
        final ScreenContext screenContext2;
        final ScreenContext screenContext = screenContext2 = Laby.references().renderEnvironmentContext().screenContext();
        final AbstractWidget<?> widget = this.widget;
        Objects.requireNonNull(widget);
        screenContext2.runInContext(stack, mouse, tickDelta, widget::render);
        return true;
    }
    
    @Nullable
    public W getWidget() {
        return this.widget;
    }
    
    @Nullable
    public AbstractWidgetConverter<?, ?> getWidgetConverter() {
        return this.widgetConverter;
    }
    
    public boolean hasReplacement() {
        return this.hasReplacement;
    }
    
    public void invalidate() {
        this.lastSourceContent = null;
        this.initialized = false;
    }
    
    @Override
    public String toString() {
        return "WidgetWatcher{widget=" + String.valueOf(this.widget) + ", lastSourceContent=" + String.valueOf(this.lastSourceContent) + ", initialized=" + this.initialized + ", hasReplacement=" + this.hasReplacement;
    }
}
