// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.embed.content.field;

public interface FormButton extends FormField<Void>
{
    default boolean isSubmit() {
        return true;
    }
}
