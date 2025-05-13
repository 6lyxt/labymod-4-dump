// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.chat;

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
import javax.annotation.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.component.format.Style;

@Mixin({ yd.class })
@Implements({ @Interface(iface = Style.class, prefix = "style$", remap = Interface.Remap.NONE) })
public abstract class MixinStyle implements Style
{
    @Shadow
    @Final
    public static alr b;
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
    @Shadow
    @Final
    @Nullable
    private Boolean i;
    private ResourceLocation labyMod$fontLocation;
    
    @Shadow
    @Nullable
    public abstract xm shadow$j();
    
    @Shadow
    public abstract yd a(@Nullable final xm p0);
    
    @Shadow
    @Nullable
    public abstract xe shadow$i();
    
    @Shadow
    public abstract yd a(@org.jetbrains.annotations.Nullable final xe p0);
    
    @Shadow
    public abstract yd a(@org.jetbrains.annotations.Nullable final yf p0);
    
    @Shadow
    @Nullable
    public abstract yf shadow$a();
    
    @Shadow
    @Nullable
    public abstract Integer shadow$b();
    
    @Shadow
    public abstract String shadow$k();
    
    @Shadow
    public abstract boolean c();
    
    @Shadow
    public abstract boolean d();
    
    @Shadow
    public abstract boolean f();
    
    @Shadow
    public abstract boolean e();
    
    @Shadow
    public abstract boolean g();
    
    @Shadow
    public abstract yd a(@org.jetbrains.annotations.Nullable final Boolean p0);
    
    @Shadow
    public abstract yd b(@org.jetbrains.annotations.Nullable final Boolean p0);
    
    @Shadow
    public abstract yd c(@org.jetbrains.annotations.Nullable final Boolean p0);
    
    @Shadow
    public abstract yd d(@org.jetbrains.annotations.Nullable final Boolean p0);
    
    @Shadow
    public abstract yd e(@org.jetbrains.annotations.Nullable final Boolean p0);
    
    @Shadow
    public abstract alr shadow$l();
    
    @Shadow
    public abstract yd a(@org.jetbrains.annotations.Nullable final alr p0);
    
    @Shadow
    public abstract yd a(@org.jetbrains.annotations.Nullable final String p0);
    
    @Intrinsic
    public HoverEvent<?> style$getHoverEvent() {
        return (HoverEvent)this.shadow$j();
    }
    
    @Override
    public Style hoverEvent(final HoverEvent<?> hoverEvent) {
        return (Style)this.a((xm)hoverEvent);
    }
    
    @Intrinsic
    public ClickEvent style$getClickEvent() {
        return (ClickEvent)this.shadow$i();
    }
    
    @Override
    public Style clickEvent(final ClickEvent clickEvent) {
        return (Style)this.a((xe)clickEvent);
    }
    
    @Override
    public Style insertion(final String insertion) {
        return (Style)this.a(insertion);
    }
    
    @Override
    public Style color(final TextColor color) {
        return (Style)this.a((yf)color);
    }
    
    @Override
    public Style font(final ResourceLocation font) {
        return (Style)this.a(font.getMinecraftLocation());
    }
    
    @Override
    public boolean hasDecoration(final TextDecoration decoration) {
        return switch (decoration) {
            default -> throw new MatchException(null, null);
            case BOLD -> this.c();
            case ITALIC -> this.d();
            case UNDERLINED -> this.f();
            case STRIKETHROUGH -> this.e();
            case OBFUSCATED -> this.g();
        };
    }
    
    @Override
    public boolean isDecorationSet(final TextDecoration decoration) {
        return switch (decoration) {
            default -> throw new MatchException(null, null);
            case BOLD -> this.e != null;
            case ITALIC -> this.f != null;
            case UNDERLINED -> this.g != null;
            case STRIKETHROUGH -> this.h != null;
            case OBFUSCATED -> this.i != null;
        };
    }
    
    @Override
    public Style decorate(final TextDecoration decoration) {
        return switch (decoration) {
            default -> throw new MatchException(null, null);
            case BOLD -> this.setBold(true);
            case ITALIC -> this.setItalic(true);
            case UNDERLINED -> this.setUnderlined(true);
            case STRIKETHROUGH -> this.setStrikethrough(true);
            case OBFUSCATED -> this.setObfuscated(true);
        };
    }
    
    @Override
    public Style undecorate(final TextDecoration decoration) {
        return switch (decoration) {
            default -> throw new MatchException(null, null);
            case BOLD -> this.setBold(false);
            case ITALIC -> this.setItalic(false);
            case UNDERLINED -> this.setUnderlined(false);
            case STRIKETHROUGH -> this.setStrikethrough(false);
            case OBFUSCATED -> this.setObfuscated(false);
        };
    }
    
    @Override
    public Style unsetDecoration(final TextDecoration decoration) {
        return switch (decoration) {
            default -> throw new MatchException(null, null);
            case BOLD -> this.setBold(null);
            case ITALIC -> this.setItalic(null);
            case UNDERLINED -> this.setUnderlined(null);
            case STRIKETHROUGH -> this.setStrikethrough(null);
            case OBFUSCATED -> this.setObfuscated(null);
        };
    }
    
    @Override
    public boolean isEmpty() {
        return this == yd.a;
    }
    
    @Intrinsic
    public TextColor style$getColor() {
        return CastUtil.cast(this.shadow$a());
    }
    
    @Intrinsic
    public Integer style$getShadowColor() {
        return this.shadow$b();
    }
    
    @Intrinsic
    public String style$getInsertion() {
        return this.shadow$k();
    }
    
    @Intrinsic
    public ResourceLocation style$getFont() {
        if (this.labyMod$fontLocation == null) {
            final alr resourceLocation = this.shadow$l();
            if (resourceLocation == MixinStyle.b) {
                return null;
            }
            this.labyMod$fontLocation = (ResourceLocation)resourceLocation;
        }
        return this.labyMod$fontLocation;
    }
    
    private Style setBold(final Boolean value) {
        return this.set(value, this::c, this::a);
    }
    
    private Style setItalic(final Boolean value) {
        return this.set(value, this::d, this::b);
    }
    
    private Style setUnderlined(final Boolean value) {
        return this.set(value, this::f, this::c);
    }
    
    private Style setStrikethrough(final Boolean value) {
        return this.set(value, this::e, this::d);
    }
    
    private Style setObfuscated(final Boolean value) {
        return this.set(value, this::g, this::e);
    }
    
    private Style set(final Boolean value, final Supplier<Boolean> getter, final Function<Boolean, yd> setter) {
        final Boolean currentState = getter.get();
        if (Objects.equals(value, currentState)) {
            return this;
        }
        return (Style)setter.apply(value);
    }
}
