// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style.modifier.attribute;

import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.LssStyleException;
import net.labymod.api.client.gui.lss.style.StyleInstructions;
import net.labymod.api.client.gui.lss.style.reader.SingleInstruction;

public abstract class AttributePatch
{
    private final SingleInstruction instruction;
    private final String rawValue;
    private StyleInstructions meta;
    
    public AttributePatch(final SingleInstruction instruction, final String rawValue) {
        this.instruction = instruction;
        this.rawValue = rawValue;
    }
    
    public String getKey() {
        return this.instruction.getKey();
    }
    
    public SingleInstruction instruction() {
        return this.instruction;
    }
    
    public String rawValue() {
        return this.rawValue;
    }
    
    public void setMeta(final StyleInstructions meta) {
        this.meta = meta;
    }
    
    public StyleInstructions getMeta() {
        return this.meta;
    }
    
    public abstract void init() throws LssStyleException;
    
    public abstract void patch(final Widget p0);
    
    public abstract void unpatch(final Widget p0);
    
    public boolean equalsKey(final AttributePatch patch) {
        return this.getKey().equals(patch.getKey());
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final AttributePatch that = (AttributePatch)o;
        return Objects.equals(this.getKey(), that.getKey()) && Objects.equals(this.rawValue(), that.rawValue());
    }
    
    @Override
    public int hashCode() {
        int result = (this.instruction != null) ? this.instruction.hashCode() : 0;
        result = 31 * result + ((this.rawValue != null) ? this.rawValue.hashCode() : 0);
        result = 31 * result + ((this.meta != null) ? this.meta.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return this.instruction.toString();
    }
}
