// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins.client.chat;

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

@Mixin({ ym.class })
@Implements({ @Interface(iface = Style.class, prefix = "style$", remap = Interface.Remap.NONE) })
public abstract class MixinStyle implements Style
{
    @Shadow
    @Final
    public static alf b;
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
    @Nullable
    public abstract xv shadow$i();
    
    @Shadow
    public abstract ym a(@Nullable final xv p0);
    
    @Shadow
    @Nullable
    public abstract xn shadow$h();
    
    @Shadow
    public abstract ym a(@Nullable final xn p0);
    
    @Shadow
    public abstract ym a(@Nullable final yo p0);
    
    @Shadow
    @Nullable
    public abstract yo shadow$a();
    
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
    public abstract ym a(@Nullable final Boolean p0);
    
    @Shadow
    public abstract ym b(@Nullable final Boolean p0);
    
    @Shadow
    public abstract ym c(@Nullable final Boolean p0);
    
    @Shadow
    public abstract ym d(@Nullable final Boolean p0);
    
    @Shadow
    public abstract ym e(@Nullable final Boolean p0);
    
    @Shadow
    public abstract alf shadow$k();
    
    @Shadow
    public abstract ym a(@Nullable final alf p0);
    
    @Shadow
    public abstract ym a(@Nullable final String p0);
    
    @Intrinsic
    public HoverEvent<?> style$getHoverEvent() {
        return (HoverEvent)this.shadow$i();
    }
    
    @Override
    public Style hoverEvent(final HoverEvent<?> hoverEvent) {
        return (Style)this.a((xv)hoverEvent);
    }
    
    @Intrinsic
    public ClickEvent style$getClickEvent() {
        return (ClickEvent)this.shadow$h();
    }
    
    @Override
    public Style clickEvent(final ClickEvent clickEvent) {
        return (Style)this.a((xn)clickEvent);
    }
    
    @Override
    public Style insertion(final String insertion) {
        return (Style)this.a(insertion);
    }
    
    @Override
    public Style color(final TextColor color) {
        return (Style)this.a((yo)color);
    }
    
    @Override
    public Style font(final ResourceLocation font) {
        return (Style)this.a(font.getMinecraftLocation());
    }
    
    @Override
    public boolean hasDecoration(final TextDecoration decoration) {
        return switch (decoration) {
            default -> throw new MatchException(null, null);
            case BOLD -> this.b();
            case ITALIC -> this.c();
            case UNDERLINED -> this.e();
            case STRIKETHROUGH -> this.d();
            case OBFUSCATED -> this.f();
        };
    }
    
    @Override
    public boolean isDecorationSet(final TextDecoration decoration) {
        return switch (decoration) {
            default -> throw new MatchException(null, null);
            case BOLD -> this.d != null;
            case ITALIC -> this.e != null;
            case UNDERLINED -> this.f != null;
            case STRIKETHROUGH -> this.g != null;
            case OBFUSCATED -> this.h != null;
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
        return this == ym.a;
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
            final alf resourceLocation = this.shadow$k();
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
        return this.set(value, this::d, this::d);
    }
    
    private Style setObfuscated(final Boolean value) {
        return this.set(value, this::f, this::e);
    }
    
    private Style set(final Boolean value, final Supplier<Boolean> getter, final Function<Boolean, ym> setter) {
        final Boolean currentState = getter.get();
        if (Objects.equals(value, currentState)) {
            return this;
        }
        return (Style)setter.apply(value);
    }
}
