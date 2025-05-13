// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface LabyEvent {
    boolean background() default false;
    
    boolean classLoaderExclusive() default false;
    
    boolean allowAllExceptions() default false;
    
    Class<? extends RuntimeException>[] allowExceptions() default {};
}
