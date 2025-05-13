// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.game;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.LabyScreenAccessor;
import net.labymod.api.client.gui.screen.ScreenInstance;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.ScreenService;
import org.jetbrains.annotations.ApiStatus;
import net.labymod.api.tag.Taggable;

@ApiStatus.NonExtendable
public interface GameScreen extends Taggable
{
    public static final ScreenService SCREEN_SERVICE = Laby.references().screenService();
    
    String getId();
    
    boolean allowCustomFont();
    
    default boolean isOptions() {
        final ScreenTag tag = this.getTag();
        if (tag == null) {
            return this.taggedObject().hasTag(ScreenTags.OPTIONS);
        }
        return tag == ScreenTag.OPTIONS;
    }
    
    @Deprecated(forRemoval = true, since = "4.1.12")
    @Nullable
    ScreenTag getTag();
    
    default ScreenInstance create() {
        return GameScreen.SCREEN_SERVICE.createScreen(this);
    }
    
    default boolean isScreen(Object screen) {
        if (screen == null) {
            return false;
        }
        if (screen instanceof final LabyScreenAccessor accessor) {
            screen = accessor.screen();
        }
        if (screen instanceof final ScreenInstance screenInstance) {
            screen = screenInstance.mostInnerScreen();
        }
        final Class<?> screenClass = screen.getClass();
        final String screenName = GameScreen.SCREEN_SERVICE.getScreenNameByClass(screenClass);
        return screenName != null && screenName.equals(this.getId());
    }
}
