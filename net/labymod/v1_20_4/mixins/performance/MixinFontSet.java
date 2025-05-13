// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.performance;

import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.List;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.core.util.collection.table.CodepointTable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ezv.class })
public abstract class MixinFontSet
{
    private final CodepointTable<ezz> labyMod$bakedGlyphTable;
    private final CodepointTable<ezv.a> labyMod$glyphInfoTable;
    
    public MixinFontSet() {
        this.labyMod$bakedGlyphTable = new CodepointTable<ezz>(ezz[]::new, x$0 -> new ezz[x$0][]);
        this.labyMod$glyphInfoTable = new CodepointTable<ezv.a>(ezv.a[]::new, x$0 -> new ezv.a[x$0][]);
    }
    
    @Shadow
    protected abstract ezz c(final int p0);
    
    @Shadow
    protected abstract ezv.a b(final int p0);
    
    @Inject(method = { "reload" }, at = { @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/ints/Int2ObjectMap;clear()V", ordinal = 0) })
    private void labyMod$clearGlyphCache(final List<eoe> providers, final CallbackInfo ci) {
        this.labyMod$bakedGlyphTable.clear();
        this.labyMod$glyphInfoTable.clear();
    }
    
    @Overwrite
    public ezz a(final int index) {
        ezz bakedGlyph = this.labyMod$bakedGlyphTable.get(index);
        if (bakedGlyph == null) {
            bakedGlyph = this.c(index);
            this.labyMod$bakedGlyphTable.set(index, bakedGlyph);
        }
        return bakedGlyph;
    }
    
    @Overwrite
    public eod a(final int index, final boolean filter) {
        ezv.a info = this.labyMod$glyphInfoTable.get(index);
        if (info == null) {
            info = this.b(index);
            this.labyMod$glyphInfoTable.set(index, info);
        }
        return info.a(filter);
    }
}
