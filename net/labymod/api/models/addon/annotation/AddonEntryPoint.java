// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.models.addon.annotation;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AddonEntryPoint {
    Point value() default Point.EARLY_INITIALIZATION;
    
    int priority() default 1000;
    
    public enum Point
    {
        EARLY_INITIALIZATION, 
        LOAD, 
        ENABLE;
    }
}
