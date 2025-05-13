// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline;

public interface MatrixOptions
{
    public static final int MULTIPLY_WITH_GAME_MODEL_VIEW_MATRIX = 1;
    public static final int MULTIPLY_WITH_VIEW_MATRIX = 4;
    
    default boolean hasOption(final int options, final int option) {
        return (options & option) == option;
    }
}
