// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style;

import net.labymod.api.client.gui.lss.style.reader.SingleInstruction;
import java.util.Map;
import net.labymod.api.client.gui.lss.style.reader.StyleBlock;
import net.labymod.api.client.gui.lss.style.Selector;
import net.labymod.api.client.gui.lss.style.StyleInstructions;

public class DefaultStyleInstruction implements StyleInstructions
{
    private Selector selector;
    private final StyleBlock block;
    
    public DefaultStyleInstruction(final String rawSubSelector, final StyleBlock block) {
        this.selector = new DefaultSelector(rawSubSelector);
        this.block = block;
    }
    
    public DefaultStyleInstruction(final Selector selector, final StyleBlock block) {
        this.selector = selector;
        this.block = block;
    }
    
    @Override
    public Selector getSelector() {
        return this.selector;
    }
    
    public void setSelector(final Selector selector) {
        this.selector = selector;
    }
    
    @Override
    public Map<String, SingleInstruction> getInstructions() {
        return this.block.getInstructions();
    }
    
    @Override
    public StyleBlock getBlock() {
        return this.block;
    }
}
