// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.blockentity;

import net.labymod.api.client.component.serializer.plain.PlainTextComponentSerializer;
import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.NotNull;

public interface SignBlockEntity extends BlockEntity
{
    float getRotationYaw();
    
    SignType getType();
    
    boolean hasSide(@NotNull final SignSide p0);
    
    @NotNull
    default Component[] getLines() {
        return this.getLines(SignSide.FRONT);
    }
    
    @NotNull
    Component[] getLines(@NotNull final SignSide p0) throws IllegalArgumentException;
    
    @NotNull
    default String[] getStringLines() {
        return this.getStringLines(SignSide.FRONT);
    }
    
    @NotNull
    default String[] getStringLines(@NotNull final SignSide side) throws IllegalArgumentException {
        final Component[] lines = this.getLines(side);
        final String[] stringLines = new String[lines.length];
        for (int i = 0; i < lines.length; ++i) {
            stringLines[i] = PlainTextComponentSerializer.plainText().serialize(lines[i]);
        }
        return stringLines;
    }
    
    public enum SignType
    {
        WALL_SIGN, 
        STANDING_SIGN, 
        WALL_HANGING_SIGN, 
        CEILING_HANGING_SIGN;
    }
    
    public enum SignSide permits SignBlockEntity$SignSide$1
    {
        FRONT, 
        BACK {
            @Override
            public float modifyYaw(final float yaw) {
                return yaw - 180.0f;
            }
        };
        
        public float modifyYaw(final float yaw) {
            return yaw;
        }
    }
}
