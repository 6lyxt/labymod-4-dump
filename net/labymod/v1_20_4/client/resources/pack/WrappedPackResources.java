// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.resources.pack;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Laby;
import net.labymod.core.client.resources.transform.DefaultResourceTransformerRegistry;
import java.io.IOException;
import java.util.Set;
import org.jetbrains.annotations.Nullable;
import java.io.InputStream;

record WrappedPackResources(aow delegate) implements aow {
    @Nullable
    public aqa<InputStream> a(final String... var1) {
        return (aqa<InputStream>)this.delegate.a(var1);
    }
    
    @Nullable
    public aqa<InputStream> a(final aox var1, final ahg var2) {
        return (aqa<InputStream>)this.delegate.a(var1, var2);
    }
    
    public void a(final aox var1, final String var2, final String var3, final aow.a var4) {
        this.delegate.a(var1, var2, var3, (aow.a)new WrappedResourceOutput(var4));
    }
    
    public Set<String> a(final aox var1) {
        return this.delegate.a(var1);
    }
    
    @Nullable
    public <T> T a(final api<T> var1) throws IOException {
        return (T)this.delegate.a((api)var1);
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
    
    record WrappedResourceOutput(aow.a delegate) implements aow.a {
        public void accept(final ahg location, final aqa<InputStream> inputStreamIoSupplier) {
            this.delegate.accept((Object)location, (Object)(() -> ((DefaultResourceTransformerRegistry)Laby.references().resourceTransformerRegistry()).applyTransformers((ResourceLocation)location, (InputStream)inputStreamIoSupplier.get())));
        }
    }
}
