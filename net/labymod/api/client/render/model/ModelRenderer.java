// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.model;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ModelRenderer
{
    ModelUploader modelUploader();
    
    default void render(final Stack stack, final Model model) {
        this.render(stack, model, true);
    }
    
    void render(final Stack p0, final Model p1, final boolean p2);
}
