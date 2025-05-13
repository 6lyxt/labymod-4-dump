// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.performance;

import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.List;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.core.util.collection.table.CodepointTable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ebr.class })
public abstract class MixinFontSet
{
    private final CodepointTable<ebu> labyMod$bakedGlyphTable;
    private final CodepointTable<drq> labyMod$glyphInfoTable;
    @Shadow
    @Final
    private static drq b;
    @Shadow
    @Final
    private static ebv a;
    
    public MixinFontSet() {
        this.labyMod$bakedGlyphTable = new CodepointTable<ebu>(ebu[]::new, x$0 -> new ebu[x$0][]);
        this.labyMod$glyphInfoTable = new CodepointTable<drq>(drq[]::new, x$0 -> new drq[x$0][]);
    }
    
    @Shadow
    protected abstract drs d(final int p0);
    
    @Shadow
    protected abstract ebu a(final drs p0);
    
    @Inject(method = { "reload" }, at = { @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/ints/Int2ObjectMap;clear()V", ordinal = 0) })
    private void labyMod$clearGlyphCache(final List<drr> providers, final CallbackInfo ci) {
        this.labyMod$bakedGlyphTable.clear();
        this.labyMod$glyphInfoTable.clear();
    }
    
    @Overwrite
    public ebu b(final int index) {
        ebu bakedGlyph = this.labyMod$bakedGlyphTable.get(index);
        if (bakedGlyph == null) {
            bakedGlyph = this.computeBakedGlyph(index);
            this.labyMod$bakedGlyphTable.set(index, bakedGlyph);
        }
        return bakedGlyph;
    }
    
    @Overwrite
    public drq a(final int index) {
        drq info = this.labyMod$glyphInfoTable.get(index);
        if (info == null) {
            info = this.computeGlyphInfo(index);
            this.labyMod$glyphInfoTable.set(index, info);
        }
        return info;
    }
    
    private drq computeGlyphInfo(final int codepoint) {
        return (drq)((codepoint == 32) ? MixinFontSet.b : this.d(codepoint));
    }
    
    private ebu computeBakedGlyph(final int codepoint) {
        return (ebu)((codepoint == 32) ? MixinFontSet.a : this.a(this.d(codepoint)));
    }
}
