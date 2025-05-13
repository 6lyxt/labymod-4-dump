// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.state;

import net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClass;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.Selector;
import net.labymod.api.client.gui.lss.style.modifier.attribute.state.AbstractPseudoClass;

public class HasSelectorPseudoClass extends AbstractPseudoClass
{
    private final Selector subSelector;
    
    public HasSelectorPseudoClass(final Element element, final Selector subSelector) {
        super(element);
        this.subSelector = subSelector;
    }
    
    public Selector subSelector() {
        return this.subSelector;
    }
    
    @Override
    public boolean matchesState(final Widget widget) {
        for (final Widget child : widget.getChildren()) {
            if (this.subSelector.match(child, true)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int getPriority() {
        final PseudoClass pseudoClass = this.subSelector.lastPseudoClass();
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
        final HasSelectorPseudoClass that = (HasSelectorPseudoClass)object;
        return this.subSelector.equals(that.subSelector);
    }
    
    @Override
    public int hashCode() {
        return (this.subSelector != null) ? this.subSelector.hashCode() : 0;
    }
}
