// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.ingame.interaction;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.event.Subscribe;
import net.labymod.api.labynet.models.SocialMediaEntry;
import net.labymod.api.client.entity.Entity;
import net.labymod.core.client.gui.screen.widget.widgets.interaction.social.SocialPointWidget;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.LabyAPI;
import net.labymod.core.client.gui.screen.widget.widgets.interaction.bullet.BulletPointWidget;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.util.math.Transformation;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gfx.pipeline.post.processors.PostProcessors;
import net.labymod.api.configuration.labymod.main.laby.ingame.MenuBlurConfig;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.network.NetworkPlayerInfo;
import java.util.UUID;
import net.labymod.api.client.network.ClientPacketListener;
import java.util.Iterator;
import net.labymod.api.configuration.labymod.AutoTextConfigAccessor;
import java.util.List;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.entity.player.interaction.BulletPoint;
import java.util.Collection;
import net.labymod.core.client.entity.player.interaction.autotext.AutoTextBulletPoint;
import net.labymod.api.client.chat.autotext.AutoTextEntry;
import net.labymod.api.client.gui.screen.Parent;
import javax.inject.Inject;
import net.labymod.api.client.render.model.ModelService;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.core.client.gui.screen.widget.widgets.interaction.social.SocialPointRendererWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ModelWidget;
import net.labymod.core.client.gui.screen.widget.widgets.interaction.bullet.BulletPointRendererWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.entity.player.interaction.InteractionMenuRegistry;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.types.AbstractInteractionOverlayActivity;

@AutoActivity
@Link("activity/overlay/interaction-menu.lss")
public class InteractionMenuOverlay extends AbstractInteractionOverlayActivity implements InteractionAnimationController
{
    private static final float OPEN_DURATION = 100.0f;
    private static final float COLLAPSE_DURATION = 300.0f;
    private final InteractionMenuRegistry registry;
    private final Model headModel;
    private final AnimationController animationController;
    private final InteractionMenuKeyListener interactionMenuKeyListener;
    private Player player;
    private DivWidget wrapperWidget;
    private BulletPointRendererWidget rendererWidget;
    private ModelWidget headWidget;
    private SocialPointRendererWidget socialBarWidget;
    private boolean focusSocialBar;
    private long timeSocialBarTransitionStart;
    
    @Inject
    public InteractionMenuOverlay() {
        super(Laby.references().cameraLockController());
        this.registry = Laby.references().interactionMenuRegistry();
        final ModelService modelService = Laby.references().modelService();
        this.headModel = modelService.loadBlockBenchModel(Constants.Resources.MODEL_HEAD);
        this.animationController = modelService.createAnimationController();
        this.interactionMenuKeyListener = new InteractionMenuKeyListener(this);
        this.labyAPI.eventBus().registerListener(this.interactionMenuKeyListener);
    }
    
    @Override
    public void onOpenScreen() {
        super.onOpenScreen();
        this.labyAPI.eventBus().registerListener(this.interactionMenuKeyListener);
    }
    
    @Override
    public void onCloseScreen() {
        super.onCloseScreen();
        this.labyAPI.eventBus().unregisterListener(this.interactionMenuKeyListener);
    }
    
    @Override
    public void closeInteraction() {
        super.closeInteraction();
        this.player = null;
    }
    
    @Override
    public void tick() {
        if (this.player == null) {
            return;
        }
        super.tick();
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        if (this.player == null) {
            return;
        }
        (this.wrapperWidget = new DivWidget()).addId("wrapper");
        final List<BulletPoint> bulletPoints = this.registry.values();
        bulletPoints.removeIf(bulletPoint -> !bulletPoint.isVisible(this.player));
        final AutoTextConfigAccessor autoTextConfig = Laby.references().chatProvider().autoTextConfigAccessor();
        for (final AutoTextEntry entry : autoTextConfig.getEntries()) {
            if (entry.displayInInteractionMenu().get()) {
                if (!entry.isActiveOnCurrentServer()) {
                    continue;
                }
                bulletPoints.add(new AutoTextBulletPoint(entry));
            }
        }
        ((AbstractWidget<Widget>)(this.rendererWidget = new BulletPointRendererWidget(this, bulletPoints))).addId("bullet-point-renderer");
        ((AbstractWidget<BulletPointRendererWidget>)this.wrapperWidget).addChild(this.rendererWidget);
        final ClientPacketListener listener = this.labyAPI.minecraft().getClientPacketListener();
        if (listener != null) {
            final UUID id = this.player.profile().getUniqueId();
            final NetworkPlayerInfo info = listener.getNetworkPlayerInfo(id);
            if (info != null) {
                this.headModel.setCompletableTexture(info.getSkin().getCompletableSkinTexture());
            }
        }
        (this.headWidget = new ModelWidget(this.headModel, this.animationController, 8.0f, 8.0f)).addId("head");
        final ComponentWidget nameWidget = ComponentWidget.text(this.player.getName());
        nameWidget.addId("name");
        ((AbstractWidget<ComponentWidget>)this.headWidget).addChild(nameWidget);
        ((AbstractWidget<ModelWidget>)this.wrapperWidget).addChild(this.headWidget);
        ((AbstractWidget<Widget>)(this.socialBarWidget = new SocialPointRendererWidget(this, this.player))).addId("social-bar");
        ((AbstractWidget<SocialPointRendererWidget>)this.wrapperWidget).addChild(this.socialBarWidget);
        ((AbstractWidget<DivWidget>)this.document).addChild(this.wrapperWidget);
    }
    
    @Override
    public void render(final ScreenContext context) {
        PostProcessors.processMenuBlur(MenuBlurConfig.ScreenType.INTERACTION_WHEEL, context.getTickDelta());
        super.render(context);
    }
    
    public void renderInteractionOverlay(final Stack stack, final MutableMouse mouse, final float partialTicks) {
        final Bounds bounds = this.bounds();
        final float mouseOffsetX = mouse.getX() - bounds.getCenterX();
        final float mouseOffsetY = mouse.getY() - bounds.getCenterY();
        final float rotationX = (float)Math.toRadians(MathHelper.clamp(mouseOffsetY, -80.0f, 80.0f));
        final float rotationY = (float)Math.toRadians(MathHelper.clamp(mouseOffsetX, -80.0f, 80.0f));
        final float scale = (this.rendererWidget == null) ? 0.0f : this.getOpenProgress();
        final ModelPart head = this.headModel.getPart("head");
        final Transformation headTransform = head.getAnimationTransformation();
        headTransform.setScale(scale);
        headTransform.setRotation(rotationX, -rotationY, 0.0f);
        if (this.headWidget != null) {
            this.headWidget.setScale(scale);
        }
        final boolean inSocialTriggerZone = mouseOffsetY > this.getRadius() - 10.0f;
        if (inSocialTriggerZone && !this.focusSocialBar) {
            this.focusSocialBar = true;
            this.timeSocialBarTransitionStart = TimeUtil.getMillis();
        }
        if (mouseOffsetY < 0.0f && this.focusSocialBar) {
            this.focusSocialBar = false;
            this.timeSocialBarTransitionStart = TimeUtil.getMillis();
        }
    }
    
    private boolean openInteraction(final Player player) {
        this.player = player;
        return super.openInteraction();
    }
    
    @Override
    protected float getRadius() {
        return this.wrapperWidget.bounds().getHeight() / 2.0f;
    }
    
    @Override
    public float getOpenProgress() {
        final long timePassed = TimeUtil.getMillis() - this.rendererWidget.getLastInitialTime();
        return (float)(-Math.exp(-timePassed / 100.0f)) + 1.0f;
    }
    
    @Override
    public float getSocialTransitionProgress() {
        final long timePassedSinceHiddenChanged = TimeUtil.getMillis() - this.timeSocialBarTransitionStart;
        float hideProgress = MathHelper.clamp(timePassedSinceHiddenChanged / 300.0f, 0.0f, 1.0f);
        hideProgress = (this.focusSocialBar ? hideProgress : (1.0f - hideProgress));
        return (float)CubicBezier.EASE_IN_OUT.curve(hideProgress);
    }
    
    @Override
    public float getBulletRotationOffset(final BulletPointWidget.Side side) {
        final float size = this.socialBarWidget.getChildren().size() / 10.0f;
        final float offset = (float)(0.7853981633974483 * size);
        final float progress = offset * this.getSocialTransitionProgress();
        return (side == BulletPointWidget.Side.LEFT) ? progress : (-progress);
    }
    
    public static class InteractionMenuKeyListener
    {
        private final InteractionMenuOverlay overlay;
        private final LabyAPI labyAPI;
        
        public InteractionMenuKeyListener(final InteractionMenuOverlay overlay) {
            this.overlay = overlay;
            this.labyAPI = Laby.labyAPI();
        }
        
        @Subscribe
        public void onKey(final KeyEvent event) {
            if (!this.labyAPI.minecraft().isMouseLocked()) {
                return;
            }
            if (event.key() != this.labyAPI.config().hotkeys().interactionMenuKey().get()) {
                return;
            }
            if (event.state() == KeyEvent.State.PRESS) {
                final Entity targetEntity = this.labyAPI.minecraft().getTargetEntity();
                if (!(targetEntity instanceof Player)) {
                    return;
                }
                final Player player = (Player)targetEntity;
                this.overlay.openInteraction(player);
                PostProcessors.resetMenuBlur();
            }
            if (this.overlay.isInteractionOpen() && event.state() == KeyEvent.State.UNPRESSED) {
                final BulletPointWidget bulletPoint = this.overlay.rendererWidget.getNearestPoint();
                if (bulletPoint == null) {
                    final SocialPointWidget socialPoint = this.overlay.socialBarWidget.getNearestPoint();
                    if (socialPoint != null) {
                        final SocialMediaEntry entry = socialPoint.entry();
                        if (entry.getService().equals("discord")) {
                            socialPoint.entry().copyName();
                        }
                        else {
                            socialPoint.entry().open();
                        }
                    }
                }
                else {
                    bulletPoint.bulletPoint().execute(this.overlay.player);
                }
                this.overlay.closeInteraction();
            }
        }
    }
}
