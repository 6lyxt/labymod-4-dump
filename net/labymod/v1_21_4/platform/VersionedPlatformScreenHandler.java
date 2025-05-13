// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.platform;

import java.util.function.Function;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.core.platform.PlatformScreenHandler;

public class VersionedPlatformScreenHandler extends PlatformScreenHandler<fum>
{
    @Override
    public void onInitialize() {
        this.register(NamedScreen.SINGLEPLAYER, fzt.class);
        this.register(NamedScreen.MULTIPLAYER, fxc.class);
        this.register(NamedScreen.EDIT_SERVER, ftu.class);
        this.register(NamedScreen.MAIN_MENU, fuo.class);
        this.register(NamedScreen.CHAT_INPUT, fti.class);
        this.register(NamedScreen.CHAT_INPUT_IN_BED, ftz.class);
        this.register(NamedScreen.INGAME_MENU, fuh.class);
        this.register(NamedScreen.INVENTORY, fwc.class);
        this.register(NamedScreen.CREATIVE_INVENTORY, fvr.class);
        this.register(NamedScreen.CONNECTING, ftl.class);
        this.register(NamedScreen.DISCONNECTED, ftt.class);
        this.register(NamedScreen.CREDITS, fto.class);
        this.register(NamedScreen.REALMS, fgg.class);
        this.register(NamedScreen.CREATE_WORLD, fzl.class);
        this.register(NamedScreen.LEVEL_LOADING, fua.class);
        this.register(NamedScreen.RECEIVING_LEVEL, fuk.class);
        this.register(NamedScreen.PACK_CONFIRM, gfx.b.class);
        this.register(NamedScreen.PROGRESS, fuj.class);
        this.register(NamedScreen.GENERIC_MESSAGE, ftx.class);
        this.register(NamedScreen.OPEN_LAN_WORLD, fun.class);
        this.register(NamedScreen.STATISTICS, fuq.class);
        this.register(NamedScreen.ADVANCEMENTS, fuw.class);
        this.register(NamedScreen.CONFIRM, ftk.class);
        this.register(NamedScreen.SOCIAL_INTERACTIONS, fze.class);
        this.register(NamedScreen.EDIT_BOOK, fvi.class);
        this.register(NamedScreen.OPTIONS, fxp.class);
        this.register(NamedScreen.SKIN_CUSTOMIZATION, fxr.class);
        this.register(NamedScreen.VIDEO_SETTINGS, fxu.class);
        this.register(NamedScreen.LANGUAGE_SELECTION, fxm.class);
        this.register(NamedScreen.RESOURCE_PACK_SETTINGS, fyc.class);
        this.register(NamedScreen.AUDIO_SETTINGS, fxs.class);
        this.register(NamedScreen.CONTROL_SETTINGS, fxv.class);
        this.register(NamedScreen.CHAT_SETTINGS, fxk.class);
        this.register(NamedScreen.ACCESSIBILITY_SETTINGS, fxj.class);
        this.register(NamedScreen.KEYBIND_SETTINGS, fxx.class);
        this.register(NamedScreen.MOUSE_SETTINGS, fxn.class);
        this.registerFactory((GameScreen)NamedScreen.SINGLEPLAYER, () -> {
            new fzt((fum)new fuo(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.MULTIPLAYER, () -> {
            new fxc((fum)new fuo(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.OPTIONS, () -> {
            fum prevScreen = flk.Q().z;
            if (prevScreen instanceof ftx) {
                prevScreen = null;
            }
            return new fxp(prevScreen, flk.Q().n);
        });
        this.registerFactory((GameScreen)NamedScreen.CHAT_INPUT, () -> new fti(""));
        this.registerFactory((GameScreen)NamedScreen.INGAME_MENU, () -> new fuh(true));
        this.registerFactory((GameScreen)NamedScreen.DIRECT_CONNECT, () -> {
            final fum prevScreen2 = flk.Q().z;
            final ggp data = new ggp(hgb.a("selectServer.defaultName", new Object[0]), "", ggp.c.c);
            return new fts(prevScreen2, join -> {
                if (join) {
                    ftl.a(prevScreen2, flk.Q(), ghs.a(data.b), data, false, (ggt)null);
                }
                else {
                    flk.Q().a(prevScreen2);
                }
            }, data);
        });
        this.registerFactory(NamedScreen.CREDITS, (Function<fum, fum>)fto::new);
        this.registerFactory(NamedScreen.REALMS, (Function<fum, fum>)fgg::new);
        this.registerFactory((GameScreen)NamedScreen.LANGUAGE_SELECTION, () -> new fxm(flk.Q().z, flk.Q().n, flk.Q().ah()));
        this.registerFactory((GameScreen)NamedScreen.ACCESSIBILITY_SETTINGS, () -> new fxj(flk.Q().z, flk.Q().n));
        this.registerFactory(NamedScreen.OPEN_LAN_WORLD, (Function<fum, fum>)fun::new);
    }
    
    @Override
    protected void registerFactory(final GameScreen screen, final Function<fum, fum> screenFactory) {
        this.registerFactory(screen, () -> screenFactory.apply(flk.Q().z));
    }
    
    @Override
    public boolean isInventoryScreen(final Object screen) {
        return screen instanceof fvb;
    }
}
