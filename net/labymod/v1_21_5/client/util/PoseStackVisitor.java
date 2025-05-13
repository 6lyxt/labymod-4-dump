// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.util;

import org.joml.Matrix4fc;
import org.jetbrains.annotations.NotNull;

public class PoseStackVisitor implements gkr.d
{
    private final fld stack;
    
    public PoseStackVisitor(final fld stack) {
        this.stack = stack;
    }
    
    public void visit(@NotNull final fld.a pose, @NotNull final String partName, final int index, @NotNull final gkr.a cube) {
        this.stack.a((Matrix4fc)pose.a());
    }
}
