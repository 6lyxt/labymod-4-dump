// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.performance;

import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Set;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.core.util.collection.table.CodepointTable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fqy.class })
public abstract class MixinFontSet
{
    private final CodepointTable<frc> labyMod$bakedGlyphTable;
    private final CodepointTable<fqy.a> labyMod$glyphInfoTable;
    
    public MixinFontSet() {
        this.labyMod$bakedGlyphTable = new CodepointTable<frc>(frc[]::new, x$0 -> new frc[x$0][]);
        this.labyMod$glyphInfoTable = new CodepointTable<fqy.a>(fqy.a[]::new, x$0 -> new fqy.a[x$0][]);
    }
    
    @Shadow
    protected abstract frc c(final int p0);
    
    @Shadow
    protected abstract fqy.a b(final int p0);
    
    @Inject(method = { "reload(Ljava/util/Set;)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/font/FontSet;selectProviders(Ljava/util/List;Ljava/util/Set;)Ljava/util/List;", shift = At.Shift.BEFORE) })
    private void labyMod$clearGlyphCache(final Set<fqx> options, final CallbackInfo ci) {
        this.labyMod$bakedGlyphTable.clear();
        this.labyMod$glyphInfoTable.clear();
    }
    
    @Overwrite
    public frc a(final int index) {
        frc bakedGlyph = this.labyMod$bakedGlyphTable.get(index);
        if (bakedGlyph == null) {
            bakedGlyph = this.c(index);
            this.labyMod$bakedGlyphTable.set(index, bakedGlyph);
        }
        return bakedGlyph;
    }
    
    @Overwrite
    public feq a(final int index, final boolean filter) {
        fqy.a info = this.labyMod$glyphInfoTable.get(index);
        if (info == null) {
            info = this.b(index);
            this.labyMod$glyphInfoTable.set(index, info);
        }
        return info.a(filter);
    }
}
