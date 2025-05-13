// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.platform;

import java.util.function.Function;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.core.platform.PlatformScreenHandler;

public class VersionedPlatformScreenHandler extends PlatformScreenHandler<eyk>
{
    @Override
    public void onInitialize() {
        this.register(NamedScreen.SINGLEPLAYER, fde.class);
        this.register(NamedScreen.MULTIPLAYER, fbg.class);
        this.register(NamedScreen.EDIT_SERVER, exn.class);
        this.register(NamedScreen.MAIN_MENU, eyp.class);
        this.register(NamedScreen.CHAT_INPUT, exb.class);
        this.register(NamedScreen.CHAT_INPUT_IN_BED, exs.class);
        this.register(NamedScreen.INGAME_MENU, eyf.class);
        this.register(NamedScreen.INVENTORY, fah.class);
        this.register(NamedScreen.CREATIVE_INVENTORY, ezw.class);
        this.register(NamedScreen.CONNECTING, exe.class);
        this.register(NamedScreen.DISCONNECTED, exm.class);
        this.register(NamedScreen.CREDITS, exh.class);
        this.register(NamedScreen.REALMS, ema.class);
        this.register(NamedScreen.CREATE_WORLD, fcy.class);
        this.register(NamedScreen.LEVEL_LOADING, exu.class);
        this.register(NamedScreen.RECEIVING_LEVEL, eyj.class);
        this.register(NamedScreen.PROGRESS, eyi.class);
        this.register(NamedScreen.GENERIC_MESSAGE, exq.class);
        this.register(NamedScreen.OPEN_LAN_WORLD, eyl.class);
        this.register(NamedScreen.STATISTICS, eys.class);
        this.register(NamedScreen.ADVANCEMENTS, eyz.class);
        this.register(NamedScreen.CONFIRM, exd.class);
        this.register(NamedScreen.SOCIAL_INTERACTIONS, fcs.class);
        this.register(NamedScreen.EDIT_BOOK, ezo.class);
        this.register(NamedScreen.OPTIONS, eyb.class);
        this.register(NamedScreen.SKIN_CUSTOMIZATION, eyn.class);
        this.register(NamedScreen.VIDEO_SETTINGS, eyq.class);
        this.register(NamedScreen.LANGUAGE_SELECTION, ext.class);
        this.register(NamedScreen.RESOURCE_PACK_SETTINGS, fbp.class);
        this.register(NamedScreen.AUDIO_SETTINGS, eyo.class);
        this.register(NamedScreen.CONTROL_SETTINGS, ezb.class);
        this.register(NamedScreen.CHAT_SETTINGS, exa.class);
        this.register(NamedScreen.ACCESSIBILITY_SETTINGS, eww.class);
        this.register(NamedScreen.KEYBIND_SETTINGS, ezd.class);
        this.register(NamedScreen.MOUSE_SETTINGS, exy.class);
        this.registerFactory((GameScreen)NamedScreen.SINGLEPLAYER, () -> {
            new fde((eyk)new eyp(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.MULTIPLAYER, () -> {
            new fbg((eyk)new eyp(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.OPTIONS, () -> {
            eyk prevScreen = eqv.O().y;
            if (prevScreen instanceof exq) {
                prevScreen = null;
            }
            return new eyb(prevScreen, eqv.O().m);
        });
        this.registerFactory((GameScreen)NamedScreen.CHAT_INPUT, () -> new exb(""));
        this.registerFactory((GameScreen)NamedScreen.INGAME_MENU, () -> new eyf(true));
        this.registerFactory((GameScreen)NamedScreen.DIRECT_CONNECT, () -> {
            final eyk prevScreen2 = eqv.O().y;
            final fjh data = new fjh(gak.a("selectServer.defaultName", new Object[0]), "", fjh.b.c);
            return new exl(prevScreen2, join -> {
                if (join) {
                    exe.a(prevScreen2, eqv.O(), fki.a(data.b), data, false);
                }
                else {
                    eqv.O().a(prevScreen2);
                }
            }, data);
        });
        this.registerFactory(NamedScreen.CREDITS, (Function<eyk, eyk>)exh::new);
        this.registerFactory(NamedScreen.REALMS, (Function<eyk, eyk>)ema::new);
        this.registerFactory((GameScreen)NamedScreen.LANGUAGE_SELECTION, () -> new ext(eqv.O().y, eqv.O().m, eqv.O().ae()));
        this.registerFactory((GameScreen)NamedScreen.ACCESSIBILITY_SETTINGS, () -> new eww(eqv.O().y, eqv.O().m));
        this.registerFactory(NamedScreen.OPEN_LAN_WORLD, (Function<eyk, eyk>)eyl::new);
    }
    
    @Override
    protected void registerFactory(final GameScreen screen, final Function<eyk, eyk> screenFactory) {
        this.registerFactory(screen, () -> screenFactory.apply(eqv.O().y));
    }
    
    @Override
    public boolean isInventoryScreen(final Object screen) {
        return screen instanceof ezi;
    }
}
