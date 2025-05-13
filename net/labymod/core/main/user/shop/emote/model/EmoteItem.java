// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.emote.model;

import java.util.Objects;
import net.labymod.api.client.render.model.ModelBuffer;
import net.labymod.api.client.render.model.animation.meta.AnimationTrigger;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import java.util.Collections;
import net.labymod.api.util.CharSequences;
import net.labymod.api.client.resources.AnimatedResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.render.model.Model;
import net.labymod.core.main.user.shop.AnimationContainer;
import net.labymod.api.client.options.Perspective;
import net.labymod.core.main.user.shop.item.model.AttachmentPoint;
import net.labymod.api.client.render.model.animation.TransformationType;
import net.labymod.core.main.user.shop.emote.abort.AbortAction;
import java.util.Collection;
import net.labymod.core.main.user.shop.item.texture.Ratio;
import net.labymod.core.main.user.shop.Purchasable;

public class EmoteItem implements Purchasable
{
    private final int id;
    private final String name;
    private final Ratio textureRatio;
    private final long textureAnimationDelay;
    private final Collection<AbortAction> abortActions;
    private final Collection<AbortAction> ignoredAbortActions;
    private final Collection<TransformationType> disabledSuspensions;
    private final AttachmentPoint attachedTo;
    private final boolean draft;
    private final boolean props;
    private final boolean playerModel;
    private final Perspective perspective;
    private transient CharSequence lowercaseName;
    private transient AnimationContainer animationContainer;
    private transient Model propsModel;
    private transient ResourceLocation propsTextureLocation;
    private transient AnimatedResourceLocation animatedPropsTextureLocation;
    private transient Model steveModel;
    private transient Model alexModel;
    
    public EmoteItem(final int id, final String name, final Ratio textureRatio, final long textureAnimationDelay, final Collection<AbortAction> abortActions, final Collection<AbortAction> ignoredAbortActions, final Collection<TransformationType> disabledSuspensions, final AttachmentPoint attachedTo, final boolean draft, final boolean props, final boolean playerModel, final Perspective perspective) {
        this.id = id;
        this.name = name;
        this.textureRatio = textureRatio;
        this.textureAnimationDelay = textureAnimationDelay;
        this.abortActions = abortActions;
        this.ignoredAbortActions = ignoredAbortActions;
        this.disabledSuspensions = disabledSuspensions;
        this.attachedTo = attachedTo;
        this.draft = draft;
        this.props = props;
        this.playerModel = playerModel;
        this.perspective = perspective;
        this.lowercaseName = CharSequences.toLowerCase(name);
    }
    
    public int getId() {
        return this.id;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public Ratio getTextureRatio() {
        return this.textureRatio;
    }
    
    public long getTextureAnimationDelay() {
        return (this.textureAnimationDelay == 0L) ? 50L : this.textureAnimationDelay;
    }
    
    public CharSequence getLowercaseName() {
        if (this.lowercaseName == null) {
            this.lowercaseName = CharSequences.toLowerCase(this.getName());
        }
        return this.lowercaseName;
    }
    
    public Collection<AbortAction> getAbortActions() {
        return (Collection<AbortAction>)((this.abortActions == null) ? Collections.emptyList() : this.abortActions);
    }
    
    public Collection<AbortAction> getIgnoredAbortActions() {
        return (Collection<AbortAction>)((this.ignoredAbortActions == null) ? Collections.emptyList() : this.ignoredAbortActions);
    }
    
    public Collection<TransformationType> getDisabledSuspensions() {
        return (Collection<TransformationType>)((this.disabledSuspensions == null) ? Collections.emptyList() : this.disabledSuspensions);
    }
    
    public AttachmentPoint getAttachedTo() {
        return (this.attachedTo == null) ? AttachmentPoint.NONE : this.attachedTo;
    }
    
    public boolean isDraft() {
        return this.draft;
    }
    
    public boolean hasProps() {
        return this.props;
    }
    
    public boolean hasPlayerModel() {
        return this.playerModel;
    }
    
    public Perspective getPerspective() {
        return this.perspective;
    }
    
    public AnimationContainer animationContainer() {
        return this.animationContainer;
    }
    
    public void setAnimationContainer(final AnimationContainer animationContainer) {
        this.animationContainer = animationContainer;
    }
    
    public ModelAnimation getStartAnimation() {
        return this.animationContainer.getByTrigger(AnimationTrigger.NONE);
    }
    
    public Model getPropsModel() {
        return this.propsModel;
    }
    
    public void setPropsModel(final Model propsModel) {
        this.propsModel = propsModel;
    }
    
    public ResourceLocation getPropsTextureLocation() {
        return this.propsTextureLocation;
    }
    
    public void setPropsTextureLocation(final ResourceLocation propsTextureLocation) {
        this.propsTextureLocation = propsTextureLocation;
    }
    
    public AnimatedResourceLocation getAnimatedPropsTextureLocation() {
        return this.animatedPropsTextureLocation;
    }
    
    public void setAnimatedPropsTextureLocation(final AnimatedResourceLocation animatedPropsTextureLocation) {
        this.animatedPropsTextureLocation = animatedPropsTextureLocation;
    }
    
    public Model getSteveModel() {
        return this.steveModel;
    }
    
    public void setSteveModel(final Model steveModelJson) {
        this.steveModel = steveModelJson;
    }
    
    public Model getAlexModel() {
        return this.alexModel;
    }
    
    public void setAlexModel(final Model alexModel) {
        this.alexModel = alexModel;
    }
    
    public void updateAnimatedTexture(final Model propsModel, final ModelBuffer propsModelBuffer) {
        if (this.animatedPropsTextureLocation != null) {
            final ResourceLocation previousLocation = propsModel.getTextureLocation();
            this.animatedPropsTextureLocation.update();
            final ResourceLocation currentLocation = this.animatedPropsTextureLocation.getCurrentResourceLocation();
            if (!Objects.equals(previousLocation, currentLocation)) {
                propsModel.setTextureLocation(currentLocation);
                propsModelBuffer.rebuildModel();
            }
        }
    }
    
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        final EmoteItem emote = (EmoteItem)object;
        return this.id == emote.id;
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }
}
