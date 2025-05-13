// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.platform;

import java.util.function.Function;
import com.google.common.util.concurrent.Runnables;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.core.platform.PlatformScreenHandler;

public class VersionedPlatformScreenHandler extends PlatformScreenHandler<elm>
{
    @Override
    public void onInitialize() {
        this.register(NamedScreen.SINGLEPLAYER, epq.class);
        this.register(NamedScreen.MULTIPLAYER, eob.class);
        this.register(NamedScreen.EDIT_SERVER, ekr.class);
        this.register(NamedScreen.MAIN_MENU, elr.class);
        this.register(NamedScreen.CHAT_INPUT, ekg.class);
        this.register(NamedScreen.CHAT_INPUT_IN_BED, ekv.class);
        this.register(NamedScreen.INGAME_MENU, elh.class);
        this.register(NamedScreen.INVENTORY, eng.class);
        this.register(NamedScreen.CREATIVE_INVENTORY, emx.class);
        this.register(NamedScreen.CONNECTING, ekj.class);
        this.register(NamedScreen.DISCONNECTED, ekq.class);
        this.register(NamedScreen.CREDITS, elt.class);
        this.register(NamedScreen.CREATE_WORLD, epl.class);
        this.register(NamedScreen.LEVEL_LOADING, ekx.class);
        this.register(NamedScreen.RECEIVING_LEVEL, ell.class);
        this.register(NamedScreen.PROGRESS, elk.class);
        this.register(NamedScreen.GENERIC_MESSAGE, ekt.class);
        this.register(NamedScreen.OPEN_LAN_WORLD, eln.class);
        this.register(NamedScreen.STATISTICS, elu.class);
        this.register(NamedScreen.ADVANCEMENTS, emb.class);
        this.register(NamedScreen.CONFIRM, eki.class);
        this.register(NamedScreen.SOCIAL_INTERACTIONS, epj.class);
        this.register(NamedScreen.EDIT_BOOK, emp.class);
        this.register(NamedScreen.OPTIONS, eld.class);
        this.register(NamedScreen.SKIN_CUSTOMIZATION, elp.class);
        this.register(NamedScreen.VIDEO_SETTINGS, els.class);
        this.register(NamedScreen.LANGUAGE_SELECTION, ekw.class);
        this.register(NamedScreen.RESOURCE_PACK_SETTINGS, eoj.class);
        this.register(NamedScreen.AUDIO_SETTINGS, elq.class);
        this.register(NamedScreen.CONTROL_SETTINGS, emd.class);
        this.register(NamedScreen.CHAT_SETTINGS, ekf.class);
        this.register(NamedScreen.ACCESSIBILITY_SETTINGS, ekb.class);
        this.register(NamedScreen.KEYBIND_SETTINGS, emf.class);
        this.register(NamedScreen.MOUSE_SETTINGS, elb.class);
        this.registerFactory((GameScreen)NamedScreen.SINGLEPLAYER, () -> {
            new epq((elm)new elr(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.MULTIPLAYER, () -> {
            new eob((elm)new elr(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.OPTIONS, () -> {
            elm prevScreen = efu.I().z;
            if (prevScreen instanceof ekt) {
                prevScreen = null;
            }
            return new eld(prevScreen, efu.I().m);
        });
        this.registerFactory((GameScreen)NamedScreen.CHAT_INPUT, () -> new ekg(""));
        this.registerFactory((GameScreen)NamedScreen.INGAME_MENU, () -> new elh(true));
        this.registerFactory((GameScreen)NamedScreen.DIRECT_CONNECT, () -> {
            final elm prevScreen2 = efu.I().z;
            final evb data = new evb(fkz.a("selectServer.defaultName", new Object[0]), "", false);
            return new ekp(prevScreen2, join -> {
                if (join) {
                    ekj.a(prevScreen2, efu.I(), evz.a(data.b), data);
                }
                else {
                    efu.I().a(prevScreen2);
                }
            }, data);
        });
        this.registerFactory((GameScreen)NamedScreen.CREDITS, () -> new elt(false, Runnables.doNothing()));
        this.registerFactory((GameScreen)NamedScreen.LANGUAGE_SELECTION, () -> new ekw(efu.I().z, efu.I().m, efu.I().W()));
        this.registerFactory((GameScreen)NamedScreen.ACCESSIBILITY_SETTINGS, () -> new ekb(efu.I().z, efu.I().m));
        this.registerFactory(NamedScreen.OPEN_LAN_WORLD, (Function<elm, elm>)eln::new);
    }
    
    @Override
    protected void registerFactory(final GameScreen screen, final Function<elm, elm> screenFactory) {
        this.registerFactory(screen, () -> screenFactory.apply(efu.I().z));
    }
    
    @Override
    public boolean isInventoryScreen(final Object screen) {
        return screen instanceof emk;
    }
}
