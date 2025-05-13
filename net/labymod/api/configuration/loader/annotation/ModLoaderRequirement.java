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
public @interface ModLoaderRequirement {
    String loaderId() default "any";
    
    ModRequirement.RequirementState state() default ModRequirement.RequirementState.INSTALLED;
}
