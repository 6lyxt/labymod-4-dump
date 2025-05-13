// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.markdown;

public class MarkdownHeaderComponent extends MarkdownComponent
{
    private final int level;
    private final String text;
    
    protected MarkdownHeaderComponent(final String identifier, final int level, final String text) {
        super(identifier);
        this.level = this.validateLevel(level);
        this.text = text;
    }
    
    public MarkdownHeaderComponent(final int level, final String text) {
        this("header", level, text);
    }
    
    public String getText() {
        return this.text;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    protected String getNiceName() {
        return "MarkdownHeaderComponent";
    }
    
    protected int validateLevel(final int level) {
        if (level < 1 || level > 6) {
            throw new IllegalStateException("Header level has to be between 1-6");
        }
        return level;
    }
    
    @Override
    public String toString() {
        return this.getNiceName() + "{level=" + this.level + ", text='" + this.text + "'}";
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof final MarkdownHeaderComponent that) {
            return this.level == that.level && this.text.equals(that.text);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int result = this.level;
        result = 31 * result + ((this.text != null) ? this.text.hashCode() : 0);
        return result;
    }
}
