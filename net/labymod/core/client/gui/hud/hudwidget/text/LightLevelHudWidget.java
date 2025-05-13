// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;

@IntroducedIn("4.1.0")
@SpriteSlot(x = 0, y = 5)
public class LightLevelHudWidget extends TextHudWidget<TextHudWidgetConfig>
{
    private TextLine lightLevelLine;
    
    public LightLevelHudWidget() {
        super("light_level");
        this.bindCategory(HudWidgetCategory.INGAME);
    }
    
    @Override
    public void load(final TextHudWidgetConfig config) {
        super.load(config);
        this.lightLevelLine = super.createLine("Light Level", "?");
    }
    
    @Override
    public void onTick(final boolean isEditorContext) {
        final Entity entity = this.labyAPI.minecraft().getCameraEntity();
        final ClientWorld world = this.labyAPI.minecraft().clientWorld();
        if (entity == null || world == null) {
            return;
        }
        final Position position = entity.position();
        final int x = MathHelper.floor(position.getX());
        final int y = MathHelper.floor(position.getY());
        final int z = MathHelper.floor(position.getZ());
        final BlockState blockState = world.getBlockState(x, y, z);
        final int level = blockState.getLightLevel();
        this.lightLevelLine.updateAndFlush(level);
    }
}
