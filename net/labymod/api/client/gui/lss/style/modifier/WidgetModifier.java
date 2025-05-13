// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style.modifier;

import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributePatch;
import net.labymod.api.client.gui.lss.style.reader.SingleInstruction;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.modifier.attribute.WidgetAttributeAliasHandler;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface WidgetModifier
{
    boolean isVariableKey(final String p0);
    
    void registerForwarder(final Forwarder p0);
    
    void registerPostProcessor(final PostProcessor p0);
    
    void registerAliasHandler(final WidgetAttributeAliasHandler p0);
    
    AttributePatch makeAttributePatch(final Widget p0, final Forwarder p1, final SingleInstruction p2, final String p3);
    
    AttributePatch makeAttributePatch(final Widget p0, final Forwarder p1, final SingleInstruction p2, final String p3, final Element p4);
    
    ProcessedObject[] processValue(final Widget p0, final Class<?> p1, final String p2, final String p3) throws Exception;
    
    ProcessedObject[] processValue(final Widget p0, final Class<?> p1, final String p2, final Element p3) throws Exception;
    
    ProcessedObject[] processValue(final Widget p0, final Class<?> p1, final String p2, final String p3, final Element p4) throws Exception;
    
    Forwarder findForwarder(final Widget p0, final String p1);
    
    String findAlias(final Widget p0, final String p1);
    
    Query findQuery(final String p0);
}
