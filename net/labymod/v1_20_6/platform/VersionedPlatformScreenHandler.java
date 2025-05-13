// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.platform;

import java.util.function.Function;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.core.platform.PlatformScreenHandler;

public class VersionedPlatformScreenHandler extends PlatformScreenHandler<fnf>
{
    @Override
    public void onInitialize() {
        this.register(NamedScreen.SINGLEPLAYER, fsa.class);
        this.register(NamedScreen.MULTIPLAYER, fqd.class);
        this.register(NamedScreen.EDIT_SERVER, fmh.class);
        this.register(NamedScreen.MAIN_MENU, fnk.class);
        this.register(NamedScreen.CHAT_INPUT, flv.class);
        this.register(NamedScreen.CHAT_INPUT_IN_BED, fmn.class);
        this.register(NamedScreen.INGAME_MENU, fna.class);
        this.register(NamedScreen.INVENTORY, fpe.class);
        this.register(NamedScreen.CREATIVE_INVENTORY, fot.class);
        this.register(NamedScreen.CONNECTING, fly.class);
        this.register(NamedScreen.DISCONNECTED, fmg.class);
        this.register(NamedScreen.CREDITS, fmb.class);
        this.register(NamedScreen.REALMS, fal.class);
        this.register(NamedScreen.CREATE_WORLD, fru.class);
        this.register(NamedScreen.LEVEL_LOADING, fmp.class);
        this.register(NamedScreen.RECEIVING_LEVEL, fnd.class);
        this.register(NamedScreen.PACK_CONFIRM, fxu.b.class);
        this.register(NamedScreen.PROGRESS, fnc.class);
        this.register(NamedScreen.GENERIC_MESSAGE, fml.class);
        this.register(NamedScreen.OPEN_LAN_WORLD, fng.class);
        this.register(NamedScreen.STATISTICS, fno.class);
        this.register(NamedScreen.ADVANCEMENTS, fnu.class);
        this.register(NamedScreen.CONFIRM, flx.class);
        this.register(NamedScreen.SOCIAL_INTERACTIONS, fro.class);
        this.register(NamedScreen.EDIT_BOOK, foj.class);
        this.register(NamedScreen.OPTIONS, fmw.class);
        this.register(NamedScreen.SKIN_CUSTOMIZATION, fni.class);
        this.register(NamedScreen.VIDEO_SETTINGS, fnm.class);
        this.register(NamedScreen.LANGUAGE_SELECTION, fmo.class);
        this.register(NamedScreen.RESOURCE_PACK_SETTINGS, fql.class);
        this.register(NamedScreen.AUDIO_SETTINGS, fnj.class);
        this.register(NamedScreen.CONTROL_SETTINGS, fnw.class);
        this.register(NamedScreen.CHAT_SETTINGS, flu.class);
        this.register(NamedScreen.ACCESSIBILITY_SETTINGS, flq.class);
        this.register(NamedScreen.KEYBIND_SETTINGS, fny.class);
        this.register(NamedScreen.MOUSE_SETTINGS, fmt.class);
        this.registerFactory((GameScreen)NamedScreen.SINGLEPLAYER, () -> {
            new fsa((fnf)new fnk(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.MULTIPLAYER, () -> {
            new fqd((fnf)new fnk(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.OPTIONS, () -> {
            fnf prevScreen = ffh.Q().y;
            if (prevScreen instanceof fml) {
                prevScreen = null;
            }
            return new fmw(prevScreen, ffh.Q().m);
        });
        this.registerFactory((GameScreen)NamedScreen.CHAT_INPUT, () -> new flv(""));
        this.registerFactory((GameScreen)NamedScreen.INGAME_MENU, () -> new fna(true));
        this.registerFactory((GameScreen)NamedScreen.DIRECT_CONNECT, () -> {
            final fnf prevScreen2 = ffh.Q().y;
            final fyl data = new fyl(gqh.a("selectServer.defaultName", new Object[0]), "", fyl.c.c);
            return new fmf(prevScreen2, join -> {
                if (join) {
                    fly.a(prevScreen2, ffh.Q(), fzo.a(data.b), data, false, (fyp)null);
                }
                else {
                    ffh.Q().a(prevScreen2);
                }
            }, data);
        });
        this.registerFactory(NamedScreen.CREDITS, (Function<fnf, fnf>)fmb::new);
        this.registerFactory(NamedScreen.REALMS, (Function<fnf, fnf>)fal::new);
        this.registerFactory((GameScreen)NamedScreen.LANGUAGE_SELECTION, () -> new fmo(ffh.Q().y, ffh.Q().m, ffh.Q().ag()));
        this.registerFactory((GameScreen)NamedScreen.ACCESSIBILITY_SETTINGS, () -> new flq(ffh.Q().y, ffh.Q().m));
        this.registerFactory(NamedScreen.OPEN_LAN_WORLD, (Function<fnf, fnf>)fng::new);
    }
    
    @Override
    protected void registerFactory(final GameScreen screen, final Function<fnf, fnf> screenFactory) {
        this.registerFactory(screen, () -> screenFactory.apply(ffh.Q().y));
    }
    
    @Override
    public boolean isInventoryScreen(final Object screen) {
        return screen instanceof fod;
    }
}
