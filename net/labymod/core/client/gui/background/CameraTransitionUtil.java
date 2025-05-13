// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.background;

import net.labymod.core.client.gui.background.position.PositionTransition;
import net.labymod.core.client.gui.background.position.ResourceLocationPositionTransition;
import net.labymod.core.client.gui.background.position.ScreenPositionRegistry;
import net.labymod.core.util.camera.spline.position.Location;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.resources.ResourceLocation;

public final class CameraTransitionUtil
{
    private static final ResourceLocation DYNAMIC_TRANSITION;
    
    public static void execute(final ResourceLocation identifier) {
        final DynamicBackgroundController controller = LabyMod.references().dynamicBackgroundController();
        controller.executeTransition(identifier);
    }
    
    public static void execute(final double x, final double y, final double z, final double yaw, final double pitch, final double roll) {
        final DynamicBackgroundController controller = LabyMod.references().dynamicBackgroundController();
        final Location location = new Location(x, y, z, yaw, pitch, roll);
        controller.executeTransition(new ResourceLocationPositionTransition(location, ScreenPositionRegistry.DEFAULT_SCREEN_SWITCH_CURVE, 500L, CameraTransitionUtil.DYNAMIC_TRANSITION));
    }
    
    static {
        DYNAMIC_TRANSITION = ResourceLocation.create("labymod", "dynamic_transition");
    }
}
