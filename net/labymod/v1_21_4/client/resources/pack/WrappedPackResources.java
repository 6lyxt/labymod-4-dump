// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.resources.pack;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.Laby;
import net.labymod.core.client.resources.transform.DefaultResourceTransformerRegistry;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;
import java.util.Set;
import javax.annotation.Nullable;
import java.io.InputStream;

record WrappedPackResources(atc delegate) implements atc {
    @Nullable
    public auh<InputStream> a(final String... var1) {
        return (auh<InputStream>)this.delegate.a(var1);
    }
    
    @Nullable
    public auh<InputStream> a(final ate var1, final akv var2) {
        return (auh<InputStream>)this.delegate.a(var1, var2);
    }
    
    public void a(final ate var1, final String var2, final String var3, final atc.a var4) {
        this.delegate.a(var1, var2, var3, (atc.a)new WrappedResourceOutput(var4));
    }
    
    public Set<String> a(final ate var1) {
        return this.delegate.a(var1);
    }
    
    @Nullable
    public <T> T a(@NotNull final atp<T> type) throws IOException {
        return (T)this.delegate.a((atp)type);
    }
    
    public atb a() {
        return this.delegate.a();
    }
    
    public String b() {
        return this.delegate.b();
    }
    
    public void close() {
        this.delegate.close();
    }
    
    record WrappedResourceOutput(atc.a delegate) implements atc.a {
        public void accept(final akv location, final auh<InputStream> inputStreamIoSupplier) {
            this.delegate.accept((Object)location, (Object)(() -> ((DefaultResourceTransformerRegistry)Laby.references().resourceTransformerRegistry()).applyTransformers((ResourceLocation)location, (InputStream)inputStreamIoSupplier.get())));
        }
    }
}
