// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.chat;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.format.TextColor;
import java.util.Map;
import java.util.List;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ChatSymbolRegistry
{
    List<Character> getSymbols();
    
    void addSymbol(final char p0);
    
    void reloadSymbols();
    
    Map<Character, TextColor> getTextColors();
    
    Map<TextColor, Character> getInverseTextColors();
    
    Map<Character, TextDecoration> getTextDecorations();
    
    Map<TextDecoration, Character> getInverseTextDecorations();
    
    Style getStyle(final char p0);
    
    default Character getParagraph() {
        return '§';
    }
    
    default Character getAmpersand() {
        return '&';
    }
}
