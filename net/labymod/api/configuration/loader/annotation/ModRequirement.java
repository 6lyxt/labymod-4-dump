// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.loader.annotation;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ModRequirement {
    String namespace();
    
    RequirementState state() default RequirementState.INSTALLED;
    
    RequirementType type() default RequirementType.ADDON;
    
    public enum RequirementState
    {
        INSTALLED, 
        NOT_INSTALLED;
    }
    
    public enum RequirementType
    {
        ADDON((String)null), 
        FABRIC_MOD("fabricloader"), 
        FORGE_MOD("forge"), 
        NEO_FORGE_MOD("neoforge");
        
        private final String loaderId;
        
        private RequirementType(final String loaderId) {
            this.loaderId = loaderId;
        }
        
        public String getLoaderId() {
            return this.loaderId;
        }
    }
}
