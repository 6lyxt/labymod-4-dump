// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss;

import java.util.Locale;
import net.labymod.api.client.gui.lss.style.reader.SingleInstruction;
import net.labymod.api.client.gui.lss.style.StyleSheet;

public class LssStyleException extends Exception
{
    private final StyleSheet styleSheet;
    private final SingleInstruction instruction;
    
    public LssStyleException(final StyleSheet styleSheet, final SingleInstruction instruction, final String message) {
        super(String.format(Locale.ROOT, "Failed to parse style instruction from %s:%s: %s", styleSheet.file(), instruction.getLineNumber(), message));
        this.styleSheet = styleSheet;
        this.instruction = instruction;
    }
    
    public StyleSheet styleSheet() {
        return this.styleSheet;
    }
    
    public SingleInstruction instruction() {
        return this.instruction;
    }
}
