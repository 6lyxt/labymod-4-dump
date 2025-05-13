// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.platform;

import net.labymod.v1_8_9.client.gui.screen.VersionedFunctionalConfirmScreen;
import java.util.function.Supplier;
import java.util.function.Function;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.core.platform.PlatformScreenHandler;

public class VersionedPlatformScreenHandler extends PlatformScreenHandler<axu>
{
    @Override
    public void onInitialize() {
        this.register(NamedScreen.SINGLEPLAYER, axv.class);
        this.register(NamedScreen.MULTIPLAYER, azh.class);
        this.register(NamedScreen.EDIT_SERVER, axi.class);
        this.register(NamedScreen.MAIN_MENU, aya.class);
        this.register(NamedScreen.CHAT_INPUT, awv.class);
        this.register(NamedScreen.CHAT_INPUT_IN_BED, axk.class);
        this.register(NamedScreen.INGAME_MENU, axp.class);
        this.register(NamedScreen.INVENTORY, azc.class);
        this.register(NamedScreen.CREATIVE_INVENTORY, ayu.class);
        this.register(NamedScreen.CONNECTING, awz.class);
        this.register(NamedScreen.DISCONNECTED, axh.class);
        this.register(NamedScreen.CREDITS, ayc.class);
        this.register(NamedScreen.CREATE_WORLD, axb.class);
        this.register(NamedScreen.LEVEL_LOADING, axs.class);
        this.register(NamedScreen.OPEN_LAN_WORLD, axw.class);
        this.register(NamedScreen.STATISTICS, ayf.class);
        this.register(NamedScreen.ADVANCEMENTS, aye.class);
        this.register(NamedScreen.CONFIRM, awy.class);
        this.register(NamedScreen.EDIT_BOOK, ayo.class);
        this.register(NamedScreen.OPTIONS, axn.class);
        this.register(NamedScreen.SKIN_CUSTOMIZATION, axx.class);
        this.register(NamedScreen.VIDEO_SETTINGS, ayb.class);
        this.register(NamedScreen.LANGUAGE_SELECTION, axl.class);
        this.register(NamedScreen.RESOURCE_PACK_SETTINGS, azo.class);
        this.register(NamedScreen.AUDIO_SETTINGS, axz.class);
        this.register(NamedScreen.CONTROL_SETTINGS, ayj.class);
        this.register(NamedScreen.CHAT_SETTINGS, awu.class);
        this.register(NamedScreen.SNOOPER_SETTINGS, axy.class);
        this.registerFactory(NamedScreen.SINGLEPLAYER, (Function<axu, axu>)axv::new);
        this.registerFactory(NamedScreen.MULTIPLAYER, (Function<axu, axu>)azh::new);
        this.registerFactory((GameScreen)NamedScreen.OPTIONS, () -> {
            final axu previousScreen = ave.A().m;
            return new axn(previousScreen, ave.A().t);
        });
        this.registerFactory((GameScreen)NamedScreen.CHAT_INPUT, () -> new awv(""));
        this.registerFactory(NamedScreen.INGAME_MENU, (Supplier<axu>)axp::new);
        this.registerFactory((GameScreen)NamedScreen.DIRECT_CONNECT, () -> {
            final axu prevScreen = ave.A().m;
            final bde data = new bde(bnq.a("selectServer.defaultName", new Object[0]), "", false);
            new axg((axu)new VersionedFunctionalConfirmScreen(0, join -> {
                if (join) {
                    ave.A().a((axu)new awz(prevScreen, ave.A(), data));
                }
                else {
                    ave.A().a(prevScreen);
                }
                return;
            }), data);
            return;
        });
        this.registerFactory(NamedScreen.CREDITS, (Supplier<axu>)ayc::new);
        this.registerFactory((GameScreen)NamedScreen.LANGUAGE_SELECTION, () -> new axl(ave.A().m, ave.A().t, ave.A().S()));
        this.registerFactory(NamedScreen.OPEN_LAN_WORLD, (Function<axu, axu>)axw::new);
    }
    
    public void registerFactory(final GameScreen screen, final Function<axu, axu> screenFactory) {
        this.registerFactory(screen, () -> screenFactory.apply(ave.A().m));
    }
    
    @Override
    public boolean isInventoryScreen(final Object screen) {
        return screen instanceof ayl;
    }
}
