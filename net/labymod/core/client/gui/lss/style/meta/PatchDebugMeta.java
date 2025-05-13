// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.meta;

import net.labymod.api.client.gui.lss.style.reader.StyleBlock;
import net.labymod.core.client.gui.lss.style.DefaultStyleInstruction;
import net.labymod.api.client.gui.lss.style.modifier.attribute.PatchMeta;

public class PatchDebugMeta implements PatchMeta
{
    private final DefaultStyleInstruction instruction;
    
    public PatchDebugMeta(final DefaultStyleInstruction instruction) {
        this.instruction = instruction;
    }
    
    @Override
    public StyleBlock block() {
        return this.instruction.getBlock();
    }
    
    public DefaultStyleInstruction getInstruction() {
        return this.instruction;
    }
}
