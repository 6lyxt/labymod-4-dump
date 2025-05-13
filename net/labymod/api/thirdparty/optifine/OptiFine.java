// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.thirdparty.optifine;

import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface OptiFine
{
    public static final String BETTER_GRASS_CLASS_NAME = "net.optifine.BetterGrass";
    public static final boolean BUNDLED_OPTIFINE = Constants.SystemProperties.getBoolean(Constants.SystemProperties.BUNDLED_OPTIFINE);
    public static final String NAMESPACE = "optifine";
    public static final String FABRIC_MOD_ID = "optifabric";
    
    default boolean isPresent() {
        return Laby.references().optiFine().isOptiFinePresent();
    }
    
    default boolean isPresentViaFabric() {
        return Laby.references().optiFine().isOptiFabricPresent();
    }
    
    default OptiFineConfig config() {
        return Laby.references().optiFine().optiFineConfig();
    }
    
    boolean isOptiFinePresent();
    
    boolean isOptiFabricPresent();
    
    OptiFineConfig optiFineConfig();
}
