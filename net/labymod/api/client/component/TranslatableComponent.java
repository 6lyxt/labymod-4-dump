// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component;

import java.util.Objects;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public interface TranslatableComponent extends BaseComponent<TranslatableComponent>
{
    default Builder builder() {
        return new Builder();
    }
    
    String getKey();
    
    @Nullable
    default String getFallback() {
        return null;
    }
    
    List<Component> getArguments();
    
    TranslatableComponent argument(final Component p0);
    
    TranslatableComponent arguments(final Component... p0);
    
    TranslatableComponent arguments(final Collection<Component> p0);
    
    TranslatableComponent plainCopy();
    
    @Deprecated
    default String key() {
        return this.getKey();
    }
    
    @Deprecated
    default List<Component> args() {
        return this.getArguments();
    }
    
    @Deprecated
    default TranslatableComponent args(final Component... args) {
        return this.arguments(args);
    }
    
    @Deprecated
    default TranslatableComponent args(final List<Component> args) {
        return this.arguments(args);
    }
    
    default Builder toBuilder() {
        return new Builder(this);
    }
    
    public static class Builder extends Component.Builder<TranslatableComponent, Builder>
    {
        protected String key;
        protected List<Component> arguments;
        
        protected Builder() {
            this.key = null;
            this.arguments = null;
        }
        
        protected Builder(final TranslatableComponent component) {
            super(component);
            this.key = null;
            this.arguments = null;
            this.key = component.getKey();
            final List<Component> arguments = component.getArguments();
            if (!arguments.isEmpty()) {
                this.arguments = new ArrayList<Component>(arguments);
            }
        }
        
        public Builder key(final String key) {
            this.key = key;
            return this;
        }
        
        public Builder argument(final Component argument) {
            if (this.arguments == null) {
                this.arguments = new ArrayList<Component>();
            }
            this.arguments.add(argument);
            return this;
        }
        
        public Builder arguments(final Component... arguments) {
            for (final Component argument : arguments) {
                this.argument(argument);
            }
            return this;
        }
        
        public Builder arguments(final Collection<Component> arguments) {
            for (final Component argument : arguments) {
                this.argument(argument);
            }
            return this;
        }
        
        @Deprecated
        public Builder args(final Component... args) {
            return this.arguments(args);
        }
        
        @Deprecated
        public Builder args(final Collection<Component> args) {
            return this.arguments(args);
        }
        
        @Override
        public TranslatableComponent build() {
            Objects.requireNonNull(this.key, "Key cannot be null");
            return ComponentService.translatableComponent(this.key, this.isEmpty() ? null : this.buildStyle(), this.children, this.arguments);
        }
    }
}
