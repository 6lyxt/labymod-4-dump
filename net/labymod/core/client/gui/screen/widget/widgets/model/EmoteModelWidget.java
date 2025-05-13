// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.model;

import net.labymod.api.client.render.model.DefaultModelBuffer;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.model.animation.meta.AnimationTrigger;
import net.labymod.core.main.user.shop.emote.animation.EmoteAnimationMeta;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.ModelBuffer;
import net.labymod.api.client.render.model.Model;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;

@AutoWidget
public class EmoteModelWidget extends CosmeticModelWidget
{
    private static final float PROPS_SHIFT = -1.5f;
    protected EmoteItem emoteItem;
    protected boolean cosmetics;
    protected Model propsModel;
    protected ModelBuffer propsModelBuffer;
    private boolean forceProjectionMatrix;
    private Model originalModel;
    
    public EmoteModelWidget(final Model model, final AnimationController animationController, final float modelWidth, final float modelHeight, final EmoteItem emoteItem, final boolean cosmetics) {
        super(model, animationController, modelWidth, modelHeight);
        this.forceProjectionMatrix = true;
        this.cosmetics = cosmetics;
        if (emoteItem != null) {
            this.setEmote(emoteItem);
        }
    }
    
    @Override
    public void renderModel(final Stack stack, final int color, final float tickDelta) {
        if (this.emoteItem != null && (!this.animationController.isPlaying() || !this.animationController.getPlaying().getMetaDefault(EmoteAnimationMeta.START_ANIMATION, false))) {
            this.emoteItem.animationContainer().handleAnimationTrigger(AnimationTrigger.IDLE, this.animationController, null);
        }
        super.renderModel(stack, color, tickDelta);
    }
    
    @Override
    protected void renderModelAttachments(final Stack stack, final int packedLight, final float tickDelta) {
        if (this.cosmetics) {
            super.renderModelAttachments(stack, packedLight, tickDelta);
        }
        if (this.emoteItem == null || this.propsModel == null || this.propsModelBuffer == null || !super.animationController.isPlaying()) {
            return;
        }
        super.animationController.applyAnimation(this.propsModel, new String[0]);
        this.emoteItem.updateAnimatedTexture(this.propsModel, this.propsModelBuffer);
        stack.push();
        stack.translate(0.0f, -1.5f, 0.0f);
        this.propsModelBuffer.setForceProjectionMatrix(this.isForceProjectionMatrix());
        this.propsModelBuffer.render(stack);
        stack.pop();
    }
    
    @Override
    public void setModel(final Model model) {
        if (this.emoteItem != null && this.emoteItem.getAlexModel() != null && this.emoteItem.getSteveModel() != null) {
            final boolean slim = model.metadata().get("variant", MinecraftServices.SkinVariant.CLASSIC) == MinecraftServices.SkinVariant.SLIM;
            Model emoteModel = slim ? this.emoteItem.getAlexModel() : this.emoteItem.getSteveModel();
            if (emoteModel != null) {
                this.originalModel = model;
                emoteModel = emoteModel.copy();
                emoteModel.setTextureLocation(model.getTextureLocation());
                super.setModel(emoteModel);
                return;
            }
        }
        super.setModel(model);
    }
    
    @Override
    public void rebuildModel() {
        super.rebuildModel();
        if (this.propsModelBuffer != null) {
            this.propsModelBuffer.rebuildModel();
        }
    }
    
    @Override
    public void dispose() {
        super.dispose();
        if (this.propsModelBuffer != null) {
            this.propsModelBuffer.dispose();
        }
    }
    
    public void playEmote(final EmoteItem emoteItem) {
        if (this.animationController.isPlaying()) {
            this.animationController.stop();
            this.model.reset();
        }
        this.setEmote(emoteItem);
        if (emoteItem != null) {
            this.animationController.playNext(emoteItem.getStartAnimation());
        }
    }
    
    public void stopEmote() {
        this.playEmote(null);
    }
    
    protected void setEmote(final EmoteItem emoteItem) {
        this.emoteItem = emoteItem;
        this.propsModel = null;
        if (this.propsModelBuffer != null) {
            this.propsModelBuffer.dispose();
            this.propsModelBuffer = null;
        }
        if (emoteItem != null && emoteItem.getPropsModel() != null) {
            this.propsModel = emoteItem.getPropsModel().copy();
            if (this.propsModel != null) {
                this.propsModel.setTextureLocation(emoteItem.getPropsTextureLocation());
                this.propsModelBuffer = new DefaultModelBuffer(this.propsModel);
            }
        }
        this.setModel((this.originalModel != null) ? this.originalModel : this.model);
    }
    
    public boolean isForceProjectionMatrix() {
        return this.forceProjectionMatrix;
    }
    
    public void setForceProjectionMatrix(final boolean forceProjectionMatrix) {
        this.forceProjectionMatrix = forceProjectionMatrix;
    }
}
