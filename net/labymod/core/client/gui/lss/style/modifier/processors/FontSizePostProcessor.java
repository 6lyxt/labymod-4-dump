// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.processors;

import net.labymod.api.client.gui.lss.LssPropertyException;
import net.labymod.api.util.StringUtil;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.render.font.FontSize;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;

public class FontSizePostProcessor implements PostProcessor
{
    @Override
    public boolean requiresPostProcessing(final String key, final Element element, final Class<?> type) {
        return type == FontSize.class;
    }
    
    @Override
    public Object process(final Widget widget, final Class<?> type, final String key, final Element element) throws Exception {
        final String rawValue = element.getRawValue();
        if (!rawValue.contains(".")) {
            final FontSize.PredefinedFontSize fontSize = FontSize.PredefinedFontSize.of(StringUtil.toUppercase(rawValue));
            if (fontSize != null) {
                return FontSize.predefined(fontSize);
            }
        }
        try {
            return FontSize.custom(Float.parseFloat(rawValue));
        }
        catch (final NumberFormatException e) {
            throw new LssPropertyException("Invalid fontSize value: " + rawValue);
        }
    }
    
    @Override
    public int getPriority() {
        return 1;
    }
}
