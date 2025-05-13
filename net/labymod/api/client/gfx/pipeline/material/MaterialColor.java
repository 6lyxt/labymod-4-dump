// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.material;

import net.labymod.api.util.Color;
import java.util.function.Supplier;

public class MaterialColor
{
    private Supplier<Color> colorSupplier;
    private long cycle;
    private boolean rainbow;
    
    public MaterialColor() {
        this(null);
    }
    
    public MaterialColor(final Color color) {
        this(color, 0L, false);
    }
    
    public MaterialColor(final Color color, final long cycle, final boolean rainbow) {
        this.colorSupplier = (() -> color);
        this.cycle = cycle;
        this.rainbow = rainbow;
    }
    
    public Color getColor() {
        return this.colorSupplier.get();
    }
    
    public void setColor(final Color color) {
        this.colorSupplier = (() -> color);
    }
    
    public void setColor(final Supplier<Color> colorSupplier) {
        this.colorSupplier = colorSupplier;
    }
    
    public long getCycle() {
        return this.cycle;
    }
    
    public void setCycle(final long cycle) {
        this.cycle = cycle;
    }
    
    public boolean isRainbow() {
        return this.rainbow;
    }
    
    public void setRainbow(final boolean rainbow) {
        this.rainbow = rainbow;
    }
}
