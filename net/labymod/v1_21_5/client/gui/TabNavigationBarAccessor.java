// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.gui;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.ComponentTab;
import com.google.common.collect.ImmutableList;

public interface TabNavigationBarAccessor
{
    ImmutableList<fwb> getTabs();
    
    fwc getTabManager();
    
    VersionedTab versionedTabOf(final fwb p0);
    
    public static class VersionedTab extends ComponentTab
    {
        private static final ComponentMapper COMPONENT_MAPPER;
        private final fwb tab;
        
        public VersionedTab(final fwb tab) {
            this.tab = tab;
        }
        
        @Override
        public Component createComponent() {
            return VersionedTab.COMPONENT_MAPPER.fromMinecraftComponent(this.tab.a());
        }
        
        @Override
        public ScreenInstance createScreen() {
            return null;
        }
        
        public fwb tab() {
            return this.tab;
        }
        
        static {
            COMPONENT_MAPPER = Laby.references().componentMapper();
        }
    }
}
