// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import java.util.Objects;
import org.jetbrains.annotations.Nullable;

public final class Triple<L, M, R>
{
    @Nullable
    private L left;
    @Nullable
    private M middle;
    @Nullable
    private R right;
    
    public Triple(@Nullable final L left, @Nullable final M middle, @Nullable final R right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }
    
    public static <F, S, T> Triple<F, S, T> of(@Nullable final F first, @Nullable final S second, @Nullable final T third) {
        return new Triple<F, S, T>(first, second, third);
    }
    
    @Nullable
    public L getLeft() {
        return this.left;
    }
    
    public void setLeft(@Nullable final L left) {
        this.left = left;
    }
    
    @Nullable
    public M getMiddle() {
        return this.middle;
    }
    
    public void setMiddle(@Nullable final M middle) {
        this.middle = middle;
    }
    
    @Nullable
    public R getRight() {
        return this.right;
    }
    
    public void setRight(@Nullable final R right) {
        this.right = right;
    }
    
    public void set(@Nullable final L left, @Nullable final M middle, @Nullable final R right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }
    
    public Triple<L, M, R> copy() {
        return new Triple<L, M, R>(this.left, this.middle, this.right);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Triple<?, ?, ?> triple = (Triple<?, ?, ?>)o;
        return Objects.equals(this.left, triple.left) && Objects.equals(this.middle, triple.middle) && Objects.equals(this.right, triple.right);
    }
    
    @Override
    public int hashCode() {
        int result = (this.left != null) ? this.left.hashCode() : 0;
        result = 31 * result + ((this.middle != null) ? this.middle.hashCode() : 0);
        result = 31 * result + ((this.right != null) ? this.right.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "Triple{left=" + String.valueOf(this.left) + ", middle=" + String.valueOf(this.middle) + ", right=" + String.valueOf(this.right);
    }
}
