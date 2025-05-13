// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.client.gui;

import com.mojang.serialization.Codec;
import java.util.function.Function;
import java.util.Objects;
import java.util.Optional;
import java.util.function.ToIntFunction;
import java.util.function.IntFunction;

record IntRangeBaseSliderableValueSet<R>(fgr.g base, IntFunction<? extends R> intFunction, ToIntFunction<? super R> mapperFunction) implements fgr.k<R> {
    public double b(final R value) {
        return this.base.b(Integer.valueOf(this.mapperFunction.applyAsInt(value)));
    }
    
    public R b(final double value) {
        return (R)this.intFunction.apply(this.base.a(value));
    }
    
    public Optional<R> a(final R value) {
        final Optional a = this.base.a((Object)this.mapperFunction.applyAsInt(value));
        final IntFunction<? extends R> intFunction = this.intFunction;
        Objects.requireNonNull(intFunction);
        return a.map(intFunction::apply);
    }
    
    public Codec<R> f() {
        final Codec f = this.base.f();
        final IntFunction<? extends R> intFunction = this.intFunction;
        Objects.requireNonNull(intFunction);
        final Function<Integer, Object> function = intFunction::apply;
        final ToIntFunction<? super R> mapperFunction = this.mapperFunction;
        Objects.requireNonNull(mapperFunction);
        return (Codec<R>)f.xmap((Function)function, (Function)mapperFunction::applyAsInt);
    }
}
