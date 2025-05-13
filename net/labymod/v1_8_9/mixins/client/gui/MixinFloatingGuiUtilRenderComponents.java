// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.gui;

import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import java.util.ArrayList;
import com.google.common.collect.Lists;
import net.labymod.v1_8_9.client.font.text.FloatingFontRenderer;
import java.util.List;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ avu.class })
public class MixinFloatingGuiUtilRenderComponents
{
    @Shadow
    public static String a(final String p_178909_0_, final boolean p_178909_1_) {
        return null;
    }
    
    @Insert(method = { "splitText" }, at = @At("HEAD"), cancellable = true)
    private static void labyMod$splitTextFloat(final eu rawText, final int maxWidth, final avn renderer, final boolean removeLeadingSpaces, final boolean p_splitText_4_, final InsertInfoReturnable<List<eu>> callback) {
        if (!(renderer instanceof FloatingFontRenderer)) {
            return;
        }
        final FloatingFontRenderer floatingRenderer = (FloatingFontRenderer)renderer;
        float var5 = 0.0f;
        fa var6 = new fa("");
        final ArrayList var7 = Lists.newArrayList();
        final ArrayList var8 = Lists.newArrayList((Iterable)rawText);
        for (int var9 = 0; var9 < var8.size(); ++var9) {
            final eu var10 = var8.get(var9);
            String var11 = var10.e();
            boolean var12 = false;
            if (var11.contains("\n")) {
                final int var13 = var11.indexOf(10);
                final String var14 = var11.substring(var13 + 1);
                var11 = var11.substring(0, var13 + 1);
                final fa var15 = new fa(var14);
                var15.a(var10.b().m());
                var8.add(var9 + 1, var15);
                var12 = true;
            }
            final String var16 = a(var10.b().k() + var11, p_splitText_4_);
            final String var14 = var16.endsWith("\n") ? var16.substring(0, var16.length() - 1) : var16;
            float var17 = floatingRenderer.getStringWidthFloat(var14);
            fa var18 = new fa(var14);
            var18.a(var10.b().m());
            if (var5 + var17 > maxWidth) {
                String var19 = floatingRenderer.trimStringToWidthFloat(var16, maxWidth - var5, false);
                String var20 = (var19.length() < var16.length()) ? var16.substring(var19.length()) : null;
                if (var20 != null && var20.length() > 0) {
                    int var21 = var19.lastIndexOf(" ");
                    if (var21 >= 0 && floatingRenderer.getStringWidthFloat(var16.substring(0, var21)) > 0.0f) {
                        var19 = var16.substring(0, var21);
                        if (removeLeadingSpaces) {
                            ++var21;
                        }
                        var20 = var16.substring(var21);
                    }
                    else if (var5 > 0.0f && !var16.contains(" ")) {
                        var19 = "";
                        var20 = var16;
                    }
                    final fa var22 = new fa(var20);
                    var22.a(var10.b().m());
                    var8.add(var9 + 1, var22);
                }
                var17 = floatingRenderer.getStringWidthFloat(var19);
                var18 = new fa(var19);
                var18.a(var10.b().m());
                var12 = true;
            }
            if (var5 + var17 <= maxWidth) {
                var5 += var17;
                var6.a((eu)var18);
            }
            else {
                var12 = true;
            }
            if (var12) {
                var7.add(var6);
                var5 = 0.0f;
                var6 = new fa("");
            }
        }
        var7.add(var6);
        callback.setReturnValue(var7);
    }
}
