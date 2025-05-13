// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.platform;

import java.util.function.Function;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.core.platform.PlatformScreenHandler;

public class VersionedPlatformScreenHandler extends PlatformScreenHandler<euq>
{
    @Override
    public void onInitialize() {
        this.register(NamedScreen.SINGLEPLAYER, ezg.class);
        this.register(NamedScreen.MULTIPLAYER, exn.class);
        this.register(NamedScreen.EDIT_SERVER, etu.class);
        this.register(NamedScreen.MAIN_MENU, euw.class);
        this.register(NamedScreen.CHAT_INPUT, eti.class);
        this.register(NamedScreen.CHAT_INPUT_IN_BED, etz.class);
        this.register(NamedScreen.INGAME_MENU, eul.class);
        this.register(NamedScreen.INVENTORY, ewo.class);
        this.register(NamedScreen.CREATIVE_INVENTORY, ewd.class);
        this.register(NamedScreen.CONNECTING, etl.class);
        this.register(NamedScreen.DISCONNECTED, ett.class);
        this.register(NamedScreen.CREDITS, eto.class);
        this.register(NamedScreen.REALMS, eiu.class);
        this.register(NamedScreen.CREATE_WORLD, eza.class);
        this.register(NamedScreen.LEVEL_LOADING, eub.class);
        this.register(NamedScreen.RECEIVING_LEVEL, eup.class);
        this.register(NamedScreen.PROGRESS, euo.class);
        this.register(NamedScreen.GENERIC_MESSAGE, etx.class);
        this.register(NamedScreen.OPEN_LAN_WORLD, eur.class);
        this.register(NamedScreen.STATISTICS, euz.class);
        this.register(NamedScreen.ADVANCEMENTS, evg.class);
        this.register(NamedScreen.CONFIRM, etk.class);
        this.register(NamedScreen.SOCIAL_INTERACTIONS, eyu.class);
        this.register(NamedScreen.EDIT_BOOK, evv.class);
        this.register(NamedScreen.OPTIONS, euh.class);
        this.register(NamedScreen.SKIN_CUSTOMIZATION, eut.class);
        this.register(NamedScreen.VIDEO_SETTINGS, eux.class);
        this.register(NamedScreen.LANGUAGE_SELECTION, eua.class);
        this.register(NamedScreen.RESOURCE_PACK_SETTINGS, exv.class);
        this.register(NamedScreen.AUDIO_SETTINGS, euu.class);
        this.register(NamedScreen.CONTROL_SETTINGS, evi.class);
        this.register(NamedScreen.CHAT_SETTINGS, eth.class);
        this.register(NamedScreen.ACCESSIBILITY_SETTINGS, etd.class);
        this.register(NamedScreen.KEYBIND_SETTINGS, evk.class);
        this.register(NamedScreen.MOUSE_SETTINGS, euf.class);
        this.registerFactory((GameScreen)NamedScreen.SINGLEPLAYER, () -> {
            new ezg((euq)new euw(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.MULTIPLAYER, () -> {
            new exn((euq)new euw(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.OPTIONS, () -> {
            euq prevScreen = enn.N().z;
            if (prevScreen instanceof etx) {
                prevScreen = null;
            }
            return new euh(prevScreen, enn.N().m);
        });
        this.registerFactory((GameScreen)NamedScreen.CHAT_INPUT, () -> new eti(""));
        this.registerFactory((GameScreen)NamedScreen.INGAME_MENU, () -> new eul(true));
        this.registerFactory((GameScreen)NamedScreen.DIRECT_CONNECT, () -> {
            final euq prevScreen2 = enn.N().z;
            final ffd data = new ffd(fvz.a("selectServer.defaultName", new Object[0]), "", false);
            return new ets(prevScreen2, join -> {
                if (join) {
                    etl.a(prevScreen2, enn.N(), fga.a(data.b), data, false);
                }
                else {
                    enn.N().a(prevScreen2);
                }
            }, data);
        });
        this.registerFactory(NamedScreen.CREDITS, (Function<euq, euq>)eto::new);
        this.registerFactory(NamedScreen.REALMS, (Function<euq, euq>)eiu::new);
        this.registerFactory((GameScreen)NamedScreen.LANGUAGE_SELECTION, () -> new eua(enn.N().z, enn.N().m, enn.N().ad()));
        this.registerFactory((GameScreen)NamedScreen.ACCESSIBILITY_SETTINGS, () -> new etd(enn.N().z, enn.N().m));
        this.registerFactory(NamedScreen.OPEN_LAN_WORLD, (Function<euq, euq>)eur::new);
    }
    
    @Override
    protected void registerFactory(final GameScreen screen, final Function<euq, euq> screenFactory) {
        this.registerFactory(screen, () -> screenFactory.apply(enn.N().z));
    }
    
    @Override
    public boolean isInventoryScreen(final Object screen) {
        return screen instanceof evp;
    }
}
