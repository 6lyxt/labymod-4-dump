// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.cosmetics;

import net.labymod.api.event.client.render.item.PlayerItemRenderContextEvent;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.core.event.client.render.item.PlayerItemRenderContextEventCaller;
import net.labymod.api.Laby;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import net.labymod.api.client.entity.player.Player;
import net.labymod.v1_12_2.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bzw.class })
public class MixinCustomItemRenderer
{
    private vp labyMod$livingEntity;
    private bwc.b labyMod$transformType;
    
    public MixinCustomItemRenderer() {
        this.labyMod$transformType = bwc.b.h;
    }
    
    @Inject(method = { "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;Z)V" }, at = { @At("HEAD") })
    private void labyMod$beginRenderStatic(final aip lvt_1_1_, final vp lvt_2_1_, final bwc.b lvt_3_1_, final boolean lvt_4_1_, final CallbackInfo ci) {
        this.labyMod$livingEntity = lvt_2_1_;
        this.labyMod$transformType = lvt_3_1_;
    }
    
    @Inject(method = { "renderItemAndEffectIntoGUI(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;II)V" }, at = { @At("HEAD") })
    private void labyMod$beginRenderGui(final vp lvt_1_1_, final aip lvt_2_1_, final int lvt_3_1_, final int lvt_4_1_, final CallbackInfo ci) {
        this.labyMod$livingEntity = lvt_1_1_;
        this.labyMod$transformType = bwc.b.g;
    }
    
    @Inject(method = { "renderItemAndEffectIntoGUI(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;II)V" }, at = { @At("TAIL") })
    private void labyMod$endRenderGui(final vp lvt_1_1_, final aip lvt_2_1_, final int lvt_3_1_, final int lvt_4_1_, final CallbackInfo ci) {
        this.labyMod$livingEntity = null;
        this.labyMod$transformType = bwc.b.h;
    }
    
    @Inject(method = { "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;Z)V" }, at = { @At("TAIL") })
    private void labyMod$endRenderStatic(final aip itemStack, final vp livingEntity, final bwc.b transformType, final boolean lvt_4_1_, final CallbackInfo ci) {
        this.labyMod$livingEntity = null;
        this.labyMod$transformType = bwc.b.h;
    }
    
    @Inject(method = { "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/IBakedModel;)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", shift = At.Shift.AFTER) }, cancellable = true)
    private void labyMod$callPlayerItemRenderContext(final aip itemStack, final cfy model, final CallbackInfo ci) {
        if (this.labyMod$transformType == bwc.b.h) {
            return;
        }
        vp livingEntity = this.labyMod$livingEntity;
        if (livingEntity == null) {
            livingEntity = (vp)bib.z().h;
        }
        final ItemStack apiItemStack = MinecraftUtil.fromMinecraft(itemStack);
        if (!itemStack.b() && livingEntity instanceof Player) {
            final Player player = (Player)livingEntity;
            final bzg renderer = bib.z().ac().a((vg)livingEntity);
            if (renderer instanceof final cct playerRenderer) {
                final PlayerItemRenderContextEvent event = PlayerItemRenderContextEventCaller.call(VersionedStackProvider.DEFAULT_STACK, apiItemStack, MinecraftUtil.fromMinecraft(this.labyMod$transformType), player, (PlayerModel)playerRenderer.h(), Laby.references().renderEnvironmentContext().getPackedLight());
                if (event.isCancelled()) {
                    bus.H();
                    ci.cancel();
                }
            }
        }
    }
}
