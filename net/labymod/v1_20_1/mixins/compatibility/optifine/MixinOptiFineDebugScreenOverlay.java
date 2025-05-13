// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@DynamicMixin("optifine")
@Mixin({ epq.class })
public abstract class MixinOptiFineDebugScreenOverlay
{
    @Shadow
    private eeg i;
    @Shadow
    @Final
    private enn f;
    @Shadow
    private eeg j;
    
    @Shadow
    protected abstract void a(final eox p0, final aoo p1, final int p2, final int p3, final boolean p4);
    
    @Shadow
    protected abstract void b(final eox p0);
    
    @Shadow
    protected abstract void c(final eox p0);
    
    @Overwrite
    public void a(final eox graphicsIn) {
        this.f.aG().a("debug");
        final bfj entity = this.f.al();
        this.i = entity.a(20.0, 0.0f, false);
        this.j = entity.a(20.0, 0.0f, true);
        graphicsIn.a(() -> {
            this.b(graphicsIn);
            this.c(graphicsIn);
            if (this.f.m.ac) {
                graphicsIn.c().a();
                graphicsIn.c().a(0.0f, 0.0f, 400.0f);
                final int i = graphicsIn.a();
                this.a(graphicsIn, this.f.aq(), 0, i / 2, true);
                final fyp integratedserver = this.f.S();
                if (integratedserver != null) {
                    this.a(graphicsIn, integratedserver.aM(), i - Math.min(i / 2, 240), i / 2, false);
                }
                graphicsIn.c().b();
            }
            return;
        });
        this.f.aG().c();
    }
}
