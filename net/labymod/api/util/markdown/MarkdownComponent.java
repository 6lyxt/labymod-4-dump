// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.markdown;

import java.util.List;

public abstract class MarkdownComponent
{
    private final String identifier;
    
    protected MarkdownComponent(final String identifier) {
        this.identifier = identifier;
    }
    
    public String getIdentifier() {
        return this.identifier;
    }
    
    public interface Parser
    {
        int parse(final MarkdownParser.MarkdownLine p0, final int p1, final int p2, final MarkdownParser.MarkdownLine[] p3, final List<MarkdownComponent> p4);
    }
}
