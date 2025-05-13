// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.tag.renderer.positions;

import net.labymod.api.metadata.Metadata;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.tag.renderer.stack.VerticalStackRenderer;

public class BelowNameRenderer extends VerticalStackRenderer
{
    @Override
    protected boolean keepUsernamePosition() {
        return false;
    }
    
    @Override
    protected float getEntryYOffset() {
        final Theme currentTheme = Laby.labyAPI().themeService().currentTheme();
        final Metadata metadata = currentTheme.metadata();
        return metadata.get("below_name_entry_y_offset", 0.0f);
    }
}
