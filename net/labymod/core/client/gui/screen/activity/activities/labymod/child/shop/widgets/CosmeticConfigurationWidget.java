// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.widgets;

import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import java.util.Iterator;
import net.labymod.core.labymodnet.widgetoptions.WidgetOptionService;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.WrappedListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.gui.screen.Parent;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.widget.action.Switchable;
import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.core.labymodnet.widgetoptions.WidgetOption;
import java.util.List;
import net.labymod.core.labymodnet.models.Cosmetic;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.cosmetics.CosmeticSettingsWidget;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class CosmeticConfigurationWidget extends AbstractWidget<Widget>
{
    private static final ModifyReason REASON;
    private static final String TRANSLATION_KEY_PREFIX = "labymod.activity.playerCustomization.cosmetics.settings.";
    private final CosmeticSettingsWidget.CosmeticSettingsListener listener;
    private Cosmetic cosmetic;
    private List<WidgetOption> options;
    private DivWidget wrapper;
    private VerticalListWidget<Widget> container;
    private ScrollWidget scrollWidget;
    
    public CosmeticConfigurationWidget(final Consumer<Cosmetic> onDataUpdate) {
        this.listener = new CosmeticSettingsWidget.CosmeticSettingsListener(this) {
            @Override
            public void onOpenSettings(final CosmeticSettingsWidget widget) {
            }
            
            @Override
            public void onCloseSettings(final CosmeticSettingsWidget widget) {
            }
            
            @Nullable
            @Override
            public Switchable onSwitch() {
                return null;
            }
            
            @Override
            public void onDataUpdate(final Cosmetic cosmetic) {
                onDataUpdate.accept(cosmetic);
            }
        };
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        if (this.cosmetic == null) {
            return;
        }
        if (this.options == null) {
            final WidgetOptionService service = LabyMod.references().shopController().getWidgetOptionService();
            this.options = service.getOptions(this.cosmetic, null);
        }
        if (this.options.isEmpty()) {
            return;
        }
        (this.container = new VerticalListWidget<Widget>()).addId("setting-container");
        final ComponentWidget title = ComponentWidget.text(this.cosmetic.getName());
        title.addId("title");
        this.container.addChild(title);
        final WrappedListWidget<Widget> listWidget = new WrappedListWidget<Widget>();
        listWidget.addId("setting-list");
        for (final WidgetOption option : this.options) {
            option.begin(this.cosmetic, this.listener, "labymod.activity.playerCustomization.cosmetics.settings.");
            final WidgetOption widgetOption = option;
            final WrappedListWidget<Widget> obj = listWidget;
            Objects.requireNonNull(obj);
            widgetOption.create(obj::addChild);
        }
        if (!this.options.isEmpty()) {
            this.container.addChild(listWidget);
            final ComponentWidget info = ComponentWidget.i18n("labymod.activity.shop.cosmeticSettingsInfo");
            info.addId("info");
            this.container.addChild(info);
        }
        (this.scrollWidget = new ScrollWidget(this.container)).addId("setting-scroll");
        (this.wrapper = new DivWidget()).addId("setting-wrapper");
        ((AbstractWidget<ScrollWidget>)this.wrapper).addChild(this.scrollWidget);
        ((AbstractWidget<DivWidget>)this).addChild(this.wrapper);
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
    
    @Override
    protected void updateContentBounds() {
        super.updateContentBounds();
        this.update();
    }
    
    public CosmeticConfigurationWidget update(final Cosmetic cosmetic) {
        if (cosmetic == null) {
            this.cosmetic = null;
            this.options = null;
            return this;
        }
        if (this.cosmetic == null || this.cosmetic.getId() != cosmetic.getId()) {
            this.cosmetic = cosmetic;
            this.options = null;
        }
        return this;
    }
    
    public boolean isIdMatching(final int id) {
        return this.cosmetic != null && this.cosmetic.getId() == id;
    }
    
    private void update() {
        if (this.wrapper == null || this.scrollWidget == null || this.container == null) {
            return;
        }
        final Bounds bounds = this.bounds();
        final Bounds wrapperBounds = this.wrapper.bounds();
        final Bounds scrollBounds = this.scrollWidget.bounds();
        final float maxContentHeight = bounds.getHeight() - wrapperBounds.getVerticalOffset(BoundsType.BORDER);
        final float contentHeight = this.container.getContentHeight(BoundsType.OUTER) + scrollBounds.getVerticalOffset(BoundsType.OUTER);
        final float scrollHeight = Math.min(contentHeight, maxContentHeight);
        final float contentTop = wrapperBounds.getBottom() - scrollHeight;
        wrapperBounds.setX(bounds.getX(), BoundsType.BORDER, CosmeticConfigurationWidget.REASON);
        wrapperBounds.setSize(bounds.getWidth(), scrollHeight + wrapperBounds.getVerticalOffset(BoundsType.BORDER), BoundsType.BORDER, CosmeticConfigurationWidget.REASON);
        scrollBounds.setOuterPosition(wrapperBounds.getX(), contentTop, CosmeticConfigurationWidget.REASON);
        scrollBounds.setOuterSize(wrapperBounds.getWidth(), wrapperBounds.getHeight(), CosmeticConfigurationWidget.REASON);
        final Bounds containerBounds = this.container.bounds();
        containerBounds.setOuterPosition(scrollBounds.getX(), scrollBounds.getY(), CosmeticConfigurationWidget.REASON);
        containerBounds.setOuterSize(scrollBounds.getWidth(), contentHeight, CosmeticConfigurationWidget.REASON);
    }
    
    static {
        REASON = ModifyReason.of("settingContent");
    }
}
