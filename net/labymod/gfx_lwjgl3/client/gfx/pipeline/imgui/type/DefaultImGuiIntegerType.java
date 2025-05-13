// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.type;

import org.jetbrains.annotations.NotNull;
import imgui.type.ImInt;
import net.labymod.api.client.gfx.imgui.type.ImGuiIntegerType;

public class DefaultImGuiIntegerType implements ImGuiIntegerType
{
    private final ImInt value;
    
    public DefaultImGuiIntegerType() {
        this(new ImInt());
    }
    
    public DefaultImGuiIntegerType(final int value) {
        this(new ImInt(value));
    }
    
    public DefaultImGuiIntegerType(final ImInt value) {
        this.value = value;
    }
    
    public DefaultImGuiIntegerType(@NotNull final ImGuiIntegerType other) {
        this(new ImInt((ImInt)other.asImGuiType()));
    }
    
    @Override
    public int get() {
        return this.value.get();
    }
    
    @Override
    public void set(final int value) {
        this.value.set(value);
    }
    
    @Override
    public <T> T asImGuiType() {
        return (T)this.value;
    }
}
