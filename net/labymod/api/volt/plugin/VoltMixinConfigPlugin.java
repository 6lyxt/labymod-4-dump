// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.volt.plugin;

import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.objectweb.asm.tree.ClassNode;
import java.util.List;
import java.util.Set;
import net.labymod.api.mixin.dynamic.DynamicMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;

public class VoltMixinConfigPlugin implements IMixinConfigPlugin
{
    private final DynamicMixinConfigPlugin dynamicMixinConfigPlugin;
    
    public VoltMixinConfigPlugin() {
        this.dynamicMixinConfigPlugin = new DynamicMixinConfigPlugin();
    }
    
    public void onLoad(final String mixinPackage) {
        this.dynamicMixinConfigPlugin.onLoad(Thread.currentThread().getContextClassLoader(), mixinPackage, System.getProperty("net.labymod.running-version"));
    }
    
    public String getRefMapperConfig() {
        return null;
    }
    
    public boolean shouldApplyMixin(final String targetClassName, final String mixinClassName) {
        return this.dynamicMixinConfigPlugin.shouldApply(this::isValid, targetClassName, mixinClassName);
    }
    
    protected boolean isValid(final String name) {
        return true;
    }
    
    public void acceptTargets(final Set<String> myTargets, final Set<String> otherTargets) {
    }
    
    public List<String> getMixins() {
        return List.of();
    }
    
    public void preApply(final String targetClassName, final ClassNode targetClass, final String mixinClassName, final IMixinInfo mixinInfo) {
    }
    
    public void postApply(final String targetClassName, final ClassNode targetClass, final String mixinClassName, final IMixinInfo mixinInfo) {
    }
}
