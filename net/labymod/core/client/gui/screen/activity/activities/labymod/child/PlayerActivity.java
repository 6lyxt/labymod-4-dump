// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.HashMap;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.api.client.resources.AnimatedResourceLocation;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.icon.AnimatedIcon;
import net.labymod.core.labymodnet.event.LabyModNetRefreshEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.session.Session;
import net.labymod.api.event.client.session.SessionUpdateEvent;
import java.util.Iterator;
import java.util.function.Predicate;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.FlexibleContentEntry;
import net.labymod.core.labyconnect.session.ApplyTextureController;
import net.labymod.api.labynet.models.textures.Skin;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.Parent;
import java.util.concurrent.TimeUnit;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.util.ColorUtil;
import java.util.UUID;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.listener.CosmeticsItemTextureListener;
import net.labymod.core.client.gui.screen.widget.widgets.customization.PlayerModelWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.SkinActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.EmotesActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.CosmeticsActivity;
import net.labymod.api.client.resources.CompletableResourceLocation;
import java.util.Map;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Links;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Activity;

@AutoActivity
@Links({ @Link("activity/player/player.lss"), @Link("activity/player/player-child.lss") })
public class PlayerActivity extends Activity
{
    public static final String TRANSLATION_KEY_PREFIX = "labymod.activity.playerCustomization.";
    private static final String MODEL_VISIBLE_KEY = "--model-visible";
    private static final String STATIC_BUTTON_ID = "static-button";
    private static final String MODEL_VISIBILITY_ID = "model-visibility-toggle";
    private static final long TIMEOUT = 300000L;
    public static final Map<String, CompletableResourceLocation> SKIN_CACHE;
    private final CosmeticsActivity cosmeticsActivity;
    private final EmotesActivity emotesActivity;
    private final SkinActivity skinActivity;
    private final ScreenRendererWidget screenRenderer;
    private final PlayerModelWidget modelWidget;
    private final CosmeticsItemTextureListener itemTextureListener;
    protected Task task;
    private FlexibleContentWidget containerWrapper;
    private FlexibleContentWidget headerWidget;
    private DivWidget modelWrapper;
    private DivWidget modelExtraContainer;
    private IconWidget loadingIconWidget;
    private boolean modelVisible;
    private int modelColor;
    private UUID uniqueId;
    
    public PlayerActivity() {
        this.itemTextureListener = new CosmeticsItemTextureListener();
        this.modelColor = ColorUtil.WHITE_ARGB;
        this.cosmeticsActivity = new CosmeticsActivity(this, "labymod.activity.playerCustomization.");
        this.emotesActivity = new EmotesActivity(this, "labymod.activity.playerCustomization.");
        this.skinActivity = new SkinActivity(this, "labymod.activity.playerCustomization.");
        (this.screenRenderer = new ScreenRendererWidget()).addId("screen-renderer");
        this.screenRenderer.displayScreen(this.cosmeticsActivity);
        this.screenRenderer.addPostDisplayListener(screen -> this.refreshHeaderButtons());
        this.modelVisible = true;
        this.modelWidget = new PlayerModelWidget();
        this.uniqueId = this.modelWidget.getPlayer().getUniqueId();
        this.task = Task.builder(() -> {
            this.modelColor = ColorFormat.ARGB32.pack(25, 25, 25, 255);
            this.loadingIconWidget.setVisible(true);
        }).delay(200L, TimeUnit.MILLISECONDS).build();
    }
    
    public void displayChild(final Class<? extends Child> childClass) {
        if (childClass == CosmeticsActivity.class) {
            this.displayChild(this.cosmeticsActivity);
        }
        else if (childClass == EmotesActivity.class) {
            this.displayChild(this.emotesActivity);
        }
        else if (childClass == SkinActivity.class) {
            this.displayChild(this.skinActivity);
        }
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        if (this.uniqueId != this.labyAPI.getUniqueId()) {
            this.setSkinTexture(this.uniqueId = this.labyAPI.getUniqueId());
            this.modelWidget.setPlayerUniqueId(this.uniqueId);
        }
        ((AbstractWidget<Widget>)(this.containerWrapper = new FlexibleContentWidget())).addId("container-wrapper");
        final FlexibleContentWidget container = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)container).addId("container");
        ((AbstractWidget<Widget>)(this.headerWidget = new FlexibleContentWidget())).addId("header");
        this.headerWidget.addFlexibleContent(this.createButton(this.cosmeticsActivity));
        this.headerWidget.addFlexibleContent(this.createButton(this.emotesActivity));
        this.headerWidget.addFlexibleContent(this.createButton(this.skinActivity));
        final ButtonWidget refreshLabyMod = ButtonWidget.icon(Textures.SpriteCommon.REFRESH);
        ((AbstractWidget<Widget>)refreshLabyMod).addId("refresh-labymod", "static-button");
        refreshLabyMod.setHoverComponent(Component.translatable("labymod.activity.playerCustomization.refreshLabyMod.description", new Component[0]));
        refreshLabyMod.setPressable(() -> {
            final Child activeChild = this.getActiveChild();
            if (activeChild != null) {
                activeChild.onLabyModRefresh(UpdateContext.INITIALIZE);
            }
            refreshLabyMod.setEnabled(false);
            Laby.labyAPI().refresh();
            return;
        });
        this.headerWidget.addContent(refreshLabyMod);
        this.refreshHeaderButtons();
        container.addContent(this.headerWidget);
        container.addFlexibleContent(this.screenRenderer);
        this.containerWrapper.addFlexibleContent(container);
        (this.modelWrapper = new DivWidget()).addId("model-wrapper");
        this.setModelVisible(this.modelVisible);
        this.setSkinApplyable(this.modelWidget.isModified());
        this.modelWidget.addId("model");
        this.modelWidget.draggable().set(true);
        ((AbstractWidget<PlayerModelWidget>)this.modelWrapper).addChild(this.modelWidget);
        (this.modelExtraContainer = new DivWidget()).addId("model-extra-container");
        ((AbstractWidget<DivWidget>)this.modelWrapper).addChild(this.modelExtraContainer);
        final DivWidget loadingContainer = new DivWidget();
        loadingContainer.addId("model-loading-container");
        ((AbstractWidget<DivWidget>)this.modelWrapper).addChild(loadingContainer);
        ((AbstractWidget<IconWidget>)loadingContainer).addChild(this.createLoadingIcon());
        this.containerWrapper.addContent(this.modelWrapper);
        final DivWidget stencilWrapper = new DivWidget();
        stencilWrapper.addId("container-stencil-wrapper");
        ((AbstractWidget<FlexibleContentWidget>)stencilWrapper).addChild(this.containerWrapper);
        ((AbstractWidget<DivWidget>)this.document).addChild(stencilWrapper);
    }
    
    @Override
    public void render(final ScreenContext context) {
        if (this.modelWidget != null) {
            this.modelWidget.modelColor().set(this.modelColor);
        }
        super.render(context);
    }
    
    @Override
    public void onCloseScreen() {
        super.onCloseScreen();
        final TextureRepository textureRepository = Laby.references().textureRepository();
        PlayerActivity.SKIN_CACHE.forEach((uuid, skin) -> {
            final ResourceLocation completed = skin.getCompleted();
            if (completed != null) {
                textureRepository.releaseTexture(completed);
            }
            return;
        });
        PlayerActivity.SKIN_CACHE.clear();
    }
    
    public void displayScreen(final Child child) {
        this.screenRenderer.displayScreen(child);
    }
    
    public PlayerModelWidget modelWidget() {
        return this.modelWidget;
    }
    
    private ButtonWidget createButton(final Child child) {
        final ButtonWidget buttonWidget = ButtonWidget.i18n(child.translationKeyPrefix + "name");
        ((AbstractWidget<Widget>)buttonWidget).addId(child.groupIdentifier);
        buttonWidget.setPressable(() -> this.displayChild(child));
        return buttonWidget;
    }
    
    private void setModelVisible(final boolean visible) {
        this.modelVisible = visible;
        if (this.modelWrapper != null) {
            this.modelWrapper.setVariable("--model-visible", visible);
        }
        if (this.containerWrapper != null) {
            this.containerWrapper.updateBounds();
        }
    }
    
    private void setSkinApplyable(final boolean applyable) {
        if (this.modelExtraContainer == null) {
            return;
        }
        this.modelExtraContainer.removeChildIf(widget -> widget.hasId("apply-skin-button"));
        if (!applyable) {
            return;
        }
        final String translationKeyPrefix = this.skinActivity.translationKeyPrefix + "apply.";
        final ButtonWidget applySkinButton = ButtonWidget.i18n(translationKeyPrefix + "upload");
        ((AbstractWidget<Widget>)applySkinButton).addId("apply-skin-button");
        if (!this.labyAPI.minecraft().sessionAccessor().isPremium()) {
            applySkinButton.setEnabled(false);
            applySkinButton.setHoverComponent(Component.translatable(translationKeyPrefix + "noPremium", new Component[0]));
        }
        applySkinButton.setPressable(() -> {
            if (!this.modelWidget.isModified()) {
                return;
            }
            else {
                applySkinButton.setEnabled(false);
                applySkinButton.updateComponent(Component.translatable(translationKeyPrefix + "uploading", new Component[0]));
                final Skin previewedSkin = this.modelWidget.getPreviewedSkin();
                final ApplyTextureController applyTextureController = LabyMod.references().applyTextureController();
                applyTextureController.uploadSkinAsync(previewedSkin.skinVariant(), new MinecraftServices.SkinPayload(previewedSkin.getDownloadUrl()), result -> applySkinButton.updateComponent(Component.translatable(translationKeyPrefix + "uploading" + (result.isPresent() ? "Success" : "Failed"), new Component[0])));
                return;
            }
        });
        ((AbstractWidget<ButtonWidget>)this.modelExtraContainer).addChildInitialized(applySkinButton);
    }
    
    public DivWidget getModelExtraContainer() {
        return this.modelExtraContainer;
    }
    
    public void addWidgetToModelWrapper(final Widget widget) {
        this.modelExtraContainer.addChildInitialized(widget);
    }
    
    private void refreshHeaderButtons() {
        final Child activeChild = this.getActiveChild();
        if (activeChild == null || (!activeChild.allowModifiedModel() && this.modelWidget.isModified())) {
            this.setSkinTexture(this.uniqueId);
        }
        if (activeChild != null && !this.uniqueId.equals(activeChild.uniqueId)) {
            activeChild.onSessionUpdate(UpdateContext.INITIALIZE, this.uniqueId);
            activeChild.uniqueId = this.uniqueId;
        }
        if (this.modelExtraContainer != null) {
            final Predicate<Widget> predicate = (activeChild == null) ? (widget -> true) : activeChild.getModelContainerClearPredicate();
            this.modelExtraContainer.removeChildIf(predicate);
        }
        if (this.headerWidget == null) {
            return;
        }
        final ScreenInstance screen = this.screenRenderer.getScreen();
        if (!(screen instanceof Child)) {
            return;
        }
        final String childIdentifier = ((Child)screen).groupIdentifier;
        for (final FlexibleContentEntry child : this.headerWidget.getChildren()) {
            final Widget widget = child.childWidget();
            if (widget instanceof final ButtonWidget buttonWidget) {
                if (widget.hasId("static-button")) {
                    continue;
                }
                final boolean active = widget.hasId(childIdentifier);
                buttonWidget.setEnabled(!active);
                buttonWidget.setActive(active);
            }
        }
    }
    
    @Subscribe
    public void onSessionUpdate(final SessionUpdateEvent event) {
        final Session session = event.newSession();
        if (session == null || !session.hasUniqueId()) {
            return;
        }
        final UUID uniqueId = session.getUniqueId();
        if (uniqueId != this.uniqueId) {
            this.setSkinTexture(this.uniqueId = uniqueId);
            this.modelWidget.setPlayerUniqueId(uniqueId);
        }
        final Child activeChild = this.getActiveChild();
        if (activeChild != null && uniqueId != activeChild.uniqueId) {
            activeChild.uniqueId = uniqueId;
            activeChild.onSessionUpdate(UpdateContext.EVENT, uniqueId);
        }
    }
    
    @Subscribe
    public void onLabyModRefresh(final LabyModNetRefreshEvent event) {
        if (this.headerWidget == null) {
            return;
        }
        final FlexibleContentEntry refreshLabyModButton = this.headerWidget.findFirstChildIf(entry -> entry.hasId("refresh-labymod"));
        if (refreshLabyModButton != null) {
            final Widget childWidget = refreshLabyModButton.childWidget();
            if (childWidget instanceof final ButtonWidget widget) {
                widget.setEnabled(false);
                Task.builder(() -> widget.setEnabled(true)).delay(5L, TimeUnit.SECONDS).build().executeOnRenderThread();
            }
        }
    }
    
    public Child getActiveChild() {
        final ScreenInstance screen = this.screenRenderer.getScreen();
        if (!(screen instanceof Child)) {
            return null;
        }
        return (Child)screen;
    }
    
    public void setSkinTexture(final Skin skin) {
        this.modelWidget.setModel(skin);
        this.setSkinApplyable(this.modelWidget.isModified());
    }
    
    public void setSkinTexture(final UUID uniqueId) {
        this.modelWidget.setModel(uniqueId);
        this.setSkinApplyable(this.modelWidget.isModified());
    }
    
    private void displayChild(final Child child) {
        if (this.screenRenderer.getScreen() != child) {
            this.screenRenderer.displayScreen(child);
        }
    }
    
    private IconWidget createLoadingIcon() {
        final AnimatedResourceLocation.Builder builder = this.labyAPI.renderPipeline().resources().resourceLocationFactory().builder();
        final AnimatedResourceLocation spinnerLocation = builder.resourceLocations("labymod", "textures/spinner/spinner", 30).delay(33L).build();
        (this.loadingIconWidget = new IconWidget(AnimatedIcon.of(spinnerLocation))).addId("model-loading-icon");
        this.loadingIconWidget.setVisible(false);
        return this.loadingIconWidget;
    }
    
    public CosmeticsItemTextureListener itemTextureListener() {
        return this.itemTextureListener;
    }
    
    @Override
    public void tick() {
        super.tick();
        final boolean timeout = this.itemTextureListener.isTimeout(300000L);
        if (timeout) {
            this.resetLoading();
        }
        else {
            final CosmeticsItemTextureListener.State state = this.itemTextureListener.state();
            if (state == CosmeticsItemTextureListener.State.BEGIN) {
                this.task.executeOnRenderThread();
            }
            else {
                this.resetLoading();
            }
        }
    }
    
    private void resetLoading() {
        this.modelColor = ColorUtil.WHITE_ARGB;
        if (this.loadingIconWidget != null) {
            this.loadingIconWidget.setVisible(false);
        }
    }
    
    static {
        SKIN_CACHE = new HashMap<String, CompletableResourceLocation>();
    }
    
    public enum UpdateContext
    {
        INITIALIZE, 
        EVENT;
    }
    
    @Link("activity/player/player-child.lss")
    public abstract static class Child extends Activity
    {
        protected final PlayerActivity playerActivity;
        protected final String translationKeyPrefix;
        protected final String groupIdentifier;
        protected UUID uniqueId;
        
        protected Child(final PlayerActivity playerActivity, final String translationKeyPrefix, final String groupIdentifier) {
            this.playerActivity = playerActivity;
            this.translationKeyPrefix = translationKeyPrefix;
            this.groupIdentifier = groupIdentifier;
            ((Document)this.document).setLazy(true);
        }
        
        protected boolean allowModifiedModel() {
            return false;
        }
        
        protected void onSessionUpdate(final UpdateContext context, final UUID uniqueId) {
        }
        
        protected void onLabyModRefresh(final UpdateContext context) {
        }
        
        protected Predicate<Widget> getModelContainerClearPredicate() {
            return widget -> true;
        }
        
        public PlayerActivity playerActivity() {
            return this.playerActivity;
        }
        
        public String getGroupIdentifier() {
            return this.groupIdentifier;
        }
        
        public String getTranslationKeyPrefix() {
            return this.translationKeyPrefix;
        }
    }
}
