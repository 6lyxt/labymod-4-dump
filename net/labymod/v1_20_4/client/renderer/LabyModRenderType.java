// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.renderer;

import java.util.Objects;
import net.labymod.api.client.render.vertex.phase.RenderPhase;

public class LabyModRenderType extends ftp
{
    private final RenderPhase phase;
    
    public LabyModRenderType(final RenderPhase phase) {
        final String name = phase.getName();
        final eqg eqg = phase.getVertexFormat().getMojangVertexFormat();
        final eqg.b mode = getMode(phase.getMode());
        final int bufferSize = phase.getBufferSize();
        final boolean affectsCrumbling = phase.isAffectsCrumbling();
        final boolean sortOnUpload = phase.isSortOnUpload();
        Objects.requireNonNull(phase);
        final Runnable runnable = phase::apply;
        Objects.requireNonNull(phase);
        super(name, eqg, mode, bufferSize, affectsCrumbling, sortOnUpload, runnable, phase::clear);
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
    
    public static eqg.b getMode(final int mode) {
        return switch (mode) {
            case 0,  4,  7 -> eqg.b.h;
            case 1 -> eqg.b.a;
            case 3 -> eqg.b.d;
            case 5 -> eqg.b.f;
            case 6 -> eqg.b.g;
            case -5 -> eqg.b.c;
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        };
    }
}
