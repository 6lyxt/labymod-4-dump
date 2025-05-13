// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.type;

import org.jetbrains.annotations.NotNull;
import imgui.type.ImBoolean;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;

public class DefaultImGuiBooleanType implements ImGuiBooleanType
{
    private final ImBoolean value;
    
    public DefaultImGuiBooleanType() {
        this(new ImBoolean());
    }
    
    public DefaultImGuiBooleanType(final boolean value) {
        this(new ImBoolean(value));
    }
    
    public DefaultImGuiBooleanType(final ImBoolean value) {
        this.value = value;
    }
    
    public DefaultImGuiBooleanType(@NotNull final ImGuiBooleanType other) {
        this(new ImBoolean((ImBoolean)other.asImGuiType()));
    }
    
    @Override
    public boolean get() {
        return this.value.get();
    }
    
    @Override
    public void set(final boolean value) {
        this.value.set(value);
    }
    
    @Override
    public <T> T asImGuiType() {
        return (T)this.value;
    }
}
