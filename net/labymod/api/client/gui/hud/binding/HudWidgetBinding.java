// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.binding;

import net.labymod.api.Laby;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.service.Identifiable;

public abstract class HudWidgetBinding implements Identifiable
{
    protected final String namespace;
    protected final String id;
    
    public HudWidgetBinding(@NotNull final Object holder, @NotNull final String id) {
        Objects.requireNonNull(holder, "Holder cannot be null");
        Objects.requireNonNull(id, "Identifier cannot be null");
        this.namespace = Laby.labyAPI().getNamespace(holder);
        if (this.namespace.equals("labymod")) {
            throw new IllegalArgumentException("Please supply a holder that belongs to your addon");
        }
        this.id = id;
    }
    
    protected HudWidgetBinding(final String id) {
        this.namespace = "labymod";
        this.id = id;
    }
    
    @Override
    public String getId() {
        return this.id;
    }
    
    public String getNamespace() {
        return this.namespace;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final HudWidgetBinding that = (HudWidgetBinding)o;
        return Objects.equals(this.namespace, that.getNamespace()) && Objects.equals(this.id, that.getId());
    }
    
    @Override
    public int hashCode() {
        int result = this.namespace.hashCode();
        result = 31 * result + this.id.hashCode();
        return result;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{namespace='" + this.namespace + "', id='" + this.id + "'}";
    }
}
