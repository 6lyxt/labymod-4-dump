// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labyconnect.desktop.sections;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendNoteUpdateEvent;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendServerEvent;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendStatusEvent;
import net.labymod.api.event.labymod.labyconnect.session.chat.LabyConnectChatMessageDeleteEvent;
import net.labymod.api.event.labymod.labyconnect.session.chat.LabyConnectChatMessageEvent;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectDisconnectEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendRemoveEvent;
import net.labymod.api.Constants;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import java.util.Objects;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.entries.LabyConnectFriendWidget;
import net.labymod.api.Textures;
import net.labymod.api.event.labymod.labyconnect.session.chat.LabyConnectChatInitializeEvent;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.Laby;
import net.labymod.api.client.screenshot.ScreenshotUtil;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.chat.LabyConnectFriendProfileWidget;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.chat.LabyConnectChatWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Links;
import net.labymod.api.client.gui.screen.activity.Activity;

@Links({ @Link("activity/labyconnect/laby-connect-chat.lss"), @Link("activity/labyconnect/laby-connect-chat-log.lss") })
@AutoActivity
public class LabyConnectDirectChatActivity extends Activity
{
    private final ListSession<Widget> listSession;
    private final Friend friend;
    private final TextFieldWidget inputField;
    private final LabyConnectChatWidget widgetChat;
    private LabyConnectFriendProfileWidget profile;
    
    public LabyConnectDirectChatActivity(final Friend friend) {
        this.listSession = new ListSession<Widget>();
        this.friend = friend;
        (this.inputField = new TextFieldWidget()).addId("input-field");
        this.inputField.submitHandler(message -> this.submitMessage());
        this.inputField.imagePasteHandler(gameImage -> {
            final GameImage resized = ScreenshotUtil.maxSize(gameImage, 1920, 1080);
            final ResourceLocation resourceLocation = Laby.labyAPI().renderPipeline().resources().resourceLocationFactory().createLabyMod("send_image_preview");
            resized.uploadTextureAt(resourceLocation);
            final Icon icon = Icon.texture(resourceLocation);
            icon.resolution(resized.getWidth(), resized.getHeight());
            final String i18nPrefix = "labymod.activity.labyconnect.chat.lanworld.share.image.";
            PopupWidget.builder().title(Component.translatable(i18nPrefix + "title", new Component[0])).text(Component.translatable(i18nPrefix + "text", new Component[0]).arguments(Component.text(friend.getName()))).icon(icon).confirmCallback(() -> {
                try {
                    this.friend.chat().sendImage(resized);
                }
                catch (final Exception e) {
                    e.printStackTrace();
                }
            }).cancelCallback(() -> {}).build().displayInOverlay();
            return;
        });
        this.inputField.setText(this.friend.chat().getInputMessage());
        this.inputField.setCooldownMillis(1000L);
        this.listSession.scrollToBottom();
        (this.widgetChat = new LabyConnectChatWidget(this.friend.chat(), this.listSession)).addId("chat");
    }
    
    @Override
    public void onOpenScreen() {
        super.onOpenScreen();
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final FlexibleContentWidget container = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)container).addId("container");
        final DivWidget header = new DivWidget();
        header.addId("header");
        (this.profile = new LabyConnectFriendProfileWidget(this.friend)).addId("profile");
        ((AbstractWidget<LabyConnectFriendProfileWidget>)header).addChild(this.profile);
        final HorizontalListWidget buttonContainer = new HorizontalListWidget().addId("button-container");
        final boolean singleplayer = this.labyAPI.minecraft().isSingleplayer();
        String hoverTranslatable;
        if (!this.friend.isOnline()) {
            hoverTranslatable = "notOnline";
        }
        else if (!singleplayer) {
            hoverTranslatable = "notSingleplayer";
        }
        else {
            hoverTranslatable = null;
        }
        final ButtonWidget createDropdownMenu = ButtonWidget.i18n("labymod.activity.labyconnect.chat.lanworld.invite.title");
        ((AbstractWidget<Widget>)createDropdownMenu).addId("invite-button");
        if (hoverTranslatable != null) {
            createDropdownMenu.setHoverComponent(Component.translatable("labymod.activity.labyconnect.chat.lanworld.invite." + hoverTranslatable, new Component[0]));
        }
        createDropdownMenu.setEnabled(this.friend.isOnline() && singleplayer);
        createDropdownMenu.setPressable(() -> LabyMod.references().sharedLanWorldService().inviteToLan(this.friend));
        buttonContainer.addEntry(createDropdownMenu);
        Laby.fireEvent(new LabyConnectChatInitializeEvent(this.friend, buttonContainer));
        final ButtonWidget dropdownWidget = ButtonWidget.icon(Textures.SpriteCommon.SMALL_BURGER_DOTS);
        dropdownWidget.setContextMenu(LabyConnectFriendWidget.createContextMenu(this.friend));
        final ButtonWidget buttonWidget = dropdownWidget;
        final ButtonWidget obj = dropdownWidget;
        Objects.requireNonNull(obj);
        buttonWidget.setPressable(obj::openContextMenu);
        buttonContainer.addEntry(dropdownWidget);
        ((AbstractWidget<HorizontalListWidget>)header).addChild(buttonContainer);
        container.addContent(header);
        container.addContent(new DivWidget().addId("split"));
        container.addFlexibleContent(new ScrollWidget(this.widgetChat, this.listSession));
        final FlexibleContentWidget chatInputContainer = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)chatInputContainer).addId("chat-input-container");
        this.inputField.setFocused(true);
        chatInputContainer.addFlexibleContent(this.inputField);
        container.addContent(chatInputContainer);
        ((AbstractWidget<FlexibleContentWidget>)this.document).addChild(container);
    }
    
    @Override
    public void onCloseScreen() {
        super.onCloseScreen();
        this.friend.chat().setInputMessage(this.inputField.getText());
    }
    
    private void submitMessage() {
        String message = this.inputField.getText();
        message = message.trim();
        if (message.isEmpty()) {
            return;
        }
        this.labyAPI.minecraft().sounds().playSound(Constants.Resources.SOUND_CHAT_MESSAGE, 0.35f, 1.0f);
        this.friend.chat().sendMessage(message);
        this.inputField.setText("");
        this.labyAPI.minecraft().executeNextTick(() -> this.inputField.setFocused(true));
    }
    
    @Subscribe
    public void onLabyConnectFriendRemove(final LabyConnectFriendRemoveEvent event) {
        if (this.friend.equals(event.friend())) {
            this.displayScreen(new LabyConnectEmptyActivity());
        }
    }
    
    @Subscribe
    public void onLabyConnectDisconnect(final LabyConnectDisconnectEvent event) {
        this.displayScreen(new LabyConnectEmptyActivity());
    }
    
    @Subscribe
    public void onLabyConnectChatMessage(final LabyConnectChatMessageEvent event) {
        if (this.friend.chat().equals(event.chat())) {
            this.widgetChat.addMessage(event.message());
        }
    }
    
    @Subscribe
    public void onLabyConnectChatMessageDelete(final LabyConnectChatMessageDeleteEvent event) {
        if (this.friend.chat().equals(event.chat())) {
            this.reload();
        }
    }
    
    @Subscribe
    public void onLabyConnectFriendStatus(final LabyConnectFriendStatusEvent event) {
        if (this.friend.equals(event.friend())) {
            this.profile.reInitialize();
        }
    }
    
    @Subscribe
    public void onLabyConnectFriendServer(final LabyConnectFriendServerEvent event) {
        if (this.friend.equals(event.friend())) {
            this.profile.reInitialize();
        }
    }
    
    @Subscribe
    public void onLabyConnectFriendNoteUpdate(final LabyConnectFriendNoteUpdateEvent event) {
        if (this.friend.equals(event.friend())) {
            this.profile.reInitialize();
        }
    }
    
    public Friend friend() {
        return this.friend;
    }
}
