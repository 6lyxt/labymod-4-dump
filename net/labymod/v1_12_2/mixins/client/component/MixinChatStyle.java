// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.component;

import org.spongepowered.asm.mixin.Overwrite;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_12_2.client.component.VersionedNamedTextColors;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Intrinsic;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.v1_12_2.client.component.VersionedTextColor;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.client.component.format.Style;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_12_2.client.component.VersionedStyle;

@Mixin({ hn.class })
@Implements({ @Interface(iface = Style.class, prefix = "style$", remap = Interface.Remap.NONE) })
public abstract class MixinChatStyle implements VersionedStyle
{
    @Shadow
    private a b;
    @Shadow
    private String j;
    @Shadow
    private hg h;
    @Shadow
    private hj i;
    @Shadow
    private Boolean c;
    @Shadow
    private Boolean d;
    @Shadow
    private Boolean e;
    @Shadow
    private Boolean f;
    @Shadow
    private Boolean g;
    @Shadow
    private hn a;
    private VersionedTextColor labyMod$color;
    
    @Shadow
    public abstract hn m();
    
    @Shadow
    public abstract boolean shadow$g();
    
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
    protected abstract hn o();
    
    @Override
    public HoverEvent<?> getHoverEvent() {
        return (HoverEvent)this.i;
    }
    
    @Override
    public Style hoverEvent(final HoverEvent<?> hoverEvent) {
        final hn shallowCopy = this.m();
        ((VersionedStyle)shallowCopy).setLabyColor(this.labyMod$color);
        shallowCopy.a((hj)hoverEvent);
        return (Style)shallowCopy;
    }
    
    @Override
    public ClickEvent getClickEvent() {
        return (ClickEvent)this.h;
    }
    
    @Override
    public Style clickEvent(final ClickEvent clickEvent) {
        final hn shallowCopy = this.m();
        ((VersionedStyle)shallowCopy).setLabyColor(this.labyMod$color);
        shallowCopy.a((hg)clickEvent);
        return (Style)shallowCopy;
    }
    
    @Override
    public Style insertion(final String insertion) {
        final hn shallowCopy = this.m();
        shallowCopy.a(insertion);
        return (Style)shallowCopy;
    }
    
    @Override
    public TextColor getColor() {
        if (this.labyMod$color != null) {
            return this.labyMod$color;
        }
        if (this.a == null) {
            return null;
        }
        return ((VersionedStyle)this.a).getColor();
    }
    
    @Override
    public Style color(final TextColor color) {
        final VersionedStyle shallowCopy = (VersionedStyle)this.m();
        shallowCopy.setLabyColor((VersionedTextColor)color);
        return shallowCopy;
    }
    
    @Override
    public ResourceLocation getFont() {
        return null;
    }
    
    @Override
    public Style font(final ResourceLocation font) {
        return this;
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
                return false;
            }
        }
    }
    
    @Override
    public boolean isDecorationSet(final TextDecoration decoration) {
        switch (decoration) {
            case BOLD: {
                return this.c != null;
            }
            case ITALIC: {
                return this.d != null;
            }
            case UNDERLINED: {
                return this.e != null;
            }
            case STRIKETHROUGH: {
                return this.f != null;
            }
            case OBFUSCATED: {
                return this.g != null;
            }
            default: {
                return false;
            }
        }
    }
    
    @Override
    public Style decorate(final TextDecoration decoration) {
        return this.updateDecoration(decoration, true);
    }
    
    @Override
    public Style undecorate(final TextDecoration decoration) {
        return this.updateDecoration(decoration, false);
    }
    
    @Override
    public Style unsetDecoration(final TextDecoration decoration) {
        return this.updateDecoration(decoration, null);
    }
    
    @Override
    public String getInsertion() {
        return this.j;
    }
    
    @Override
    public Style setLabyColor(final VersionedTextColor color) {
        this.labyMod$color = color;
        if (color == null) {
            this.b = null;
        }
        else {
            this.b = color.getFormatting();
        }
        return this;
    }
    
    @Intrinsic
    public boolean style$isEmpty() {
        return this.shadow$g();
    }
    
    @Inject(method = { "setColor" }, at = { @At("HEAD") })
    private void setColor(final a formatting, final CallbackInfoReturnable<hn> cir) {
        this.labyMod$color = VersionedNamedTextColors.byFormatting(formatting);
    }
    
    private Style updateDecoration(final TextDecoration decoration, final Boolean value) {
        switch (decoration) {
            case BOLD: {
                return this.set(value, this::b, style -> style.a(value));
            }
            case ITALIC: {
                return this.set(value, this::c, style -> style.b(value));
            }
            case UNDERLINED: {
                return this.set(value, this::e, style -> style.d(value));
            }
            case STRIKETHROUGH: {
                return this.set(value, this::d, style -> style.c(value));
            }
            case OBFUSCATED: {
                return this.set(value, this::f, style -> style.e(value));
            }
            default: {
                return this;
            }
        }
    }
    
    private Style set(final Boolean value, final Supplier<Boolean> getter, final Consumer<hn> setter) {
        if (value != null) {
            final Boolean currentState = getter.get();
            if (Objects.equals(currentState, value)) {
                return this;
            }
        }
        final hn shallowCopy = this.m();
        ((VersionedStyle)shallowCopy).setLabyColor(this.labyMod$color);
        setter.accept(shallowCopy);
        return (Style)shallowCopy;
    }
    
    @Overwrite
    @Override
    public int hashCode() {
        return Objects.hash(this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j);
    }
}
