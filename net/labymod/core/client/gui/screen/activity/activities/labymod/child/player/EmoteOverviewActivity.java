// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.event.Subscribe;
import net.labymod.core.labymodnet.event.LabyModNetRefreshEvent;
import java.util.UUID;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Iterator;
import java.util.List;
import net.labymod.core.labymodnet.models.UserItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.labymod.core.labymodnet.LabyModNetService;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.emotes.EmoteOverviewWidget;
import java.util.Locale;
import net.labymod.core.labymodnet.models.Emote;
import java.util.ArrayList;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.emotes.DummySegmentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;
import net.labymod.core.main.user.shop.emote.EmoteService;
import net.labymod.core.labymodnet.DefaultLabyModNetService;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.PlayerActivity;

@AutoActivity
@Link("activity/player/emotes-overview.lss")
public class EmoteOverviewActivity extends PlayerActivity.Child
{
    private final DefaultLabyModNetService labyModNetService;
    private final EmoteService emoteService;
    private final EmotesActivity emotesActivity;
    private String searchQuery;
    private boolean showUnowned;
    private int selectedSlot;
    private int page;
    private EmoteItem selectedEmote;
    
    public EmoteOverviewActivity(final EmotesActivity emotesActivity) {
        super(emotesActivity.playerActivity(), emotesActivity.getTranslationKeyPrefix() + "overview.", emotesActivity.getGroupIdentifier());
        this.searchQuery = "";
        this.showUnowned = false;
        this.selectedSlot = -1;
        this.labyModNetService = (DefaultLabyModNetService)LabyMod.references().labyModNetService();
        this.emoteService = LabyMod.references().emoteService();
        this.emotesActivity = emotesActivity;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final FlexibleContentWidget content = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)content).addId("content");
        if (this.selectedEmote != null) {
            final FlexibleContentWidget sideBar = new FlexibleContentWidget();
            ((AbstractWidget<Widget>)sideBar).addId("sidebar");
            final ComponentWidget titleWidget = ComponentWidget.i18n(this.translationKeyPrefix + "swapTitle", this.selectedEmote.getName());
            titleWidget.addId("title");
            sideBar.addContent(titleWidget);
            final DivWidget wheelWrapper = new DivWidget();
            wheelWrapper.addId("wheel-wrapper");
            final WheelWidget wheel = new WheelWidget();
            ((AbstractWidget<Widget>)wheel).addId("wheel");
            for (int i = 0; i < 6; ++i) {
                wheel.addSegment(new DummySegmentWidget(i == this.selectedSlot).addId("segment-" + i));
            }
            ((AbstractWidget<WheelWidget>)wheelWrapper).addChild(wheel);
            if (this.page != -1) {
                final ComponentWidget page = ComponentWidget.i18n(this.translationKeyPrefix + "page", this.page + 1);
                page.addId("page");
                ((AbstractWidget<ComponentWidget>)wheelWrapper).addChild(page);
            }
            sideBar.addContent(wheelWrapper);
            sideBar.addFlexibleContent(new DivWidget());
            final ButtonWidget cancelButton = ButtonWidget.i18n("labymod.ui.button.cancel");
            ((AbstractWidget<Widget>)cancelButton).addId("cancel-button");
            cancelButton.setPressable(() -> this.playerActivity.displayScreen(this.emotesActivity));
            sideBar.addContent(cancelButton);
            content.addContent(sideBar);
        }
        final FlexibleContentWidget listWrapper = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)listWrapper).addId("list-wrapper");
        final VerticalListWidget<Widget> list = new VerticalListWidget<Widget>();
        list.addId("list");
        this.fill(list);
        final HorizontalListWidget header = new HorizontalListWidget();
        ((AbstractWidget<Widget>)header).addId("header");
        if (this.selectedEmote == null) {
            final ButtonWidget cancelButton2 = ButtonWidget.icon(Textures.SpriteCommon.BACK_BUTTON);
            ((AbstractWidget<Widget>)cancelButton2).addId("cancel-button");
            cancelButton2.setPressable(() -> this.playerActivity.displayScreen(this.emotesActivity));
            header.addEntry(cancelButton2);
            final ComponentWidget titleWidget2 = ComponentWidget.i18n(this.translationKeyPrefix + "genericTitle");
            titleWidget2.addId("title");
            header.addEntry(titleWidget2);
        }
        final TextFieldWidget searchField = new TextFieldWidget();
        searchField.addId("search-field");
        if (this.selectedEmote == null) {
            searchField.addId("full-size");
        }
        searchField.setText(this.searchQuery);
        searchField.placeholder(Component.translatable("labymod.ui.textfield.search", new Component[0]));
        searchField.updateListener(query -> {
            if (query.trim().isEmpty() && !this.searchQuery.trim().isEmpty()) {
                this.searchQuery = "";
            }
            else {
                this.searchQuery = query;
            }
            this.fill(list);
            return;
        });
        header.addEntry(searchField);
        final CheckBoxWidget showUnownedCheckBox = new CheckBoxWidget();
        showUnownedCheckBox.addId("show-unowned");
        showUnownedCheckBox.setState(this.showUnowned ? CheckBoxWidget.State.CHECKED : CheckBoxWidget.State.UNCHECKED);
        showUnownedCheckBox.setPressable(() -> {
            this.showUnowned = !this.showUnowned;
            this.fill(list);
            return;
        });
        final ButtonWidget showUnownedButton = ButtonWidget.i18n(this.translationKeyPrefix + "showUnowned");
        ((AbstractWidget<Widget>)showUnownedButton).addId("show-unowned-button");
        final ButtonWidget buttonWidget = showUnownedButton;
        final CheckBoxWidget obj = showUnownedCheckBox;
        Objects.requireNonNull(obj);
        buttonWidget.setActionListener(obj::onPress);
        showUnownedButton.addEntry(showUnownedCheckBox);
        header.addEntry(showUnownedButton);
        listWrapper.addContent(header);
        listWrapper.addFlexibleContent(new ScrollWidget(list).addId("player-scroll"));
        content.addFlexibleContent(listWrapper);
        ((AbstractWidget<FlexibleContentWidget>)this.document).addChild(content);
    }
    
    private void fill(final VerticalListWidget<Widget> list) {
        list.getChildren().clear();
        final UserItems userItems = this.labyModNetService.getUserItems();
        List<Emote> userEmotes;
        if (userItems == null) {
            userEmotes = new ArrayList<Emote>();
        }
        else {
            userEmotes = userItems.getEmotes();
        }
        final List<EmoteItem> emotes = new ArrayList<EmoteItem>();
        String query = this.searchQuery.trim().toLowerCase(Locale.ROOT);
        if (query.length() == 0) {
            query = null;
        }
        for (final Emote userEmote : userEmotes) {
            final EmoteItem emote = this.emoteService.getEmote(userEmote.getItemId());
            if (emote != null) {
                if (query != null && !emote.getName().toLowerCase(Locale.ROOT).contains(query)) {
                    continue;
                }
                emotes.add(emote);
            }
        }
        emotes.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        for (final EmoteItem ownedEmote : emotes) {
            final EmoteOverviewWidget emoteOverviewWidget = new EmoteOverviewWidget(this.emotesActivity, ownedEmote);
            if (this.selectedEmote != null) {
                if (ownedEmote.getId() == this.selectedEmote.getId()) {
                    emoteOverviewWidget.addId("unselectable");
                }
                else {
                    emoteOverviewWidget.setPressable(() -> {
                        this.emotesActivity.swap(this.selectedEmote, ownedEmote);
                        this.playerActivity.displayScreen(this.emotesActivity);
                        return;
                    });
                }
            }
            if (((Document)this.document).isInitialized()) {
                list.addChildInitialized(emoteOverviewWidget, false);
            }
            else {
                list.addChild(emoteOverviewWidget, false);
            }
        }
        if (list.getChildren().isEmpty()) {
            final LabyModNetService.State state = this.labyModNetService.getState();
            String translationKey = null;
            if (state == LabyModNetService.State.NOT_CONNECTED) {
                translationKey = "notConnected";
            }
            else if (state == LabyModNetService.State.LOADING || state == LabyModNetService.State.ACCOUNT_CHANGED) {
                translationKey = "loading";
            }
            else if (state == LabyModNetService.State.ERROR) {
                translationKey = "error";
            }
            if (translationKey != null) {
                final ComponentWidget componentWidget = ComponentWidget.i18n(this.translationKeyPrefix + translationKey);
                componentWidget.addId("no-emotes");
                if (((Document)this.document).isInitialized()) {
                    list.addChildInitialized(componentWidget, false);
                }
                else {
                    list.addChild(componentWidget, false);
                }
            }
        }
        if (!this.showUnowned) {
            this.appendNoEmotesComponent(list);
            list.sortChildren();
            list.updateBounds();
            return;
        }
        emotes.clear();
        for (final Int2ObjectMap.Entry<EmoteItem> emoteItemEntry : this.emoteService.getEmotes().int2ObjectEntrySet()) {
            if (query != null && !((EmoteItem)emoteItemEntry.getValue()).getName().toLowerCase(Locale.ROOT).contains(query)) {
                continue;
            }
            final int id = emoteItemEntry.getIntKey();
            boolean found = false;
            for (final Emote userEmote2 : userEmotes) {
                if (userEmote2.getItemId() == id) {
                    found = true;
                    break;
                }
            }
            if (found) {
                continue;
            }
            final EmoteItem value = (EmoteItem)emoteItemEntry.getValue();
            if (value.isDraft()) {
                continue;
            }
            emotes.add(value);
        }
        if (!emotes.isEmpty()) {
            final ComponentWidget unownedSubtitle = ComponentWidget.i18n(this.translationKeyPrefix + "unownedSubtitle");
            unownedSubtitle.addId("subtitle");
            if (((Document)this.document).isInitialized()) {
                list.addChildInitialized(unownedSubtitle, false);
            }
            else {
                list.addChild(unownedSubtitle, false);
            }
        }
        emotes.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        for (final EmoteItem emote2 : emotes) {
            final EmoteOverviewWidget emoteOverviewWidget = new EmoteOverviewWidget(this.emotesActivity, emote2).addId("unselectable");
            if (((Document)this.document).isInitialized()) {
                list.addChildInitialized(emoteOverviewWidget, false);
            }
            else {
                list.addChild(emoteOverviewWidget, false);
            }
        }
        this.appendNoEmotesComponent(list);
        list.sortChildren();
        list.updateBounds();
    }
    
    @Override
    public boolean shouldHandleEscape() {
        return true;
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        if (key == Key.ESCAPE) {
            this.playerActivity.displayScreen(this.emotesActivity);
            return true;
        }
        return super.keyPressed(key, type);
    }
    
    @Override
    protected void onSessionUpdate(final PlayerActivity.UpdateContext context, final UUID uniqueId) {
        if (context == PlayerActivity.UpdateContext.EVENT) {
            this.playerActivity.displayScreen(this.emotesActivity);
        }
    }
    
    @Override
    protected void onLabyModRefresh(final PlayerActivity.UpdateContext context) {
        this.playerActivity.displayScreen(this.emotesActivity);
    }
    
    @Subscribe
    public void onLabyModNetUpdate(final LabyModNetRefreshEvent event) {
        this.reload();
    }
    
    public void display(final EmoteItem emoteItem, final int slot, final int page) {
        this.selectedEmote = emoteItem;
        this.selectedSlot = slot;
        this.page = page;
        this.playerActivity.displayScreen(this);
    }
    
    public void display() {
        this.selectedEmote = null;
        this.selectedSlot = -1;
        this.page = 0;
        this.playerActivity.displayScreen(this);
    }
    
    private void appendNoEmotesComponent(final VerticalListWidget<Widget> list) {
        if (!list.getChildren().isEmpty()) {
            return;
        }
        String key;
        if (this.searchQuery.isEmpty()) {
            key = "noEmotes";
        }
        else {
            key = "noEmotesSearch";
        }
        final ComponentWidget componentWidget = ComponentWidget.i18n(this.translationKeyPrefix + key);
        componentWidget.addId("no-emotes");
        if (((Document)this.document).isInitialized()) {
            list.addChildInitialized(componentWidget, false);
        }
        else {
            list.addChild(componentWidget, false);
        }
    }
}
