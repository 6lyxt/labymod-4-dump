// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.web;

import java.util.Locale;
import java.util.Iterator;
import java.util.ArrayList;
import net.labymod.api.util.KeyValue;
import java.util.List;

public class UrlBuilder
{
    private final String endpoint;
    private final List<KeyValue<String>> parameters;
    
    public UrlBuilder(final String endpoint) {
        this.parameters = new ArrayList<KeyValue<String>>();
        this.endpoint = endpoint;
    }
    
    public UrlBuilder addParameter(final String key, final Object value) {
        return this.addParameter(key, value.toString());
    }
    
    public UrlBuilder addParameter(final String key, final String value) {
        final KeyValue<String> pair = new KeyValue<String>(key, value);
        this.parameters.remove(pair);
        this.parameters.add(pair);
        return this;
    }
    
    public List<KeyValue<String>> getParameters() {
        return this.parameters;
    }
    
    public String getParameterValue(final String key) {
        for (final KeyValue<String> parameter : this.parameters) {
            if (parameter.getKey().equals(key)) {
                return parameter.getValue();
            }
        }
        return null;
    }
    
    public String build() {
        boolean hasParameters = this.endpoint.contains("?");
        final StringBuilder url = new StringBuilder(this.endpoint);
        for (final KeyValue<String> parameter : this.parameters) {
            url.append(String.format(Locale.ROOT, "%s%s=%s", hasParameters ? "&" : "?", parameter.getKey(), parameter.getValue()));
            hasParameters = true;
        }
        return url.toString();
    }
}
