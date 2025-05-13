// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.resources.pack;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Laby;
import net.labymod.core.client.resources.transform.DefaultResourceTransformerRegistry;
import java.io.IOException;
import java.util.Set;
import org.jetbrains.annotations.Nullable;
import java.io.InputStream;

record WrappedPackResources(ais delegate) implements ais {
    @Nullable
    public ajw<InputStream> a(final String... var1) {
        return (ajw<InputStream>)this.delegate.a(var1);
    }
    
    @Nullable
    public ajw<InputStream> a(final ait var1, final acf var2) {
        return (ajw<InputStream>)this.delegate.a(var1, var2);
    }
    
    public void a(final ait var1, final String var2, final String var3, final ais.a var4) {
        this.delegate.a(var1, var2, var3, (ais.a)new WrappedResourceOutput(var4));
    }
    
    public Set<String> a(final ait var1) {
        return this.delegate.a(var1);
    }
    
    @Nullable
    public <T> T a(final aje<T> var1) throws IOException {
        return (T)this.delegate.a((aje)var1);
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
    
    record WrappedResourceOutput(ais.a delegate) implements ais.a {
        public void accept(final acf location, final ajw<InputStream> inputStreamIoSupplier) {
            this.delegate.accept((Object)location, (Object)(() -> ((DefaultResourceTransformerRegistry)Laby.references().resourceTransformerRegistry()).applyTransformers((ResourceLocation)location, (InputStream)inputStreamIoSupplier.get())));
        }
    }
}
