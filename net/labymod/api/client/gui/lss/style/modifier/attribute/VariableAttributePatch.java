// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style.modifier.attribute;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.reader.SingleInstruction;

public class VariableAttributePatch extends AttributePatch
{
    public VariableAttributePatch(final SingleInstruction instruction, final String rawValue) {
        super(instruction, rawValue);
    }
    
    @Override
    public void init() {
    }
    
    @Override
    public void patch(final Widget widget) {
        widget.setVariable(this.getKey(), this.rawValue());
    }
    
    @Override
    public void unpatch(final Widget widget) {
        widget.clearVariable(this.getKey());
    }
}
