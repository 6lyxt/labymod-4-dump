// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.renderer;

import java.util.Objects;
import net.labymod.api.client.render.vertex.phase.RenderPhase;

public class LabyModRenderType extends fed
{
    private final RenderPhase phase;
    
    public LabyModRenderType(final RenderPhase phase) {
        final String name = phase.getName();
        final eei eei = phase.getVertexFormat().getMojangVertexFormat();
        final eei.b mode = getMode(phase.getMode());
        final int bufferSize = phase.getBufferSize();
        final boolean affectsCrumbling = phase.isAffectsCrumbling();
        final boolean sortOnUpload = phase.isSortOnUpload();
        Objects.requireNonNull(phase);
        final Runnable runnable = phase::apply;
        Objects.requireNonNull(phase);
        super(name, eei, mode, bufferSize, affectsCrumbling, sortOnUpload, runnable, phase::clear);
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
    
    public static eei.b getMode(final int mode) {
        return switch (mode) {
            case 0,  4,  7 -> eei.b.h;
            case 1 -> eei.b.a;
            case 3 -> eei.b.d;
            case 5 -> eei.b.f;
            case 6 -> eei.b.g;
            case -5 -> eei.b.c;
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        };
    }
}
