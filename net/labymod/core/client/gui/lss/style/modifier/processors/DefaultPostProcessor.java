// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.processors;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.function.Element;
import javax.inject.Inject;
import net.labymod.api.client.gui.lss.style.modifier.TypeParser;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;

@Singleton
@Implements(PostProcessor.class)
public class DefaultPostProcessor implements PostProcessor
{
    private final TypeParser typeParser;
    
    @Inject
    public DefaultPostProcessor(final TypeParser typeParser) {
        this.typeParser = typeParser;
    }
    
    @Override
    public boolean requiresPostProcessing(final String key, final Element element, final Class<?> type) {
        return true;
    }
    
    @Override
    public Object process(final Widget widget, final Class<?> type, final String key, final Element element) throws Exception {
        return this.typeParser.parseValue(type, element.getRawValue());
    }
}
