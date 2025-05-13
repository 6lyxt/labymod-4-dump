// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.performance;

import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.List;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.core.util.collection.table.CodepointTable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dym.class })
public abstract class MixinFontSet
{
    private final CodepointTable<dyp> labyMod$bakedGlyphTable;
    private final CodepointTable<dop> labyMod$glyphInfoTable;
    @Shadow
    @Final
    private static dop b;
    @Shadow
    @Final
    private static dyq a;
    
    public MixinFontSet() {
        this.labyMod$bakedGlyphTable = new CodepointTable<dyp>(dyp[]::new, x$0 -> new dyp[x$0][]);
        this.labyMod$glyphInfoTable = new CodepointTable<dop>(dop[]::new, x$0 -> new dop[x$0][]);
    }
    
    @Shadow
    protected abstract dor c(final int p0);
    
    @Shadow
    protected abstract dyp a(final dor p0);
    
    @Inject(method = { "reload" }, at = { @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/ints/Int2ObjectMap;clear()V", ordinal = 0) })
    private void labyMod$clearGlyphCache(final List<doq> providers, final CallbackInfo ci) {
        this.labyMod$bakedGlyphTable.clear();
        this.labyMod$glyphInfoTable.clear();
    }
    
    @Overwrite
    public dyp b(final int index) {
        dyp bakedGlyph = this.labyMod$bakedGlyphTable.get(index);
        if (bakedGlyph == null) {
            bakedGlyph = this.computeBakedGlyph(index);
            this.labyMod$bakedGlyphTable.set(index, bakedGlyph);
        }
        return bakedGlyph;
    }
    
    @Overwrite
    public dop a(final int index) {
        dop info = this.labyMod$glyphInfoTable.get(index);
        if (info == null) {
            info = this.computeGlyphInfo(index);
            this.labyMod$glyphInfoTable.set(index, info);
        }
        return info;
    }
    
    private dop computeGlyphInfo(final int codepoint) {
        return (dop)((codepoint == 32) ? MixinFontSet.b : this.c(codepoint));
    }
    
    private dyp computeBakedGlyph(final int codepoint) {
        return (dyp)((codepoint == 32) ? MixinFontSet.a : this.a(this.c(codepoint)));
    }
}
