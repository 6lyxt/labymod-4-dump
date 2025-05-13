// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.platform;

import java.util.function.Function;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.core.platform.PlatformScreenHandler;

public class VersionedPlatformScreenHandler extends PlatformScreenHandler<fty>
{
    @Override
    public void onInitialize() {
        this.register(NamedScreen.SINGLEPLAYER, fzf.class);
        this.register(NamedScreen.MULTIPLAYER, fwo.class);
        this.register(NamedScreen.EDIT_SERVER, ftg.class);
        this.register(NamedScreen.MAIN_MENU, fua.class);
        this.register(NamedScreen.CHAT_INPUT, fsu.class);
        this.register(NamedScreen.CHAT_INPUT_IN_BED, ftl.class);
        this.register(NamedScreen.INGAME_MENU, ftt.class);
        this.register(NamedScreen.INVENTORY, fvo.class);
        this.register(NamedScreen.CREATIVE_INVENTORY, fvd.class);
        this.register(NamedScreen.CONNECTING, fsx.class);
        this.register(NamedScreen.DISCONNECTED, ftf.class);
        this.register(NamedScreen.CREDITS, fta.class);
        this.register(NamedScreen.REALMS, fhd.class);
        this.register(NamedScreen.CREATE_WORLD, fyx.class);
        this.register(NamedScreen.LEVEL_LOADING, ftm.class);
        this.register(NamedScreen.RECEIVING_LEVEL, ftw.class);
        this.register(NamedScreen.PACK_CONFIRM, gfh.b.class);
        this.register(NamedScreen.PROGRESS, ftv.class);
        this.register(NamedScreen.GENERIC_MESSAGE, ftj.class);
        this.register(NamedScreen.OPEN_LAN_WORLD, ftz.class);
        this.register(NamedScreen.STATISTICS, fuc.class);
        this.register(NamedScreen.ADVANCEMENTS, fui.class);
        this.register(NamedScreen.CONFIRM, fsw.class);
        this.register(NamedScreen.SOCIAL_INTERACTIONS, fyq.class);
        this.register(NamedScreen.EDIT_BOOK, fuu.class);
        this.register(NamedScreen.OPTIONS, fxb.class);
        this.register(NamedScreen.SKIN_CUSTOMIZATION, fxd.class);
        this.register(NamedScreen.VIDEO_SETTINGS, fxg.class);
        this.register(NamedScreen.LANGUAGE_SELECTION, fwy.class);
        this.register(NamedScreen.RESOURCE_PACK_SETTINGS, fxo.class);
        this.register(NamedScreen.AUDIO_SETTINGS, fxe.class);
        this.register(NamedScreen.CONTROL_SETTINGS, fxh.class);
        this.register(NamedScreen.CHAT_SETTINGS, fww.class);
        this.register(NamedScreen.ACCESSIBILITY_SETTINGS, fwv.class);
        this.register(NamedScreen.KEYBIND_SETTINGS, fxj.class);
        this.register(NamedScreen.MOUSE_SETTINGS, fwz.class);
        this.registerFactory((GameScreen)NamedScreen.SINGLEPLAYER, () -> {
            new fzf((fty)new fua(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.MULTIPLAYER, () -> {
            new fwo((fty)new fua(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.OPTIONS, () -> {
            fty prevScreen = fmg.Q().z;
            if (prevScreen instanceof ftj) {
                prevScreen = null;
            }
            return new fxb(prevScreen, fmg.Q().n);
        });
        this.registerFactory((GameScreen)NamedScreen.CHAT_INPUT, () -> new fsu(""));
        this.registerFactory((GameScreen)NamedScreen.INGAME_MENU, () -> new ftt(true));
        this.registerFactory((GameScreen)NamedScreen.DIRECT_CONNECT, () -> {
            final fty prevScreen2 = fmg.Q().z;
            final gfz data = new gfz(hcs.a("selectServer.defaultName", new Object[0]), "", gfz.c.c);
            return new fte(prevScreen2, join -> {
                if (join) {
                    fsx.a(prevScreen2, fmg.Q(), ghc.a(data.b), data, false, (ggd)null);
                }
                else {
                    fmg.Q().a(prevScreen2);
                }
            }, data);
        });
        this.registerFactory(NamedScreen.CREDITS, (Function<fty, fty>)fta::new);
        this.registerFactory(NamedScreen.REALMS, (Function<fty, fty>)fhd::new);
        this.registerFactory((GameScreen)NamedScreen.LANGUAGE_SELECTION, () -> new fwy(fmg.Q().z, fmg.Q().n, fmg.Q().ah()));
        this.registerFactory((GameScreen)NamedScreen.ACCESSIBILITY_SETTINGS, () -> new fwv(fmg.Q().z, fmg.Q().n));
        this.registerFactory(NamedScreen.OPEN_LAN_WORLD, (Function<fty, fty>)ftz::new);
    }
    
    @Override
    protected void registerFactory(final GameScreen screen, final Function<fty, fty> screenFactory) {
        this.registerFactory(screen, () -> screenFactory.apply(fmg.Q().z));
    }
    
    @Override
    public boolean isInventoryScreen(final Object screen) {
        return screen instanceof fun;
    }
}
