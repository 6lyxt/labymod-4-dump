// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.gui.window;

import net.labymod.api.client.gui.window.TooManyIterationsException;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.v1_8_9.client.gui.screen.LabyScreenRenderer;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.ScreenInstance;
import org.lwjgl.opengl.Display;
import net.labymod.core.client.gui.window.DefaultAbstractWindow;

public class VersionedWindow extends DefaultAbstractWindow
{
    private avr scaledResolution;
    
    @Override
    public int getRawWidth() {
        return this.getScaledResolution().a() * this.getScaledResolution().e();
    }
    
    @Override
    public int getRawHeight() {
        return this.getScaledResolution().b() * this.getScaledResolution().e();
    }
    
    @Override
    public int getAbsoluteScaledWidth() {
        return this.getScaledResolution().a();
    }
    
    @Override
    public int getAbsoluteScaledHeight() {
        return this.getScaledResolution().b();
    }
    
    @Override
    public long getPointer() {
        return Display.getWindowHandle();
    }
    
    @Override
    public float getScale() {
        return (float)this.getScaledResolution().e();
    }
    
    @Override
    public boolean isFocused() {
        return Display.isActive();
    }
    
    @Override
    public void resetBounds() {
        final avr window = this.getScaledResolution();
        this.bounds.setBounds(0.0f, 0.0f, (float)window.a(), (float)window.b(), VersionedWindow.WINDOW_RESET);
        this.absoluteBounds.setBounds(0.0f, 0.0f, (float)window.a(), (float)window.b(), VersionedWindow.WINDOW_RESET);
    }
    
    @Override
    public void resetAbsoluteBounds() {
        final avr window = this.getScaledResolution();
        this.absoluteBounds.setBounds(0.0f, 0.0f, (float)window.a(), (float)window.b(), VersionedWindow.WINDOW_RESET);
    }
    
    private avr getScaledResolution() {
        final ave minecraft = ave.A();
        boolean updateScaledResolution = this.scaledResolution == null;
        if (!updateScaledResolution) {
            float width;
            float height;
            if (minecraft.m != null) {
                width = (float)minecraft.m.l;
                height = (float)minecraft.m.m;
            }
            else {
                final float scale = (float)this.scaledResolution.e();
                width = minecraft.d / scale;
                height = minecraft.e / scale;
            }
            if (width != this.scaledResolution.a()) {
                updateScaledResolution = true;
            }
            if (height != this.scaledResolution.b()) {
                updateScaledResolution = true;
            }
        }
        if (updateScaledResolution) {
            this.scaledResolution = new avr(minecraft);
        }
        return this.scaledResolution;
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
            ave.A().a((axu)screenObject);
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public ScreenWrapper currentScreen() {
        final axu screen = ave.A().m;
        if (screen == null) {
            this.currentScreen = null;
            return null;
        }
        if (screen != this.currentScreen) {
            this.currentScreen = screen;
            final ScreenWrapper wrapper = Laby.references().screenWrapperFactory().create(screen);
            final boolean previousScreen = !(screen instanceof awy);
            wrapper.metadata().set("isPreviousScreen", previousScreen);
            this.currentScreenWrapper = wrapper;
        }
        return this.currentScreenWrapper;
    }
    
    @Override
    public LabyScreen currentLabyScreen() {
        final axu screen = ave.A().m;
        if (screen instanceof final LabyScreenRenderer labyScreenRenderer) {
            return labyScreenRenderer.screen();
        }
        return null;
    }
    
    @Override
    public String getCurrentScreenName() {
        Object screen = ave.A().m;
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
        return this.getMostInnerScreen(ave.A().m);
    }
    
    @Override
    public Object getMostInnerScreen(final Object screen) throws TooManyIterationsException {
        if (screen instanceof final LabyScreenRenderer labyScreenRenderer) {
            return labyScreenRenderer.screen().mostInnerScreen();
        }
        if (screen instanceof final ScreenInstance screenInstance) {
            return screenInstance.mostInnerScreen();
        }
        return screen;
    }
}
