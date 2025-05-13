// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.platform;

import java.util.function.Function;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.core.platform.PlatformScreenHandler;

public class VersionedPlatformScreenHandler extends PlatformScreenHandler<etd>
{
    @Override
    public void onInitialize() {
        this.register(NamedScreen.SINGLEPLAYER, ext.class);
        this.register(NamedScreen.MULTIPLAYER, ewa.class);
        this.register(NamedScreen.EDIT_SERVER, esi.class);
        this.register(NamedScreen.MAIN_MENU, eti.class);
        this.register(NamedScreen.CHAT_INPUT, erw.class);
        this.register(NamedScreen.CHAT_INPUT_IN_BED, esm.class);
        this.register(NamedScreen.INGAME_MENU, esy.class);
        this.register(NamedScreen.INVENTORY, eva.class);
        this.register(NamedScreen.CREATIVE_INVENTORY, eup.class);
        this.register(NamedScreen.CONNECTING, erz.class);
        this.register(NamedScreen.DISCONNECTED, esh.class);
        this.register(NamedScreen.CREDITS, esc.class);
        this.register(NamedScreen.REALMS, eho.class);
        this.register(NamedScreen.CREATE_WORLD, exn.class);
        this.register(NamedScreen.LEVEL_LOADING, eso.class);
        this.register(NamedScreen.RECEIVING_LEVEL, etc.class);
        this.register(NamedScreen.PROGRESS, etb.class);
        this.register(NamedScreen.GENERIC_MESSAGE, esk.class);
        this.register(NamedScreen.OPEN_LAN_WORLD, ete.class);
        this.register(NamedScreen.STATISTICS, etl.class);
        this.register(NamedScreen.ADVANCEMENTS, ets.class);
        this.register(NamedScreen.CONFIRM, ery.class);
        this.register(NamedScreen.SOCIAL_INTERACTIONS, exh.class);
        this.register(NamedScreen.EDIT_BOOK, euh.class);
        this.register(NamedScreen.OPTIONS, esu.class);
        this.register(NamedScreen.SKIN_CUSTOMIZATION, etg.class);
        this.register(NamedScreen.VIDEO_SETTINGS, etj.class);
        this.register(NamedScreen.LANGUAGE_SELECTION, esn.class);
        this.register(NamedScreen.RESOURCE_PACK_SETTINGS, ewi.class);
        this.register(NamedScreen.AUDIO_SETTINGS, eth.class);
        this.register(NamedScreen.CONTROL_SETTINGS, etu.class);
        this.register(NamedScreen.CHAT_SETTINGS, erv.class);
        this.register(NamedScreen.ACCESSIBILITY_SETTINGS, err.class);
        this.register(NamedScreen.KEYBIND_SETTINGS, etw.class);
        this.register(NamedScreen.MOUSE_SETTINGS, ess.class);
        this.registerFactory((GameScreen)NamedScreen.SINGLEPLAYER, () -> {
            new ext((etd)new eti(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.MULTIPLAYER, () -> {
            new ewa((etd)new eti(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.OPTIONS, () -> {
            etd prevScreen = emh.N().z;
            if (prevScreen instanceof esk) {
                prevScreen = null;
            }
            return new esu(prevScreen, emh.N().m);
        });
        this.registerFactory((GameScreen)NamedScreen.CHAT_INPUT, () -> new erw(""));
        this.registerFactory((GameScreen)NamedScreen.INGAME_MENU, () -> new esy(true));
        this.registerFactory((GameScreen)NamedScreen.DIRECT_CONNECT, () -> {
            final etd prevScreen2 = emh.N().z;
            final fdq data = new fdq(fug.a("selectServer.defaultName", new Object[0]), "", false);
            return new esg(prevScreen2, join -> {
                if (join) {
                    erz.a(prevScreen2, emh.N(), fen.a(data.b), data);
                }
                else {
                    emh.N().a(prevScreen2);
                }
            }, data);
        });
        this.registerFactory(NamedScreen.CREDITS, (Function<etd, etd>)esc::new);
        this.registerFactory(NamedScreen.REALMS, (Function<etd, etd>)eho::new);
        this.registerFactory((GameScreen)NamedScreen.LANGUAGE_SELECTION, () -> new esn(emh.N().z, emh.N().m, emh.N().ad()));
        this.registerFactory((GameScreen)NamedScreen.ACCESSIBILITY_SETTINGS, () -> new err(emh.N().z, emh.N().m));
        this.registerFactory(NamedScreen.OPEN_LAN_WORLD, (Function<etd, etd>)ete::new);
    }
    
    @Override
    protected void registerFactory(final GameScreen screen, final Function<etd, etd> screenFactory) {
        this.registerFactory(screen, () -> screenFactory.apply(emh.N().z));
    }
    
    @Override
    public boolean isInventoryScreen(final Object screen) {
        return screen instanceof eub;
    }
}
