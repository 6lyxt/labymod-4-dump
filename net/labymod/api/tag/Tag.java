// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.tag;

import java.util.stream.Stream;
import java.util.Optional;

public final class Tag
{
    private static final StackWalker WALKER;
    private static final String STATIC_CONSTRUCTOR_NAME = "<clinit>";
    private final int id;
    private final String namespace;
    private final String name;
    
    private Tag(final String namespace, final String name) {
        this.id = TagRegistry.ID_COUNTER.getAndIncrement();
        this.namespace = namespace;
        this.name = name;
    }
    
    public static Tag of(final String namespace, final String name) {
        return TagRegistry.getOrRegister(namespace, name, Tag::createTag);
    }
    
    @Deprecated(forRemoval = true, since = "4.1.12")
    public static Tag ofInternal(final String name) {
        return TagRegistry.getOrRegister("deprecated", name, Tag::new);
    }
    
    private static Tag createTag(final String namespace, final String name) {
        final StackWalker.StackFrame frame = Tag.WALKER.walk(frames -> frames.skip(3L).findFirst()).orElse(null);
        if (frame == null) {
            throw new IllegalStateException("Unable to retrieve stack frame");
        }
        final String methodName = frame.getMethodName();
        if (!"<clinit>".equals(methodName)) {
            throw new IllegalStateException("Tag.of(namespace, name) may only be used for constants as this is very performance intensive");
        }
        return new Tag(namespace, name);
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getNamespace() {
        return this.namespace;
    }
    
    public String getName() {
        return this.name;
    }
    
    @Override
    public String toString() {
        return this.namespace + ":" + this.name + "[" + this.id;
    }
    
    static {
        WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
    }
}
