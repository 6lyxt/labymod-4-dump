// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.cosmetics;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.client.gui.screen.AutoAlignType;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.action.Switchable;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.WrappedListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.core.labymodnet.widgetoptions.WidgetOption;
import java.util.List;
import net.labymod.core.labymodnet.models.Cosmetic;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
@Deprecated
@Link("activity/player/cosmetic-settings.lss")
public class CosmeticSettingsWidget extends AbstractWidget<Widget>
{
    private static final ModifyReason REASON;
    private final String translationKeyPrefix;
    private final Cosmetic cosmetic;
    private final List<WidgetOption> options;
    private final CosmeticSettingsListener listener;
    private SwitchWidget switchWidget;
    private ScrollWidget scrollWidget;
    
    public CosmeticSettingsWidget(final Cosmetic cosmetic, final List<WidgetOption> options, final CosmeticSettingsListener listener) {
        this.translationKeyPrefix = "labymod.activity.playerCustomization.cosmetics.settings.";
        this.cosmetic = cosmetic;
        this.options = options;
        this.listener = listener;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final VerticalListWidget<Widget> container = new VerticalListWidget<Widget>();
        container.addId("setting-container");
        final FlexibleContentWidget header = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)header).addId("setting-header");
        final ComponentWidget title = ComponentWidget.text(this.cosmetic.getName());
        title.addId("title");
        header.addFlexibleContent(title);
        final Switchable switchable = this.listener.onSwitch();
        if (switchable != null) {
            (this.switchWidget = SwitchWidget.text("", "", switchable)).addId("switch");
            this.switchWidget.setValue(this.cosmetic.isEnabled());
            header.addContent(this.switchWidget);
        }
        container.addChild(header);
        final WrappedListWidget<Widget> listWidget = new WrappedListWidget<Widget>();
        listWidget.addId("setting-list");
        for (final WidgetOption option : this.options) {
            option.begin(this.cosmetic, this.listener, this.translationKeyPrefix);
            final WidgetOption widgetOption = option;
            final WrappedListWidget<Widget> obj = listWidget;
            Objects.requireNonNull(obj);
            widgetOption.create(obj::addChild);
        }
        if (!this.options.isEmpty()) {
            container.addChild(listWidget);
        }
        this.initializeFooter(container);
        (this.scrollWidget = new ScrollWidget(container)).addId("setting-scroll");
        ((AbstractWidget<ScrollWidget>)this).addChild(this.scrollWidget);
        if (!this.options.isEmpty()) {
            this.createContextMenuLazy(contextMenu -> {
                final ContextMenuEntry resetOptionsEntry = ContextMenuEntry.builder().text(Component.translatable("labymod.activity.shop.cosmeticSettingsReset", new Component[0])).icon(Textures.SpriteCommon.TRASH).clickHandler(entry -> {
                    this.cosmetic.resetData();
                    this.listener.onDataUpdate(this.cosmetic);
                    this.reInitialize();
                    return true;
                }).build();
                contextMenu.addEntry(resetOptionsEntry);
            });
        }
    }
    
    protected void initializeFooter(final VerticalListWidget<Widget> container) {
    }
    
    public Cosmetic cosmetic() {
        return this.cosmetic;
    }
    
    public void setCosmeticEnabled(final boolean enabled) {
        if (this.switchWidget == null || this.switchWidget.getValue() == enabled) {
            return;
        }
        this.switchWidget.setValue(enabled);
        this.switchWidget.refreshActionTime();
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        if (this.scrollWidget != null) {
            return this.scrollWidget.bounds().getHeight(BoundsType.OUTER) + this.bounds().getVerticalOffset(type);
        }
        return super.getContentHeight(type);
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        if (this.scrollWidget == null || this.parent == null) {
            super.onBoundsChanged(previousRect, newRect);
            return;
        }
        final Bounds bounds = this.bounds();
        final Bounds parentBounds = this.parent.bounds();
        final ReasonableMutableRectangle content = this.scrollWidget.bounds().rectangle(BoundsType.OUTER);
        final float maxHeight = parentBounds.getHeight(BoundsType.INNER) - bounds.getVerticalOffset(BoundsType.OUTER);
        if (content.getHeight() > maxHeight) {
            content.setHeight(maxHeight, CosmeticSettingsWidget.REASON);
        }
        else {
            content.setHeight(this.scrollWidget.getContentHeight(BoundsType.OUTER), CosmeticSettingsWidget.REASON);
        }
    }
    
    @Override
    public boolean hasAutoBounds(final Widget child, final AutoAlignType type) {
        return type == AutoAlignType.HEIGHT;
    }
    
    public void onOpenSettings(final CosmeticSettingsWidget widget) {
        this.listener.onOpenSettings(widget);
    }
    
    public void onCloseSettings(final CosmeticSettingsWidget widget) {
        this.listener.onCloseSettings(widget);
    }
    
    static {
        REASON = ModifyReason.of("settingContent");
    }
    
    public interface CosmeticSettingsListener
    {
        void onOpenSettings(final CosmeticSettingsWidget p0);
        
        void onCloseSettings(final CosmeticSettingsWidget p0);
        
        @Nullable
        Switchable onSwitch();
        
        void onDataUpdate(final Cosmetic p0);
        
        default boolean shouldSendRemoteRequest() {
            return false;
        }
    }
}
