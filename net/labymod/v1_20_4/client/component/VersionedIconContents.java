// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.component;

import com.mojang.datafixers.kinds.Applicative;
import net.labymod.api.Laby;
import com.mojang.serialization.Codec;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.icon.Icon;
import com.mojang.serialization.MapCodec;

public class VersionedIconContents implements vg
{
    private static final MapCodec<VersionedIconContents> CODEC;
    private static final vg.a<VersionedIconContents> TYPE;
    private Icon icon;
    private int width;
    private int height;
    private String placeholder;
    
    public VersionedIconContents(final Icon icon) {
        this.icon = icon;
    }
    
    @NotNull
    public Icon icon() {
        return this.icon;
    }
    
    public void setIcon(final Icon icon) {
        this.icon = icon;
    }
    
    public int width() {
        return this.width;
    }
    
    public void setWidth(final int width) {
        this.width = width;
    }
    
    public int height() {
        return this.height;
    }
    
    public void setHeight(final int height) {
        this.height = height;
    }
    
    @NotNull
    public String placeholder() {
        return this.placeholder;
    }
    
    public void setPlaceholder(final String placeholder) {
        this.placeholder = placeholder;
    }
    
    @NotNull
    public <T> Optional<T> a(@NotNull final vk.a<T> consumer) {
        return consumer.accept(this.placeholder);
    }
    
    public vg.a<?> a() {
        return VersionedIconContents.TYPE;
    }
    
    @NotNull
    public <T> Optional<T> a(@NotNull final vk.b<T> consumer, @NotNull final wc style) {
        return consumer.accept(style, this.placeholder);
    }
    
    @NotNull
    @Override
    public String toString() {
        return "icon{icon=" + String.valueOf(this.icon) + ", width=" + this.width + ", height=" + this.height + ", placeholder='" + this.placeholder + "'}";
    }
    
    static {
        CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Codec.STRING.fieldOf("icon").forGetter(versionedIconContents -> versionedIconContents.icon().getResourceLocation().toString())).apply((Applicative)$$0, s -> new VersionedIconContents(Icon.texture(Laby.references().resourceLocationFactory().parse(s)))));
        TYPE = new vg.a((MapCodec)VersionedIconContents.CODEC, "icon");
    }
}
