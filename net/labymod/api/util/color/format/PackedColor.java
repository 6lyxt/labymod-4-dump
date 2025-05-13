// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.color.format;

import org.jetbrains.annotations.NotNull;

record PackedColor(ColorFormat colorFormat, int value) {
    public int red() {
        return this.colorFormat.red(this.value);
    }
    
    public int green() {
        return this.colorFormat.green(this.value);
    }
    
    public int blue() {
        return this.colorFormat.blue(this.value);
    }
    
    public int alpha() {
        return this.colorFormat.alpha(this.value);
    }
    
    public int packTo(@NotNull final ColorFormat destinationFormat) {
        return this.colorFormat.packTo(destinationFormat, this.value);
    }
}
