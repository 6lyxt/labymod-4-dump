// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.state;

import net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClass;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.Selector;
import net.labymod.api.client.gui.lss.style.modifier.attribute.state.AbstractPseudoClass;

public class HasParentPseudoClass extends AbstractPseudoClass
{
    private final Selector parentSelector;
    
    public HasParentPseudoClass(final Element element, final Selector parentSelector) {
        super(element);
        this.parentSelector = parentSelector;
    }
    
    @Override
    public boolean matchesState(final Widget widget) {
        return widget.getParent() instanceof Widget && this.parentSelector.match((Widget)widget.getParent(), true);
    }
    
    @Override
    public int getPriority() {
        final PseudoClass pseudoClass = this.parentSelector.lastPseudoClass();
        return (pseudoClass != null) ? pseudoClass.getPriority() : 0;
    }
    
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        final HasParentPseudoClass that = (HasParentPseudoClass)object;
        return this.parentSelector.equals(that.parentSelector);
    }
    
    @Override
    public int hashCode() {
        return (this.parentSelector != null) ? this.parentSelector.hashCode() : 0;
    }
}
