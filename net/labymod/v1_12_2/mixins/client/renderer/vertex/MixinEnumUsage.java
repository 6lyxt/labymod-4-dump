// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.renderer.vertex;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import java.util.List;
import net.labymod.v1_12_2.client.render.vertex.EnumUsageCustom;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ceb.b.class })
public class MixinEnumUsage
{
    @Mutable
    @Shadow
    @Final
    private static ceb.b[] i;
    
    @Invoker("<init>")
    private static ceb.b labyMod$newUsage(final String internalName, final int internalId, final String name) {
        return null;
    }
    
    @Inject(method = { "<clinit>" }, at = { @At("TAIL") })
    private static void labyMod$addCustomUsage(final CallbackInfo ci) {
        final List<ceb.b> usages = new ArrayList<ceb.b>(Arrays.asList(MixinEnumUsage.i));
        final ceb.b lastUsage = usages.get(usages.size() - 1);
        final ceb.b genericUsage = labyMod$newUsage("GENERIC", lastUsage.ordinal() + 1, "Generic");
        usages.add(EnumUsageCustom.GENERIC = genericUsage);
        MixinEnumUsage.i = usages.toArray(new ceb.b[0]);
    }
}
