// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component.flattener;

import net.labymod.api.client.component.ComponentService;
import java.util.Iterator;
import java.util.Objects;
import java.util.HashMap;
import net.labymod.api.Laby;
import net.labymod.api.event.client.component.flattener.ComponentFlattenerConstructEvent;
import java.util.concurrent.ConcurrentHashMap;
import net.labymod.api.client.component.Component;
import java.util.function.Function;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public class DefaultComponentFlattener implements ComponentFlattener
{
    private static final int MAX_DEPTH = 512;
    private final ConcurrentMap<Class<?>, Handler> handlers;
    private final Map<Class<?>, Function<Component, String>> mappers;
    private final Map<Class<?>, ComplexMapper<Component>> complexMappers;
    private final Function<Component, String> unknownMapper;
    private final Handler unknownHandler;
    private final String identifier;
    
    @Deprecated(forRemoval = true, since = "4.1.3")
    protected DefaultComponentFlattener() {
        this("unknown-component-flattener");
    }
    
    protected DefaultComponentFlattener(final String identifier) {
        this.handlers = new ConcurrentHashMap<Class<?>, Handler>();
        this.identifier = identifier;
        Laby.fireEvent(new ComponentFlattenerConstructEvent(null, identifier));
        this.mappers = new HashMap<Class<?>, Function<Component, String>>();
        this.complexMappers = new HashMap<Class<?>, ComplexMapper<Component>>();
        this.unknownMapper = null;
        this.unknownHandler = null;
    }
    
    private DefaultComponentFlattener(final Builder builder, final String identifier) {
        this.handlers = new ConcurrentHashMap<Class<?>, Handler>();
        this.identifier = identifier;
        Laby.fireEvent(new ComponentFlattenerConstructEvent(builder, identifier));
        this.mappers = new HashMap<Class<?>, Function<Component, String>>(builder.mappers);
        this.complexMappers = new HashMap<Class<?>, ComplexMapper<Component>>(builder.complexMappers);
        this.unknownMapper = builder.unknownMapper;
        this.unknownHandler = ((component, listener, depth) -> this.unknownMapper.apply(component));
    }
    
    @Override
    public void flatten(final Component input, final FlattenerListener listener) {
        this.flattenInternal(input, listener, 0);
    }
    
    @Override
    public String getIdentifier() {
        return this.identifier;
    }
    
    private void flattenInternal(final Component input, final FlattenerListener listener, final int depth) {
        Objects.requireNonNull(input, "input");
        Objects.requireNonNull(listener, "listener");
        if (depth > 512) {
            throw new IllegalStateException("Exceeded maximum depth of 512");
        }
        final Handler handler = this.getHandler(input);
        listener.push(input);
        try {
            if (handler != null) {
                handler.handle(input, listener, depth + 1);
            }
            for (final Component child : input.children()) {
                this.flattenInternal(child, listener, depth + 1);
            }
        }
        finally {
            listener.pop(input);
        }
    }
    
    private Handler getHandler(final Component input) {
        final Class<?> cls = ComponentService.getActualClass(input);
        final Handler handler = this.handlers.computeIfAbsent(cls, key -> {
            final Handler complexHandler = this.getComplexHandler(key);
            if (complexHandler != null) {
                return complexHandler;
            }
            else {
                final Handler simpleHandler = this.getSimpleHandler(key);
                if (simpleHandler != null) {
                    return simpleHandler;
                }
                else {
                    return Handler.NONE;
                }
            }
        });
        return (handler == Handler.NONE) ? this.unknownHandler : handler;
    }
    
    private Handler getSimpleHandler(final Class<?> cls) {
        final Function<Component, String> mapper = this.mappers.get(cls);
        if (mapper != null) {
            return (component, listener, depth) -> listener.component(mapper.apply(component));
        }
        for (final Map.Entry<Class<?>, Function<Component, String>> entry : this.mappers.entrySet()) {
            if (!entry.getKey().isAssignableFrom(cls)) {
                continue;
            }
            return (component, listener, depth) -> listener.component(entry.getValue().apply(component));
        }
        return null;
    }
    
    private Handler getComplexHandler(final Class<?> cls) {
        final ComplexMapper<Component> mapper = this.complexMappers.get(cls);
        if (mapper != null) {
            return this.createComplexHandler(mapper);
        }
        for (final Map.Entry<Class<?>, ComplexMapper<Component>> entry : this.complexMappers.entrySet()) {
            if (!entry.getKey().isAssignableFrom(cls)) {
                continue;
            }
            return this.createComplexHandler(entry.getValue());
        }
        return null;
    }
    
    protected Handler createComplexHandler(final ComplexMapper<Component> mapper) {
        return (component, listener, depth) -> {
            try {
                mapper.map(component, input -> this.flattenInternal(input, listener, depth));
            }
            catch (final Exception e) {
                mapper.exception(component, input -> this.flattenInternal(input, listener, depth), e);
            }
        };
    }
    
    @Override
    public ComponentFlattener.Builder toBuilder() {
        return new Builder(this);
    }
    
    public static final class Builder implements ComponentFlattener.Builder
    {
        private final Map<Class<?>, Function<Component, String>> mappers;
        private final Map<Class<?>, ComplexMapper<Component>> complexMappers;
        private Function<Component, String> unknownMapper;
        private String identifier;
        
        Builder() {
            this.identifier = "unknown-component-flattener";
            this.mappers = new HashMap<Class<?>, Function<Component, String>>();
            this.complexMappers = new HashMap<Class<?>, ComplexMapper<Component>>();
        }
        
        Builder(final ComponentFlattener flattener) {
            this.identifier = "unknown-component-flattener";
            if (flattener instanceof final DefaultComponentFlattener defaultFlattener) {
                this.identifier = defaultFlattener.identifier;
                this.mappers = new HashMap<Class<?>, Function<Component, String>>(defaultFlattener.mappers);
                this.complexMappers = new HashMap<Class<?>, ComplexMapper<Component>>(defaultFlattener.complexMappers);
                this.unknownMapper = defaultFlattener.unknownMapper;
                return;
            }
            throw new IllegalStateException("Cannot copy a custom flattener");
        }
        
        @Override
        public ComponentFlattener.Builder withIdentifier(final String identifier) {
            this.identifier = identifier;
            return this;
        }
        
        @Override
        public <T extends Component> Builder mapper(final Class<T> type, final Function<T, String> mapper) {
            this.complexMappers.remove(type);
            this.mappers.put(type, (Function<Component, String>)mapper);
            return this;
        }
        
        @Override
        public <T extends Component> Builder complexMapper(final Class<T> type, final ComplexMapper<T> complexMapper) {
            this.mappers.remove(type);
            this.complexMappers.put(type, (ComplexMapper<Component>)complexMapper);
            return this;
        }
        
        @Override
        public Builder unknownMapper(final Function<Component, String> unknownMapper) {
            this.unknownMapper = unknownMapper;
            return this;
        }
        
        @Override
        public ComponentFlattener build() {
            return new DefaultComponentFlattener(this, this.identifier);
        }
    }
    
    private interface Handler
    {
        public static final Handler NONE = (input, listener, depth) -> {};
        
        void handle(final Component p0, final FlattenerListener p1, final int p2);
    }
}
