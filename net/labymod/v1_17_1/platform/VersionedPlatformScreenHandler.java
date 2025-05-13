// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.platform;

import java.util.function.Function;
import com.google.common.util.concurrent.Runnables;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.core.platform.PlatformScreenHandler;

public class VersionedPlatformScreenHandler extends PlatformScreenHandler<eaq>
{
    @Override
    public void onInitialize() {
        this.register(NamedScreen.SINGLEPLAYER, eei.class);
        this.register(NamedScreen.MULTIPLAYER, edc.class);
        this.register(NamedScreen.EDIT_SERVER, dzy.class);
        this.register(NamedScreen.MAIN_MENU, eav.class);
        this.register(NamedScreen.CHAT_INPUT, dzn.class);
        this.register(NamedScreen.CHAT_INPUT_IN_BED, eab.class);
        this.register(NamedScreen.INGAME_MENU, eal.class);
        this.register(NamedScreen.INVENTORY, ecj.class);
        this.register(NamedScreen.CREATIVE_INVENTORY, eca.class);
        this.register(NamedScreen.CONNECTING, dzq.class);
        this.register(NamedScreen.DISCONNECTED, dzx.class);
        this.register(NamedScreen.CREDITS, eax.class);
        this.register(NamedScreen.CREATE_WORLD, eee.class);
        this.register(NamedScreen.LEVEL_LOADING, ead.class);
        this.register(NamedScreen.RECEIVING_LEVEL, eap.class);
        this.register(NamedScreen.PROGRESS, eao.class);
        this.register(NamedScreen.GENERIC_MESSAGE, eaa.class);
        this.register(NamedScreen.OPEN_LAN_WORLD, ear.class);
        this.register(NamedScreen.STATISTICS, eay.class);
        this.register(NamedScreen.ADVANCEMENTS, ebf.class);
        this.register(NamedScreen.CONFIRM, dzp.class);
        this.register(NamedScreen.SOCIAL_INTERACTIONS, eec.class);
        this.register(NamedScreen.EDIT_BOOK, ebs.class);
        this.register(NamedScreen.OPTIONS, eah.class);
        this.register(NamedScreen.SKIN_CUSTOMIZATION, eat.class);
        this.register(NamedScreen.VIDEO_SETTINGS, eaw.class);
        this.register(NamedScreen.LANGUAGE_SELECTION, eac.class);
        this.register(NamedScreen.RESOURCE_PACK_SETTINGS, edi.class);
        this.register(NamedScreen.AUDIO_SETTINGS, eau.class);
        this.register(NamedScreen.CONTROL_SETTINGS, ebi.class);
        this.register(NamedScreen.CHAT_SETTINGS, dzm.class);
        this.register(NamedScreen.ACCESSIBILITY_SETTINGS, dzj.class);
        this.registerFactory((GameScreen)NamedScreen.SINGLEPLAYER, () -> {
            new eei((eaq)new eav(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.MULTIPLAYER, () -> {
            new edc((eaq)new eav(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.OPTIONS, () -> {
            eaq prevScreen = dvp.C().y;
            if (prevScreen instanceof eaa) {
                prevScreen = null;
            }
            return new eah(prevScreen, dvp.C().l);
        });
        this.registerFactory((GameScreen)NamedScreen.CHAT_INPUT, () -> new dzn(""));
        this.registerFactory((GameScreen)NamedScreen.INGAME_MENU, () -> new eal(true));
        this.registerFactory((GameScreen)NamedScreen.DIRECT_CONNECT, () -> {
            final eaq prevScreen2 = dvp.C().y;
            final ejn data = new ejn(eyh.a("selectServer.defaultName", new Object[0]), "", false);
            return new dzw(prevScreen2, join -> {
                if (join) {
                    dzq.a(prevScreen2, dvp.C(), ejt.a(data.b), data);
                }
                else {
                    dvp.C().a(prevScreen2);
                }
            }, data);
        });
        this.registerFactory((GameScreen)NamedScreen.CREDITS, () -> new eax(false, Runnables.doNothing()));
        this.registerFactory((GameScreen)NamedScreen.LANGUAGE_SELECTION, () -> new eac(dvp.C().y, dvp.C().l, dvp.C().R()));
        this.registerFactory((GameScreen)NamedScreen.ACCESSIBILITY_SETTINGS, () -> new dzj(dvp.C().y, dvp.C().l));
        this.registerFactory(NamedScreen.OPEN_LAN_WORLD, (Function<eaq, eaq>)ear::new);
    }
    
    @Override
    protected void registerFactory(final GameScreen screen, final Function<eaq, eaq> screenFactory) {
        this.registerFactory(screen, () -> screenFactory.apply(dvp.C().y));
    }
    
    @Override
    public boolean isInventoryScreen(final Object screen) {
        return screen instanceof ebn;
    }
}
