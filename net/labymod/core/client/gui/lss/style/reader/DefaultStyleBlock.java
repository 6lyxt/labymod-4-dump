// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.reader;

import net.labymod.api.client.gui.lss.style.modifier.attribute.StyleInstructions;
import java.util.Iterator;
import net.labymod.core.client.gui.lss.style.DefaultStyleInstruction;
import java.util.HashMap;
import net.labymod.api.client.gui.lss.style.reader.SingleInstruction;
import java.util.Map;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.lss.style.reader.StyleBlock;
import net.labymod.core.client.gui.lss.style.DefaultStyleSelectorList;

public class DefaultStyleBlock extends DefaultStyleSelectorList implements StyleBlock
{
    private StyleSheet styleSheet;
    private int depth;
    private String selector;
    private final Map<String, SingleInstruction> instructions;
    
    public DefaultStyleBlock() {
        this.instructions = new HashMap<String, SingleInstruction>();
    }
    
    public DefaultStyleBlock(final StyleSheet styleSheet, final DefaultStyleBlock block) {
        this.styleSheet = styleSheet;
        this.depth = block.depth;
        this.selector = block.selector;
        this.instructions = block.instructions;
        for (final DefaultStyleInstruction selector : block.selectors) {
            this.selectors.add(new DefaultStyleInstruction(selector.getSelector(), this));
        }
    }
    
    @Override
    public StyleSheet styleSheet() {
        return this.styleSheet;
    }
    
    public void setStyleSheet(final StyleSheet styleSheet) {
        this.styleSheet = styleSheet;
    }
    
    @Override
    public Map<String, SingleInstruction> getInstructions() {
        return this.instructions;
    }
    
    @Override
    public void put(final SingleInstruction instruction) {
        this.instructions.put(instruction.getKey(), instruction);
    }
    
    @Override
    public void setRawSelector(final int depth, final String selector) {
        this.depth = depth;
        this.selector = selector;
    }
    
    @Override
    public String getRawSelector() {
        return this.selector;
    }
    
    @Override
    public int getDepth() {
        return this.depth;
    }
    
    @Override
    public int getLineOf(final String key) {
        return this.instructions.get(key).getLineNumber();
    }
    
    @Override
    public String toString() {
        return this.selector + " { " + String.join(", ", this.instructions.keySet()) + " }";
    }
    
    @Override
    public int hashCode() {
        int result = (this.selector != null) ? this.selector.hashCode() : 0;
        result = 31 * result + ((this.instructions != null) ? this.instructions.hashCode() : 0);
        return result;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof final DefaultStyleBlock block) {
            if (block.selector.equals(this.selector) && block.instructions.equals(this.instructions)) {
                return true;
            }
        }
        return false;
    }
    
    public DefaultStyleBlock copy() {
        final DefaultStyleBlock copy = new DefaultStyleBlock();
        copy.styleSheet = this.styleSheet;
        copy.depth = this.depth;
        copy.selector = this.selector;
        copy.instructions.putAll(this.instructions);
        copy.patches.putAll(this.patches);
        for (final DefaultStyleInstruction styleInstruction : this.selectors) {
            copy.add(styleInstruction.getSelector().buildSelector(), this);
        }
        return copy;
    }
}
