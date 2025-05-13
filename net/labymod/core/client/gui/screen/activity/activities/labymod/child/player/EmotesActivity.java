// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player;

import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;
import net.labymod.core.labymodnet.models.ChangeResponse;
import java.util.Iterator;
import net.labymod.core.labymodnet.models.Emote;
import java.util.List;
import java.util.Collections;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.user.GameUser;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.api.event.Phase;
import net.labymod.api.event.labymod.user.UserUpdateDataEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.core.labymodnet.DefaultLabyModNetService;
import net.labymod.core.labymodnet.event.LabyModNetRefreshEvent;
import net.labymod.core.labymodnet.LabyModNetService;
import java.util.UUID;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.emotes.EmoteCustomizationSegmentWidget;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.Laby;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.emotes.EmotePlaceholderSegmentWidget;
import net.labymod.api.client.component.Component;
import net.labymod.api.Textures;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.emotes.VariableListWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.core.client.gui.screen.widget.widgets.emote.EmoteWheelWidget;
import net.labymod.core.main.user.shop.emote.EmoteService;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.PlayerActivity;

@AutoActivity
@Link("activity/player/emotes.lss")
public class EmotesActivity extends PlayerActivity.Child
{
    private static final int SEGMENT_COUNT = 6;
    private static final String IDENTIFIER = "emotes";
    private final EmoteService emoteService;
    private final EmoteOverviewActivity emoteOverviewActivity;
    private int page;
    private int maxPages;
    private EmoteWheelWidget emoteWheelWidget;
    private ButtonWidget backButton;
    private ComponentWidget pageText;
    private ButtonWidget nextButton;
    private long lastUpdate;
    private EmoteItem highlightedEmote;
    
    public EmotesActivity(final PlayerActivity playerActivity, final String translationKeyPrefix) {
        super(playerActivity, translationKeyPrefix + "emotes.", "emotes");
        this.emoteService = LabyMod.references().emoteService();
        this.emoteOverviewActivity = new EmoteOverviewActivity(this);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final VariableListWidget container = new VariableListWidget();
        ((AbstractWidget<Widget>)container).addId("container");
        final ComponentWidget title = ComponentWidget.i18n(this.translationKeyPrefix + "title");
        title.addId("title");
        ((AbstractWidget<ComponentWidget>)container).addChild(title);
        ((AbstractWidget<Widget>)(this.emoteWheelWidget = new EmoteWheelWidget(() -> this.page, () -> 6))).addId("emote-wheel");
        this.emoteWheelWidget.segmentSupplier((index, wheelIndex, item) -> {
            if (item == null) {
                final EmotePlaceholderSegmentWidget segment = new EmotePlaceholderSegmentWidget(Textures.SpriteCommon.DARK_ADD, Component.text("Shop"));
                segment.setPressable(() -> {
                    Laby.references().soundService().play(SoundType.BUTTON_CLICK);
                    this.labyAPI.minecraft().chatExecutor().openUrl("https://www.labymod.net/shop#emotes");
                    return;
                });
                segment.addId("placeholder-segment");
                return segment;
            }
            else {
                final EmoteCustomizationSegmentWidget segment2 = new EmoteCustomizationSegmentWidget(this, index, wheelIndex, item);
                if (item == this.highlightedEmote) {
                    segment2.highlightAt(this.lastUpdate);
                    if (!segment2.isHighlighted()) {
                        this.highlightedEmote = null;
                        this.lastUpdate = 0L;
                    }
                }
                segment2.addId("present-emote-segment");
                segment2.setSelectable(true);
                segment2.setPressable(() -> {
                    Laby.references().soundService().play(SoundType.BUTTON_CLICK);
                    this.emoteOverviewActivity.display(item, wheelIndex, (this.maxPages == 0) ? -1 : this.page);
                    return;
                });
                return segment2;
            }
        });
        final IconWidget listWidget = new IconWidget(Textures.SpriteFlint.DOCUMENT);
        listWidget.addId("emote-list");
        listWidget.setHoverComponent(Component.translatable(this.translationKeyPrefix + "openOverview", new Component[0]));
        listWidget.setPressable(() -> {
            Laby.references().soundService().play(SoundType.BUTTON_CLICK);
            this.emoteOverviewActivity.display();
            return;
        });
        ((AbstractWidget<IconWidget>)this.emoteWheelWidget).addChild(listWidget);
        container.setFlexibleChild(this.emoteWheelWidget);
        final HorizontalListWidget buttonContainer = new HorizontalListWidget();
        ((AbstractWidget<Widget>)buttonContainer).addId("pagination-container");
        ((AbstractWidget<Widget>)(this.backButton = ButtonWidget.i18n(this.translationKeyPrefix + "previousPage"))).addId("back-button");
        this.backButton.setPressable(() -> this.navigate(-1));
        buttonContainer.addEntry(this.backButton);
        (this.pageText = ComponentWidget.text("")).addId("current-page");
        ((AbstractWidget<ComponentWidget>)container).addChild(this.pageText);
        ((AbstractWidget<Widget>)(this.nextButton = ButtonWidget.i18n(this.translationKeyPrefix + "nextPage"))).addId("next-button");
        this.nextButton.setPressable(() -> this.navigate(1));
        buttonContainer.addEntry(this.nextButton);
        ((AbstractWidget<HorizontalListWidget>)container).addChild(buttonContainer);
        ((AbstractWidget<VariableListWidget>)this.document).addChild(container);
        this.navigate(0);
    }
    
    private void navigate(final int offset) {
        final int prevPage = this.page;
        this.page += offset;
        if (this.page < 0) {
            this.page = 0;
        }
        else if (this.page > this.maxPages) {
            this.page = this.maxPages;
        }
        if (offset != 0 && this.page == prevPage) {
            return;
        }
        if (this.backButton != null) {
            this.backButton.setEnabled(this.page > 0);
        }
        if (this.pageText != null) {
            this.pageText.setComponent(Component.translatable(this.translationKeyPrefix + "pagination", Component.text(this.page + 1), Component.text(this.maxPages + 1)));
        }
        if (this.nextButton != null) {
            this.nextButton.setEnabled(this.page < this.maxPages);
        }
        if (offset != 0 && this.emoteWheelWidget != null) {
            this.emoteWheelWidget.refresh();
        }
    }
    
    @Override
    protected void onSessionUpdate(final PlayerActivity.UpdateContext context, final UUID uniqueId) {
        final EmoteWheelWidget.Storage storage = EmoteWheelWidget.Storage.INSTANCE;
        if (storage.getUniqueId() == null || !storage.getUniqueId().equals(uniqueId)) {
            if (context == PlayerActivity.UpdateContext.INITIALIZE && LabyMod.references().labyModNetService().getState() == LabyModNetService.State.OK) {
                storage.refreshUserData();
            }
            else {
                storage.getUserEmotes().clear();
            }
        }
        this.refresh(storage);
    }
    
    @Subscribe
    public void onLabyModNetUpdate(final LabyModNetRefreshEvent event) {
        final DefaultLabyModNetService labyModNetService = (DefaultLabyModNetService)LabyMod.references().labyModNetService();
        if (labyModNetService.getState() == LabyModNetService.State.OK) {
            EmoteWheelWidget.Storage.INSTANCE.refreshUserData();
            this.refresh(EmoteWheelWidget.Storage.INSTANCE);
        }
    }
    
    @Subscribe
    public void onUserUpdateData(final UserUpdateDataEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        final GameUser gameUser = event.gameUser();
        if (!(gameUser instanceof DefaultGameUser) || !gameUser.getUniqueId().equals(this.uniqueId)) {
            return;
        }
        final EmoteWheelWidget.Storage storage = EmoteWheelWidget.Storage.INSTANCE;
        storage.refreshUserData();
        this.refresh(storage);
    }
    
    private void refresh(final EmoteWheelWidget.Storage storage) {
        this.maxPages = (int)Math.ceil(storage.getUserEmotes().size() / 6.0f);
        if (this.maxPages > 0) {
            --this.maxPages;
        }
        this.navigate(0);
        this.reload();
    }
    
    public void playEmote(final EmoteItem emoteItem) {
        this.playerActivity.modelWidget().playEmote(emoteItem);
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        if (super.keyPressed(key, type)) {
            return true;
        }
        if (key == Key.ARROW_LEFT) {
            this.navigate(-1);
            return true;
        }
        if (key == Key.ARROW_RIGHT) {
            this.navigate(1);
            return true;
        }
        return false;
    }
    
    public void stopEmote(final EmoteItem condition) {
        this.playerActivity.modelWidget().stopEmote(condition);
    }
    
    public void swap(final EmoteItem a, final EmoteItem b) {
        this.lastUpdate = TimeUtil.getMillis();
        this.highlightedEmote = b;
        Laby.references().soundService().play(SoundType.HUD_ATTACH);
        Emote firstEmote = null;
        Emote secondEmote = null;
        final GameUser gameUser = LabyMod.references().gameUserService().clientGameUser();
        if (gameUser instanceof final DefaultGameUser defaultGameUser) {
            final List<Integer> emotes = defaultGameUser.getUserData().getEmotes();
            int firstIndex = -1;
            int secondIndex = -1;
            for (int i = 0; i < emotes.size(); ++i) {
                final int id = emotes.get(i);
                if (id == a.getId()) {
                    firstIndex = i;
                }
                else if (id == b.getId()) {
                    secondIndex = i;
                }
            }
            if (firstIndex != -1) {
                if (secondIndex == -1) {
                    emotes.remove(firstIndex);
                    emotes.add(firstIndex, b.getId());
                }
                else {
                    Collections.swap(emotes, firstIndex, secondIndex);
                }
                EmoteWheelWidget.Storage.INSTANCE.refreshUserData();
                if (this.playerActivity.getActiveChild() instanceof EmotesActivity) {
                    this.emoteWheelWidget.refresh();
                }
            }
        }
        for (final Emote emote : LabyMod.references().labyModNetService().getUserItems().getEmotes()) {
            if (emote.getItemId() == a.getId()) {
                firstEmote = emote;
            }
            else {
                if (emote.getItemId() != b.getId()) {
                    continue;
                }
                secondEmote = emote;
            }
        }
        if (firstEmote == null || secondEmote == null) {
            return;
        }
        final int order = firstEmote.getOrder();
        firstEmote.setOrder(secondEmote.getOrder());
        secondEmote.setOrder(order);
        LabyMod.references().labyModNetService().updateEmotes(response -> {});
    }
}
