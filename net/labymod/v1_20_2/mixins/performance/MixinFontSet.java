// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.performance;

import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.List;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.core.util.collection.table.CodepointTable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ eve.class })
public abstract class MixinFontSet
{
    private final CodepointTable<evi> labyMod$bakedGlyphTable;
    private final CodepointTable<eve.a> labyMod$glyphInfoTable;
    
    public MixinFontSet() {
        this.labyMod$bakedGlyphTable = new CodepointTable<evi>(evi[]::new, x$0 -> new evi[x$0][]);
        this.labyMod$glyphInfoTable = new CodepointTable<eve.a>(eve.a[]::new, x$0 -> new eve.a[x$0][]);
    }
    
    @Shadow
    protected abstract evi c(final int p0);
    
    @Shadow
    protected abstract eve.a b(final int p0);
    
    @Inject(method = { "reload" }, at = { @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/ints/Int2ObjectMap;clear()V", ordinal = 0) })
    private void labyMod$clearGlyphCache(final List<ejs> providers, final CallbackInfo ci) {
        this.labyMod$bakedGlyphTable.clear();
        this.labyMod$glyphInfoTable.clear();
    }
    
    @Overwrite
    public evi a(final int index) {
        evi bakedGlyph = this.labyMod$bakedGlyphTable.get(index);
        if (bakedGlyph == null) {
            bakedGlyph = this.c(index);
            this.labyMod$bakedGlyphTable.set(index, bakedGlyph);
        }
        return bakedGlyph;
    }
    
    @Overwrite
    public ejr a(final int index, final boolean filter) {
        eve.a info = this.labyMod$glyphInfoTable.get(index);
        if (info == null) {
            info = this.b(index);
            this.labyMod$glyphInfoTable.set(index, info);
        }
        return info.a(filter);
    }
}
