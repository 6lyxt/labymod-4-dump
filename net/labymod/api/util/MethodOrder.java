// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
public @interface MethodOrder {
    String before() default "";
    
    String after() default "";
}
