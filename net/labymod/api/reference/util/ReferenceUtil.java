// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.reference.util;

import java.util.function.Consumer;

public final class ReferenceUtil
{
    private static Consumer<Throwable> throwableConsumer;
    
    private ReferenceUtil() {
    }
    
    public static void setThrowableConsumer(final Consumer<Throwable> throwableConsumer) {
        ReferenceUtil.throwableConsumer = throwableConsumer;
    }
    
    public static void onError(final Throwable throwable) {
        final Consumer<Throwable> consumer = ReferenceUtil.throwableConsumer;
        if (consumer == null) {
            return;
        }
        consumer.accept(throwable);
    }
    
    static {
        ReferenceUtil.throwableConsumer = Throwable::printStackTrace;
    }
}
