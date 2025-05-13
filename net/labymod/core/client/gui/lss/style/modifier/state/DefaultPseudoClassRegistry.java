// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.state;

import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.lss.style.function.Function;
import net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClass;
import javax.inject.Inject;
import net.labymod.api.client.gui.lss.style.Selector;
import net.labymod.core.client.gui.lss.style.DefaultSelector;
import net.labymod.api.client.gui.lss.style.function.Element;
import java.util.HashMap;
import net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClassFactory;
import java.util.Map;
import net.labymod.api.client.gui.lss.style.function.parser.ElementParser;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClassRegistry;

@Singleton
@Implements(PseudoClassRegistry.class)
public class DefaultPseudoClassRegistry implements PseudoClassRegistry
{
    private final ElementParser elementParser;
    private final Map<String, PseudoClassFactory> factories;
    
    @Inject
    public DefaultPseudoClassRegistry(final ElementParser elementParser) {
        this.factories = new HashMap<String, PseudoClassFactory>();
        this.elementParser = elementParser;
        this.registerFactory("not", (function, value) -> new InvertedPseudoClass(function, this.parse(value)));
        this.registerFactory("has", (function, value) -> {
            new HasSelectorPseudoClass(function, new DefaultSelector(value.getRawValue()));
            return;
        });
        this.registerFactory("has-parent", (function, value) -> {
            new HasParentPseudoClass(function, new DefaultSelector(value.getRawValue()));
            return;
        });
    }
    
    @Override
    public void registerFactory(final String key, final PseudoClassFactory factory) {
        this.factories.put(key, factory);
    }
    
    @Override
    public PseudoClass parse(final String rawValue) {
        return this.parse(this.elementParser.parseElement(rawValue));
    }
    
    @Override
    public PseudoClass parse(final Element value) {
        if (!(value instanceof Function)) {
            final AttributeState state = AttributeState.getByName(this.processKey(value.getRawValue()));
            if (state == AttributeState.NORMAL) {
                return null;
            }
            return new SimplePseudoClass(value, state);
        }
        else {
            final Function function = (Function)value;
            final String key = this.processKey(function.getName());
            final PseudoClassFactory factory = this.factories.get(key);
            if (factory == null) {
                throw new IllegalArgumentException("Invalid attribute state \"" + key + "(...)\"");
            }
            if (function.parameters().length != 1) {
                throw new IllegalArgumentException("Invalid attribute state parameters in \"" + key + "(...)\"");
            }
            return factory.create(function, function.parameters()[0]);
        }
    }
    
    private String processKey(final String key) {
        if (key.isEmpty()) {
            throw new IllegalArgumentException("Invalid element: \"\"");
        }
        final String processed = (key.charAt(0) == ':') ? key.substring(1) : key;
        if (processed.isEmpty()) {
            throw new IllegalArgumentException("Invalid element: \":\"");
        }
        return processed;
    }
}
