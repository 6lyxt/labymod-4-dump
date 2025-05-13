// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD })
public @interface SettingOrder {
    byte value();
    
    public static class Order
    {
        public static final byte FIRST = -127;
        public static final byte SOON = -64;
        public static final byte NORMAL = 0;
        public static final byte LATE = 64;
        public static final byte LAST = 126;
        
        private Order() {
        }
    }
}
