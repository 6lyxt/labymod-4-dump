// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.model;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.render.model.compiler.ImmediateModelCompiler;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.Disposable;

public interface ModelBuffer extends Disposable
{
    void render(final Stack p0);
    
    void rebuildModel();
    
    boolean isForceProjectionMatrix();
    
    void setForceProjectionMatrix(final boolean p0);
    
    @NotNull
    ImmediateModelCompiler.Visitor visitor();
    
    void setVisitor(@Nullable final ImmediateModelCompiler.Visitor p0);
    
    boolean isImmediate();
    
    void setImmediate(final boolean p0);
    
    int getARGB();
    
    void setARGB(final int p0);
}
