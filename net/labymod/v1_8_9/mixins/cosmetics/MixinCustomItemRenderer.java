// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.cosmetics;

import net.labymod.api.event.client.render.item.PlayerItemRenderContextEvent;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.core.event.client.render.item.PlayerItemRenderContextEventCaller;
import net.labymod.api.Laby;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import net.labymod.api.client.entity.player.Player;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bjh.class })
public class MixinCustomItemRenderer
{
    private pr labyMod$livingEntity;
    private bgr.b labyMod$transformType;
    
    public MixinCustomItemRenderer() {
        this.labyMod$transformType = bgr.b.f;
    }
    
    @Inject(method = { "renderItemModelForEntity" }, at = { @At("HEAD") })
    private void labyMod$beginRenderStatic(final zx lvt_1_1_, final pr lvt_2_1_, final bgr.b lvt_3_1_, final CallbackInfo ci) {
        this.labyMod$livingEntity = lvt_2_1_;
        this.labyMod$transformType = lvt_3_1_;
    }
    
    @Inject(method = { "renderItemAndEffectIntoGUI" }, at = { @At("HEAD") })
    private void labyMod$beginRenderGui(final zx lvt_1_1_, final int lvt_2_1_, final int lvt_3_1_, final CallbackInfo ci) {
        this.labyMod$livingEntity = (pr)ave.A().h;
        this.labyMod$transformType = bgr.b.e;
    }
    
    @Inject(method = { "renderItemAndEffectIntoGUI" }, at = { @At("TAIL") })
    private void labyMod$endRenderGui(final zx lvt_1_1_, final int lvt_2_1_, final int lvt_3_1_, final CallbackInfo ci) {
        this.labyMod$livingEntity = null;
        this.labyMod$transformType = bgr.b.f;
    }
    
    @Inject(method = { "renderItemModelForEntity" }, at = { @At("TAIL") })
    private void labyMod$endRenderStatic(final zx lvt_1_1_, final pr lvt_2_1_, final bgr.b lvt_3_1_, final CallbackInfo ci) {
        this.labyMod$livingEntity = null;
        this.labyMod$transformType = bgr.b.f;
    }
    
    @Inject(method = { "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", shift = At.Shift.AFTER) }, cancellable = true)
    private void labyMod$callPlayerItemRenderContext(final zx itemStack, final boq model, final CallbackInfo ci) {
        if (this.labyMod$transformType == bgr.b.f) {
            return;
        }
        pr livingEntity = this.labyMod$livingEntity;
        if (livingEntity == null) {
            livingEntity = (pr)ave.A().h;
        }
        final ItemStack apiItemStack = MinecraftUtil.fromMinecraft(itemStack);
        if (itemStack != null && livingEntity instanceof Player) {
            final Player player = (Player)livingEntity;
            final biv renderer = ave.A().af().a((pk)livingEntity);
            if (renderer instanceof final bln playerRenderer) {
                final PlayerItemRenderContextEvent event = PlayerItemRenderContextEventCaller.call(VersionedStackProvider.DEFAULT_STACK, apiItemStack, MinecraftUtil.fromMinecraft(this.labyMod$transformType), player, (PlayerModel)playerRenderer.g(), Laby.references().renderEnvironmentContext().getPackedLight());
                if (event.isCancelled()) {
                    bfl.F();
                    ci.cancel();
                }
            }
        }
    }
}
