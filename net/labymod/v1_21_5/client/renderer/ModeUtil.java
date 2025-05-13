// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.renderer;

import com.mojang.blaze3d.vertex.VertexFormat;

public class ModeUtil
{
    public static VertexFormat.b getMode(final int mode) {
        return switch (mode) {
            case 0,  4,  7 -> VertexFormat.b.h;
            case 1 -> VertexFormat.b.a;
            case 3 -> VertexFormat.b.d;
            case 5 -> VertexFormat.b.f;
            case 6 -> VertexFormat.b.g;
            case -5 -> VertexFormat.b.c;
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        };
    }
}
