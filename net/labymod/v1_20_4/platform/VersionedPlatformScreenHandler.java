// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.platform;

import java.util.function.Function;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.core.platform.PlatformScreenHandler;

public class VersionedPlatformScreenHandler extends PlatformScreenHandler<fdb>
{
    @Override
    public void onInitialize() {
        this.register(NamedScreen.SINGLEPLAYER, fhx.class);
        this.register(NamedScreen.MULTIPLAYER, ffz.class);
        this.register(NamedScreen.EDIT_SERVER, fce.class);
        this.register(NamedScreen.MAIN_MENU, fdg.class);
        this.register(NamedScreen.CHAT_INPUT, fbs.class);
        this.register(NamedScreen.CHAT_INPUT_IN_BED, fcj.class);
        this.register(NamedScreen.INGAME_MENU, fcw.class);
        this.register(NamedScreen.INVENTORY, ffa.class);
        this.register(NamedScreen.CREATIVE_INVENTORY, fep.class);
        this.register(NamedScreen.CONNECTING, fbv.class);
        this.register(NamedScreen.DISCONNECTED, fcd.class);
        this.register(NamedScreen.CREDITS, fby.class);
        this.register(NamedScreen.REALMS, eqm.class);
        this.register(NamedScreen.CREATE_WORLD, fhr.class);
        this.register(NamedScreen.LEVEL_LOADING, fcl.class);
        this.register(NamedScreen.RECEIVING_LEVEL, fcz.class);
        this.register(NamedScreen.PACK_CONFIRM, fnp.b.class);
        this.register(NamedScreen.PROGRESS, fcy.class);
        this.register(NamedScreen.GENERIC_MESSAGE, fch.class);
        this.register(NamedScreen.OPEN_LAN_WORLD, fdc.class);
        this.register(NamedScreen.STATISTICS, fdk.class);
        this.register(NamedScreen.ADVANCEMENTS, fdr.class);
        this.register(NamedScreen.CONFIRM, fbu.class);
        this.register(NamedScreen.SOCIAL_INTERACTIONS, fhl.class);
        this.register(NamedScreen.EDIT_BOOK, feg.class);
        this.register(NamedScreen.OPTIONS, fcs.class);
        this.register(NamedScreen.SKIN_CUSTOMIZATION, fde.class);
        this.register(NamedScreen.VIDEO_SETTINGS, fdi.class);
        this.register(NamedScreen.LANGUAGE_SELECTION, fck.class);
        this.register(NamedScreen.RESOURCE_PACK_SETTINGS, fgi.class);
        this.register(NamedScreen.AUDIO_SETTINGS, fdf.class);
        this.register(NamedScreen.CONTROL_SETTINGS, fdt.class);
        this.register(NamedScreen.CHAT_SETTINGS, fbr.class);
        this.register(NamedScreen.ACCESSIBILITY_SETTINGS, fbn.class);
        this.register(NamedScreen.KEYBIND_SETTINGS, fdv.class);
        this.register(NamedScreen.MOUSE_SETTINGS, fcp.class);
        this.registerFactory((GameScreen)NamedScreen.SINGLEPLAYER, () -> {
            new fhx((fdb)new fdg(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.MULTIPLAYER, () -> {
            new ffz((fdb)new fdg(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.OPTIONS, () -> {
            fdb prevScreen = evi.O().y;
            if (prevScreen instanceof fch) {
                prevScreen = null;
            }
            return new fcs(prevScreen, evi.O().m);
        });
        this.registerFactory((GameScreen)NamedScreen.CHAT_INPUT, () -> new fbs(""));
        this.registerFactory((GameScreen)NamedScreen.INGAME_MENU, () -> new fcw(true));
        this.registerFactory((GameScreen)NamedScreen.DIRECT_CONNECT, () -> {
            final fdb prevScreen2 = evi.O().y;
            final fod data = new fod(gfs.a("selectServer.defaultName", new Object[0]), "", fod.b.c);
            return new fcc(prevScreen2, join -> {
                if (join) {
                    fbv.a(prevScreen2, evi.O(), fpf.a(data.b), data, false);
                }
                else {
                    evi.O().a(prevScreen2);
                }
            }, data);
        });
        this.registerFactory(NamedScreen.CREDITS, (Function<fdb, fdb>)fby::new);
        this.registerFactory(NamedScreen.REALMS, (Function<fdb, fdb>)eqm::new);
        this.registerFactory((GameScreen)NamedScreen.LANGUAGE_SELECTION, () -> new fck(evi.O().y, evi.O().m, evi.O().ae()));
        this.registerFactory((GameScreen)NamedScreen.ACCESSIBILITY_SETTINGS, () -> new fbn(evi.O().y, evi.O().m));
        this.registerFactory(NamedScreen.OPEN_LAN_WORLD, (Function<fdb, fdb>)fdc::new);
    }
    
    @Override
    protected void registerFactory(final GameScreen screen, final Function<fdb, fdb> screenFactory) {
        this.registerFactory(screen, () -> screenFactory.apply(evi.O().y));
    }
    
    @Override
    public boolean isInventoryScreen(final Object screen) {
        return screen instanceof fea;
    }
}
