// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.annotation;

import net.labymod.api.configuration.settings.SwitchableHandler;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface SettingElement {
    boolean extended() default false;
    
    Class<? extends SwitchableHandler> switchable() default SwitchableHandler.class;
}
