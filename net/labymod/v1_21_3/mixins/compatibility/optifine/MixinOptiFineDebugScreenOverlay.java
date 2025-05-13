// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@DynamicMixin("optifine")
@Mixin({ fon.class })
public abstract class MixinOptiFineDebugScreenOverlay
{
    @Shadow
    private fbw i;
    @Shadow
    @Final
    private fmg f;
    @Shadow
    private fbw j;
    @Shadow
    @Final
    private fpx w;
    @Shadow
    @Final
    private fqa x;
    @Shadow
    @Final
    private fpw z;
    @Shadow
    @Final
    private fpy y;
    @Shadow
    private boolean p;
    @Shadow
    private boolean q;
    @Shadow
    @Final
    private fpz A;
    @Shadow
    @Final
    private bod s;
    
    @Shadow
    protected abstract void b(final fns p0);
    
    @Shadow
    protected abstract void c(final fns p0);
    
    @Overwrite
    public void a(final fns graphics) {
        final bpt filler = bps.a();
        filler.a("debug");
        final bvk cameraEntity = this.f.ao();
        this.i = cameraEntity.a(20.0, 0.0f, false);
        this.j = cameraEntity.a(20.0, 0.0f, true);
        this.b(graphics);
        this.c(graphics);
        this.A.a(10);
        if (this.p) {
            final int $$3 = graphics.a();
            final int $$4 = $$3 / 2;
            this.w.a(graphics, 0, this.w.a($$4));
            if (this.s.d() > 0) {
                final int $$5 = this.x.a($$4);
                this.x.a(graphics, $$3 - $$5, $$5);
            }
            this.A.a(this.x.a());
        }
        if (this.q) {
            final int $$6 = graphics.a();
            final int $$7 = $$6 / 2;
            if (!this.f.T()) {
                this.z.a(graphics, 0, this.z.a($$7));
            }
            final int $$8 = this.y.a($$7);
            this.y.a(graphics, $$6 - $$8, $$8);
            this.A.a(this.y.a());
        }
        try (final bpy $$9 = filler.d("profilerPie")) {
            this.A.a(graphics);
        }
        filler.c();
    }
}
