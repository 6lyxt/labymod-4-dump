// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import java.util.Objects;
import org.jetbrains.annotations.Nullable;

public class Pair<F, S>
{
    @Nullable
    private F first;
    @Nullable
    private S second;
    
    private Pair(@Nullable final F first, @Nullable final S second) {
        this.first = first;
        this.second = second;
    }
    
    public static <T, U> Pair<T, U> of(@Nullable final T first, @Nullable final U second) {
        return new Pair<T, U>(first, second);
    }
    
    @Nullable
    public F getFirst() {
        return this.first;
    }
    
    public void setFirst(@Nullable final F first) {
        this.first = first;
    }
    
    @Nullable
    public S getSecond() {
        return this.second;
    }
    
    public void setSecond(@Nullable final S second) {
        this.second = second;
    }
    
    public void set(@Nullable final F first, @Nullable final S second) {
        this.first = first;
        this.second = second;
    }
    
    public Pair<F, S> copy() {
        return new Pair<F, S>(this.first, this.second);
    }
    
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        final Pair<?, ?> pair = (Pair<?, ?>)object;
        return Objects.equals(this.first, pair.first) && Objects.equals(this.second, pair.second);
    }
    
    @Override
    public int hashCode() {
        int result = (this.first != null) ? this.first.hashCode() : 0;
        result = 31 * result + ((this.second != null) ? this.second.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "Pair{first=" + String.valueOf(this.first) + ", second=" + String.valueOf(this.second);
    }
}
