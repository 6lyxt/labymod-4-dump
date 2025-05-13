// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.store.profile;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.Parent;
import java.util.Optional;
import java.util.function.Function;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.core.flint.marketplace.FlintModification;
import java.text.SimpleDateFormat;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

@AutoWidget
public class ChangelogWidget extends VerticalListWidget<Widget>
{
    private static final SimpleDateFormat DATE_FORMAT;
    private final FlintModification modification;
    private final FlintModification.Changelog changelog;
    private final InstalledAddonInfo installedAddon;
    
    public ChangelogWidget(final FlintModification modification, final FlintModification.Changelog changelog) {
        this.modification = modification;
        this.changelog = changelog;
        final Optional<LoadedAddon> optionalAddon = DefaultAddonService.getInstance().getAddon(modification.getNamespace());
        this.installedAddon = optionalAddon.map((Function<? super LoadedAddon, ? extends InstalledAddonInfo>)LoadedAddon::info).orElse(null);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        if (this.installedAddon != null && this.installedAddon.isFlintAddon() && this.installedAddon.getVersion().equals(this.changelog.getRelease())) {
            this.addId("changelog-installed");
        }
        final HorizontalListWidget headerWidget = new HorizontalListWidget();
        ((AbstractWidget<Widget>)headerWidget).addId("changelog-header");
        final ComponentWidget release = ComponentWidget.text(this.changelog.getRelease());
        release.addId("changelog-release");
        headerWidget.addEntry(release);
        final ComponentWidget date = ComponentWidget.text(ChangelogWidget.DATE_FORMAT.format(this.changelog.getAddedAt()));
        date.addId("changelog-date");
        headerWidget.addEntry(date);
        ((AbstractWidget<HorizontalListWidget>)this).addChild(headerWidget);
        final String changes = this.changelog.getChanges();
        if (changes != null && !changes.isEmpty()) {
            final ComponentWidget description = ComponentWidget.text(changes);
            description.addId("changelog-description");
            ((AbstractWidget<ComponentWidget>)this).addChild(description);
        }
    }
    
    static {
        DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy - HH:mm");
    }
}
