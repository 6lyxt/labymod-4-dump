// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.chat;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import net.labymod.api.util.CastUtil;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.event.ClickEvent;
import org.spongepowered.asm.mixin.Intrinsic;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.format.Style;

@Mixin({ ob.class })
@Implements({ @Interface(iface = Style.class, prefix = "style$", remap = Interface.Remap.NONE) })
public abstract class MixinStyle implements Style
{
    @Shadow
    @Final
    public static vk b;
    @Shadow
    @Final
    @Nullable
    private Boolean d;
    @Shadow
    @Final
    @Nullable
    private Boolean e;
    @Shadow
    @Final
    @Nullable
    private Boolean f;
    @Shadow
    @Final
    @Nullable
    private Boolean g;
    @Shadow
    @Final
    @Nullable
    private Boolean h;
    private ResourceLocation labyMod$fontLocation;
    @Shadow
    @Final
    @Nullable
    private od c;
    @Shadow
    @Final
    @Nullable
    private np i;
    @Shadow
    @Final
    @Nullable
    private nv j;
    @Shadow
    @Final
    @Nullable
    private vk l;
    @Shadow
    @Final
    @Nullable
    private String k;
    
    @Shadow
    @Nullable
    public abstract nv shadow$i();
    
    @Shadow
    public abstract ob a(@Nullable final nv p0);
    
    @Shadow
    @Nullable
    public abstract np shadow$h();
    
    @Shadow
    public abstract ob a(@Nullable final np p0);
    
    @Shadow
    public abstract ob a(@Nullable final od p0);
    
    @Shadow
    @Nullable
    public abstract od shadow$a();
    
    @Shadow
    public abstract String shadow$j();
    
    @Shadow
    public abstract boolean b();
    
    @Shadow
    public abstract boolean c();
    
    @Shadow
    public abstract boolean e();
    
    @Shadow
    public abstract boolean d();
    
    @Shadow
    public abstract boolean f();
    
    @Shadow
    public abstract ob a(@Nullable final Boolean p0);
    
    @Shadow
    public abstract ob b(@Nullable final Boolean p0);
    
    @Shadow
    public abstract ob c(@Nullable final Boolean p0);
    
    @Shadow
    public abstract vk shadow$k();
    
    @Shadow
    public abstract ob a(@Nullable final vk p0);
    
    @Shadow
    public abstract ob a(final String p0);
    
    @Intrinsic
    public HoverEvent<?> style$getHoverEvent() {
        return (HoverEvent)this.shadow$i();
    }
    
    @Override
    public Style hoverEvent(final HoverEvent<?> hoverEvent) {
        return (Style)this.a((nv)hoverEvent);
    }
    
    @Intrinsic
    public ClickEvent style$getClickEvent() {
        return (ClickEvent)this.shadow$h();
    }
    
    @Override
    public Style clickEvent(final ClickEvent clickEvent) {
        return (Style)this.a((np)clickEvent);
    }
    
    @Override
    public Style insertion(final String insertion) {
        return (Style)this.a(insertion);
    }
    
    @Override
    public Style color(final TextColor color) {
        return (Style)this.a((od)color);
    }
    
    @Override
    public Style font(final ResourceLocation font) {
        return (Style)this.a(font.getMinecraftLocation());
    }
    
    @Override
    public boolean hasDecoration(final TextDecoration decoration) {
        switch (decoration) {
            case BOLD: {
                return this.b();
            }
            case ITALIC: {
                return this.c();
            }
            case UNDERLINED: {
                return this.e();
            }
            case STRIKETHROUGH: {
                return this.d();
            }
            case OBFUSCATED: {
                return this.f();
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(decoration));
            }
        }
    }
    
    @Override
    public boolean isDecorationSet(final TextDecoration decoration) {
        switch (decoration) {
            case BOLD: {
                return this.d != null;
            }
            case ITALIC: {
                return this.e != null;
            }
            case UNDERLINED: {
                return this.f != null;
            }
            case STRIKETHROUGH: {
                return this.g != null;
            }
            case OBFUSCATED: {
                return this.h != null;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(decoration));
            }
        }
    }
    
    @Override
    public Style decorate(final TextDecoration decoration) {
        switch (decoration) {
            case BOLD: {
                return this.setBold(true);
            }
            case ITALIC: {
                return this.setItalic(true);
            }
            case UNDERLINED: {
                return this.setUnderlined(true);
            }
            case STRIKETHROUGH: {
                return this.setStrikethrough(true);
            }
            case OBFUSCATED: {
                return this.setObfuscated(true);
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(decoration));
            }
        }
    }
    
    @Override
    public Style undecorate(final TextDecoration decoration) {
        switch (decoration) {
            case BOLD: {
                return this.setBold(false);
            }
            case ITALIC: {
                return this.setItalic(false);
            }
            case UNDERLINED: {
                return this.setUnderlined(false);
            }
            case STRIKETHROUGH: {
                return this.setStrikethrough(false);
            }
            case OBFUSCATED: {
                return this.setObfuscated(false);
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(decoration));
            }
        }
    }
    
    @Override
    public Style unsetDecoration(final TextDecoration decoration) {
        switch (decoration) {
            case BOLD: {
                return this.setBold(null);
            }
            case ITALIC: {
                return this.setItalic(null);
            }
            case UNDERLINED: {
                return this.setUnderlined(null);
            }
            case STRIKETHROUGH: {
                return this.setStrikethrough(null);
            }
            case OBFUSCATED: {
                return this.setObfuscated(null);
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(decoration));
            }
        }
    }
    
    @Override
    public boolean isEmpty() {
        return this == ob.a;
    }
    
    @Intrinsic
    public TextColor style$getColor() {
        return CastUtil.cast(this.shadow$a());
    }
    
    @Intrinsic
    public String style$getInsertion() {
        return this.shadow$j();
    }
    
    @Intrinsic
    public ResourceLocation style$getFont() {
        if (this.labyMod$fontLocation == null) {
            final vk resourceLocation = this.shadow$k();
            if (resourceLocation == MixinStyle.b) {
                return null;
            }
            this.labyMod$fontLocation = (ResourceLocation)resourceLocation;
        }
        return this.labyMod$fontLocation;
    }
    
    private Style setBold(final Boolean value) {
        return this.set(value, this::b, this::a);
    }
    
    private Style setItalic(final Boolean value) {
        return this.set(value, this::c, this::b);
    }
    
    private Style setUnderlined(final Boolean value) {
        return this.set(value, this::e, this::c);
    }
    
    private Style setStrikethrough(final Boolean value) {
        return this.set(value, this::d, v -> new ob(this.c, this.d, this.e, this.f, v, this.h, this.i, this.j, this.k, this.l));
    }
    
    private Style setObfuscated(final Boolean value) {
        return this.set(value, this::f, v -> new ob(this.c, this.d, this.e, this.f, this.g, v, this.i, this.j, this.k, this.l));
    }
    
    private Style set(final Boolean value, final Supplier<Boolean> getter, final Function<Boolean, ob> setter) {
        final Boolean currentState = getter.get();
        if (Objects.equals(value, currentState)) {
            return this;
        }
        return (Style)setter.apply(value);
    }
}
