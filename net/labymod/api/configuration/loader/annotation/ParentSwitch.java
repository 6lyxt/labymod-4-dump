// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.loader.annotation;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Deprecated(forRemoval = true, since = "4.2.11")
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ParentSwitch {
    boolean hide() default true;
}
