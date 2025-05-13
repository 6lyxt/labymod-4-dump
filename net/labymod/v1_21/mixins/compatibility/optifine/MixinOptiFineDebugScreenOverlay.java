// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@DynamicMixin("optifine")
@Mixin({ fiu.class })
public abstract class MixinOptiFineDebugScreenOverlay
{
    @Shadow
    private exa i;
    @Shadow
    @Final
    private fgo f;
    @Shadow
    private exa j;
    @Shadow
    @Final
    private fkd w;
    @Shadow
    @Final
    private fkf x;
    @Shadow
    @Final
    private fkc z;
    @Shadow
    @Final
    private fke y;
    @Shadow
    private boolean p;
    @Shadow
    private boolean q;
    
    @Shadow
    protected abstract void b(final fhz p0);
    
    @Shadow
    protected abstract void c(final fhz p0);
    
    @Overwrite
    public void a(final fhz graphicsIn) {
        this.f.aH().a("debug");
        final bsr entity = this.f.an();
        this.i = entity.a(20.0, 0.0f, false);
        this.j = entity.a(20.0, 0.0f, true);
        graphicsIn.a(() -> {
            this.b(graphicsIn);
            this.c(graphicsIn);
            if (this.p) {
                final int width = graphicsIn.a();
                final int halfWidth = width / 2;
                this.w.a(graphicsIn, 0, this.w.a(halfWidth));
                if (this.f.V() != null) {
                    final int tpsChartWidth = this.x.a(halfWidth);
                    this.x.a(graphicsIn, width - tpsChartWidth, tpsChartWidth);
                }
            }
            if (this.q) {
                final int width2 = graphicsIn.a();
                final int halfWidth2 = width2 / 2;
                if (!this.f.T()) {
                    this.z.a(graphicsIn, 0, this.z.a(halfWidth2));
                }
                final int pingChartWidth = this.y.a(halfWidth2);
                this.y.a(graphicsIn, width2 - pingChartWidth, pingChartWidth);
            }
            return;
        });
        this.f.aH().c();
    }
}
