// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.markdown;

import java.util.Collections;
import java.util.List;

public class MarkdownDocument
{
    private final List<MarkdownComponent> components;
    
    public MarkdownDocument(final List<MarkdownComponent> components) {
        this.components = Collections.unmodifiableList((List<? extends MarkdownComponent>)components);
    }
    
    public List<MarkdownComponent> getComponents() {
        return this.components;
    }
}
