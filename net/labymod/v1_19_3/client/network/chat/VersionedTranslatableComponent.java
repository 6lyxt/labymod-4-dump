// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.client.network.chat;

import java.util.Collection;
import net.labymod.api.client.component.Component;
import java.util.List;
import net.labymod.v1_19_3.client.network.chat.contents.TranslatableContentsAccessor;
import net.labymod.api.client.component.TranslatableComponent;

public class VersionedTranslatableComponent extends VersionedBaseComponent<TranslatableComponent, TranslatableContentsAccessor> implements TranslatableComponent
{
    public VersionedTranslatableComponent(final tf holder) {
        super(holder);
    }
    
    @Override
    public TranslatableComponent plainCopy() {
        return (TranslatableComponent)((MutableComponentAccessor)ss.b(this.getKey())).getLabyComponent();
    }
    
    @Override
    public String getKey() {
        return ((VersionedBaseComponent<T, TranslatableContentsAccessor>)this).getContents().getKey();
    }
    
    @Override
    public List<Component> getArguments() {
        return ((VersionedBaseComponent<T, TranslatableContentsAccessor>)this).getContents().getLabyArguments();
    }
    
    @Override
    public TranslatableComponent argument(final Component argument) {
        ((VersionedBaseComponent<T, TranslatableContentsAccessor>)this).getContents().getLabyArguments().add(argument);
        return this;
    }
    
    @Override
    public TranslatableComponent arguments(final Component... arguments) {
        return this.arguments(List.of(arguments));
    }
    
    @Override
    public TranslatableComponent arguments(final Collection<Component> arguments) {
        ((VersionedBaseComponent<T, TranslatableContentsAccessor>)this).getContents().getLabyArguments().addAll(arguments);
        return this;
    }
}
