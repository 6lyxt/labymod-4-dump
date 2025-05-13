// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@DynamicMixin("optifine")
@Mixin({ fug.class })
public abstract class MixinOptiFineDebugScreenOverlay
{
    @Shadow
    private fga m;
    @Shadow
    @Final
    private fqq h;
    @Shadow
    private fga n;
    @Shadow
    @Final
    private fvp A;
    @Shadow
    @Final
    private fvs B;
    @Shadow
    @Final
    private fvo D;
    @Shadow
    @Final
    private fvq C;
    @Shadow
    private boolean t;
    @Shadow
    private boolean u;
    @Shadow
    @Final
    private fvr E;
    @Shadow
    @Final
    private bpn w;
    
    @Shadow
    protected abstract void b(final ftk p0);
    
    @Shadow
    protected abstract void c(final ftk p0);
    
    @Overwrite
    public void a(final ftk graphics) {
        final brm filler = brl.a();
        filler.a("debug");
        final bxe cameraEntity = this.h.ao();
        this.m = cameraEntity.a(20.0, 0.0f, false);
        this.n = cameraEntity.a(20.0, 0.0f, true);
        this.b(graphics);
        this.c(graphics);
        this.E.a(10);
        if (this.t) {
            final int $$3 = graphics.a();
            final int $$4 = $$3 / 2;
            this.A.a(graphics, 0, this.A.a($$4));
            if (this.w.d() > 0) {
                final int $$5 = this.B.a($$4);
                this.B.a(graphics, $$3 - $$5, $$5);
            }
            this.E.a(this.B.a());
        }
        if (this.u) {
            final int $$6 = graphics.a();
            final int $$7 = $$6 / 2;
            if (!this.h.T()) {
                this.D.a(graphics, 0, this.D.a($$7));
            }
            final int $$8 = this.C.a($$7);
            this.C.a(graphics, $$6 - $$8, $$8);
            this.E.a(this.C.a());
        }
        try (final brr $$9 = filler.d("profilerPie")) {
            this.E.a(graphics);
        }
        filler.c();
    }
}
