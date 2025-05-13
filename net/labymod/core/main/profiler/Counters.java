// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.profiler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class Counters
{
    static final List<Counter> COUNTERS;
    public static final Counter RENDER_CALL;
    public static final Counter RENDERED_COSMETICS;
    public static final Counter COSMETIC_TEXTURE_BINDINGS;
    
    public static void renderCounters(final int type) {
        for (final Counter counter : getCounters()) {
            if (counter.getType() == type) {
                counter.renderImGui();
            }
        }
    }
    
    public static List<Counter> getCounters() {
        return Counters.COUNTERS;
    }
    
    private static Counter create(final int type, final String name) {
        final Counter counter = new Counter(type, name);
        Counters.COUNTERS.add(counter);
        return counter;
    }
    
    static {
        COUNTERS = new ArrayList<Counter>();
        RENDER_CALL = create(CounterType.NONE, "Render Calls");
        RENDERED_COSMETICS = create(CounterType.COSMETICS, "Rendered Cosmetics");
        COSMETIC_TEXTURE_BINDINGS = create(CounterType.COSMETICS, "Cosmetic Texture Bindings");
    }
}
