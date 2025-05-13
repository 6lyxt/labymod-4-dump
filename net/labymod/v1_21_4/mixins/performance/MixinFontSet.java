// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins.performance;

import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Set;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.core.util.collection.table.CodepointTable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ frm.class })
public abstract class MixinFontSet
{
    private final CodepointTable<frq> labyMod$bakedGlyphTable;
    private final CodepointTable<frm.a> labyMod$glyphInfoTable;
    
    public MixinFontSet() {
        this.labyMod$bakedGlyphTable = new CodepointTable<frq>(frq[]::new, x$0 -> new frq[x$0][]);
        this.labyMod$glyphInfoTable = new CodepointTable<frm.a>(frm.a[]::new, x$0 -> new frm.a[x$0][]);
    }
    
    @Shadow
    protected abstract frq c(final int p0);
    
    @Shadow
    protected abstract frm.a b(final int p0);
    
    @Inject(method = { "reload(Ljava/util/Set;)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/font/FontSet;selectProviders(Ljava/util/List;Ljava/util/Set;)Ljava/util/List;", shift = At.Shift.BEFORE) })
    private void labyMod$clearGlyphCache(final Set<frl> options, final CallbackInfo ci) {
        this.labyMod$bakedGlyphTable.clear();
        this.labyMod$glyphInfoTable.clear();
    }
    
    @Overwrite
    public frq a(final int index) {
        frq bakedGlyph = this.labyMod$bakedGlyphTable.get(index);
        if (bakedGlyph == null) {
            bakedGlyph = this.c(index);
            this.labyMod$bakedGlyphTable.set(index, bakedGlyph);
        }
        return bakedGlyph;
    }
    
    @Overwrite
    public fdt a(final int index, final boolean filter) {
        frm.a info = this.labyMod$glyphInfoTable.get(index);
        if (info == null) {
            info = this.b(index);
            this.labyMod$glyphInfoTable.set(index, info);
        }
        return info.a(filter);
    }
}
