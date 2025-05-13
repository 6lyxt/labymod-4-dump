// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.client.gui.window;

import net.labymod.api.client.gui.window.TooManyIterationsException;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.v1_20_1.client.gui.screen.LabyScreenRenderer;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.core.client.gui.window.DefaultAbstractWindow;

public class VersionedWindow extends DefaultAbstractWindow
{
    @Override
    public int getRawWidth() {
        return enn.N().aM().k();
    }
    
    @Override
    public int getRawHeight() {
        return enn.N().aM().l();
    }
    
    @Override
    public int getAbsoluteScaledWidth() {
        return enn.N().aM().o();
    }
    
    @Override
    public int getAbsoluteScaledHeight() {
        return enn.N().aM().p();
    }
    
    @Override
    public long getPointer() {
        final ehn window = enn.N().aM();
        if (window == null) {
            return 0L;
        }
        return window.i();
    }
    
    @Override
    public float getScale() {
        return (float)enn.N().aM().s();
    }
    
    @Override
    public boolean isFocused() {
        return enn.N().aB();
    }
    
    @Override
    public void resetBounds() {
        final ehn window = enn.N().aM();
        this.bounds.setBounds(0.0f, 0.0f, (float)window.o(), (float)window.p(), VersionedWindow.WINDOW_RESET);
        this.absoluteBounds.setBounds(0.0f, 0.0f, (float)window.o(), (float)window.p(), VersionedWindow.WINDOW_RESET);
    }
    
    @Override
    public void resetAbsoluteBounds() {
        final ehn window = enn.N().aM();
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
            enn.N().a((euq)screenObject);
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public ScreenWrapper currentScreen() {
        final euq screen = enn.N().z;
        if (screen == null) {
            this.currentScreen = null;
            return null;
        }
        if (screen != this.currentScreen) {
            this.currentScreen = screen;
            final ScreenWrapper wrapper = Laby.references().screenWrapperFactory().create(screen);
            final boolean previousScreen = !(screen instanceof etk);
            wrapper.metadata().set("isPreviousScreen", previousScreen);
            this.currentScreenWrapper = wrapper;
        }
        return this.currentScreenWrapper;
    }
    
    @Override
    public LabyScreen currentLabyScreen() {
        final euq screen = enn.N().z;
        if (screen instanceof final LabyScreenRenderer labyScreenRenderer) {
            return labyScreenRenderer.screen();
        }
        return null;
    }
    
    @Override
    public String getCurrentScreenName() {
        Object screen = enn.N().z;
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
        return this.getMostInnerScreen(enn.N().z);
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
