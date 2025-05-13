// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.tab;

import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.key.KeyHandler;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.component.Component;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.Parent;
import javax.inject.Inject;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.widgets.layout.TilesGridWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import java.util.List;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.chat.ChatSymbolRegistry;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.activity.types.chatinput.ChatInputTabActivity;

@Singleton
@Link("activity/chat/input/chat-symbol.lss")
@AutoActivity
@Referenceable
public class ChatSymbolActivity extends ChatInputTabActivity<DivWidget>
{
    private final ListSession<?> session;
    private final ChatSymbolRegistry registry;
    private final ConfigProperty<List<String>> favorites;
    private final VerticalListWidget<Widget> list;
    private final TilesGridWidget<Widget> grid;
    private final TilesGridWidget<Widget> favoritesGrid;
    
    @Inject
    public ChatSymbolActivity() {
        this.session = new ListSession<Object>();
        this.registry = Laby.references().chatSymbolRegistry();
        this.favorites = this.labyAPI.config().ingame().chatInput().favoriteSymbols();
        (this.list = new VerticalListWidget<Widget>()).addId("list");
        (this.grid = new TilesGridWidget<Widget>()).addId("grid");
        (this.favoritesGrid = new TilesGridWidget<Widget>()).addId("grid");
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final DivWidget extraWindow = new DivWidget();
        extraWindow.addId("extra-window");
        final HorizontalListWidget wrapper = new HorizontalListWidget();
        ((AbstractWidget<Widget>)wrapper).addId("wrapper");
        for (int i = 0; i < 3; ++i) {
            final VerticalListWidget<Widget> list = new VerticalListWidget<Widget>();
            list.addId("list");
            list.selectable().set(false);
            if (i == 0) {
                for (final Character symbol : this.registry.getTextDecorations().keySet()) {
                    list.addChild(this.createFormattedComponentTile(symbol.toString()));
                }
                list.addChild(this.createFormattedComponentTile("r"));
            }
            else {
                for (final Character symbol : this.registry.getTextColors().keySet()) {
                    if (i == 1 == Character.isAlphabetic(symbol)) {
                        list.addChild(this.createFormattedComponentTile(symbol.toString()));
                    }
                }
            }
            wrapper.addEntry(list);
        }
        ((AbstractWidget<HorizontalListWidget>)extraWindow).addChild(wrapper);
        ((AbstractWidget<DivWidget>)this.document).addChild(extraWindow);
        final DivWidget window = this.createSymbolWindow();
        ((AbstractWidget<DivWidget>)this.document).addChild(window);
    }
    
    @NotNull
    private DivWidget createSymbolWindow() {
        this.contentWidget = (T)new DivWidget();
        ((DivWidget)this.contentWidget).addId("window");
        final DivWidget titleBar = new DivWidget();
        titleBar.addId("title-bar");
        final ComponentWidget title = ComponentWidget.component(Component.translatable("labymod.chatInput.tab.symbol.name", new Component[0]));
        title.addId("title");
        ((AbstractWidget<ComponentWidget>)titleBar).addChild(title);
        ((AbstractWidget<DivWidget>)this.contentWidget).addChild(titleBar);
        final DivWidget gridDivWidget = new DivWidget();
        gridDivWidget.addId("grid-div");
        final ScrollWidget scrollWidget = new ScrollWidget(this.list, this.session);
        scrollWidget.addId("scroll-widget");
        final int favoriteColor = NamedTextColor.GOLD.getValue();
        for (final String favorite : this.favorites.get()) {
            final Widget tile = this.createComponentTile(favorite, favorite, favoriteColor);
            tile.addId("favorite");
            this.favoritesGrid.addTile(tile);
        }
        this.favoritesGrid.sortChildren();
        this.favoritesGrid.setVisible(!this.favoritesGrid.getChildren().isEmpty());
        this.list.addChild(this.favoritesGrid);
        final List<Character> symbols = this.registry.getSymbols();
        for (final Character symbol : symbols) {
            final String symbolString = symbol.toString();
            final int color = this.favorites.get().contains(symbolString) ? Integer.MAX_VALUE : -1;
            final Widget tile2 = this.createComponentTile(symbolString, symbolString, color);
            this.grid.addTile(tile2);
        }
        this.grid.sortChildren();
        this.list.addChild(this.grid);
        ((AbstractWidget<ScrollWidget>)gridDivWidget).addChild(scrollWidget);
        ((AbstractWidget<DivWidget>)this.contentWidget).addChild(gridDivWidget);
        return (DivWidget)this.contentWidget;
    }
    
    @NotNull
    private Widget createFormattedComponentTile(final String symbol) {
        final String textToPastInChat = String.format(Locale.ROOT, "%s%s", this.registry.getAmpersand(), symbol);
        final String displayText = String.format(Locale.ROOT, "%s%s%<s", this.registry.getParagraph(), symbol);
        return this.createComponentTile(displayText, textToPastInChat);
    }
    
    @NotNull
    private Widget createComponentTile(final String displayText, final String textToPastInChat) {
        return this.createComponentTile(displayText, textToPastInChat, -1);
    }
    
    @NotNull
    private Widget createComponentTile(final String displayText, final String textToPastInChat, final int color) {
        final TileComponentWidget tile = new TileComponentWidget(displayText, textToPastInChat, color);
        tile.addId("tile");
        tile.setLazy(true);
        tile.updateContextMenu(this);
        tile.setPressable(() -> {
            if (KeyHandler.isShiftDown() || KeyHandler.isAltDown()) {
                final boolean isFavoriteSymbol = this.favorites.get().contains(textToPastInChat);
                this.editFavoriteSymbols(textToPastInChat, !isFavoriteSymbol);
                tile.updateContextMenu(this);
                return;
            }
            else {
                this.labyAPI.minecraft().sounds().playButtonPress();
                this.labyAPI.minecraft().chatExecutor().insertText(textToPastInChat);
                return;
            }
        });
        return tile;
    }
    
    private void editFavoriteSymbols(final String symbol, final boolean shouldAddAsFavorite) {
        if (shouldAddAsFavorite) {
            this.addToFavoriteSymbols(symbol);
        }
        else {
            this.removeFromFavoriteSymbols(symbol);
        }
    }
    
    private void addToFavoriteSymbols(final String symbol) {
        final List<String> favourites = this.favorites.get();
        if (favourites.contains(symbol)) {
            return;
        }
        favourites.add(symbol);
        this.reload();
    }
    
    private void removeFromFavoriteSymbols(final String symbol) {
        final List<String> favourites = this.favorites.get();
        favourites.remove(symbol);
        this.reload();
    }
    
    @AutoWidget
    public static class TileComponentWidget extends AbstractWidget<Widget>
    {
        private final String symbol;
        private final String textToPastInChat;
        private final Component component;
        private final RenderableComponent renderableComponent;
        private final int color;
        
        public TileComponentWidget(final String symbol, final String textToPastInChat, final int color) {
            this.symbol = symbol;
            this.textToPastInChat = textToPastInChat;
            this.component = Component.text(symbol);
            this.renderableComponent = RenderableComponent.of(this.component);
            this.color = color;
        }
        
        public TileComponentWidget(final String symbol, final String textToPastInChat) {
            this(symbol, textToPastInChat, -1);
        }
        
        @Override
        public void render(final ScreenContext context) {
            final Bounds bounds = this.bounds();
            super.render(context);
            final ComponentRenderer renderer = this.labyAPI.renderPipeline().componentRenderer();
            final float x = bounds.getCenterX();
            final float y = bounds.getCenterY() - renderer.height() / 2.0f + 0.5f;
            renderer.builder().text(this.renderableComponent).pos(x, y).useFloatingPointPosition(false).centered(true).color(this.color).render(context.stack());
        }
        
        public void updateContextMenu(final ChatSymbolActivity activity) {
            this.setContextMenu(null);
            this.createContextMenuLazy(menu -> {
                final boolean isFavoriteSymbol = activity.favorites.get().contains(this.textToPastInChat);
                menu.with(ContextMenuEntry.builder().text(Component.translatable("labymod.chatInput.tab.symbol.context.copy", new Component[0])).clickHandler(entry -> {
                    this.labyAPI.minecraft().setClipboard(this.textToPastInChat);
                    return true;
                }).build()).with(ContextMenuEntry.builder().text(Component.translatable("labymod.chatInput.tab.symbol.context." + (isFavoriteSymbol ? "removeFromFavorites" : "addToFavorites"), new Component[0])).clickHandler(entry -> {
                    activity.editFavoriteSymbols(this.textToPastInChat, !isFavoriteSymbol);
                    this.updateContextMenu(activity);
                    return true;
                }).build());
            });
        }
    }
}
