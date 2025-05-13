// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Repeatable;

@Repeatable(Links.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.CLASS)
public @interface Link {
    String value();
    
    byte priority() default 0;
}
