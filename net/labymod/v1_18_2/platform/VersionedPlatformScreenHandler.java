// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.platform;

import java.util.function.Function;
import com.google.common.util.concurrent.Runnables;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.core.platform.PlatformScreenHandler;

public class VersionedPlatformScreenHandler extends PlatformScreenHandler<edw>
{
    @Override
    public void onInitialize() {
        this.register(NamedScreen.SINGLEPLAYER, ehs.class);
        this.register(NamedScreen.MULTIPLAYER, egk.class);
        this.register(NamedScreen.EDIT_SERVER, edd.class);
        this.register(NamedScreen.MAIN_MENU, eeb.class);
        this.register(NamedScreen.CHAT_INPUT, ecs.class);
        this.register(NamedScreen.CHAT_INPUT_IN_BED, edg.class);
        this.register(NamedScreen.INGAME_MENU, edr.class);
        this.register(NamedScreen.INVENTORY, efq.class);
        this.register(NamedScreen.CREATIVE_INVENTORY, efh.class);
        this.register(NamedScreen.CONNECTING, ecv.class);
        this.register(NamedScreen.DISCONNECTED, edc.class);
        this.register(NamedScreen.CREDITS, eed.class);
        this.register(NamedScreen.CREATE_WORLD, eho.class);
        this.register(NamedScreen.LEVEL_LOADING, edi.class);
        this.register(NamedScreen.RECEIVING_LEVEL, edv.class);
        this.register(NamedScreen.PROGRESS, edu.class);
        this.register(NamedScreen.GENERIC_MESSAGE, edf.class);
        this.register(NamedScreen.OPEN_LAN_WORLD, edx.class);
        this.register(NamedScreen.STATISTICS, eee.class);
        this.register(NamedScreen.ADVANCEMENTS, eel.class);
        this.register(NamedScreen.CONFIRM, ecu.class);
        this.register(NamedScreen.SOCIAL_INTERACTIONS, ehm.class);
        this.register(NamedScreen.EDIT_BOOK, eez.class);
        this.register(NamedScreen.OPTIONS, edn.class);
        this.register(NamedScreen.SKIN_CUSTOMIZATION, edz.class);
        this.register(NamedScreen.VIDEO_SETTINGS, eec.class);
        this.register(NamedScreen.LANGUAGE_SELECTION, edh.class);
        this.register(NamedScreen.RESOURCE_PACK_SETTINGS, egs.class);
        this.register(NamedScreen.AUDIO_SETTINGS, eea.class);
        this.register(NamedScreen.CONTROL_SETTINGS, een.class);
        this.register(NamedScreen.CHAT_SETTINGS, ecr.class);
        this.register(NamedScreen.ACCESSIBILITY_SETTINGS, eco.class);
        this.register(NamedScreen.KEYBIND_SETTINGS, eep.class);
        this.register(NamedScreen.MOUSE_SETTINGS, edl.class);
        this.registerFactory((GameScreen)NamedScreen.SINGLEPLAYER, () -> {
            new ehs((edw)new eeb(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.MULTIPLAYER, () -> {
            new egk((edw)new eeb(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.OPTIONS, () -> {
            edw prevScreen = dyr.D().y;
            if (prevScreen instanceof edf) {
                prevScreen = null;
            }
            return new edn(prevScreen, dyr.D().l);
        });
        this.registerFactory((GameScreen)NamedScreen.CHAT_INPUT, () -> new ecs(""));
        this.registerFactory((GameScreen)NamedScreen.INGAME_MENU, () -> new edr(true));
        this.registerFactory((GameScreen)NamedScreen.DIRECT_CONNECT, () -> {
            final edw prevScreen2 = dyr.D().y;
            final emx data = new emx(fbt.a("selectServer.defaultName", new Object[0]), "", false);
            return new edb(prevScreen2, join -> {
                if (join) {
                    ecv.a(prevScreen2, dyr.D(), end.a(data.b), data);
                }
                else {
                    dyr.D().a(prevScreen2);
                }
            }, data);
        });
        this.registerFactory((GameScreen)NamedScreen.CREDITS, () -> new eed(false, Runnables.doNothing()));
        this.registerFactory((GameScreen)NamedScreen.LANGUAGE_SELECTION, () -> new edh(dyr.D().y, dyr.D().l, dyr.D().R()));
        this.registerFactory((GameScreen)NamedScreen.ACCESSIBILITY_SETTINGS, () -> new eco(dyr.D().y, dyr.D().l));
        this.registerFactory(NamedScreen.OPEN_LAN_WORLD, (Function<edw, edw>)edx::new);
    }
    
    @Override
    protected void registerFactory(final GameScreen screen, final Function<edw, edw> screenFactory) {
        this.registerFactory(screen, () -> screenFactory.apply(dyr.D().y));
    }
    
    @Override
    public boolean isInventoryScreen(final Object screen) {
        return screen instanceof eeu;
    }
}
