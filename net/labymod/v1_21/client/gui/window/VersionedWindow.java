// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.client.gui.window;

import net.labymod.api.client.gui.window.TooManyIterationsException;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.v1_21.client.gui.screen.LabyScreenRenderer;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.core.client.gui.window.DefaultAbstractWindow;

public class VersionedWindow extends DefaultAbstractWindow
{
    @Override
    public int getRawWidth() {
        return fgo.Q().aM().l();
    }
    
    @Override
    public int getRawHeight() {
        return fgo.Q().aM().m();
    }
    
    @Override
    public int getAbsoluteScaledWidth() {
        return fgo.Q().aM().p();
    }
    
    @Override
    public int getAbsoluteScaledHeight() {
        return fgo.Q().aM().q();
    }
    
    @Override
    public long getPointer() {
        final fam window = fgo.Q().aM();
        if (window == null) {
            return 0L;
        }
        return window.j();
    }
    
    @Override
    public float getScale() {
        return (float)fgo.Q().aM().t();
    }
    
    @Override
    public boolean isFocused() {
        return fgo.Q().aA();
    }
    
    @Override
    public void resetBounds() {
        final fam window = fgo.Q().aM();
        this.bounds.setBounds(0.0f, 0.0f, (float)window.p(), (float)window.q(), VersionedWindow.WINDOW_RESET);
        this.absoluteBounds.setBounds(0.0f, 0.0f, (float)window.p(), (float)window.q(), VersionedWindow.WINDOW_RESET);
    }
    
    @Override
    public void resetAbsoluteBounds() {
        final fam window = fgo.Q().aM();
        this.absoluteBounds.setBounds(0.0f, 0.0f, (float)window.p(), (float)window.q(), VersionedWindow.WINDOW_RESET);
    }
    
    @Override
    public void displayScreen(final ScreenInstance screen) {
        if (screen == null) {
            this.displayScreenRaw(null);
        }
        else {
            if (screen instanceof final LabyScreen labyScreen) {
                labyScreen.updatePreviousScreen();
                this.displayScreenRaw(new LabyScreenRenderer((LabyScreen)screen));
            }
            if (screen instanceof final ScreenWrapper screenWrapper) {
                this.displayScreenRaw(screenWrapper.getVersionedScreen());
            }
        }
    }
    
    @Override
    public void displayScreenRaw(final Object screenObject) {
        Laby.labyAPI().minecraft().updateKeyRepeatingMode(false);
        try {
            fgo.Q().a((fod)screenObject);
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public ScreenWrapper currentScreen() {
        final fod screen = fgo.Q().y;
        if (screen == null) {
            this.currentScreen = null;
            return null;
        }
        if (screen != this.currentScreen) {
            this.currentScreen = screen;
            final ScreenWrapper wrapper = Laby.references().screenWrapperFactory().create(screen);
            final boolean previousScreen = !(screen instanceof fnb);
            wrapper.metadata().set("isPreviousScreen", previousScreen);
            this.currentScreenWrapper = wrapper;
        }
        return this.currentScreenWrapper;
    }
    
    @Override
    public LabyScreen currentLabyScreen() {
        final fod screen = fgo.Q().y;
        if (screen instanceof final LabyScreenRenderer labyScreenRenderer) {
            return labyScreenRenderer.screen();
        }
        return null;
    }
    
    @Override
    public String getCurrentScreenName() {
        Object screen = fgo.Q().y;
        if (screen == null) {
            return null;
        }
        if (screen instanceof final LabyScreenRenderer labyScreenRenderer) {
            screen = labyScreenRenderer.screen();
        }
        return Laby.labyAPI().screenService().getScreenNameByClass(screen.getClass());
    }
    
    @Override
    public Object mostInnerScreen() throws TooManyIterationsException {
        return this.getMostInnerScreen(fgo.Q().y);
    }
    
    @Override
    public Object getMostInnerScreen(final Object screen) {
        if (screen instanceof final LabyScreenRenderer labyScreenRenderer) {
            return labyScreenRenderer.screen().mostInnerScreen();
        }
        if (screen instanceof final ScreenInstance screenInstance) {
            return screenInstance.mostInnerScreen();
        }
        return screen;
    }
}
