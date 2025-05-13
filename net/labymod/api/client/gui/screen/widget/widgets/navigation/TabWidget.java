// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.navigation;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.ComponentTab;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.theme.config.VanillaThemeConfigAccessor;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.Tab;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
public class TabWidget extends SimpleWidget
{
    private final Tab tab;
    
    public TabWidget(final Tab tab) {
        this.tab = tab;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final VanillaThemeConfigAccessor config = Laby.labyAPI().themeService().getThemeConfig(VanillaThemeConfigAccessor.class);
        final boolean freshUi = PlatformEnvironment.isFreshUI() && config != null && config.freshUI().get();
        if (freshUi) {
            this.addId("fresh-tab");
        }
        final Tab tab = this.tab;
        if (tab instanceof final ComponentTab componentTab) {
            ((AbstractWidget<ComponentWidget>)this).addChild(ComponentWidget.component(componentTab.createComponent()));
        }
    }
    
    @Override
    protected SoundType getInteractionSound() {
        return SoundType.BUTTON_CLICK;
    }
    
    @Override
    protected boolean playInteractionSoundAfterHandling() {
        return true;
    }
    
    public Tab getTab() {
        return this.tab;
    }
    
    @Override
    public void setVisible(final boolean visible) {
        super.setVisible(visible);
        if (this.getParent() instanceof final AbstractWidget abstractWidget) {
            abstractWidget.updateState(true);
        }
    }
}
