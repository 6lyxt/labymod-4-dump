// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier;

import net.labymod.api.Constants;
import net.labymod.core.client.gui.lss.style.modifier.cache.CacheKey;
import java.util.Iterator;
import net.labymod.api.client.gui.lss.style.function.ElementArray;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.lss.style.modifier.attribute.PropertyAttributePatch;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributePatch;
import net.labymod.api.client.gui.lss.style.reader.SingleInstruction;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.function.Element;
import javax.inject.Inject;
import net.labymod.core.client.gui.lss.style.modifier.queries.MediaQuery;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.BorderRadiusForwarder;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.BorderColorForwarder;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.BorderWidthForwarder;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.BorderForwarder;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.MinMaxSizeForwarder;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.ObjectPositionForwarder;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.priority.HighPriorityForwarder;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.ShadowForwarder;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.StencilForwarder;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.ScaleForwarder;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.MarginForwarder;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.PaddingForwarder;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.WidthHeightForwarder;
import net.labymod.core.client.gui.lss.style.modifier.processors.FontSizePostProcessor;
import net.labymod.core.client.gui.lss.style.modifier.processors.MathPostProcessor;
import net.labymod.core.client.gui.lss.style.modifier.processors.WidgetSizePostProcessor;
import net.labymod.core.client.gui.lss.style.modifier.processors.VarPostProcessor;
import net.labymod.core.client.gui.lss.style.modifier.processors.FilterPostProcessor;
import net.labymod.core.client.gui.lss.style.modifier.processors.ComponentPostProcessor;
import net.labymod.core.client.gui.lss.style.modifier.processors.IconPostProcessor;
import net.labymod.core.client.gui.lss.style.modifier.processors.BorderPostProcessor;
import net.labymod.core.client.gui.lss.style.modifier.processors.ResourceLocationPostProcessor;
import net.labymod.core.client.gui.lss.style.modifier.processors.AnimationTimingFunctionProcessor;
import net.labymod.core.client.gui.lss.style.modifier.processors.ColorPostProcessor;
import net.labymod.core.client.gui.lss.style.modifier.processors.PercentagePostProcessor;
import net.labymod.api.client.gui.screen.widget.attributes.FilterType;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.ArrayList;
import net.labymod.core.client.gui.lss.style.modifier.cache.SimpleWidgetModifierCache;
import net.labymod.core.client.gui.lss.style.modifier.cache.NOPWidgetModifierCache;
import net.labymod.api.client.gui.lss.style.modifier.TypeParser;
import net.labymod.api.client.gui.lss.style.function.FunctionRegistry;
import net.labymod.core.client.gui.lss.style.modifier.forwarder.PropertyValueAccessorForwarder;
import net.labymod.api.client.gui.lss.style.function.parser.ElementParser;
import net.labymod.api.client.gui.lss.style.modifier.attribute.WidgetAttributeAliasHandler;
import net.labymod.api.client.gui.lss.style.modifier.Query;
import net.labymod.api.client.gui.lss.style.modifier.Forwarder;
import java.util.List;
import net.labymod.core.client.gui.lss.style.modifier.cache.WidgetModifierCache;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;
import java.util.Comparator;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.lss.style.modifier.WidgetModifier;

@Singleton
@Implements(WidgetModifier.class)
public class DefaultWidgetModifier implements WidgetModifier
{
    private static final boolean CACHE_DISABLED;
    private static final Comparator<PostProcessor> COMPARATOR;
    private final WidgetModifierCache cache;
    private final List<PostProcessor> postProcessors;
    private final List<Forwarder> forwarders;
    private final List<Query> queries;
    private final List<WidgetAttributeAliasHandler> aliasHandlers;
    private final PostProcessor fallbackPostProcessor;
    private final ElementParser elementParser;
    private final PropertyValueAccessorForwarder propertyValueAccessorForwarder;
    
    @Inject
    public DefaultWidgetModifier(final FunctionRegistry functionRegistry, final TypeParser typeParser, final ElementParser elementParser, final PostProcessor fallbackPostProcessor) {
        this.cache = (DefaultWidgetModifier.CACHE_DISABLED ? new NOPWidgetModifierCache() : new SimpleWidgetModifierCache());
        this.postProcessors = new ArrayList<PostProcessor>();
        this.forwarders = new ArrayList<Forwarder>();
        this.queries = new ArrayList<Query>();
        this.aliasHandlers = new ArrayList<WidgetAttributeAliasHandler>();
        this.propertyValueAccessorForwarder = new PropertyValueAccessorForwarder();
        this.elementParser = elementParser;
        this.fallbackPostProcessor = fallbackPostProcessor;
        functionRegistry.registerFunction("sprite", ResourceLocation.class, Integer.TYPE, Integer.TYPE, Integer.TYPE);
        functionRegistry.registerFunction("location", String.class, String.class);
        functionRegistry.registerFunction("rgba", Integer.TYPE, Integer.TYPE, Integer.TYPE, Float.TYPE);
        functionRegistry.registerFunction("rgb", Integer.TYPE, Integer.TYPE, Integer.TYPE);
        functionRegistry.registerFunction("rgba", Integer.TYPE, Float.TYPE);
        functionRegistry.registerFunction("cubic-bezier", Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE);
        functionRegistry.registerFunction("var", String.class);
        functionRegistry.registerFunction("min", Float.TYPE, Float.TYPE);
        functionRegistry.registerFunction("max", Float.TYPE, Float.TYPE);
        for (final FilterType type : FilterType.values()) {
            if (type.getTypes() != null) {
                functionRegistry.registerFunction(type.getName(), type.getTypes());
            }
        }
        this.registerPostProcessor(new PercentagePostProcessor(typeParser));
        this.registerPostProcessor(new ColorPostProcessor());
        this.registerPostProcessor(new AnimationTimingFunctionProcessor());
        this.registerPostProcessor(new ResourceLocationPostProcessor());
        this.registerPostProcessor(new BorderPostProcessor(this));
        this.registerPostProcessor(new IconPostProcessor());
        this.registerPostProcessor(new ComponentPostProcessor());
        this.registerPostProcessor(new FilterPostProcessor());
        this.registerPostProcessor(new VarPostProcessor(this));
        this.registerPostProcessor(new WidgetSizePostProcessor(this));
        this.registerPostProcessor(new MathPostProcessor());
        this.registerPostProcessor(new FontSizePostProcessor());
        this.registerForwarder(new WidthHeightForwarder());
        this.registerForwarder(new PaddingForwarder());
        this.registerForwarder(new MarginForwarder());
        this.registerForwarder(new ScaleForwarder());
        this.registerForwarder(new StencilForwarder());
        this.registerForwarder(new ShadowForwarder(this));
        this.registerForwarder(new HighPriorityForwarder());
        this.registerForwarder(new ObjectPositionForwarder(this));
        this.registerForwarder(new MinMaxSizeForwarder());
        this.registerForwarder(new BorderForwarder());
        this.registerForwarder(new BorderWidthForwarder());
        this.registerForwarder(new BorderColorForwarder());
        this.registerForwarder(new BorderRadiusForwarder());
        this.queries.add(new MediaQuery());
    }
    
    private Element parseValue(final String value) {
        return this.elementParser.parseElement(value);
    }
    
    @Override
    public boolean isVariableKey(final String key) {
        return key.length() >= 3 && key.charAt(0) == '-' && key.charAt(1) == '-' && key.charAt(2) != '-';
    }
    
    @Override
    public void registerForwarder(final Forwarder forwarder) {
        this.forwarders.add(forwarder);
        this.cache.clearForwarders();
    }
    
    @Override
    public void registerPostProcessor(final PostProcessor postProcessor) {
        this.postProcessors.add(postProcessor);
        this.postProcessors.sort(DefaultWidgetModifier.COMPARATOR);
        this.cache.clearPostProcessors();
    }
    
    @Override
    public void registerAliasHandler(final WidgetAttributeAliasHandler aliasHandler) {
        this.aliasHandlers.add(aliasHandler);
    }
    
    @Override
    public AttributePatch makeAttributePatch(final Widget widget, final Forwarder forwarder, final SingleInstruction instruction, final String value) {
        return this.makeAttributePatch(widget, forwarder, instruction, value, this.parseValue(value));
    }
    
    @Override
    public AttributePatch makeAttributePatch(final Widget widget, final Forwarder forwarder, final SingleInstruction instruction, final String rawValue, final Element element) {
        final Class<?> type = forwarder.getType(widget, instruction.getKey(), element.getRawValue());
        if (type == null) {
            return null;
        }
        return new PropertyAttributePatch(forwarder, type, instruction, element, () -> this.processValue(widget, type, instruction.getKey(), rawValue, element));
    }
    
    @Override
    public ProcessedObject[] processValue(final Widget widget, final Class<?> type, final String key, final String value) throws Exception {
        return this.processValue(widget, type, key, value, this.parseValue(value));
    }
    
    @Override
    public ProcessedObject[] processValue(final Widget widget, final Class<?> type, final String key, final Element element) throws Exception {
        return this.processValue(widget, type, key, element.getRawValue(), element);
    }
    
    @Override
    public ProcessedObject[] processValue(final Widget widget, final Class<?> type, final String key, final String rawValue, final Element value) throws Exception {
        if (type.isArray()) {
            ProcessedObject[] objects;
            if (value instanceof final ElementArray elementArray) {
                final List<Element> elements = elementArray.getElements();
                if (elements.isEmpty()) {
                    return null;
                }
                if (elements.get(0).getRawValue().equalsIgnoreCase("null")) {
                    return null;
                }
                objects = new ProcessedObject[elements.size()];
                for (int i = 0; i < elements.size(); ++i) {
                    final Element element = elements.get(i);
                    final PostProcessor processor = this.findProcessor(key, element, type.getComponentType());
                    objects[i] = ProcessedObject.makeObject(processor, element.getRawValue(), processor.process(widget, type.getComponentType(), key, element));
                }
            }
            else {
                final PostProcessor processor2 = this.findProcessor(key, value, type.getComponentType());
                final Object processed = processor2.process(widget, type.getComponentType(), key, value);
                if (processed == null) {
                    return null;
                }
                objects = new ProcessedObject[] { ProcessedObject.makeObject(processor2, rawValue, processed) };
            }
            return objects;
        }
        final PostProcessor processor3 = this.findProcessor(key, value, type);
        ProcessedObject[] objects = { ProcessedObject.makeObject(processor3, rawValue, processor3.process(widget, type, key, value)) };
        return objects;
    }
    
    @Override
    public Forwarder findForwarder(final Widget widget, final String key) {
        final String aliasKey = this.findAlias(widget, key);
        final Forwarder cachedForwarder = this.cache.findForwarder(aliasKey);
        if (cachedForwarder != null) {
            return cachedForwarder;
        }
        Forwarder finalForwarder = this.propertyValueAccessorForwarder;
        for (final Forwarder forwarder : this.forwarders) {
            if (forwarder.requiresForwarding(widget, aliasKey)) {
                finalForwarder = forwarder;
                break;
            }
        }
        this.cache.addForwarder(aliasKey, finalForwarder);
        return finalForwarder;
    }
    
    @Override
    public String findAlias(final Widget widget, final String key) {
        for (final WidgetAttributeAliasHandler aliasHandler : this.aliasHandlers) {
            final String alias = aliasHandler.mapKey(widget, key);
            if (!alias.equals(key)) {
                return this.findAlias(widget, alias);
            }
        }
        return key;
    }
    
    private PostProcessor findProcessor(final String key, final Element value, final Class<?> type) {
        final CacheKey cacheKey = this.cache.createKey(key, value, type);
        final PostProcessor cachedPostProcessor = this.cache.findPostProcessor(cacheKey);
        if (cachedPostProcessor != null) {
            return cachedPostProcessor;
        }
        PostProcessor finalProcessor = this.fallbackPostProcessor;
        for (final PostProcessor postProcessor : this.postProcessors) {
            if (postProcessor.requiresPostProcessing(key, value, type)) {
                finalProcessor = postProcessor;
                break;
            }
        }
        this.cache.addPostProcessor(cacheKey, finalProcessor);
        return finalProcessor;
    }
    
    @Override
    public Query findQuery(final String key) {
        Query result = null;
        for (final Query query : this.queries) {
            if (query.matches(key)) {
                result = query;
                break;
            }
        }
        return result;
    }
    
    static {
        CACHE_DISABLED = Constants.SystemProperties.getBoolean(Constants.SystemProperties.DISABLE_WIDGET_MODIFIER_CACHE);
        COMPARATOR = Comparator.comparingInt(PostProcessor::getPriority);
    }
}
