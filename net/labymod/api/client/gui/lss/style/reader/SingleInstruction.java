// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style.reader;

import java.util.Locale;
import net.labymod.api.client.gui.lss.style.StyleSheet;

public class SingleInstruction
{
    private final String key;
    private final String value;
    private final int lineNumber;
    private final boolean important;
    private final StyleSheet styleSheet;
    
    public SingleInstruction(final String key, final String value, final int lineNumber, final boolean important, final StyleSheet styleSheet) {
        this.key = key;
        this.value = value;
        this.lineNumber = lineNumber;
        this.important = important;
        this.styleSheet = styleSheet;
    }
    
    public String getKey() {
        return this.key;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public int getLineNumber() {
        return this.lineNumber;
    }
    
    public boolean isImportant() {
        return this.important;
    }
    
    public StyleSheet styleSheet() {
        return this.styleSheet;
    }
    
    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%s: %s", this.key, this.value);
    }
}
