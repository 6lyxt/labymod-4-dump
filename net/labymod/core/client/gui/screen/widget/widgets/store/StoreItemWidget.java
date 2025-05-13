// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.store;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.api.client.gui.screen.widget.widgets.RatingWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.VariableIconWidget;
import net.labymod.core.flint.FlintController;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.core.flint.index.FlintIndex;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import java.util.List;
import java.util.Collection;
import net.labymod.api.util.CollectionHelper;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.addons.AddonProfileActivity;
import java.util.ArrayList;
import net.labymod.core.flint.downloader.AddonDownloadRequest;
import net.labymod.api.util.I18n;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.core.flint.marketplace.FlintModification;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
public class StoreItemWidget extends SimpleWidget
{
    private final Pressable flintPressable;
    private FlintModification modification;
    private final LssProperty<Integer> installedColor;
    private final LssProperty<Integer> deletedColor;
    private final String loadingString;
    protected final boolean hasUnsupportedDependencies;
    private boolean loadingModification;
    
    public StoreItemWidget(final FlintModification modification, final Pressable pressable) {
        this.installedColor = new LssProperty<Integer>(0);
        this.deletedColor = new LssProperty<Integer>(0);
        this.modification = modification;
        this.flintPressable = pressable;
        this.loadingString = I18n.translate("", new Object[0]);
        this.hasUnsupportedDependencies = hasUnsupportedDependencies(modification);
        this.lazy = true;
    }
    
    public static boolean hasUnsupportedDependencies(final FlintModification modification) {
        if (modification == null || modification.getDependencies().length == 0 || AddonDownloadRequest.UNSUPPORTED_ADDONS.size() == 0) {
            return false;
        }
        final List<FlintModification> dependencies = new ArrayList<FlintModification>();
        AddonProfileActivity.collectUninstalledDependencies(modification, dependencies, false);
        return dependencies.size() != 0 && CollectionHelper.anyMatch(dependencies, dependency -> AddonDownloadRequest.UNSUPPORTED_ADDONS.contains(dependency.getNamespace()));
    }
    
    public static Component getIncompatibleComponent(final FlintModification modification, final boolean compatible) {
        if (!modification.isBuildCompatible()) {
            return Component.translatable("labymod.addons.store.profile.incompatible.description", new Component[0]);
        }
        if (modification.getNamespace().equals("optifine")) {
            return Component.translatable("labymod.addons.store.profile.incompatibleVersion.optifineDescription", Component.text(Laby.labyAPI().labyModLoader().version().toString()));
        }
        if (!compatible) {
            return Component.translatable("labymod.addons.store.profile.incompatibleVersion." + (modification.isOrganizationLabyMod() ? "labyModDescription" : "description"), Component.text(modification.getName()), Component.text(Laby.labyAPI().labyModLoader().version().toString()));
        }
        return Component.translatable("labymod.addons.store.profile.unsupported", new Component[0]);
    }
    
    @Override
    public void initialize(final Parent parent) {
        if (this.modification != null && (!this.modification.isCompatible() || this.hasUnsupportedDependencies)) {
            this.addId("incompatible");
        }
        else {
            this.removeId("incompatible");
        }
        super.initialize(parent);
        this.loadModification();
        this.initializeStoreItem();
    }
    
    @Override
    public void handleAttributes() {
        super.handleAttributes();
        this.setBackgroundColor();
    }
    
    @Override
    protected SoundType getInteractionSound() {
        return SoundType.BUTTON_CLICK;
    }
    
    private void setBackgroundColor() {
        if (this.modification != null && this.modification.isInstalled()) {
            final Integer deletedColor = this.deletedColor.get();
            if (this.modification.isDeleted() && deletedColor != 0) {
                this.backgroundColor().set(deletedColor);
            }
            else {
                final Integer installedColor = this.installedColor.get();
                if (installedColor != 0) {
                    this.backgroundColor().set(installedColor);
                }
            }
        }
    }
    
    public FlintModification modification() {
        return this.modification;
    }
    
    public Pressable flintPressable() {
        return this.flintPressable;
    }
    
    public LssProperty<Integer> installedColor() {
        return this.installedColor;
    }
    
    public LssProperty<Integer> deletedColor() {
        return this.deletedColor;
    }
    
    protected void setModification(final FlintModification modification) {
        this.modification = modification;
    }
    
    protected void loadModification() {
        if (this.loadingModification || this.modification == null) {
            return;
        }
        if (!(this.modification instanceof FlintIndex.FlintIndexModification)) {
            return;
        }
        final FlintModification mod = this.modification.loadModification(result -> {
            if (!result.isPresent() || !(this.modification instanceof FlintIndex.FlintIndexModification)) {
                return;
            }
            else {
                this.modification = (FlintModification)result.get();
                this.loadingModification = false;
                this.labyAPI.minecraft().executeOnRenderThread(this::reInitialize);
                return;
            }
        });
        if (mod != null) {
            this.modification = mod;
        }
        else {
            this.loadingModification = true;
        }
    }
    
    protected boolean isIncompatible() {
        return !this.modification.isBuildCompatible() || !this.modification.isCompatible() || this.hasUnsupportedDependencies;
    }
    
    protected Component getIncompatibleComponent() {
        return getIncompatibleComponent(this.modification, this.modification.isCompatible());
    }
    
    protected void initializeStoreItem() {
        if (this.modification == null) {
            return;
        }
        final FlexibleContentWidget container = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)container).addId("item-container");
        final FlintModification.Image icon = this.modification.getIcon();
        final VariableIconWidget iconWidget = new VariableIconWidget(Textures.DEFAULT_SERVER_ICON, (icon == null) ? null : icon.getIconUrl(), FlintController::getVariableBrandUrl);
        iconWidget.setCleanupOnDispose(true);
        iconWidget.addId("item-icon");
        container.addContent(iconWidget);
        final FlexibleContentWidget textContainer = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)textContainer).addId("item-text-container");
        Component displayName = Component.text(this.modification.getName());
        if (this.modification instanceof FlintIndex.FlintIndexModification) {
            displayName = Component.translatable("labymod.addons.loading", displayName);
        }
        final ComponentWidget nameWidget = ComponentWidget.component(displayName);
        nameWidget.addId("item-name");
        if (this.isIncompatible()) {
            final HorizontalListWidget itemNameWrapper = new HorizontalListWidget();
            ((AbstractWidget<Widget>)itemNameWrapper).addId("item-name-wrapper");
            itemNameWrapper.addEntry(nameWidget);
            final IconWidget incompatibleBadge = new IconWidget(Textures.SpriteFlint.WARNING);
            incompatibleBadge.addId("incompatible-badge");
            incompatibleBadge.setHoverComponent(this.getIncompatibleComponent());
            itemNameWrapper.addEntry(incompatibleBadge);
            textContainer.addContent(itemNameWrapper);
        }
        else {
            textContainer.addContent(nameWidget);
        }
        final String authorName = this.modification.getAuthor();
        final String organizationName = (authorName == null) ? "" : authorName;
        final ComponentWidget organizationWidget = ComponentWidget.i18n("labymod.addons.store.modification.organization.name", organizationName);
        organizationWidget.addId("item-organization");
        textContainer.addContent(organizationWidget);
        if (!(this.modification instanceof FlintIndex.FlintIndexModification)) {
            final FlintModification.Rating rating = this.modification.getRating();
            textContainer.addContent(new RatingWidget(rating.getRating(), rating.getCount()));
        }
        final ComponentWidget descriptionWidget = ComponentWidget.text(this.modification.getShortDescription());
        descriptionWidget.addId("item-description");
        textContainer.addContent(descriptionWidget);
        container.addFlexibleContent(textContainer);
        if (this.flintPressable != null && !(this.modification instanceof FlintIndex.FlintIndexModification)) {
            this.setPressable(this.flintPressable);
        }
        ((AbstractWidget<FlexibleContentWidget>)this).addChild(container);
    }
}
