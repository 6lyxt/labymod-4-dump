// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.bridge;

import net.labymod.core.main.user.shop.item.AbstractItem;
import net.labymod.api.util.math.position.Position;
import java.util.Iterator;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.core.main.user.shop.item.items.pet.WalkingPet;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.client.options.Perspective;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.event.client.render.world.RenderWorldEvent;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import net.labymod.api.client.options.MainHand;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.render.model.ModelTransformType;
import net.labymod.api.event.client.render.item.PlayerItemRenderContextEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.user.shop.RenderMode;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.model.entity.player.PlayerModelRenderHandEvent;
import net.labymod.core.main.LabyMod;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.LabyAPI;

public class CustomItemRenderer
{
    private final LabyAPI labyAPI;
    private final ShopItemLayer shopItemLayer;
    private final GFXRenderPipeline renderPipeline;
    
    public CustomItemRenderer() {
        this.labyAPI = Laby.labyAPI();
        this.shopItemLayer = LabyMod.references().shopItemLayer();
        this.renderPipeline = Laby.references().gfxRenderPipeline();
    }
    
    @Subscribe
    public void handleModelHandRender(final PlayerModelRenderHandEvent event) {
        if (!this.isRenderCosmetics() || event.phase() != Phase.POST) {
            return;
        }
        if (MinecraftVersions.V24w33a.orNewer() && this.labyAPI.config().ingame().cosmetics().renderMode().getOrDefault(RenderMode.IMMEDIATE) == RenderMode.RETAINED) {
            Laby.gfx().blaze3DBufferSource().endBuffer();
        }
        this.shopItemLayer.applyTag(ItemTags.FIRST_PERSON).filter(ItemFilters.noWalkingPets()).hand(event.hand()).render(event.stack(), event.player(), event.model(), event.getPackedLight(), this.labyAPI.minecraft().getPartialTicks());
    }
    
    @Subscribe
    public void onPlayerItemRenderContext(final PlayerItemRenderContextEvent event) {
        if (!this.isRenderCosmetics()) {
            return;
        }
        final ModelTransformType transformType = event.transformType();
        if (transformType == ModelTransformType.GROUND) {
            return;
        }
        final ItemStack itemStack = event.itemStack();
        final boolean firstPerson = transformType.isFirstPerson();
        if (firstPerson) {
            this.shopItemLayer.applyTag(ItemTags.FIRST_PERSON);
        }
        final MainHand hand = this.findHand(firstPerson ? (transformType == ModelTransformType.FIRST_PERSON_LEFT_HAND) : (transformType == ModelTransformType.THIRD_PERSON_LEFT_HAND));
        this.shopItemLayer.filter(item -> ItemFilters.filterItem(item, itemStack)).hand(hand).render(event.stack(), event.player(), event.playerModel(), event.getPackedLight(), event.getPartialTicks());
        event.setCancelled(this.shopItemLayer.isRenderedCustomMinecraftItem());
    }
    
    @Subscribe
    public void onRenderItem(final RenderFirstPersonItemInHandEvent event) {
        if (event.phase() != RenderFirstPersonItemInHandEvent.TransformPhase.PRE_RENDER) {
            return;
        }
        if (!event.isRenderItem()) {
            return;
        }
        if (!this.isRenderCosmetics()) {
            return;
        }
        final ItemStack itemStack = event.itemStack();
        final ClientPlayer clientPlayer = Laby.labyAPI().minecraft().getClientPlayer();
        if (clientPlayer == null) {
            return;
        }
        this.shopItemLayer.filter(item -> ItemFilters.filterItem(item, itemStack)).hand((event.side() == LivingEntity.HandSide.LEFT) ? MainHand.LEFT : MainHand.RIGHT).render(event.stack(), event.player(), event.playerModel(), (this.renderPipeline == null) ? 0 : this.renderPipeline.renderEnvironmentContext().getPackedLight(), event.getPartialTicks());
        event.setRenderItem(!this.shopItemLayer.isRenderedCustomMinecraftItem());
    }
    
    @Subscribe
    public void onRenderWorld(final RenderWorldEvent event) {
        if (!this.isRenderCosmetics()) {
            return;
        }
        final boolean walkingPetsVisible = Laby.labyAPI().config().ingame().cosmetics().walkingPets().get();
        if (!walkingPetsVisible) {
            return;
        }
        this.renderPipeline.setProjectionMatrix();
        final FloatMatrix4 prevModelViewMatrix = this.renderPipeline.getModelViewMatrix();
        this.renderPipeline.setModelViewMatrix(FloatMatrix4.newIdentity());
        final ClientWorld level = Laby.labyAPI().minecraft().clientWorld();
        final Stack stack = event.stack();
        stack.push();
        for (final Player player : level.getPlayers()) {
            if (player.isInvisible()) {
                continue;
            }
            final ShopItemLayer layer = this.shopItemLayer;
            if (this.labyAPI.minecraft().options().perspective() == Perspective.FIRST_PERSON) {
                layer.applyTag(ItemTags.FIRST_PERSON);
            }
            if (PlatformEnvironment.isAncientOpenGL() && player.isCrouching() && !layer.hasTag(ItemTags.FIRST_PERSON)) {
                stack.translate(0.0f, -0.2f, 0.0f);
            }
            layer.applyTag(ItemTags.WORLD);
            final Position position = player.position();
            layer.filter(item -> !(item instanceof WalkingPet)).render(stack, player, player.playerModel(), level.getPackedLight(position.getX(), position.getY(), position.getZ()), event.getPartialTicks());
        }
        stack.pop();
        this.renderPipeline.setModelViewMatrix(prevModelViewMatrix);
    }
    
    private boolean isRenderCosmetics() {
        return this.labyAPI.config().ingame().cosmetics().renderCosmetics().get();
    }
    
    private MainHand findHand(final boolean leftSide) {
        return leftSide ? MainHand.LEFT : MainHand.RIGHT;
    }
}
