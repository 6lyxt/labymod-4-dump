// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.client.gui;

import com.mojang.serialization.Codec;
import java.util.Optional;

public class WrappedIntRangeBase implements emk.g
{
    private final emk.g delegate;
    
    public WrappedIntRangeBase(final emk.g delegate) {
        this.delegate = delegate;
    }
    
    public int d() {
        return this.delegate.d();
    }
    
    public int b() {
        return this.delegate.b();
    }
    
    public Optional<Integer> validateValue(final Integer value) {
        return this.delegate.a((Object)value);
    }
    
    public Codec<Integer> f() {
        return (Codec<Integer>)this.delegate.f();
    }
}
