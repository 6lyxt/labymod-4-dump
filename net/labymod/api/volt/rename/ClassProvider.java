// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.volt.rename;

import java.io.InputStream;
import java.net.URL;
import java.io.IOException;
import net.labymod.api.volt.asm.util.ASMHelper;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class ClassProvider
{
    private static ClassProvider singleton;
    private final Map<String, ClassInfo> classes;
    private final ResourceSupplier resourceSupplier;
    
    public ClassProvider(final ResourceSupplier resourceSupplier) {
        this.classes = new ConcurrentHashMap<String, ClassInfo>();
        this.resourceSupplier = resourceSupplier;
    }
    
    public static ClassProvider getSingleton(final ResourceSupplier supplier) {
        if (ClassProvider.singleton == null) {
            ClassProvider.singleton = new ClassProvider(supplier);
        }
        return ClassProvider.singleton;
    }
    
    public ClassInfo getOrLoad(final String name) {
        if (name == null) {
            return null;
        }
        ClassInfo classInfo = this.classes.get(name);
        if (classInfo != null) {
            return classInfo;
        }
        final String resourceName = name.replace('.', '/').concat(".class");
        final URL resource = this.resourceSupplier.findResource(resourceName);
        if (resource == null) {
            return null;
        }
        try (final InputStream stream = resource.openStream()) {
            classInfo = ClassInfo.parse(this, ASMHelper.getClassNode(stream));
            this.classes.put(name, classInfo);
            return classInfo;
        }
        catch (final IOException exception) {
            throw new IllegalStateException("Unable to load class info", exception);
        }
    }
    
    public interface ResourceSupplier
    {
        URL findResource(final String p0);
    }
}
