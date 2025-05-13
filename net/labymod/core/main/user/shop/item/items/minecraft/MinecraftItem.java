// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items.minecraft;

import net.labymod.core.main.user.shop.item.positionprovider.ShieldItemPositionProvider;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.world.item.VanillaItem;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.core.main.user.shop.item.geometry.effect.effects.metadata.MinecraftItemGeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;
import net.labymod.core.main.user.shop.item.positionprovider.MinecraftItemPositionProvider;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.core.main.user.shop.item.geometry.AnimationStorage;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.model.animation.meta.AnimationTrigger;
import net.labymod.api.client.options.MainHand;
import net.labymod.core.main.user.shop.bridge.ItemTags;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.core.main.user.shop.item.ItemDetails;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.main.user.shop.item.AbstractItem;

public class MinecraftItem extends AbstractItem
{
    private static final MinecraftItemRegistry REGISTRY;
    private static final MinecraftItemGameDetails DETAILS;
    private ResourceLocation itemIdentifier;
    private boolean available;
    
    public MinecraftItem(final int listId, final ItemDetails itemDetails) {
        super(listId, itemDetails);
        this.available = false;
    }
    
    @Override
    protected void render(final Stack stack, final int packedLight, final int packedOverlay, final float partialTicks) {
        final AnimationStorage storage = this.getAnimationStorage(this.user);
        if (storage == null) {
            return;
        }
        final AnimationController controller = storage.getController();
        final boolean isActivity = this.hasTag(ItemTags.ACTIVITY);
        if (isActivity) {
            this.hand = MainHand.RIGHT;
        }
        final boolean leftSide = this.hand == MainHand.LEFT;
        this.setPosition(leftSide ? Position.LEFT : Position.RIGHT);
        final float offset = leftSide ? 1.0f : -1.0f;
        if (this.player.getItemUseDurationTicks() > 0) {
            this.animationContainer.handleAnimationTrigger(AnimationTrigger.BLOCKING, controller, this.player);
        }
        else {
            this.animationContainer.handleAnimationTrigger(AnimationTrigger.IDLE, controller, this.player);
        }
        if (MinecraftItem.DETAILS.blockedDamage(this.player) && this.isShield()) {
            this.animationContainer.handleAnimationTrigger(AnimationTrigger.BLOCK_ATTACK, controller, this.player);
        }
        stack.push();
        if (!this.available || isActivity) {
            final MinecraftItemPositionProvider provider = MinecraftItem.REGISTRY.findProvider(this.itemIdentifier);
            provider.apply(stack, this.playerModel);
        }
        if (!this.firstPerson) {
            stack.translate(0.0f, 0.0f, -(PlatformEnvironment.isAncientOpenGL() ? 2.2f : 1.25f) * 0.0625f);
        }
        stack.rotate(offset * 180.0f, 0.0f, 1.0f, 0.0f);
        controller.applyAnimation(this.model, new String[0]);
        this.renderModel(stack, packedLight, packedOverlay);
        stack.pop();
    }
    
    @Override
    public AbstractItem copy() {
        return new MinecraftItem(this.getListId(), this.itemDetails);
    }
    
    @Override
    protected void onModelLoad() {
        final GeometryEffect effect = this.itemEffect.findEffect(GeometryEffect.Type.METADATA, geometryEffect -> geometryEffect instanceof MinecraftItemGeometryEffect);
        if (effect instanceof final MinecraftItemGeometryEffect minecraftItemGeometryEffect) {
            this.itemIdentifier = minecraftItemGeometryEffect.getItemIdentifier();
            final VanillaItem item = VanillaItems.findItem(this.itemIdentifier);
            if (item != null) {
                this.available = item.isAvailable();
            }
        }
    }
    
    private boolean isShield() {
        return this.itemIdentifier != null && this.itemIdentifier.equals(VanillaItems.SHIELD.identifier());
    }
    
    @Nullable
    public ResourceLocation getItemIdentifier() {
        return this.itemIdentifier;
    }
    
    public boolean resolved() {
        return this.model != null && this.itemIdentifier != null;
    }
    
    public boolean isAvailable() {
        return this.available;
    }
    
    static {
        REGISTRY = MinecraftItemRegistry.construct(registry -> registry.register(VanillaItems.SHIELD, new ShieldItemPositionProvider()));
        DETAILS = new MinecraftItemGameDetails();
    }
}
