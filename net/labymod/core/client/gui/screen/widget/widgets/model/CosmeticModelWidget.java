// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.model;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.entity.player.PlayerClothes;
import net.labymod.api.user.shop.CloakPriority;
import net.labymod.api.client.render.model.DefaultModelBuffer;
import java.util.function.Consumer;
import net.labymod.api.Constants;
import net.labymod.api.client.render.model.ModelBuffer;
import net.labymod.api.LabyAPI;
import net.labymod.api.util.Disposable;
import java.util.UUID;
import net.labymod.api.user.GameUser;
import net.labymod.api.util.math.Transformation;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.Laby;
import net.labymod.core.main.user.shop.bridge.ItemFilters;
import net.labymod.core.main.user.shop.bridge.ItemTags;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.core.client.entity.player.DummyPlayerModel;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.core.main.user.shop.bridge.ShopItemLayer;
import net.labymod.core.client.entity.player.DummyPlayer;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ModelWidget;

@AutoWidget
public class CosmeticModelWidget extends ModelWidget
{
    private final DummyPlayer player;
    private final ShopItemLayer layer;
    private final VanillaLayer vanillaLayer;
    private PlayerModel playerModel;
    
    public CosmeticModelWidget(final Model model, final AnimationController animationController, final float modelWidth, final float modelHeight) {
        super(model, animationController, modelWidth, modelHeight);
        super.pivotPoint.set(0.0f, -17.5f, 0.0f);
        this.player = new DummyPlayer();
        this.layer = LabyMod.references().shopItemLayer();
        this.vanillaLayer = new VanillaLayer(this.labyAPI, false);
        this.playerModel = new DummyPlayerModel(model);
    }
    
    @Override
    protected void renderModelAttachments(final Stack stack, final int packedLight, final float tickDelta) {
        final ModelPart body = this.playerModel.getPart("body");
        if (body == null) {
            return;
        }
        stack.push();
        final Transformation modelPartTransformation = body.getModelPartTransform();
        modelPartTransformation.transform(stack, body.getAnimationTransformation());
        stack.translate(0.0f, -0.390625f, 0.0f);
        this.vanillaLayer.render(stack, this.player);
        this.layer.applyTag(ItemTags.ACTIVITY).filter(ItemFilters.noFilter()).render(stack, this.player, this.playerModel, packedLight, Laby.labyAPI().minecraft().getPartialTicks());
        stack.pop();
    }
    
    public GameUser gameUser() {
        return this.player.gameUser();
    }
    
    public void setPlayerUniqueId(final UUID uniqueId) {
        final UUID prevUniqueId = this.player.getUniqueId();
        if (prevUniqueId.equals(uniqueId)) {
            return;
        }
        this.player.setUniqueId(uniqueId);
        this.rebuildModel();
    }
    
    @Override
    public void dispose() {
        super.dispose();
        if (this.vanillaLayer != null) {
            this.vanillaLayer.dispose();
        }
    }
    
    @Override
    public void setModel(final Model model) {
        super.setModel(model);
        this.playerModel = new DummyPlayerModel(model);
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public PlayerModel playerModel() {
        return this.playerModel;
    }
    
    static class VanillaLayer implements Disposable
    {
        private final LabyAPI labyAPI;
        private final ModelBuffer modelBuffer;
        private final Model capeModel;
        private final boolean showElytra;
        
        public VanillaLayer(final LabyAPI labyAPI, final boolean showElytra) {
            this.labyAPI = labyAPI;
            this.showElytra = showElytra;
            this.capeModel = labyAPI.renderPipeline().modelService().loadBlockBenchModel(Constants.Resources.CLOAK_AND_ELYTRA);
            if (showElytra) {
                this.capeModel.findPart("cloak").ifPresent(new Consumer<ModelPart>(this) {
                    @Override
                    public void accept(final ModelPart part) {
                        part.setVisible(false);
                    }
                });
            }
            else {
                this.capeModel.findPart("elytra").ifPresent(new Consumer<ModelPart>(this) {
                    @Override
                    public void accept(final ModelPart part) {
                        part.setVisible(false);
                    }
                });
            }
            this.modelBuffer = new DefaultModelBuffer(this.capeModel);
        }
        
        public void render(final Stack stack, final Player player) {
            final ResourceLocation cloakTexture = player.getCloakTexture();
            if (cloakTexture == null) {
                return;
            }
            if (player instanceof final DummyPlayer dummyPlayer) {
                dummyPlayer.setWearingElytra(this.showElytra);
            }
            stack.push();
            stack.translate(0.0f, 1.0f, 0.0f);
            stack.translate(0.0f, 0.0f, 0.1875f);
            final GameUser clientUser = this.labyAPI.gameUserService().clientGameUser();
            final boolean hasCloak = clientUser.hasTag(GameUser.HIDE_CAPE);
            boolean showVanillaCape = !hasCloak;
            if (hasCloak) {
                showVanillaCape = (this.labyAPI.config().ingame().cosmetics().cloakPriority().get() == CloakPriority.VANILLA);
            }
            if (this.showElytra || (showVanillaCape && player.isShownModelPart(PlayerClothes.CAPE))) {
                this.capeModel.setTextureLocation(cloakTexture);
                this.modelBuffer.render(stack);
            }
            if (player instanceof final DummyPlayer dummyPlayer2) {
                dummyPlayer2.setWearingElytra(false);
            }
            stack.pop();
        }
        
        @Override
        public void dispose() {
            this.modelBuffer.dispose();
        }
    }
}
