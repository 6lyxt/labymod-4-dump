// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.type;

import org.jetbrains.annotations.NotNull;
import imgui.type.ImLong;
import net.labymod.api.client.gfx.imgui.type.ImGuiLongType;

public class DefaultImGuiLongType implements ImGuiLongType
{
    private final ImLong value;
    
    public DefaultImGuiLongType() {
        this(new ImLong());
    }
    
    public DefaultImGuiLongType(final long value) {
        this(new ImLong(value));
    }
    
    public DefaultImGuiLongType(final ImLong value) {
        this.value = value;
    }
    
    public DefaultImGuiLongType(@NotNull final ImGuiLongType other) {
        this(new ImLong((ImLong)other.asImGuiType()));
    }
    
    @Override
    public long get() {
        return this.value.get();
    }
    
    @Override
    public void set(final long value) {
        this.value.set(value);
    }
    
    @Override
    public <T> T asImGuiType() {
        return (T)this.value;
    }
}
