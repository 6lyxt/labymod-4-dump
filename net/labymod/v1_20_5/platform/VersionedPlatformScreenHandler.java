// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.platform;

import java.util.function.Function;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.core.platform.PlatformScreenHandler;

public class VersionedPlatformScreenHandler extends PlatformScreenHandler<fne>
{
    @Override
    public void onInitialize() {
        this.register(NamedScreen.SINGLEPLAYER, frz.class);
        this.register(NamedScreen.MULTIPLAYER, fqc.class);
        this.register(NamedScreen.EDIT_SERVER, fmg.class);
        this.register(NamedScreen.MAIN_MENU, fnj.class);
        this.register(NamedScreen.CHAT_INPUT, flu.class);
        this.register(NamedScreen.CHAT_INPUT_IN_BED, fmm.class);
        this.register(NamedScreen.INGAME_MENU, fmz.class);
        this.register(NamedScreen.INVENTORY, fpd.class);
        this.register(NamedScreen.CREATIVE_INVENTORY, fos.class);
        this.register(NamedScreen.CONNECTING, flx.class);
        this.register(NamedScreen.DISCONNECTED, fmf.class);
        this.register(NamedScreen.CREDITS, fma.class);
        this.register(NamedScreen.REALMS, fak.class);
        this.register(NamedScreen.CREATE_WORLD, frt.class);
        this.register(NamedScreen.LEVEL_LOADING, fmo.class);
        this.register(NamedScreen.RECEIVING_LEVEL, fnc.class);
        this.register(NamedScreen.PACK_CONFIRM, fxt.b.class);
        this.register(NamedScreen.PROGRESS, fnb.class);
        this.register(NamedScreen.GENERIC_MESSAGE, fmk.class);
        this.register(NamedScreen.OPEN_LAN_WORLD, fnf.class);
        this.register(NamedScreen.STATISTICS, fnn.class);
        this.register(NamedScreen.ADVANCEMENTS, fnt.class);
        this.register(NamedScreen.CONFIRM, flw.class);
        this.register(NamedScreen.SOCIAL_INTERACTIONS, frn.class);
        this.register(NamedScreen.EDIT_BOOK, foi.class);
        this.register(NamedScreen.OPTIONS, fmv.class);
        this.register(NamedScreen.SKIN_CUSTOMIZATION, fnh.class);
        this.register(NamedScreen.VIDEO_SETTINGS, fnl.class);
        this.register(NamedScreen.LANGUAGE_SELECTION, fmn.class);
        this.register(NamedScreen.RESOURCE_PACK_SETTINGS, fqk.class);
        this.register(NamedScreen.AUDIO_SETTINGS, fni.class);
        this.register(NamedScreen.CONTROL_SETTINGS, fnv.class);
        this.register(NamedScreen.CHAT_SETTINGS, flt.class);
        this.register(NamedScreen.ACCESSIBILITY_SETTINGS, flp.class);
        this.register(NamedScreen.KEYBIND_SETTINGS, fnx.class);
        this.register(NamedScreen.MOUSE_SETTINGS, fms.class);
        this.registerFactory((GameScreen)NamedScreen.SINGLEPLAYER, () -> {
            new frz((fne)new fnj(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.MULTIPLAYER, () -> {
            new fqc((fne)new fnj(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.OPTIONS, () -> {
            fne prevScreen = ffg.Q().y;
            if (prevScreen instanceof fmk) {
                prevScreen = null;
            }
            return new fmv(prevScreen, ffg.Q().m);
        });
        this.registerFactory((GameScreen)NamedScreen.CHAT_INPUT, () -> new flu(""));
        this.registerFactory((GameScreen)NamedScreen.INGAME_MENU, () -> new fmz(true));
        this.registerFactory((GameScreen)NamedScreen.DIRECT_CONNECT, () -> {
            final fne prevScreen2 = ffg.Q().y;
            final fyk data = new fyk(gqg.a("selectServer.defaultName", new Object[0]), "", fyk.c.c);
            return new fme(prevScreen2, join -> {
                if (join) {
                    flx.a(prevScreen2, ffg.Q(), fzn.a(data.b), data, false, (fyo)null);
                }
                else {
                    ffg.Q().a(prevScreen2);
                }
            }, data);
        });
        this.registerFactory(NamedScreen.CREDITS, (Function<fne, fne>)fma::new);
        this.registerFactory(NamedScreen.REALMS, (Function<fne, fne>)fak::new);
        this.registerFactory((GameScreen)NamedScreen.LANGUAGE_SELECTION, () -> new fmn(ffg.Q().y, ffg.Q().m, ffg.Q().ag()));
        this.registerFactory((GameScreen)NamedScreen.ACCESSIBILITY_SETTINGS, () -> new flp(ffg.Q().y, ffg.Q().m));
        this.registerFactory(NamedScreen.OPEN_LAN_WORLD, (Function<fne, fne>)fnf::new);
    }
    
    @Override
    protected void registerFactory(final GameScreen screen, final Function<fne, fne> screenFactory) {
        this.registerFactory(screen, () -> screenFactory.apply(ffg.Q().y));
    }
    
    @Override
    public boolean isInventoryScreen(final Object screen) {
        return screen instanceof foc;
    }
}
