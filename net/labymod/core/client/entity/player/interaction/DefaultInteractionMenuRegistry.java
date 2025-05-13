// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.entity.player.interaction;

import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.core.client.entity.player.interaction.server.ServerBulletPoint;
import net.labymod.api.util.KeyValue;
import javax.inject.Inject;
import net.labymod.core.client.entity.player.interaction.defaults.AddFriendBulletPoint;
import net.labymod.core.client.entity.player.interaction.defaults.ReportCloakBulletPoint;
import net.labymod.core.client.entity.player.interaction.defaults.NameHistoryBulletPoint;
import net.labymod.core.client.entity.player.interaction.defaults.CopyNameBulletPoint;
import net.labymod.core.client.entity.player.interaction.defaults.LabyNetBulletPoint;
import net.labymod.api.client.entity.player.Player;
import java.util.ArrayList;
import net.labymod.api.client.entity.player.interaction.InteractionMenuPlaceholder;
import java.util.List;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.entity.player.interaction.InteractionMenuRegistry;
import net.labymod.api.client.entity.player.interaction.BulletPoint;
import net.labymod.api.service.DefaultRegistry;

@Singleton
@Implements(InteractionMenuRegistry.class)
public class DefaultInteractionMenuRegistry extends DefaultRegistry<BulletPoint> implements InteractionMenuRegistry
{
    private final List<InteractionMenuPlaceholder> placeholders;
    
    @Inject
    public DefaultInteractionMenuRegistry() {
        this.placeholders = new ArrayList<InteractionMenuPlaceholder>();
        this.registerPlaceHolder(InteractionMenuPlaceholder.of("name", Player::getName));
        this.registerPlaceHolder(InteractionMenuPlaceholder.of("uuid", player -> player.getUniqueId().toString()));
        ((DefaultRegistry<LabyNetBulletPoint>)this).register("labynet", new LabyNetBulletPoint());
        ((DefaultRegistry<CopyNameBulletPoint>)this).register("copy_name", new CopyNameBulletPoint());
        ((DefaultRegistry<NameHistoryBulletPoint>)this).register("name_history", new NameHistoryBulletPoint());
        ((DefaultRegistry<ReportCloakBulletPoint>)this).register("report_cape", new ReportCloakBulletPoint());
        ((DefaultRegistry<AddFriendBulletPoint>)this).register("add_labymod_friend", new AddFriendBulletPoint());
    }
    
    @Override
    public void unregisterServerBulletPoints() {
        for (final KeyValue<BulletPoint> pair : this.getElements().toArray(new KeyValue[0])) {
            final BulletPoint point = pair.getValue();
            if (point instanceof ServerBulletPoint) {
                this.unregister(pair.getKey());
            }
        }
    }
    
    @Override
    public void registerPlaceHolder(@NotNull final InteractionMenuPlaceholder placeholder) {
        Objects.requireNonNull(placeholder, "placeholder");
        if (this.placeholders.contains(placeholder)) {
            return;
        }
        this.placeholders.add(placeholder);
    }
    
    public String replacePlaceholders(@NotNull final String text, @NotNull final Player player) {
        Objects.requireNonNull(text, "text");
        Objects.requireNonNull(player, "player");
        String value = text;
        for (final InteractionMenuPlaceholder placeholder : this.placeholders) {
            value = value.replace(String.format(Locale.ROOT, "{%s}", placeholder.getIdentifier()), placeholder.getReplacement(player));
        }
        return value;
    }
}
