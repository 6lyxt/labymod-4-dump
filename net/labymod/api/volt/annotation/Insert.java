// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.volt.annotation;

import net.labymod.api.volt.callback.JumpStatement;
import org.spongepowered.asm.mixin.injection.At;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Insert {
    String[] method();
    
    At at();
    
    boolean cancellable() default false;
    
    JumpStatement jumpStatement() default JumpStatement.RETURN;
}
