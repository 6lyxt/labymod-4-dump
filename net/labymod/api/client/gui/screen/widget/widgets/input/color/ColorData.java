// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input.color;

import java.util.Iterator;
import java.util.Objects;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.util.Color;

public class ColorData
{
    private Color color;
    private Color actualColor;
    private int saturation;
    private int brightness;
    private boolean alpha;
    private boolean chroma;
    private boolean removeAlpha;
    private boolean chromaSpeed;
    private boolean removeChromaSpeed;
    private final Map<String, Runnable> updateListeners;
    
    protected ColorData(final Color color) {
        this.saturation = -1;
        this.brightness = -1;
        this.updateListeners = new HashMap<String, Runnable>();
        this.set(color);
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public Color getActualColor() {
        return this.actualColor;
    }
    
    public void setAlpha(final int alpha) {
        if (this.removeAlpha()) {
            return;
        }
        this.set(this.actualColor.withAlpha(alpha));
    }
    
    public void setHue(final int hue) {
        this.set(this.actualColor.withHue(hue));
    }
    
    public void setSaturation(final int saturation) {
        this.saturation = saturation;
    }
    
    public void setBrightness(final int brightness) {
        this.brightness = brightness;
    }
    
    public void setRgb(final int rgb) {
        int alpha = 255;
        if (!this.removeAlpha() && this.actualColor.getAlpha() != 255) {
            alpha = this.actualColor.getAlpha();
        }
        final Color color = Color.of(rgb, alpha);
        if (this.actualColor.isChroma()) {
            this.set(color, color.withChroma(this.actualColor.getChromaSpeed()));
        }
        else {
            this.set(color, color.withoutChroma(this.actualColor.getChromaSpeed()));
        }
    }
    
    public void applySB() {
        if (this.brightness == -1 && this.saturation == -1) {
            return;
        }
        int brightness = this.brightness;
        int saturation = this.saturation;
        final int hue = this.color.getHue();
        if (brightness == -1) {
            brightness = this.color.getBrightness();
        }
        if (saturation == -1) {
            saturation = this.color.getSaturation();
        }
        final Color color = Color.ofHSB(hue, saturation, brightness, this.actualColor.getAlpha());
        if (this.actualColor.isChroma()) {
            this.set(color, color.withChroma());
        }
        else {
            this.set(color);
        }
        this.brightness = -1;
        this.saturation = -1;
    }
    
    public void setChroma(final boolean chroma) {
        this.set(chroma ? this.actualColor.withChroma() : this.actualColor.withoutChroma());
    }
    
    public void setChromaSpeed(final float chromaSpeed) {
        this.set(this.actualColor.isChroma() ? this.actualColor.withChroma(chromaSpeed) : this.actualColor.withoutChroma(chromaSpeed));
    }
    
    public void setValue(final int value) {
        if (this.color.get() == value) {
            return;
        }
        final Color color = Color.of(value, this.actualColor.getAlpha());
        if (this.actualColor.isChroma()) {
            final Color chroma = color.withChroma();
            this.set(color, chroma);
            return;
        }
        this.set(color);
    }
    
    public void set(final Color color) {
        this.set(color.withoutChroma(), color);
    }
    
    private void set(final Color color, final Color actualColor) {
        if (Objects.equals(this.actualColor, actualColor)) {
            return;
        }
        this.color = color;
        this.actualColor = actualColor;
        for (final Runnable updateListener : this.updateListeners.values()) {
            updateListener.run();
        }
    }
    
    public boolean enabledAlpha() {
        return this.alpha;
    }
    
    public boolean enabledChroma() {
        return this.chroma;
    }
    
    public boolean enabledChromaSpeed() {
        return this.chromaSpeed;
    }
    
    public ColorData alphaEnabled(final boolean alpha) {
        this.alpha = alpha;
        return this;
    }
    
    public ColorData chromaEnabled(final boolean chroma) {
        this.chroma = chroma;
        return this;
    }
    
    public ColorData removeAlpha(final boolean removeAlpha) {
        this.removeAlpha = removeAlpha;
        if (this.removeAlpha()) {
            this.set(this.actualColor.withoutAlpha());
        }
        return this;
    }
    
    public ColorData chromaSpeedEnabled(final boolean chromaSpeed) {
        this.chromaSpeed = chromaSpeed;
        return this;
    }
    
    public ColorData removeChromaSpeed(final boolean removeChromaSpeed) {
        this.removeChromaSpeed = removeChromaSpeed;
        if (this.removeChromaSpeed()) {
            this.set(this.actualColor.resetChromaSpeed());
        }
        return this;
    }
    
    public void addUpdateListener(final Object instance, final Runnable updateListener) {
        this.updateListeners.put(instance.getClass().getSimpleName(), updateListener);
    }
    
    private boolean removeAlpha() {
        return !this.alpha && this.removeAlpha;
    }
    
    private boolean removeChromaSpeed() {
        return !this.chromaSpeed && this.removeChromaSpeed;
    }
}
