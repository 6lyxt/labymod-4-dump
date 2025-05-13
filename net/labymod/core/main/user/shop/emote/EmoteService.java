// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.emote;

import java.util.Arrays;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.Laby;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectPlayEmoteEvent;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.core.main.user.shop.AnimationContainer;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.model.animation.meta.AnimationTrigger;
import net.labymod.core.main.user.shop.emote.animation.EmoteAnimationMeta;
import java.util.Iterator;
import net.labymod.api.event.client.render.GameRenderEvent;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.event.client.render.model.entity.player.PlayerCapeRenderEvent;
import net.labymod.api.event.client.render.model.entity.HumanoidModelAnimateEvent;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.event.client.render.model.entity.player.PlayerModelRenderEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.options.Perspective;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.camera.CameraSetupEvent;
import javax.inject.Inject;
import net.labymod.core.main.user.shop.emote.abort.PlayerEmoteAbortWatcher;
import java.util.HashMap;
import net.labymod.api.event.EventBus;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.entity.player.Player;
import net.labymod.core.main.user.shop.emote.abort.EmoteAbortWatcher;
import net.labymod.core.main.user.shop.emote.animation.EmoteAnimationStorage;
import java.util.UUID;
import java.util.Map;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.labymod.api.configuration.labymod.main.laby.ingame.EmotesConfig;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.client.Minecraft;
import net.labymod.core.main.user.shop.emote.abort.AbortAction;
import java.util.Collection;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;
import net.labymod.api.service.Service;

@Singleton
@Referenceable
public class EmoteService extends Service
{
    private static final Collection<AbortAction> DEFAULT_ABORT_ACTIONS;
    private final EmoteLoader emoteLoader;
    private final EmoteRenderer emoteRenderer;
    private final Minecraft minecraft;
    private final LabyConnect labyConnect;
    private final EmotesConfig emotesConfig;
    private final Int2ObjectOpenHashMap<EmoteItem> emotes;
    private final Map<UUID, EmoteAnimationStorage> animationStorages;
    private final EmoteAbortWatcher<Player> emoteAbortWatcher;
    
    @Inject
    public EmoteService(final EmoteLoader emoteLoader, final EmoteRenderer emoteRenderer, final LabyAPI labyAPI, final LabyConnect labyConnect, final EventBus eventBus) {
        this.emoteLoader = emoteLoader;
        this.emoteRenderer = emoteRenderer;
        this.minecraft = labyAPI.minecraft();
        this.labyConnect = labyConnect;
        this.emotesConfig = labyAPI.config().ingame().emotes();
        this.emotes = (Int2ObjectOpenHashMap<EmoteItem>)new Int2ObjectOpenHashMap();
        this.animationStorages = new HashMap<UUID, EmoteAnimationStorage>();
        this.emoteAbortWatcher = new PlayerEmoteAbortWatcher(EmoteService.DEFAULT_ABORT_ACTIONS);
        eventBus.registerListener(this);
    }
    
    @Override
    protected void prepare() {
        this.emoteLoader.loadEmotes(this.emotes);
    }
    
    @Override
    public void onServiceUnload() {
        this.emotes.clear();
    }
    
    @Subscribe
    public void applyCameraAnimation(final CameraSetupEvent event) {
        if (event.phase() == Phase.POST && this.minecraft.options().perspective() == Perspective.FIRST_PERSON) {
            final Player clientPlayer = this.minecraft.getClientPlayer();
            if (clientPlayer == null) {
                return;
            }
            final EmoteAnimationStorage animationStorage = this.getAnimationStorage(clientPlayer);
            if (animationStorage != null && animationStorage.isPlaying()) {
                this.emoteRenderer.animationStorage(animationStorage).player(clientPlayer).transformCamera(event.stack(), this.emotesConfig.firstPersonHeadAnimation().get());
            }
        }
    }
    
    @Subscribe
    public void applyBodyAnimation(final PlayerModelRenderEvent event) {
        if (event.phase() == Phase.PRE) {
            final Stack stack = event.stack();
            final EmoteAnimationStorage animationStorage = this.getAnimationStorage(event.player());
            if (animationStorage != null && animationStorage.isPlaying()) {
                this.emoteRenderer.animationStorage(animationStorage).player(event.player()).model(event.model());
                if (animationStorage.hasProps()) {
                    this.emoteRenderer.renderProps(stack);
                }
                this.emoteRenderer.transformBody(stack);
                if (animationStorage.hasPlayerModel()) {
                    event.setCancelled(true);
                    this.emoteRenderer.renderPlayerModel(stack);
                }
            }
        }
    }
    
    @Subscribe
    public void applyModelAnimation(final HumanoidModelAnimateEvent event) {
        if (!(event.livingEntity() instanceof Player)) {
            return;
        }
        final HumanoidModel model = event.model();
        if (event.phase() == Phase.POST) {
            final Player player = (Player)event.livingEntity();
            final EmoteAnimationStorage animationStorage = this.getAnimationStorage(player);
            if (animationStorage != null && animationStorage.isPlaying()) {
                this.emoteRenderer.animationStorage(animationStorage).player(player).model(model).firstPerson(player.equals(this.minecraft.getClientPlayer()) && this.minecraft.options().perspective() == Perspective.FIRST_PERSON).transformModel();
            }
        }
        else {
            model.reset();
        }
    }
    
    @Subscribe
    public void animateCape(final PlayerCapeRenderEvent event) {
        if (MinecraftVersions.V24w33a.orNewer()) {
            return;
        }
        if (event.phase() != Phase.PRE) {
            return;
        }
        final EmoteAnimationStorage animationStorage = this.getAnimationStorage(event.player());
        if (animationStorage != null && animationStorage.isPlaying()) {
            event.playerModel().getBody().getAnimationTransformation().transform(event.stack());
        }
    }
    
    @Subscribe
    public void checkForPlayingEnd(final GameRenderEvent event) {
        if (event.phase() == Phase.POST) {
            return;
        }
        final float partialTicks = event.getPartialTicks();
        final Iterator<Map.Entry<UUID, EmoteAnimationStorage>> iterator = this.animationStorages.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<UUID, EmoteAnimationStorage> entry = iterator.next();
            final UUID uniqueId = entry.getKey();
            final Player player = this.minecraft.clientWorld().getPlayer(uniqueId).orElse(null);
            final EmoteAnimationStorage animationStorage = entry.getValue();
            this.handleAnimationTriggers(player, animationStorage, partialTicks);
            final boolean self = player == this.minecraft.getClientPlayer();
            final boolean playerAbsent = player == null && (this.minecraft.getClientPacketListener() == null || this.minecraft.getClientPacketListener().getNetworkPlayerInfo(uniqueId) == null);
            final boolean playingEnded = playerAbsent || !animationStorage.isPlaying();
            final boolean playingAborted = animationStorage.isActive() && animationStorage.isPlaying() && (animationStorage.isAbortRequested() || (player != null && this.emoteAbortWatcher.shouldAbort(animationStorage, player, partialTicks)));
            final EmoteItem emote = animationStorage.getEmote();
            if (emote != null && animationStorage.getLastPerspective() != null && this.minecraft.options().perspective() != emote.getPerspective()) {
                animationStorage.setLastPerspective(null);
            }
            if (playingEnded || playingAborted) {
                if (playingAborted) {
                    animationStorage.suspendAllParts();
                    if (self) {
                        this.stopClientEmote();
                    }
                }
                animationStorage.deactivate();
                if (!playingEnded) {
                    continue;
                }
                iterator.remove();
                if (!self || animationStorage.getLastPerspective() == null) {
                    continue;
                }
                this.minecraft.options().setPerspective(animationStorage.getLastPerspective());
            }
            else {
                if (!animationStorage.hasProps()) {
                    continue;
                }
                this.emoteRenderer.animationStorage(animationStorage).animateProps();
            }
        }
    }
    
    private void handleAnimationTriggers(final Player player, final EmoteAnimationStorage animationStorage, final float partialTicks) {
        if (!animationStorage.isActive()) {
            return;
        }
        final EmoteItem emote = animationStorage.getEmote();
        final AnimationContainer animationContainer = emote.animationContainer();
        final AnimationController animationController = animationStorage.animationController();
        if (animationController.isPlaying() && animationController.getPlaying().getMetaDefault(EmoteAnimationMeta.START_ANIMATION, false)) {
            return;
        }
        if (player == null) {
            animationContainer.handleAnimationTrigger(AnimationTrigger.IDLE, animationController, null);
            return;
        }
        final boolean crouching = player.isCrouching();
        final boolean moving = player.getWalkingSpeed(partialTicks) > 0.1;
        if (crouching && !animationStorage.isLastSneaking()) {
            animationContainer.handleAnimationTrigger(AnimationTrigger.START_SNEAKING, animationController, player);
        }
        else if (!crouching && animationStorage.isLastSneaking()) {
            animationContainer.handleAnimationTrigger(AnimationTrigger.STOP_SNEAKING, animationController, player);
        }
        animationStorage.setLastSneaking(crouching);
        if (moving && !animationStorage.isLastMoving()) {
            animationContainer.handleAnimationTrigger(AnimationTrigger.START_MOVING, animationController, player);
        }
        else if (!moving && animationStorage.isLastMoving()) {
            animationContainer.handleAnimationTrigger(AnimationTrigger.STOP_MOVING, animationController, player);
        }
        animationStorage.setLastMoving(moving);
        final AnimationTrigger trigger = AnimationTrigger.getMovingOrIdle(moving, crouching);
        animationContainer.handleAnimationTrigger(trigger, animationController, player);
    }
    
    @Subscribe
    public void playLabyConnectEmote(final LabyConnectPlayEmoteEvent event) {
        if (!this.minecraft.isIngame()) {
            return;
        }
        if (event.getEmoteId() == -1) {
            final EmoteAnimationStorage animationStorage = this.getAnimationStorage(event.getUniqueId());
            if (animationStorage != null) {
                animationStorage.requestAbort();
            }
            return;
        }
        final EmoteItem emote = this.getEmote(event.getEmoteId());
        if (emote == null) {
            return;
        }
        if (emote.isDraft() && !Laby.references().gameUserService().clientGameUser().visibleGroup().isStaffOrCosmeticCreator()) {
            return;
        }
        final UUID uniqueId = event.getUniqueId();
        final EmoteAnimationStorage animationStorage2 = this.getOrCreateAnimationStorage(uniqueId);
        animationStorage2.playEmote(emote);
        if (this.emotesConfig.emotePerspective().get() && emote.getPerspective() != null) {
            final Player clientPlayer = this.minecraft.getClientPlayer();
            if (clientPlayer != null && uniqueId.equals(clientPlayer.getUniqueId())) {
                animationStorage2.setLastPerspective(this.minecraft.options().perspective());
                this.minecraft.options().setPerspective(emote.getPerspective());
            }
        }
    }
    
    public boolean playClientEmote(final EmoteItem emote) {
        final boolean emoteDebug = this.emotesConfig.emoteDebug().get();
        if (emoteDebug && this.minecraft.getClientPlayer() != null) {
            final EmoteAnimationStorage animationStorage = this.getOrCreateAnimationStorage(this.minecraft.getClientPlayer());
            animationStorage.playEmote(emote);
            if (this.emotesConfig.emotePerspective().get() && emote.getPerspective() != null) {
                animationStorage.setLastPerspective(this.minecraft.options().perspective());
                this.minecraft.options().setPerspective(emote.getPerspective());
            }
        }
        if (!this.labyConnect.isAuthenticated() || this.labyConnect.getSession() == null) {
            return emoteDebug;
        }
        this.labyConnect.getSession().playEmote((short)emote.getId());
        return true;
    }
    
    public void playClientEmote(final UUID uuid, final EmoteItem item) {
        this.getOrCreateAnimationStorage(uuid).playEmote(item);
    }
    
    public void stopClientEmote() {
        final boolean emoteDebug = this.emotesConfig.emoteDebug().get();
        if (emoteDebug && this.minecraft.getClientPlayer() != null) {
            final EmoteAnimationStorage animationStorage = this.getAnimationStorage(this.minecraft.getClientPlayer());
            if (animationStorage != null) {
                animationStorage.requestAbort();
            }
        }
        if (!this.labyConnect.isAuthenticated() || this.labyConnect.getSession() == null) {
            return;
        }
        this.labyConnect.getSession().playEmote((short)(-1));
    }
    
    public EmoteItem getEmote(final int id) {
        return (EmoteItem)this.emotes.get(id);
    }
    
    public Int2ObjectOpenHashMap<EmoteItem> getEmotes() {
        return this.emotes;
    }
    
    @Nullable
    public EmoteAnimationStorage getAnimationStorage(final UUID uniqueId) {
        return this.animationStorages.get(uniqueId);
    }
    
    @Nullable
    public EmoteAnimationStorage getAnimationStorage(final Player player) {
        return this.getAnimationStorage(player.getUniqueId());
    }
    
    public EmoteAnimationStorage getOrCreateAnimationStorage(final UUID uniqueId) {
        return this.animationStorages.computeIfAbsent(uniqueId, key -> new EmoteAnimationStorage());
    }
    
    public EmoteAnimationStorage getOrCreateAnimationStorage(final Player player) {
        return this.getOrCreateAnimationStorage(player.getUniqueId());
    }
    
    static {
        DEFAULT_ABORT_ACTIONS = Arrays.asList(AbortAction.MOVEMENT, AbortAction.DAMAGE, AbortAction.SWIMMING, AbortAction.FLYING);
    }
}
