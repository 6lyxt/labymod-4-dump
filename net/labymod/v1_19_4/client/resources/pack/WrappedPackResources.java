// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.client.resources.pack;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Laby;
import net.labymod.core.client.resources.transform.DefaultResourceTransformerRegistry;
import java.io.IOException;
import java.util.Set;
import org.jetbrains.annotations.Nullable;
import java.io.InputStream;

record WrappedPackResources(ajv delegate) implements ajv {
    @Nullable
    public akz<InputStream> a(final String... var1) {
        return (akz<InputStream>)this.delegate.a(var1);
    }
    
    @Nullable
    public akz<InputStream> a(final ajw var1, final add var2) {
        return (akz<InputStream>)this.delegate.a(var1, var2);
    }
    
    public void a(final ajw var1, final String var2, final String var3, final ajv.a var4) {
        this.delegate.a(var1, var2, var3, (ajv.a)new WrappedResourceOutput(var4));
    }
    
    public Set<String> a(final ajw var1) {
        return this.delegate.a(var1);
    }
    
    @Nullable
    public <T> T a(final akh<T> var1) throws IOException {
        return (T)this.delegate.a((akh)var1);
    }
    
    public String a() {
        return this.delegate.a();
    }
    
    public boolean b() {
        return this.delegate.b();
    }
    
    public void close() {
        this.delegate.close();
    }
    
    record WrappedResourceOutput(ajv.a delegate) implements ajv.a {
        public void accept(final add location, final akz<InputStream> inputStreamIoSupplier) {
            this.delegate.accept((Object)location, (Object)(() -> ((DefaultResourceTransformerRegistry)Laby.references().resourceTransformerRegistry()).applyTransformers((ResourceLocation)location, (InputStream)inputStreamIoSupplier.get())));
        }
    }
}
