// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style.modifier.attribute;

import java.util.HashMap;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Locale;
import java.util.Map;

public class AttributeState
{
    private static final Map<String, AttributeState> STATES;
    public static final AttributeState NORMAL;
    public static final AttributeState SELECTED;
    public static final AttributeState FOCUS;
    public static final AttributeState ENABLED;
    public static final AttributeState HOVER;
    public static final AttributeState ACTIVE;
    public static final AttributeState DRAGGING;
    private final String name;
    private final String lowerName;
    private final int priority;
    private final boolean staticState;
    
    private AttributeState(final String name, final int priority, final boolean staticState) {
        this.name = name;
        this.lowerName = name.toLowerCase(Locale.ENGLISH);
        this.priority = priority;
        this.staticState = staticState;
    }
    
    public static AttributeState register(final String name, final int priority) {
        return register(name, priority, false);
    }
    
    public static AttributeState staticState(final String name, final int priority) {
        return register(name, priority, true);
    }
    
    private static AttributeState register(String name, final int priority, final boolean staticState) {
        name = name.toUpperCase(Locale.ENGLISH);
        if (AttributeState.STATES.containsKey(name)) {
            return AttributeState.STATES.get(name);
        }
        final AttributeState state = new AttributeState(name, priority, staticState);
        AttributeState.STATES.put(name, state);
        return state;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getLowerName() {
        return this.lowerName;
    }
    
    public int getPriority() {
        return this.priority;
    }
    
    public boolean isStaticState() {
        return this.staticState;
    }
    
    public boolean isEnabled(final Widget widget) {
        return this == AttributeState.NORMAL || widget.isAttributeStateEnabled(this);
    }
    
    @Override
    public String toString() {
        return this.lowerName;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final AttributeState state = (AttributeState)o;
        return this.name.equals(state.name);
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
    
    public static AttributeState getByName(final String name) {
        final AttributeState state = AttributeState.STATES.get(name.toUpperCase(Locale.ENGLISH));
        if (state == null) {
            throw new IllegalArgumentException("Unknown AttributeState: " + name);
        }
        return state;
    }
    
    static {
        STATES = new HashMap<String, AttributeState>();
        NORMAL = register("NORMAL", 0);
        SELECTED = register("SELECTED", 100);
        FOCUS = register("FOCUS", 200);
        ENABLED = register("ENABLED", 300);
        HOVER = register("HOVER", 400);
        ACTIVE = register("ACTIVE", 500);
        DRAGGING = register("DRAGGING", 600);
    }
}
