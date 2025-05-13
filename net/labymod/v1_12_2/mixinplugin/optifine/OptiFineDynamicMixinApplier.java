// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixinplugin.optifine;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.mixin.dynamic.DynamicMixinApplier;

public class OptiFineDynamicMixinApplier implements DynamicMixinApplier
{
    private final List<String> mixins;
    
    public OptiFineDynamicMixinApplier() {
        (this.mixins = new ArrayList<String>()).add("net.labymod.v1_8_9.mixins.client.screen.MixinGuiVideoSettings");
    }
    
    @Override
    public boolean apply(final String targetClassName, final String mixinClassName) {
        return !this.mixins.contains(mixinClassName);
    }
}
