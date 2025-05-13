// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mixin.dynamic;

public final class AlwaysDynamicMixinApplier implements DynamicMixinApplier
{
    public static final AlwaysDynamicMixinApplier INSTANCE;
    
    private AlwaysDynamicMixinApplier() {
    }
    
    @Override
    public boolean apply(final String targetClassName, final String mixinClassName) {
        return true;
    }
    
    static {
        INSTANCE = new AlwaysDynamicMixinApplier();
    }
}
