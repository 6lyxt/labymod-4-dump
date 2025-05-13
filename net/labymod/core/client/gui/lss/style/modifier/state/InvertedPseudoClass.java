// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.state;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClass;
import net.labymod.api.client.gui.lss.style.modifier.attribute.state.AbstractPseudoClass;

public class InvertedPseudoClass extends AbstractPseudoClass
{
    private final PseudoClass state;
    
    public InvertedPseudoClass(final Element element, final PseudoClass state) {
        super(element);
        this.state = state;
    }
    
    public PseudoClass state() {
        return this.state;
    }
    
    @Override
    public boolean matchesState(final Widget widget) {
        return !this.state.matchesState(widget);
    }
    
    @Override
    public int getPriority() {
        return this.state.getPriority();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final InvertedPseudoClass that = (InvertedPseudoClass)o;
        return this.state.equals(that.state);
    }
    
    @Override
    public int hashCode() {
        return (this.state != null) ? this.state.hashCode() : 0;
    }
}
