// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.multiplayer;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetReference;
import java.util.Collections;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import net.labymod.api.client.gui.lss.meta.LinkReference;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.key.KeyHandler;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget;
import net.labymod.api.client.resources.CompletableResourceLocation;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.util.Color;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import java.util.ArrayList;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import java.util.function.BooleanSupplier;
import net.labymod.api.client.network.server.storage.ServerFolder;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.LabyNetServerInfoCache;
import java.util.List;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerEntryWidget;

@AutoWidget
public class ServerFolderWidget extends ServerEntryWidget
{
    private final List<LabyNetServerInfoCache<StorageServerData>> servers;
    private final ServerFolder folder;
    private final Runnable delete;
    private final Runnable save;
    private final BooleanSupplier dragging;
    private IconWidget iconWidget;
    
    public ServerFolderWidget(final ServerFolder folder, final Runnable delete, final Runnable save, final BooleanSupplier dragging) {
        this.servers = new ArrayList<LabyNetServerInfoCache<StorageServerData>>();
        this.folder = folder;
        this.delete = delete;
        this.save = save;
        this.dragging = dragging;
        this.createContextMenu().with(ContextMenuEntry.builder().text(Component.translatable("labymod.ui.button.rename", new Component[0])).icon(Textures.SpriteCommon.SETTINGS).clickHandler(entry -> {
            final String previousName = folder.getName();
            final Color previousColor = folder.getColor();
            promptRename(this.folder, (name, color) -> {
                this.folder.setName(name);
                this.folder.setColor(color);
                this.save.run();
                this.reInitialize();
                return;
            }, (name, color) -> {
                if (name != null) {
                    this.folder.setName(name);
                    this.reInitialize();
                }
                if (color != null) {
                    this.folder.setColor(color);
                    this.reInitialize();
                }
                return;
            }, () -> {
                this.folder.setName(previousName);
                this.folder.setColor(previousColor);
                this.reInitialize();
                return;
            });
            return true;
        }).build()).with(ContextMenuEntry.builder().text(Component.translatable("labymod.ui.button.delete", new Component[0])).icon(Textures.SpriteCommon.TRASH).clickHandler(entry -> {
            this.delete.run();
            return true;
        }).build());
    }
    
    @Override
    public void tick() {
        super.tick();
        if (this.dragging.getAsBoolean()) {
            this.removeId("hover-effect");
            this.addId("no-transition");
        }
        else {
            this.addId("hover-effect");
            this.removeId("no-transition");
        }
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        (this.iconWidget = new IconWidget(Textures.SpriteCommon.FOLDER)).addId("icon");
        this.iconWidget.color().set(this.folder.getColor().getValue() | 0xFF000000);
        this.addSelectionWidgets(this.iconWidget);
        ((AbstractWidget<IconWidget>)this).addChild(this.iconWidget);
        final ComponentWidget title = ComponentWidget.text(this.folder.getName());
        title.addId("title");
        ((AbstractWidget<ComponentWidget>)this).addChild(title);
        final HorizontalListWidget list = new HorizontalListWidget();
        ((AbstractWidget<Widget>)list).addId("servers");
        int count = 0;
        for (final LabyNetServerInfoCache<StorageServerData> server : this.servers) {
            final CompletableResourceLocation icon = server.serverInfo().getIcon();
            final IconWidget iconWidget = new IconWidget(Icon.completable(icon));
            iconWidget.addId("server-icon");
            iconWidget.setPressOnRelease(true);
            iconWidget.setPressable(() -> server.serverInfo().connect());
            list.addEntry(iconWidget);
            if (++count >= 16) {
                break;
            }
        }
        ((AbstractWidget<HorizontalListWidget>)this).addChild(list);
    }
    
    public void enter() {
        if (this.movable != ServerInfoWidget.Movable.NO && !KeyHandler.isShiftDown()) {
            this.moveHandler.accept(ServerInfoWidget.Movable.ADD);
        }
    }
    
    public void addServer(final LabyNetServerInfoCache<StorageServerData> server) {
        this.servers.add(server);
    }
    
    public List<LabyNetServerInfoCache<StorageServerData>> getServers() {
        return this.servers;
    }
    
    @Override
    public int getListIndex() {
        return this.servers.isEmpty() ? 0 : this.servers.get(0).getSortingValue();
    }
    
    public IconWidget getIconWidget() {
        return this.iconWidget;
    }
    
    public ServerFolder getFolder() {
        return this.folder;
    }
    
    @Override
    public String toString() {
        final StringBuilder string = new StringBuilder();
        for (final LabyNetServerInfoCache<StorageServerData> server : this.servers) {
            if (!string.isEmpty()) {
                string.append(",");
            }
            string.append(server.serverAddress().toString());
        }
        return "[" + String.valueOf(string);
    }
    
    public static void promptRename(final ServerFolder currentFolder, final FolderRenameCallback callback) {
        promptRename(currentFolder, callback, null, null);
    }
    
    public static void promptRename(final ServerFolder currentFolder, final FolderRenameCallback callback, @Nullable final FolderUpdateCallback updateCallback, @Nullable final Runnable onCancel) {
        final FlexibleContentWidget list = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)list).addId("rename-options");
        final TextFieldWidget textField = new TextFieldWidget();
        textField.setFocused(true);
        textField.setText((currentFolder == null) ? "" : currentFolder.getName());
        textField.updateListener(text -> {
            if (updateCallback != null) {
                updateCallback.update(text, null);
            }
            return;
        });
        textField.setCursorAtEnd();
        list.addFlexibleContent(textField);
        final Color currentColor = (currentFolder == null) ? ServerFolder.DEFAULT_COLOR : currentFolder.getColor();
        final ColorPickerWidget colorPicker = ColorPickerWidget.of(currentColor);
        colorPicker.addUpdateListener(currentColor, color -> {
            if (updateCallback != null) {
                updateCallback.update(null, color);
            }
            return;
        });
        list.addContent(colorPicker);
        final StyleSheet styleSheet = new LinkReference("labymod", "lss/activity/multiplayer/server-list.lss").loadStyleSheet();
        final PopupWidget widget = PopupWidget.builder().title(Component.translatable("labymod.activity.multiplayer.private.folder.rename.title", new Component[0])).widgetSupplier(() -> list).confirmCallback(() -> {
            final String name = textField.getText().trim();
            if (name.isEmpty()) {
                return;
            }
            else {
                callback.rename(name, colorPicker.value());
                return;
            }
        }).cancelCallback(() -> {
            if (onCancel != null) {
                onCancel.run();
            }
            return;
        }).build();
        final WidgetReference ref = widget.displayInOverlay(Collections.singletonList(styleSheet));
        ref.clickRemoveStrategy(WidgetReference.ClickRemoveStrategy.OUTSIDE);
        ref.keyPressRemoveStrategy(WidgetReference.KeyPressRemoveStrategy.ESCAPE);
    }
    
    public interface FolderRenameCallback
    {
        void rename(final String p0, final Color p1);
    }
    
    public interface FolderUpdateCallback
    {
        void update(final String p0, final Color p1);
    }
}
