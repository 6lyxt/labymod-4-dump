// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.modifier.processors;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.style.function.Function;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.client.gui.lss.style.modifier.SingleFunctionPostProcessor;

public class ResourceLocationPostProcessor extends SingleFunctionPostProcessor
{
    private final ResourceLocationFactory resourceLocationFactory;
    
    public ResourceLocationPostProcessor() {
        super("location");
        this.resourceLocationFactory = Laby.labyAPI().renderPipeline().resources().resourceLocationFactory();
    }
    
    @Override
    public Object process(final Widget widget, final Class<?> type, final String key, final Function function) throws Exception {
        final Object[] values = function.firstValues(widget, key);
        final String namespace = (String)values[0];
        final String path = (String)values[1];
        final ResourceLocation location = this.resourceLocationFactory.create(namespace, path);
        if (type == Icon.class) {
            return Icon.texture(location);
        }
        return location;
    }
    
    @Override
    public int getPriority() {
        return 3;
    }
}
