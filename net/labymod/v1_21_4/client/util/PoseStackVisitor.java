// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.util;

import org.jetbrains.annotations.NotNull;

public class PoseStackVisitor implements gfe.d
{
    private final ffv stack;
    
    public PoseStackVisitor(final ffv stack) {
        this.stack = stack;
    }
    
    public void visit(@NotNull final ffv.a pose, @NotNull final String partName, final int index, @NotNull final gfe.a cube) {
        this.stack.a(pose.a());
    }
}
