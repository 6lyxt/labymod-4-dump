// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mapping;

import org.spongepowered.asm.mixin.extensibility.IRemapper;
import net.labymod.api.Laby;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface MixinRemapperInjector
{
    default MixinRemapperInjector instance() {
        return Laby.references().mixinRemapperInjector();
    }
    
    void injectRemapper(final IRemapper p0);
}
