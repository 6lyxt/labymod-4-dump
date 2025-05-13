// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.resources.pack;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Laby;
import net.labymod.core.client.resources.transform.DefaultResourceTransformerRegistry;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;
import java.util.Set;
import javax.annotation.Nullable;
import java.io.InputStream;

record WrappedPackResources(aua delegate) implements aua {
    @Nullable
    public avg<InputStream> a(final String... var1) {
        return (avg<InputStream>)this.delegate.a(var1);
    }
    
    @Nullable
    public avg<InputStream> a(final auc var1, final alr var2) {
        return (avg<InputStream>)this.delegate.a(var1, var2);
    }
    
    public void a(final auc var1, final String var2, final String var3, final aua.a var4) {
        this.delegate.a(var1, var2, var3, (aua.a)new WrappedResourceOutput(var4));
    }
    
    public Set<String> a(final auc var1) {
        return this.delegate.a(var1);
    }
    
    @Nullable
    public <T> T a(@NotNull final aun<T> type) throws IOException {
        return (T)this.delegate.a((aun)type);
    }
    
    public atz a() {
        return this.delegate.a();
    }
    
    public String b() {
        return this.delegate.b();
    }
    
    public void close() {
        this.delegate.close();
    }
    
    record WrappedResourceOutput(aua.a delegate) implements aua.a {
        public void accept(final alr location, final avg<InputStream> inputStreamIoSupplier) {
            this.delegate.accept((Object)location, (Object)(() -> ((DefaultResourceTransformerRegistry)Laby.references().resourceTransformerRegistry()).applyTransformers((ResourceLocation)location, (InputStream)inputStreamIoSupplier.get())));
        }
    }
}
