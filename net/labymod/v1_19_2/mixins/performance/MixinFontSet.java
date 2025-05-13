// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.performance;

import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.List;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.core.util.collection.table.CodepointTable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ejf.class })
public abstract class MixinFontSet
{
    private final CodepointTable<eji> labyMod$bakedGlyphTable;
    private final CodepointTable<ejf.a> labyMod$glyphInfoTable;
    
    public MixinFontSet() {
        this.labyMod$bakedGlyphTable = new CodepointTable<eji>(eji[]::new, x$0 -> new eji[x$0][]);
        this.labyMod$glyphInfoTable = new CodepointTable<ejf.a>(ejf.a[]::new, x$0 -> new ejf.a[x$0][]);
    }
    
    @Shadow
    protected abstract eji c(final int p0);
    
    @Shadow
    protected abstract ejf.a b(final int p0);
    
    @Inject(method = { "reload" }, at = { @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/ints/Int2ObjectMap;clear()V", ordinal = 0) })
    private void labyMod$clearGlyphCache(final List<dyt> providers, final CallbackInfo ci) {
        this.labyMod$bakedGlyphTable.clear();
        this.labyMod$glyphInfoTable.clear();
    }
    
    @Overwrite
    public eji a(final int index) {
        eji bakedGlyph = this.labyMod$bakedGlyphTable.get(index);
        if (bakedGlyph == null) {
            bakedGlyph = this.c(index);
            this.labyMod$bakedGlyphTable.set(index, bakedGlyph);
        }
        return bakedGlyph;
    }
    
    @Overwrite
    public dys a(final int index, final boolean filter) {
        ejf.a info = this.labyMod$glyphInfoTable.get(index);
        if (info == null) {
            info = this.b(index);
            this.labyMod$glyphInfoTable.set(index, info);
        }
        return info.a(filter);
    }
}
