// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.store.profile;

import net.labymod.core.flint.marketplace.FlintPermission;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.Locale;
import net.labymod.api.util.TextFormat;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.VariableIconWidget;
import net.labymod.core.flint.FlintController;
import net.labymod.api.client.gui.screen.widget.widgets.layout.IconSliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.MarkdownWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.util.markdown.MarkdownDocument;
import net.labymod.core.flint.marketplace.FlintModification;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

@AutoWidget
public class DescriptionWidget extends VerticalListWidget<Widget>
{
    private static final String PERMISSION_TRANSLATION_KEY_PREFIX = "labymod.addons.store.profile.permission.";
    private final FlintModification modification;
    private final MarkdownDocument description;
    
    public DescriptionWidget(final FlintModification modification, final MarkdownDocument description) {
        this.modification = modification;
        this.description = description;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        if (this.description == null || this.description.getComponents().isEmpty()) {
            final ComponentWidget widget = ComponentWidget.text(this.modification.getShortDescription());
            widget.addId("description-text");
            ((AbstractWidget<ComponentWidget>)this).addChild(widget);
        }
        else {
            final MarkdownWidget markdownWidget = new MarkdownWidget(this.description);
            markdownWidget.addId("description-markdown");
            ((AbstractWidget<MarkdownWidget>)this).addChild(markdownWidget);
        }
        final FlintModification.Image[] sliderImages = this.modification.getSliderImages();
        if (sliderImages != null && sliderImages.length != 0) {
            final ComponentWidget screenshotTitle = ComponentWidget.text("Screenshots");
            screenshotTitle.addId("addon-screenshots-title");
            ((AbstractWidget<ComponentWidget>)this).addChild(screenshotTitle);
            AbstractWidget<Widget> container = this;
            if (sliderImages.length > 1) {
                container = new IconSliderWidget();
                container.addId("addon-screenshots-container");
                ((AbstractWidget<AbstractWidget<Widget>>)this).addChild(container);
            }
            for (final FlintModification.Image sliderImage : sliderImages) {
                final VariableIconWidget screenshotWidget = new VariableIconWidget(sliderImage.getIconUrl(), FlintController::getVariableBrandUrl);
                screenshotWidget.setCleanupOnDispose(true);
                screenshotWidget.addId("addon-screenshot");
                if (sliderImages.length == 1) {
                    screenshotWidget.addId("addon-screenshot-single");
                }
                container.addChild(screenshotWidget);
            }
        }
        final String[] rawPermissions = this.modification.getRawPermissions();
        if (rawPermissions != null && rawPermissions.length != 0) {
            final FlintPermission[] permissions = this.modification.getPermissions();
            final FlexibleContentWidget permissionList = new FlexibleContentWidget();
            ((AbstractWidget<Widget>)permissionList).addId("addon-permission-list");
            final ComponentWidget titleWidget = ComponentWidget.text(this.modification.getName() + "...");
            titleWidget.addId("addon-permissions-title");
            permissionList.addContent(titleWidget);
            final FlintPermission[] array2 = permissions;
            for (int length2 = array2.length, j = 0; j < length2; ++j) {
                final FlintPermission permission = array2[j];
                final HorizontalListWidget permissionContainer = new HorizontalListWidget();
                ((AbstractWidget<Widget>)permissionContainer).addId("addon-permission-container");
                if (permission.isCritical()) {
                    ((AbstractWidget<Widget>)permissionContainer).addId("addon-permission-critical");
                    final IconWidget iconWidget = new IconWidget(Textures.SpriteFlint.WARNING);
                    iconWidget.addId("addon-permission-icon");
                    permissionContainer.addEntry(iconWidget);
                }
                else {
                    permissionContainer.addEntry(new DivWidget().addId("addon-permission-icon"));
                }
                final ComponentWidget permissionWidget = ComponentWidget.i18n("labymod.addons.store.profile.permission.types." + TextFormat.SNAKE_CASE.toLowerCamelCase(permission.getKey().toUpperCase(Locale.ENGLISH)));
                permissionWidget.addId("addon-permission-text");
                permissionContainer.addEntry(permissionWidget);
                permissionList.addContent(permissionContainer);
            }
            final ComponentWidget infoWidget = ComponentWidget.i18n("labymod.addons.store.profile.permission." + (this.modification.isVerified() ? "verified" : "unverified"));
            infoWidget.addId("addon-permissions-info");
            permissionList.addContent(infoWidget);
            final DivWidget wrapper = new DivWidget();
            wrapper.addId("addon-permission-wrapper");
            ((AbstractWidget<FlexibleContentWidget>)wrapper).addChild(permissionList);
            ((AbstractWidget<DivWidget>)this).addChild(wrapper);
        }
    }
}
