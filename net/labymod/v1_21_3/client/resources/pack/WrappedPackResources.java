// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.resources.pack;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Laby;
import net.labymod.core.client.resources.transform.DefaultResourceTransformerRegistry;
import java.io.IOException;
import java.util.Set;
import javax.annotation.Nullable;
import java.io.InputStream;

record WrappedPackResources(aug delegate) implements aug {
    @Nullable
    public avn<InputStream> a(final String... var1) {
        return (avn<InputStream>)this.delegate.a(var1);
    }
    
    @Nullable
    public avn<InputStream> a(final aui var1, final alz var2) {
        return (avn<InputStream>)this.delegate.a(var1, var2);
    }
    
    public void a(final aui var1, final String var2, final String var3, final aug.a var4) {
        this.delegate.a(var1, var2, var3, (aug.a)new WrappedResourceOutput(var4));
    }
    
    public Set<String> a(final aui var1) {
        return this.delegate.a(var1);
    }
    
    @Nullable
    public <T> T a(final aut<T> var1) throws IOException {
        return (T)this.delegate.a((aut)var1);
    }
    
    public auf a() {
        return this.delegate.a();
    }
    
    public String b() {
        return this.delegate.b();
    }
    
    public void close() {
        this.delegate.close();
    }
    
    record WrappedResourceOutput(aug.a delegate) implements aug.a {
        public void accept(final alz location, final avn<InputStream> inputStreamIoSupplier) {
            this.delegate.accept((Object)location, (Object)(() -> ((DefaultResourceTransformerRegistry)Laby.references().resourceTransformerRegistry()).applyTransformers((ResourceLocation)location, (InputStream)inputStreamIoSupplier.get())));
        }
    }
}
