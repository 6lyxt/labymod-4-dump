// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.markdown;

public class MarkdownAlternateHeaderComponent extends MarkdownHeaderComponent
{
    public MarkdownAlternateHeaderComponent(final int level, final String text) {
        super("alternateheader", level, text);
    }
    
    @Override
    protected int validateLevel(final int level) {
        if (level < 1 || level > 2) {
            throw new IllegalStateException("Header level has to be between 1-2");
        }
        return level;
    }
    
    @Override
    protected String getNiceName() {
        return "MarkdownAlternateHeaderComponent";
    }
}
