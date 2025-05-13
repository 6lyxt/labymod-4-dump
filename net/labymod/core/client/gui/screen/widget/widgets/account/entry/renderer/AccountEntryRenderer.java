// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.account.entry.renderer;

import org.jetbrains.annotations.NotNull;
import net.labymod.core.client.gui.screen.widget.widgets.account.AccountPopupEntryWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.Laby;
import net.labymod.core.client.gui.screen.widget.widgets.account.entry.AccountEntry;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.renderer.EntryRenderer;

public class AccountEntryRenderer implements EntryRenderer<AccountEntry>
{
    @Override
    public float getWidth(final AccountEntry entry, final float maxWidth) {
        return Laby.labyAPI().renderPipeline().componentRenderer().width(entry.getComponent());
    }
    
    @Override
    public float getHeight(final AccountEntry entry, final float maxWidth) {
        return 15.0f;
    }
    
    @NotNull
    @Override
    public Widget createEntryWidget(final AccountEntry entry) {
        return new AccountPopupEntryWidget(entry);
    }
}
