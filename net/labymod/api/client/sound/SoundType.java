// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.sound;

import net.labymod.api.Constants;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;

public abstract class SoundType
{
    public static final SoundType BUTTON_CLICK;
    public static final SoundType SWITCH_TOGGLE_ON;
    public static final SoundType SWITCH_TOGGLE_OFF;
    public static final SoundType HUD_ALIGN;
    public static final SoundType HUD_ATTACH;
    public static final SoundType HUD_TRASH;
    public static final SoundType SERVER_MOVE;
    protected final String identifier;
    protected final SoundType parent;
    protected ResourceLocation location;
    
    protected SoundType(final String identifier, final ResourceLocation location, final SoundType parent) {
        this.identifier = identifier;
        this.location = location;
        this.parent = parent;
    }
    
    public static SoundType create(final String identifier, final ResourceLocation location) {
        return Laby.references().soundService().createSoundType(identifier, location, null);
    }
    
    public static SoundType create(final String identifier, final SoundType parent) {
        return Laby.references().soundService().createSoundType(identifier, null, parent);
    }
    
    public static SoundType create(final String identifier) {
        return Laby.references().soundService().createSoundType(identifier, null, null);
    }
    
    @NotNull
    public String getIdentifier() {
        return this.identifier;
    }
    
    @Nullable
    public ResourceLocation getLocation() {
        if (this.location != null) {
            return this.location;
        }
        return (this.parent == null) ? null : this.parent.getLocation();
    }
    
    public SoundType getParent() {
        return this.parent;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SoundType)) {
            return false;
        }
        final SoundType soundType = (SoundType)o;
        return this.identifier.equals(soundType.identifier) && Objects.equals(this.parent, soundType.parent) && Objects.equals(this.location, soundType.location);
    }
    
    @Override
    public int hashCode() {
        int result = (this.identifier != null) ? this.identifier.hashCode() : 0;
        result = 31 * result + ((this.parent != null) ? this.parent.hashCode() : 0);
        result = 31 * result + ((this.location != null) ? this.location.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "SoundType{identifier='" + this.identifier + "', parent=" + String.valueOf(this.parent) + ", location=" + String.valueOf(this.location);
    }
    
    static {
        BUTTON_CLICK = create("ui.button.click");
        SWITCH_TOGGLE_ON = create("ui.switch.on", SoundType.BUTTON_CLICK);
        SWITCH_TOGGLE_OFF = create("ui.switch.off", SoundType.BUTTON_CLICK);
        HUD_ALIGN = create("hudwidget.align", Constants.Resources.SOUND_HUDWIDGET_ALIGN);
        HUD_ATTACH = create("hudwidget.attach", Constants.Resources.SOUND_HUDWIDGET_ATTACH);
        HUD_TRASH = create("hudwidget.detach", Constants.Resources.SOUND_HUDWIDGET_TRASH);
        SERVER_MOVE = create("ui.server.move", Constants.Resources.SOUND_UI_SERVER_MOVE);
    }
}
