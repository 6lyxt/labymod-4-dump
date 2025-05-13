// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.web;

import java.util.Locale;
import java.util.HashSet;
import java.util.Set;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class UrlQuery
{
    private final Builder builder;
    private final List<Param> params;
    
    private UrlQuery(final Builder builder) {
        this.builder = builder;
        this.params = new ArrayList<Param>();
        for (final Param param : builder.getParams()) {
            this.params.add(param.copy());
        }
    }
    
    public static UrlQuery create(final Builder builder) {
        return new UrlQuery(builder);
    }
    
    public Object getParamValue(final String key) {
        final Param param = this.getParam(key);
        if (param == null) {
            return null;
        }
        return param.getValue();
    }
    
    public UrlQuery setParam(final String key, final Object value) {
        final Param param = this.getParam(key);
        if (param == null) {
            throw new IllegalArgumentException("The param " + key + " has not been set by the builder");
        }
        param.setValue(value);
        return this;
    }
    
    public List<Param> getChangedParams(final String... ignoredKeys) {
        final List<String> ignored = new ArrayList<String>();
        Collections.addAll(ignored, ignoredKeys);
        final List<Param> changedParams = new ArrayList<Param>();
        for (final Param param : this.params) {
            if (!ignored.contains(param.getKey()) && param.isChanged()) {
                changedParams.add(param);
            }
        }
        return changedParams;
    }
    
    public boolean isChanged(final String... ignoredKeys) {
        return !this.getChangedParams(ignoredKeys).isEmpty();
    }
    
    public String build() {
        final UrlBuilder urlBuilder = new UrlBuilder(this.builder.getUrl());
        for (final Param param : this.params) {
            if (param.getValue() != null) {
                urlBuilder.addParameter(param.getKey(), param.getValue().toString());
            }
        }
        return urlBuilder.build();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final UrlQuery query = (UrlQuery)o;
        return query.builder.url.equals(this.builder.url) && query.params.equals(this.params);
    }
    
    private Param getParam(final String key) {
        for (final Param param : this.params) {
            if (param.getKey().equals(key)) {
                return param;
            }
        }
        return null;
    }
    
    public enum Direction
    {
        ASCENDING("asc"), 
        DESCENDING("desc");
        
        private final String id;
        
        private Direction(final String id) {
            this.id = id;
        }
        
        public String getId() {
            return this.id;
        }
        
        @Override
        public String toString() {
            return this.getId();
        }
    }
    
    public static class Builder
    {
        private final String url;
        private final Set<Param> params;
        
        private Builder(final String url) {
            this.url = url;
            this.params = new HashSet<Param>();
        }
        
        public static Builder of(final String url) {
            return new Builder(url);
        }
        
        public static Builder of(final String url, final Object... args) {
            return new Builder(String.format(Locale.ROOT, url, args));
        }
        
        public String getUrl() {
            return this.url;
        }
        
        public Set<Param> getParams() {
            return this.params;
        }
        
        public Builder addParam(final String key, final Object defaultValue) {
            this.params.add(new Param(key, defaultValue));
            return this;
        }
        
        public UrlQuery build() {
            return UrlQuery.create(this);
        }
    }
    
    public static class Param
    {
        private final String key;
        private Object value;
        private boolean changed;
        
        private Param(final String key, final Object value) {
            this.key = key;
            this.value = value;
        }
        
        public String getKey() {
            return this.key;
        }
        
        public Object getValue() {
            return this.value;
        }
        
        public void setValue(final Object value) {
            this.value = value;
            this.changed = true;
        }
        
        public boolean isChanged() {
            return this.changed;
        }
        
        @Override
        public boolean equals(final Object object) {
            if (object instanceof final Param param) {
                return this.key.equals(param.getKey()) && this.value.toString().equals(param.getValue().toString());
            }
            return false;
        }
        
        private Param copy() {
            return new Param(this.key, this.value);
        }
    }
}
