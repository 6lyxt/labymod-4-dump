// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.platform;

import com.google.common.util.concurrent.Runnables;
import net.labymod.v1_12_2.client.gui.screen.VersionedFunctionalConfirmScreen;
import java.util.function.Supplier;
import java.util.function.Function;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.core.platform.PlatformScreenHandler;

public class VersionedPlatformScreenHandler extends PlatformScreenHandler<blk>
{
    @Override
    public void onInitialize() {
        this.register(NamedScreen.SINGLEPLAYER, bok.class);
        this.register(NamedScreen.MULTIPLAYER, bnf.class);
        this.register(NamedScreen.EDIT_SERVER, bkz.class);
        this.register(NamedScreen.MAIN_MENU, blr.class);
        this.register(NamedScreen.CHAT_INPUT, bkn.class);
        this.register(NamedScreen.CHAT_INPUT_IN_BED, blb.class);
        this.register(NamedScreen.INGAME_MENU, blg.class);
        this.register(NamedScreen.INVENTORY, bmx.class);
        this.register(NamedScreen.CREATIVE_INVENTORY, bmp.class);
        this.register(NamedScreen.CONNECTING, bkr.class);
        this.register(NamedScreen.DISCONNECTED, bky.class);
        this.register(NamedScreen.CREDITS, blt.class);
        this.register(NamedScreen.CREATE_WORLD, boi.class);
        this.register(NamedScreen.LEVEL_LOADING, blj.class);
        this.register(NamedScreen.OPEN_LAN_WORLD, bll.class);
        this.register(NamedScreen.STATISTICS, blu.class);
        this.register(NamedScreen.ADVANCEMENTS, bmb.class);
        this.register(NamedScreen.CONFIRM, bkq.class);
        this.register(NamedScreen.EDIT_BOOK, bmj.class);
        this.register(NamedScreen.OPTIONS, ble.class);
        this.register(NamedScreen.SKIN_CUSTOMIZATION, blm.class);
        this.register(NamedScreen.VIDEO_SETTINGS, bls.class);
        this.register(NamedScreen.LANGUAGE_SELECTION, blc.class);
        this.register(NamedScreen.RESOURCE_PACK_SETTINGS, bnw.class);
        this.register(NamedScreen.AUDIO_SETTINGS, blo.class);
        this.register(NamedScreen.CONTROL_SETTINGS, bme.class);
        this.register(NamedScreen.CHAT_SETTINGS, bkm.class);
        this.register(NamedScreen.SNOOPER_SETTINGS, bln.class);
        this.registerFactory(NamedScreen.SINGLEPLAYER, (Function<blk, blk>)bok::new);
        this.registerFactory(NamedScreen.MULTIPLAYER, (Function<blk, blk>)bnf::new);
        this.registerFactory((GameScreen)NamedScreen.OPTIONS, () -> {
            final blk previousScreen = bib.z().m;
            return new ble(previousScreen, bib.z().t);
        });
        this.registerFactory((GameScreen)NamedScreen.CHAT_INPUT, () -> new bkn(""));
        this.registerFactory(NamedScreen.INGAME_MENU, (Supplier<blk>)blg::new);
        this.registerFactory((GameScreen)NamedScreen.DIRECT_CONNECT, () -> {
            final blk prevScreen = bib.z().m;
            final bse data = new bse(cey.a("selectServer.defaultName", new Object[0]), "", false);
            new bkx((blk)new VersionedFunctionalConfirmScreen(0, join -> {
                if (join) {
                    bib.z().a((blk)new bkr(prevScreen, bib.z(), data));
                }
                else {
                    bib.z().a(prevScreen);
                }
                return;
            }), data);
            return;
        });
        this.registerFactory((GameScreen)NamedScreen.CREDITS, () -> new blt(false, Runnables.doNothing()));
        this.registerFactory((GameScreen)NamedScreen.LANGUAGE_SELECTION, () -> new blc(bib.z().m, bib.z().t, bib.z().Q()));
        this.registerFactory(NamedScreen.OPEN_LAN_WORLD, (Function<blk, blk>)bll::new);
    }
    
    @Override
    protected void registerFactory(final GameScreen screen, final Function<blk, blk> screenFactory) {
        this.registerFactory(screen, () -> screenFactory.apply(bib.z().m));
    }
    
    @Override
    public boolean isInventoryScreen(final Object screen) {
        return screen instanceof bmg;
    }
}
