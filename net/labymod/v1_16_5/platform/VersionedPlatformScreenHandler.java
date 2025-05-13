// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.platform;

import java.util.function.Function;
import com.google.common.util.concurrent.Runnables;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.core.platform.PlatformScreenHandler;

public class VersionedPlatformScreenHandler extends PlatformScreenHandler<dot>
{
    @Override
    public void onInitialize() {
        this.register(NamedScreen.SINGLEPLAYER, dsj.class);
        this.register(NamedScreen.MULTIPLAYER, drc.class);
        this.register(NamedScreen.EDIT_SERVER, dob.class);
        this.register(NamedScreen.MAIN_MENU, doy.class);
        this.register(NamedScreen.CHAT_INPUT, dnq.class);
        this.register(NamedScreen.CHAT_INPUT_IN_BED, doe.class);
        this.register(NamedScreen.INGAME_MENU, doo.class);
        this.register(NamedScreen.INVENTORY, dql.class);
        this.register(NamedScreen.CREATIVE_INVENTORY, dqc.class);
        this.register(NamedScreen.CONNECTING, dnt.class);
        this.register(NamedScreen.DISCONNECTED, doa.class);
        this.register(NamedScreen.CREDITS, dpa.class);
        this.register(NamedScreen.CREATE_WORLD, dsf.class);
        this.register(NamedScreen.LEVEL_LOADING, dog.class);
        this.register(NamedScreen.RECEIVING_LEVEL, dos.class);
        this.register(NamedScreen.PROGRESS, dor.class);
        this.register(NamedScreen.GENERIC_MESSAGE, dod.class);
        this.register(NamedScreen.OPEN_LAN_WORLD, dou.class);
        this.register(NamedScreen.STATISTICS, dpb.class);
        this.register(NamedScreen.ADVANCEMENTS, dpi.class);
        this.register(NamedScreen.CONFIRM, dns.class);
        this.register(NamedScreen.SOCIAL_INTERACTIONS, dsc.class);
        this.register(NamedScreen.EDIT_BOOK, dpu.class);
        this.register(NamedScreen.OPTIONS, dok.class);
        this.register(NamedScreen.SKIN_CUSTOMIZATION, dow.class);
        this.register(NamedScreen.VIDEO_SETTINGS, doz.class);
        this.register(NamedScreen.LANGUAGE_SELECTION, dof.class);
        this.register(NamedScreen.RESOURCE_PACK_SETTINGS, dri.class);
        this.register(NamedScreen.AUDIO_SETTINGS, dox.class);
        this.register(NamedScreen.CONTROL_SETTINGS, dpl.class);
        this.register(NamedScreen.CHAT_SETTINGS, dnp.class);
        this.register(NamedScreen.ACCESSIBILITY_SETTINGS, dnm.class);
        this.registerFactory((GameScreen)NamedScreen.SINGLEPLAYER, () -> {
            new dsj((dot)new doy(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.MULTIPLAYER, () -> {
            new drc((dot)new doy(false));
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.OPTIONS, () -> {
            dot prevScreen = djz.C().y;
            if (prevScreen instanceof dod) {
                prevScreen = null;
            }
            return new dok(prevScreen, djz.C().k);
        });
        this.registerFactory((GameScreen)NamedScreen.CHAT_INPUT, () -> new dnq(""));
        this.registerFactory((GameScreen)NamedScreen.INGAME_MENU, () -> new doo(true));
        this.registerFactory((GameScreen)NamedScreen.DIRECT_CONNECT, () -> {
            final dot prevScreen2 = djz.C().y;
            final dwz data = new dwz(ekx.a("selectServer.defaultName", new Object[0]), "", false);
            return new dnz(prevScreen2, join -> {
                if (join) {
                    djz.C().a((dot)new dnt(prevScreen2, djz.C(), data));
                }
                else {
                    djz.C().a(prevScreen2);
                }
            }, data);
        });
        this.registerFactory((GameScreen)NamedScreen.CREDITS, () -> new dpa(false, Runnables.doNothing()));
        this.registerFactory((GameScreen)NamedScreen.LANGUAGE_SELECTION, () -> new dof(djz.C().y, djz.C().k, djz.C().R()));
        this.registerFactory((GameScreen)NamedScreen.ACCESSIBILITY_SETTINGS, () -> new dnm(djz.C().y, djz.C().k));
        this.registerFactory(NamedScreen.OPEN_LAN_WORLD, (Function<dot, dot>)dou::new);
    }
    
    @Override
    protected void registerFactory(final GameScreen screen, final Function<dot, dot> screenFactory) {
        this.registerFactory(screen, () -> screenFactory.apply(djz.C().y));
    }
    
    @Override
    public boolean isInventoryScreen(final Object screen) {
        return screen instanceof dpp;
    }
}
