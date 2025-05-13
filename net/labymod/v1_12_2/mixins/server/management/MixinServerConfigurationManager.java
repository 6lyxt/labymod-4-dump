// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.server.management;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_12_2.server.VersionedServerConfigurationManager;

@Mixin({ pl.class })
public class MixinServerConfigurationManager implements VersionedServerConfigurationManager
{
    @Shadow
    private boolean u;
    
    @Override
    public boolean isCommandsAllowedForAll() {
        return this.u;
    }
}
