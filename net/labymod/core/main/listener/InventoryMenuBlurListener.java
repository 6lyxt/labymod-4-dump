// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.listener;

import net.labymod.api.configuration.labymod.main.laby.ingame.MenuBlurConfig;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.ScreenRenderEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.gui.screen.ScreenService;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gfx.pipeline.post.processors.PostProcessors;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.main.LabyMod;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;

public final class InventoryMenuBlurListener
{
    private boolean isInInventory;
    private static final long OFFSET = 50L;
    private long lastTimeOpen;
    
    public InventoryMenuBlurListener() {
        this.isInInventory = false;
    }
    
    @Subscribe
    public void onScreenDisplay(final ScreenDisplayEvent event) {
        final ScreenInstance screen = event.getScreen();
        if (screen == null) {
            this.setInInventory(false);
            return;
        }
        final Object object = screen.mostInnerScreen();
        final ScreenService screenService = LabyMod.references().screenService();
        if (!screenService.isInventory(object)) {
            this.setInInventory(false);
            return;
        }
        if (this.isInInventory) {
            return;
        }
        if (this.lastTimeOpen < TimeUtil.getMillis()) {
            PostProcessors.resetMenuBlur();
        }
        this.setInInventory(true);
    }
    
    @Subscribe
    public void onScreenRender(final ScreenRenderEvent event) {
        if (event.phase() != Phase.PRE || !this.isInInventory) {
            return;
        }
        PostProcessors.processMenuBlur(MenuBlurConfig.ScreenType.INVENTORIES, event.getTickDelta());
    }
    
    private void setInInventory(final boolean state) {
        this.lastTimeOpen = TimeUtil.getMillis() + 50L;
        this.isInInventory = state;
    }
}
