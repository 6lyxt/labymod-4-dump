// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.client.resources.pack;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Laby;
import net.labymod.core.client.resources.transform.DefaultResourceTransformerRegistry;
import java.io.IOException;
import java.util.Set;
import org.jetbrains.annotations.Nullable;
import java.io.InputStream;

record WrappedPackResources(ajl delegate) implements ajl {
    @Nullable
    public akp<InputStream> a(final String... var1) {
        return (akp<InputStream>)this.delegate.a(var1);
    }
    
    @Nullable
    public akp<InputStream> a(final ajm var1, final acq var2) {
        return (akp<InputStream>)this.delegate.a(var1, var2);
    }
    
    public void a(final ajm var1, final String var2, final String var3, final ajl.a var4) {
        this.delegate.a(var1, var2, var3, (ajl.a)new WrappedResourceOutput(var4));
    }
    
    public Set<String> a(final ajm var1) {
        return this.delegate.a(var1);
    }
    
    @Nullable
    public <T> T a(final ajx<T> var1) throws IOException {
        return (T)this.delegate.a((ajx)var1);
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
    
    record WrappedResourceOutput(ajl.a delegate) implements ajl.a {
        public void accept(final acq location, final akp<InputStream> inputStreamIoSupplier) {
            this.delegate.accept((Object)location, (Object)(() -> ((DefaultResourceTransformerRegistry)Laby.references().resourceTransformerRegistry()).applyTransformers((ResourceLocation)location, (InputStream)inputStreamIoSupplier.get())));
        }
    }
}
