// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.loader.annotation;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface SpriteSlot {
    int x() default 0;
    
    int y() default 0;
    
    int size() default 16;
    
    int page() default 0;
}
