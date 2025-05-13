// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.component.format.numbers;

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
        if (!(obj instanceof wv)) {
            throw new IllegalArgumentException("Object is not an instance of " + String.valueOf(wv.class));
        }
        final wv numberFormat = (wv)obj;
        if (numberFormat instanceof wt) {
            return NumberFormat.blank();
        }
        if (numberFormat instanceof final wu fixedFormat) {
            return NumberFormat.fixed(this.componentMapper.fromMinecraftComponent(fixedFormat.b));
        }
        if (numberFormat instanceof final wy styledFormat) {
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
            return wt.a;
        }
        if (format instanceof final FixedFormat fixedFormat) {
            return new wu((vf)this.componentMapper.toMinecraftComponent(fixedFormat.value()));
        }
        if (format instanceof final StyledFormat styledFormat) {
            return new wy((wc)styledFormat.style());
        }
        throw new IllegalArgumentException("Unsupported number format: " + String.valueOf(format.getClass()));
    }
}
