// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.util;

import org.spongepowered.asm.mixin.Overwrite;
import java.util.Objects;
import org.spongepowered.asm.mixin.Shadow;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ es.class })
public abstract class MixinChatComponentStyle
{
    @Shadow
    protected List<eu> a;
    
    @Shadow
    public abstract ez b();
    
    @Overwrite
    @Override
    public int hashCode() {
        return Objects.hash(this.b(), this.a);
    }
}
