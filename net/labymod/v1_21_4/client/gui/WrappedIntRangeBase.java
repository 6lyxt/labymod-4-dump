// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.gui;

import com.mojang.serialization.Codec;
import java.util.Optional;

public class WrappedIntRangeBase implements fln.g
{
    private final fln.g delegate;
    
    public WrappedIntRangeBase(final fln.g delegate) {
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
