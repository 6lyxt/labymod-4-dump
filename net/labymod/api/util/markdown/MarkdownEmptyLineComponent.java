// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.markdown;

public class MarkdownEmptyLineComponent extends MarkdownComponent
{
    public static final MarkdownEmptyLineComponent INSTANCE;
    
    protected MarkdownEmptyLineComponent() {
        super("emptyline");
    }
    
    @Override
    public String toString() {
        return "MarkdownEmptyLineComponent{}";
    }
    
    static {
        INSTANCE = new MarkdownEmptyLineComponent();
    }
}
