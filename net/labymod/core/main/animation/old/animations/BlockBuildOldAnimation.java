// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.animation.old.animations;

import net.labymod.api.client.options.MinecraftInputMapping;
import net.labymod.api.client.world.phys.hit.HitResult;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.world.phys.hit.BlockHitResult;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.client.input.MouseButtonEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.user.permission.PermissionRegistry;
import net.labymod.api.client.particle.ParticleController;
import net.labymod.api.client.world.phys.hit.HitResultController;
import net.labymod.core.main.animation.old.AbstractOldAnimation;

public class BlockBuildOldAnimation extends AbstractOldAnimation
{
    public static final String NAME = "block_build";
    private final HitResultController hitResultController;
    private final ParticleController particleController;
    private final PermissionRegistry permissionRegistry;
    private final Minecraft minecraft;
    private boolean interruptedAttack;
    private boolean interruptedItemUse;
    
    public BlockBuildOldAnimation() {
        super("block_build");
        this.interruptedAttack = false;
        this.interruptedItemUse = false;
        this.hitResultController = Laby.references().hitResultController();
        this.particleController = Laby.references().particleController();
        this.permissionRegistry = Laby.references().permissionRegistry();
        this.minecraft = Laby.labyAPI().minecraft();
    }
    
    @Subscribe
    public void onRenderFirstPersonItemInHand(final RenderFirstPersonItemInHandEvent event) {
        if (!this.isEnabled() || event.phase() != RenderFirstPersonItemInHandEvent.TransformPhase.HEAD) {
            return;
        }
        if (event.itemStack().isSword()) {
            event.setAttackWhileItemUse(true);
            final MinecraftOptions options = this.minecraft.options();
            event.setUsingItem(options.useItemInput().isDown() && !this.minecraft.isLastBlockUsed());
        }
        if (event.isUsingItem()) {
            event.setAttackWhileItemUse(true);
        }
    }
    
    @Subscribe
    public void onMouseButton(final MouseButtonEvent event) {
        if (!this.isEnabled()) {
            return;
        }
        this.updateInputs();
    }
    
    @Subscribe(126)
    public void onTick(final GameTickEvent event) {
        if (!this.isEnabled() || event.phase() != Phase.PRE) {
            return;
        }
        this.updateInputs();
        final ClientPlayer clientPlayer = this.minecraft.getClientPlayer();
        if (clientPlayer != null && !this.minecraft.isDestroying() && (this.interruptedAttack || this.interruptedItemUse) && this.minecraft.isMouseLocked() && this.hitResultController.isCrosshairOverBlock()) {
            final HitResult result = this.hitResultController.getResult();
            if (result instanceof final BlockHitResult blockHitResult) {
                this.particleController.crackBlock(blockHitResult.getBlockPosition(), blockHitResult.getBlockDirection());
            }
            clientPlayer.swingArm(LivingEntity.Hand.MAIN_HAND, true);
        }
    }
    
    private void updateInputs() {
        final MinecraftOptions minecraftOptions = this.minecraft.options();
        final ClientPlayer clientPlayer = this.minecraft.getClientPlayer();
        final MinecraftInputMapping attackInput = minecraftOptions.attackInput();
        final MinecraftInputMapping useItemInput = minecraftOptions.useItemInput();
        final boolean attackActuallyDown = attackInput.isActuallyDown();
        final boolean useItemActuallyDown = useItemInput.isActuallyDown();
        if (!attackActuallyDown) {
            this.interruptedAttack = false;
        }
        if (!useItemActuallyDown) {
            this.interruptedItemUse = false;
        }
        if (clientPlayer == null || !useItemActuallyDown || !attackActuallyDown || !this.minecraft.isMouseLocked() || !this.hitResultController.isCrosshairOverBlock() || !clientPlayer.canDestroyBlocks()) {
            return;
        }
        if (this.minecraft.isDestroying()) {
            attackInput.unpress();
            this.continueAttack();
            this.interruptedAttack = true;
        }
        else if (!this.interruptedAttack && !attackInput.isDown()) {
            attackInput.press();
            this.continueAttack();
            this.interruptedAttack = true;
        }
    }
    
    private void continueAttack() {
        final boolean leftClick = this.minecraft.minecraftWindow().currentScreen() == null && this.minecraft.options().attackInput().isDown() && this.minecraft.isMouseLocked();
        this.minecraft.updateBlockBreak(leftClick);
    }
    
    @Override
    public boolean isEnabled() {
        return this.permissionRegistry.isPermissionEnabled("blockbuild", this.classicPvPConfig.oldBlockBuild());
    }
}
