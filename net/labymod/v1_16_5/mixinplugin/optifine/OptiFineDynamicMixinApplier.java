// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixinplugin.optifine;

import net.labymod.api.models.version.Version;
import net.labymod.api.util.version.SemanticVersion;
import net.labymod.api.util.version.serial.VersionDeserializer;
import net.labymod.api.loader.platform.PlatformEnvironment;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.mixin.dynamic.DynamicMixinApplier;

public class OptiFineDynamicMixinApplier implements DynamicMixinApplier
{
    private final List<String> mixins;
    
    public OptiFineDynamicMixinApplier() {
        (this.mixins = new ArrayList<String>()).add("net.labymod.v1_16_5.mixins.client.renderer.MixinShaderInstance");
        this.mixins.add("net.labymod.v1_16_5.mixins.mojang.blaze3d.vertex.MixinBufferUploader");
        this.mixins.add("net.labymod.v1_16_5.mixins.client.renderer.entity.layers.MixinHumanoidArmorLayer");
        this.mixins.add("net.labymod.v1_16_5.mixins.client.MixinScreenshot");
        this.mixins.add("net.labymod.v1_16_5.mixins.client.renderer.MixinBufferSource");
        this.mixins.add("net.labymod.v1_16_5.mixins.client.renderer.MixinScreenEffectRenderer");
        final String runningVersion = PlatformEnvironment.getRunningVersion();
        final Version version = VersionDeserializer.from(runningVersion);
        if (version.isGreaterThan((Version)new SemanticVersion("1.19"))) {
            this.mixins.add("net.labymod.v1_16_5.mixins.compatibility.optifine.MixinOptiFineBufferUploader");
        }
    }
    
    @Override
    public boolean apply(final String targetClassName, final String mixinClassName) {
        return !this.mixins.contains(mixinClassName);
    }
}
