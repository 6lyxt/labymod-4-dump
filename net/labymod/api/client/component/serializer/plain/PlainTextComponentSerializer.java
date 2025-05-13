// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component.serializer.plain;

import net.labymod.api.client.component.builder.StyleableBuilder;
import java.util.Objects;
import java.net.URI;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.uri.URIParser;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.flattener.ComponentFlattener;

public class PlainTextComponentSerializer
{
    private static final ComponentFlattener DEFAULT_FLATTENER;
    private static final PlainTextComponentSerializer INSTANCE;
    private static final PlainTextComponentSerializer URL_INSTANCE;
    private final ComponentFlattener flattener;
    private final boolean extractUrls;
    
    private PlainTextComponentSerializer(final ComponentFlattener flattener, final boolean extractUrls) {
        this.flattener = flattener;
        this.extractUrls = extractUrls;
    }
    
    public static PlainTextComponentSerializer plainText() {
        return PlainTextComponentSerializer.INSTANCE;
    }
    
    public static PlainTextComponentSerializer plainUrl() {
        return PlainTextComponentSerializer.URL_INSTANCE;
    }
    
    public static PlainTextComponentSerializer of(final ComponentFlattener flattener) {
        return of(flattener, false);
    }
    
    public static PlainTextComponentSerializer of(final ComponentFlattener flattener, final boolean extractUrls) {
        return new PlainTextComponentSerializer(flattener, extractUrls);
    }
    
    public TextComponent deserialize(final String input) {
        if (!this.extractUrls) {
            return Component.text(input);
        }
        final TextComponent component = Component.empty();
        final StringBuilder builder = new StringBuilder();
        final String[] segments = input.split(" ");
        for (int i = 0; i < segments.length; ++i) {
            final String segment = segments[i];
            final URI uri = URIParser.parseSegment(segment);
            if (uri == null) {
                builder.append(segment).append(' ');
            }
            else {
                final String scheme = uri.getScheme();
                if (scheme == null) {
                    builder.append(segment).append(' ');
                }
                else {
                    if (!builder.isEmpty()) {
                        component.append(Component.text(builder.toString()));
                        builder.setLength();
                    }
                    component.append(Component.text(segment, ((StyleableBuilder<T, Style.Builder>)Style.builder()).clickEvent(ClickEvent.openUrl(uri.toString())).build()));
                    if (i != segments.length - 1) {
                        component.append(Component.space());
                    }
                }
            }
        }
        if (!builder.isEmpty()) {
            component.append(Component.text(builder.substring()));
            builder.setLength();
        }
        return component;
    }
    
    public String serialize(final Component component) {
        if (component == null) {
            throw new IllegalArgumentException("Passed null as Component argument");
        }
        final StringBuilder stringBuilder = new StringBuilder();
        final ComponentFlattener flattener = this.flattener;
        final StringBuilder obj = stringBuilder;
        Objects.requireNonNull(obj);
        flattener.flatten(component, obj::append);
        return stringBuilder.toString();
    }
    
    static {
        DEFAULT_FLATTENER = ComponentFlattener.BASIC.toBuilder().withIdentifier("basic_unknown").unknownMapper(component -> {
            throw new UnsupportedOperationException("Don't know how to turn " + component.getClass().getSimpleName() + " into a string");
        }).build();
        INSTANCE = new PlainTextComponentSerializer(PlainTextComponentSerializer.DEFAULT_FLATTENER, false);
        URL_INSTANCE = new PlainTextComponentSerializer(PlainTextComponentSerializer.DEFAULT_FLATTENER, true);
    }
}
