// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.reader;

import java.io.IOException;

public class StyleParseException extends IOException
{
    private final int line;
    private final String message;
    
    public StyleParseException(final int line, final String message) {
        this.line = line;
        this.message = message;
    }
    
    public int getLine() {
        return this.line;
    }
    
    @Override
    public String getMessage() {
        return "Parse error at line " + this.line + ": " + this.message;
    }
    
    public static StyleParseException unexpectedCharacter(final int line, final char character) {
        return new StyleParseException(line, "Unexpected character: '" + character);
    }
}
