// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.loader.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface PermissionRequired {
    String value();
    
    boolean canForceEnable() default false;
}
