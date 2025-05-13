// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.multiplayer.child;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.function.Consumer;
import java.util.Objects;
import java.util.Locale;
import net.labymod.api.client.network.server.ServerInfoCache;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import net.labymod.core.client.gui.screen.widget.widgets.multiplayer.StorageServerInfoWidget;
import java.util.Iterator;
import net.labymod.core.client.gui.screen.widget.widgets.multiplayer.LabyNetServerInfoWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.util.Color;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.sound.SoundService;
import java.util.List;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget;
import net.labymod.core.client.gui.screen.widget.widgets.multiplayer.ServerFolderWidget;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.client.network.server.storage.MoveActionType;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.Laby;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.LabyNetServerInfoCache;
import java.util.function.BiConsumer;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.network.server.storage.ServerFolder;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.directconnect.DirectConnectActivity;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.core.client.network.server.storage.DefaultServerList;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.MultiplayerActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerEntryWidget;
import net.labymod.api.client.network.server.storage.StorageServerData;

@AutoActivity
@Link("activity/multiplayer/private-server-list.lss")
public class PrivateServerListActivity extends LabyNetServerListActivity<StorageServerData, ServerEntryWidget>
{
    private final MultiplayerActivity multiplayerActivity;
    private final DefaultServerList serverList;
    private final ButtonWidget editButton;
    private final ButtonWidget deleteButton;
    private DirectConnectActivity directConnectActivity;
    private ServerFolder currentFolder;
    
    public PrivateServerListActivity(final MultiplayerActivity multiplayerActivity, final TextFieldWidget searchField) {
        super("private", searchField);
        this.multiplayerActivity = multiplayerActivity;
        this.serverList = (DefaultServerList)this.serverController.serverList();
        if (this.serverList.size() == 0) {
            this.serverList.load();
        }
        this.getOrRegisterServerData(null);
        final List<ServerEntryWidget> children = this.serverListWidget.getChildren();
        this.serverListWidget.registerServerMover((dragging, destination, type) -> {
            final SoundService soundService = Laby.references().soundService();
            final int draggingIndex = dragging.getListIndex();
            final Widget parent = (Widget)this.serverListWidget.getParent();
            final boolean insideFolder = this.currentFolder != null;
            if (insideFolder && !parent.isHovered()) {
                if (this.currentFolder != null) {
                    final boolean success = this.serverList.move(draggingIndex, -1, MoveActionType.REMOVE_FROM_FOLDER, this.currentFolder);
                    if (success) {
                        soundService.play(SoundType.SERVER_MOVE, 0.45f);
                        if (this.currentFolder.isEmpty()) {
                            this.currentFolder = null;
                            soundService.play(SoundType.HUD_TRASH, 0.45f);
                        }
                        this.reload();
                    }
                }
                return;
            }
            else {
                if (destination != null) {
                    final int destinationIndex = destination.getListIndex();
                    if (type == MoveActionType.ADD_TO_FOLDER && !this.serverList.hasFolder(destinationIndex) && !this.serverList.hasFolder(draggingIndex)) {
                        ServerFolderWidget.promptRename(null, (name, color) -> {
                            final ServerFolder target = this.serverList.getOrCreateFolder(destinationIndex);
                            target.setName(name);
                            target.setColor(color);
                            final boolean success3 = this.serverList.move(draggingIndex, destinationIndex, type, this.currentFolder);
                            if (success3) {
                                Laby.references().soundService().play(SoundType.HUD_ATTACH, 0.15f);
                                this.reload();
                            }
                        });
                    }
                    else {
                        final boolean success2 = this.serverList.move(draggingIndex, destinationIndex, type, this.currentFolder);
                        if (success2) {
                            final SoundType sound = (type == MoveActionType.ADD_TO_FOLDER) ? SoundType.HUD_ATTACH : SoundType.SERVER_MOVE;
                            soundService.play(sound, 0.45f);
                            this.reload();
                        }
                    }
                }
                return;
            }
        });
        this.session.setEntrySwapper((a, b) -> {
            final ServerEntryWidget widget1 = children.get(a);
            final ServerEntryWidget widget2 = children.get(b);
            this.serverList.swap(widget1.getSortingValue(), widget2.getSortingValue(), this.currentFolder);
            Laby.references().soundService().play(SoundType.SERVER_MOVE, 0.45f);
            this.reload();
            return;
        });
        this.deleteButton = ButtonWidget.i18n("labymod.ui.button.delete", () -> {
            final ServerEntryWidget selected = this.session.getSelectedEntry();
            if (selected == null) {
                return;
            }
            else {
                this.delete(selected);
                return;
            }
        });
        this.editButton = ButtonWidget.i18n("labymod.ui.button.edit", () -> {
            final ServerEntryWidget selected2 = this.session.getSelectedEntry();
            if (selected2 == null) {
                return;
            }
            else if (selected2 instanceof ServerInfoWidget) {
                final ServerInfoWidget<?> serverInfo = (ServerInfoWidget<?>)selected2;
                final ServerData data = (ServerData)serverInfo.serverData();
                if (data instanceof final StorageServerData storageServerData) {
                    this.displayScreen(this.serverController.createEditServerScreen(storageServerData, s -> {
                        this.serverList.update(s);
                        this.serverList.saveAsync();
                    }));
                    return;
                }
                else {
                    return;
                }
            }
            else {
                return;
            }
        });
        this.setServerButtonsEnabled(false);
        this.serverListWidget.setSelectCallback(widget -> this.setServerButtonsEnabled(widget instanceof ServerInfoWidget));
    }
    
    @Override
    protected void fillServerList(final VerticalListWidget<ServerEntryWidget> serverListWidget, final String searchQuery) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnull          8
        //     4: aconst_null    
        //     5: goto            12
        //     8: aload_0         /* this */
        //     9: getfield        net/labymod/core/client/gui/screen/activity/activities/multiplayer/child/PrivateServerListActivity.currentFolder:Lnet/labymod/api/client/network/server/storage/ServerFolder;
        //    12: astore_3        /* currentFolder */
        //    13: aload_0         /* this */
        //    14: getfield        net/labymod/core/client/gui/screen/activity/activities/multiplayer/child/PrivateServerListActivity.serverList:Lnet/labymod/core/client/network/server/storage/DefaultServerList;
        //    17: invokevirtual   net/labymod/core/client/network/server/storage/DefaultServerList.size:()I
        //    20: iconst_1       
        //    21: isub           
        //    22: istore          size
        //    24: aload_0         /* this */
        //    25: aload_0         /* this */
        //    26: aload_3         /* currentFolder */
        //    27: aload_2         /* searchQuery */
        //    28: iload           size
        //    30: aload_1         /* serverListWidget */
        //    31: invokedynamic   BootstrapMethod #5, accept:(Lnet/labymod/core/client/gui/screen/activity/activities/multiplayer/child/PrivateServerListActivity;Lnet/labymod/api/client/network/server/storage/ServerFolder;Ljava/lang/String;ILnet/labymod/api/client/gui/screen/widget/widgets/layout/list/VerticalListWidget;)Ljava/util/function/BiConsumer;
        //    36: invokevirtual   net/labymod/core/client/gui/screen/activity/activities/multiplayer/child/PrivateServerListActivity.getOrRegisterServerData:(Ljava/util/function/BiConsumer;)V
        //    39: return         
        //    Signature:
        //  (Lnet/labymod/api/client/gui/screen/widget/widgets/layout/list/VerticalListWidget<Lnet/labymod/api/client/gui/screen/widget/widgets/activity/multiplayer/ServerEntryWidget;>;Ljava/lang/String;)V
        //    StackMapTable: 00 02 08 43 07 00 CC
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Could not infer any expression.
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:382)
        //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:95)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:344)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:206)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:93)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:868)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:761)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:638)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:605)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:195)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:162)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:137)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:333)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:254)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:129)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    protected void fillButtonContainer(final FlexibleContentWidget container) {
        this.setServerButtonsEnabled(this.session.getSelectedEntry() != null);
        final FlexibleContentWidget buttonRowOne = new FlexibleContentWidget();
        buttonRowOne.addFlexibleContent(this.joinButton);
        buttonRowOne.addFlexibleContent(ButtonWidget.i18n(this.getTranslationKey("button.directConnection"), () -> {
            if (this.directConnectActivity == null) {
                this.directConnectActivity = new DirectConnectActivity(this.multiplayerActivity);
            }
            this.displayScreen(this.directConnectActivity);
            return;
        }));
        buttonRowOne.addFlexibleContent(ButtonWidget.i18n(this.getTranslationKey("button.addServer"), () -> {
            final ScreenWrapper screen = this.serverController.createNewServerScreen(serverData -> {
                this.serverList.add(serverData);
                this.reload();
                return;
            });
            this.displayScreen(screen);
            return;
        }));
        container.addContent(buttonRowOne);
        final FlexibleContentWidget buttonRowTwo = new FlexibleContentWidget();
        buttonRowTwo.addFlexibleContent(this.editButton);
        buttonRowTwo.addFlexibleContent(this.deleteButton);
        buttonRowTwo.addFlexibleContent(ButtonWidget.i18n("labymod.ui.button.refresh", () -> this.refresh(true)));
        buttonRowTwo.addFlexibleContent(ButtonWidget.i18n("labymod.ui.button.cancel", () -> this.displayScreen((ScreenInstance)null)));
        container.addContent(buttonRowTwo);
    }
    
    @Override
    protected void initializeHeader(final FlexibleContentWidget container, final String searchQuery) {
        if (this.currentFolder == null || searchQuery != null) {
            return;
        }
        final DivWidget header = new DivWidget();
        header.addId("header");
        header.setContextMenu(this.createServerFolderWidget(this.currentFolder).getContextMenu());
        final ButtonWidget backButton = ButtonWidget.icon(Textures.SpriteCommon.BACK_BUTTON);
        ((AbstractWidget<Widget>)backButton).addId("back-button");
        backButton.setPressable(() -> {
            this.currentFolder = null;
            this.reload();
            return;
        });
        ((AbstractWidget<ButtonWidget>)header).addChild(backButton);
        final IconWidget backIconWidget = new IconWidget(Textures.SpriteCommon.FOLDER);
        final Color color = this.currentFolder.getColor();
        backIconWidget.color().set(ColorFormat.ARGB32.pack(color.getValue(), 255));
        backIconWidget.addId("icon");
        ((AbstractWidget<IconWidget>)header).addChild(backIconWidget);
        final ComponentWidget title = ComponentWidget.text(this.currentFolder.getName());
        title.addId("title");
        ((AbstractWidget<ComponentWidget>)header).addChild(title);
        container.addContent(header);
    }
    
    @Override
    protected void setServerButtonsEnabled(final boolean enabled) {
        super.setServerButtonsEnabled(enabled);
        this.deleteButton.setEnabled(enabled);
        this.editButton.setEnabled(enabled);
    }
    
    @Override
    public void displayScreen(final ScreenInstance screen) {
        this.labyAPI.minecraft().minecraftWindow().displayScreen(screen);
    }
    
    @Override
    public void render(final ScreenContext context) {
        super.render(context);
        this.applyAnimation();
    }
    
    private void handleServerMove(final ServerEntryWidget widget, final ServerInfoWidget.Movable movable) {
        try {
            final int listIndex = widget.getSortingValue();
            switch (movable) {
                case SWAP: {
                    int targetIndex = -1;
                    for (final ServerEntryWidget child : this.serverListWidget.getChildren()) {
                        if (child.isHovered()) {
                            if (child == widget) {
                                continue;
                            }
                            targetIndex = child.getSortingValue();
                            break;
                        }
                    }
                    if (this.currentFolder != null) {
                        final boolean hoverBackIcon = !this.serverListWidget.isHovered();
                        if (hoverBackIcon) {
                            final ServerFolder folder = this.currentFolder;
                            this.serverList.move(listIndex, folder.getStartIndex(), MoveActionType.INSERT_ABOVE, this.currentFolder);
                            if (folder.isEmpty()) {
                                this.currentFolder = null;
                            }
                            this.reload();
                            break;
                        }
                        break;
                    }
                    else {
                        if (targetIndex == -1) {
                            break;
                        }
                        final int destinationIndex = targetIndex;
                        final ServerFolder folder = this.serverList.getFolder(destinationIndex);
                        if (folder != null) {
                            this.serverList.move(listIndex, destinationIndex, MoveActionType.ADD_TO_FOLDER, this.currentFolder);
                            this.reload();
                            break;
                        }
                        ServerFolderWidget.promptRename(null, (name, color) -> {
                            final ServerFolder target = this.serverList.getOrCreateFolder(destinationIndex);
                            target.setName(name);
                            target.setColor(color);
                            this.serverList.move(listIndex, destinationIndex, MoveActionType.ADD_TO_FOLDER, this.currentFolder);
                            this.reload();
                            return;
                        });
                        break;
                    }
                    break;
                }
                case UP: {
                    this.serverList.swap(listIndex, listIndex - 1, this.currentFolder);
                    Laby.references().soundService().play(SoundType.SERVER_MOVE, 0.45f);
                    this.reload();
                    break;
                }
                case DOWN: {
                    if (widget instanceof final ServerFolderWidget folderWidget) {
                        this.serverList.swap(listIndex, listIndex + folderWidget.getFolder().getLength(), this.currentFolder);
                    }
                    else {
                        this.serverList.swap(listIndex, listIndex + 1, this.currentFolder);
                    }
                    Laby.references().soundService().play(SoundType.SERVER_MOVE, 0.45f);
                    this.reload();
                    break;
                }
                case ADD: {
                    if (widget instanceof LabyNetServerInfoWidget) {
                        final LabyNetServerInfoWidget<?> server = (LabyNetServerInfoWidget<?>)widget;
                        server.connect();
                    }
                    if (widget instanceof final ServerFolderWidget folder2) {
                        this.currentFolder = folder2.getFolder();
                        Laby.references().soundService().play(SoundType.BUTTON_CLICK, 0.15f);
                        this.reload();
                    }
                    break;
                }
            }
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }
    
    private void getOrRegisterServerData(final BiConsumer<StorageServerData, LabyNetServerInfoCache<StorageServerData>> consumer) {
        for (int i = 0; i < this.serverList.size(); ++i) {
            final StorageServerData storageServerData = this.serverList.get(i);
            final LabyNetServerInfoCache<StorageServerData> cache = ((ServerListActivity<StorageServerData, W>)this).registerCache(storageServerData, server -> {
                final ServerInfoWidget<?> serverInfoWidget = ((ServerListActivity<StorageServerData, W>)this).getServerInfoWidget(storageServerData);
                if (serverInfoWidget != null) {
                    serverInfoWidget.updateServerInfo(server.serverInfo());
                }
                return;
            });
            cache.setSortingValue(i);
            if (consumer != null) {
                consumer.accept(storageServerData, cache);
            }
        }
        this.sortCache();
    }
    
    private void delete(final ServerEntryWidget widget) {
        if (this.session.getSelectedEntry() == widget) {
            this.session.setSelectedEntry(null);
        }
        if (widget instanceof final StorageServerInfoWidget storageServerInfoWidget) {
            final StorageServerData data = storageServerInfoWidget.serverData();
            PopupWidget.builder().title(Component.translatable(this.getTranslationKey("button.deleteServer.title"), Component.text(data.getName()))).confirmCallback(() -> {
                final SoundService soundService = Laby.references().soundService();
                soundService.play(SoundType.HUD_TRASH, 0.45f);
                this.serverList.remove(data);
                this.reload();
            }).build().displayInOverlay();
        }
    }
    
    private ServerFolderWidget getFolderWidget(final ServerFolder folder) {
        for (final ServerEntryWidget child : this.serverListWidget.getChildren()) {
            if (child instanceof final ServerFolderWidget folderWidget) {
                if (((ServerFolderWidget)child).getFolder() == folder) {
                    return folderWidget;
                }
                continue;
            }
        }
        return null;
    }
    
    @NotNull
    private ServerFolderWidget createServerFolderWidget(final ServerFolder folder) {
        return new ServerFolderWidget(folder, () -> {
            this.serverList.removeFolder(folder, false);
            this.reload();
        }, () -> {
            this.serverList.saveAsync();
            if (this.serverListWidget != null) {
                this.reload();
            }
        }, () -> this.serverListWidget.getDraggingWidget() != null);
    }
    
    private void applyAnimation() {
        final ServerEntryWidget draggingWidget = this.serverListWidget.getDraggingWidget();
        if (draggingWidget == null) {
            return;
        }
        final boolean insideFolder = this.currentFolder != null;
        if (!insideFolder) {
            return;
        }
        final Widget parent = (Widget)this.serverListWidget.getParent();
        draggingWidget.applyAnimation(parent);
    }
}
