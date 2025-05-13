// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@DynamicMixin("optifine")
@Mixin({ exo.class })
public abstract class MixinOptiFineDebugScreenOverlay
{
    @Shadow
    private elr i;
    @Shadow
    @Final
    private evi f;
    @Shadow
    private elr j;
    @Shadow
    @Final
    private eyw v;
    @Shadow
    @Final
    private eyy w;
    @Shadow
    @Final
    private eyv y;
    @Shadow
    @Final
    private eyx x;
    @Shadow
    private boolean p;
    @Shadow
    private boolean q;
    
    @Shadow
    protected abstract void b(final ewu p0);
    
    @Shadow
    protected abstract void c(final ewu p0);
    
    @Overwrite
    public void a(final ewu graphicsIn) {
        this.f.aG().a("debug");
        final blv entity = this.f.am();
        this.i = entity.a(20.0, 0.0f, false);
        this.j = entity.a(20.0, 0.0f, true);
        graphicsIn.a(() -> {
            this.b(graphicsIn);
            this.c(graphicsIn);
            if (this.p) {
                final int width = graphicsIn.a();
                final int halfWidth = width / 2;
                this.v.a(graphicsIn, 0, this.v.a(halfWidth));
                if (this.f.T() != null) {
                    final int tpsChartWidth = this.w.a(halfWidth);
                    this.w.a(graphicsIn, width - tpsChartWidth, tpsChartWidth);
                }
            }
            if (this.q) {
                final int width2 = graphicsIn.a();
                final int halfWidth2 = width2 / 2;
                if (!this.f.R()) {
                    this.y.a(graphicsIn, 0, this.y.a(halfWidth2));
                }
                final int pingChartWidth = this.x.a(halfWidth2);
                this.x.a(graphicsIn, width2 - pingChartWidth, pingChartWidth);
            }
            return;
        });
        this.f.aG().c();
    }
}
