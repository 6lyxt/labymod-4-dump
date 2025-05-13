// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.main.laby;

import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface NotificationsConfig extends ConfigAccessor
{
    ConfigProperty<Boolean> enabled();
    
    ConfigProperty<Integer> maxNotifications();
    
    ConfigProperty<Boolean> hideChatTrustToast();
    
    ConfigProperty<Boolean> screenshot();
    
    ConfigProperty<Boolean> screenshotSound();
}
