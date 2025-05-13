// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.platform;

import net.labymod.api.client.gui.screen.ScreenService;
import java.util.Objects;
import net.labymod.api.client.os.OperatingSystemAccessorFactory;
import net.labymod.core.client.os.DefaultOperatingSystemAccessorFactory;
import net.labymod.api.client.resources.pack.ResourcePackRepository;
import net.labymod.api.client.Minecraft;
import net.labymod.core.main.LabyMod;

public abstract class Platform
{
    protected LabyMod labyMod;
    protected Minecraft minecraft;
    protected ResourcePackRepository resourcePackRepository;
    protected PlatformScreenHandler<?> platformScreenHandler;
    
    public void initialize(final LabyMod labyMod) {
        this.labyMod = labyMod;
        this.setOperatingSystemAccessorFactory(new DefaultOperatingSystemAccessorFactory());
        this.onInitialization();
        if (this.platformScreenHandler != null) {
            this.platformScreenHandler.onInitialize();
        }
    }
    
    protected abstract void onInitialization();
    
    public abstract void onPostStartup();
    
    public void setMinecraft(final Minecraft minecraft) {
        this.minecraft = minecraft;
        this.labyMod.setMinecraft(minecraft);
    }
    
    public void setOperatingSystemAccessorFactory(final OperatingSystemAccessorFactory osAccessorFactory) {
        this.labyMod.setOperatingSystemAccessorFactory(osAccessorFactory);
    }
    
    public void setPlatformScreenHandler(final PlatformScreenHandler<?> platformScreenHandler) {
        this.platformScreenHandler = platformScreenHandler;
        final ScreenService screenService2;
        final ScreenService screenService = screenService2 = platformScreenHandler.getScreenService();
        Objects.requireNonNull(platformScreenHandler);
        screenService2.setInventoryCondition(platformScreenHandler::isInventoryScreen);
        this.labyMod.setScreenService(screenService);
    }
}
