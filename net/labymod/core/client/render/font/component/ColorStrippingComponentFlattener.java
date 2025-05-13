// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.component;

import net.labymod.api.Laby;
import net.labymod.api.client.component.flattener.FlattenerListener;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.flattener.ComponentFlattener;

public class ColorStrippingComponentFlattener implements ComponentFlattener
{
    private final ComponentFlattener wrapped;
    
    public ColorStrippingComponentFlattener(final ComponentFlattener wrapped) {
        this.wrapped = wrapped;
    }
    
    @Override
    public void flatten(@NotNull final Component input, @NotNull final FlattenerListener listener) {
        this.wrapped.flatten(input, new WrappedFlattenerListener(listener));
    }
    
    @Override
    public String getIdentifier() {
        return this.wrapped.getIdentifier();
    }
    
    @Override
    public Builder toBuilder() {
        return null;
    }
    
    private static class WrappedFlattenerListener implements FlattenerListener
    {
        private final FlattenerListener wrapped;
        
        private WrappedFlattenerListener(final FlattenerListener wrapped) {
            this.wrapped = wrapped;
        }
        
        @Override
        public void push(@NotNull final Component source) {
            this.wrapped.push(source);
        }
        
        @Override
        public void component(@NotNull final String text) {
            this.wrapped.component(Laby.references().textColorStripper().stripColorCodes(text));
        }
        
        @Override
        public void pop(@NotNull final Component source) {
            this.wrapped.pop(source);
        }
    }
}
