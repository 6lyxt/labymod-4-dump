// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.client.resources.pack;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Laby;
import net.labymod.core.client.resources.transform.DefaultResourceTransformerRegistry;
import java.io.IOException;
import java.util.Set;
import org.jetbrains.annotations.Nullable;
import java.io.InputStream;

record WrappedPackResources(atb delegate) implements atb {
    @Nullable
    public auh<InputStream> a(final String... var1) {
        return (auh<InputStream>)this.delegate.a(var1);
    }
    
    @Nullable
    public auh<InputStream> a(final atd var1, final alf var2) {
        return (auh<InputStream>)this.delegate.a(var1, var2);
    }
    
    public void a(final atd var1, final String var2, final String var3, final atb.a var4) {
        this.delegate.a(var1, var2, var3, (atb.a)new WrappedResourceOutput(var4));
    }
    
    public Set<String> a(final atd var1) {
        return this.delegate.a(var1);
    }
    
    @Nullable
    public <T> T a(final ato<T> var1) throws IOException {
        return (T)this.delegate.a((ato)var1);
    }
    
    public ata a() {
        return this.delegate.a();
    }
    
    public String b() {
        return this.delegate.b();
    }
    
    public void close() {
        this.delegate.close();
    }
    
    record WrappedResourceOutput(atb.a delegate) implements atb.a {
        public void accept(final alf location, final auh<InputStream> inputStreamIoSupplier) {
            this.delegate.accept((Object)location, (Object)(() -> ((DefaultResourceTransformerRegistry)Laby.references().resourceTransformerRegistry()).applyTransformers((ResourceLocation)location, (InputStream)inputStreamIoSupplier.get())));
        }
    }
}
