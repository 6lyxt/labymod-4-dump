// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render.item;

import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.Laby;
import org.jetbrains.annotations.ApiStatus;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Event;

public class RenderFirstPersonItemInHandEvent implements Event
{
    private final Stack stack;
    private final Player player;
    private final PlayerModel playerModel;
    private final LivingEntity.Hand hand;
    private final LivingEntity.HandSide side;
    private final ItemStack itemStack;
    private final AnimationType animationType;
    private final float partialTicks;
    private float equipProgress;
    private float attackProgress;
    private TransformPhase phase;
    private boolean renderItem;
    private boolean applyItemArmTransform;
    private boolean applyItemArmAttackTransform;
    private boolean attackWhileItemUse;
    private boolean isUsingItem;
    
    public RenderFirstPersonItemInHandEvent(final Stack stack, final Player player, final PlayerModel playerModel, final LivingEntity.Hand hand, final LivingEntity.HandSide side, final ItemStack itemStack, final AnimationType animationType, final float partialTicks, final float equipProgress, final float attackProgress, final boolean isUsingItem) {
        this.stack = stack;
        this.player = player;
        this.playerModel = playerModel;
        this.hand = hand;
        this.side = side;
        this.itemStack = itemStack;
        this.animationType = animationType;
        this.partialTicks = partialTicks;
        this.equipProgress = equipProgress;
        this.attackProgress = attackProgress;
        this.renderItem = true;
        this.applyItemArmTransform = true;
        this.applyItemArmAttackTransform = true;
        this.isUsingItem = isUsingItem;
        this.attackWhileItemUse = false;
    }
    
    public Stack stack() {
        return this.stack;
    }
    
    public Player player() {
        return this.player;
    }
    
    public PlayerModel playerModel() {
        return this.playerModel;
    }
    
    public LivingEntity.Hand hand() {
        return this.hand;
    }
    
    public LivingEntity.HandSide side() {
        return this.side;
    }
    
    public ItemStack itemStack() {
        return this.itemStack;
    }
    
    public AnimationType animationType() {
        return this.animationType;
    }
    
    public TransformPhase phase() {
        return this.phase;
    }
    
    @ApiStatus.Internal
    public void setPhase(final TransformPhase phase) {
        this.phase = phase;
    }
    
    public float getAttackProgress() {
        return this.attackProgress;
    }
    
    public void setAttackProgress(final float attackProgress) {
        this.attackProgress = attackProgress;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
    
    public float getEquipProgress() {
        return this.equipProgress;
    }
    
    public void setEquipProgress(final float equipProgress) {
        this.equipProgress = equipProgress;
    }
    
    public boolean isRenderItem() {
        return this.renderItem;
    }
    
    public void setRenderItem(final boolean renderItem) {
        this.renderItem = renderItem;
    }
    
    public boolean isUsingItem() {
        return this.isUsingItem;
    }
    
    public void setUsingItem(final boolean usingItem) {
        this.isUsingItem = usingItem;
    }
    
    public boolean isApplyItemArmTransform() {
        return this.applyItemArmTransform;
    }
    
    public void setApplyItemArmTransform(final boolean applyItemArmTransform) {
        this.applyItemArmTransform = applyItemArmTransform;
    }
    
    public boolean isApplyItemArmAttackTransform() {
        return this.applyItemArmAttackTransform;
    }
    
    public void setApplyItemArmAttackTransform(final boolean applyItemArmAttackTransform) {
        this.applyItemArmAttackTransform = applyItemArmAttackTransform;
    }
    
    public boolean isAttackWhileItemUse() {
        return this.attackWhileItemUse;
    }
    
    public void setAttackWhileItemUse(final boolean attackWhileItemUse) {
        this.attackWhileItemUse = attackWhileItemUse;
    }
    
    public enum AnimationType
    {
        NONE, 
        EAT, 
        DRINK, 
        BLOCK, 
        BOW, 
        SPEAR, 
        CROSSBOW, 
        SPYGLASS, 
        TOOT_HORN, 
        BRUSH, 
        BUNDLE;
        
        public static final AnimationType[] VALUES;
        
        public static AnimationType fromMinecraft(final Object animationType) {
            return Laby.references().renderFirstPersonItemInHandEventAnimationTypeMapper().fromMinecraft(animationType);
        }
        
        static {
            VALUES = values();
        }
    }
    
    public enum TransformPhase
    {
        HEAD, 
        PRE_ARM_TRANSFORM, 
        POST_ARM_TRANSFORM, 
        PRE_EAT_TRANSFORM, 
        POST_EAT_TRANSFORM, 
        PRE_ATTACK_TRANSFORM, 
        POST_ATTACK_TRANSFORM, 
        PRE_RENDER;
    }
    
    @Referenceable
    public interface AnimationTypeMapper
    {
        AnimationType fromMinecraft(final Object p0);
        
        Object toMinecraft(final AnimationType p0);
    }
}
