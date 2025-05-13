// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@DynamicMixin("optifine")
@Mixin({ fpc.class })
public abstract class MixinOptiFineDebugScreenOverlay
{
    @Shadow
    private faz i;
    @Shadow
    @Final
    private flk f;
    @Shadow
    private faz j;
    @Shadow
    @Final
    private fql w;
    @Shadow
    @Final
    private fqo x;
    @Shadow
    @Final
    private fqk z;
    @Shadow
    @Final
    private fqm y;
    @Shadow
    private boolean p;
    @Shadow
    private boolean q;
    @Shadow
    @Final
    private fqn A;
    @Shadow
    @Final
    private bne s;
    
    @Shadow
    protected abstract void b(final fof p0);
    
    @Shadow
    protected abstract void c(final fof p0);
    
    @Overwrite
    public void a(final fof graphics) {
        final bou filler = bot.a();
        filler.a("debug");
        final bum cameraEntity = this.f.ao();
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
        try (final boz $$9 = filler.d("profilerPie")) {
            this.A.a(graphics);
        }
        filler.c();
    }
}
