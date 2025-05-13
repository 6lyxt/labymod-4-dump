// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.client.gui.window;

import net.labymod.api.client.gui.window.TooManyIterationsException;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.v1_17_1.client.gui.screen.LabyScreenRenderer;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.core.client.gui.window.DefaultAbstractWindow;

public class VersionedWindow extends DefaultAbstractWindow
{
    @Override
    public int getRawWidth() {
        return dvp.C().aD().k();
    }
    
    @Override
    public int getRawHeight() {
        return dvp.C().aD().l();
    }
    
    @Override
    public int getAbsoluteScaledWidth() {
        return dvp.C().aD().o();
    }
    
    @Override
    public int getAbsoluteScaledHeight() {
        return dvp.C().aD().p();
    }
    
    @Override
    public long getPointer() {
        final dpr window = dvp.C().aD();
        if (window == null) {
            return 0L;
        }
        return window.i();
    }
    
    @Override
    public float getScale() {
        return (float)dvp.C().aD().s();
    }
    
    @Override
    public boolean isFocused() {
        return dvp.C().ap();
    }
    
    @Override
    public void resetBounds() {
        final dpr window = dvp.C().aD();
        this.bounds.setBounds(0.0f, 0.0f, (float)window.o(), (float)window.p(), VersionedWindow.WINDOW_RESET);
        this.absoluteBounds.setBounds(0.0f, 0.0f, (float)window.o(), (float)window.p(), VersionedWindow.WINDOW_RESET);
    }
    
    @Override
    public void resetAbsoluteBounds() {
        final dpr window = dvp.C().aD();
        this.absoluteBounds.setBounds(0.0f, 0.0f, (float)window.o(), (float)window.p(), VersionedWindow.WINDOW_RESET);
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
            dvp.C().a((eaq)screenObject);
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public ScreenWrapper currentScreen() {
        final eaq screen = dvp.C().y;
        if (screen == null) {
            this.currentScreen = null;
            return null;
        }
        if (screen != this.currentScreen) {
            this.currentScreen = screen;
            final ScreenWrapper wrapper = Laby.references().screenWrapperFactory().create(screen);
            final boolean previousScreen = !(screen instanceof dzp);
            wrapper.metadata().set("isPreviousScreen", previousScreen);
            this.currentScreenWrapper = wrapper;
        }
        return this.currentScreenWrapper;
    }
    
    @Override
    public LabyScreen currentLabyScreen() {
        final eaq screen = dvp.C().y;
        if (screen instanceof final LabyScreenRenderer labyScreenRenderer) {
            return labyScreenRenderer.screen();
        }
        return null;
    }
    
    @Override
    public String getCurrentScreenName() {
        Object screen = dvp.C().y;
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
        return this.getMostInnerScreen(dvp.C().y);
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
