// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.platform;

import java.util.function.Function;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.core.platform.PlatformScreenHandler;

public class VersionedPlatformScreenHandler extends PlatformScreenHandler<fod>
{
    @Override
    public void onInitialize() {
        this.register(NamedScreen.SINGLEPLAYER, fti.class);
        this.register(NamedScreen.MULTIPLAYER, fqt.class);
        this.register(NamedScreen.EDIT_SERVER, fnl.class);
        this.register(NamedScreen.MAIN_MENU, fof.class);
        this.register(NamedScreen.CHAT_INPUT, fmz.class);
        this.register(NamedScreen.CHAT_INPUT_IN_BED, fnq.class);
        this.register(NamedScreen.INGAME_MENU, fny.class);
        this.register(NamedScreen.INVENTORY, fpt.class);
        this.register(NamedScreen.CREATIVE_INVENTORY, fpi.class);
        this.register(NamedScreen.CONNECTING, fnc.class);
        this.register(NamedScreen.DISCONNECTED, fnk.class);
        this.register(NamedScreen.CREDITS, fnf.class);
        this.register(NamedScreen.REALMS, fbt.class);
        this.register(NamedScreen.CREATE_WORLD, ftc.class);
        this.register(NamedScreen.LEVEL_LOADING, fnr.class);
        this.register(NamedScreen.RECEIVING_LEVEL, fob.class);
        this.register(NamedScreen.PACK_CONFIRM, fzc.b.class);
        this.register(NamedScreen.PROGRESS, foa.class);
        this.register(NamedScreen.GENERIC_MESSAGE, fno.class);
        this.register(NamedScreen.OPEN_LAN_WORLD, foe.class);
        this.register(NamedScreen.STATISTICS, foh.class);
        this.register(NamedScreen.ADVANCEMENTS, fon.class);
        this.register(NamedScreen.CONFIRM, fnb.class);
        this.register(NamedScreen.SOCIAL_INTERACTIONS, fsw.class);
        this.register(NamedScreen.EDIT_BOOK, foz.class);
        this.register(NamedScreen.OPTIONS, frg.class);
        this.register(NamedScreen.SKIN_CUSTOMIZATION, fri.class);
        this.register(NamedScreen.VIDEO_SETTINGS, frl.class);
        this.register(NamedScreen.LANGUAGE_SELECTION, frd.class);
        this.register(NamedScreen.RESOURCE_PACK_SETTINGS, frt.class);
        this.register(NamedScreen.AUDIO_SETTINGS, frj.class);
        this.register(NamedScreen.CONTROL_SETTINGS, frm.class);
        this.register(NamedScreen.CHAT_SETTINGS, frb.class);
        this.register(NamedScreen.ACCESSIBILITY_SETTINGS, fra.class);
        this.register(NamedScreen.KEYBIND_SETTINGS, fro.class);
        this.register(NamedScreen.MOUSE_SETTINGS, fre.class);
        this.registerFactory((GameScreen)NamedScreen.SINGLEPLAYER, () -> {
            new fti((fod)new fof(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.MULTIPLAYER, () -> {
            new fqt((fod)new fof(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.OPTIONS, () -> {
            fod prevScreen = fgo.Q().y;
            if (prevScreen instanceof fno) {
                prevScreen = null;
            }
            return new frg(prevScreen, fgo.Q().m);
        });
        this.registerFactory((GameScreen)NamedScreen.CHAT_INPUT, () -> new fmz(""));
        this.registerFactory((GameScreen)NamedScreen.INGAME_MENU, () -> new fny(true));
        this.registerFactory((GameScreen)NamedScreen.DIRECT_CONNECT, () -> {
            final fod prevScreen2 = fgo.Q().y;
            final fzt data = new fzt(grr.a("selectServer.defaultName", new Object[0]), "", fzt.c.c);
            return new fnj(prevScreen2, join -> {
                if (join) {
                    fnc.a(prevScreen2, fgo.Q(), gax.a(data.b), data, false, (fzy)null);
                }
                else {
                    fgo.Q().a(prevScreen2);
                }
            }, data);
        });
        this.registerFactory(NamedScreen.CREDITS, (Function<fod, fod>)fnf::new);
        this.registerFactory(NamedScreen.REALMS, (Function<fod, fod>)fbt::new);
        this.registerFactory((GameScreen)NamedScreen.LANGUAGE_SELECTION, () -> new frd(fgo.Q().y, fgo.Q().m, fgo.Q().ag()));
        this.registerFactory((GameScreen)NamedScreen.ACCESSIBILITY_SETTINGS, () -> new fra(fgo.Q().y, fgo.Q().m));
        this.registerFactory(NamedScreen.OPEN_LAN_WORLD, (Function<fod, fod>)foe::new);
    }
    
    @Override
    protected void registerFactory(final GameScreen screen, final Function<fod, fod> screenFactory) {
        this.registerFactory(screen, () -> screenFactory.apply(fgo.Q().y));
    }
    
    @Override
    public boolean isInventoryScreen(final Object screen) {
        return screen instanceof fot;
    }
}
