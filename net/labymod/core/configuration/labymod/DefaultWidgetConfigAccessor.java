// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod;

import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.ConfigProvider;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.labymod.WidgetConfigAccessor;
import net.labymod.api.configuration.loader.Config;

@ConfigName("widgets")
public class DefaultWidgetConfigAccessor extends Config implements WidgetConfigAccessor
{
    private int lastSelectedColor;
    
    public DefaultWidgetConfigAccessor() {
        this.lastSelectedColor = -16740376;
    }
    
    @Override
    public int getLastSelectedColor() {
        return this.lastSelectedColor;
    }
    
    @Override
    public void setLastSelectedColor(final int lastSelectedColor) {
        this.lastSelectedColor = lastSelectedColor;
    }
    
    public static class WidgetConfigProvider extends ConfigProvider<WidgetConfigAccessor>
    {
        public static final WidgetConfigProvider INSTANCE;
        
        private WidgetConfigProvider() {
        }
        
        @Override
        protected Class<? extends ConfigAccessor> getType() {
            return DefaultWidgetConfigAccessor.class;
        }
        
        static {
            INSTANCE = new WidgetConfigProvider();
        }
    }
}
