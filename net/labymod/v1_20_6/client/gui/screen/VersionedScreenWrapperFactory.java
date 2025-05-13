// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.client.gui.screen;

import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.ScreenWrapper;

@Singleton
@Implements(ScreenWrapper.Factory.class)
public class VersionedScreenWrapperFactory implements ScreenWrapper.Factory
{
    @Override
    public ScreenWrapper create(final Object vanillaScreen) {
        if (vanillaScreen instanceof final fnf screen) {
            return new VersionedScreenWrapper(screen);
        }
        throw new IllegalArgumentException(vanillaScreen.getClass().getName() + " is not an instance of " + fnf.class.getName());
    }
}
