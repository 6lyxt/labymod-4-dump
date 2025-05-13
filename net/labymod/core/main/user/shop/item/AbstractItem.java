// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item;

import net.labymod.api.util.io.web.request.AbstractRequest;
import net.labymod.api.client.render.model.animation.meta.AnimationTrigger;
import net.labymod.core.main.user.shop.item.geometry.BlockBenchLoader;
import net.labymod.core.main.user.shop.item.renderer.immediate.ImmediateItemRenderer;
import net.labymod.core.main.user.shop.item.model.OffsetVector;
import java.util.Iterator;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.types.InputStreamRequest;
import net.labymod.api.util.io.web.request.Callback;
import java.io.InputStream;
import java.io.IOException;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.client.render.model.animation.meta.AnimationMeta;
import net.labymod.core.main.user.shop.item.geometry.animation.AnimationLoader;
import java.util.Objects;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.core.main.user.shop.item.geometry.AnimationStorage;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import java.util.Collection;
import net.labymod.core.main.user.shop.bridge.ItemTags;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;
import net.labymod.api.Constants;
import net.labymod.core.main.user.shop.item.items.PetItem;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.configuration.labymod.main.laby.ingame.CosmeticsConfig;
import net.labymod.api.user.group.Group;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.user.shop.CloakPriority;
import net.labymod.api.client.entity.player.PlayerClothes;
import net.labymod.core.main.user.DefaultGameUserService;
import net.labymod.api.Laby;
import net.labymod.core.main.LabyMod;
import java.util.ArrayList;
import net.labymod.core.main.user.shop.item.geometry.GeometryLoader;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.core.main.user.shop.item.geometry.effect.PhysicData;
import net.labymod.api.client.options.MainHand;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.api.user.GameUser;
import net.labymod.core.main.user.shop.item.renderer.ItemRenderer;
import net.labymod.api.LabyAPI;
import net.labymod.core.main.user.shop.AnimationContainer;
import net.labymod.api.client.render.model.Model;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.tag.TaggedObject;
import net.labymod.core.main.user.shop.item.geometry.effect.ItemEffect;
import net.labymod.api.user.GameUserService;
import java.util.concurrent.ExecutorService;
import java.util.List;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.tag.Taggable;
import net.labymod.api.util.Disposable;
import net.labymod.core.main.user.shop.Purchasable;

public abstract class AbstractItem implements Purchasable, Disposable, Taggable
{
    private static final Logging LOGGER;
    private static final int MAX_COSMETIC_DISTANCE = 5;
    private static final int PRIORITY_MULTIPLIER = 3;
    private final List<Runnable> disposeListeners;
    private final int listId;
    protected final ItemDetails itemDetails;
    private final ExecutorService itemExecutorService;
    private final GameUserService gameUserService;
    protected final ItemEffect itemEffect;
    private final TaggedObject taggedObject;
    @Nullable
    protected String name;
    protected Model model;
    protected final AnimationContainer animationContainer;
    protected LabyAPI labyAPI;
    private boolean resolved;
    private ItemRenderer itemRenderer;
    protected GameUser user;
    protected ItemMetadata itemMetadata;
    protected Player player;
    protected PlayerModel playerModel;
    protected boolean firstPerson;
    protected MainHand hand;
    protected PhysicData physicData;
    @Deprecated
    private boolean shopItem;
    private int priorityLevel;
    private Position position;
    protected final ItemRendererContext itemRendererContext;
    private final FloatMatrix4 modelViewMatrix;
    private final FloatMatrix4 projectionMatrix;
    private GeometryLoader geometryLoader;
    
    public AbstractItem(final int listId, final ItemDetails itemDetails) {
        this.disposeListeners = new ArrayList<Runnable>();
        this.taggedObject = new TaggedObject();
        this.animationContainer = new AnimationContainer();
        this.resolved = false;
        this.position = Position.NONE;
        this.modelViewMatrix = FloatMatrix4.newIdentity();
        this.projectionMatrix = FloatMatrix4.newIdentity();
        this.listId = listId;
        this.itemDetails = itemDetails;
        this.labyAPI = LabyMod.getInstance();
        this.gameUserService = Laby.references().gameUserService();
        this.itemExecutorService = ((DefaultGameUserService)this.gameUserService).itemService().getItemExecutorService();
        this.itemEffect = new ItemEffect(this);
        this.itemRendererContext = new ItemRendererContext(this);
    }
    
    public AbstractItem itemRenderer(final ItemRenderer itemRenderer) {
        this.itemRenderer = itemRenderer;
        return this;
    }
    
    public AbstractItem user(final GameUser user) {
        this.user = user;
        return this;
    }
    
    public AbstractItem player(final Player player, final PlayerModel model) {
        this.player = player;
        this.playerModel = model;
        return this;
    }
    
    public AbstractItem itemMetadata(final ItemMetadata metadata) {
        this.itemMetadata = metadata;
        return this;
    }
    
    public AbstractItem firstPerson(final boolean firstPerson) {
        this.firstPerson = firstPerson;
        return this;
    }
    
    public AbstractItem hand(final MainHand hand) {
        this.hand = hand;
        return this;
    }
    
    public AbstractItem physicData(final PhysicData physicData) {
        this.physicData = physicData;
        return this;
    }
    
    public boolean shouldRender(final Player player) {
        if (this.firstPerson && this.isInvisibleInFirstPersonContext()) {
            return false;
        }
        final Group group = this.gameUserService.clientGameUser().visibleGroup();
        if (this.itemDetails.isDraft() && !group.isStaffOrCosmeticCreator()) {
            return false;
        }
        final boolean hideCape = this.itemDetails.isHideCape();
        if (hideCape && !player.isShownModelPart(PlayerClothes.CAPE)) {
            return false;
        }
        if (this.itemDetails.isHiddenWhileWearingElytra() && player.isWearingElytra()) {
            return false;
        }
        final CosmeticsConfig cosmetics = this.labyAPI.config().ingame().cosmetics();
        if (hideCape && cosmetics.cloakPriority().get() != CloakPriority.LABYMOD && player.getCloakTexture() != null) {
            return false;
        }
        if (hideCape && !player.isWearingElytra()) {
            return true;
        }
        if (this.labyAPI.minecraft().getClientPlayer() == null || this.labyAPI.gfxRenderPipeline().renderEnvironmentContext().isScreenContext()) {
            return true;
        }
        if (cosmetics.hideCosmeticsInDistance().get() && this.priorityLevel != 0) {
            final int distanceExt = 5 - Math.min(5, this.priorityLevel);
            final double dist = Math.pow(cosmetics.hideAfterBlocks().get() + distanceExt * 3, 2.0);
            return MathHelper.distanceSquared(player, this.labyAPI.minecraft().getClientPlayer()) <= dist;
        }
        return this.isInRenderDistance();
    }
    
    public final void renderItem(final Stack stack, final int packedLight, final int packedOverlay, final float partialTicks) {
        this.render(stack, packedLight, packedOverlay, partialTicks);
    }
    
    protected abstract void render(final Stack p0, final int p1, final int p2, final float p3);
    
    public abstract AbstractItem copy();
    
    public void tick() {
    }
    
    public boolean canBeRendered() {
        return this.model != null && this.model.getTextureLocation() != null;
    }
    
    public ItemDetails itemDetails() {
        return this.itemDetails;
    }
    
    public int getIdentifier() {
        return this.itemDetails.getIdentifier();
    }
    
    public void resolveItemData(final DefaultGameUser user, final ItemMetadata data) {
        if (this instanceof final PetItem petItem) {
            petItem.getPetDataStorage(user).setRightShoulder(data.isRightSide());
        }
        if (this.resolved) {
            return;
        }
        this.resolved = true;
        if (!this.isRemote()) {
            return;
        }
        this.itemExecutorService.submit(() -> {
            try {
                final int identifier = this.getIdentifier();
                this.executeRequest(Constants.LegacyUrls.REMOTE_COSMETICS_ANIMATION, identifier, this::loadAnimations);
                this.executeRequest(Constants.LegacyUrls.REMOTE_COSMETICS_GEOMETRY, identifier, this::loadModel);
            }
            catch (final Exception exception) {
                AbstractItem.LOGGER.error("Failed to load cosmetic {} for user {}", this.getIdentifier(), user.getUniqueId());
                AbstractItem.LOGGER.error(exception.getMessage(), exception);
            }
        });
    }
    
    public void renderModel(final Stack stack, final int packedLight, final int packedOverlay) {
        this.renderModel(stack, packedLight, packedOverlay, true);
    }
    
    public void renderModelWithEffects(final Stack stack, final int packedLight, final int packedOverlay, final boolean rightSide) {
        if (this.isImmediate()) {
            this.applyEffects(this.player, this.playerModel, this.itemMetadata, rightSide, GeometryEffect.Type.BUFFER_CREATION);
        }
        this.applyEffects(this.player, this.playerModel, this.itemMetadata, rightSide, GeometryEffect.Type.PHYSIC);
        this.renderModel(stack, packedLight, packedOverlay);
    }
    
    public void renderModel(final Stack stack, final int packedLight, final int packedOverlay, final boolean enableScale) {
        if (this.model == null) {
            return;
        }
        if (enableScale) {
            stack.push();
            final float scale = (float)this.itemDetails.getScale();
            stack.scale(scale);
        }
        this.modelViewMatrix.set(stack.getProvider().getPosition());
        this.projectionMatrix.set(this.labyAPI.gfxRenderPipeline().getProjectionMatrix());
        this.itemRendererContext.update(stack, this.firstPerson, packedLight, packedOverlay, this.hasTag(ItemTags.WORLD));
        this.itemRenderer.render(this, this.itemRendererContext);
        if (enableScale) {
            stack.pop();
        }
    }
    
    public void unload() {
        if (!this.resolved) {
            return;
        }
        this.model = null;
        this.animationContainer.updateAnimations(null);
    }
    
    public AnimationStorage getAnimationStorage(@NotNull final GameUser user) {
        return ((DefaultGameUser)user).getUserItemStorage().getAnimationStorage(this);
    }
    
    public ExecutorService getItemExecutorService() {
        return this.itemExecutorService;
    }
    
    public Model getModel() {
        return this.model;
    }
    
    public float getNameTagOffset() {
        return this.itemDetails.getNameTagOffset();
    }
    
    @Override
    public String getName() {
        return this.itemDetails.getName() + "/" + this.itemDetails.getIdentifier();
    }
    
    public void applyEffects(final Player player, final PlayerModel playerModel, final ItemMetadata metadata, final boolean rightSide, final GeometryEffect.Type type) {
        this.itemEffect.apply(player, playerModel, this.physicData, metadata, rightSide, type);
    }
    
    public int getPriorityLevel() {
        return this.priorityLevel;
    }
    
    public void setPriorityLevel(final int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }
    
    private void loadAnimations(final Response<WebInputStream> response) {
        final WebInputStream webInputStream = response.getNullable();
        try {
            if (response.hasException()) {
                throw response.exception();
            }
            Objects.requireNonNull(webInputStream);
            AnimationLoader animationLoader = new AnimationLoader(webInputStream.getInputStream());
            final AnimationLoader finalAnimationLoader;
            animationLoader = (finalAnimationLoader = animationLoader.load(AnimationMeta.defaults()));
            ThreadSafe.executeOnRenderThread(() -> this.animationContainer.updateAnimations(finalAnimationLoader.getAnimations()));
        }
        catch (final IOException exception) {
            AbstractItem.LOGGER.error("Animation for item {} could not be loaded. ({})", this, exception.getMessage());
        }
    }
    
    private void loadModel(final Response<WebInputStream> response) {
        final WebInputStream webInputStream = response.getNullable();
        try {
            if (response.hasException()) {
                throw response.exception();
            }
            Objects.requireNonNull(webInputStream);
            final InputStream inputStream = webInputStream.getInputStream();
            this.geometryLoader = new GeometryLoader(inputStream);
            ThreadSafe.executeOnRenderThread(this::initializeModel);
        }
        catch (final IOException exception) {
            AbstractItem.LOGGER.error("Model for item {} could not be loaded. ({})", this, exception.getMessage());
        }
    }
    
    private void executeRequest(final String url, final int identifier, final Callback<WebInputStream> response) {
        ((AbstractRequest<WebInputStream, R>)((AbstractRequest<T, InputStreamRequest>)((AbstractRequest<T, InputStreamRequest>)Request.ofInputStream()).url(url, new Object[] { identifier })).async(false)).execute(response);
    }
    
    @Override
    public int hashCode() {
        return this.itemDetails.getIdentifier();
    }
    
    public Position getPosition() {
        return this.position;
    }
    
    public void setPosition(final Position position) {
        this.position = position;
    }
    
    public int getListId() {
        return this.listId;
    }
    
    public boolean isInvisibleInFirstPersonContext() {
        return true;
    }
    
    public FloatMatrix4 modelViewMatrix() {
        return this.modelViewMatrix;
    }
    
    public FloatMatrix4 projectionMatrix() {
        return this.projectionMatrix;
    }
    
    @NotNull
    public ItemEffect itemEffect() {
        return this.itemEffect;
    }
    
    public void reset() {
        this.position = Position.NONE;
    }
    
    @Override
    public TaggedObject taggedObject() {
        return this.taggedObject;
    }
    
    @Override
    public void addDisposeListener(final Runnable listener) {
        this.disposeListeners.add(listener);
    }
    
    @Override
    public void dispose() {
        for (final Runnable disposeListener : this.disposeListeners) {
            disposeListener.run();
        }
    }
    
    @Deprecated
    public boolean isRemote() {
        return true;
    }
    
    @Deprecated
    public boolean isShopItem() {
        return this.shopItem;
    }
    
    @Deprecated
    public void setShopItem(final boolean shopItem) {
        this.shopItem = shopItem;
    }
    
    protected void onModelLoad() {
    }
    
    protected void translateOffset(final Stack stack) {
        this.translateOffset(stack, false);
    }
    
    protected void translateOffset(final Stack stack, final boolean translateShoulder) {
        final OffsetVector offset = this.itemMetadata.getOffset();
        if (offset == null) {
            return;
        }
        final float modelScale = this.labyAPI.renderPipeline().renderConstants().modelScale();
        final int side = translateShoulder ? (this.itemMetadata.isRightSide() ? -1 : 1) : 1;
        stack.translate((float)(offset.getX() * side) / modelScale, (float)(-offset.getY()) / modelScale, (float)offset.getZ() / modelScale);
    }
    
    protected boolean isInRenderDistance() {
        return true;
    }
    
    protected boolean isImmediate() {
        return this.itemRenderer instanceof ImmediateItemRenderer;
    }
    
    public void initializeModel() {
        if (this.geometryLoader == null) {
            return;
        }
        final BlockBenchLoader blockBenchLoader = this.geometryLoader.toBlockBenchLoader(meta -> meta.shouldGenerateBoneIds(true).shouldSplitModel(true));
        this.model = blockBenchLoader.getModel();
        this.itemEffect.loadEffects(blockBenchLoader.getEffects());
        this.onModelLoad();
    }
    
    public AnimationTrigger getAnimationTrigger() {
        return this.animationContainer.getTrigger();
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
    
    public enum Position
    {
        LEFT, 
        RIGHT, 
        NONE;
    }
}
