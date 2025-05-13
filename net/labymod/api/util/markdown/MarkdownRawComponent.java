// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.markdown;

import net.labymod.api.util.CollectionHelper;
import java.util.Set;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class MarkdownRawComponent extends MarkdownComponent
{
    private static final List<Formatting> NO_FORMATTING;
    private final String originalText;
    private final String text;
    private final List<Formatting> formatting;
    
    public MarkdownRawComponent(final String text) {
        this(text, (Formatting[])null);
    }
    
    public MarkdownRawComponent(final String text, final Formatting... formatting) {
        this(text, text, formatting);
    }
    
    public MarkdownRawComponent(final String originalText, final String text, final Formatting... formatting) {
        super("rawtext");
        this.originalText = originalText;
        this.text = text;
        if (formatting == null) {
            this.formatting = MarkdownRawComponent.NO_FORMATTING;
        }
        else {
            this.formatting = List.of(formatting);
        }
    }
    
    public String getText() {
        return this.text;
    }
    
    public List<Formatting> getFormatting() {
        return this.formatting;
    }
    
    public boolean hasFormatting(final Formatting formatting) {
        return this.formatting.contains(formatting);
    }
    
    public boolean hasFormatting() {
        return !this.formatting.isEmpty();
    }
    
    public boolean fromSameLine(final MarkdownRawComponent other) {
        return this.originalText.equals(other.originalText);
    }
    
    @Override
    public String toString() {
        return "MarkdownRawComponent{text='" + this.text + "', formatting=" + String.valueOf(this.formatting);
    }
    
    static {
        NO_FORMATTING = Collections.unmodifiableList((List<? extends Formatting>)new ArrayList<Formatting>(0));
    }
    
    public enum Formatting
    {
        BOLD, 
        ITALIC;
        
        public static final Set<Formatting> VALUES;
        
        static {
            VALUES = CollectionHelper.asUnmodifiableSet(values());
        }
    }
}
