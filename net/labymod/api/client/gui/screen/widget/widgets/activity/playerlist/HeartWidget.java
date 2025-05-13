// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.activity.playerlist;

import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
public class HeartWidget extends SimpleWidget
{
    private final NetworkPlayerInfo playerInfo;
    
    public HeartWidget(final NetworkPlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        final Bounds bounds = this.bounds();
        this.labyAPI.renderPipeline().heartRenderer().renderHearts(context.stack(), bounds.getX(), bounds.getY(), (int)bounds.getHeight(), this.playerInfo);
        super.renderWidget(context);
    }
}
