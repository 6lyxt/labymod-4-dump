// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.server.management;

import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_8_9.server.VersionedServerConfigurationManager;

@Mixin({ lx.class })
public class MixinServerConfigurationManager implements VersionedServerConfigurationManager
{
    @Shadow
    private boolean t;
    
    @Override
    public boolean isCommandsAllowedForAll() {
        return this.t;
    }
}
