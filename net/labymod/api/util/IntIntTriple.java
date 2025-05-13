// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import java.util.Objects;

public class IntIntTriple<R>
{
    private int left;
    private int middle;
    private R right;
    
    public IntIntTriple(final int left, final int middle, final R right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }
    
    public int getLeft() {
        return this.left;
    }
    
    public void setLeft(final int left) {
        this.left = left;
    }
    
    public int getMiddle() {
        return this.middle;
    }
    
    public void setMiddle(final int middle) {
        this.middle = middle;
    }
    
    public R getRight() {
        return this.right;
    }
    
    public void setRight(final R right) {
        this.right = right;
    }
    
    public void set(final int left, final int middle, final R right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }
    
    public IntIntTriple<R> copy() {
        return new IntIntTriple<R>(this.left, this.middle, this.right);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final IntIntTriple<?> that = (IntIntTriple<?>)o;
        return this.left == that.left && this.middle == that.middle && Objects.equals(this.right, that.right);
    }
    
    @Override
    public int hashCode() {
        int result = this.left;
        result = 31 * result + this.middle;
        result = 31 * result + ((this.right != null) ? this.right.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "IntIntTriple{left=" + this.left + ", middle=" + this.middle + ", right=" + String.valueOf(this.right);
    }
}
