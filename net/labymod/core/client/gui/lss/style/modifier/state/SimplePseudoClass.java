// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.state;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.lss.style.modifier.attribute.state.AbstractPseudoClass;

public class SimplePseudoClass extends AbstractPseudoClass
{
    private final AttributeState state;
    
    public SimplePseudoClass(final Element element, final AttributeState state) {
        super(element);
        this.state = state;
    }
    
    public AttributeState state() {
        return this.state;
    }
    
    @Override
    public boolean matchesState(final Widget widget) {
        return this.state.isEnabled(widget);
    }
    
    @Override
    public int getPriority() {
        return this.state.getPriority();
    }
}
