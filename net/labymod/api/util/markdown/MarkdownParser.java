// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.markdown;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface MarkdownParser
{
    MarkdownDocument parse(final String p0);
    
    public static class MarkdownLine
    {
        private final String text;
        private String trimmedText;
        private char[] trimmedChars;
        private char[] chars;
        
        public MarkdownLine(@NotNull final String text) {
            Objects.requireNonNull(text);
            this.text = text;
        }
        
        public String getText() {
            return this.text;
        }
        
        public String getTrimmedText() {
            if (this.trimmedText == null) {
                this.trimmedText = this.text.trim();
            }
            return this.trimmedText;
        }
        
        public char[] getChars() {
            if (this.chars == null) {
                this.chars = this.text.toCharArray();
            }
            return this.chars;
        }
        
        public char[] getTrimmedChars() {
            if (this.trimmedChars == null) {
                this.trimmedChars = this.getTrimmedText().toCharArray();
            }
            return this.trimmedChars;
        }
    }
}
