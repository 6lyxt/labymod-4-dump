// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.client.renderer;

import java.util.Objects;
import net.labymod.api.client.render.vertex.phase.RenderPhase;

public class LabyModRenderType extends fio
{
    private final RenderPhase phase;
    
    public LabyModRenderType(final RenderPhase phase) {
        final String name = phase.getName();
        final ehj ehj = phase.getVertexFormat().getMojangVertexFormat();
        final ehj.b mode = getMode(phase.getMode());
        final int bufferSize = phase.getBufferSize();
        final boolean affectsCrumbling = phase.isAffectsCrumbling();
        final boolean sortOnUpload = phase.isSortOnUpload();
        Objects.requireNonNull(phase);
        final Runnable runnable = phase::apply;
        Objects.requireNonNull(phase);
        super(name, ehj, mode, bufferSize, affectsCrumbling, sortOnUpload, runnable, phase::clear);
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
    
    public static ehj.b getMode(final int mode) {
        return switch (mode) {
            case 0,  4,  7 -> ehj.b.h;
            case 1 -> ehj.b.a;
            case 3 -> ehj.b.d;
            case 5 -> ehj.b.f;
            case 6 -> ehj.b.g;
            case -5 -> ehj.b.c;
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        };
    }
}
