// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.animation.old.animations;

import net.labymod.api.event.Subscribe;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import net.labymod.api.Laby;
import net.labymod.api.user.permission.PermissionRegistry;
import net.labymod.core.main.animation.old.AbstractOldAnimation;

public class FoodOldAnimation extends AbstractOldAnimation
{
    public static final String NAME = "food";
    private final PermissionRegistry permissionRegistry;
    
    public FoodOldAnimation() {
        super("food");
        this.permissionRegistry = Laby.references().permissionRegistry();
    }
    
    @Subscribe
    public void onRenderItemInHand(final RenderFirstPersonItemInHandEvent event) {
        if (event.phase() != RenderFirstPersonItemInHandEvent.TransformPhase.HEAD || !this.isEnabled()) {
            return;
        }
        final RenderFirstPersonItemInHandEvent.AnimationType type = event.animationType();
        if (type != RenderFirstPersonItemInHandEvent.AnimationType.DRINK && type != RenderFirstPersonItemInHandEvent.AnimationType.EAT) {
            return;
        }
        final Stack stack = event.stack();
        if (event.isUsingItem()) {
            stack.rotate(0.97f, 1.0f, 0.0f, 0.0f);
            stack.rotate(3.39f, 0.0f, 1.0f, 0.0f);
            stack.rotate(2.51f, 0.0f, 0.0f, 1.0f);
            stack.translate(0.085625f, -0.075625f, 0.005f);
        }
        else {
            stack.rotate(0.48f, 1.0f, 0.0f, 0.0f);
            stack.rotate(7.27f, 0.0f, 1.0f, 0.0f);
            stack.translate(0.090625f, -0.02f, 0.050625f);
            if (type == RenderFirstPersonItemInHandEvent.AnimationType.DRINK && MinecraftVersions.V1_16_5.orNewer()) {
                stack.translate(0.03f, 0.0f, 0.0f);
            }
        }
    }
    
    @Override
    public boolean isEnabled() {
        return this.permissionRegistry.isPermissionEnabled("animations", this.classicPvPConfig.oldFood());
    }
}
