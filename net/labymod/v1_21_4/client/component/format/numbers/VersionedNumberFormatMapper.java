// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.component.format.numbers;

import net.labymod.api.client.component.format.numbers.StyledFormat;
import net.labymod.api.client.component.format.numbers.FixedFormat;
import net.labymod.api.client.component.format.numbers.BlankFormat;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.numbers.NumberFormat;
import javax.inject.Inject;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.component.format.numbers.NumberFormatMapper;

@Singleton
@Implements(NumberFormatMapper.class)
public class VersionedNumberFormatMapper implements NumberFormatMapper
{
    private final ComponentMapper componentMapper;
    
    @Inject
    public VersionedNumberFormatMapper(final ComponentMapper componentMapper) {
        this.componentMapper = componentMapper;
    }
    
    @Override
    public NumberFormat fromMinecraft(final Object obj) {
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof yf)) {
            throw new IllegalArgumentException("Object is not an instance of " + String.valueOf(yf.class));
        }
        final yf numberFormat = (yf)obj;
        if (numberFormat instanceof yd) {
            return NumberFormat.blank();
        }
        if (numberFormat instanceof final ye fixedFormat) {
            return NumberFormat.fixed(this.componentMapper.fromMinecraftComponent(fixedFormat.b));
        }
        if (numberFormat instanceof final yi styledFormat) {
            return NumberFormat.styled((Style)styledFormat.e);
        }
        throw new IllegalArgumentException("Unsupported number format: " + String.valueOf(numberFormat.getClass()));
    }
    
    @Override
    public Object toMinecraft(final NumberFormat format) {
        if (format == null) {
            return null;
        }
        if (format instanceof BlankFormat) {
            return yd.a;
        }
        if (format instanceof final FixedFormat fixedFormat) {
            return new ye((wp)this.componentMapper.toMinecraftComponent(fixedFormat.value()));
        }
        if (format instanceof final StyledFormat styledFormat) {
            return new yi((xm)styledFormat.style());
        }
        throw new IllegalArgumentException("Unsupported number format: " + String.valueOf(format.getClass()));
    }
}
