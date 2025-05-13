// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity.types;

import net.labymod.api.client.component.builder.StyleableBuilder;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.event.Subscribe;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;
import net.labymod.api.util.CharSequences;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.gui.screen.activity.activities.SearchActivity;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.HorizontalListEntry;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import net.labymod.api.client.gfx.pipeline.post.processors.PostProcessors;
import net.labymod.api.configuration.labymod.main.laby.ingame.MenuBlurConfig;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.Laby;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.event.EventBus;
import net.labymod.api.client.gui.screen.activity.util.PageNavigator;
import net.labymod.api.client.gui.screen.key.Key;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.screen.activity.Link;

@Link("activity/generic-wheel.lss")
public abstract class AbstractWheelInteractionOverlayActivity extends AbstractInteractionOverlayActivity
{
    protected static final String CONTAINER_ID = "container";
    protected static final String WHEEL_ID = "wheel";
    protected static final String WHEEL_TITLE_ID = "wheel-title";
    protected static final String WHEEL_OPEN_ANIMATION_ID = "wheel-open-animation";
    protected static final String WHEEL_PAGE_SWITCH_ANIMATION_ID = "wheel-page-switch-animation";
    protected static final String WHEEL_SUBTITLE_ID = "wheel-subtitle";
    protected static final String WHEEL_PAGE_BAR_ID = "wheel-page-bar";
    protected static final String ARROW_ICON_ID = "arrow-icon";
    protected static final String MOUSE_ICON_ID = "mouse-icon";
    protected static final String ICON_PAGE_SWITCH_LEFT_ID = "icon-page-switch-left";
    protected static final String ICON_PAGE_SWITCH_RIGHT_ID = "icon-page-switch-right";
    protected static final String WHEEL_OPEN_ANIMATION = "wheel-open";
    protected static final String ICON_PAGE_SWITCH_ANIMATION = "icon-page-switch";
    protected static final String WHEEL_PAGE_SWITCH_LEFT_ANIMATION = "wheel-page-switch-left";
    protected static final String WHEEL_PAGE_SWITCH_RIGHT_ANIMATION = "wheel-page-switch-right";
    protected static final int DEFAULT_SEGMENT_COUNT = 6;
    private static final ModifyReason SEARCH_BOUNDS;
    private static final String NOT_CONNECTED_SHORT_TRANSLATION_KEY = "labymod.activity.generic_wheel.notConnectedShort";
    private static final String PAGE_NAME_TRANSLATION_KEY = "labymod.activity.generic_wheel.page.name";
    private static final int MOUSE_WIDGETS = 2;
    protected final Widget[] leftMouseWidgets;
    protected final Widget[] rightMouseWidgets;
    private final Object2IntMap<Key> mappedKeys;
    private final PageNavigator pageNavigator;
    private final EventBus eventBus;
    private final WheelInteractionKeyListener keyListener;
    private DivWidget containerWidget;
    private ComponentWidget titleWidget;
    private WheelWidget wheelWidget;
    private CharSequence searchText;
    private boolean initialized;
    private boolean skipAnimation;
    private long lastTimeKeyPressed;
    
    protected AbstractWheelInteractionOverlayActivity() {
        this.leftMouseWidgets = new Widget[2];
        this.rightMouseWidgets = new Widget[2];
        this.mappedKeys = (Object2IntMap<Key>)new Object2IntOpenHashMap();
        this.searchText = "";
        this.initialized = false;
        this.skipAnimation = false;
        this.lastTimeKeyPressed = 0L;
        this.eventBus = Laby.references().eventBus();
        this.pageNavigator = new PageNavigator(() -> {
            this.lastTimeKeyPressed = 0L;
            super.reload();
            return;
        });
        this.keyListener = new WheelInteractionKeyListener(this.labyAPI, this);
        this.eventBus.registerListener(this.keyListener);
    }
    
    @Override
    public void onOpenScreen() {
        super.onOpenScreen();
        if (!this.eventBus.isListenerRegistered(this.keyListener)) {
            this.eventBus.registerListener(this.keyListener);
        }
    }
    
    @Override
    public void onCloseScreen() {
        super.onCloseScreen();
        this.eventBus.unregisterListener(this.keyListener);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.initializeMappedKeys();
        (this.containerWidget = new DivWidget()).addId("container");
        this.containerWidget.addId("wheel-open-animation");
        ((AbstractWidget<Widget>)(this.wheelWidget = this.createWheelWidget())).addId("wheel");
        ((AbstractWidget<Widget>)this.wheelWidget).addId("wheel-page-switch-animation");
        (this.titleWidget = ComponentWidget.component(this.createTitleComponent())).addId("wheel-title");
        ((AbstractWidget<ComponentWidget>)this.containerWidget).addChild(this.titleWidget);
        ((AbstractWidget<WheelWidget>)this.containerWidget).addChild(this.wheelWidget);
        this.containerWidget.addChild(this.createSubtitle());
        final Document document = super.document();
        ((AbstractWidget<DivWidget>)document).addChild(this.containerWidget);
    }
    
    @Override
    protected void postStyleSheetLoad() {
        super.postStyleSheetLoad();
        final int currentPage = this.pageNavigator.getCurrentPage();
        final int previousPage = this.pageNavigator.getPreviousPage();
        if (previousPage == currentPage) {
            if (this.isSearchActivityOpen()) {
                if (!this.initialized) {
                    this.playAnimation(this.wheelWidget, "wheel-open");
                    this.initialized = true;
                }
            }
            else {
                this.playAnimation(this.wheelWidget, "wheel-open");
            }
        }
        else if (previousPage > currentPage) {
            this.playAnimation(this.wheelWidget, "wheel-page-switch-left");
            for (final Widget leftMouseWidget : this.leftMouseWidgets) {
                if (leftMouseWidget != null) {
                    this.playAnimation(leftMouseWidget, "icon-page-switch");
                }
            }
        }
        else {
            this.playAnimation(this.wheelWidget, "wheel-page-switch-right");
            for (final Widget rightMouseWidget : this.rightMouseWidgets) {
                if (rightMouseWidget != null) {
                    this.playAnimation(rightMouseWidget, "icon-page-switch");
                }
            }
        }
    }
    
    @Override
    public void render(final ScreenContext context) {
        PostProcessors.processMenuBlur(MenuBlurConfig.ScreenType.SPRAY_WHEEL, context.getTickDelta());
        super.render(context);
    }
    
    @Override
    protected float getRadius() {
        return this.containerWidget.bounds().getWidth() / 2.1f;
    }
    
    @Override
    public void closeInteraction() {
        super.closeInteraction();
        this.pageNavigator.setCurrentPage(0);
        this.pageNavigator.setPreviousPageToCurrentPage();
        this.searchText = "";
        this.initialized = false;
    }
    
    public PageNavigator pageNavigator() {
        return this.pageNavigator;
    }
    
    public final WheelWidget wheelWidget() {
        return this.wheelWidget;
    }
    
    public final ComponentWidget titleWidget() {
        return this.titleWidget;
    }
    
    @MustBeInvokedByOverriders
    protected void refresh(final boolean skipOpenAnimation) {
        final boolean prevSkipOpenAnimation = this.skipAnimation;
        try {
            this.skipAnimation = skipOpenAnimation;
            this.reload();
        }
        finally {
            this.skipAnimation = prevSkipOpenAnimation;
        }
    }
    
    protected Widget createSubtitle() {
        final VerticalListWidget<Widget> subtitleList = new VerticalListWidget<Widget>();
        subtitleList.addId("wheel-subtitle");
        if (this.hasEntries()) {
            subtitleList.addChild(this.createPageBar());
        }
        else if (!this.labyAPI.labyConnect().isAuthenticated()) {
            subtitleList.addChild(ComponentWidget.i18n("labymod.activity.generic_wheel.notConnectedShort", NamedTextColor.RED));
        }
        else {
            subtitleList.addChild(ComponentWidget.text("labymod.net/shop", ((StyleableBuilder<T, Style.Builder>)((StyleableBuilder<T, Style.Builder>)Style.builder()).color(NamedTextColor.AQUA)).decorate(TextDecoration.ITALIC).build()));
        }
        return subtitleList;
    }
    
    protected Widget createPageBar() {
        final HorizontalListWidget pageBar = new HorizontalListWidget();
        ((AbstractWidget<Widget>)pageBar).addId("wheel-page-bar");
        if (this.pageNavigator.getCurrentPage() > this.pageNavigator.getMinimumPage()) {
            final IconWidget leftArrowWidget = new IconWidget(Textures.SpriteCommon.ARROW_LEFT);
            leftArrowWidget.addId("arrow-icon");
            final IconWidget leftMouseWidget = new IconWidget(Textures.SpriteCommon.MOUSE_LEFT);
            leftMouseWidget.addId("mouse-icon");
            this.leftMouseWidgets[0] = leftArrowWidget;
            this.leftMouseWidgets[1] = leftMouseWidget;
            if (this.pageNavigator.getPreviousPage() > this.pageNavigator.getCurrentPage()) {
                leftArrowWidget.addId("icon-page-switch-left");
                leftMouseWidget.addId("icon-page-switch-left");
            }
            pageBar.addEntry(leftArrowWidget);
            pageBar.addEntry(leftMouseWidget);
        }
        final Widget pageIndicatorWidget = this.createPageIndicator();
        final HorizontalListEntry pageIndicatorWidgetEntry = pageBar.addEntry(pageIndicatorWidget);
        pageIndicatorWidgetEntry.alignment().set(HorizontalAlignment.CENTER);
        if (this.pageNavigator.getCurrentPage() < this.pageNavigator.getMaximumPage()) {
            final IconWidget rightArrowWidget = new IconWidget(Textures.SpriteCommon.ARROW_RIGHT);
            rightArrowWidget.addId("arrow-icon");
            final IconWidget rightMouseWidget = new IconWidget(Textures.SpriteCommon.MOUSE_RIGHT);
            rightMouseWidget.addId("mouse-icon");
            this.rightMouseWidgets[0] = rightArrowWidget;
            this.rightMouseWidgets[1] = rightMouseWidget;
            if (this.pageNavigator.getPreviousPage() < this.pageNavigator.getCurrentPage()) {
                rightArrowWidget.addId("icon-page-switch-right");
                rightMouseWidget.addId("icon-page-switch-right");
            }
            final HorizontalListEntry rightArrowWidgetEntry = pageBar.addEntry(rightArrowWidget);
            rightArrowWidgetEntry.alignment().set(HorizontalAlignment.RIGHT);
            final HorizontalListEntry rightMouseWidgetEntry = pageBar.addEntry(rightMouseWidget);
            rightMouseWidgetEntry.alignment().set(HorizontalAlignment.RIGHT);
        }
        return pageBar;
    }
    
    protected Widget createPageIndicator() {
        return ComponentWidget.component(Component.translatable("labymod.activity.generic_wheel.page.name", new Component[0]).arguments(Component.text(this.pageNavigator.getCurrentPage() + 1), Component.text(this.pageNavigator.getMaximumPage() + 1)));
    }
    
    protected final void initializeMappedKeys() {
        this.mappedKeys.clear();
        this.onInitializeMappedKeys(this.mappedKeys);
    }
    
    protected int getMappedPosition(final Key key) {
        if (this.mappedKeys.containsKey((Object)key)) {
            return this.mappedKeys.getInt((Object)key);
        }
        return Integer.MIN_VALUE;
    }
    
    protected abstract Component createTitleComponent();
    
    protected abstract boolean hasEntries();
    
    protected int getSegmentCount() {
        return 6;
    }
    
    protected abstract WheelWidget createWheelWidget();
    
    protected abstract Key getKeyToOpen();
    
    protected abstract void onInitializeMappedKeys(final Object2IntMap<Key> p0);
    
    protected abstract void onKey(final Key p0, final KeyEvent.State p1);
    
    protected boolean shouldOpenInteractionMenu() {
        return true;
    }
    
    @MustBeInvokedByOverriders
    protected void openInteractionOverlay() {
        this.openInteraction();
    }
    
    @MustBeInvokedByOverriders
    protected void closeInteractionOverlay() {
        this.closeInteraction();
    }
    
    protected CharSequence getSearchText() {
        return this.searchText;
    }
    
    @Override
    public boolean isAcceptingInput() {
        return (this.isInteractionOpen() && this.isSearchActivityOpen()) || super.isAcceptingInput();
    }
    
    protected boolean isSearchActivityOpen() {
        final Window window = this.labyAPI.minecraft().minecraftWindow();
        final ScreenWrapper currentScreen = window.currentScreen();
        return currentScreen != null && currentScreen.isActivity() && currentScreen.asActivity() instanceof SearchActivity;
    }
    
    private void handleSearchOverlay(final boolean skipFirstCharacter) {
        final SearchActivity activity = new SearchActivity(value -> {
            this.searchText = CharSequences.toLowerCase(value);
            super.reload();
            this.openInteractionOverlay();
            return;
        }, widget -> {
            final Window window = this.labyAPI.minecraft().minecraftWindow();
            widget.setFocused(true);
            widget.alignmentX().set(WidgetAlignment.CENTER);
            final Bounds bounds = widget.bounds();
            final float widthValue = window.getScaledWidth() / 100.0f;
            final float heightValue = window.getScaledHeight() / 100.0f;
            float width = widthValue * 25.0f;
            final float maxWidth = 200.0f;
            if (width >= maxWidth) {
                width = maxWidth;
            }
            bounds.setMiddleWidth(width, AbstractWheelInteractionOverlayActivity.SEARCH_BOUNDS);
            bounds.setOuterPosition(window.getScaledWidth() / 2.0f - width / 2.0f, heightValue * 15.0f, AbstractWheelInteractionOverlayActivity.SEARCH_BOUNDS);
            return;
        });
        activity.addCloseRunnable(this::closeInteractionOverlay);
        if (skipFirstCharacter) {
            activity.skipNextCharacter();
        }
        this.displayScreen(activity);
    }
    
    private void playAnimation(final Widget widget, final String id) {
        if (this.skipAnimation) {
            return;
        }
        widget.playAnimation(id);
    }
    
    static {
        SEARCH_BOUNDS = ModifyReason.of("searchBounds");
    }
    
    public static class WheelInteractionKeyListener
    {
        private final LabyAPI labyAPI;
        private final AbstractWheelInteractionOverlayActivity overlayActivity;
        
        public WheelInteractionKeyListener(final LabyAPI labyAPI, final AbstractWheelInteractionOverlayActivity overlayActivity) {
            this.labyAPI = labyAPI;
            this.overlayActivity = overlayActivity;
        }
        
        @Subscribe
        public void onKey(final KeyEvent event) {
            if (!this.overlayActivity.shouldOpenInteractionMenu()) {
                return;
            }
            if (!this.labyAPI.minecraft().isMouseLocked()) {
                return;
            }
            final Key key = event.key();
            final KeyEvent.State state = event.state();
            if (this.overlayActivity.isInteractionOpen()) {
                this.handlePageNavigation(key, state);
                this.overlayActivity.onKey(key, state);
            }
            if (this.overlayActivity.getKeyToOpen() == key) {
                this.handleOverlay(key, state, event.inputType());
            }
        }
        
        private void handleOverlay(final Key key, final KeyEvent.State state, final InputType inputType) {
            if (state == KeyEvent.State.PRESS) {
                final boolean searchActivityOpen = this.overlayActivity.isSearchActivityOpen();
                if (!searchActivityOpen && this.overlayActivity.lastTimeKeyPressed + 300L >= TimeUtil.getMillis()) {
                    this.overlayActivity.handleSearchOverlay(this.shouldSkipFirstCharacter(inputType));
                }
                this.overlayActivity.lastTimeKeyPressed = TimeUtil.getMillis();
            }
            if (state == KeyEvent.State.PRESS) {
                this.overlayActivity.openInteractionOverlay();
                PostProcessors.resetMenuBlur();
            }
            else if (state == KeyEvent.State.UNPRESSED && this.overlayActivity.isInteractionOpen()) {
                this.overlayActivity.closeInteractionOverlay();
            }
        }
        
        private boolean shouldSkipFirstCharacter(final InputType inputType) {
            return !PlatformEnvironment.isAncientOpenGL() && inputType == InputType.CHARACTER;
        }
        
        private void handlePageNavigation(final Key key, final KeyEvent.State state) {
            if (state != KeyEvent.State.PRESS) {
                return;
            }
            final PageNavigator pageNavigator = this.overlayActivity.pageNavigator();
            final int currentPage = pageNavigator.getCurrentPage();
            if (key == MouseButton.LEFT && currentPage > pageNavigator.getMinimumPage()) {
                pageNavigator.switchPage(true);
            }
            else if (key == MouseButton.RIGHT && currentPage < pageNavigator.getMaximumPage()) {
                pageNavigator.switchPage(false);
            }
        }
    }
}
