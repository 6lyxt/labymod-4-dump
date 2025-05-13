// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.client.resources.pack;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Laby;
import net.labymod.core.client.resources.transform.DefaultResourceTransformerRegistry;
import java.io.IOException;
import java.util.Set;
import org.jetbrains.annotations.Nullable;
import java.io.InputStream;

record WrappedPackResources(asq delegate) implements asq {
    @Nullable
    public atw<InputStream> a(final String... var1) {
        return (atw<InputStream>)this.delegate.a(var1);
    }
    
    @Nullable
    public atw<InputStream> a(final ass var1, final akr var2) {
        return (atw<InputStream>)this.delegate.a(var1, var2);
    }
    
    public void a(final ass var1, final String var2, final String var3, final asq.a var4) {
        this.delegate.a(var1, var2, var3, (asq.a)new WrappedResourceOutput(var4));
    }
    
    public Set<String> a(final ass var1) {
        return this.delegate.a(var1);
    }
    
    @Nullable
    public <T> T a(final atd<T> var1) throws IOException {
        return (T)this.delegate.a((atd)var1);
    }
    
    public asp a() {
        return this.delegate.a();
    }
    
    public String b() {
        return this.delegate.b();
    }
    
    public void close() {
        this.delegate.close();
    }
    
    record WrappedResourceOutput(asq.a delegate) implements asq.a {
        public void accept(final akr location, final atw<InputStream> inputStreamIoSupplier) {
            this.delegate.accept((Object)location, (Object)(() -> ((DefaultResourceTransformerRegistry)Laby.references().resourceTransformerRegistry()).applyTransformers((ResourceLocation)location, (InputStream)inputStreamIoSupplier.get())));
        }
    }
}
