// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.screenshots;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.Laby;
import java.nio.file.Path;
import java.util.List;
import net.labymod.core.client.gui.screen.widget.widgets.screenshot.timeline.ScreenshotTileWidget;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.core.client.screenshot.ScreenshotSection;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.util.TextFormat;
import java.util.Locale;
import net.labymod.core.client.screenshot.Screenshot;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.Constants;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ProgressBarWidget;
import net.labymod.core.client.screenshot.ScreenshotBrowser;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.core.client.gui.screen.widget.widgets.screenshot.viewer.ScreenshotViewerWidget;
import net.labymod.core.client.gui.screen.widget.widgets.screenshot.timeline.ScreenshotTimelineWidget;
import net.labymod.core.client.screenshot.ScreenshotBrowserNotifier;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;

@Link("activity/screenshot/screenshot-browser.lss")
@AutoActivity
public class ScreenshotBrowserActivity extends SimpleActivity implements ScreenshotBrowserNotifier, ScreenshotTimelineWidget.ScreenshotTimelineHolder, ScreenshotViewerWidget.ScreenshotViewerHolder
{
    public static final ScreenshotBrowserActivity INSTANCE;
    private final ScreenshotBrowser browser;
    private final ScreenshotTimelineWidget timelineWidget;
    private final ScreenshotViewerWidget screenshotViewerWidget;
    private final ProgressBarWidget progressBarWidget;
    private final SliderWidget zoomSlider;
    private IndexState indexState;
    private float indexProgress;
    private ComponentWidget statusWidget;
    private TextFieldWidget searchWidget;
    private int previousScreenshotAmount;
    
    public ScreenshotBrowserActivity() {
        this.previousScreenshotAmount = 0;
        this.browser = LabyMod.references().screenshotBrowser();
        this.indexState = this.browser.getState();
        (this.timelineWidget = new ScreenshotTimelineWidget(this)).addId("timeline");
        (this.screenshotViewerWidget = new ScreenshotViewerWidget(this)).addId("viewer");
        (this.progressBarWidget = new ProgressBarWidget()).addId("progress-bar");
        (this.zoomSlider = new SliderWidget(1.0f, value -> this.timelineWidget.updateTileCount((int)value))).range(5.0f, 30.0f);
        this.zoomSlider.addId("zoom-slider");
        ((Document)this.document).addId("browser");
    }
    
    @Override
    public void onOpenScreen() {
        super.onOpenScreen();
        this.browser.subscribe(this);
        final int amount = this.browser.getScreenshots().size();
        if (amount != this.previousScreenshotAmount) {
            this.timelineWidget.updateAllSections();
            this.screenshotViewerWidget.onSectionChanged();
        }
    }
    
    @Override
    public void onCloseScreen() {
        super.onCloseScreen();
        this.browser.unsubscribe(this);
        this.previousScreenshotAmount = this.browser.getScreenshots().size();
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        (this.statusWidget = ComponentWidget.empty()).addId("status");
        ((AbstractWidget<ComponentWidget>)this.document).addChild(this.statusWidget);
        final DivWidget headerWidget = new DivWidget();
        headerWidget.addId("header-bar");
        final HorizontalListWidget headerGroup = new HorizontalListWidget();
        ((AbstractWidget<Widget>)headerGroup).addId("header-group");
        (this.searchWidget = new TextFieldWidget()).addId("search");
        final String query = this.timelineWidget.screenshotFilter().getQuery();
        if (query != null) {
            this.searchWidget.setText(query);
        }
        this.searchWidget.placeholder(Component.translatable("labymod.ui.textfield.search", new Component[0]));
        this.searchWidget.setActionListener(() -> {
            this.timelineWidget.updateQuery(this.searchWidget.getText());
            this.updateStatus();
            return;
        });
        headerGroup.addEntry(this.searchWidget);
        final ButtonWidget refreshButton = ButtonWidget.icon(Textures.SpriteCommon.REFRESH);
        ((AbstractWidget<Widget>)refreshButton).addId("refresh", "button");
        refreshButton.setHoverComponent(Component.translatable("labymod.ui.button.refresh", new Component[0]));
        refreshButton.setPressable(() -> {
            this.browser.refresh();
            this.updateStatus();
            return;
        });
        headerGroup.addEntry(refreshButton);
        final ButtonWidget openFolderWidget = ButtonWidget.icon(Textures.SpriteCommon.EXPORT);
        ((AbstractWidget<Widget>)openFolderWidget).addId("open-folder", "button");
        openFolderWidget.setHoverComponent(Component.translatable("labymod.activity.screenshotBrowser.openFolder", new Component[0]));
        openFolderWidget.setPressable(() -> OperatingSystem.getPlatform().openFile(Constants.Files.SCREENSHOT_DIRECTORY.toFile()));
        headerGroup.addEntry(openFolderWidget);
        ((AbstractWidget<HorizontalListWidget>)headerWidget).addChild(headerGroup);
        ((AbstractWidget<DivWidget>)this.document).addChild(headerWidget);
        final DivWidget footerWidget = new DivWidget();
        footerWidget.addId("footer-bar");
        final ComponentWidget zoomTitle = ComponentWidget.i18n("labymod.activity.screenshotBrowser.zoom");
        zoomTitle.addId("zoom-title");
        ((AbstractWidget<ComponentWidget>)footerWidget).addChild(zoomTitle);
        ((AbstractWidget<SliderWidget>)footerWidget).addChild(this.zoomSlider);
        final ButtonWidget buttonWidget = ButtonWidget.i18n("labymod.ui.button.done");
        ((AbstractWidget<Widget>)buttonWidget).addId("done");
        buttonWidget.setPressListener(this::displayPreviousScreen);
        ((AbstractWidget<ButtonWidget>)footerWidget).addChild(buttonWidget);
        ((AbstractWidget<DivWidget>)this.document).addChild(footerWidget);
        ((AbstractWidget<ScreenshotTimelineWidget>)this.document).addChild(this.timelineWidget);
        ((AbstractWidget<ScreenshotViewerWidget>)this.document).addChild(this.screenshotViewerWidget);
        ((AbstractWidget<ProgressBarWidget>)this.document).addChild(this.progressBarWidget);
        this.updateStatus();
    }
    
    @Override
    public void render(final ScreenContext context) {
        final float opacity = this.screenshotViewerWidget.isOpen() ? 0.0f : 1.0f;
        for (final Widget child : ((Document)this.document).getChildren()) {
            if (!(child instanceof ScreenshotViewerWidget)) {
                ((AbstractWidget)child).opacity().set(opacity);
            }
        }
        this.zoomSlider.setValue(this.timelineWidget.getTilesPerRow());
        super.render(context);
    }
    
    @Override
    public void open(final Screenshot screenshot) {
        this.screenshotViewerWidget.displayScreenshot(screenshot);
    }
    
    private void updateStatus() {
        final boolean isEmpty = this.timelineWidget.isEmpty();
        if (isEmpty) {
            if (this.indexState != IndexState.IDLE) {
                final String key = String.format(Locale.ROOT, "labymod.activity.screenshotBrowser.status.state.%s", TextFormat.SNAKE_CASE.toCamelCase(this.indexState.name(), true));
                final int progress = (int)(this.indexProgress * 100.0f);
                this.statusWidget.setComponent(((BaseComponent<Component>)Component.translatable(key, new Component[0]).args(Component.text("" + progress))).color(NamedTextColor.GRAY));
            }
            else if (this.timelineWidget.screenshotFilter().hasQuery()) {
                this.statusWidget.setComponent(((BaseComponent<Component>)Component.translatable("labymod.activity.screenshotBrowser.status.noResults", new Component[0])).color(NamedTextColor.RED));
            }
            else {
                this.statusWidget.setComponent(((BaseComponent<Component>)Component.translatable("labymod.activity.screenshotBrowser.status.noScreenshots", new Component[0])).color(NamedTextColor.RED));
            }
        }
        this.statusWidget.setVisible(isEmpty);
        this.progressBarWidget.setVisible(this.indexState != IndexState.IDLE);
        this.progressBarWidget.setProgress(this.indexProgress);
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        if (this.screenshotViewerWidget.isOpen() && key == Key.ESCAPE) {
            this.screenshotViewerWidget.close();
            return true;
        }
        return super.keyPressed(key, type);
    }
    
    @Override
    public void onIndexProgress(final IndexState state, final float progress) {
        this.indexState = state;
        this.indexProgress = progress;
        this.labyAPI.minecraft().executeOnRenderThread(this::updateStatus);
    }
    
    @Override
    public void onSectionAdded(final ScreenshotSection section) {
        this.labyAPI.minecraft().executeOnRenderThread(this::updateStatus);
        this.timelineWidget.addSection(section);
        this.screenshotViewerWidget.onSectionChanged();
    }
    
    @Override
    public void onSectionChanged(final ScreenshotSection section) {
        this.labyAPI.minecraft().executeOnRenderThread(this::updateStatus);
        this.timelineWidget.updateSection(section);
        this.screenshotViewerWidget.onSectionChanged();
    }
    
    @Override
    public void onSectionRemoved(final ScreenshotSection section) {
        this.labyAPI.minecraft().executeOnRenderThread(this::updateStatus);
        this.timelineWidget.removeSection(section);
        this.screenshotViewerWidget.onSectionChanged();
    }
    
    @Override
    public ScreenshotBrowser browser() {
        return this.browser;
    }
    
    @Override
    public Rectangle getTileRectangleOf(final Screenshot screenshot) {
        final ScreenshotTileWidget tileWidget = this.timelineWidget.getContainer().getTileWidget(screenshot);
        return (tileWidget == null) ? null : tileWidget.bounds();
    }
    
    @Override
    public List<Screenshot> getScreenshots() {
        return this.timelineWidget.getFilteredScreenshots();
    }
    
    @Override
    public Screenshot.QualityType getQuality() {
        return this.timelineWidget.getQuality();
    }
    
    @Override
    public Screenshot getOpenScreenshot() {
        return this.screenshotViewerWidget.isOpen() ? this.screenshotViewerWidget.getScreenshot() : null;
    }
    
    public static void openScreenshot(final Path path) {
        final ScreenshotBrowserActivity activity = ScreenshotBrowserActivity.INSTANCE;
        Laby.labyAPI().minecraft().minecraftWindow().displayScreen(activity);
        final Screenshot screenshot = activity.browser().getScreenshot(path);
        if (screenshot == null) {
            return;
        }
        activity.open(screenshot);
    }
    
    static {
        INSTANCE = new ScreenshotBrowserActivity();
    }
}
