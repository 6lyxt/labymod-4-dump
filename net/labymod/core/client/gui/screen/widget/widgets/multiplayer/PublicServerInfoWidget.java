// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.multiplayer;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.client.component.Component;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.screen.Parent;
import java.util.Objects;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.LabyNetServerInfoCache;
import org.jetbrains.annotations.NotNull;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.network.server.global.PublicServerData;

@AutoWidget
public class PublicServerInfoWidget extends LabyNetServerInfoWidget<PublicServerData>
{
    private final Predicate<PublicServerInfoWidget> savable;
    private final Consumer<PublicServerInfoWidget> save;
    
    public PublicServerInfoWidget(@NotNull final PublicServerData serverData, @NotNull final LabyNetServerInfoCache<PublicServerData> cache, @NotNull final Predicate<PublicServerInfoWidget> savable, @NotNull final Consumer<PublicServerInfoWidget> save) {
        super(serverData, cache);
        Objects.requireNonNull(savable, "Savable predicate cannot be null!");
        Objects.requireNonNull(save, "Save consumer cannot be null!");
        this.savable = savable;
        this.save = save;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final ContextMenu contextMenu = this.createContextMenu();
        final ContextMenuEntry saveContextMenuEntry = ContextMenuEntry.builder().icon(Textures.SpriteCommon.PIN).text(Component.translatable("labymod.activity.multiplayer.public.button.saveServer", new Component[0])).disabled(() -> !this.savable.test(this)).clickHandler((entry, widget) -> this.save.accept(this)).build();
        contextMenu.addEntry(saveContextMenuEntry);
    }
    
    @Override
    protected Component serverName() {
        TextComponent rank = Component.text("#" + (this.cache().getSortingValue() + 1));
        if (this.serverData().isPartner()) {
            rank = rank.color(NamedTextColor.YELLOW).hoverEvent(HoverEvent.showText(((BaseComponent<Component>)Component.text("LabyMod Partner")).color(NamedTextColor.YELLOW)));
        }
        else {
            rank = rank.color(NamedTextColor.GRAY);
        }
        return ((BaseComponent<Component>)Component.empty().append(rank)).append(super.serverName());
    }
}
