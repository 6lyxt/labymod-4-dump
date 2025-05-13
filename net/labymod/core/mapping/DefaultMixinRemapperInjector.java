// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.mapping;

import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IRemapper;
import java.lang.reflect.Method;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.mapping.MixinRemapperInjector;

@Singleton
@Implements(MixinRemapperInjector.class)
public class DefaultMixinRemapperInjector implements MixinRemapperInjector
{
    private Object remapperChain;
    private Method addRemapperMethod;
    
    @Override
    public void injectRemapper(final IRemapper remapper) {
        try {
            if (this.remapperChain == null) {
                final Method getRemappersMethod = MixinEnvironment.class.getMethod("getRemappers", (Class<?>[])new Class[0]);
                getRemappersMethod.setAccessible(true);
                this.remapperChain = getRemappersMethod.invoke(MixinEnvironment.getDefaultEnvironment(), new Object[0]);
            }
            if (this.addRemapperMethod == null) {
                (this.addRemapperMethod = this.remapperChain.getClass().getMethod("add", IRemapper.class)).setAccessible(true);
            }
            this.addRemapperMethod.invoke(this.remapperChain, remapper);
        }
        catch (final ReflectiveOperationException exception) {
            throw new RuntimeException("Failed to register Mixin remapper", exception);
        }
    }
}
