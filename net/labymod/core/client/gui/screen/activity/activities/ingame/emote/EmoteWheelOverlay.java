// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.ingame.emote;

import net.labymod.api.client.component.builder.StyleableBuilder;
import net.labymod.api.client.gui.screen.activity.util.PageNavigator;
import net.labymod.api.util.math.MathHelper;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.chat.ChatExecutor;
import it.unimi.dsi.fastutil.ints.IntList;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.event.client.input.KeyEvent;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.resources.CompletableResourceLocation;
import java.util.UUID;
import net.labymod.api.mojang.texture.MojangTextureService;
import net.labymod.api.mojang.model.MojangModelService;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.client.component.format.Style;
import net.labymod.core.client.gui.screen.widget.widgets.emote.EmoteSegmentWidget;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.mojang.texture.MojangTextureType;
import net.labymod.api.Laby;
import net.labymod.api.util.CharSequences;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;
import net.labymod.core.client.gui.screen.widget.widgets.emote.EmoteWheelWidget;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.Component;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.core.main.user.shop.emote.animation.EmoteAnimationStorage;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.Minecraft;
import net.labymod.core.main.user.shop.emote.EmoteService;
import java.util.function.Function;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.AbstractWheelInteractionOverlayActivity;

@Link("activity/emote-wheel.lss")
@AutoActivity
public class EmoteWheelOverlay extends AbstractWheelInteractionOverlayActivity
{
    private static final Function<String, String> TRANSLATION_KEY_FACTORY;
    private static final String NO_EMOTES_TRANSLATION_KEY;
    private static final String ALREADY_PLAYING_TRANSLATION_KEY;
    private static final String SELECT_TRANSLATION_KEY;
    private static final String NOT_CONNECTED_TRANSLATION_KEY;
    private static final String PAGE_DAILY_EMOTES_TRANSLATION_KEY;
    private final EmoteService emoteService;
    private final Minecraft minecraft;
    private boolean emotePlaying;
    
    public EmoteWheelOverlay() {
        this.emoteService = LabyMod.references().emoteService();
        this.minecraft = this.labyAPI.minecraft();
    }
    
    @Override
    protected void renderInteractionOverlay(final Stack stack, final MutableMouse mouse, final float partialTicks) {
        final EmoteAnimationStorage animationStorage = this.emoteService.getAnimationStorage(this.minecraft.getClientPlayer());
        final boolean emotePlaying = animationStorage != null && animationStorage.isPlaying();
        if (emotePlaying != this.emotePlaying) {
            this.emotePlaying = emotePlaying;
            this.pageNavigator().setPreviousPageToCurrentPage();
            super.reload();
        }
    }
    
    @Override
    public void initialize(final Parent parent) {
        this.refreshUserData();
        super.initialize(parent);
    }
    
    public void closeInteractionOverlay() {
        this.playEmote(null, false);
        super.closeInteractionOverlay();
    }
    
    @Override
    protected Component createTitleComponent() {
        if (!this.hasEntries()) {
            return Component.translatable(EmoteWheelOverlay.NO_EMOTES_TRANSLATION_KEY, new Component[0]);
        }
        if (this.emotePlaying) {
            return Component.translatable(EmoteWheelOverlay.ALREADY_PLAYING_TRANSLATION_KEY, NamedTextColor.RED);
        }
        return Component.translatable(EmoteWheelOverlay.SELECT_TRANSLATION_KEY, new Component[0]);
    }
    
    @Override
    protected boolean hasEntries() {
        return !EmoteWheelWidget.Storage.INSTANCE.getUserEmotes().isEmpty();
    }
    
    @Override
    protected WheelWidget createWheelWidget() {
        final EmoteWheelWidget wheel = new EmoteWheelWidget(() -> this.pageNavigator().getCurrentPage(), () -> rec$.getSegmentCount());
        wheel.querySupplier(() -> {
            final CharSequence searchText = this.getSearchText();
            if (CharSequences.isEmpty(searchText)) {
                return null;
            }
            else {
                return searchText;
            }
        });
        wheel.segmentSupplier(item -> {
            if (item == null) {
                final WheelWidget.Segment segment = new WheelWidget.Segment();
                segment.setSelectable(false);
                return segment;
            }
            else {
                final MojangModelService modelService = Laby.references().mojangModelService();
                final MojangTextureService textureService = Laby.references().mojangTextureService();
                final UUID uuid = this.labyAPI.getUniqueId();
                final CompletableResourceLocation completable = textureService.getTexture(uuid, MojangTextureType.SKIN);
                final MinecraftServices.SkinVariant variant = textureService.getVariant(completable.getCompleted());
                final Model model = modelService.getPlayerModel(variant, completable.getCompleted());
                new EmoteSegmentWidget(item, model, this.emotePlaying, this.labyAPI.config().ingame().emotes().showCosmeticsInWheel().get(), ((StyleableBuilder<T, Style.Builder>)Style.builder()).color((this.pageNavigator().getCurrentPage() == -1) ? NamedTextColor.GOLD : NamedTextColor.WHITE).build());
                final EmoteSegmentWidget emoteSegmentWidget;
                final EmoteSegmentWidget segment2 = emoteSegmentWidget;
                final WheelWidget.Segment segment;
                completable.addCompletableListener(() -> {
                    final MinecraftServices.SkinVariant loadedVariant = textureService.getVariant(completable.getCompleted());
                    final Model loadedModel = modelService.getPlayerModel(loadedVariant, completable.getCompleted());
                    segment.updateModel(loadedModel);
                    return;
                });
                segment2.addId("emote-wrapper");
                segment2.setSelectable(true);
                return segment2;
            }
        });
        return wheel;
    }
    
    @Override
    protected Key getKeyToOpen() {
        return this.labyAPI.config().hotkeys().emoteWheelKey().get();
    }
    
    @Override
    protected void onInitializeMappedKeys(final Object2IntMap<Key> mappedKeys) {
        mappedKeys.put((Object)Key.NUM1, 0);
        mappedKeys.put((Object)Key.NUM2, 1);
        mappedKeys.put((Object)Key.NUM3, 2);
        mappedKeys.put((Object)Key.NUM4, 3);
        mappedKeys.put((Object)Key.NUM5, 4);
        mappedKeys.put((Object)Key.NUM6, 5);
        mappedKeys.put((Object)Key.NUMPAD1, 0);
        mappedKeys.put((Object)Key.NUMPAD2, 1);
        mappedKeys.put((Object)Key.NUMPAD3, 2);
        mappedKeys.put((Object)Key.NUMPAD4, 3);
        mappedKeys.put((Object)Key.NUMPAD5, 4);
        mappedKeys.put((Object)Key.NUMPAD6, 5);
    }
    
    @Override
    protected void onKey(final Key key, final KeyEvent.State state) {
        final int mappedPosition = this.getMappedPosition(key);
        if (mappedPosition == Integer.MIN_VALUE) {
            return;
        }
        final EmoteItem emoteItem = this.findEmoteByPosition(mappedPosition);
        if (emoteItem == null) {
            return;
        }
        this.playEmote(emoteItem, true);
    }
    
    @Override
    protected boolean shouldOpenInteractionMenu() {
        return this.labyAPI.config().ingame().emotes().emotes().get();
    }
    
    @Override
    protected Widget createPageIndicator() {
        final int currentPage = this.pageNavigator().getCurrentPage();
        this.addOrRemoveDailyId(currentPage - 1 == -1);
        if (currentPage == -1) {
            return ComponentWidget.component(Component.translatable(EmoteWheelOverlay.PAGE_DAILY_EMOTES_TRANSLATION_KEY, NamedTextColor.GOLD));
        }
        return super.createPageIndicator();
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (this.isSearchActivityOpen()) {
            this.playEmote(null, true);
            this.closeScreen();
        }
        return super.mouseClicked(mouse, mouseButton);
    }
    
    private void addOrRemoveDailyId(final boolean add) {
        for (final Widget leftMouseWidget : this.leftMouseWidgets) {
            if (leftMouseWidget != null) {
                if (add) {
                    leftMouseWidget.addId("daily");
                }
                else {
                    leftMouseWidget.removeId("daily");
                }
            }
        }
    }
    
    @Nullable
    private EmoteItem findEmoteByPosition(final int position) {
        final int currentPage = this.pageNavigator().getCurrentPage();
        if (currentPage == -1) {
            return this.findEmote(EmoteWheelWidget.Storage.INSTANCE.getDailyEmotes(), position);
        }
        final int pagePosition = currentPage * this.getSegmentCount() + position;
        return this.findEmote(EmoteWheelWidget.Storage.INSTANCE.getUserEmotes(), pagePosition);
    }
    
    @Nullable
    private EmoteItem findEmote(final IntList list, final int position) {
        if (position >= list.size()) {
            return null;
        }
        return this.emoteService.getEmote(list.getInt(position));
    }
    
    private void playEmote(final EmoteItem forcedEmote, final boolean closeMenu) {
        EmoteItem emote = forcedEmote;
        if (emote == null) {
            emote = this.findSelectedEmote();
        }
        if (emote != null) {
            if (this.emotePlaying) {
                this.emoteService.stopClientEmote();
            }
            else if (!this.emoteService.playClientEmote(emote)) {
                final ChatExecutor chatExecutor = this.labyAPI.minecraft().chatExecutor();
                chatExecutor.displayClientMessage(Component.translatable(EmoteWheelOverlay.NOT_CONNECTED_TRANSLATION_KEY, NamedTextColor.RED));
            }
        }
        if (closeMenu) {
            this.closeInteraction();
        }
    }
    
    @Nullable
    private EmoteItem findSelectedEmote() {
        for (final AbstractWidget<?> child : this.wheelWidget().getChildren()) {
            if (child instanceof final EmoteSegmentWidget emoteSegmentWidget) {
                if (emoteSegmentWidget.isSelectable() && emoteSegmentWidget.isSegmentSelected()) {
                    return emoteSegmentWidget.getEmote();
                }
                continue;
            }
        }
        return null;
    }
    
    private void refreshUserData() {
        final EmoteWheelWidget.Storage storage = EmoteWheelWidget.Storage.INSTANCE;
        storage.refreshUserData();
        final int maxPages = MathHelper.ceil(storage.getUserEmotes().size() / (float)this.getSegmentCount()) - 1;
        final PageNavigator pageNavigator = this.pageNavigator();
        pageNavigator.setMaximumPage(maxPages);
        pageNavigator.setMinimumPage(storage.hasDailyEmotes() ? -1 : 0);
    }
    
    static {
        TRANSLATION_KEY_FACTORY = (s -> "labymod.activity.emoteWheel." + s);
        NO_EMOTES_TRANSLATION_KEY = EmoteWheelOverlay.TRANSLATION_KEY_FACTORY.apply("noEmotes");
        ALREADY_PLAYING_TRANSLATION_KEY = EmoteWheelOverlay.TRANSLATION_KEY_FACTORY.apply("alreadyPlaying");
        SELECT_TRANSLATION_KEY = EmoteWheelOverlay.TRANSLATION_KEY_FACTORY.apply("select");
        NOT_CONNECTED_TRANSLATION_KEY = EmoteWheelOverlay.TRANSLATION_KEY_FACTORY.apply("notConnected");
        PAGE_DAILY_EMOTES_TRANSLATION_KEY = EmoteWheelOverlay.TRANSLATION_KEY_FACTORY.apply("page.dailyEmotes");
    }
}
