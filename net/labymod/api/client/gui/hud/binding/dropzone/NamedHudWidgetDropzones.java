// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.binding.dropzone;

import net.labymod.api.client.gui.hud.binding.dropzone.zones.CrosshairDropzone;
import net.labymod.api.client.gui.hud.binding.dropzone.zones.ItemHudWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.dropzone.zones.ItemCounterHudWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.dropzone.zones.BossBarWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.dropzone.zones.ActionBarWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.dropzone.zones.TitleWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.dropzone.zones.DirectionHudWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.dropzone.zones.SaturationHudWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.dropzone.zones.ScoreboardHudWidgetDropzone;

public class NamedHudWidgetDropzones
{
    public static final HudWidgetDropzone SCOREBOARD_LEFT;
    public static final HudWidgetDropzone SCOREBOARD_RIGHT;
    public static final HudWidgetDropzone SATURATION;
    public static final HudWidgetDropzone DIRECTION;
    public static final HudWidgetDropzone TITLE;
    public static final HudWidgetDropzone ACTION_BAR;
    public static final HudWidgetDropzone BOSS_BAR;
    public static final HudWidgetDropzone ITEM_COUNTER_LEFT;
    public static final HudWidgetDropzone ITEM_COUNTER_RIGHT;
    public static final HudWidgetDropzone ITEM_TOP_LEFT;
    public static final HudWidgetDropzone ITEM_TOP_RIGHT;
    public static final HudWidgetDropzone ITEM_MIDDLE_LEFT;
    public static final HudWidgetDropzone ITEM_MIDDLE_RIGHT;
    public static final HudWidgetDropzone ITEM_BOTTOM_LEFT;
    public static final HudWidgetDropzone ITEM_BOTTOM_RIGHT;
    public static final HudWidgetDropzone CROSSHAIR_TOP;
    public static final HudWidgetDropzone CROSSHAIR_BOTTOM;
    public static final HudWidgetDropzone[] ITEMS;
    
    static {
        SCOREBOARD_LEFT = new ScoreboardHudWidgetDropzone(false);
        SCOREBOARD_RIGHT = new ScoreboardHudWidgetDropzone(true);
        SATURATION = new SaturationHudWidgetDropzone();
        DIRECTION = new DirectionHudWidgetDropzone();
        TITLE = new TitleWidgetDropzone();
        ACTION_BAR = new ActionBarWidgetDropzone();
        BOSS_BAR = new BossBarWidgetDropzone();
        ITEM_COUNTER_LEFT = new ItemCounterHudWidgetDropzone(false);
        ITEM_COUNTER_RIGHT = new ItemCounterHudWidgetDropzone(true);
        ITEM_TOP_LEFT = new ItemHudWidgetDropzone(ItemHudWidgetDropzone.Type.TOP_LEFT);
        ITEM_TOP_RIGHT = new ItemHudWidgetDropzone(ItemHudWidgetDropzone.Type.TOP_RIGHT);
        ITEM_MIDDLE_LEFT = new ItemHudWidgetDropzone(ItemHudWidgetDropzone.Type.MIDDLE_LEFT);
        ITEM_MIDDLE_RIGHT = new ItemHudWidgetDropzone(ItemHudWidgetDropzone.Type.MIDDLE_RIGHT);
        ITEM_BOTTOM_LEFT = new ItemHudWidgetDropzone(ItemHudWidgetDropzone.Type.BOTTOM_LEFT);
        ITEM_BOTTOM_RIGHT = new ItemHudWidgetDropzone(ItemHudWidgetDropzone.Type.BOTTOM_RIGHT);
        CROSSHAIR_TOP = new CrosshairDropzone(true);
        CROSSHAIR_BOTTOM = new CrosshairDropzone(false);
        ITEMS = new HudWidgetDropzone[] { NamedHudWidgetDropzones.ITEM_TOP_LEFT, NamedHudWidgetDropzones.ITEM_TOP_RIGHT, NamedHudWidgetDropzones.ITEM_MIDDLE_LEFT, NamedHudWidgetDropzones.ITEM_MIDDLE_RIGHT, NamedHudWidgetDropzones.ITEM_BOTTOM_LEFT, NamedHudWidgetDropzones.ITEM_BOTTOM_RIGHT };
    }
}
