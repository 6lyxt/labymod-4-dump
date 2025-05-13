// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.bridge;

import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.core.main.user.shop.item.geometry.AnimationStorage;
import net.labymod.core.main.user.shop.item.items.pet.PetDataStorage;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.render.model.Model;
import net.labymod.core.main.user.shop.item.ItemDetails;
import net.labymod.core.main.user.shop.item.texture.ItemTexture;
import net.labymod.core.main.profiler.RenderProfiler;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.Textures;
import net.labymod.core.main.user.shop.item.model.type.ItemType;
import net.labymod.core.main.user.shop.item.model.AttachmentPoint;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.core.main.user.shop.item.items.minecraft.MinecraftItem;
import net.labymod.core.main.debug.ErrorWrapper;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.client.entity.player.DummyPlayer;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.tag.Tag;
import net.labymod.core.main.user.shop.item.AbstractItem;
import java.util.List;
import net.labymod.core.main.user.GameUserData;
import net.labymod.api.user.GameUser;
import net.labymod.core.main.user.shop.item.items.pet.WalkingPet;
import net.labymod.core.main.user.GameUserItem;
import net.labymod.core.main.user.DefaultGameUser;
import java.util.Iterator;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.resources.ReleaseTextureEvent;
import javax.inject.Inject;
import net.labymod.api.client.entity.player.tag.renderer.TagRenderer;
import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.core.main.user.shop.item.renderer.retained.RetainedItemRenderer;
import net.labymod.core.main.user.shop.item.renderer.immediate.ImmediateItemRenderer;
import java.util.HashMap;
import net.labymod.api.Laby;
import java.util.HashSet;
import net.labymod.api.event.EventBus;
import net.labymod.api.client.entity.player.tag.TagRegistry;
import net.labymod.api.client.options.MainHand;
import net.labymod.api.client.gfx.pipeline.renderer.shader.ShaderPipeline;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.user.GameUserService;
import net.labymod.api.configuration.labymod.main.laby.ingame.CosmeticsConfig;
import net.labymod.core.main.user.shop.item.geometry.effect.PhysicData;
import net.labymod.core.main.user.shop.item.renderer.ItemRenderer;
import net.labymod.api.user.shop.RenderMode;
import java.util.Map;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Set;
import net.labymod.api.tag.TaggedObject;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;
import net.labymod.api.tag.Taggable;

@Singleton
@Referenceable
public class ShopItemLayer implements Taggable
{
    private static final Logging LOGGER;
    private static final float SNEAK_OFFSET = 0.2f;
    private static final float LEG_SNEAK_OFFSET = 0.1875f;
    private final TaggedObject taggedObject;
    private final Set<ResourceLocation> releasedTextures;
    private final LabyAPI labyAPI;
    private final Map<RenderMode, ItemRenderer> itemRenderers;
    private final PhysicData physicData;
    private final CosmeticsConfig cosmeticsConfig;
    private final GameUserService gameUserService;
    private final LabyConnect labyConnect;
    private final ShaderPipeline shaderPipeline;
    private ItemFilter itemFilter;
    private boolean renderedCustomMinecraftItem;
    private MainHand hand;
    private boolean storeStates;
    
    @Inject
    public ShopItemLayer(final TagRegistry tagRegistry, final LabyAPI labyAPI, final EventBus eventBus, final ShaderPipeline shaderPipeline) {
        this.taggedObject = new TaggedObject();
        this.releasedTextures = new HashSet<ResourceLocation>();
        this.itemFilter = ItemFilters.defaultFilter();
        this.storeStates = false;
        this.labyAPI = labyAPI;
        this.shaderPipeline = shaderPipeline;
        this.cosmeticsConfig = this.labyAPI.config().ingame().cosmetics();
        this.gameUserService = Laby.references().gameUserService();
        this.labyConnect = Laby.references().labyConnect();
        this.itemRenderers = new HashMap<RenderMode, ItemRenderer>();
        this.registerItemRenderer(new ImmediateItemRenderer(this.labyAPI));
        this.registerItemRenderer(new RetainedItemRenderer(this.labyAPI));
        this.physicData = new PhysicData();
        tagRegistry.register("item-offset", PositionType.BELOW_NAME, new ItemOffsetTag());
        eventBus.registerListener(this);
    }
    
    @Subscribe
    public void onTextureRelease(final ReleaseTextureEvent event) {
        this.releasedTextures.add(event.location());
    }
    
    @Subscribe
    public void onTick(final GameTickEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        if (!this.cosmeticsConfig.renderCosmetics().get()) {
            return;
        }
        final ClientWorld level = this.labyAPI.minecraft().clientWorld();
        for (final Player player : level.getPlayers()) {
            this.tick(player);
        }
    }
    
    private void tick(final Player player) {
        final DefaultGameUser user = (DefaultGameUser)player.gameUser();
        final GameUserData userData = user.getUserData();
        if (userData == null || userData.getItems().isEmpty()) {
            return;
        }
        final boolean walkingPetsVisible = this.cosmeticsConfig.walkingPets().get();
        this.physicData.resetWalkingPetIndex();
        final List<GameUserItem> items = userData.getItems();
        for (final GameUserItem userItem : items) {
            final AbstractItem item = userItem.item();
            if (item instanceof WalkingPet && !walkingPetsVisible) {
                continue;
            }
            item.player(player, player.playerModel());
            item.user(user);
            this.guardItemTick(item);
        }
    }
    
    private void guardItemTick(final AbstractItem item) {
        try {
            item.tick();
        }
        catch (final Throwable throwable) {
            ShopItemLayer.LOGGER.error("Error occurred while ticking item", throwable);
        }
    }
    
    public ShopItemLayer applyTag(final Tag tag) {
        this.setTag(tag);
        return this;
    }
    
    public ShopItemLayer applyTag(final Tag... tags) {
        this.setTag(tags);
        return this;
    }
    
    public ShopItemLayer filter(final ItemFilter itemFilter) {
        this.itemFilter = itemFilter;
        return this;
    }
    
    public ShopItemLayer hand(final MainHand hand) {
        this.hand = hand;
        return this;
    }
    
    public void render(final Stack stack, final Player player, final PlayerModel playerModel, final int packedLight, final float partialTicks) {
        this.renderedCustomMinecraftItem = false;
        final boolean firstPerson = this.hasTag(ItemTags.FIRST_PERSON);
        if (player.isInvisible() && !firstPerson) {
            this.resetValues();
            return;
        }
        if (!(player instanceof DummyPlayer) && !this.isSelfOrFriend(player)) {
            this.resetValues();
            return;
        }
        final RenderMode renderMode = this.cosmeticsConfig.renderMode().get();
        final int maxItems = this.cosmeticsConfig.maxCosmeticsPerPlayer().get();
        final ItemRenderer itemRenderer = this.itemRenderers.get(this.shaderPipeline.hasActiveShaderPack() ? RenderMode.IMMEDIATE : renderMode);
        if (itemRenderer == null) {
            this.resetValues();
            return;
        }
        final DefaultGameUser user = (DefaultGameUser)player.gameUser();
        final GFXBridge gfx = this.labyAPI.gfxRenderPipeline().gfx();
        final GameUserData userData = user.getUserData();
        if (userData == null || userData.getItems().isEmpty()) {
            this.resetValues();
            return;
        }
        if (!DynamicBackgroundController.isEnabled()) {
            this.labyAPI.gfxRenderPipeline().setProjectionMatrix();
        }
        this.physicData.update(player, playerModel, partialTicks);
        itemRenderer.begin(user);
        final List<GameUserItem> items = userData.getItems();
        for (int renderedCosmetics = 0, i = 0; i < items.size() && renderedCosmetics < maxItems; ++i) {
            final GameUserItem entry = items.get(i);
            final AbstractItem item = entry.item();
            if (!this.itemFilter.filter(item)) {
                if (this.hasTag(ItemTags.WORLD)) {
                    item.setTag(ItemTags.WORLD);
                }
                item.firstPerson(firstPerson);
                if (item.shouldRender(player)) {
                    final boolean rendered = ErrorWrapper.wrap(() -> this.renderItem(stack, player, playerModel, item, entry.itemMetadata(), user, packedLight, partialTicks, itemRenderer), false, () -> "Item Rendering (" + item.getName());
                    if (rendered) {
                        ++renderedCosmetics;
                        if (item instanceof MinecraftItem) {
                            this.renderedCustomMinecraftItem = true;
                        }
                        if (!this.storeStates) {
                            this.storeStates = true;
                        }
                    }
                }
            }
        }
        itemRenderer.end(user);
        this.resetValues();
        if (this.storeStates) {
            this.storeStates = false;
            gfx.invalidateBlaze3DBuffers();
            gfx.restoreBlaze3DStates();
        }
    }
    
    public boolean isRenderedCustomMinecraftItem() {
        return this.renderedCustomMinecraftItem;
    }
    
    private void resetValues() {
        this.hand = null;
        this.itemFilter = ItemFilters.defaultFilter();
        this.clearTags();
    }
    
    private boolean renderItem(final Stack stack, final Player player, final PlayerModel playerModel, final AbstractItem item, final ItemMetadata metadata, final DefaultGameUser user, final int packedLight, final float partialTicks, final ItemRenderer itemRenderer) {
        final ItemTexture texture = user.getUserItemStorage().getTexture(item);
        if (texture == null) {
            return false;
        }
        item.resolveItemData(user, metadata);
        final boolean firstPerson = this.hasTag(ItemTags.FIRST_PERSON);
        final ItemDetails details = metadata.getDetails();
        if (firstPerson && details.getAttachmentPoint() != AttachmentPoint.ARM && details.getType() != ItemType.WALKING_PET) {
            return false;
        }
        texture.update();
        final ResourceLocation resourceLocation = item.isRemote() ? texture.getOrResolveTexture(player, metadata, this::released) : Textures.WHITE;
        if (resourceLocation == null) {
            return false;
        }
        final Model model = item.getModel();
        if (model == null) {
            return false;
        }
        if (!this.storeStates) {
            this.labyAPI.gfxRenderPipeline().gfx().storeBlaze3DStates();
        }
        model.setTextureLocation(resourceLocation);
        stack.push();
        final boolean sneaking = player.isCrouching();
        if (sneaking && !firstPerson && PlatformEnvironment.isAncientOpenGL()) {
            switch (item.itemDetails().getAttachmentPoint()) {
                case BODY:
                case HEAD:
                case ARM: {
                    stack.translate(0.0f, 0.2f, 0.0f);
                    break;
                }
                case LEG: {
                    stack.translate(0.0f, 0.1875f, 0.0f);
                    break;
                }
            }
        }
        itemRenderer.apply(player, playerModel, metadata, partialTicks, item, resourceLocation);
        final TaggedObject taggedObject = this.taggedObject();
        if (taggedObject.hasTag(ItemTags.ACTIVITY)) {
            item.setTag(ItemTags.ACTIVITY);
        }
        item.user(user).itemMetadata(metadata).firstPerson(firstPerson).hand(this.hand).player(player, playerModel).physicData(this.physicData).itemRenderer(itemRenderer).renderItem(stack, packedLight, 0, partialTicks);
        itemRenderer.finish();
        RenderProfiler.increaseRenderedCosmeticCount();
        stack.pop();
        item.clearTags();
        return true;
    }
    
    public void resetAnimations(final GameUser user, final boolean screenContext) {
        final RenderEnvironmentContext context = Laby.labyAPI().gfxRenderPipeline().renderEnvironmentContext();
        final boolean prevScreenContext = context.isScreenContext();
        context.setScreenContext(screenContext);
        final DefaultGameUser gameUser = (DefaultGameUser)user;
        for (final GameUserItem userItem : gameUser.getUserData().getItems()) {
            final AbstractItem item = userItem.item();
            item.getAnimationStorage(user).getController().stop();
            final PetDataStorage pet = gameUser.getUserItemStorage().getPetDataStorage(item);
            if (pet != null) {
                pet.getAnimationController().stop();
            }
            final AnimationStorage storage = gameUser.getUserItemStorage().getAnimationStorage(item);
            if (storage != null) {
                storage.getController().stop();
            }
        }
        context.setScreenContext(prevScreenContext);
    }
    
    private void registerItemRenderer(final ItemRenderer renderer) {
        this.itemRenderers.put(renderer.renderMode(), renderer);
    }
    
    private boolean isSelfOrFriend(final Player player) {
        final boolean isSelf = player.gameUser() == this.gameUserService.clientGameUser();
        if (isSelf) {
            return true;
        }
        final boolean onlyFriends = this.cosmeticsConfig.onlyFriends().get();
        if (!onlyFriends) {
            return true;
        }
        final LabyConnectSession session = this.labyConnect.getSession();
        if (session == null) {
            return false;
        }
        final Friend friend = session.getFriend(player.getUniqueId());
        return friend != null;
    }
    
    private boolean released(final ResourceLocation key) {
        if (this.releasedTextures.isEmpty()) {
            return false;
        }
        if (this.releasedTextures.contains(key)) {
            this.releasedTextures.remove(key);
            return true;
        }
        return false;
    }
    
    @Override
    public TaggedObject taggedObject() {
        return this.taggedObject;
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
