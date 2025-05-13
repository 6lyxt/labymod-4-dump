// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.type;

import org.jetbrains.annotations.NotNull;
import imgui.type.ImFloat;
import net.labymod.api.client.gfx.imgui.type.ImGuiFloatType;

public class DefaultImGuiFloatType implements ImGuiFloatType
{
    private final ImFloat value;
    
    public DefaultImGuiFloatType() {
        this(new ImFloat());
    }
    
    public DefaultImGuiFloatType(final float value) {
        this(new ImFloat(value));
    }
    
    public DefaultImGuiFloatType(final ImFloat container) {
        this.value = container;
    }
    
    public DefaultImGuiFloatType(@NotNull final ImGuiFloatType other) {
        this(new ImFloat((ImFloat)other.asImGuiType()));
    }
    
    @Override
    public float get() {
        return this.value.get();
    }
    
    @Override
    public void set(final float value) {
        this.value.set(value);
    }
    
    @Override
    public <T> T asImGuiType() {
        return (T)this.value;
    }
}
