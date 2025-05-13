// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.renderer;

import java.util.Objects;
import net.labymod.api.client.render.vertex.phase.RenderPhase;

public class LabyModRenderType extends eao
{
    private final RenderPhase phase;
    
    public LabyModRenderType(final RenderPhase phase) {
        final String name = phase.getName();
        final dfr dfr = phase.getVertexFormat().getMojangVertexFormat();
        final int mode = getMode(phase.getMode());
        final int bufferSize = phase.getBufferSize();
        final boolean affectsCrumbling = phase.isAffectsCrumbling();
        final boolean sortOnUpload = phase.isSortOnUpload();
        Objects.requireNonNull(phase);
        final Runnable runnable = phase::apply;
        Objects.requireNonNull(phase);
        super(name, dfr, mode, bufferSize, affectsCrumbling, sortOnUpload, runnable, phase::clear);
        this.phase = phase;
    }
    
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        final LabyModRenderType that = (LabyModRenderType)object;
        return Objects.equals(this.phase, that.phase);
    }
    
    public int hashCode() {
        return (this.phase != null) ? this.phase.hashCode() : 0;
    }
    
    public static int getMode(final int mode) {
        switch (mode) {
            case -5:
            case 1: {
                return 1;
            }
            case 0:
            case 4:
            case 7: {
                return 7;
            }
            case 3: {
                return 3;
            }
            case 6: {
                return 6;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + mode);
            }
        }
    }
}
