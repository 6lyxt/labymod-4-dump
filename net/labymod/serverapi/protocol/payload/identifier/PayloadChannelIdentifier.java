// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.serverapi.protocol.payload.identifier;

import java.util.Objects;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Deprecated(forRemoval = true, since = "4.2.24")
public final class PayloadChannelIdentifier
{
    private final String namespace;
    private final String path;
    
    private PayloadChannelIdentifier(@NotNull final String namespace, @NotNull final String path) {
        this.namespace = namespace;
        this.path = path;
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    public static PayloadChannelIdentifier create(@NotNull final String namespace, @NotNull final String path) {
        return new PayloadChannelIdentifier(namespace, path);
    }
    
    @NotNull
    public String getNamespace() {
        return this.namespace;
    }
    
    @NotNull
    public String getPath() {
        return this.path;
    }
    
    @Contract(pure = true)
    @NotNull
    @Override
    public String toString() {
        return this.namespace + ":" + this.path;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final PayloadChannelIdentifier that = (PayloadChannelIdentifier)o;
        return Objects.equals(this.namespace, that.namespace) && Objects.equals(this.path, that.path);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.namespace, this.path);
    }
}
