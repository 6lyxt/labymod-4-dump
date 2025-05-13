// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client.chat;

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

@Mixin({ to.class })
@Implements({ @Interface(iface = Style.class, prefix = "style$", remap = Interface.Remap.NONE) })
public abstract class MixinStyle implements Style
{
    @Shadow
    @Final
    public static acf c;
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
    public abstract sx shadow$i();
    
    @Shadow
    public abstract to a(@Nullable final sx p0);
    
    @Shadow
    @Nullable
    public abstract sq shadow$h();
    
    @Shadow
    public abstract to a(@Nullable final sq p0);
    
    @Shadow
    public abstract to a(@Nullable final tq p0);
    
    @Shadow
    @Nullable
    public abstract tq shadow$a();
    
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
    public abstract to a(@Nullable final Boolean p0);
    
    @Shadow
    public abstract to b(@Nullable final Boolean p0);
    
    @Shadow
    public abstract to c(@Nullable final Boolean p0);
    
    @Shadow
    public abstract to d(@Nullable final Boolean p0);
    
    @Shadow
    public abstract to e(@Nullable final Boolean p0);
    
    @Shadow
    public abstract acf shadow$k();
    
    @Shadow
    public abstract to a(@Nullable final acf p0);
    
    @Shadow
    public abstract to a(@Nullable final String p0);
    
    @Intrinsic
    public HoverEvent<?> style$getHoverEvent() {
        return (HoverEvent)this.shadow$i();
    }
    
    @Override
    public Style hoverEvent(final HoverEvent<?> hoverEvent) {
        return (Style)this.a((sx)hoverEvent);
    }
    
    @Intrinsic
    public ClickEvent style$getClickEvent() {
        return (ClickEvent)this.shadow$h();
    }
    
    @Override
    public Style clickEvent(final ClickEvent clickEvent) {
        return (Style)this.a((sq)clickEvent);
    }
    
    @Override
    public Style insertion(final String insertion) {
        return (Style)this.a(insertion);
    }
    
    @Override
    public Style color(final TextColor color) {
        return (Style)this.a((tq)color);
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
        return this == to.a;
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
            final acf resourceLocation = this.shadow$k();
            if (resourceLocation == MixinStyle.c) {
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
    
    private Style set(final Boolean value, final Supplier<Boolean> getter, final Function<Boolean, to> setter) {
        final Boolean currentState = getter.get();
        if (Objects.equals(value, currentState)) {
            return this;
        }
        return (Style)setter.apply(value);
    }
}
