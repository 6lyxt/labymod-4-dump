// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.processors;

import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.lss.LssPropertyException;
import net.labymod.api.client.resources.ThemeResourceRegistry;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.core.client.gui.lss.style.function.parameter.FixedElement;
import net.labymod.api.client.gui.lss.style.function.Function;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;

public class IconPostProcessor implements PostProcessor
{
    @Override
    public boolean requiresPostProcessing(final String key, final Element element, final Class<?> type) {
        return type == Icon.class && ((element instanceof Function && ((Function)element).getName().equals("sprite")) || element instanceof FixedElement);
    }
    
    @Override
    public Object process(final Widget widget, final Class<?> type, final String key, final Element element) throws Exception {
        if (element instanceof final Function function) {
            final ProcessedObject[] values = function.firstValues(widget, key);
            final ResourceLocation location = (ResourceLocation)values[0].value();
            final int x = (int)values[1].value();
            final int y = (int)values[2].value();
            final int size = (int)values[3].value();
            return Icon.sprite(location, x * size, y * size, size, size, 128, 128);
        }
        final String id = element.getRawValue();
        final Icon icon = ThemeResourceRegistry.getIcon(id);
        if (icon == null) {
            throw new LssPropertyException("Unknown sprite icon: '" + id);
        }
        return icon;
    }
}
