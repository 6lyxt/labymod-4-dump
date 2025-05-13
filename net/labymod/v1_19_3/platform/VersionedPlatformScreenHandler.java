// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.platform;

import java.util.function.Function;
import com.google.common.util.concurrent.Runnables;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.core.platform.PlatformScreenHandler;

public class VersionedPlatformScreenHandler extends PlatformScreenHandler<epb>
{
    @Override
    public void onInitialize() {
        this.register(NamedScreen.SINGLEPLAYER, etn.class);
        this.register(NamedScreen.MULTIPLAYER, erv.class);
        this.register(NamedScreen.EDIT_SERVER, eog.class);
        this.register(NamedScreen.MAIN_MENU, epg.class);
        this.register(NamedScreen.CHAT_INPUT, env.class);
        this.register(NamedScreen.CHAT_INPUT_IN_BED, eok.class);
        this.register(NamedScreen.INGAME_MENU, eow.class);
        this.register(NamedScreen.INVENTORY, eqx.class);
        this.register(NamedScreen.CREATIVE_INVENTORY, eqn.class);
        this.register(NamedScreen.CONNECTING, eny.class);
        this.register(NamedScreen.DISCONNECTED, eof.class);
        this.register(NamedScreen.CREDITS, epi.class);
        this.register(NamedScreen.CREATE_WORLD, eti.class);
        this.register(NamedScreen.LEVEL_LOADING, eom.class);
        this.register(NamedScreen.RECEIVING_LEVEL, epa.class);
        this.register(NamedScreen.PROGRESS, eoz.class);
        this.register(NamedScreen.GENERIC_MESSAGE, eoi.class);
        this.register(NamedScreen.OPEN_LAN_WORLD, epc.class);
        this.register(NamedScreen.STATISTICS, epj.class);
        this.register(NamedScreen.ADVANCEMENTS, epq.class);
        this.register(NamedScreen.CONFIRM, enx.class);
        this.register(NamedScreen.SOCIAL_INTERACTIONS, etc.class);
        this.register(NamedScreen.EDIT_BOOK, eqf.class);
        this.register(NamedScreen.OPTIONS, eos.class);
        this.register(NamedScreen.SKIN_CUSTOMIZATION, epe.class);
        this.register(NamedScreen.VIDEO_SETTINGS, eph.class);
        this.register(NamedScreen.LANGUAGE_SELECTION, eol.class);
        this.register(NamedScreen.RESOURCE_PACK_SETTINGS, esd.class);
        this.register(NamedScreen.AUDIO_SETTINGS, epf.class);
        this.register(NamedScreen.CONTROL_SETTINGS, eps.class);
        this.register(NamedScreen.CHAT_SETTINGS, enu.class);
        this.register(NamedScreen.ACCESSIBILITY_SETTINGS, enq.class);
        this.register(NamedScreen.KEYBIND_SETTINGS, epu.class);
        this.register(NamedScreen.MOUSE_SETTINGS, eoq.class);
        this.registerFactory((GameScreen)NamedScreen.SINGLEPLAYER, () -> {
            new etn((epb)new epg(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.MULTIPLAYER, () -> {
            new erv((epb)new epg(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.OPTIONS, () -> {
            epb prevScreen = ejf.N().z;
            if (prevScreen instanceof eoi) {
                prevScreen = null;
            }
            return new eos(prevScreen, ejf.N().m);
        });
        this.registerFactory((GameScreen)NamedScreen.CHAT_INPUT, () -> new env(""));
        this.registerFactory((GameScreen)NamedScreen.INGAME_MENU, () -> new eow(true));
        this.registerFactory((GameScreen)NamedScreen.DIRECT_CONNECT, () -> {
            final epb prevScreen2 = ejf.N().z;
            final ezg data = new ezg(fpo.a("selectServer.defaultName", new Object[0]), "", false);
            return new eoe(prevScreen2, join -> {
                if (join) {
                    eny.a(prevScreen2, ejf.N(), fac.a(data.b), data);
                }
                else {
                    ejf.N().a(prevScreen2);
                }
            }, data);
        });
        this.registerFactory((GameScreen)NamedScreen.CREDITS, () -> new epi(false, Runnables.doNothing()));
        this.registerFactory((GameScreen)NamedScreen.LANGUAGE_SELECTION, () -> new eol(ejf.N().z, ejf.N().m, ejf.N().ad()));
        this.registerFactory((GameScreen)NamedScreen.ACCESSIBILITY_SETTINGS, () -> new enq(ejf.N().z, ejf.N().m));
        this.registerFactory(NamedScreen.OPEN_LAN_WORLD, (Function<epb, epb>)epc::new);
    }
    
    @Override
    protected void registerFactory(final GameScreen screen, final Function<epb, epb> screenFactory) {
        this.registerFactory(screen, () -> screenFactory.apply(ejf.N().z));
    }
    
    @Override
    public boolean isInventoryScreen(final Object screen) {
        return screen instanceof epz;
    }
}
