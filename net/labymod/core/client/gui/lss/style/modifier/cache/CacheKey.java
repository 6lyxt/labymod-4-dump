// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.cache;

import java.util.Objects;
import net.labymod.api.client.gui.lss.style.function.Element;

record CacheKey(String key, Element element, Class<?> type) {
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        final CacheKey cacheKey = (CacheKey)object;
        return Objects.equals(this.key, cacheKey.key) && Objects.equals(this.element, cacheKey.element) && Objects.equals(this.type, cacheKey.type);
    }
    
    @Override
    public int hashCode() {
        int result = (this.key != null) ? this.key.hashCode() : 0;
        result = 31 * result + ((this.element != null) ? this.element.hashCode() : 0);
        result = 31 * result + ((this.type != null) ? this.type.hashCode() : 0);
        return result;
    }
}
