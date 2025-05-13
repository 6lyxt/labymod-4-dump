// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@DynamicMixin("optifine")
@Mixin({ esy.class })
public abstract class MixinOptiFineDebugScreenOverlay
{
    @Shadow
    private ehl i;
    @Shadow
    @Final
    private eqv f;
    @Shadow
    private ehl j;
    @Shadow
    @Final
    private euf v;
    @Shadow
    @Final
    private euh w;
    @Shadow
    @Final
    private eue y;
    @Shadow
    @Final
    private eug x;
    @Shadow
    private boolean p;
    @Shadow
    private boolean q;
    
    @Shadow
    protected abstract void b(final esf p0);
    
    @Shadow
    protected abstract void c(final esf p0);
    
    @Overwrite
    public void a(final esf graphicsIn) {
        this.f.aG().a("debug");
        final biq entity = this.f.am();
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
                final int pingCHartWidth = this.x.a(halfWidth2);
                this.x.a(graphicsIn, width2 - pingCHartWidth, pingCHartWidth);
            }
            return;
        });
        this.f.aG().c();
    }
}
