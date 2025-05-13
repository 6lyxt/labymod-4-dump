// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.type;

import org.jetbrains.annotations.NotNull;
import imgui.type.ImDouble;
import net.labymod.api.client.gfx.imgui.type.ImGuiDoubleType;

public class DefaultImGuiDoubleType implements ImGuiDoubleType
{
    private final ImDouble value;
    
    public DefaultImGuiDoubleType() {
        this(new ImDouble());
    }
    
    public DefaultImGuiDoubleType(final double value) {
        this(new ImDouble(value));
    }
    
    public DefaultImGuiDoubleType(final ImDouble value) {
        this.value = value;
    }
    
    public DefaultImGuiDoubleType(@NotNull final ImGuiDoubleType other) {
        this(new ImDouble((ImDouble)other.asImGuiType()));
    }
    
    @Override
    public double get() {
        return this.value.get();
    }
    
    @Override
    public void set(final double value) {
        this.value.set(value);
    }
    
    @Override
    public <T> T asImGuiType() {
        return (T)this.value;
    }
}
