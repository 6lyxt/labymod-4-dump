// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.addon.transformer;

import net.labymod.api.addon.exception.AddonTransformException;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import net.labymod.api.util.reflection.Reflection;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandle;
import net.labymod.api.addon.transform.LoadedAddonClassTransformer;

public class WrappedAddonClassTransformer implements LoadedAddonClassTransformer
{
    private final Class<?> transformerInterface;
    private final Object transformer;
    private final MethodHandle shouldTransformMethod;
    private final MethodHandle transformMethod;
    
    public WrappedAddonClassTransformer(final Class<?> transformerInterface, final Object transformer) throws NoSuchMethodException, IllegalAccessException {
        this.transformerInterface = transformerInterface;
        this.transformer = transformer;
        this.transformMethod = Reflection.findVirtual(this.transformerInterface, "transform", MethodType.methodType(byte[].class, String.class, String.class, byte[].class));
        this.shouldTransformMethod = Reflection.findVirtual(this.transformerInterface, "shouldTransform", MethodType.methodType(Boolean.TYPE, String.class, String.class));
    }
    
    @Override
    public void init() {
        try {
            final Method method = this.transformerInterface.getDeclaredMethod("init", (Class<?>[])new Class[0]);
            method.invoke(this.transformer, new Object[0]);
        }
        catch (final NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public boolean shouldTransform(final String name, final String transformedName) {
        try {
            return this.shouldTransformMethod.invoke(this.transformer, name, transformedName);
        }
        catch (final Throwable throwable) {
            throw new AddonTransformException("Failed to check if the class '" + name + "' should be transformed", throwable);
        }
    }
    
    @Override
    public byte[] transform(final String name, final String transformedName, final byte[] classBytes) {
        try {
            return this.transformMethod.invoke(this.transformer, name, transformedName, classBytes);
        }
        catch (final Throwable throwable) {
            throw new AddonTransformException("Failed to transform class '" + name, throwable);
        }
    }
}
