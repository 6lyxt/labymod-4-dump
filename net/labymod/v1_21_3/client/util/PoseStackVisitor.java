// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.util;

import org.jetbrains.annotations.NotNull;

public class PoseStackVisitor implements geo.d
{
    private final fgs stack;
    
    public PoseStackVisitor(final fgs stack) {
        this.stack = stack;
    }
    
    public void visit(@NotNull final fgs.a pose, @NotNull final String partName, final int index, @NotNull final geo.a cube) {
        this.stack.a(pose.a());
    }
}
