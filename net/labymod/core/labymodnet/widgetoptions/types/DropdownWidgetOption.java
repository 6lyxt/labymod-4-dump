// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labymodnet.widgetoptions.types;

import net.labymod.api.util.I18n;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.core.labymodnet.models.CosmeticOptionEntry;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.function.Consumer;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.cosmetics.CosmeticSettingsWidget;
import net.labymod.core.labymodnet.models.Cosmetic;
import net.labymod.core.labymodnet.models.CosmeticOption;
import net.labymod.core.labymodnet.widgetoptions.CustomWidgetOption;

public class DropdownWidgetOption extends CustomWidgetOption
{
    private static final String DEBOUNCE_ID = "dropdown-debounce";
    
    public DropdownWidgetOption(final CosmeticOption option, final String optionName, final int optionIndex) {
        super(option, optionName, optionIndex);
    }
    
    @Override
    public void begin(final Cosmetic cosmetic, final CosmeticSettingsWidget.CosmeticSettingsListener listener, final String translationKeyPrefix) {
        super.begin(cosmetic, listener, translationKeyPrefix);
        this.translationKeyPrefix += "dropdown.";
    }
    
    @Override
    protected void create(final String data, final Consumer<Widget> consumer) {
        CosmeticOptionEntry selectedEntry = null;
        final Iterator<CosmeticOptionEntry> iterator = this.option.options().iterator();
        CosmeticOptionEntry entry = null;
        while (iterator.hasNext()) {
            entry = iterator.next();
            if (entry.getData().equals(data)) {
                selectedEntry = entry;
                break;
            }
        }
        if (selectedEntry == null) {
            selectedEntry = this.option.first();
        }
        final Iterator<CosmeticOptionEntry> iterator2 = this.option.options().iterator();
        while (iterator2.hasNext()) {
            entry = iterator2.next();
            this.translateName(entry);
        }
        final DropdownWidget<CosmeticOptionEntry> dropdownWidget = new DropdownWidget<CosmeticOptionEntry>();
        dropdownWidget.addAll(this.option.options());
        dropdownWidget.setSelected(selectedEntry);
        dropdownWidget.setChangeListener(entry -> this.setData("dropdown-debounce", entry.getData()));
        consumer.accept(dropdownWidget);
    }
    
    @Override
    protected long getDebounceLength() {
        return 0L;
    }
    
    private void translateName(final CosmeticOptionEntry entry) {
        final String key = entry.getCustomKey();
        final String name = entry.getName();
        final String translation = I18n.getTranslation(this.translationKeyPrefix + key + "." + name, new Object[0]);
        entry.setCustomName(translation);
    }
}
