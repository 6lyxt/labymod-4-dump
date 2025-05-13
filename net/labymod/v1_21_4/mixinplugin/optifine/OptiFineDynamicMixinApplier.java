// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixinplugin.optifine;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.mixin.dynamic.DynamicMixinApplier;

public class OptiFineDynamicMixinApplier implements DynamicMixinApplier
{
    private final List<String> mixins;
    
    public OptiFineDynamicMixinApplier() {
        this.mixins = new ArrayList<String>();
        this.addMixin("mojang.blaze3d.vertex.MixinBufferUploader");
        this.addMixin("client.screen.MixinLoadingOverlayDarkBackground");
        this.addMixin("client.renderer.entity.layers.MixinHumanoidArmorLayer");
        this.addMixin("client.MixinScreenshot");
        this.addMixin("client.renderer.MixinLevelRendererRenderWorldEvent");
        this.addMixin("client.renderer.MixinScreenEffectRenderer");
        this.addMixin("client.MixinLevelRenderer_CustomBlockOutline");
        this.addMixin("client.renderer.MixinLevelRendererLevelRenderContext");
    }
    
    @Override
    public boolean apply(final String targetClassName, final String mixinClassName) {
        return !this.mixins.contains(mixinClassName);
    }
    
    private void addMixin(final String path) {
        this.mixins.add("net.labymod.v1_21_4.mixins." + path);
    }
}
