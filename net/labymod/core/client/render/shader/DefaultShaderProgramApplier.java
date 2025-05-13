// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.shader;

import net.labymod.api.Laby;
import org.jetbrains.annotations.Nullable;

public final class DefaultShaderProgramApplier
{
    public void apply(@Nullable final Runnable fallback) {
        Laby.references().glStateBridge().applyShader(fallback);
    }
    
    public void stop(@Nullable final Runnable fallback) {
        Laby.references().glStateBridge().clearShader(fallback);
    }
}
