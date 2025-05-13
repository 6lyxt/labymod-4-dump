// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.program;

import java.util.Objects;
import org.jetbrains.annotations.ApiStatus;

public class DynamicRenderProgram extends RenderProgram
{
    private final RenderProgram delegate;
    
    public DynamicRenderProgram(final RenderProgram delegate) {
        super(delegate.getName(), delegate.mode(), delegate.vertexFormat(), delegate.getParameters());
        this.delegate = delegate;
    }
    
    @ApiStatus.Internal
    @Override
    public void apply() {
        this.delegate.apply();
    }
    
    @ApiStatus.Internal
    @Override
    public void clear() {
        this.delegate.clear();
    }
    
    public void applyDynamicParameter(final RenderParameter parameter) {
        parameter.apply();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final DynamicRenderProgram that = (DynamicRenderProgram)o;
        return Objects.equals(this.delegate, that.delegate);
    }
}
