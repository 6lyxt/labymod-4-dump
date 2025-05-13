// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.renderer;

import java.util.Objects;
import net.labymod.api.client.render.vertex.phase.RenderPhase;

public class LabyModRenderType extends glv
{
    private final RenderPhase phase;
    
    public LabyModRenderType(final RenderPhase phase) {
        final String name = phase.getName();
        final fgx fgx = phase.getVertexFormat().getMojangVertexFormat();
        final fgx.c mode = getMode(phase.getMode());
        final int bufferSize = phase.getBufferSize();
        final boolean affectsCrumbling = phase.isAffectsCrumbling();
        final boolean sortOnUpload = phase.isSortOnUpload();
        Objects.requireNonNull(phase);
        final Runnable runnable = phase::apply;
        Objects.requireNonNull(phase);
        super(name, fgx, mode, bufferSize, affectsCrumbling, sortOnUpload, runnable, phase::clear);
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
    
    public static fgx.c getMode(final int mode) {
        return switch (mode) {
            case 0,  4,  7 -> fgx.c.h;
            case 1 -> fgx.c.a;
            case 3 -> fgx.c.d;
            case 5 -> fgx.c.f;
            case 6 -> fgx.c.g;
            case -5 -> fgx.c.c;
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        };
    }
}
