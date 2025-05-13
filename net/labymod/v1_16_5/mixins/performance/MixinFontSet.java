// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.performance;

import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.List;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.core.util.collection.table.CodepointTable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dmw.class })
public abstract class MixinFontSet
{
    private final CodepointTable<dmz> labyMod$bakedGlyphTable;
    private final CodepointTable<dea> labyMod$glyphInfoTable;
    @Shadow
    @Final
    private static dea b;
    @Shadow
    @Final
    private static dna a;
    
    public MixinFontSet() {
        this.labyMod$bakedGlyphTable = new CodepointTable<dmz>(dmz[]::new, x$0 -> new dmz[x$0][]);
        this.labyMod$glyphInfoTable = new CodepointTable<dea>(dea[]::new, x$0 -> new dea[x$0][]);
    }
    
    @Shadow
    protected abstract dec c(final int p0);
    
    @Shadow
    protected abstract dmz a(final dec p0);
    
    @Inject(method = { "reload" }, at = { @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/ints/Int2ObjectMap;clear()V", ordinal = 0) })
    private void labyMod$clearGlyphCache(final List<deb> providers, final CallbackInfo ci) {
        this.labyMod$bakedGlyphTable.clear();
        this.labyMod$glyphInfoTable.clear();
    }
    
    @Overwrite
    public dmz b(final int index) {
        dmz bakedGlyph = this.labyMod$bakedGlyphTable.get(index);
        if (bakedGlyph == null) {
            bakedGlyph = this.computeBakedGlyph(index);
            this.labyMod$bakedGlyphTable.set(index, bakedGlyph);
        }
        return bakedGlyph;
    }
    
    @Overwrite
    public dea a(final int index) {
        dea info = this.labyMod$glyphInfoTable.get(index);
        if (info == null) {
            info = this.computeGlyphInfo(index);
            this.labyMod$glyphInfoTable.set(index, info);
        }
        return info;
    }
    
    private dea computeGlyphInfo(final int codepoint) {
        return (dea)((codepoint == 32) ? MixinFontSet.b : this.c(codepoint));
    }
    
    private dmz computeBakedGlyph(final int codepoint) {
        return (dmz)((codepoint == 32) ? MixinFontSet.a : this.a(this.c(codepoint)));
    }
}
