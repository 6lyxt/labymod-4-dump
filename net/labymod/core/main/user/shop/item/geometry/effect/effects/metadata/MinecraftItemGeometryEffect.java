// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry.effect.effects.metadata;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.user.GameUser;
import net.labymod.core.main.user.shop.item.geometry.effect.ItemEffect;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.core.main.user.shop.item.AbstractItem;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;

public class MinecraftItemGeometryEffect extends GeometryEffect
{
    private ResourceLocation itemIdentifier;
    private boolean shield;
    
    public MinecraftItemGeometryEffect(final String effectArgument, final ModelPart modelPart) {
        super(effectArgument, modelPart, Type.METADATA, 2);
    }
    
    @Override
    protected boolean processParameters() {
        if (!this.hasParameter(0)) {
            return false;
        }
        final String[] arguments = this.getArguments();
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < arguments.length; ++i) {
            stringBuilder.append(arguments[i]);
            if (i < arguments.length - 1) {
                stringBuilder.append("_");
            }
        }
        if (stringBuilder.isEmpty()) {
            return false;
        }
        this.itemIdentifier = ResourceLocation.create("minecraft", stringBuilder.toString());
        if (VanillaItems.SHIELD.identifier().equals(this.itemIdentifier)) {
            this.shield = true;
        }
        return true;
    }
    
    @Override
    public void apply(final AbstractItem item, final Player player, final PlayerModel playerModel, final ItemMetadata itemMetadata, final ItemEffect.EffectData effectData) {
        if (this.shield) {
            player.gameUser().setTag(GameUser.HIDE_SHIELD);
        }
    }
    
    @Nullable
    public ResourceLocation getItemIdentifier() {
        return this.itemIdentifier;
    }
}
