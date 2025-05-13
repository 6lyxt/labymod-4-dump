// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.transformer;

import net.labymod.api.addon.exception.AddonTransformException;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles;
import net.minecraft.launchwrapper.Launch;
import java.lang.invoke.MethodHandle;
import net.minecraft.launchwrapper.IClassTransformer;

public class AddonClassTransformer implements IClassTransformer
{
    private final MethodHandle transformMethod;
    
    public AddonClassTransformer() {
        try {
            final Class<?> transformer = Launch.classLoader.findClass("net.labymod.core.addon.AddonTransformer");
            this.transformMethod = MethodHandles.publicLookup().findStatic(transformer, "transform", MethodType.methodType(byte[].class, String.class, String.class, byte[].class));
        }
        catch (final NoSuchMethodException | ClassNotFoundException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    public byte[] transform(final String name, final String transformedName, final byte[] classData) {
        try {
            return this.transformMethod.invoke(name, transformedName, classData);
        }
        catch (final Throwable e) {
            if (e instanceof final AddonTransformException ex) {
                throw ex;
            }
            throw new AddonTransformException("Failed to transform class '" + name, e);
        }
    }
}
