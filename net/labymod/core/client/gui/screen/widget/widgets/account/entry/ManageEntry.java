// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.account.entry;

import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.core.client.gui.screen.activity.activities.account.AccountManagerActivity;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;

public class ManageEntry implements AccountEntry
{
    @Override
    public Component getComponent() {
        return Component.translatable("labymod.activity.accountManager.button.manage", new Component[0]);
    }
    
    @Override
    public Icon getIcon() {
        return Textures.SpriteCommon.SETTINGS;
    }
    
    @Override
    public void interact(final Runnable callback) {
        Laby.labyAPI().minecraft().minecraftWindow().displayScreen(new AccountManagerActivity());
    }
}
