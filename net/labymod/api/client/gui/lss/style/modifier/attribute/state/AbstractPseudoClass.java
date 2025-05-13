// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style.modifier.attribute.state;

import net.labymod.api.client.gui.lss.style.function.Element;

public abstract class AbstractPseudoClass implements PseudoClass
{
    private final Element element;
    
    public AbstractPseudoClass(final Element element) {
        this.element = element;
    }
    
    @Override
    public Element element() {
        return this.element;
    }
}
