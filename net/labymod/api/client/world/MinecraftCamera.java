// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world;

import net.labymod.api.util.math.Quaternion;
import net.labymod.api.util.math.vector.DoubleVector3;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.volt.annotation.RenamedFrom;
import net.labymod.api.util.math.vector.FloatVector3;

public interface MinecraftCamera
{
    @Deprecated(forRemoval = true, since = "4.2.53")
    @RenamedFrom("position()Lnet/labymod/api/util/math/vector/FloatVector3;")
    @NotNull
    FloatVector3 deprecated$position();
    
    @Deprecated(forRemoval = true, since = "4.2.53")
    @RenamedFrom("renderPosition()Lnet/labymod/api/util/math/vector/FloatVector3;")
    @NotNull
    default FloatVector3 deprecated$renderPosition() {
        return this.deprecated$position();
    }
    
    @NotNull
    DoubleVector3 position();
    
    @NotNull
    default DoubleVector3 renderPosition() {
        return this.position();
    }
    
    @NotNull
    Quaternion rotation();
    
    @Deprecated(forRemoval = true, since = "4.1.12")
    default float getX() {
        return this.deprecated$position().getX();
    }
    
    @Deprecated(forRemoval = true, since = "4.1.12")
    default float getY() {
        return this.deprecated$position().getY();
    }
    
    @Deprecated(forRemoval = true, since = "4.1.12")
    default float getZ() {
        return this.deprecated$position().getZ();
    }
    
    default float getYaw() {
        return this.rotation().getYaw();
    }
    
    default float getPitch() {
        return this.rotation().getPitch();
    }
    
    default float getRoll() {
        return this.rotation().getRoll();
    }
}
