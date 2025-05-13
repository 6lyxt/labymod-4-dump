// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.sound;

import java.util.Objects;
import java.util.Locale;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;
import java.util.Iterator;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.function.Supplier;
import net.labymod.api.util.Pair;
import java.util.List;
import net.labymod.api.client.sound.SoundType;

public class DefaultSoundType extends SoundType
{
    private List<Pair<String, Supplier<ResourceLocation>>> suppliers;
    
    public DefaultSoundType(final String identifier, final ResourceLocation location, final SoundType parent) {
        super(identifier, location, parent);
    }
    
    @Nullable
    @Override
    public ResourceLocation getLocation() {
        final ResourceLocation fromSuppliers = this.getFromSuppliers();
        if (fromSuppliers != null) {
            return fromSuppliers;
        }
        for (SoundType parent = this.parent; parent != null; parent = parent.getParent()) {
            ResourceLocation parentLocation;
            if (parent instanceof final DefaultSoundType defaultSoundType) {
                parentLocation = defaultSoundType.getFromSuppliers();
            }
            else {
                parentLocation = parent.getLocation();
            }
            if (parentLocation != null) {
                return parentLocation;
            }
        }
        if (this.location != null) {
            return this.location;
        }
        for (SoundType parent = this.parent; parent != null; parent = parent.getParent()) {
            ResourceLocation parentLocation;
            if (parent instanceof final DefaultSoundType defaultSoundType2) {
                parentLocation = defaultSoundType2.location;
            }
            else {
                parentLocation = parent.getLocation();
            }
            if (parentLocation != null) {
                return parentLocation;
            }
        }
        return null;
    }
    
    private ResourceLocation getFromSuppliers() {
        if (this.suppliers != null) {
            for (final Pair<String, Supplier<ResourceLocation>> supplier : this.suppliers) {
                final ResourceLocation resourceLocation = supplier.getSecond().get();
                if (resourceLocation != null) {
                    return resourceLocation;
                }
            }
        }
        return null;
    }
    
    public void bind(@NotNull final String identifier, @NotNull final Supplier<ResourceLocation> supplier) {
        final Pair<String, Supplier<ResourceLocation>> bound = this.getBound(identifier);
        if (bound != null) {
            this.suppliers.remove(bound);
        }
        if (this.suppliers == null) {
            this.suppliers = new ArrayList<Pair<String, Supplier<ResourceLocation>>>();
        }
        this.suppliers.add(0, Pair.of(identifier.toLowerCase(Locale.ROOT), supplier));
    }
    
    public Pair<String, Supplier<ResourceLocation>> getBound(String identifier) {
        if (this.suppliers == null) {
            return null;
        }
        identifier = identifier.toLowerCase(Locale.ROOT);
        for (final Pair<String, Supplier<ResourceLocation>> supplier : this.suppliers) {
            if (supplier.getFirst().equals(identifier)) {
                return supplier;
            }
        }
        return null;
    }
    
    public void setDefault(final ResourceLocation defaultResourceLocation) {
        this.location = defaultResourceLocation;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultSoundType)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        final DefaultSoundType that = (DefaultSoundType)o;
        return Objects.equals(this.suppliers, that.suppliers);
    }
    
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + ((this.suppliers != null) ? this.suppliers.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        String string = "DefaultSoundType{identifier='" + this.identifier;
        if (this.location != null) {
            string = string + ", location=" + String.valueOf(this.location);
        }
        if (this.suppliers != null) {
            final StringBuilder builder = new StringBuilder();
            for (final Pair<String, Supplier<ResourceLocation>> supplier : this.suppliers) {
                builder.append(';').append(supplier.getFirst());
            }
            string = string + ", suppliers=" + builder.substring();
        }
        if (this.parent != null) {
            string = string + ", parent=" + String.valueOf(this.parent);
        }
        return string;
    }
}
