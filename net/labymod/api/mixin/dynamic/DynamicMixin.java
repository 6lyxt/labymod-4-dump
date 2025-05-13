// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mixin.dynamic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.TYPE })
public @interface DynamicMixin {
    String value();
    
    Class<? extends DynamicMixinApplier> applier() default AlwaysDynamicMixinApplier.class;
}
