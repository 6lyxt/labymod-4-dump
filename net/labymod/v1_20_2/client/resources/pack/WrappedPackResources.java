// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.client.resources.pack;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Laby;
import net.labymod.core.client.resources.transform.DefaultResourceTransformerRegistry;
import java.io.IOException;
import java.util.Set;
import org.jetbrains.annotations.Nullable;
import java.io.InputStream;

record WrappedPackResources(amh delegate) implements amh {
    @Nullable
    public anl<InputStream> a(final String... var1) {
        return (anl<InputStream>)this.delegate.a(var1);
    }
    
    @Nullable
    public anl<InputStream> a(final ami var1, final aew var2) {
        return (anl<InputStream>)this.delegate.a(var1, var2);
    }
    
    public void a(final ami var1, final String var2, final String var3, final amh.a var4) {
        this.delegate.a(var1, var2, var3, (amh.a)new WrappedResourceOutput(var4));
    }
    
    public Set<String> a(final ami var1) {
        return this.delegate.a(var1);
    }
    
    @Nullable
    public <T> T a(final amt<T> var1) throws IOException {
        return (T)this.delegate.a((amt)var1);
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
    
    record WrappedResourceOutput(amh.a delegate) implements amh.a {
        public void accept(final aew location, final anl<InputStream> inputStreamIoSupplier) {
            this.delegate.accept((Object)location, (Object)(() -> ((DefaultResourceTransformerRegistry)Laby.references().resourceTransformerRegistry()).applyTransformers((ResourceLocation)location, (InputStream)inputStreamIoSupplier.get())));
        }
    }
}
